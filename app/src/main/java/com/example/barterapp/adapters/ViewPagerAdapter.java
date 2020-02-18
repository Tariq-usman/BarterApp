package com.example.barterapp.adapters;
import com.example.barterapp.fragments.tasks.GiveServices;
import com.example.barterapp.fragments.tasks.ReturnServices;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] childFragments;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        childFragments = new Fragment[]{
                new GiveServices(), //0
                new ReturnServices() //1
        };
    }

    @Override
    public Fragment getItem(int position) {
        return childFragments[position];
    }

    @Override
    public int getCount() {
        return childFragments.length; //3 items
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = "Sell Services";
        } else if (position == 1) {
            title = "Buy Services";
        }
        return title;
    }
}