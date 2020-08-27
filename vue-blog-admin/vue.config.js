module.exports = {
    devServer: {
        port: 8080,
        historyApiFallback: true,
        hot: true,
        inline: true,
        stats: { colors: true },
        proxy: {
            //匹配代理的url
            '/api': {
                // 目标服务器地址
                target: 'http://www.lihaoyuan.online/lhy-blog/admin',
                //路径重写
                pathRewrite: {'^/api' : ''},
                changeOrigin: true,
                secure: false,
            }
        },
        disableHostCheck:true
    },

    publicPath: process.env.NODE_ENV === 'production' ? '/back/' : '/',

    pwa: {
        iconPaths: {
            favicon32: './favicon.png',
            favicon16: './favicon.png',
            appleTouchIcon: './favicon.png',
            maskIcon: './favicon.png',
            msTileImage: './favicon.png'
        }
    },
};
