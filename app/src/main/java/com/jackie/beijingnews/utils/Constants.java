package com.jackie.beijingnews.utils;

/**
 * 作用：常量类，配置联网请求地址
 */
public class Constants {

    /**
     * 联网请求的ip和端口
     */
    public static final String BASE_URL = "http://169.254.163.31:8080/web_home";

    /**
     * 新闻中心的网络地址
     */
    public static final String NEWSCENTER_PAGER_URL = BASE_URL + "/static/api/news/categories.json";
}
