package com.ennovation.servicewaleenquery.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ennovation.servicewaleenquery.InterFace.ServiceInterface;
import com.ennovation.servicewaleenquery.MainActivity;
import com.ennovation.servicewaleenquery.Model.loginResponse;
import com.ennovation.servicewaleenquery.R;
import com.ennovation.servicewaleenquery.Utils.ApiClient;
import com.ennovation.servicewaleenquery.Utils.Helper;
import com.ennovation.servicewaleenquery.Utils.YourPreference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;

public class IntroPage extends AppCompatActivity {
    RelativeLayout txt_loginLayout;
    EditText ed_mobileNumber;
    ProgressBar mainProgressbar;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intropage);

        FirebaseApp.initializeApp(this);

        txt_loginLayout = findViewById(R.id.txt_loginLayout);
        ed_mobileNumber = findViewById(R.id.ed_mobileNumber);
        mainProgressbar = findViewById(R.id.mainProgressbar);


        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            token = task.getResult().getToken();
                        } else {
                            token = "";
                        }
                    }
                });


        txt_loginLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_mobileNumber.getText().toString().equalsIgnoreCase("")) {
                    ed_mobileNumber.setError("*Required");
                } else if (ed_mobileNumber.getText().toString().length() != 10) {
                    ed_mobileNumber.setError("Required 10 Digit Number");
                } else {
                    if (Helper.INSTANCE.isNetworkAvailable(IntroPage.this)) {
                        signUpWithOTP();
                    } else {
                        Helper.INSTANCE.Error(IntroPage.this, getString(R.string.NOCONN));
                    }
                }
            }
        });
    }

    private void doLogin() {
        HashMap<String, String> map = new HashMap<>();
        mainProgressbar.setVisibility(View.VISIBLE);
        map.put("mobile", ed_mobileNumber.getText().toString());
        map.put("device_id", token);

        ServiceInterface serviceInterface = ApiClient.getClient().create(ServiceInterface.class);
        Call<loginResponse> call = serviceInterface.doLogin(map);
        call.enqueue(new Callback<loginResponse>() {
            @Override
            public void onResponse(Call<loginResponse> call, retrofit2.Response<loginResponse> response) {
                if (response.isSuccessful()) {
                    mainProgressbar.setVisibility(View.GONE);
                    String status = response.body().getStatus().toString();
                    if (status.equals("1")) {
                        YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
                        yourPrefrence.saveData("USERNAME", response.body().getName());
                        yourPrefrence.saveData("PROFESSION", response.body().getProfession());
                        yourPrefrence.saveData("STATEID", response.body().getStateId());
                        yourPrefrence.saveData("STATE", response.body().getStateName());
                        yourPrefrence.saveData("CITY", response.body().getCity());
                        yourPrefrence.saveData("PARTNERID", response.body().getPartnerId());
                        yourPrefrence.saveData("PROFILEPHOTO", response.body().getProfilePhoto());
                        yourPrefrence.saveData("isFramLauncher","true");

                        Intent intent = new Intent(IntroPage.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(IntroPage.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    mainProgressbar.setVisibility(View.GONE);
                    Toast.makeText(IntroPage.this, "Something is wrong try again later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<loginResponse> call, Throwable t) {
                Log.d("ff", t.toString());
                mainProgressbar.setVisibility(View.GONE);
            }
        });
    }

    private void signUpWithOTP() {
        if (token != null && !token.equalsIgnoreCase("")) {
            doLogin();
        } else {
            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                token = task.getResult().getToken();
                                signUpWithOTP();
                            } else {
                                token = "";
                            }
                        }
                    });
        }
    }


}