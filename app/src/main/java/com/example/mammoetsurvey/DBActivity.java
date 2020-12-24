package com.example.mammoetsurvey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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

public class DBActivity extends AppCompatActivity {
    private EditText km, desc;
    private ImageView photo, mapScreeshot;
    private DatabaseReference mDataBase;
    private String mark = "Mark";
    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init() {
        id = mDataBase.getKey();
        km = findViewById(R.id.desc);
        desc = findViewById(R.id.desc);
        photo = findViewById(R.id.chooseph);
        mapScreeshot = findViewById(R.id.chooseph);
        mDataBase = FirebaseDatabase.getInstance().getReference(mark);
    }

    private void writeNewMark(String markId, EditText km, EditText desc, ImageView mapScreeshot, ImageView photo) {
        Mark mark = new Mark(markId, km.getText().toString(), mapScreeshot.getDrawable(), desc.getText().toString(), photo.getDrawable());

        mDataBase.child("marks").child(markId).setValue(mark);
    }
}
