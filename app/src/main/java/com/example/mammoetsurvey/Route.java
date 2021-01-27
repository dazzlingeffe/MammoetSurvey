package com.example.mammoetsurvey;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Route {
    private static Route INSTANCE;
    public String routeName;
    LatLng startPosition;
    LatLng endPosition;
    DatabaseReference routesRef = FirebaseDatabase.getInstance().getReference("routes");

    private Route() {

    }

    private Route(String routeName) {
        this.routeName = routeName;
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
    public static Route getInstance(String routeName) {
        if (INSTANCE == null) {
            INSTANCE = new Route(routeName);
        }
        return INSTANCE;
    }

    public static Route getInstance(String routeName, LatLng startPosition, LatLng endPosition) {
        if (INSTANCE == null) {
            INSTANCE = new Route(routeName, startPosition, endPosition);
        }
        return INSTANCE;
    }

    public void addRouteToDB() {
        routesRef.child(routeName).setValue(this);
        routesRef.child(routeName).child("startPosition/latitude").setValue(startPosition.latitude);
        routesRef.child(routeName).child("startPosition/longitude").setValue(startPosition.longitude);
        routesRef.child(routeName).child("endPosition/latitude").setValue(endPosition.latitude);
        routesRef.child(routeName).child("endPosition/longitude").setValue(endPosition.longitude);
    }
    public void pushRoute() {
        routesRef.push();
    }
}
