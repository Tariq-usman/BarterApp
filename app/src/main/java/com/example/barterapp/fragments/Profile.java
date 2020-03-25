package com.example.barterapp.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.barterapp.adapters.UpdateProfilePortfolioAdapter;
import com.example.barterapp.interfaces.RecyclerClickInterface;
import com.example.barterapp.activities.Portfolio;
import com.example.barterapp.R;
import com.example.barterapp.adapters.ProfilePortfolioAdapter;
import com.example.barterapp.adapters.ProfileTradesAdapter;
import com.example.barterapp.others.MySingleton;
import com.example.barterapp.others.Preferences;
import com.example.barterapp.others.VolleyMultipartRequest;
import com.example.barterapp.responses.profile.CurrentUserResponse;
import com.example.barterapp.utils.ImageFilePath;
import com.example.barterapp.utils.URLs;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static java.lang.String.format;
import static java.lang.String.valueOf;

public class Profile extends Fragment implements View.OnClickListener, RecyclerClickInterface {
    private static String result;
    private int RESULT_LOAD_IMAGE = 1;
    private Dialog myDialog;
    private TextView tvUserName, tradesView,tvSeeAll;
    private LinearLayout layoutEdit;
    private TextView tvCompletedTasks, textViewPortfolio, saveBtn;
    private RecyclerView recyclerView, recyclerViewTrades;
    private FlexboxLayoutManager layoutManager;
    private EditText et_experience;
    private Preferences preferences;
    private ProfileTradesAdapter profileTradesAdapter;
    private ProfilePortfolioAdapter profilePortfolioAdapter;
    private UpdateProfilePortfolioAdapter updateProfilePortfolioAdapter;
    private ImageButton ibAddNewTrade, ibAddNewPortfolio;
    private Button btnYes, btnNo;

    private List<String> trades_list;
    public static List<String> portfolio_pics = new ArrayList<>();
    public static List<Uri> updated_list;
    private CircleImageView iv_profileImage;
    private boolean select_image_status = true;
    private int currentPosition = -1;
    private String token;
    private byte[] bytesArray, bytesArray1;
    private Bitmap bitmap;
    Uri selectedImage;
    private ProgressDialog progressDialog;
    String path = null;
    private boolean portfolio_status = false;
    List<byte[]> portfolios = new ArrayList<>();
    byte[] b;
    Bitmap bitmap1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        customProgressDialog(getContext());
        currentUser();

        trades_list = new ArrayList<>();
        updated_list = new ArrayList<>();
        preferences = new Preferences(getContext());
        preferences.setEditStatus(0);

        tvUserName = view.findViewById(R.id.tv_user_name);
        tvCompletedTasks = view.findViewById(R.id.tv_completed_tasks);
        tvSeeAll = view.findViewById(R.id.tv_see_all);
        tvSeeAll.setOnClickListener(this);
        iv_profileImage = view.findViewById(R.id.profile_image);
        iv_profileImage.setOnClickListener(this);
        iv_profileImage.setClickable(false);

        ibAddNewTrade = view.findViewById(R.id.btn_add_trade_profile);
        ibAddNewTrade.setOnClickListener(this);
        ibAddNewPortfolio = view.findViewById(R.id.btn_add_portfolio_profile);
        ibAddNewPortfolio.setOnClickListener(this);

        et_experience = view.findViewById(R.id.tv_experience);
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
        profileTradesAdapter = new ProfileTradesAdapter(getContext(), trades_list);
        recyclerViewTrades.setAdapter(profileTradesAdapter);

