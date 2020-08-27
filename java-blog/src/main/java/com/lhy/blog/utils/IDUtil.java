package com.lhy.blog.utils;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class IDUtil {

    public static Integer generateUID(){
        Random random = new Random();
        StringBuilder result= new StringBuilder();
        //首字母不能为0
        result.append(random.nextInt(9) + 1);
        for(int i=0; i<5; i++){
            result.append(random.nextInt(10));
        }
        return Integer.parseInt(result.toString());
    }

    public static String generateUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

//    public static void main(String[] args){
//        for(int i=0; i<20; i++)
//            System.out.println(generateTimeStamp());
//    }

}
