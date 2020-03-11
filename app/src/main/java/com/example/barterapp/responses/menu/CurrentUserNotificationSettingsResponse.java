package com.example.barterapp.responses.menu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentUserNotificationSettingsResponse {

    @SerializedName("notificationSetting")
    @Expose
    private NotificationSetting notificationSetting;

    public NotificationSetting getNotificationSetting() {
        return notificationSetting;
    }

    public void setNotificationSetting(NotificationSetting notificationSetting) {
        this.notificationSetting = notificationSetting;
    }

    public class NotificationSetting {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("sound")
        @Expose
        private Integer sound;
        @SerializedName("notification")
        @Expose
        private Integer notification;
        @SerializedName("new_order")
        @Expose
        private Integer newOrder;
        @SerializedName("chat")
        @Expose
        private Integer chat;
        @SerializedName("created_at")
        @Expose
        private Object createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getSound() {
            return sound;
        }

        public void setSound(Integer sound) {
            this.sound = sound;
        }

        public Integer getNotification() {
            return notification;
        }

        public void setNotification(Integer notification) {
            this.notification = notification;
        }

        public Integer getNewOrder() {
            return newOrder;
        }

        public void setNewOrder(Integer newOrder) {
            this.newOrder = newOrder;
        }

        public Integer getChat() {
            return chat;
        }

        public void setChat(Integer chat) {
            this.chat = chat;
        }

        public Object getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Object createdAt) {
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
