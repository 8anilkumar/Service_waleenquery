package com.ennovation.servicewaleenquery.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ennovation.servicewaleenquery.Activity.IntroPage;
import com.ennovation.servicewaleenquery.Adapter.ServiceAdapter;
import com.ennovation.servicewaleenquery.Adapter.SliderAdapter;
import com.ennovation.servicewaleenquery.InterFace.GetLead;
import com.ennovation.servicewaleenquery.InterFace.ServiceInterface;
import com.ennovation.servicewaleenquery.Model.Banner.BannerResponse;
import com.ennovation.servicewaleenquery.Model.Banner.BannerResponseData;
import com.ennovation.servicewaleenquery.Model.LeadEnquiryResponse;
import com.ennovation.servicewaleenquery.Model.Servicelead.ServiceResponse;
import com.ennovation.servicewaleenquery.Model.Servicelead.ServiceResponseData;
import com.ennovation.servicewaleenquery.R;
import com.ennovation.servicewaleenquery.Utils.ApiClient;
import com.ennovation.servicewaleenquery.Utils.Helper;
import com.ennovation.servicewaleenquery.Utils.YourPreference;
import com.github.demono.AutoScrollViewPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static com.ennovation.servicewaleenquery.Utils.Constants.IMAGEURL;

public class Enquries extends Fragment implements GetLead {

