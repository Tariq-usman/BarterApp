package com.example.barterapp.fragments.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.barterapp.R;
import com.example.barterapp.activities.AddTask;
import com.example.barterapp.adapters.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class Tasks extends Fragment {

private Button addNewTasksBtn;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.tasks_view_pager);
        viewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);


        addNewTasksBtn = view.findViewById(R.id.btn_add_new_task);
        addNewTasksBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddTask.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });


        return view;
    }
}
