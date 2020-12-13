package com.example.mammoetsurvey;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.os.Bundle;

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
//
//    public void OnClickSave(View view){
//        String id = mDataBase.getKey();
//        String Km = km.getText().toString();
//        String Desc = desc.getText().toString();
//        Image Photo = photo;
//
//    }
//
//    public void OnClickRead(View view){
//
//    }
}
