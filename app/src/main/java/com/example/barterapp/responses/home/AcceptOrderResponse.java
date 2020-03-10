package com.example.barterapp.responses.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AcceptOrderResponse {
    @SerializedName("acceptOrders")
    @Expose
    private String acceptOrders;

    public String getAcceptOrders() {
        return acceptOrders;
    }

    public void setAcceptOrders(String acceptOrders) {
        this.acceptOrders = acceptOrders;
    }
}
