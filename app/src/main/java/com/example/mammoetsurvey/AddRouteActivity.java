package com.example.mammoetsurvey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;

public class AddRouteActivity extends AppCompatActivity implements OnMapReadyCallback {
    Route newRoute;
    Button saveRouteBtn;
    EditText routeName;
    GoogleMap gMap;
    LatLng startPosition;
    LatLng endPosition;
    ArrayList<LatLng> listpoints;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);

        listpoints = new ArrayList<>();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapConfirm);
        mapFragment.getMapAsync(this);

        dbRef = FirebaseDatabase.getInstance().getReference("routes");

        saveRouteBtn = findViewById(R.id.savebtn);
        routeName = findViewById(R.id.routenametextbox);
        saveRouteBtn.setEnabled(false);
        saveRouteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(AddRouteActivity.this, PickImageDesc.class);
                newRoute = Route.getInstance(routeName.getText().toString(), startPosition, endPosition);
// this is in Route.java now
//                dbRef.child(newRoute.routeName).setValue(newRoute);
//                dbRef.child(newRoute.routeName).child("startPosition/latitude").setValue(startPosition.latitude);
//                dbRef.child(newRoute.routeName).child("startPosition/longitude").setValue(startPosition.longitude);
//                dbRef.child(newRoute.routeName).child("endPosition/latitude").setValue(endPosition.latitude);
//                dbRef.child(newRoute.routeName).child("endPosition/longitude").setValue(endPosition.longitude);
//                newRoute.addRouteToDB();
                //
//                newRoute.pushRoute();
//                dbRef.push();
                startActivity(intent);
            }
        });
    }

    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if (listpoints.size() == 2) {
                    listpoints.clear();
                    gMap.clear();
                }
                listpoints.add(latLng);
                System.out.println(latLng);

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);

                if (listpoints.size() == 1) {
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    startPosition = listpoints.get(0);
                } else {
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    endPosition = listpoints.get(1);
                    saveRouteBtn.setEnabled(routeName.length() != 0);
                    routeName.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            saveRouteBtn.setEnabled(charSequence.length() != 0);
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });
                }
                gMap.addMarker(markerOptions);
            }
        });
    }
}