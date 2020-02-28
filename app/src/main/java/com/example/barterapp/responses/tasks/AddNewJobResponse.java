package com.example.barterapp.responses.tasks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddNewJobResponse {

    @SerializedName("jobs")
    @Expose
    private String jobs;

    public String getJobs() {
        return jobs;
    }

    public void setJobs(String jobs) {
        this.jobs = jobs;
    }

}
