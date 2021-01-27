package com.example.mammoetsurvey;

import com.google.android.gms.maps.model.LatLng;

public class Route {
    private static Route INSTANCE;
    public String routeName;
    LatLng startPosition;
    LatLng endPosition;

    private Route() {
    }

    private Route(String routeName, LatLng startPosition, LatLng endPosition) {
        this.routeName = routeName;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    public static Route getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Route();
        }
        return INSTANCE;
    }

    public static Route getInstance(String routeName, LatLng startPosition, LatLng endPosition) {
        if (INSTANCE == null) {
            INSTANCE = new Route(routeName, startPosition, endPosition);
        }
        return INSTANCE;
    }
}
