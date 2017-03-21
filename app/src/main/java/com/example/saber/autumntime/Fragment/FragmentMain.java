package com.example.saber.autumntime.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.saber.autumntime.Adapter.FragmentAdapter;
import com.example.saber.autumntime.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saber on 2017/3/20.
 */

public class FragmentMain extends Fragment {


    private ViewPager viewPager;
    private FragmentAdapter fragmentAdapter;
    private FragmentZhihu fragmentZhihu;
    private FragmentGuoke fragmentGuoke;
    private FragmentDouban fragmentDouban;
    private List<Fragment> fragments = new ArrayList<>();

    private TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);

        initViews(view);

        setHasOptionsMenu(true);

        // 当tab layout位置为果壳精选时，隐藏fab
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FloatingActionButton fab =(FloatingActionButton) getActivity().findViewById(R.id.fab);
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

        return view;
    }

    /**
     * 初始化
     * @param view
     */
    private void initViews(View view) {

        viewPager = (ViewPager)view.findViewById(R.id.vp);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);


        //初始化fragment
        fragmentZhihu = new FragmentZhihu();
        fragmentGuoke = new FragmentGuoke();
        fragmentDouban = new FragmentDouban();
        fragments.add(fragmentZhihu);
        fragments.add(fragmentGuoke);
        fragments.add(fragmentDouban);

        fragmentAdapter = new FragmentAdapter(getFragmentManager(),fragments);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOffscreenPageLimit(3);
        //TabLayout关联ViewPager
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.options,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_helpyouself){
            //// TODO: 2017/3/20  
        }
        return  true;
    }
}
