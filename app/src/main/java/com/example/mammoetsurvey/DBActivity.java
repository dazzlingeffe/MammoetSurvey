package com.example.mammoetsurvey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class DBActivity extends AppCompatActivity {
    private EditText km, desc;
    private ImageView photo, mapScreeshot;
    private DatabaseReference mDataBase;
    private String mark = "Mark";
    private String id;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.read_layout);
        init();
        readMarks();
    }

    public void init() {
        id = mDataBase.getKey();
        km = findViewById(R.id.desc);
        desc = findViewById(R.id.desc);
        photo = findViewById(R.id.chooseph);
        mapScreeshot = findViewById(R.id.chooseph);
        mDataBase = FirebaseDatabase.getInstance().getReference(mark);
        listView = findViewById(R.id.listView);
        listData = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);
    }

    private void writeNewMark(String markId, EditText km, EditText desc, ImageView mapScreeshot, ImageView photo) {
        Mark mark = new Mark(markId, km.getText().toString(), mapScreeshot.getDrawable(), desc.getText().toString(), photo.getDrawable());

        mDataBase.child("marks").child(markId).setValue(mark);
    }
    private void readMarks() {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (listData.size() > 0) listData.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Mark mark = ds.getValue(Mark.class);
                    assert mark != null;
                    listData.add(mark.id);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDataBase.addValueEventListener(listener);
    }
}
