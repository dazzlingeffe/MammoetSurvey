package com.example.mammoetsurvey;

import android.media.Image;
import android.widget.ImageView;

public class Metka {
    public  String id, km, desc;
    public  Image  metka_na_karte, photo;

    public Metka() {
    }

    public Metka(String id, String km, Image metka_na_karte, String desc, Image photo) {
        this.id = id;
        this.km = km;
        this.metka_na_karte = metka_na_karte;
        this.desc = desc;
        this.photo = photo;
    }
}
