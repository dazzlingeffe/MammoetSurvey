package com.example.mammoetsurvey;

import android.graphics.drawable.Drawable;
import android.media.Image;

public class Mark {
    public  String id, km, desc;
    public Drawable markOnMap, photo;

    public Mark(String id, String km, Drawable markOnMap, String desc, Drawable photo) {
        this.id = id;
        this.km = km;
        this.markOnMap = markOnMap;
        this.desc = desc;
        this.photo = photo;
    }
}