        if (portfolio_status == false) {
            recyclerView = view.findViewById(R.id.recycler_view_profile_portfolio);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
            recyclerView.setHasFixedSize(true);
            profilePortfolioAdapter = new ProfilePortfolioAdapter(getContext(), portfolio_pics);
            recyclerView.setAdapter(profilePortfolioAdapter);
        }
        recyclerView = view.findViewById(R.id.recycler_view_profile_portfolio);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setHasFixedSize(true);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_edit:
                portfolio_status = true;
                layoutEdit.setVisibility(View.GONE);
                saveBtn.setVisibility(View.VISIBLE);
                tvSeeAll.setVisibility(View.INVISIBLE);
                et_experience.setFocusable(true);
                et_experience.setClickable(true);
                et_experience.setCursorVisible(true);
                et_experience.setFocusableInTouchMode(true);
                ibAddNewTrade.setVisibility(View.VISIBLE);
                ibAddNewPortfolio.setVisibility(View.VISIBLE);
                iv_profileImage.setEnabled(true);
                iv_profileImage.setClickable(true);
                preferences.setEditStatus(1);
                /*for (int i = 0; i < portfolio_pics.size(); i++) {
                    updated_list.add(Uri.parse(portfolio_pics.get(i)));
                }*/
                updateProfilePortfolioAdapter = new UpdateProfilePortfolioAdapter(getContext(), updated_list, Profile.this);
                recyclerView.setAdapter(updateProfilePortfolioAdapter);
                profileTradesAdapter.notifyDataSetChanged();
                updateProfilePortfolioAdapter.notifyDataSetChanged();
                break;
            case R.id.save_btn:
                portfolio_status = false;
                updateUserData();
                layoutEdit.setVisibility(View.VISIBLE);
                saveBtn.setVisibility(View.GONE);
                tvSeeAll.setVisibility(View.VISIBLE);
                et_experience.setFocusable(false);
                et_experience.setClickable(false);
                et_experience.setCursorVisible(false);
                et_experience.setFocusableInTouchMode(false);
                ibAddNewTrade.setVisibility(View.INVISIBLE);
                ibAddNewPortfolio.setVisibility(View.INVISIBLE);
                iv_profileImage.setEnabled(false);
                iv_profileImage.setClickable(false);
                preferences.setEditStatus(0);
                profilePortfolioAdapter = new ProfilePortfolioAdapter(getContext(), portfolio_pics);
                recyclerView.setAdapter(profilePortfolioAdapter);
                profilePortfolioAdapter.notifyDataSetChanged();
                updateProfilePortfolioAdapter.notifyDataSetChanged();
                break;
            case R.id.profile_image:
                select_image_status = false;
                pickImageFormGallery();
                break;
            case R.id.tv_see_all:
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

    private void currentUser() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.current_user_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("Response", response);
                    CurrentUserResponse userResponse = gson.fromJson(response, CurrentUserResponse.class);

                    String userName = userResponse.getUser().getName();
                    String experience = userResponse.getUser().getExperience();
                    String completed_tasks = userResponse.getUser().getCompleteOrder().toString();
                    String trades = userResponse.getUser().getTrades();
                    String profileImage = URLs.image_url + userResponse.getUser().getPicture();

                    tvUserName.setText(userName);
//                    Picasso.get().load(profileImage).error(R.drawable.profile).into(iv_profileImage);
                    Glide.with(iv_profileImage.getContext()).load(profileImage).into(iv_profileImage);
                    et_experience.setText(experience);
                    tvCompletedTasks.setText(completed_tasks);
                    if (trades != null) {
                        trades_list = new ArrayList<>(Arrays.asList(trades.replaceAll("\\s", "").split(",")));
                        profileTradesAdapter = new ProfileTradesAdapter(getContext(), trades_list);
                        recyclerViewTrades.setAdapter(profileTradesAdapter);
                    } else {
                        profileTradesAdapter = new ProfileTradesAdapter(getContext(), trades_list);
                        recyclerViewTrades.setAdapter(profileTradesAdapter);
                        Toast.makeText(getContext(), "Null Trade", Toast.LENGTH_SHORT).show();
                    }
                    portfolio_pics.clear();
                    for (int i = 0; i < userResponse.getUser().getPortfolios().size(); i++) {
                        portfolio_pics.add(userResponse.getUser().getPortfolios().get(i).getPicture());
                    }
                    profilePortfolioAdapter.notifyDataSetChanged();

