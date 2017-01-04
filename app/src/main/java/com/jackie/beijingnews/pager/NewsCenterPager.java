package com.jackie.beijingnews.pager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jackie.beijingnews.activity.MainActivity;
import com.jackie.beijingnews.base.BasePager;
import com.jackie.beijingnews.domain.NewsCenterPagerBean;
import com.jackie.beijingnews.fragment.LeftmenuFragment;
import com.jackie.beijingnews.utils.Constants;
import com.jackie.beijingnews.utils.LogUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * 作用：新闻中心
 */
public class NewsCenterPager extends BasePager {

    /**
     * 左侧菜单对应的数据集合
     */
    private List<NewsCenterPagerBean.DataEntity> data;

    public NewsCenterPager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("新闻中心数据被初始化了..");
        //1.设置标题
        tv_title.setText("新闻中心");
        //2.联网请求，得到数据，创建视图
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);
        //3.把子视图添加到BasePager的FrameLayout中
        fl_content.addView(textView);
        //4.绑定数据
        textView.setText("新闻中心内容");

        //联网请求数据
        getDataFromNet();
    }

    /**
     * 使用xUtils3联网请求数据
     */
    private void getDataFromNet() {
        RequestParams params = new RequestParams(Constants.NEWSCENTER_PAGER_URL);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("使用xUtils3联网请求成功==" + result);
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("使用xUtils3联网请求失败==" + ex.getMessage());
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {
                LogUtil.e("使用xUtils3-onCancelled==" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                LogUtil.e("使用xUtils3-onFinished");
            }
        });
    }

    /**
     * 解析json数据和显示数据
     *
     * @param json
     */
    private void processData(String json) {

        NewsCenterPagerBean bean = parsedJson(json);
        String title = bean.getData().get(0).getChildren().get(1).getTitle();
        LogUtil.e("使用Gson解析json数据成功-title==" + title);

        //给左侧菜单传递数据
        data = bean.getData();

        MainActivity mainActivity = (MainActivity) context;
        //得到左侧菜单
        LeftmenuFragment leftmenuFragment = mainActivity.getLeftmenuFragment();

        //把数据传递给左侧菜单
        leftmenuFragment.setData(data);
    }

    /**
     * 解析json数据：1,使用系统的API解析json；2,使用第三方框架解析json数据，例如Gson,fastjson
     *
     * @param json
     * @return
     */
    private NewsCenterPagerBean parsedJson(String json) {
        return new Gson().fromJson(json, NewsCenterPagerBean.class);
    }
}
