package com.example.jacek.trainingapp.physicalActivity.workouts;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class WorkoutFragmentPagerAdapter extends FragmentPagerAdapter
{
    private List<Fragment> fragmentList;
    private List<String> fragmentTitles;

    public WorkoutFragmentPagerAdapter(FragmentManager fm)
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
