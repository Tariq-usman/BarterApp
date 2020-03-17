package com.example.barterapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.barterapp.R;
import com.example.barterapp.adapters.ChatAdapter;
import com.example.barterapp.fragments.dialog_fragments.DialogFragmentInvoice;
import com.example.barterapp.others.Preferences;
import com.example.barterapp.responses.chat_responses.AddNewMessageResponse;
import com.example.barterapp.responses.chat_responses.GetAllChatMessagesResponse;
import com.example.barterapp.utils.URLs;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private ImageView imageView, ivSendMessage;
    private EditText etTypeMessage;
    DialogFragmentInvoice fragmentInvoice;
    private ImageView generateInvoice;
    private int sender_id, receiver_id;
    private Preferences preferences;
    private ProgressDialog progressDialog;
    List<GetAllChatMessagesResponse.Message> messageList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        customProgressDialog(ChatActivity.this);
        preferences = new Preferences(this);
        receiver_id = preferences.getUserId();
        sender_id = preferences.getSenderId();

        getAllMessages();

        fragmentInvoice = new DialogFragmentInvoice();
        generateInvoice = findViewById(R.id.view_get_custom_offers);
        generateInvoice.setOnClickListener(this);
        imageView = findViewById(R.id.iv_back_chat);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ivSendMessage = findViewById(R.id.view_send_msg);
        ivSendMessage.setOnClickListener(this);
        etTypeMessage = findViewById(R.id.view_write_msg);
        recyclerView = findViewById(R.id.recycler_view_chat);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        chatAdapter = new ChatAdapter( sender_id,getApplicationContext(), messageList);
        recyclerView.setAdapter(chatAdapter);
        recyclerView.smoothScrollToPosition(chatAdapter.getItemCount()+1);  }

    private void getAllMessages() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(ChatActivity.this);
        final Gson gson = new GsonBuilder().create();

        StringRequest request = new StringRequest(Request.Method.GET, URLs.get_all_chat_messages_url + sender_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                GetAllChatMessagesResponse allChatMessagesResponse = gson.fromJson(response, GetAllChatMessagesResponse.class);
                messageList.clear();
                for (int i = 0; i < allChatMessagesResponse.getMessage().size(); i++) {
                    messageList.add(allChatMessagesResponse.getMessage().get(i));
                }
                chatAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
//                Toast.makeText(ChatActivity.this, "Response", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.e("Error", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<>();
                String token = preferences.getToken();
                headerMap.put("Authorization", "Bearer " + token);
                return headerMap;

            }
        };
        requestQueue.add(request);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_get_custom_offers:
                Intent intent = new Intent(ChatActivity.this, OfferAcceptReject.class);
                startActivity(intent);
                break;
//                fragmentInvoice.show(getSupportFragmentManager(),"Invoice Dialog");
            case R.id.view_send_msg:
                addMessage(etTypeMessage.getText().toString());
                etTypeMessage.setText("");
                break;
        }

    }

    private void addMessage(final String strMsg) {
        RequestQueue requestQueue = Volley.newRequestQueue(ChatActivity.this);

        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.POST, URLs.add_messages , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                GetAllChatMessagesResponse.Message msg = new GetAllChatMessagesResponse.Message();
                msg.setDateTime("");
                msg.setDeliver(0);
                msg.setMessageId(0);
                msg.setReceiverId(sender_id);
                msg.setSenderId(receiver_id);
                msg.setSeen(0);
                msg.setText(strMsg);

                messageList.add(msg);
                chatAdapter.notifyItemInserted(chatAdapter.getItemCount()+1);
                recyclerView.smoothScrollToPosition(chatAdapter.getItemCount());
            AddNewMessageResponse addNewMessageResponse = gson.fromJson(response,AddNewMessageResponse.class);
                Toast.makeText(ChatActivity.this, ""+addNewMessageResponse.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error",error.toString());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<>();
                String token = preferences.getToken();
                headerMap.put("Authorization", "Bearer " + token);
                return headerMap;

            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> map = new HashMap<>();
                String message = etTypeMessage.getText().toString();
                map.put("receiver_id",String.valueOf(sender_id));
                map.put("text",message);
                return map;
            }
        };
        requestQueue.add(request);

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
