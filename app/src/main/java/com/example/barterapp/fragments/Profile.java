package com.example.barterapp.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
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
import com.example.barterapp.interfaces.RecyclerClickInterface;
import com.example.barterapp.activities.Portfolio;
import com.example.barterapp.R;
import com.example.barterapp.adapters.ProfilePortfolioAdapter;
import com.example.barterapp.adapters.ProfileTradesAdapter;
import com.example.barterapp.others.MySingleton;
import com.example.barterapp.others.Preferences;
import com.example.barterapp.others.VolleyMultipartRequest;
import com.example.barterapp.responses.profile.CurrentUserResponse;
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

import java.io.ByteArrayOutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
    private TextView tvUserName, tradesView;
    private LinearLayout layoutEdit;
    private TextView textViewPortfolio, saveBtn;
    private RecyclerView recyclerView, recyclerViewTrades;
    private FlexboxLayoutManager layoutManager;
    private EditText et_experience;
    private Preferences preferences;
    private ProfileTradesAdapter profileTradesAdapter;
    private ProfilePortfolioAdapter profilePortfolioAdapter;
    private ImageButton ibAddNewTrade, ibAddNewPortfolio;
    private Button btnYes, btnNo;

    private List<String> trades_list;
    public static List<String> portfolio_pics = new ArrayList<>();
    private CircleImageView iv_profileImage;
    private boolean select_image_status = true;
    private int currentPosition = -1;
    private String token;
    private byte[] bytesArray;
    private Bitmap bitmap;
    private ProgressDialog progressDialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        customProgressDialog(getContext());
        currentUser();

        trades_list = new ArrayList<>();
        trades_list.add("Android developer");
//        portfolio_pics.add(Uri.parse(String.valueOf(R.drawable.notification_image)));

        preferences = new Preferences(getContext());
        preferences.setEditStatus(0);

        tvUserName = view.findViewById(R.id.tv_user_name);

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


        recyclerView = view.findViewById(R.id.recycler_view_profile_portfolio);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setHasFixedSize(true);
        profilePortfolioAdapter = new ProfilePortfolioAdapter(getContext(), portfolio_pics, Profile.this);
        recyclerView.setAdapter(profilePortfolioAdapter);


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_edit:
                layoutEdit.setVisibility(View.GONE);
                saveBtn.setVisibility(View.VISIBLE);
                et_experience.setFocusable(true);
                et_experience.setClickable(true);
                et_experience.setCursorVisible(true);
                et_experience.setFocusableInTouchMode(true);
                ibAddNewTrade.setVisibility(View.VISIBLE);
                ibAddNewPortfolio.setVisibility(View.VISIBLE);
                iv_profileImage.setEnabled(true);
                iv_profileImage.setClickable(true);
                preferences.setEditStatus(1);
                profileTradesAdapter.notifyDataSetChanged();
                profilePortfolioAdapter.notifyDataSetChanged();
                break;
            case R.id.save_btn:
                try {
                    updateUserData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                layoutEdit.setVisibility(View.VISIBLE);
                saveBtn.setVisibility(View.GONE);
                et_experience.setFocusable(false);
                et_experience.setClickable(false);
                et_experience.setCursorVisible(false);
                et_experience.setFocusableInTouchMode(false);
                ibAddNewTrade.setVisibility(View.INVISIBLE);
                ibAddNewPortfolio.setVisibility(View.INVISIBLE);
                iv_profileImage.setEnabled(false);
                iv_profileImage.setClickable(false);
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

    private void currentUser() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.current_user_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                CurrentUserResponse userResponse = gson.fromJson(response, CurrentUserResponse.class);

                String userName = userResponse.getUser().getName();
                String experience = userResponse.getUser().getExperience();
                String trades = userResponse.getUser().getTrades();
                String profileImage = URLs.image_url + userResponse.getUser().getPicture();

                tvUserName.setText(userName);
                Glide.with(getContext()).load(profileImage).into(iv_profileImage);
                et_experience.setText(experience);
                if (!trades.equals(null)) {
                    trades_list = new ArrayList<>(Arrays.asList(trades.replaceAll("\\s", "").split(",")));
                    profileTradesAdapter = new ProfileTradesAdapter(getContext(), trades_list);
                    recyclerViewTrades.setAdapter(profileTradesAdapter);
                } else {
                    Toast.makeText(getContext(), "Null Trade", Toast.LENGTH_SHORT).show();
                }
                portfolio_pics.clear();
                for (int i = 0; i < userResponse.getUser().getPortfolios().size(); i++) {
                    portfolio_pics.add(userResponse.getUser().getPortfolios().get(i).getPicture());
                }
                profilePortfolioAdapter.notifyDataSetChanged();

                progressDialog.dismiss();

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

    public void updateUserData() throws JSONException {
        progressDialog.show();

        Map<String, String> headers = new HashMap<>();
        String token = preferences.getToken();
        headers.put("Authorization", "Bearer " + token);

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLs.update_profile_url, headers, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {

                Log.e("Response", response.toString());

                Toast.makeText(getContext(), "Response", Toast.LENGTH_SHORT).show();
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
               /* for (int i=0;i<portfolio_pics.size();i++){
                    bytesArray = imageToString(i);
                    params.put("portfolioImage",new DataPart(UUID.randomUUID().toString() + ".png", bytesArray));
                }*/
                params.put("portfolioImage", new DataPart(UUID.randomUUID().toString() + ".png", bytesArray));
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("experience", et_experience.getText().toString().trim());
                StringBuilder string_return_trades = new StringBuilder();
                for (String str : trades_list) {
                    string_return_trades.append(str + ", ");
                   // string_return_trades.append("\t");
                }
                hashMap.put("trades", string_return_trades.toString());
//                hashMap.put("trades", et_experience.getText().toString().trim());
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
            Uri selectedImage = data.getData();
            String path = null;
            try {
                path = getFilePath(getContext(), selectedImage);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            /*String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            String image = selectedImage.toString();
            cursor.close();*/

            if (select_image_status == false) {
                try {
                    /*Bitmap myBitmap = BitmapFactory.decodeFile(path);
                    iv_profileImage.setImageBitmap(myBitmap);*/
//                    Picasso.get().load(picturePath).into(iv_profileImage);
                    Glide.with(getContext()).load(selectedImage.toString()).into(iv_profileImage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                select_image_status = true;
            } else if (currentPosition >= 0) {
                portfolio_pics.remove(currentPosition);
                portfolio_pics.add(currentPosition, selectedImage.toString());
                profilePortfolioAdapter.notifyItemInserted(portfolio_pics.size());
                profilePortfolioAdapter.notifyDataSetChanged();
                currentPosition = -1;
            } else {
                if (portfolio_pics.size() == 0) {
                    portfolio_pics.clear();
                    portfolio_pics.add(selectedImage.toString());
                    profilePortfolioAdapter.notifyItemInserted(portfolio_pics.size());
                    profilePortfolioAdapter.notifyDataSetChanged();
                } else {
                    portfolio_pics.add(selectedImage.toString());
                    profilePortfolioAdapter.notifyItemInserted(portfolio_pics.size());
                    profilePortfolioAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @SuppressLint("NewApi")
    public static String getFilePath(Context context, Uri uri) throws URISyntaxException {
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{
                        split[1]
                };
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {


            if (isGooglePhotosUri(uri)) {
                return uri.getLastPathSegment();
            }

            String[] projection = {
                    MediaStore.Images.Media.DATA
            };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver()
                        .query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
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
