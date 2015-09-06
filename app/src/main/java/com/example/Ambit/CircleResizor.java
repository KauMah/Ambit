package com.example.Ambit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;


/**
 * This shows how to draw circles on a map.
 */
public class CircleResizor extends FragmentActivity implements OnSeekBarChangeListener,
        OnMarkerDragListener, OnMapLongClickListener, OnMapReadyCallback {

    public static final double RADIUS_OF_EARTH_METERS = 6371009;
    private static final int DEFAULT_ALPHA = 80;
    private static final int SIZE_MAX = 30000;
    private static final double DEFAULT_RADIUS = SIZE_MAX / 2;
    private static double pastRadius = DEFAULT_RADIUS;
    private int peopleWithinRadius = 0;
    private ArrayList<LatLng> othersGeoPositions = new ArrayList<LatLng>();
    private LatLng myGeoPosition;
    private GoogleMap mMap;

    private List<DraggableCircle> mCircles = new ArrayList<DraggableCircle>(1);

    private SeekBar mColorBar;
    private SeekBar mSizeBar;
    private int mStrokeColor;
    private int mFillColor;
    private int[] otherFillColor = new int[6];//number ofpeople who join the room

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circle_resizor);

        mColorBar = (SeekBar) findViewById(R.id.hueSeekBar);
        mColorBar.setMax(360);
        mColorBar.setProgress(60);

        mSizeBar = (SeekBar) findViewById(R.id.widthSeekBar);
        mSizeBar.setMax(SIZE_MAX);
        mSizeBar.setProgress(SIZE_MAX / 2);

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();


        othersGeoPositions.add(new LatLng(latitude + .1, longitude + .1));
        othersGeoPositions.add(new LatLng(latitude - .1, longitude - .1));
        othersGeoPositions.add(new LatLng(latitude, longitude + .2));
        othersGeoPositions.add(new LatLng(latitude, longitude - .2));
        othersGeoPositions.add(new LatLng(latitude, longitude + .15));
        othersGeoPositions.add(new LatLng(latitude - .075, longitude + .25));


        myGeoPosition = new LatLng(latitude, longitude);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    public void joinChat(View view){
        Intent intent = new Intent(this, ChatActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        Intent oldIntent = getIntent();
        intent.putExtra("username", oldIntent.getStringExtra("username"));
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        map.setContentDescription("Google Map with circles.");

        mColorBar.setOnSeekBarChangeListener(this);
        mSizeBar.setOnSeekBarChangeListener(this);
        mMap.setOnMarkerDragListener(this);
        mMap.setOnMapLongClickListener(this);

        mFillColor = Color.HSVToColor(
                DEFAULT_ALPHA, new float[]{mColorBar.getProgress(), 1, 1});
        for (int i = 0; i < otherFillColor.length; i++)
            otherFillColor[i] = Color.HSVToColor(DEFAULT_ALPHA / 2, new float[]{240, 1, 1});
        mStrokeColor = Color.BLACK;

        DraggableCircle circle = new DraggableCircle(myGeoPosition);
        mCircles.add(circle);

        // Move the map so that it is centered on the initial circle
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myGeoPosition, 9.0f));
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // Don't do anything here.
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // Don't do anything here.
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar == mColorBar) {
            mFillColor = Color.HSVToColor(Color.alpha(mFillColor), new float[]{progress, 1, 1});
        }/* else if (seekBar == mAlphaBar) {
            mFillColor = Color.argb(progress, Color.red(mFillColor), Color.green(mFillColor),
                  Color.blue(mFillColor));
        }*/

        for (DraggableCircle draggableCircle : mCircles) {
            draggableCircle.onStyleChange();
        }
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        onMarkerMoved(marker);
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        onMarkerMoved(marker);
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        onMarkerMoved(marker);
    }

    private void onMarkerMoved(Marker marker) {
    }

    @Override
    public void onMapLongClick(LatLng point) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myGeoPosition, 9.0f));
    }

    private class DraggableCircle {
        private final Circle circle;
        private ArrayList<Circle> otherCircles = new ArrayList<Circle>();

        public DraggableCircle(LatLng center) {
            mMap.addMarker(new MarkerOptions()
                    .position(center)
                    .draggable(false));
            for (int i = 0; i < othersGeoPositions.size(); i++) {
               Circle tempCircle = mMap.addCircle(new CircleOptions()
                        .center(othersGeoPositions.get(i))
                        .radius(DEFAULT_RADIUS)
                        .strokeWidth(0)
                        .fillColor(otherFillColor[i]));
                otherCircles.add(tempCircle);
            }
            circle = mMap.addCircle(new CircleOptions()
                    .center(center)
                    .radius(DEFAULT_RADIUS)
                    .strokeWidth(7)
                    .strokeColor(mStrokeColor)
                    .fillColor(mFillColor));
        }

        public void onStyleChange() {
            circle.setFillColor(mFillColor);
            circle.setStrokeColor(mStrokeColor);
            if (mSizeBar.getProgress() != pastRadius) {
                circle.setRadius(mSizeBar.getProgress());
                float[] results = new float[1];
                for (int i = 0; i < othersGeoPositions.size(); i++) {
                    Location.distanceBetween(myGeoPosition.latitude, myGeoPosition.longitude,
                            othersGeoPositions.get(i).latitude, othersGeoPositions.get(i).longitude, results);
                    if (results[0] > mSizeBar.getProgress()+otherCircles.get(i).getRadius()) {
                        otherCircles.get(i).setFillColor(Color.HSVToColor(DEFAULT_ALPHA / 2, new float[]{0, 1, 1}));
                    }else if (results[0] > mSizeBar.getProgress()) {
                        otherCircles.get(i).setFillColor(Color.HSVToColor(DEFAULT_ALPHA / 2, new float[]{240, 1, 1}));
                    }else {
                        otherCircles.get(i).setFillColor(Color.HSVToColor(DEFAULT_ALPHA / 2, new float[]{120, 1f, .5f}));
                    }
                }
                pastRadius = mSizeBar.getProgress();
            }

            circle.setStrokeWidth(10);
        }
    }
}
