package com.example.mammoetsurvey;

import android.media.Image;

public class Mark {
    public  String id, km, desc;
    public  Image markOnMap, photo;

    public Mark(String id, String km, Image markOnMap, String desc, Image photo) {
        this.id = id;
        this.km = km;
        this.markOnMap = markOnMap;
        this.desc = desc;
        this.photo = photo;
    }
}
