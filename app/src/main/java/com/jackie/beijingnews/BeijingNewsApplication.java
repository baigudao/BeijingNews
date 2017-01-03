package com.jackie.beijingnews;

import android.app.Application;

import org.xutils.x;


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
    }
}
