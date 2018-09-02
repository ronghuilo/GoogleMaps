package com.example.mypc.googlemaps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.app.Dialog;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class NavActivity extends AppCompatActivity {

    private static final String TAG = "NavActivity";

    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_button);

        if(isServicesOK()){
            init();
        }
    }



    private void init(){
        Button btnMap = (Button) findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(NavActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

    }

    public boolean isServicesOK(){
       Log.d(TAG, "isServicesOK: checking google services version");

       int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(NavActivity.this);

       if(available == ConnectionResult.SUCCESS){
           //EVERYTHING IS FINE AND THE USER CAN MAKE MAP REQUEST
           Log.d(TAG, "isServicesOK: Google Play Services is working");
           return true;
       }
       else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
           //AN ERROR OCCURED BUT WE CAN RESOLVE IT
           Log.d(TAG, "isServicesOK: an error occured but we can fix it");
           Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(NavActivity.this,available, ERROR_DIALOG_REQUEST );
           dialog.show();
       }
       else{
           //CANT DO ANYTHING
           Toast.makeText(this, "We cant make map request", Toast.LENGTH_SHORT).show();
       }
       return false;
    }
}
