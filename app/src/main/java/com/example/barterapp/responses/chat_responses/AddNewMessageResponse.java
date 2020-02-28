package com.example.barterapp.responses.chat_responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddNewMessageResponse {

    @SerializedName("message")
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
