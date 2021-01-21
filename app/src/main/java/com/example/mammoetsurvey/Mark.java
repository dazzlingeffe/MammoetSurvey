package com.example.mammoetsurvey;

import android.graphics.drawable.Drawable;

public class Mark {
    public String km, desc, route;
    public Drawable markOnMap;
    public String photo;

    public Mark() {

    }

    public Mark(String km, Drawable markOnMap, String desc, String photo) {
        this.km = km;
        this.markOnMap = markOnMap;
        this.desc = desc;
        this.photo = photo;
    }
}
