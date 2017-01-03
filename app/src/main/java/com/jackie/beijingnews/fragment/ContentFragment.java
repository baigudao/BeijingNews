package com.jackie.beijingnews.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;

import com.jackie.beijingnews.R;
import com.jackie.beijingnews.base.BaseFragment;
import com.jackie.beijingnews.utils.LogUtil;

/**
 * 作用：正文Fragment
 */
public class ContentFragment extends BaseFragment {

    private ViewPager viewPager;
    private RadioGroup rg_main;

    @Override
    public View initView() {
        LogUtil.e("正文Fragemnt视图被初始化了");
        View view = View.inflate(context, R.layout.content_fragment,null);
        viewPager = (ViewPager)view.findViewById(R.id.viewpager);
        rg_main = (RadioGroup)view.findViewById(R.id.rg_main);
        //1.把视图注入到框架中，让ContentFragment.this和View关联起来
        //x.view().inject(ContentFragment.this,view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("正文Fragment数据被初始化了");
        rg_main.check(R.id.rb_home);
    }
}