                    progressDialog.dismiss();

                } catch (Exception e) {
                    Log.e("Exception", e.toString());
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<>();
                token = preferences.getToken();
                headerMap.put("Authorization", "Bearer " + token);
                return headerMap;
            }
        };
        requestQueue.add(request);
    }

    public void updateUserData() {
        progressDialog.show();
        Map<String, String> headers = new HashMap<>();
        String token = preferences.getToken();
        headers.put("Authorization", "Bearer " + token);
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLs.update_profile_url, headers, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                Log.e("Response", response.toString());

                Toast.makeText(getContext(), "Update profile successfully.", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                progressDialog.dismiss();
            }
        }) {

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String, DataPart> params = new HashMap<>();
                Bitmap bm = ((BitmapDrawable) iv_profileImage.getDrawable()).getBitmap();
                bytesArray = imageToString(bm);
                params.put("image", new DataPart(UUID.randomUUID().toString() + ".png", bytesArray));

                for (int i = 0; i < updated_list.size(); i++) {
                    Uri path = updated_list.get(i);
                    try {
                        bitmap1 = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), Uri.parse(String.valueOf(path)));
                        bytesArray1 = imageToString(bitmap1);
                        Log.e("list_size", String.valueOf(bytesArray1.length));
                        params.put("portfolioImage["+i+"]", new DataPart(UUID.randomUUID().toString() + ".png", bytesArray1));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("experience", et_experience.getText().toString().trim());
                StringBuilder string_return_trades = new StringBuilder();
                for (String str : trades_list) {
                    string_return_trades.append(str + ", ");
                }
                hashMap.put("trades", string_return_trades.toString());
                return hashMap;
            }

        };
        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getContext()).addToRequestQue(volleyMultipartRequest);
    }

    private byte[] imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 20, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void pickImageFormGallery() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            selectedImage = data.getData();
            path = ImageFilePath.getPath(getContext(), selectedImage);
            if (select_image_status == false) {

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    Glide.with(getContext()).load(bitmap).into(iv_profileImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                select_image_status = true;
            } else if (currentPosition >= 0) {
                updated_list.remove(currentPosition);
                updated_list.add(currentPosition, selectedImage);
                updateProfilePortfolioAdapter = new UpdateProfilePortfolioAdapter(getContext(), updated_list, Profile.this);
                recyclerView.setAdapter(updateProfilePortfolioAdapter);
                updateProfilePortfolioAdapter.notifyItemInserted(updated_list.size());
                updateProfilePortfolioAdapter.notifyDataSetChanged();
                currentPosition = -1;
            } else {
                if (updated_list.size() == 0) {
                    updated_list.add(selectedImage);
                    updateProfilePortfolioAdapter = new UpdateProfilePortfolioAdapter(getContext(), updated_list, Profile.this);
                    recyclerView.setAdapter(updateProfilePortfolioAdapter);
                    updateProfilePortfolioAdapter.notifyItemInserted(updated_list.size());
                    updateProfilePortfolioAdapter.notifyDataSetChanged();
                } else {
                    updated_list.add(selectedImage);
                    updateProfilePortfolioAdapter = new UpdateProfilePortfolioAdapter(getContext(), updated_list, Profile.this);
                    recyclerView.setAdapter(updateProfilePortfolioAdapter);
                    updateProfilePortfolioAdapter.notifyItemInserted(updated_list.size());
                    updateProfilePortfolioAdapter.notifyDataSetChanged();
                }
            }
        }
    }


    public void AddTrades() {
        myDialog = new Dialog(getContext());
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setContentView(R.layout.customdialog);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        tradesView = myDialog.findViewById(R.id.et_add_amount);
        btnYes = myDialog.findViewById(R.id.btn_add);
        btnNo = myDialog.findViewById(R.id.btn_no);
        btnYes.setEnabled(true);
        btnNo.setEnabled(true);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trade = tradesView.getText().toString().trim();
                if (!trade.isEmpty() && trade != null && trade != "") {
                    trades_list.add(trade);
                    profileTradesAdapter.notifyDataSetChanged();
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

    public void customProgressDialog(Context context) {
        progressDialog = new ProgressDialog(context);
        // Setting Message
        progressDialog.setMessage("Loading...");
        // Progress Dialog Style Spinner
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // Fetching max value
        progressDialog.getMax();
    }
}
