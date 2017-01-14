package com.jackie.beijingnews;

import android.app.Application;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;


/**
 * 作用：代表整个软件
 */
public class BeijingNewsApplication extends Application {
    /**
     所有组件被创建之前执行
     */
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化xUtils
        x.Ext.setDebug(true);
        x.Ext.init(this);

        //初始化极光推送
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
    }
}
