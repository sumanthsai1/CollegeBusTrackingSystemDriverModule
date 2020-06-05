package com.example.cbts1;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;

import static com.example.cbts1.R.drawable.busicon;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{
    private DatabaseReference databaseReference;
    private final long MIN_TIME = 1000;
    private final long MIN_DIST = 5;
    private GoogleMap mMap;
    LocationManager locationManager;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    Marker marker;

    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        databaseReference = FirebaseDatabase.getInstance().getReference("Location");
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);

        }
        else{


            locationListener = new LocationListener() {
                @Override

                public void onLocationChanged(Location location) {
                    LocationRequest locationRequest = LocationRequest.create();
                    locationRequest.setInterval(10000);
                    locationRequest.setFastestInterval(50000);
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    //get the location name from latitude and longitude
                    int height = 100;
                    int width = 100;
                    Bitmap b = BitmapFactory.decodeResource(getResources(), busicon);
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                    BitmapDescriptor smallMarkerIcon = BitmapDescriptorFactory.fromBitmap(smallMarker);
                    LocationManager locationManager = (LocationManager)
                            getSystemService(Context.LOCATION_SERVICE);



                    LocationHelper helper=new LocationHelper(location.getLatitude(),location.getLongitude());
                    FirebaseDatabase.getInstance().getReference("Location").setValue(helper);


                    LatLng latLng = new LatLng(latitude, longitude);
                    if (marker != null){
                        marker.remove();
                       // new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.icon));

                        marker = mMap.addMarker(new MarkerOptions().position(latLng).title("Bus Location").icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                        mMap.setMaxZoomPreference(20);
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
                        mMap.animateCamera(cameraUpdate);
                    }
                    else{
                       // marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.busicon));
                        //new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.icon));

                        marker = mMap.addMarker(new MarkerOptions().position(latLng).title("Bus Location").icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));

                        mMap.setMaxZoomPreference(20);
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
                        mMap.animateCamera(cameraUpdate);                        }


                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){

            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    //get the location name from latitude and longitude
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    LocationHelper helper=new LocationHelper(location.getLatitude(),location.getLongitude());
                    FirebaseDatabase.getInstance().getReference("Location").setValue(helper);
                    try {
                        List<Address> addresses =
                                geocoder.getFromLocation(latitude, longitude, 1);
                        String result = addresses.get(0).getLocality()+":";
                        result += addresses.get(0).getCountryName();
                        LatLng latLng = new LatLng(latitude, longitude);
                        if (marker != null){
                            marker.remove();
                            marker = mMap.addMarker(new MarkerOptions().position(latLng).title(result));
                            mMap.setMaxZoomPreference(20);
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
                            mMap.animateCamera(cameraUpdate);                        }
                        else{
                            marker = mMap.addMarker(new MarkerOptions().position(latLng).title(result));
                            mMap.setMaxZoomPreference(20);
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
                            mMap.animateCamera(cameraUpdate);                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(locationListener);
    }
}
