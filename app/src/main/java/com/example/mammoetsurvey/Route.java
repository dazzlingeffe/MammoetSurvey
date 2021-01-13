package com.example.mammoetsurvey;

import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;

public class Route {
    public String routeName;
    LatLng startPosition;
    LatLng endPosition;

    public Route(String routeName) {
        this.routeName = routeName;
        // Add start and end to constructor parameters
//        this.startPosition = startPosition;
//        this.endPosition = endPosition;
    }
}
