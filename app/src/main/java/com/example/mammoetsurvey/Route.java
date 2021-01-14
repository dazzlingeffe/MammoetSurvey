package com.example.mammoetsurvey;

import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;

public class Route {
    public String routeName;
    LatLng startPosition;
    LatLng endPosition;

    public Route(String routeName, LatLng startPosition, LatLng endPosition) {
        this.routeName = routeName;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }
}
