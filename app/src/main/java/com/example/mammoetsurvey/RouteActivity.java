package com.example.mammoetsurvey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RouteActivity extends AppCompatActivity {
    public static Mark newMark;
    Button createRouteBtn;
    DatabaseReference routesRef;
    ListView listView;
    ArrayAdapter<String> adapter;
    List<String> list;
    ValueEventListener routesListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        createRouteBtn = findViewById(R.id.createroutebtn);
        listView = findViewById(R.id.routeslistview);
        list = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        routesRef = FirebaseDatabase.getInstance().getReference("routes");

        routesListener = new ValueEventListener() {
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
        createRouteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(RouteActivity.this, AddRouteActivity.class);
                startActivity(intent);
            }
        });
    }
}