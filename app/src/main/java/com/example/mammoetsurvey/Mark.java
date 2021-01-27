package com.example.mammoetsurvey;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Mark {
    private static Mark INSTANCE;
    public String km, desc, route, markOnMap, photo, obstacleType;
    long id;
    DatabaseReference marksRef = FirebaseDatabase.getInstance().getReference().child("marks");


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

    public void addMarkToDB() {
        marksRef.child(String.valueOf(id)).setValue(this);
    }

    public void pushMark() {
        marksRef.push();
    }
}
