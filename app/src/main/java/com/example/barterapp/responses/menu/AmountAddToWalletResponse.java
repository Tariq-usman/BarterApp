package com.example.barterapp.responses.menu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AmountAddToWalletResponse {

    @SerializedName("charges")
    @Expose
    private String charges;

    public String getCharges() {
        return charges;
    }

    public void setCharges(String charges) {
        this.charges = charges;
    }
}
