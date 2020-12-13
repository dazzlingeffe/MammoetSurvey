package com.example.mammoetsurvey;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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


    ImageView chooseph;
    Button choosebt;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        chooseph = findViewById(R.id.chooseIV);
        choosebt = findViewById(R.id.choosePhoto);

        choosebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                }
            }
        });
        setContentView(R.layout.activity_main);
        init();
    }

    public void init(){
        id = mDataBase.getKey();
        km = findViewById(R.id.km);
        desc = findViewById(R.id.desc);
        photo = findViewById(R.id.photo);
        mapScreeshot = findViewById(R.id.scr);
        mDataBase = FirebaseDatabase.getInstance().getReference(mark);
    }

    private void writeNewMark(String markId, EditText km, EditText desc, ImageView mapScreeshot, ImageView photo) {
        Mark mark = new Mark(markId, km.getText().toString(), mapScreeshot.getDrawable(), desc.getText().toString(), photo.getDrawable());

        mDataBase.child("marks").child(markId).setValue(mark);
    }
}
