package com.ennovation.servicewaleenquery.Model.Servicelead;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServiceResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("bonus_lead")
    @Expose
    private String bonus_lead;
    @SerializedName("balance_lead")
    @Expose
    private String balance_lead;
    @SerializedName("helpline_number")
    @Expose
    private String helpline_number;
    @SerializedName("data")
    @Expose
    private List<ServiceResponseData> data = null;

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

    public List<ServiceResponseData> getData() {
        return data;
    }

    public void setData(List<ServiceResponseData> data) {
        this.data = data;
    }

    public String getBalance_lead() {
        return balance_lead;
    }

    public void setBalance_lead(String balance_lead) {
        this.balance_lead = balance_lead;
    }

    public String getBonus_lead() {
        return bonus_lead;
    }

    public void setBonus_lead(String bonus_lead) {
        this.bonus_lead = bonus_lead;
    }

    public String getHelpline_number() {
        return helpline_number;
    }

    public void setHelpline_number(String helpline_number) {
        this.helpline_number = helpline_number;
    }
}
