package com.mirea.saburov.mireaproject.ui.map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mirea.saburov.mireaproject.R;

import org.jetbrains.annotations.NotNull;

public class MapsFragment extends Fragment implements
        GoogleMap.OnInfoWindowClickListener,
        OnMapReadyCallback {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean locationPermissionGranted;
    SupportMapFragment mGoogleMap;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng mirea1 = new LatLng(55.670117, 37.480337);
        LatLng mirea2 = new LatLng(55.793772, 37.701201);
        LatLng mirea3 = new LatLng(55.661817, 37.477714);
        googleMap.addMarker(new MarkerOptions().position(mirea1).title("МИРЭА").snippet("Address: prospekt Vernadskogo, 78, Moscow, 119454 " +
                "\nFounded: May 28, 1947" + "\n 55.670117, 37.480337"));
        googleMap.addMarker(new MarkerOptions().position(mirea2).title("МГУПИ").snippet("Address: Ulitsa Stromynka, 20, Moscow, 107996 " +
                "\nFounded: August 29, 1936" + "\n 55.661817, 37.477714"));
        googleMap.addMarker(new MarkerOptions().position(mirea3).title("МИТХТ").snippet("Address: prospekt Vernadskogo, 86, Moscow, 119571 " +
                "\nFounded: 1900" + "\n 55.661817, 37.477714"));
        googleMap.setOnInfoWindowClickListener(this);
        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Nullable
            @Override
            public View getInfoWindow(@NonNull @NotNull Marker marker) {
                return null;
            }

            @Nullable
            @Override
            public View getInfoContents(@NonNull @NotNull Marker marker) {
                Context mContext = getActivity();

                LinearLayout info = new LinearLayout(mContext);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(mContext);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());
                title.setSingleLine(false);

                TextView snippet = new TextView(mContext);
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });

    }


    @Override
    public void onInfoWindowClick(@NotNull Marker marker) {

        marker.showInfoWindow();

    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        locationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true;
                }
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGoogleMap =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        getLocationPermission();
        if (mGoogleMap != null) {
            mGoogleMap.getMapAsync(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mGoogleMap.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mGoogleMap.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleMap.onDestroy();
    }

}