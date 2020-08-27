package com.lhy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhy.blog.entity.Comment;
import com.lhy.blog.mapper.CommentMapper;
import com.lhy.blog.service.CommentService;
import com.lhy.blog.vo.CommentVO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

@Log4j2
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public Integer create(Comment comment, HttpServletRequest request) {
        try {
            comment.setIpv4(getIpAddr(request));
        } catch (Exception e){
            log.error("ip获取失败");
        }
        return commentMapper.insert(comment);
    }

    @Override
    public List<CommentVO> getAllCommentByArticleId(Integer articleId) {
        List<CommentVO> result = new ArrayList<>();
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", articleId).orderByAsc("create_time");
        List<Comment> commentList = commentMapper.selectList(wrapper);
        Map<String, Integer> map = new LinkedHashMap<>();
        for (int i=0; i<commentList.size(); i++) {
            Comment comment = commentList.get(i);
            if (StringUtils.isEmpty(comment.getParentId())){
                CommentVO commentVO = new CommentVO();
                BeanUtils.copyProperties(comment, commentVO);
                commentVO.setChildren(new ArrayList<>());
                result.add(commentVO);
            }
            map.put(comment.getId(), i);
        }
        for (Comment comment : commentList) {
            if (!StringUtils.isEmpty(comment.getParentId())) {
                addToCommentVOList(result, commentList, comment, map);
            }
        }
        Collections.reverse(result);
        return result;
    }

    private void addToCommentVOList(List<CommentVO> commentVOList, List<Comment> commentList,
                                    Comment comment, Map<String, Integer> map){
        Comment temp = new Comment();
        BeanUtils.copyProperties(comment, temp);
        while (temp.getParentId() != null){
            temp = commentList.get(map.get(temp.getParentId()));
        }
        for (CommentVO commentVO : commentVOList) {
            if (commentVO.getId().equals(temp.getId())){
                CommentVO t = new CommentVO();
                BeanUtils.copyProperties(comment, t);
                commentVO.getChildren().add(t);
                break;
            }
        }
    }

    /**
     * 获取客户端的IP地址的方法是：request.getRemoteAddr()，这种方法在大部分情况下都是有效的。
     * 但是在通过了Apache,Squid等反向代理软件就不能获取到客户端的真实IP地址了，如果通过了多级反向代理的话，
     * X-Forwarded-For的值并不止一个，而是一串IP值， 究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     * 例如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
     * 192.168.1.100 用户真实IP为： 192.168.1.110
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String unknown = "unknown";
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1")) {
                /** 根据网卡取本机配置的IP */
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                    ip = inet.getHostAddress();
                } catch (UnknownHostException e) {
                    log.error("IpHelper error." + e.toString());
                }
            }
        }

        // 对于通过多个代理的情况， 第一个IP为客户端真实IP,多个IP按照','分割 "***.***.***.***".length() = 15
        int ipMaxLength = 15;
        if (ip != null && ip.length() > ipMaxLength) {
            return ip.substring(0, ip.indexOf(","));
        }
        return ip;
    }

}
