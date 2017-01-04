package com.jackie.beijingnews.fragment;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.jackie.beijingnews.base.BaseFragment;
import com.jackie.beijingnews.utils.LogUtil;

/**
 * 作用：左侧菜单的Fragment
 */
public class LeftmenuFragment extends BaseFragment {

    private TextView textView;

    @Override
    public View initView() {
        LogUtil.e("左侧菜单视图被初始化了");
        textView = new TextView(context);
        textView.setText("我是左侧菜单哦！");
        textView.setTextSize(23);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.YELLOW);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("左侧菜单数据被初始化了");
    }
}
