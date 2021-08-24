package com.ennovation.servicewaleenquery.InterFace;

import com.ennovation.servicewaleenquery.Model.Banner.BannerResponse;
import com.ennovation.servicewaleenquery.Model.FavoriteUnFavoriteResponse;
import com.ennovation.servicewaleenquery.Model.LeadEnquiry.YourLeadEnquiryResponse;
import com.ennovation.servicewaleenquery.Model.LeadEnquiryResponse;
import com.ennovation.servicewaleenquery.Model.Servicelead.ServiceResponse;
import com.ennovation.servicewaleenquery.Model.loginResponse;
import com.ennovation.servicewaleenquery.Utils.Constants;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ServiceInterface {
    @POST(Constants.LOGIN)
    Call<loginResponse> doLogin(@Body HashMap<String, String> map);

    @POST(Constants.LEADS)
    Call<ServiceResponse> getLeads(@Body HashMap<String, String> map);

    @POST(Constants.LEADENQUIRY)
    Call<LeadEnquiryResponse> getLeadEnquiry(@Body HashMap<String, String> map);

    @POST(Constants.YOURLEADENQUIRY)
    Call<YourLeadEnquiryResponse> getLeadEnquiryData(@Body HashMap<String, String> map);

    @POST(Constants.FAVORITE)
    Call<FavoriteUnFavoriteResponse> getFavorite(@Body HashMap<String, String> map);

    @GET(Constants.BANNER)
    Call<BannerResponse> getBanner();
}
