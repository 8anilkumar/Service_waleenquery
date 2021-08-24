package com.ennovation.servicewaleenquery.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ennovation.servicewaleenquery.MainActivity;
import com.ennovation.servicewaleenquery.R;
import com.ennovation.servicewaleenquery.Utils.GPSTracker;
import com.ennovation.servicewaleenquery.Utils.GpsUtils;
import com.ennovation.servicewaleenquery.Utils.YourPreference;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import java.util.List;
import java.util.Locale;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Dexter.withActivity(this).withPermissions(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.CALL_PHONE).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                turngpspon();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();

    }


    private void redirectionScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                GPSTracker mGPS = new GPSTracker(getApplicationContext());
                Double lat,lng;

                if (mGPS.canGetLocation) {
                    mGPS.getLocation();
                    lat = mGPS.getLatitude();
                    lng = mGPS.getLongitude();
                    getCompleteAddressString(lat,lng);

                } else {
                    Toast.makeText(mGPS, "location not Available", Toast.LENGTH_SHORT).show();
                }

                YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
                String id = yourPrefrence.getData("PARTNERID");

                if (id.equalsIgnoreCase("")||id.equalsIgnoreCase(null))
                {
                    startActivity(new Intent(getApplicationContext(), IntroPage.class));
                    finish();
                }
                else {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    yourPrefrence.saveData("isFramLauncher","true");
                    finish();
                }
            }
        }, 2000);
    }

    private void getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            String fullAddress = addresses.get(0).getAddressLine(0);
            String postalCode = addresses.get(0).getPostalCode();

            String[] Amount = fullAddress.split(",");
            String add1 = Amount[1];
            String add2 = Amount[2];
            String add3 = Amount[3];

            String address = add1+" "+add2;
            String location = address+" "+postalCode;

            YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
            yourPrefrence.saveData("currentLocation",location);

        } catch (Exception e) {
            e.printStackTrace();
          //  Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private  void  turngpspon() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(this)) {
            //  Toast.makeText(getApplicationContext(), "Gps already enabled", Toast.LENGTH_SHORT).show();
            redirectionScreen();
        } else {
            if (!hasGPSDevice(this)) {
                //   Toast.makeText(getApplicationContext(), "Gps not Supported", Toast.LENGTH_SHORT).show();
                finish();
            }
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(this)) {
                //     Toast.makeText(getApplicationContext(), "Gps not enabled", Toast.LENGTH_SHORT).show();
                new GpsUtils(this).turnGPSOn(new GpsUtils.OnGpsListener() {
                    @Override
                    public void gpsStatus(boolean isGPSEnable) {
                        if (isGPSEnable) {
                            redirectionScreen();
                        }
                    }
                });
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            if (resultCode == Activity.RESULT_OK) {
                redirectionScreen();
            } else {
                finish();
            }
        }
    }

    private boolean hasGPSDevice(Context context) {
        final LocationManager mgr = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (mgr == null)
            return false;
        final List<String> providers = mgr.getAllProviders();
        return providers.contains(LocationManager.GPS_PROVIDER);
    }


}