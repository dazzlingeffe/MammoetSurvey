package com.example.mammoetsurvey;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddRouteActivity extends AppCompatActivity {
    Route newRoute;
    Button saveRouteBtn;
    EditText routeName;
    LatLng startPosition;
    LatLng endPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("routes");

        saveRouteBtn = findViewById(R.id.savebtn);
        routeName = findViewById(R.id.routenametextbox);

        saveRouteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newRoute = new Route(routeName.getText().toString());

                dbRef.push().setValue(newRoute);
            }
        });
    }
}