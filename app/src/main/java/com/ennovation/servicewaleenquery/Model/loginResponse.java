package com.ennovation.servicewaleenquery.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class loginResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("partner_id")
    @Expose
    private String partnerId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("profession")
    @Expose
    private String profession;
    @SerializedName("state_id")
    @Expose
    private String stateId;
    @SerializedName("state_name")
    @Expose
    private String stateName;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("n_leads")
    @Expose
    private String nLeads;
    @SerializedName("profile_photo")
    @Expose
    private String profilePhoto;

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

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getnLeads() {
        return nLeads;
    }

    public void setnLeads(String nLeads) {
        this.nLeads = nLeads;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
}
