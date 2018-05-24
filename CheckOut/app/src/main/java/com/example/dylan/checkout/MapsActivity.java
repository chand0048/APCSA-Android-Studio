package com.example.dylan.checkout;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.view.ViewTreeObserver;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private com.google.android.gms.common.api.GoogleApiClient mGoogleApiClient;
    private static final int REQUEST_PLACE_PICKER = 1;
    private GoogleMap mMap;
    private LatLngBounds.Builder mBounds = new LatLngBounds.Builder();

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
    protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
        if (requestCode == REQUEST_PLACE_PICKER) {
            if (resultCode == android.app.Activity.RESULT_OK) {
                com.google.android.gms.location.places.Place place = com.google.android.gms.location.places.ui.PlacePicker.getPlace(data, this);
            } else if (resultCode == com.google.android.gms.location.places.ui.PlacePicker.RESULT_ERROR) {
                android.widget.Toast.makeText(this, "Places API failure! Check that the API is enabled for your key",
                    android.widget.Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}