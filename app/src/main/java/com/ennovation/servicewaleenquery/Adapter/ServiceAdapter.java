package com.ennovation.servicewaleenquery.Adapter;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ennovation.servicewaleenquery.InterFace.GetLead;
import com.ennovation.servicewaleenquery.InterFace.ServiceInterface;
import com.ennovation.servicewaleenquery.Model.FavoriteUnFavoriteResponse;
import com.ennovation.servicewaleenquery.Model.LeadEnquiryResponse;
import com.ennovation.servicewaleenquery.Model.Servicelead.ServiceResponseData;
import com.ennovation.servicewaleenquery.R;
import com.ennovation.servicewaleenquery.Utils.ApiClient;
import com.ennovation.servicewaleenquery.Utils.YourPreference;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {

    List<ServiceResponseData> leadList;
    Context context;
    GetLead getLead;
    TextView txt_Yes, txt_No;
    String balance_lead, bonus_lead,helpLineNumber;
    ProgressBar progressBar;

    public ServiceAdapter(List<ServiceResponseData> leadList, FragmentActivity activity, GetLead getLead, String balance_lead, String bonus_lead, String helpLineNumber, ProgressBar progressBar) {
        this.leadList = leadList;
        this.context = activity;
        this.getLead = getLead;
        this.balance_lead = balance_lead;
        this.bonus_lead = bonus_lead;
        this.helpLineNumber = helpLineNumber;
        this.progressBar = progressBar;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.service_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ServiceResponseData myListData = leadList.get(position);
        holder.txt_serviceDiscription.setText(myListData.getDescriptions());
        holder.txt_serviceTime.setText(myListData.getCreatedAt());
        holder.txt_category.setText(myListData.getCategory());
        holder.txt_customerName.setText(myListData.getName());
        holder.txt_location.setText(myListData.getCompleteAddress());
        holder.txt_countryName.setText(myListData.getCity() + " " + myListData.getStateName());

        if(myListData.getFavourite().equals("1")){
            holder.img_favorite.setImageResource(R.drawable.ic_fill_star);
        } else {
            holder.img_favorite.setImageResource(R.drawable.ic_unfill_star);
        }

        if (myListData.getLeadAvailability().equals("0")) {
            holder.img_comtactLayout.setImageResource(R.drawable.ic_booked);
        } else {
            holder.img_comtactLayout.setImageResource(R.drawable.ic_contact);
        }


        holder.img_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YourPreference yourPrefrence = YourPreference.getInstance(context);
                String partner_id = yourPrefrence.getData("PARTNERID");
                HashMap<String, String> map = new HashMap<>();
                progressBar.setVisibility(View.VISIBLE);
                map.put("lead_id", myListData.getId());
                map.put("partner_id", partner_id);
                ServiceInterface serviceInterface = ApiClient.getClient().create(ServiceInterface.class);
                Call<FavoriteUnFavoriteResponse> call = serviceInterface.getFavorite(map);
                call.enqueue(new Callback<FavoriteUnFavoriteResponse>() {
                    @Override
                    public void onResponse(Call<FavoriteUnFavoriteResponse> call, retrofit2.Response<FavoriteUnFavoriteResponse> response) {
                        if (response.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            String status = response.body().getStatus().toString();
                            if (status.equals("1")) {
                                Toast.makeText(context, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                String sta = response.body().getFav().toString();
                               if (sta.equals("1")){
                                   holder.img_favorite.setImageResource(R.drawable.ic_fill_star);
                                } else {
                                   holder.img_favorite.setImageResource(R.drawable.ic_unfill_star);
                               }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<FavoriteUnFavoriteResponse> call, Throwable t) {
                        Log.d("ff", t.toString());
                        progressBar.setVisibility(View.GONE);
                    }
                });


            }
        });


        holder.img_comtactLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (myListData.getLeadAvailability().equals("0")) {

                } else if (balance_lead.equals("0") && (!bonus_lead.equals("0"))) {
                    final Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.no_lead_balance_popup);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    TextView txt_popup = dialog.findViewById(R.id.txt_ok);
                    TextView txt_no = dialog.findViewById(R.id.txt_no);

                    txt_popup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getLead.leadClickId(myListData.getId(), helpLineNumber);
                            dialog.dismiss();
                        }
                    });


                    txt_no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                    dialog.show();

                } else if (balance_lead.equals("0") && (bonus_lead.equals("0"))) {
                    final Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.no_lead_balance_custompopup);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    TextView txt_popup = dialog.findViewById(R.id.txt_ok);

                    txt_popup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (isPermissionGranted()) {
                                call_action(helpLineNumber);
                                dialog.dismiss();
                            }
                        }
                    });

                    dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                    dialog.show();

                } else {
                    final Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.contact_layout);
                    dialog.setCancelable(false);
                    txt_Yes = dialog.findViewById(R.id.txt_Yes);
                    txt_No = dialog.findViewById(R.id.txt_No);

                    txt_No.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    txt_Yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getLead.leadClickId(myListData.getId(), myListData.getMobile());
                            dialog.dismiss();
                        }
                    });

                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                    dialog.show();

                }
            }
        });

    }

    private void call_action(String mobile) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + mobile));
        context.startActivity(callIntent);
    }

    public boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (context.checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted");
                return true;
            } else {
                Log.v("TAG", "Permission is revoked");
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        } else {
            Log.v("TAG", "Permission is granted");
            return true;
        }
    }


    @Override
    public int getItemCount() {
        return leadList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_serviceDiscription, txt_serviceTime, txt_customerName, txt_category, txt_location, txt_countryName;
        ImageView img_comtactLayout,img_favorite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_serviceDiscription = itemView.findViewById(R.id.txt_serviceDiscription);
            txt_serviceTime = itemView.findViewById(R.id.txt_serviceTime);
            txt_customerName = itemView.findViewById(R.id.txt_customerName);
            img_comtactLayout = itemView.findViewById(R.id.img_comtactLayout);
            txt_category = itemView.findViewById(R.id.txt_category);
            txt_location = itemView.findViewById(R.id.txt_location);
            txt_countryName = itemView.findViewById(R.id.txt_countryName);
            img_favorite = itemView.findViewById(R.id.img_favorite);

        }
    }
}
