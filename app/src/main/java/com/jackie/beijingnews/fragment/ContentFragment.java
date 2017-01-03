package com.jackie.beijingnews.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;

import com.jackie.beijingnews.R;
import com.jackie.beijingnews.base.BaseFragment;
import com.jackie.beijingnews.utils.LogUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 作用：正文Fragment
 */
public class ContentFragment extends BaseFragment {

    //2.初始化控件
    @ViewInject(R.id.viewpager)
    private ViewPager viewPager;
    @ViewInject(R.id.rg_main)
    private RadioGroup rg_main;

    @Override
    public View initView() {
        LogUtil.e("正文Fragemnt视图被初始化了");
        View view = View.inflate(context, R.layout.content_fragment,null);
        //1.把视图注入到框架中，让ContentFragment.this和View关联起来
        x.view().inject(ContentFragment.this,view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("正文Fragment数据被初始化了");
        rg_main.check(R.id.rb_home);
    }
}
