package com.example.mammoetsurvey;

public class Mark {
    private static Mark INSTANCE;
    public String km, desc, route, markOnMap;
    public String photo;

    private Mark() {

    }

    private Mark(String km, String markOnMap, String desc, String photo) {
        this.km = km;
        this.markOnMap = markOnMap;
        this.desc = desc;
        this.photo = photo;
    }

    public static Mark getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Mark();
        }
        return INSTANCE;
    }
    public static Mark getInstance(String km, String markOnMap, String desc, String photo) {
        if (INSTANCE == null) {
            INSTANCE = new Mark(km, markOnMap, desc, photo);
        }
        return INSTANCE;
    }
}
