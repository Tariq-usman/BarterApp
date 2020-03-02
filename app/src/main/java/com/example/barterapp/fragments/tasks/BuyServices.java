package com.example.barterapp.fragments.tasks;

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
import com.example.barterapp.adapters.tasks.SellServicesAdapter;

public class BuyServices extends Fragment {
    RecyclerView recyclerView;
    int[] images ={R.drawable.notification_image,R.drawable.arslan,R.drawable.farmer_four,R.drawable.farmer_three,R.drawable.customer};

//    int[] images ={R.drawable.maintainance,R.drawable.design,R.drawable.electronic,R.drawable.equipments,R.drawable.furniture};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_return_services,container,false);
        recyclerView = view.findViewById(R.id.recycler_view_tasks);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new SellServicesAdapter(getContext(),images));

        return view;
    }
}
