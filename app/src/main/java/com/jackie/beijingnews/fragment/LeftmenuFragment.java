package com.jackie.beijingnews.fragment;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.jackie.beijingnews.base.BaseFragment;
import com.jackie.beijingnews.domain.NewsCenterPagerBean;
import com.jackie.beijingnews.utils.LogUtil;

import java.util.List;

/**
 * 作用：左侧菜单的Fragment
 */
public class LeftmenuFragment extends BaseFragment {

    private TextView textView;
    private List<NewsCenterPagerBean.DataEntity> data;

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

    /**
     * 接收数据
     *
     * @param data
     */
    public void setData(List<NewsCenterPagerBean.DataEntity> data) {
        this.data = data;
        for (int i = 0; i < data.size(); i++) {
            LogUtil.e("title==" + data.get(i).getTitle());
        }

//        //设置适配器
//        adapter = new LeftmenuFragmentAdapter();
//        listView.setAdapter(adapter);
//
//        //设置默认页面
//        swichPager(prePosition);

    }
}
