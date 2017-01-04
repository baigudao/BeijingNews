package com.jackie.beijingnews.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.jackie.beijingnews.base.BasePager;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/5.
 */
public class ContentFragmentAdapter extends PagerAdapter {

    private ArrayList<BasePager> basePagers;

    public ContentFragmentAdapter(ArrayList<BasePager> basePagers) {
        this.basePagers = basePagers;
    }

    @Override
    public int getCount() {
        return basePagers == null ? 0 : basePagers.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BasePager basePager = basePagers.get(position);//本质是各个子页面的实例
        View rootView = basePager.rootView;//通过BasePager实例得到视图
        //调用各个页面的initData()
        //basePager.initData();//初始化数据
        container.addView(rootView);
        return rootView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
