package com.example.saber.autumntime.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.saber.autumntime.Adapter.GuokrNewsAdapter;
import com.example.saber.autumntime.Presenter.ILoadGuokrPresenter;
import com.example.saber.autumntime.Presenter.impl.LoadGuokrPresenterImpl;
import com.example.saber.autumntime.R;
import com.example.saber.autumntime.Views.ILoadGuokrSummaryView;
import com.example.saber.autumntime.bean.GuokrSummaryResp;
import com.example.saber.autumntime.bean.ResultResp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saber on 2017/3/20.
 */

public class FragmentGuoke extends Fragment implements ILoadGuokrSummaryView{

    private static final String TAG = "FragmentGuoke";

    private RecyclerView recyclerView;
    private GuokrNewsAdapter guokrNewsAdapter;
    private SwipeRefreshLayout refreshLayout;

    private List<ResultResp> mResultResps;
    private ILoadGuokrPresenter presenter;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:

                    GuokrSummaryResp guokrSummaryResp = (GuokrSummaryResp) msg.obj;
                    mResultResps = guokrSummaryResp.getResult();
                    Log.d(TAG,"mResultResps.size():"+mResultResps.size());
                    Log.d(TAG,"Thread.currentThread().getId():"+Thread.currentThread().getId());
                    if(guokrNewsAdapter == null){
                        guokrNewsAdapter = new GuokrNewsAdapter(getActivity(),mResultResps);
                        recyclerView.setAdapter(guokrNewsAdapter);
                    }else {
                        guokrNewsAdapter.notifyDataSetChanged();
                    }
                    refreshLayout.setRefreshing(false);
                    break;
            }
        }
    };



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zhihu,container,false);

        initViews(view);

        //获取Guokr精选Summary
        presenter = new LoadGuokrPresenterImpl(this);
        presenter.loadGuokrSummary();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadGuokrSummary();
            }
        });

        return  view;
    }

    private void initViews(View view) {
        mResultResps = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        //设置下拉刷新按钮的颜色
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    @Override
    public void onGuokrSummaryLoaded(GuokrSummaryResp guokrSummaryResp) {
        Message msg = Message.obtain(handler,0,guokrSummaryResp);
        handler.sendMessage(msg);
    }
}
