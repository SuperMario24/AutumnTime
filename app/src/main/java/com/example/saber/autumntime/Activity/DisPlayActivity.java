package com.example.saber.autumntime.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.saber.autumntime.Presenter.ILoadZhihuPresenter;
import com.example.saber.autumntime.Presenter.impl.LoadZhihuNewsPresenterImpl;
import com.example.saber.autumntime.R;
import com.example.saber.autumntime.Utils.HtmlUtils;
import com.example.saber.autumntime.Views.ILoadZhihuContentView;
import com.example.saber.autumntime.bean.HotNewContent;

/**
 * Created by saber on 2017/3/21.
 */

public class DisPlayActivity extends AppCompatActivity implements ILoadZhihuContentView{

    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView ivTitle;
    private WebView webView;
    private NestedScrollView nestedScrollView;
    private ILoadZhihuPresenter loadZhihuNewsContentPresenter;

    private int id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        initViews();

        loadZhihuNewsContentPresenter = new LoadZhihuNewsPresenterImpl(this);
        loadZhihuNewsContentPresenter.loadZhihuContent(id);

    }

    /**
     * 初始化
     */
    private void initViews() {
        nestedScrollView = (NestedScrollView) findViewById(R.id.scroll_view);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        ivTitle = (ImageView) findViewById(R.id.iv_title);
        webView = (WebView) findViewById(R.id.web_view);


        id = getIntent().getIntExtra("id",-1);
        Log.d("info","id:"+id);

    }

    /**
     * 获取到单条数据后显示
     * @param hotNewContent
     */
    @Override
    public void showZhihuNewsContent(HotNewContent hotNewContent) {
        Log.d("info", "showZhihuNewsContent: "+hotNewContent.toString());
        Glide.with(this).load(hotNewContent.getImage()).into(ivTitle);
        setCollapsingToolbarLayoutTitle(hotNewContent.getTitle());
        webView.setScrollbarFadingEnabled(true);
        WebSettings settings = webView.getSettings();
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);//设置缓存
        settings.setSupportZoom(true);// 支持放大网页功能
        settings.setBuiltInZoomControls(false);// 支持缩小网页功能
        settings.setJavaScriptEnabled(true);// 支持js
        //开启DOM storage API功能
        settings.setDomStorageEnabled(true);
        //开启application Cache功能
        settings.setAppCacheEnabled(false);

        //不打开系统浏览器
        webView.setWebViewClient(new WebViewClient());

        //生成拼接好样式的js html
        String html = HtmlUtils.createZhihuHtml(hotNewContent.getBody());

        webView.loadDataWithBaseURL(null, html, "text/html", "utf-8",null);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_display,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
            break;
            case R.id.share:
                Toast.makeText(this, "分享功能暂未开放", Toast.LENGTH_SHORT).show();
                break;

        }
        return true;
    }

    /**
     * 自定义CollapsingToolbarLayout样式
     * @param title
     */
    private void setCollapsingToolbarLayoutTitle(String title) {
        collapsingToolbarLayout.setTitle(title);
        //自定义外观
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
    }
}
