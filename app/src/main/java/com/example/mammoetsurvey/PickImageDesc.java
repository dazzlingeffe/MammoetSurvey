package com.example.mammoetsurvey;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;

public class PickImageDesc extends AppCompatActivity {
    Mark newMark;
    ImageView chooseph;
    EditText description;
    Button choosebt, nextBtn;
    DatabaseReference marksRef;
    long maxid = 0;
    public Uri uploadUri;
    Boolean ready1;
    private StorageReference mStorageRef;
    String[] data = {"ЛЭП","Дорожные знаки", "Деревья","Металлоконструкции","Другое"};

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_image_desc);

        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        // заголовок
        spinner.setPrompt("Title");
        // выделяем элемент
        spinner.setSelection(2);
        // устанавливаем обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                newMark.obstacleType = data[i];
                Toast.makeText(getBaseContext(), "Position = " + i, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        init();
        nextBtn.setEnabled(false);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newMark.desc = description.getText().toString();
                newMark.photo = uploadUri.toString();
                newMark.id = maxid + 1;
                newMark.route = Route.getInstance().routeName;
//this is in Mark.java now
//                marksRef.child(String.valueOf(maxid + 1)).setValue(newMark);
//                marksRef.push();
                newMark.addMarkToDB();
                newMark.pushMark();
            }
        });
        choosebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImageFromGallery();
            }
        });
    }

    void checkReady(){
        nextBtn.setEnabled(ready1);
    }

    private void uploadImage(){
        Bitmap bitmap = ((BitmapDrawable) chooseph.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] byteArray = baos.toByteArray();
        final StorageReference mRef = mStorageRef.child("obstacle_id" + maxid + 1);
        UploadTask up = mRef.putBytes(byteArray);
        Task<Uri> task = up.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return mRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                uploadUri = task.getResult();
                Log.d("LogHueg","Image HUY:" + task.getResult());
            }
        });
    }

    private void init() {
        marksRef = FirebaseDatabase.getInstance().getReference("marks");
        chooseph = findViewById(R.id.chooseimage);
        choosebt = findViewById(R.id.choosebutton);
        nextBtn = findViewById(R.id.button3);
        description = (EditText)findViewById(R.id.descr);
        mStorageRef = FirebaseStorage.getInstance().getReference("obstacles");
        newMark = Mark.getInstance();

        marksRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    maxid=(snapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            chooseph.setImageURI(data.getData());
            nextBtn.setEnabled(description.getText().toString().length() != 0);
            description.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    ready1 = charSequence.length() != 0;
                    checkReady();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            uploadImage();
        }
    }
}