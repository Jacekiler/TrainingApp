package com.example.jacek.trainingapp.timers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacek on 22.03.2018.
 */

public class TimersFragmentPagerAdapter extends FragmentPagerAdapter
{
    private List<Fragment> fragmentList;
    private List<String> fragmentTitles;

    public TimersFragmentPagerAdapter(FragmentManager fm)
    {
        super(fm);
        fragmentList = new ArrayList<>();
        fragmentTitles = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position)
    {
      return fragmentList.get(position);
    }

    @Override
    public int getCount()
    {
        return fragmentTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return fragmentTitles.get(position);
    }

    public void addFragment(Fragment fragment, String title)
    {
        fragmentList.add(fragment);
        fragmentTitles.add(title);
    }
}
