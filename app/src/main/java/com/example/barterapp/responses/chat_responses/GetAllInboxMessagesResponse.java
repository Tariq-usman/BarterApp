package com.example.barterapp.responses.chat_responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAllInboxMessagesResponse {

    @SerializedName("GetMessages")
    @Expose
    private List<GetMessage> getMessages = null;

    public List<GetMessage> getGetMessages() {
        return getMessages;
    }

    public void setGetMessages(List<GetMessage> getMessages) {
        this.getMessages = getMessages;
    }

    public class GetMessage {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("picture")
        @Expose
        private String picture;
        @SerializedName("last_messages")
        @Expose
        private LastMessages lastMessages;

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

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public LastMessages getLastMessages() {
            return lastMessages;
        }

        public void setLastMessages(LastMessages lastMessages) {
            this.lastMessages = lastMessages;
        }

        public class LastMessages {

            @SerializedName("messages_id")
            @Expose
            private Integer messagesId;
            @SerializedName("messages")
            @Expose
            private String messages;
            @SerializedName("seen")
            @Expose
            private Integer seen;
            @SerializedName("dateTime")
            @Expose
            private String dateTime;

            public Integer getMessagesId() {
                return messagesId;
            }

            public void setMessagesId(Integer messagesId) {
                this.messagesId = messagesId;
            }

            public String getMessages() {
                return messages;
            }

            public void setMessages(String messages) {
                this.messages = messages;
            }

            public Integer getSeen() {
                return seen;
            }

            public void setSeen(Integer seen) {
                this.seen = seen;
            }

            public String getDateTime() {
                return dateTime;
            }

            public void setDateTime(String dateTime) {
                this.dateTime = dateTime;
            }

        }

    }
}




