package com.example.mammoetsurvey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RouteActivity extends AppCompatActivity {
    Route selectedRoute;
    Button createRouteBtn;
    DatabaseReference routesRef;
    ListView listView;
    ArrayAdapter<String> adapter;
    List<String> list;
    double tmpLat, tmpLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        init();

        readRoutes();

        createRouteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(RouteActivity.this, AddRouteActivity.class);
                startActivity(intent);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedRoute = Route.getInstance(list.get(i));
                routesRef.child(selectedRoute.routeName).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        tmpLat = Double.parseDouble(String.valueOf(task.getResult().child("endPosition").child("latitude").getValue()));
                        tmpLng = Double.parseDouble(String.valueOf(task.getResult().child("endPosition").child("longitude").getValue()));
                        selectedRoute.endPosition = new LatLng(tmpLat, tmpLng);
                        tmpLat = Double.parseDouble(String.valueOf(task.getResult().child("startPosition").child("latitude").getValue()));
                        tmpLng = Double.parseDouble(String.valueOf(task.getResult().child("startPosition").child("longitude").getValue()));
                        selectedRoute.startPosition = new LatLng(tmpLat, tmpLng);
                    }
                });
                Intent intent = new Intent();
                intent.setClass(RouteActivity.this, PickImageDesc.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        createRouteBtn = findViewById(R.id.createroutebtn);
        listView = findViewById(R.id.routeslistview);
        list = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        routesRef = FirebaseDatabase.getInstance().getReference("routes");
    }

    private void readRoutes() {
        ValueEventListener routesListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (list.size() > 0) list.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Route tmp = ds.getValue(Route.class);
                    assert tmp != null;
                    list.add(tmp.routeName);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        routesRef.addValueEventListener(routesListener);
    }
}