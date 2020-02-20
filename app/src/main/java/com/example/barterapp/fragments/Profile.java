package com.example.barterapp.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barterapp.RecyclerClickInterface;
import com.example.barterapp.activities.Portfolio;
import com.example.barterapp.R;
import com.example.barterapp.adapters.ProfilePortfolioAdapter;
import com.example.barterapp.adapters.ProfileTradesAdapter;
import com.example.barterapp.others.Preferences;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class Profile extends Fragment implements View.OnClickListener, RecyclerClickInterface {
    private int RESULT_LOAD_IMAGE = 1;
    private Dialog myDialog;
    private TextView tradesView;
    private LinearLayout layoutEdit;
    private TextView textViewPortfolio, saveBtn;
    private RecyclerView recyclerView, recyclerViewTrades;
    private FlexboxLayoutManager layoutManager;
    private EditText experienceView;
    private String[] trades = {"Design", "Mobile App Design", "Web Design", "abc", "abc", "Design"};
    int[] images = {R.drawable.notification_image, R.drawable.arslan, R.drawable.farmer_four, R.drawable.farmer_three, R.drawable.customer};

    private Preferences preferences;
    private ProfileTradesAdapter profileTradesAdapter;
    private ProfilePortfolioAdapter profilePortfolioAdapter;
    private ImageButton ibAddNewTrade, ibAddNewPortfolio;
    private Button btnYes, btnNo;

    private List<String> trade_list;
    public static List<Uri> portfolio_pics;
    private CircleImageView profileImage;
    private boolean select_image_status = true;
    private int currentPosition = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        trade_list = new ArrayList<>();
        trade_list.add("Android developer");
        portfolio_pics = new ArrayList<>();
        portfolio_pics.add(Uri.parse(String.valueOf(R.drawable.notification_image)));

        preferences = new Preferences(getContext());
        preferences.setEditStatus(0);
        profileImage = view.findViewById(R.id.profile_image);
        profileImage.setOnClickListener(this);
        ibAddNewTrade = view.findViewById(R.id.btn_add_trade_profile);
        ibAddNewTrade.setOnClickListener(this);
        ibAddNewPortfolio = view.findViewById(R.id.btn_add_portfolio_profile);
        ibAddNewPortfolio.setOnClickListener(this);

        experienceView = view.findViewById(R.id.experience_view);
        layoutEdit = view.findViewById(R.id.layout_edit);
        layoutEdit.setOnClickListener(this);
        saveBtn = view.findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(this);
        textViewPortfolio = view.findViewById(R.id.tv_portfolio_profile);
        textViewPortfolio.setOnClickListener(this);

        recyclerViewTrades = view.findViewById(R.id.recycler_view_profile_trades);
        layoutManager = new FlexboxLayoutManager(getContext());
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setAlignItems(AlignItems.STRETCH);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        recyclerViewTrades.setLayoutManager(layoutManager);
        profileTradesAdapter = new ProfileTradesAdapter(getContext(), trade_list);
        recyclerViewTrades.setAdapter(profileTradesAdapter);

        recyclerView = view.findViewById(R.id.recycler_view_profile_portfolio);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setHasFixedSize(true);
        profilePortfolioAdapter = new ProfilePortfolioAdapter(getContext(), portfolio_pics, this);
        recyclerView.setAdapter(profilePortfolioAdapter);


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_edit:
                layoutEdit.setVisibility(View.GONE);
                saveBtn.setVisibility(View.VISIBLE);
                experienceView.setFocusable(true);
                experienceView.setClickable(true);
                experienceView.setCursorVisible(true);
                experienceView.setFocusableInTouchMode(true);
                ibAddNewTrade.setVisibility(View.VISIBLE);
                ibAddNewPortfolio.setVisibility(View.VISIBLE);
                profileImage.setEnabled(true);
                preferences.setEditStatus(1);
                profileTradesAdapter.notifyDataSetChanged();
                profilePortfolioAdapter.notifyDataSetChanged();
                break;
            case R.id.save_btn:
                layoutEdit.setVisibility(View.VISIBLE);
                saveBtn.setVisibility(View.GONE);
                experienceView.setFocusable(false);
                experienceView.setClickable(false);
                experienceView.setCursorVisible(false);
                experienceView.setFocusableInTouchMode(false);
                ibAddNewTrade.setVisibility(View.INVISIBLE);
                ibAddNewPortfolio.setVisibility(View.INVISIBLE);
                profileImage.setEnabled(false);
                preferences.setEditStatus(0);
                profileTradesAdapter.notifyDataSetChanged();
                profilePortfolioAdapter.notifyDataSetChanged();
                break;
            case R.id.profile_image:
                select_image_status = false;
                pickImageFormGallery();
                break;
            case R.id.tv_portfolio_profile:
                startActivity(new Intent(getContext(), Portfolio.class));
                break;
            case R.id.btn_add_trade_profile:
                AddTrades();
                break;
            case R.id.btn_add_portfolio_profile:
                select_image_status = true;
                pickImageFormGallery();
                break;
        }

    }

    private void pickImageFormGallery() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();


            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            if (select_image_status == false) {
                profileImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                select_image_status = true;
            } else if (currentPosition >= 0) {
                portfolio_pics.remove(currentPosition);
                portfolio_pics.add(currentPosition, selectedImage);
                profilePortfolioAdapter.notifyDataSetChanged();
                currentPosition = -1;
            } else {
                if (portfolio_pics.size() == 0) {
                    portfolio_pics.clear();
                    portfolio_pics.add(selectedImage);
                    profilePortfolioAdapter.notifyDataSetChanged();
                } else {
                    portfolio_pics.add(selectedImage);
                    profilePortfolioAdapter.notifyDataSetChanged();
                }
            }
        }
    }


    public void AddTrades() {
        myDialog = new Dialog(getContext());
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setContentView(R.layout.customdialog);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        tradesView = myDialog.findViewById(R.id.et_add_trades);
        btnYes = myDialog.findViewById(R.id.btn_add);
        btnNo = myDialog.findViewById(R.id.btn_no);
        btnYes.setEnabled(true);
        btnNo.setEnabled(true);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trade = tradesView.getText().toString().trim();
                if (!trade.isEmpty() && trade != null && trade != "") {
                    trade_list.add(trade);
                    Log.i("trades", trade);
                    myDialog.cancel();
                }
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.cancel();
            }
        });
        myDialog.show();
    }

    @Override
    public void interfaceOnClick(View view, int position) {
        currentPosition = position;
        pickImageFormGallery();
    }
}
