package com.example.dylan.checkout;


import java.util.Map;
import java.util.HashMap;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.view.ViewTreeObserver;
import android.content.Intent;
import android.app.Activity;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ServerValue;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, ChildEventListener {

    private com.google.android.gms.common.api.GoogleApiClient mGoogleApiClient;
    private static final int REQUEST_PLACE_PICKER = 1;
    private GoogleMap mMap;
    private LatLngBounds.Builder mBounds = new LatLngBounds.Builder();

    private static final String FIREBASE_URL = "https://firebase-adminsdk-zzs59@root-furnace-204804.iam.gserviceaccount.com";
    private static final String FIREBASE_ROOT_NODE = "checkouts";
    private Firebase mFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Set up Google Maps
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Set up the API client for Places API
        mGoogleApiClient = new com.google.android.gms.common.api.GoogleApiClient.Builder(this)
        .addApi(com.google.android.gms.location.places.Places.GEO_DATA_API).build();
        mGoogleApiClient.connect();

        // Set up Firebase
        Firebase.setAndroidContext(this);
        mFirebase = new Firebase(FIREBASE_URL);
        mFirebase.child(FIREBASE_ROOT_NODE).addChildEventListener(this);
    }

    /**
     * Map setup. This is called when the GoogleMap is available to manipulate.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Pad the map controls to make room for the button - note that the button may not have
        // been laid out yet.
        final Button button = (Button) findViewById(R.id.checkout_button);
        button.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mMap.setPadding(0, button.getHeight(), 0, 0);
                    }
                }
        );

        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                addPointToViewPort(ll);
                // we only want to grab the location once, to allow the user to pan and zoom freely.
                mMap.setOnMyLocationChangeListener(null);
            }
        });
    }

    private void addPointToViewPort(LatLng newPoint) {
        mBounds.include(newPoint);
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(mBounds.build(),
                findViewById(R.id.checkout_button).getHeight()));
    }

    /**
    * Prompt the user to check out of their location. Called when the "Check Out!" button
    * is clicked.
    */
    public void checkOut(android.view.View view) {
        try {
            com.google.android.gms.location.places.ui.PlacePicker.IntentBuilder intentBuilder = new com.google.android.gms.location.places.ui.PlacePicker.IntentBuilder();
            android.content.Intent intent = intentBuilder.build(this);
            startActivityForResult(intent, REQUEST_PLACE_PICKER);
        } catch (com.google.android.gms.common.GooglePlayServicesRepairableException e) {
            com.google.android.gms.common.GoogleApiAvailability.getInstance().getErrorDialog(this, e.getConnectionStatusCode(),
               REQUEST_PLACE_PICKER);
        } catch (com.google.android.gms.common.GooglePlayServicesNotAvailableException e) {
            android.widget.Toast.makeText(this, "Please install Google Play Services!", android.widget.Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PLACE_PICKER) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);

                Map<String, Object> checkoutData = new HashMap<>();
                checkoutData.put("time", ServerValue.TIMESTAMP);

                mFirebase.child(FIREBASE_ROOT_NODE).child(place.getId()).setValue(checkoutData);

            } else if (resultCode == PlacePicker.RESULT_ERROR) {
                Toast.makeText(this, "Places API failure! Check the API is enabled for your key",
                        Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        String placeId = dataSnapshot.getKey();
        if (placeId != null) {
            Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId)
                    .setResultCallback(new ResultCallback<PlaceBuffer>() {
                                           @Override
                                           public void onResult(PlaceBuffer places) {
                                               LatLng location = places.get(0).getLatLng();
                                               addPointToViewPort(location);
                                               mMap.addMarker(new MarkerOptions().position(location));
                                               places.release();
                                           }
                                       }
                    );
        }
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

    }
}