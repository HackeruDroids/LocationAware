package hackeru.edu.locationaware;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location, container, false);
    }

}
