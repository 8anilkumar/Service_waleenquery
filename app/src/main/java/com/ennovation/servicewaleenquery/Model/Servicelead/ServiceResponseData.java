package com.ennovation.servicewaleenquery.Model.Servicelead;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceResponseData {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("state_id")
    @Expose
    private String stateId;
    @SerializedName("state_name")
    @Expose
    private String stateName;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("complete_address")
    @Expose
    private String completeAddress;
    @SerializedName("descriptions")
    @Expose
    private String descriptions;
    @SerializedName("lead_availability")
    @Expose
    private String leadAvailability;
    @SerializedName("my_availability")
    @Expose
    private String myAvailability;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("favourite")
    @Expose
    private String favourite;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getCompleteAddress() {
        return completeAddress;
    }

    public void setCompleteAddress(String completeAddress) {
        this.completeAddress = completeAddress;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getLeadAvailability() {
        return leadAvailability;
    }

    public void setLeadAvailability(String leadAvailability) {
        this.leadAvailability = leadAvailability;
    }

    public String getMyAvailability() {
        return myAvailability;
    }

    public void setMyAvailability(String myAvailability) {
        this.myAvailability = myAvailability;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getFavourite() {
        return favourite;
    }

    public void setFavourite(String favourite) {
        this.favourite = favourite;
    }
}
