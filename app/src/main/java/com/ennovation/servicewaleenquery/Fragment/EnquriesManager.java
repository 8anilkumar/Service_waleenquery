package com.ennovation.servicewaleenquery.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ennovation.servicewaleenquery.Activity.IntroPage;
import com.ennovation.servicewaleenquery.Adapter.EnqueryManagerAdapter;
import com.ennovation.servicewaleenquery.InterFace.ServiceInterface;
import com.ennovation.servicewaleenquery.Model.EnqueryManagerResponse;
import com.ennovation.servicewaleenquery.Model.LeadEnquiry.YourLeadEnquiryResponse;
import com.ennovation.servicewaleenquery.Model.LeadEnquiry.YourLeadEnquiryResponseData;
import com.ennovation.servicewaleenquery.Model.Servicelead.ServiceResponseData;
import com.ennovation.servicewaleenquery.R;
import com.ennovation.servicewaleenquery.Utils.ApiClient;
import com.ennovation.servicewaleenquery.Utils.YourPreference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static com.ennovation.servicewaleenquery.Utils.Constants.IMAGEURL;

public class EnquriesManager extends Fragment {

    RecyclerView enqueryManagerlistRecyclerView;
    ProgressBar mainProgressbar;
    TextView txt_location;
    ImageView img_logout;
    List<YourLeadEnquiryResponseData> youeleadList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_enquries_manager, container, false);

        enqueryManagerlistRecyclerView = view.findViewById(R.id.enqueryManagerlistRecyclerView);
        txt_location = view.findViewById(R.id.txt_location);
        mainProgressbar = view.findViewById(R.id.mainProgressbar);
        img_logout = view.findViewById(R.id.img_logout);

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

        YourPreference yourPrefrence = YourPreference.getInstance(getActivity());
        String state = yourPrefrence.getData("STATE");
        String city = yourPrefrence.getData("CITY");

        txt_location.setVisibility(View.VISIBLE);
        txt_location.setText(city+" "+state);

        getLeadEnquiry();

        return view;
    }

    private void getLeadEnquiry() {
        YourPreference yourPrefrence = YourPreference.getInstance(getActivity());
        String partner_id = yourPrefrence.getData("PARTNERID");
        HashMap<String, String> map = new HashMap<>();
        mainProgressbar.setVisibility(View.VISIBLE);
        map.put("partner_id", partner_id);
        ServiceInterface serviceInterface = ApiClient.getClient().create(ServiceInterface.class);
        Call<YourLeadEnquiryResponse> call = serviceInterface.getLeadEnquiryData(map);
        call.enqueue(new Callback<YourLeadEnquiryResponse>() {
            @Override
            public void onResponse(Call<YourLeadEnquiryResponse> call, retrofit2.Response<YourLeadEnquiryResponse> response) {
                if (response.isSuccessful()) {
                    mainProgressbar.setVisibility(View.GONE);
                    String status = response.body().getStatus().toString();
                    if (status.equals("1")) {
                        youeleadList = response.body().getData();
                        bindLead(youeleadList);
                    }
                } else {
                    mainProgressbar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Something is wrong try again later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<YourLeadEnquiryResponse> call, Throwable t) {
                Log.d("ff", t.toString());
                mainProgressbar.setVisibility(View.GONE);
            }
        });
    }

    private void bindLead(List<YourLeadEnquiryResponseData> youeleadList) {
        EnqueryManagerAdapter adapter = new EnqueryManagerAdapter(youeleadList, getActivity());
        enqueryManagerlistRecyclerView.setHasFixedSize(true);
        enqueryManagerlistRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        enqueryManagerlistRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}