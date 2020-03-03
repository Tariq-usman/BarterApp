package com.example.barterapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.barterapp.others.Preferences;
import com.example.barterapp.R;
import com.example.barterapp.fragments.Home;
import com.example.barterapp.fragments.Menu;
import com.example.barterapp.fragments.ChatInbox;
import com.example.barterapp.fragments.Notifications;
import com.example.barterapp.fragments.tasks.Tasks;
import com.example.barterapp.fragments.Profile;

public class MainPage extends AppCompatActivity implements View.OnClickListener {
    private FrameLayout layoutTitle;
    private LinearLayout layoutHome, layoutMessages, layoutTasks, layoutNotify, layoutProfile, layoutSettings;
    private ImageView imageViewHome, imageViewMessages, imageViewTask, imageViewNotify, imageViewProfile, imageViewMenu;
    private TextView textViewTitle, textViewHome, textViewMessages, textViewTask, textViewNotify, textViewProfile, textViewMenu;
    Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        preferences = new Preferences(getApplicationContext());
        String status = getIntent().getStringExtra("fragment_status");
        layoutTitle = findViewById(R.id.title_layout);
        textViewTitle = findViewById(R.id.tv_title);

        layoutHome = findViewById(R.id.home_layout);
        layoutHome.setOnClickListener(this);
        layoutMessages = findViewById(R.id.messages_layout);
        layoutMessages.setOnClickListener(this);
        layoutTasks = findViewById(R.id.task_layout);
        layoutTasks.setOnClickListener(this);
        layoutNotify = findViewById(R.id.notify_layout);
        layoutNotify.setOnClickListener(this);
        layoutProfile = findViewById(R.id.profile_layout);
        layoutProfile.setOnClickListener(this);
        layoutSettings = findViewById(R.id.menu_layout);
        layoutSettings.setOnClickListener(this);

        imageViewHome = findViewById(R.id.iv_home_icon);
        imageViewMessages = findViewById(R.id.iv_messages_icon);
        imageViewTask = findViewById(R.id.iv_task_icon);
        imageViewNotify = findViewById(R.id.iv_notify_icon);
        imageViewProfile = findViewById(R.id.iv_profile_icon);
        imageViewMenu = findViewById(R.id.iv_menu_icon);

        textViewHome = findViewById(R.id.tv_home);
        textViewMessages = findViewById(R.id.tv_messages);
        textViewTask = findViewById(R.id.tv_task);
        textViewNotify = findViewById(R.id.tv_notify);
        textViewProfile = findViewById(R.id.tv_profile);
        textViewMenu = findViewById(R.id.tv_menu);

