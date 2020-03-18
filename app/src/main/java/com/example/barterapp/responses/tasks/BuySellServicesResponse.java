package com.example.barterapp.responses.tasks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BuySellServicesResponse {

    @SerializedName("sellJob")
    @Expose
    private List<SellJob> sellJob = null;
    @SerializedName("buyJobs")
    @Expose
    private List<BuyJob> buyJobs = null;

    public List<SellJob> getSellJob() {
        return sellJob;
    }

    public void setSellJob(List<SellJob> sellJob) {
        this.sellJob = sellJob;
    }

    public List<BuyJob> getBuyJobs() {
        return buyJobs;
    }

    public void setBuyJobs(List<BuyJob> buyJobs) {
        this.buyJobs = buyJobs;
    }

    public class SellJob {

        @SerializedName("offer_user_id")
        @Expose
        private Integer offerUserId;
        @SerializedName("offer_id")
        @Expose
        private Integer offerId;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("seller_security")
        @Expose
        private Integer sellerSecurity;
        @SerializedName("user")
        @Expose
        private User user;
        @SerializedName("offer")
        @Expose
        private Offer offer;

        public Integer getOfferUserId() {
            return offerUserId;
        }

        public void setOfferUserId(Integer offerUserId) {
            this.offerUserId = offerUserId;
        }

        public Integer getOfferId() {
            return offerId;
        }

        public void setOfferId(Integer offerId) {
            this.offerId = offerId;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getSellerSecurity() {
            return sellerSecurity;
        }

        public void setSellerSecurity(Integer sellerSecurity) {
            this.sellerSecurity = sellerSecurity;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public Offer getOffer() {
            return offer;
        }

        public void setOffer(Offer offer) {
            this.offer = offer;
        }
        public class User {

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

        }
        public class Offer {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("user_id")
            @Expose
            private Integer userId;
            @SerializedName("job_id")
            @Expose
            private Integer jobId;
            @SerializedName("offer_type_id")
            @Expose
            private Integer offerTypeId;
            @SerializedName("offer")
            @Expose
            private String offer;
            @SerializedName("status_id")
            @Expose
            private Integer statusId;
            @SerializedName("description")
            @Expose
            private String description;
            @SerializedName("return_service")
            @Expose
            private Object returnService;
            @SerializedName("barter_security")
            @Expose
            private Integer barterSecurity;
            @SerializedName("created_at")
            @Expose
            private String createdAt;
            @SerializedName("updated_at")
            @Expose
            private String updatedAt;
            @SerializedName("deleted_at")
            @Expose
            private Object deletedAt;
            @SerializedName("job")
            @Expose
            private Job job;

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

            public Integer getJobId() {
                return jobId;
            }

            public void setJobId(Integer jobId) {
                this.jobId = jobId;
            }

            public Integer getOfferTypeId() {
                return offerTypeId;
            }

            public void setOfferTypeId(Integer offerTypeId) {
                this.offerTypeId = offerTypeId;
            }

            public String getOffer() {
                return offer;
            }

            public void setOffer(String offer) {
                this.offer = offer;
            }

            public Integer getStatusId() {
                return statusId;
            }

            public void setStatusId(Integer statusId) {
                this.statusId = statusId;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public Object getReturnService() {
                return returnService;
            }

            public void setReturnService(Object returnService) {
                this.returnService = returnService;
            }

            public Integer getBarterSecurity() {
                return barterSecurity;
            }

            public void setBarterSecurity(Integer barterSecurity) {
                this.barterSecurity = barterSecurity;
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

            public Object getDeletedAt() {
                return deletedAt;
            }

            public void setDeletedAt(Object deletedAt) {
                this.deletedAt = deletedAt;
            }

            public Job getJob() {
                return job;
            }

            public void setJob(Job job) {
                this.job = job;
            }
            public class Job {

                @SerializedName("id")
                @Expose
                private Integer id;
                @SerializedName("user_id")
                @Expose
                private Integer userId;
                @SerializedName("title")
                @Expose
                private String title;
                @SerializedName("description")
                @Expose
                private String description;
                @SerializedName("due_date")
                @Expose
                private String dueDate;
                @SerializedName("latitude")
                @Expose
                private String latitude;
                @SerializedName("longitude")
                @Expose
                private String longitude;
                @SerializedName("estimated_budget")
                @Expose
                private Integer estimatedBudget;
                @SerializedName("service_type")
                @Expose
                private String serviceType;
                @SerializedName("status_id")
                @Expose
                private Integer statusId;
                @SerializedName("created_at")
                @Expose
                private String createdAt;
                @SerializedName("updated_at")
                @Expose
                private String updatedAt;
                @SerializedName("deleted_at")
                @Expose
                private Object deletedAt;

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

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getDueDate() {
                    return dueDate;
                }

                public void setDueDate(String dueDate) {
                    this.dueDate = dueDate;
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

                public Integer getEstimatedBudget() {
                    return estimatedBudget;
                }

                public void setEstimatedBudget(Integer estimatedBudget) {
                    this.estimatedBudget = estimatedBudget;
                }

                public String getServiceType() {
                    return serviceType;
                }

                public void setServiceType(String serviceType) {
                    this.serviceType = serviceType;
                }

                public Integer getStatusId() {
                    return statusId;
                }

                public void setStatusId(Integer statusId) {
                    this.statusId = statusId;
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

                public Object getDeletedAt() {
                    return deletedAt;
                }

                public void setDeletedAt(Object deletedAt) {
                    this.deletedAt = deletedAt;
                }

            }

        }

    }

    public class BuyJob {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("job_user_id")
        @Expose
        private Integer jobUserId;
        @SerializedName("job_id")
        @Expose
        private Integer jobId;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("buyer_security")
        @Expose
        private Integer buyerSecurity;
        @SerializedName("user")
        @Expose
        private User user;
        @SerializedName("job")
        @Expose
        private Job job;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getJobUserId() {
            return jobUserId;
        }

        public void setJobUserId(Integer jobUserId) {
            this.jobUserId = jobUserId;
        }

        public Integer getJobId() {
            return jobId;
        }

        public void setJobId(Integer jobId) {
            this.jobId = jobId;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getBuyerSecurity() {
            return buyerSecurity;
        }

        public void setBuyerSecurity(Integer buyerSecurity) {
            this.buyerSecurity = buyerSecurity;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public Job getJob() {
            return job;
        }

        public void setJob(Job job) {
            this.job = job;
        }

        public class Job {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("title")
            @Expose
            private String title;
            @SerializedName("description")
            @Expose
            private String description;
            @SerializedName("due_date")
            @Expose
            private String dueDate;
            @SerializedName("latitude")
            @Expose
            private String latitude;
            @SerializedName("longitude")
            @Expose
            private String longitude;
            @SerializedName("estimated_budget")
            @Expose
            private Integer estimatedBudget;
            @SerializedName("service_type")
            @Expose
            private String serviceType;
            @SerializedName("status_id")
            @Expose
            private Integer statusId;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getDueDate() {
                return dueDate;
            }

            public void setDueDate(String dueDate) {
                this.dueDate = dueDate;
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

            public Integer getEstimatedBudget() {
                return estimatedBudget;
            }

            public void setEstimatedBudget(Integer estimatedBudget) {
                this.estimatedBudget = estimatedBudget;
            }

            public String getServiceType() {
                return serviceType;
            }

            public void setServiceType(String serviceType) {
                this.serviceType = serviceType;
            }

            public Integer getStatusId() {
                return statusId;
            }

            public void setStatusId(Integer statusId) {
                this.statusId = statusId;
            }

        }
        public class User {

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

        }
    }
}



















