package com.jackie.beijingnews.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.jackie.beijingnews.R;
import com.jackie.beijingnews.base.BaseFragment;
import com.jackie.beijingnews.base.BasePager;
import com.jackie.beijingnews.pager.GovaffairPager;
import com.jackie.beijingnews.pager.HomePager;
import com.jackie.beijingnews.pager.NewsCenterPager;
import com.jackie.beijingnews.pager.SettingPager;
import com.jackie.beijingnews.pager.SmartServicePager;
import com.jackie.beijingnews.utils.LogUtil;
import com.jackie.beijingnews.view.NoScrollViewPager;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * 作用：正文Fragment
 */
public class ContentFragment extends BaseFragment {

    //2.初始化控件
    @ViewInject(R.id.viewpager)
    private NoScrollViewPager viewPager;
    @ViewInject(R.id.rg_main)
    private RadioGroup rg_main;

    /**
     * 装五个页面的集合
     */
    private ArrayList<BasePager> basePagers;

    @Override
    public View initView() {
        LogUtil.e("正文Fragemnt视图被初始化了");
        View view = View.inflate(context, R.layout.content_fragment, null);
        //1.把视图注入到框架中，让ContentFragment.this和View关联起来
        x.view().inject(ContentFragment.this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("正文Fragment数据被初始化了");

        //初始化五个页面，并且放入集合中
        basePagers = new ArrayList<>();
        basePagers.add(new HomePager(context));//主页面
        basePagers.add(new NewsCenterPager(context));//新闻中心页面
        basePagers.add(new SmartServicePager(context));//智慧服务页面
        basePagers.add(new GovaffairPager(context));//政要指南页面
        basePagers.add(new SettingPager(context));//设置中心面

        //设置ViewPager的适配器
        viewPager.setAdapter(new ContentFragmentAdapter());

        //设置RadioGroup的选中状态改变的监听
        rg_main.setOnCheckedChangeListener(new MyOnCheckedChangeListener());

        //设置默认选中首页
        rg_main.check(R.id.rb_home);
    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        /**
         * @param group     RadioGroup
         * @param checkedId 被选中的RadioButton的id
         */
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_home://主页radioButton的id
                    viewPager.setCurrentItem(0, false);
                    break;
                case R.id.rb_newscenter://新闻中心radioButton的id
                    viewPager.setCurrentItem(1, false);
                    break;
                case R.id.rb_smartservice://智慧服务radioButton的id
                    viewPager.setCurrentItem(2, false);
                    break;
                case R.id.rb_govaffair://政要指南的RadioButton的id
                    viewPager.setCurrentItem(3, false);
                    break;
                case R.id.rb_setting://设置中心RadioButton的id
                    viewPager.setCurrentItem(4, false);
                    break;
            }
        }
    }

    class ContentFragmentAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return basePagers == null ? 0 : basePagers.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager basePager = basePagers.get(position);
            View rootView = basePager.rootView;
            //调用各个页面的initData()
            basePager.initData();//初始化数据
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
}
