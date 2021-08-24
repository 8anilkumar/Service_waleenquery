package com.ennovation.servicewaleenquery.Model.LeadEnquiry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class YourLeadEnquiryResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<YourLeadEnquiryResponseData> data = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<YourLeadEnquiryResponseData> getData() {
        return data;
    }

    public void setData(List<YourLeadEnquiryResponseData> data) {
        this.data = data;
    }
}
