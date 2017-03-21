package com.example.saber.autumntime.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.saber.autumntime.Adapter.FragmentAdapter;
import com.example.saber.autumntime.Fragment.FragmentDouban;
import com.example.saber.autumntime.Fragment.FragmentGuoke;
import com.example.saber.autumntime.Fragment.FragmentZhihu;
import com.example.saber.autumntime.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private FragmentAdapter fragmentAdapter;
    private FragmentZhihu fragmentZhihu;
    private FragmentGuoke fragmentGuoke;
    private FragmentDouban fragmentDouban;
    private List<Fragment> fragments = new ArrayList<>();

    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        // 当tab layout位置为果壳精选时，隐藏fab
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FloatingActionButton fab =(FloatingActionButton)findViewById(R.id.fab);
                if(tab.getPosition() == 1){
                    fab.hide();
                }else {
                    fab.show();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    /**
     * 初始化
     */
    private void initViews() {
        drawerLayout =(DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        viewPager = (ViewPager)findViewById(R.id.vp);
        tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //初始化fragment
        fragmentZhihu = new FragmentZhihu();
        fragmentGuoke = new FragmentGuoke();
        fragmentDouban = new FragmentDouban();
        fragments.add(fragmentZhihu);
        fragments.add(fragmentGuoke);
        fragments.add(fragmentDouban);

        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOffscreenPageLimit(3);

        //TabLayout关联ViewPager
        tabLayout.setupWithViewPager(viewPager);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //默认选择第一个
        navigationView.setCheckedItem(R.id.nav_home);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //关闭滑动菜单
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.menu_helpyouself:
                Toast.makeText(this, "随便看看，现在还没有数据", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