    RecyclerView serviceListRecyclerView;
    TextView txt_location, txt_leadCount, txt_bonasleadCount;
    ProgressBar mainProgressbar;
    ImageView img_logout;
    AutoScrollViewPager mViewPager;
    List<ServiceResponseData> leadListDate = new ArrayList<>();
    List<BannerResponseData> bannerResponses = new ArrayList<>();
//    ArrayList<BannerResponseData> sliderDataArrayList = new ArrayList<>();
    String isFramLauncher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_enquries, container, false);

        serviceListRecyclerView = view.findViewById(R.id.serviceListRecyclerView);
        txt_location = view.findViewById(R.id.txt_location);
        txt_leadCount = view.findViewById(R.id.txt_leadCount);
        txt_bonasleadCount = view.findViewById(R.id.txt_bonasleadCount);
        img_logout = view.findViewById(R.id.img_logout);
        mainProgressbar = view.findViewById(R.id.mainProgressbar);
        mViewPager = view.findViewById(R.id.viewPager);

        mViewPager.setCycle(true);

        YourPreference yourPrefrence = YourPreference.getInstance(getActivity());
        String state = yourPrefrence.getData("STATE");
        String city = yourPrefrence.getData("CITY");
        txt_location.setVisibility(View.VISIBLE);
        txt_location.setText(city + " " + state);

        if (Helper.INSTANCE.isNetworkAvailable(getContext())) {
            getBanner();
            getLead();
        } else {
            Helper.INSTANCE.Error(getContext(), getString(R.string.NOCONN));
        }

        img_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Logout")
                        .setMessage("\nAre you sure want to logout?\n")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                SharedPreferences preferences = getActivity().getSharedPreferences("YourCustomNamedPreference", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.clear();
                                editor.apply();

                                Intent intent = new Intent(getActivity(), IntroPage.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        })

                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                            }
                        })
                        .show();
            }
        });

        return view;

    }

    private void getBanner() {
        bannerResponses.clear();
        mainProgressbar.setVisibility(View.VISIBLE);
        ServiceInterface serviceInterface = ApiClient.getClient().create(ServiceInterface.class);
        Call<BannerResponse> call = serviceInterface.getBanner();
        call.enqueue(new Callback<BannerResponse>() {
            @Override
            public void onResponse(Call<BannerResponse> call, retrofit2.Response<BannerResponse> response) {
                if (response.isSuccessful()) {
                    mainProgressbar.setVisibility(View.GONE);
                    String status = response.body().getStatus().toString();
                    bannerResponses = response.body().getData();
                    if (status.equals("1")) {
                        init(bannerResponses);
                    }
                }
            }

            @Override
            public void onFailure(Call<BannerResponse> call, Throwable t) {
                Log.d("ff", t.toString());
                mainProgressbar.setVisibility(View.GONE);
            }
        });
    }

    private void init(List<BannerResponseData> bannerResponses) {
        mViewPager.setAdapter(new SliderAdapter(getContext(), bannerResponses));
        mViewPager.startAutoScroll();
    }

    private void getLead() {
        leadListDate.clear();
        YourPreference yourPrefrence = YourPreference.getInstance(getActivity());
        String profession = yourPrefrence.getData("PROFESSION");
        String city = yourPrefrence.getData("CITY");
        String partner_id = yourPrefrence.getData("PARTNERID");
        String state_id = yourPrefrence.getData("STATEID");

        HashMap<String, String> map = new HashMap<>();
        mainProgressbar.setVisibility(View.VISIBLE);
        map.put("profession", profession);
        map.put("partner_id", partner_id);
        map.put("city", city);
        map.put("state_id", state_id);

        ServiceInterface serviceInterface = ApiClient.getClient().create(ServiceInterface.class);
        Call<ServiceResponse> call = serviceInterface.getLeads(map);
        call.enqueue(new Callback<ServiceResponse>() {
            @Override
            public void onResponse(Call<ServiceResponse> call, retrofit2.Response<ServiceResponse> response) {
                if (response.isSuccessful()) {
                    mainProgressbar.setVisibility(View.GONE);
                    String status = response.body().getStatus().toString();
                    txt_leadCount.setText(response.body().getBalance_lead());
                    txt_bonasleadCount.setText(response.body().getBonus_lead());

                    isFramLauncher = yourPrefrence.getData("isFramLauncher");

                    if (isFramLauncher.equals("true") && (response.body().getBalance_lead().equals("0")) && (response.body().getBonus_lead().equals("0"))) {
                        noopenCustomPopup(response.body().getHelpline_number());
                        yourPrefrence.saveData("isFramLauncher", "false");
                    }

                    if (status.equals("1")) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            if (response.body().getData().get(i).getMyAvailability().equals("0")) {

                            } else {
                                leadListDate.add(response.body().getData().get(i));
                                bindLead(leadListDate, response.body().getBalance_lead(), response.body().getBonus_lead(),response.body().getHelpline_number());
                            }
                        }
                    }
                } else {
                    mainProgressbar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Something is wrong try again later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServiceResponse> call, Throwable t) {
                Log.d("ff", t.toString());
                mainProgressbar.setVisibility(View.GONE);
            }
        });
    }

    private void noopenCustomPopup(String helpline_number) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.no_lead_balance_custompopup);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        TextView txt_popup = dialog.findViewById(R.id.txt_ok);

        txt_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPermissionGranted()) {
                    call_action(helpline_number);
                    dialog.dismiss();
                }
            }
        });

        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    private void bindLead(List<ServiceResponseData> leadList, String balance_lead, String bonus_lead, String helpLineNumber) {
        ServiceAdapter adapter = new ServiceAdapter(leadList, getActivity(), this, balance_lead, bonus_lead,helpLineNumber,mainProgressbar);
        serviceListRecyclerView.setHasFixedSize(true);
        serviceListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        serviceListRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void leadClickId(String position, String number) {
        enquiryLead(position, number);
    }

    private void enquiryLead(String position, String number) {
        YourPreference yourPrefrence = YourPreference.getInstance(getActivity());
        String partner_id = yourPrefrence.getData("PARTNERID");
        HashMap<String, String> map = new HashMap<>();
        mainProgressbar.setVisibility(View.VISIBLE);
        map.put("lead_id", position);
        map.put("partner_id", partner_id);
        ServiceInterface serviceInterface = ApiClient.getClient().create(ServiceInterface.class);
        Call<LeadEnquiryResponse> call = serviceInterface.getLeadEnquiry(map);
        call.enqueue(new Callback<LeadEnquiryResponse>() {
            @Override
            public void onResponse(Call<LeadEnquiryResponse> call, retrofit2.Response<LeadEnquiryResponse> response) {
                if (response.isSuccessful()) {
                    mainProgressbar.setVisibility(View.GONE);
                    String status = response.body().getStatus().toString();
                    if (status.equals("1")) {
                        if (isPermissionGranted()) {
                            call_action(number);
                        }
                    } else if (status.equals("2")) {
                        final Dialog dialog = new Dialog(getActivity());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.lead_limit_excided);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        TextView txt_popup = dialog.findViewById(R.id.txt_ok);

                        txt_popup.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                        dialog.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LeadEnquiryResponse> call, Throwable t) {
                Log.d("ff", t.toString());
                mainProgressbar.setVisibility(View.GONE);
            }
        });
    }

    private void call_action(String mobile) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + mobile));
        startActivity(callIntent);
    }

    public boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getActivity().checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted");
                return true;
            } else {
                Log.v("TAG", "Permission is revoked");
                ActivityCompat.requestPermissions((Activity) getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        } else {
            Log.v("TAG", "Permission is granted");
            return true;
        }
    }

}