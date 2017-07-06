package hackeru.edu.locationaware;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Find the user Location Without using the map:
 * //Where is the user
 * //Distance in meters from another location
 * //bearing to another location
 * //Speed
 * //location-> address
 * //address->location
 */
public class LocationFragment extends Fragment {

    @BindView(R.id.tvLocation)
    TextView tvLocation;
    Unbinder unbinder;
    //Api provider
    FusedLocationProviderClient client;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        unbinder = ButterKnife.bind(this, view);

        client = new FusedLocationProviderClient(getContext());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        getLastKnownLocation();
    }

    //Might be null especially in the emulator:
    private void getLastKnownLocation() {
        if (!checkLocationPermission()) return;

        Task<Location> task = client.getLastLocation();

        task.addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()) {
                    Location location = task.getResult();
                    if (location != null) {
                        tvLocation.setText(location.toString());
                        tvLocation.setTextSize(20);
                    }
                }
            }
        });
    }

    private boolean checkLocationPermission() {
        //if no permission -> request it and get out.
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    getActivity()/*Activity*/,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1
            );
            return false;
        }
        return true;
    }

    private void getLocationUpdates() {
        LocationRequest request = new LocationRequest();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);//GPS
        /*request.setPriority(LocationRequest.PRIORITY_LOW_POWER);//Cellular
        request.setPriority(LocationRequest.PRIORITY_NO_POWER);//Last Known Location
        request.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);//GPS + Cellular */

        request.setInterval(30 * 1000); //check the gps chip each 30 seconds
        request.setFastestInterval(500);

        //request.setNumUpdates //infinite updates
        //request.setExpirationDuration(60*60*1000);//stop after one hour
        //request.setSmallestDisplacement(1000);//in meters


        //client.requestLocationUpdates()
    }

}
