package com.example.barterapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barterapp.R;
import com.example.barterapp.adapters.NotificationsAdapter;

public class Notifications extends Fragment {
    RecyclerView recyclerView;
    int[] images ={R.drawable.notification_image,R.drawable.arslan,R.drawable.farmer_four,R.drawable.farmer_three,R.drawable.customer};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notificatins,container,false);
        recyclerView = view.findViewById(R.id.recycler_view_notifications);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new NotificationsAdapter(getContext(),images));
        return view;
    }
}
