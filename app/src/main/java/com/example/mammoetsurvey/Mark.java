package com.example.mammoetsurvey;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class Mark {
    private static Mark INSTANCE;
    ImageView obstacleFilepath, screenshotFilepath;
    public String km, desc, route, screenshot, photo, obstacleType;
    long id;
    DatabaseReference marksRef = FirebaseDatabase.getInstance().getReference().child("marks");
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference("obstacles");
    public Uri uploadUri;

    private Mark() {

    }

//    private Mark(String km, String markOnMap, String desc, String photo) {
//        this.km = km;
//        this.markOnMap = markOnMap;
//        this.desc = desc;
//        this.photo = photo;
//    }

    public static Mark getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Mark();
        }
        return INSTANCE;
    }

//    public static Mark getInstance(String km, String markOnMap, String desc, String photo) {
//        if (INSTANCE == null) {
//            INSTANCE = new Mark(km, markOnMap, desc, photo);
//        }
//        return INSTANCE;
//    }

    public void addMarkToDB() { marksRef.child(String.valueOf(id)).setValue(this); }

    public void pushMark() {
        marksRef.push();
    }

    public void uploadImage(){
        Bitmap bitmap = ((BitmapDrawable) obstacleFilepath.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50, baos);
        byte[] byteArray = baos.toByteArray();
        final StorageReference mRef = mStorageRef.child("obstacle_id" + (id + 1));
        UploadTask up = mRef.putBytes(byteArray);
        Task<Uri> task = up.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return mRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                photo = task.getResult().toString();
                Log.d("LogHueg","Image HUY:" + photo);
            }
        });
    }
}
