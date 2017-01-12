package com.jackie.beijingnews.menudatailpager.tabdetailpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jackie.beijingnews.R;
import com.jackie.beijingnews.base.MenuDetaiBasePager;
import com.jackie.beijingnews.domain.NewsCenterPagerBean;
import com.jackie.beijingnews.domain.TabDetailPagerBean;
import com.jackie.beijingnews.utils.CacheUtils;
import com.jackie.beijingnews.utils.Constants;
import com.jackie.beijingnews.utils.LogUtil;
import com.jackie.beijingnews.view.HorizontalScrollViewPager;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * 作用：页签详情页面
 */
public class TopicDetailPager extends MenuDetaiBasePager {

    private ImageOptions imageOptions;

    private HorizontalScrollViewPager viewPager;
    private TextView tv_title;
    private LinearLayout ll_point_group;
    private ListView listView;
    private final NewsCenterPagerBean.DataEntity.ChildrenData childrenData;
    private String url;
    /**
     * 顶部轮播图的数据
     */
    private List<TabDetailPagerBean.DataBean.TopnewsBean> topnews;
    /**
     * 新闻列表数据的集合
     */
    private List<TabDetailPagerBean.DataBean.NewsBean> news;
    private TabDetailPagerListAdapter adapter;


    public TopicDetailPager(Context context, NewsCenterPagerBean.DataEntity.ChildrenData childrenData) {
        super(context);
        this.childrenData = childrenData;
        imageOptions = new ImageOptions.Builder()
                .setSize(DensityUtil.dip2px(100), DensityUtil.dip2px(100))
                .setRadius(DensityUtil.dip2px(5))
                // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setCrop(true) // 很多时候设置了合适的scaleType也不需要它.
                // 加载中或错误图片的ScaleType
                //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.drawable.news_pic_default)
                .setFailureDrawableId(R.drawable.news_pic_default)
                .build();
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.topic_detail_pager, null);
        listView = (ListView) view.findViewById(R.id.listview);

        View topNewsView = View.inflate(context, R.layout.topnews, null);
        viewPager = (HorizontalScrollViewPager) topNewsView.findViewById(R.id.viewpager);
        tv_title = (TextView) topNewsView.findViewById(R.id.tv_title);
        ll_point_group = (LinearLayout) topNewsView.findViewById(R.id.ll_point_group);
        //以头部的方式将topNewsView加入ListView中
        listView.addHeaderView(topNewsView);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        url = Constants.BASE_URL + childrenData.getUrl();
        //把之前缓存的数据取出
        String saveJson = CacheUtils.getString(context, url);
        if (!TextUtils.isEmpty(saveJson)) {
//            //解析数据和处理显示数据
            processData(saveJson);
        }
        LogUtil.e(childrenData.getTitle() + "的联网地址==" + url);
        //联网请求数据
        getDataFromNet();
    }

    private void getDataFromNet() {
//        prePosition = 0;
        LogUtil.e("url地址===" + url);
        RequestParams params = new RequestParams(url);
//        params.setConnectTimeout(4000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //缓存数据
                CacheUtils.putString(context, url, result);
                LogUtil.e(childrenData.getTitle() + "-页面数据请求成功==" + result);
                //解析和处理显示数据
                processData(result);

                //隐藏下拉刷新控件-重写显示数据，更新时间
//                listview.onRefreshFinish(true);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e(childrenData.getTitle() + "-页面数据请求失败==" + ex.getMessage());
                //隐藏下拉刷新控件 - 不更新时间，只是隐藏
//                listview.onRefreshFinish(false);
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {
                LogUtil.e(childrenData.getTitle() + "-页面数据请求onCancelled==" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                LogUtil.e(childrenData.getTitle() + "-onFinished==");
            }
        });
    }

    /**
     * 之前点高亮显示的位置
     */
    private int prePosition;

    private void processData(String json) {
        TabDetailPagerBean bean = parsedJson(json);
        LogUtil.e(childrenData.getTitle() + "解析成功==" + bean.getData().getNews().get(0).getTitle());

        topnews = bean.getData().getTopnews();
//        设置ViewPager的适配器
        viewPager.setAdapter(new TabDetailPagerTopNewsAdapter());

        //添加红点
        addPoint();

        //监听页面的改变，设置红点的变化和文本变化
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        //默认选中
        tv_title.setText(topnews.get(prePosition).getTitle());

        //准备ListView对应的集合数据
        news = bean.getData().getNews();
        //设置ListView的适配器
        adapter = new TabDetailPagerListAdapter();
        listView.setAdapter(adapter);
//    }else{
//        //加载更多
//        isLoadMore = false;
//        //添加到原来的集合中
//        news.addAll(bean.getData().getNews());
//        //刷新适配器
//        adapter.notifyDataSetChanged();
//
    }

    class TabDetailPagerListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return news.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_tabdetail_pager, null);
                viewHolder = new ViewHolder();
                viewHolder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
                viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);

                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            //根据位置得到数据
            TabDetailPagerBean.DataBean.NewsBean newsBean = news.get(position);
            String imageUrl = Constants.BASE_URL + newsBean.getListimage();
            //请求图片
            x.image().bind(viewHolder.iv_icon, imageUrl, imageOptions);
//            Glide.with(context)
//                    .load(imageUrl)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(viewHolder.iv_icon);

            //设置标题
            viewHolder.tv_title.setText(newsBean.getTitle());
            //设置时间
            viewHolder.tv_time.setText(newsBean.getPubdate());

            return convertView;
        }
    }

    static class ViewHolder {
        ImageView iv_icon;
        TextView tv_title;
        TextView tv_time;
    }

    private void addPoint() {

        ll_point_group.removeAllViews();//移除所有的红点

        for (int i = 0; i < topnews.size(); i++) {
            ImageView imageView = new ImageView(context);
            //设置背景选择器
            imageView.setBackgroundResource(R.drawable.point_selector);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(5), DensityUtil.dip2px(5));
            if (i == 0) {
                imageView.setEnabled(true);
            } else {
                imageView.setEnabled(false);
                params.leftMargin = DensityUtil.dip2px(8);
            }

            imageView.setLayoutParams(params);
            ll_point_group.addView(imageView);
        }
    }


    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //1.设置文本
            tv_title.setText(topnews.get(position).getTitle());
            //2.对应页面的点高亮-红色
            //把之前的变成灰色
            ll_point_group.getChildAt(prePosition).setEnabled(false);
            //把当前设置红色
            ll_point_group.getChildAt(position).setEnabled(true);

            prePosition = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


    class TabDetailPagerTopNewsAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return topnews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(context);
            //设置图片默认北京
            imageView.setBackgroundResource(R.drawable.home_scroll_default);
            //x轴和y轴拉伸
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //把图片添加到容器（ViewPager）中
            container.addView(imageView);

            TabDetailPagerBean.DataBean.TopnewsBean topnewsBean = topnews.get(position);
            String topimage = topnewsBean.getTopimage();
            //图片请求地址
            String imageUrl = Constants.BASE_URL + topimage;

            //联网请求图片
//            x.image().bind(imageView, imageUrl,imageOptions);
            x.image().bind(imageView, imageUrl);
            return imageView;
        }
    }

    private TabDetailPagerBean parsedJson(String json) {
        return new Gson().fromJson(json, TabDetailPagerBean.class);
    }
}