        if (status != null && status.equalsIgnoreCase("notification")) {
            layoutTitle.setVisibility(View.VISIBLE);
            textViewTitle.setText("Notifications");
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new Notifications()).commit();
            imageViewHome.setImageResource(R.drawable.ic_home_purple);
            textViewHome.setVisibility(View.GONE);
            imageViewMessages.setImageResource(R.drawable.ic_chat_purple);
            textViewMessages.setVisibility(View.GONE);
            imageViewTask.setImageResource(R.drawable.ic_task_purple);
            textViewTask.setVisibility(View.GONE);
            imageViewNotify.setImageResource(R.drawable.ic_notification_white);
            textViewNotify.setVisibility(View.VISIBLE);
            imageViewProfile.setImageResource(R.drawable.ic_profile_purple);
            textViewProfile.setVisibility(View.GONE);
            imageViewMenu.setImageResource(R.drawable.ic_menu_purple);
            textViewMenu.setVisibility(View.GONE);
        } else if (status != null && status.equalsIgnoreCase("tasks")) {
            layoutTitle.setVisibility(View.VISIBLE);
            textViewTitle.setText("Tasks");
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new Tasks()).commit();
            imageViewHome.setImageResource(R.drawable.ic_home_purple);
            textViewHome.setVisibility(View.GONE);
            imageViewMessages.setImageResource(R.drawable.ic_chat_purple);
            textViewMessages.setVisibility(View.GONE);
            imageViewTask.setImageResource(R.drawable.ic_task);
            textViewTask.setVisibility(View.VISIBLE);
            imageViewNotify.setImageResource(R.drawable.ic_notification_purple);
            textViewNotify.setVisibility(View.GONE);
            imageViewProfile.setImageResource(R.drawable.ic_profile_purple);
            textViewProfile.setVisibility(View.GONE);
            imageViewMenu.setImageResource(R.drawable.ic_menu_purple);
            textViewMenu.setVisibility(View.GONE);
        } else if (status != null && status.equalsIgnoreCase("add_task")) {
            layoutTitle.setVisibility(View.VISIBLE);
            textViewTitle.setText("Home");
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new Home(), "Home").commit();
            imageViewHome.setImageResource(R.drawable.ic_home_white);
            textViewHome.setVisibility(View.VISIBLE);
            imageViewMessages.setImageResource(R.drawable.ic_chat_purple);
            textViewMessages.setVisibility(View.GONE);
            imageViewTask.setImageResource(R.drawable.ic_task_purple);
            textViewTask.setVisibility(View.GONE);
            imageViewNotify.setImageResource(R.drawable.ic_notification_purple);
            textViewNotify.setVisibility(View.GONE);
            imageViewProfile.setImageResource(R.drawable.ic_profile_purple);
            textViewProfile.setVisibility(View.GONE);
            imageViewMenu.setImageResource(R.drawable.ic_menu_purple);
            textViewMenu.setVisibility(View.GONE);
        } else if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new Home(), "Home").commit();
            imageViewHome.setImageResource(R.drawable.ic_home_white);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_layout:
                layoutTitle.setVisibility(View.VISIBLE);
                textViewTitle.setText("Home");
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new Home(), "Home").commit();
                imageViewHome.setImageResource(R.drawable.ic_home_white);
                textViewHome.setVisibility(View.VISIBLE);
                imageViewMessages.setImageResource(R.drawable.ic_chat_purple);
                textViewMessages.setVisibility(View.GONE);
                imageViewTask.setImageResource(R.drawable.ic_task_purple);
                textViewTask.setVisibility(View.GONE);
                imageViewNotify.setImageResource(R.drawable.ic_notification_purple);
                textViewNotify.setVisibility(View.GONE);
                imageViewProfile.setImageResource(R.drawable.ic_profile_purple);
                textViewProfile.setVisibility(View.GONE);
                imageViewMenu.setImageResource(R.drawable.ic_menu_purple);
                textViewMenu.setVisibility(View.GONE);
                break;
            case R.id.messages_layout:
                layoutTitle.setVisibility(View.VISIBLE);
                textViewTitle.setText("Inbox");
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new ChatInbox()).commit();
                imageViewHome.setImageResource(R.drawable.ic_home_purple);
                textViewHome.setVisibility(View.GONE);
                imageViewMessages.setImageResource(R.drawable.ic_chat_white);
                textViewMessages.setVisibility(View.VISIBLE);
                imageViewTask.setImageResource(R.drawable.ic_task_purple);
                textViewTask.setVisibility(View.GONE);
                imageViewNotify.setImageResource(R.drawable.ic_notification_purple);
                textViewNotify.setVisibility(View.GONE);
                imageViewProfile.setImageResource(R.drawable.ic_profile_purple);
                textViewProfile.setVisibility(View.GONE);
                imageViewMenu.setImageResource(R.drawable.ic_menu_purple);
                textViewMenu.setVisibility(View.GONE);
                break;
            case R.id.task_layout:
                layoutTitle.setVisibility(View.VISIBLE);
                textViewTitle.setText("Tasks");
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new Tasks()).commit();
                imageViewHome.setImageResource(R.drawable.ic_home_purple);
                textViewHome.setVisibility(View.GONE);
                imageViewMessages.setImageResource(R.drawable.ic_chat_purple);
                textViewMessages.setVisibility(View.GONE);
                imageViewTask.setImageResource(R.drawable.ic_task);
                textViewTask.setVisibility(View.VISIBLE);
                imageViewNotify.setImageResource(R.drawable.ic_notification_purple);
                textViewNotify.setVisibility(View.GONE);
                imageViewProfile.setImageResource(R.drawable.ic_profile_purple);
                textViewProfile.setVisibility(View.GONE);
                imageViewMenu.setImageResource(R.drawable.ic_menu_purple);
                textViewMenu.setVisibility(View.GONE);
                break;
            case R.id.notify_layout:
                layoutTitle.setVisibility(View.VISIBLE);
                textViewTitle.setText("Notifications");
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new Notifications()).commit();
                imageViewHome.setImageResource(R.drawable.ic_home_purple);
                textViewHome.setVisibility(View.GONE);
                imageViewMessages.setImageResource(R.drawable.ic_chat_purple);
                textViewMessages.setVisibility(View.GONE);
                imageViewTask.setImageResource(R.drawable.ic_task_purple);
                textViewTask.setVisibility(View.GONE);
                imageViewNotify.setImageResource(R.drawable.ic_notification_white);
                textViewNotify.setVisibility(View.VISIBLE);
                imageViewProfile.setImageResource(R.drawable.ic_profile_purple);
                textViewProfile.setVisibility(View.GONE);
                imageViewMenu.setImageResource(R.drawable.ic_menu_purple);
                textViewMenu.setVisibility(View.GONE);
                break;
            case R.id.profile_layout:
                layoutTitle.setVisibility(View.GONE);
                textViewTitle.setText("Profile");
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new Profile()).commit();
                imageViewHome.setImageResource(R.drawable.ic_home_purple);
                textViewHome.setVisibility(View.GONE);
                imageViewMessages.setImageResource(R.drawable.ic_chat_purple);
                textViewMessages.setVisibility(View.GONE);
                imageViewTask.setImageResource(R.drawable.ic_task_purple);
                textViewTask.setVisibility(View.GONE);
                imageViewNotify.setImageResource(R.drawable.ic_notification_purple);
                textViewNotify.setVisibility(View.GONE);
                imageViewProfile.setImageResource(R.drawable.ic_profile_white);
                textViewProfile.setVisibility(View.VISIBLE);
                imageViewMenu.setImageResource(R.drawable.ic_menu_purple);
                textViewMenu.setVisibility(View.GONE);
                break;
            case R.id.menu_layout:
                layoutTitle.setVisibility(View.VISIBLE);
                textViewTitle.setText("Menu");
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new Menu()).commit();
                imageViewHome.setImageResource(R.drawable.ic_home_purple);
                textViewHome.setVisibility(View.GONE);
                imageViewMessages.setImageResource(R.drawable.ic_chat_purple);
                textViewMessages.setVisibility(View.GONE);
                imageViewTask.setImageResource(R.drawable.ic_task_purple);
                textViewTask.setVisibility(View.GONE);
                imageViewNotify.setImageResource(R.drawable.ic_notification_purple);
                textViewNotify.setVisibility(View.GONE);
                imageViewProfile.setImageResource(R.drawable.ic_profile_purple);
                textViewProfile.setVisibility(View.GONE);
                imageViewMenu.setImageResource(R.drawable.ic_menu);
                textViewMenu.setVisibility(View.VISIBLE);
                break;

        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("Home");
        if (preferences.getSearchVal() == true) {
            preferences.setSearchVal(false);
            layoutTitle.setVisibility(View.VISIBLE);
            setFragment(new Home(), "Home");
            imageViewHome.setImageResource(R.drawable.ic_home_white);
            textViewHome.setVisibility(View.VISIBLE);
            imageViewMessages.setImageResource(R.drawable.ic_chat_purple);
            textViewMessages.setVisibility(View.GONE);
            imageViewTask.setImageResource(R.drawable.ic_task_purple);
            textViewTask.setVisibility(View.GONE);
            imageViewProfile.setImageResource(R.drawable.ic_profile_purple);
            textViewProfile.setVisibility(View.GONE);
            imageViewMenu.setImageResource(R.drawable.ic_menu_purple);
            textViewMenu.setVisibility(View.GONE);
        } else if (fragment != null && fragment.isVisible()) {
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
        } else {
            layoutTitle.setVisibility(View.VISIBLE);
            setFragment(new Home(), "Home");
            imageViewHome.setImageResource(R.drawable.ic_home_white);
            textViewHome.setVisibility(View.VISIBLE);
            imageViewMessages.setImageResource(R.drawable.ic_chat_purple);
            textViewMessages.setVisibility(View.GONE);
            imageViewTask.setImageResource(R.drawable.ic_task_purple);
            textViewTask.setVisibility(View.GONE);
            imageViewProfile.setImageResource(R.drawable.ic_profile_purple);
            textViewProfile.setVisibility(View.GONE);
            imageViewMenu.setImageResource(R.drawable.ic_menu_purple);
            textViewMenu.setVisibility(View.GONE);
        }
    }

    public void setFragment(Fragment fragment, String tag) {
        FragmentTransaction trn = getSupportFragmentManager().beginTransaction();
        trn.addToBackStack(null);
        trn.replace(R.id.main_container, fragment, tag);
        trn.commit();
    }
}
