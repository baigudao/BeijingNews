package com.jackie.beijingnews.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jackie.beijingnews.R;

/**
 * Created by Administrator on 2017/1/13.
 */
public class RefreshListView extends ListView {

    /**
     * 下拉刷新和顶部轮播图
     */
    private LinearLayout headerView;

    private View ll_pull_down_refresh;
    private ImageView iv_arrow;
    private ProgressBar pb_status;
    private TextView tv_time;
    private TextView tv_status;
    /**
     * 下拉刷新的高
     */
    private int measuredHeight;

    public RefreshListView(Context context) {
        this(context, null);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView(context);
    }

    private void initHeaderView(Context context) {
        headerView = (LinearLayout) View.inflate(context, R.layout.refresh_header, null);
        ll_pull_down_refresh = headerView.findViewById(R.id.ll_pull_down_refresh);
        iv_arrow = (ImageView) headerView.findViewById(R.id.iv_arrow);
        pb_status = (ProgressBar) headerView.findViewById(R.id.pb_status);
        tv_status = (TextView) headerView.findViewById(R.id.tv_status);
        tv_time = (TextView) headerView.findViewById(R.id.tv_time);

        ll_pull_down_refresh.measure(0, 0);
        measuredHeight = ll_pull_down_refresh.getMeasuredHeight();

        //默认隐藏下拉刷新控件
        ll_pull_down_refresh.setPadding(0, measuredHeight, 0, 0);

        //添加ListView的头
        RefreshListView.this.addHeaderView(headerView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }
}
