package com.example.mammoetsurvey;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;

public class ConfirmationActivity extends AppCompatActivity implements OnMapReadyCallback {
    Mark newMark;
    Route newRoute;
    GoogleMap gMap;
    TextView km;
    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapConfirm);
        mapFragment.getMapAsync(this);

        init();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newRoute.pushRoute();
                newMark.uploadImage();
                newMark.pushMark();
            }
        });
    }

    private void init() {
        newMark = Mark.getInstance();
        newRoute = Route.getInstance();
        km = findViewById(R.id.km);
        saveBtn = findViewById(R.id.savebtn);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        gMap.addMarker(new MarkerOptions().position(newRoute.startPosition).title(newRoute.routeName + ": start"));
        gMap.addMarker(new MarkerOptions().position(newRoute.endPosition).title(newRoute.routeName + ": end"));
    }
}