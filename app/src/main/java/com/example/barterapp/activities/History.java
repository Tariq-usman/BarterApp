package com.example.barterapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.barterapp.R;
import com.example.barterapp.adapters.view_pager_adapters.HistoryViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class History extends AppCompatActivity {
    int[] images = {R.drawable.notification_image, R.drawable.arslan, R.drawable.farmer_four, R.drawable.farmer_three, R.drawable.customer};
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        tabLayout = findViewById(R.id.tab_layout_history);
        viewPager = findViewById(R.id.history_view_pager);
        viewPager.setAdapter(new HistoryViewPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }
}
