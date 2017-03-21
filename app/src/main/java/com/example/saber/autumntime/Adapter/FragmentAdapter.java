package com.example.saber.autumntime.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by saber on 2017/3/20.
 */

public class FragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private String[] titles;

    public FragmentAdapter(FragmentManager fm,List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
        titles = new String[]{"知乎日报","果壳精选","豆瓣一刻"};
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);

    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
