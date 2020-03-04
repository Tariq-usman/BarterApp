package com.example.barterapp.responses.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateOfferResponse {

        @SerializedName("createOffer")
        @Expose
        private String createOffer;

        public String getCreateOffer() {
            return createOffer;
        }

        public void setCreateOffer(String createOffer) {
            this.createOffer = createOffer;
        }
}
