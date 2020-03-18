package com.example.barterapp.responses.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserBalanceResponse {

    @SerializedName("balance")
    @Expose
    private Balance balance;

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

    public class Balance {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("picture")
        @Expose
        private String picture;
        @SerializedName("trades")
        @Expose
        private String trades;
        @SerializedName("experience")
        @Expose
        private String experience;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("email_verified_at")
        @Expose
        private Object emailVerifiedAt;
        @SerializedName("device_token")
        @Expose
        private String deviceToken;
        @SerializedName("deleted_at")
        @Expose
        private Object deletedAt;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("wallet")
        @Expose
        private Wallet wallet;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getTrades() {
            return trades;
        }

        public void setTrades(String trades) {
            this.trades = trades;
        }

        public String getExperience() {
            return experience;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Object getEmailVerifiedAt() {
            return emailVerifiedAt;
        }

        public void setEmailVerifiedAt(Object emailVerifiedAt) {
            this.emailVerifiedAt = emailVerifiedAt;
        }

        public String getDeviceToken() {
            return deviceToken;
        }

        public void setDeviceToken(String deviceToken) {
            this.deviceToken = deviceToken;
        }

        public Object getDeletedAt() {
            return deletedAt;
        }

        public void setDeletedAt(Object deletedAt) {
            this.deletedAt = deletedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Wallet getWallet() {
            return wallet;
        }

        public void setWallet(Wallet wallet) {
            this.wallet = wallet;
        }

        public class Wallet {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("holder_type")
            @Expose
            private String holderType;
            @SerializedName("holder_id")
            @Expose
            private Integer holderId;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("slug")
            @Expose
            private String slug;
            @SerializedName("description")
            @Expose
            private Object description;
            @SerializedName("balance")
            @Expose
            private Integer balance;
            @SerializedName("decimal_places")
            @Expose
            private Integer decimalPlaces;
            @SerializedName("created_at")
            @Expose
            private String createdAt;
            @SerializedName("updated_at")
            @Expose
            private String updatedAt;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getHolderType() {
                return holderType;
            }

            public void setHolderType(String holderType) {
                this.holderType = holderType;
            }

            public Integer getHolderId() {
                return holderId;
            }

            public void setHolderId(Integer holderId) {
                this.holderId = holderId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSlug() {
                return slug;
            }

            public void setSlug(String slug) {
                this.slug = slug;
            }

            public Object getDescription() {
                return description;
            }

            public void setDescription(Object description) {
                this.description = description;
            }

            public Integer getBalance() {
                return balance;
            }

            public void setBalance(Integer balance) {
                this.balance = balance;
            }

            public Integer getDecimalPlaces() {
                return decimalPlaces;
            }

            public void setDecimalPlaces(Integer decimalPlaces) {
                this.decimalPlaces = decimalPlaces;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public String getUpdatedAt() {
                return updatedAt;
            }

            public void setUpdatedAt(String updatedAt) {
                this.updatedAt = updatedAt;
            }

        }

    }

}
