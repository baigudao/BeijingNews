package com.jackie.beijingnews.menudatailpager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.jackie.beijingnews.base.MenuDetaiBasePager;

/**
 * 作用：互动详情页面
 */
public class InteracMenuDetailPager extends MenuDetaiBasePager {

    private TextView textView;

    public InteracMenuDetailPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        textView.setTextSize(22);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        textView.setText("互动详情页面");
    }
}
