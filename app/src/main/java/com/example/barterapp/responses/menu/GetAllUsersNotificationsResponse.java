package com.example.barterapp.responses.menu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAllUsersNotificationsResponse {
    @SerializedName("notificationStreaming")
    @Expose
    private List<NotificationStreaming> notificationStreaming = null;

    public List<NotificationStreaming> getNotificationStreaming() {
        return notificationStreaming;
    }

    public void setNotificationStreaming(List<NotificationStreaming> notificationStreaming) {
        this.notificationStreaming = notificationStreaming;
    }

    public class NotificationStreaming {

        @SerializedName("receiver_id")
        @Expose
        private Integer receiverId;
        @SerializedName("actor_id")
        @Expose
        private Integer actorId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("picture")
        @Expose
        private String picture;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("created_at")
        @Expose
        private String createdAt;

        public Integer getReceiverId() {
            return receiverId;
        }

        public void setReceiverId(Integer receiverId) {
            this.receiverId = receiverId;
        }

        public Integer getActorId() {
            return actorId;
        }

        public void setActorId(Integer actorId) {
            this.actorId = actorId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

    }
}

