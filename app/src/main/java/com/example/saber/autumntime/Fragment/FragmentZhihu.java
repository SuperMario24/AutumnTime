package com.example.saber.autumntime.Fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.saber.autumntime.Adapter.RecyclerAdapter;
import com.example.saber.autumntime.Presenter.ILoadBitmapPresenter;
import com.example.saber.autumntime.Presenter.ILoadZhihuPresenter;
import com.example.saber.autumntime.Presenter.impl.LoadZhihuNewsPresenterImpl;
import com.example.saber.autumntime.R;
import com.example.saber.autumntime.Views.ILoadView;
import com.example.saber.autumntime.bean.HotNew;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saber on 2017/3/20.
 */

public class FragmentZhihu extends Fragment implements ILoadView{

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private List<HotNew> zhihuNews;
    //获取数据的presenter
    private ILoadZhihuPresenter loadZhihuPresenter;
    //获取数据中图片bitmap的presenter
    private ILoadBitmapPresenter loadBitmapPresenter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zhihu,container,false);

        initViews(view);

        //接口以实现类的形式创建
        loadZhihuPresenter = new LoadZhihuNewsPresenterImpl(this);
        loadZhihuPresenter.loadZhihuNewsPresenter();



        return  view;
    }

    /**
     * 初始化
     * @param view
     */
    private void initViews(View view) {
        zhihuNews = new ArrayList<>();
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
        this.zhihuNews = zhihuNews;
        Log.d("info", "zhihuNews.size()"+zhihuNews.size());
        recyclerAdapter = new RecyclerAdapter(getActivity(),zhihuNews);
        recyclerView.setAdapter(recyclerAdapter);
    }

    /**
     * 显示图片
     * @param bitmap
     */
    @Override
    public void showZhihuImage(Bitmap bitmap) {

    }
}
