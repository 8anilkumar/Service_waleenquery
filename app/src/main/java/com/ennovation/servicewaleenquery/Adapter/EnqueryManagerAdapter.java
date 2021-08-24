package com.ennovation.servicewaleenquery.Adapter;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.ennovation.servicewaleenquery.Model.EnqueryManagerResponse;
import com.ennovation.servicewaleenquery.Model.LeadEnquiry.YourLeadEnquiryResponseData;
import com.ennovation.servicewaleenquery.R;

import java.util.List;

public class EnqueryManagerAdapter extends RecyclerView.Adapter<EnqueryManagerAdapter.ViewHolder>{

    Context context;
    TextView txt_serviceDiscription,txt_serviceProviderName,txt_productName,txt_From;
    LinearLayout call_linearLayout;
    List<YourLeadEnquiryResponseData> youeleadList;

    public EnqueryManagerAdapter(List<YourLeadEnquiryResponseData> youeleadList, FragmentActivity activity) {
        this.youeleadList = youeleadList;
        this.context = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.enquery_manager_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final YourLeadEnquiryResponseData myListData = youeleadList.get(position);
        holder.txt_serviceDiscription.setText(myListData.getDescriptions());
        holder.txt_serviceProviderName.setText(myListData.getName());
        holder.txt_category.setText(myListData.getCategory());
        holder.txt_time.setText(myListData.getCreatedAt());
        holder.txt_location.setText(myListData.getCity()+ " " +myListData.getState());
        holder.img_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPermissionGranted()) {
                    call_action(myListData.getMobile());
                }
            }
        });

//        holder.leadManagerLinearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Dialog dialog = new Dialog(context);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setContentView(R.layout.service_list_layout);
//
//                txt_serviceProviderName = dialog.findViewById(R.id.txt_serviceProviderName);
//                txt_serviceDiscription = dialog.findViewById(R.id.txt_serviceDiscription);
//                txt_productName = dialog.findViewById(R.id.txt_productName);
//                txt_From = dialog.findViewById(R.id.txt_From);
//                call_linearLayout = dialog.findViewById(R.id.call_linearLayout);
//                txt_serviceProviderName.setText(myListData.getName());
//                txt_serviceDiscription.setText(myListData.getDescriptions());
//                txt_productName.setText(myListData.getCategory());
//                txt_From.setText(myListData.getCompleteAddress());
//
//                call_linearLayout.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                            if (context.checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
//                                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + myListData.getMobile()));
//                                context.startActivity(callIntent);
//                            } else {
//                                Toast.makeText(context, "Please allow phone call permission", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//                });
//
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
//                dialog.show();
//
//            }
//        });

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
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG", "Permission is granted");
            return true;
        }
    }

    public void call_action(String mobile) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" +mobile ));
        context.startActivity(callIntent);

    }


    @Override
    public int getItemCount() {
        return youeleadList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_serviceProviderName,txt_serviceDiscription,txt_category,txt_time,txt_location;
        LinearLayout leadManagerLinearLayout;
        ImageView img_call;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_serviceProviderName = itemView.findViewById(R.id.txt_serviceProviderName);
            txt_serviceDiscription = itemView.findViewById(R.id.txt_serviceDiscription);
            txt_category = itemView.findViewById(R.id.txt_category);
            txt_time = itemView.findViewById(R.id.txt_time);
            img_call = itemView.findViewById(R.id.img_call);
            txt_location = itemView.findViewById(R.id.txt_location);
            leadManagerLinearLayout = itemView.findViewById(R.id.leadManagerLinearLayout);

        }
    }
}
