package com.ennovation.servicewaleenquery.Model;

public class EnqueryManagerResponse {
    String providerName;
    String serviceDiscription;
    String count;

    public EnqueryManagerResponse(String providerName, String serviceDiscription, String count) {
        this.providerName = providerName;
        this.serviceDiscription = serviceDiscription;
        this.count = count;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getServiceDiscription() {
        return serviceDiscription;
    }

    public void setServiceDiscription(String serviceDiscription) {
        this.serviceDiscription = serviceDiscription;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
