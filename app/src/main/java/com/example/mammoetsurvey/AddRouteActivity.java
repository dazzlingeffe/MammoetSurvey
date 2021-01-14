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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class AddRouteActivity extends AppCompatActivity implements OnMapReadyCallback {
    Route newRoute;
    Button saveRouteBtn;
    EditText routeName;
    GoogleMap gMap;
    LatLng startPosition;
    LatLng endPosition;
    ArrayList<LatLng> listpoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);

        listpoints = new ArrayList<>();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("routes");

        saveRouteBtn = findViewById(R.id.savebtn);
        routeName = findViewById(R.id.routenametextbox);

        saveRouteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newRoute = new Route(routeName.getText().toString(), startPosition, endPosition);

                dbRef.child(newRoute.routeName).setValue(newRoute);
                dbRef.child(newRoute.routeName).child("startPosition/latitude").setValue(startPosition.latitude);
                dbRef.child(newRoute.routeName).child("startPosition/longitude").setValue(startPosition.longitude);
                dbRef.child(newRoute.routeName).child("endPosition/latitude").setValue(endPosition.latitude);
                dbRef.child(newRoute.routeName).child("endPosition/longitude").setValue(endPosition.longitude);
                dbRef.push();

            }
        });
    }

    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if (listpoints.size()==2){
                    listpoints.clear();
                    gMap.clear();
                }
                listpoints.add(latLng);
                System.out.println(latLng);

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);

                if (listpoints.size()==1){
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    startPosition = listpoints.get(0);
                } else {
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    endPosition = listpoints.get(1);
                }
                gMap.addMarker(markerOptions);
            }
        });

        // show current location



//        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//                MarkerOptions markerOptions = new MarkerOptions();
//                markerOptions.position(latLng);
//                markerOptions.title("Start");
//                gMap.clear();
//                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
//                gMap.addMarker(markerOptions);
//                startPosition = latLng;
//            }
//        });


//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        gMap.addMarker(new MarkerOptions()
//                .position(sydney)
//                .title("Marker in Sydney"));
//        gMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}