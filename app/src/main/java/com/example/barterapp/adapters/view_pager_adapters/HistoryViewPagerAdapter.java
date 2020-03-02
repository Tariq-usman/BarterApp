package com.example.barterapp.adapters.view_pager_adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.barterapp.fragments.menu.history.UserJobFragment;
import com.example.barterapp.fragments.menu.history.UserOffersFragment;
import com.example.barterapp.fragments.tasks.BuyServices;

public class HistoryViewPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] childFragments;

    public HistoryViewPagerAdapter(FragmentManager fm) {
        super(fm);
        childFragments = new Fragment[]{
                new UserJobFragment(), //0
                new UserOffersFragment() //1
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
            title = "My Jobs";
        } else if (position == 1) {
            title = "My Offers";
        }
        return title;
    }
}