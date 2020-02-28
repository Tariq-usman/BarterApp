package com.example.barterapp.responses.chat_responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAllChatMessagesResponse {

    @SerializedName("Message")
    @Expose
    private List<Message> message = null;

    public List<Message> getMessage() {
        return message;
    }

    public void setMessage(List<Message> message) {
        this.message = message;
    }


    public static class Message {

        @SerializedName("message_id")
        @Expose
        private Integer messageId;
        @SerializedName("sender_id")
        @Expose
        private Integer senderId;
        @SerializedName("receiver_id")
        @Expose
        private Integer receiverId;
        @SerializedName("text")
        @Expose
        private String text;
        @SerializedName("seen")
        @Expose
        private Integer seen;
        @SerializedName("deliver")
        @Expose
        private Integer deliver;
        @SerializedName("dateTime")
        @Expose
        private String dateTime;

        public Integer getMessageId() {
            return messageId;
        }

        public void setMessageId(Integer messageId) {
            this.messageId = messageId;
        }

        public Integer getSenderId() {
            return senderId;
        }

        public void setSenderId(Integer senderId) {
            this.senderId = senderId;
        }

        public Integer getReceiverId() {
            return receiverId;
        }

        public void setReceiverId(Integer receiverId) {
            this.receiverId = receiverId;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Integer getSeen() {
            return seen;
        }

        public void setSeen(Integer seen) {
            this.seen = seen;
        }

        public Integer getDeliver() {
            return deliver;
        }

        public void setDeliver(Integer deliver) {
            this.deliver = deliver;
        }

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

    }
}

