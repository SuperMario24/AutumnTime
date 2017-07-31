package com.example.saber.autumntime.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.saber.autumntime.Activity.MainActivity;
import com.example.saber.autumntime.Adapter.RecyclerAdapter;
import com.example.saber.autumntime.Presenter.ILoadBitmapPresenter;
import com.example.saber.autumntime.Presenter.ILoadZhihuPresenter;
import com.example.saber.autumntime.Presenter.impl.LoadZhihuNewsPresenterImpl;
import com.example.saber.autumntime.R;
import com.example.saber.autumntime.Views.ILoadView;
import com.example.saber.autumntime.bean.HotNew;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by saber on 2017/3/20.
 */

public class FragmentZhihu extends Fragment implements ILoadView{

    private static final String TAG = "FragmentZhihu";

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private List<HotNew> zhihuNews;
    private SwipeRefreshLayout refreshLayout;
    //获取数据的presenter
    private ILoadZhihuPresenter loadZhihuPresenter;
    //获取数据中图片bitmap的presenter
    private ILoadBitmapPresenter loadBitmapPresenter;

    /**
     * 获取今天的年月日
     */
    private int year = Calendar.getInstance().get(Calendar.YEAR);
    private int month = Calendar.getInstance().get(Calendar.MONTH);
    private int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    //每天的毫秒值
    private long timeInMillis;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zhihu,container,false);

        initViews(view);

        //此处应该为获取毫秒值再减去一天的毫秒值，在此转化为yyyyMMdd格式
        Calendar c = Calendar.getInstance();
        c.set(year,month,day);
        timeInMillis = c.getTimeInMillis();

        //接口以实现类的形式创建
        loadZhihuPresenter = new LoadZhihuNewsPresenterImpl(this);


        //加载新闻
        loadZhihuPresenter.loadZhihuNewsPresenter();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadZhihuPresenter.loadZhihuNewsPresenter();
            }
        });

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            //是否是向下滑动
            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();

                //当不滚动时
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    //获取最后一个完全显示的item position
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItem = manager.getItemCount();

                    //是否滑到底并且是向下滑动
                    if(lastVisibleItem == totalItem-1 && isSlidingToLast){

                        //获取过往的新闻
                        loadZhihuPresenter.loadZhihuNewsBefore(timeInMillis);

                    }

                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                isSlidingToLast = dy>0;
                // 隐藏或者显示fab
                if(dy > 0) {
                    ((MainActivity)getActivity()).hideFloatingActionButton();
                } else {
                    ((MainActivity)getActivity()).showFloatingActionButton();
                }


            }
        });



        return  view;
    }

    /**
     * 初始化
     * @param view
     */
    private void initViews(View view) {
        zhihuNews = new ArrayList<>();
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        //设置下拉刷新按钮的颜色
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    /**
     * 获取到知乎日报数据
     * @param zhihuNews
     */
    @Override
    public void showZhihuNews(List<HotNew> zhihuNews) {
        Log.d(TAG,"Thread.currentThread().getId():"+Thread.currentThread().getId());

        if(recyclerAdapter == null){
            this.zhihuNews = zhihuNews;
            recyclerAdapter = new RecyclerAdapter(getContext(),this.zhihuNews);
            recyclerView.setAdapter(recyclerAdapter);
        }else {
            this.zhihuNews.clear();
            this.zhihuNews.addAll(zhihuNews);
            recyclerAdapter.notifyDataSetChanged();
        }
        onLoadedSuccess();

    }
    /**
     * 下拉时获取到知乎日报前一天的数据
     * @param hotNews
     */
    @Override
    public void showBeforeZhihuNews(List<HotNew> hotNews) {
        if(recyclerAdapter == null){
            this.zhihuNews = hotNews;
            recyclerAdapter = new RecyclerAdapter(getContext(),this.zhihuNews);
            recyclerView.setAdapter(recyclerAdapter);
        }else {
            this.zhihuNews.addAll(hotNews);
            recyclerAdapter.notifyDataSetChanged();
            //减一天
            timeInMillis = timeInMillis - 24*60*60*1000;
        }
        onLoadedSuccess();
    }

    //显示为加载状态
    @Override
    public void showLoading() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
            }
        });
    }
    //加载成功
    @Override
    public void onLoadedSuccess() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadedError() {
        Snackbar.make((FloatingActionButton)(getActivity().findViewById(R.id.fab)),"加载失败",Snackbar.LENGTH_INDEFINITE)
                .setAction("重试", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadZhihuPresenter.loadZhihuNewsPresenter();
                    }
                }).show();
    }


}
