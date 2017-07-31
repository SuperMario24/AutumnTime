package com.example.saber.autumntime.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
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
import com.example.saber.autumntime.Adapter.DoubanAdapter;
import com.example.saber.autumntime.Presenter.ILoadDoubanPresenter;
import com.example.saber.autumntime.Presenter.impl.LoadDoubanPresenterImpl;
import com.example.saber.autumntime.R;
import com.example.saber.autumntime.Views.ILoadDoubanSummaryView;
import com.example.saber.autumntime.bean.DoubanSummaryResp;
import com.example.saber.autumntime.bean.DoubanTitle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by saber on 2017/3/20.
 */

public class FragmentDouban extends Fragment implements ILoadDoubanSummaryView{

    private static final String TAG = "FragmentDouban";

    private RecyclerView recyclerView;
    private DoubanAdapter recyclerAdapter;
    private SwipeRefreshLayout refreshLayout;
    private List<DoubanTitle> doubanTitles;
    private ILoadDoubanPresenter loadDoubanPresenter;

    private int year = Calendar.getInstance().get(Calendar.YEAR);
    private int month = Calendar.getInstance().get(Calendar.MONTH);
    private int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    //每天的毫秒值
    private long timeInMillis = new Date().getTime() - 24*60*60*1000;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private boolean scrollend = false;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            DoubanSummaryResp doubanSummaryResp = (DoubanSummaryResp) msg.obj;
            List<DoubanTitle> posts = doubanSummaryResp.getPosts();
            switch (msg.what){
                case 0:


                    Log.d(TAG,"posts.size():"+posts.size());
                    if(recyclerAdapter == null){
                        doubanTitles = posts;
                        //TODO 可以优化为抽象和实现
                        recyclerAdapter = new DoubanAdapter(doubanTitles,getContext());
                        recyclerView.setAdapter(recyclerAdapter);
                    }else {
                        if(!scrollend){
                            doubanTitles.clear();
                        }
                        doubanTitles.addAll(posts);
                        recyclerAdapter.notifyDataSetChanged();
                    }
                    onLoadedSuccess();
                    if(scrollend){
                        timeInMillis -= 24*60*60*1000;
                        scrollend = false;
                    }

                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zhihu,container,false);

        initViews(view);

        loadDoubanPresenter = new LoadDoubanPresenterImpl(this);
        showLoading();
        loadDoubanPresenter.loadDoubanSummary(sdf.format(new Date()));

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showLoading();
                loadDoubanPresenter.loadDoubanSummary(sdf.format(new Date()));
            }
        });

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            //是否是向下滑动
            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                //当静止时，获取最后一个可见的item
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    int lastItemPosition = manager.findLastVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();
                    //是否滑到底并且是向下滑动
                    if(isSlidingToLast && lastItemPosition == totalItemCount-1){
                        //获取过往的新闻
                        scrollend = true;
                        loadDoubanPresenter.loadDoubanSummary(sdf.format(new Date(timeInMillis)));
                        Log.d(TAG,"time:"+sdf.format(new Date(timeInMillis)));
                    }
                }


                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isSlidingToLast = dy>0;
                if(dy > 0){
                    ((MainActivity)getActivity()).hideFloatingActionButton();
                }else {
                    ((MainActivity)getActivity()).showFloatingActionButton();
                }

            }
        });


        return  view;
    }

    private void initViews(View view) {
        doubanTitles = new ArrayList<>();
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        //设置下拉刷新按钮的颜色
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

    }


    @Override
    public void onDoubanSummaryLoaded(DoubanSummaryResp doubanSummaryResp) {
        if(doubanSummaryResp != null){
            Message msg = Message.obtain(handler,0,doubanSummaryResp);
            handler.sendMessage(msg);
        }

    }

    @Override
    public void onDoubanSummaryLoadedError() {
        onLoadedError();
    }

    @Override
    public void showLoading() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void onLoadedSuccess() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onLoadedError() {
        Snackbar.make(getActivity().findViewById(R.id.fab),"加载失败",Snackbar.LENGTH_INDEFINITE)
                .setAction("重试", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadDoubanPresenter.loadDoubanSummary(sdf.format(new Date()));
                    }
                }).show();
    }
}
