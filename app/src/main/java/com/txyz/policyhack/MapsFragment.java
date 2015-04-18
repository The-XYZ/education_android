package com.txyz.policyhack;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by naman on 22/12/14.
 */
public class MapsFragment extends Fragment {

    private GoogleMap mMap;
    private int resultCode;
    private RecyclerView mRecyclerView;
    MyAdapter myAdapter;

    ArrayList<ItemData> list = new ArrayList<ItemData>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View v = inflater.inflate(R.layout.fragment_maps, container, false);

        mRecyclerView=(RecyclerView) v.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        mRecyclerView.setHasFixedSize(true);
        fetchData();

        resultCode=GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
        if(resultCode != ConnectionResult.SUCCESS)
        {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(), 69);
            dialog.setCancelable(true);

            dialog.show();
        }
        else {
            mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();


            MarkerOptions markerOptions;
            LatLng position;

            markerOptions = new MarkerOptions();

            if (isGoogleMapsInstalled()) {
                position = new LatLng(28.749783333f, 77.1172f);
                markerOptions.position(position);
                markerOptions.title("Delhi Technological University");
                mMap.addMarker(markerOptions);
                CameraUpdate cameraPosition = CameraUpdateFactory.newLatLngZoom(position, 15.0f);
                mMap.animateCamera(cameraPosition);
            }
            else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Please install Google Maps");
                builder.setCancelable(true);
                builder.setPositiveButton("Install", getGoogleMapsListener());
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
        return v;
    }

    public boolean isGoogleMapsInstalled()
    {
        try
        {
            ApplicationInfo info = getActivity().getPackageManager().getApplicationInfo("com.google.android.apps.maps", 0);
            return true;
        }
        catch(PackageManager.NameNotFoundException e)
        {
            return false;
        }
    }

    public void fetchData(){
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                "SchoolNames");
//        query.orderByAscending(ParseTables.Events.CREATED_AT);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                doneFetching(parseObjects);
            }
        });
    }


    public void doneFetching(List<ParseObject> objects) {

        ArrayList<ItemData> list = new ArrayList<ItemData>();
        Log.d("lol",objects.toString());

        for (ParseObject item : objects) {

            ItemData itemData = new ItemData();
            itemData.title= item.getString("SCHOOL_NAME");
            itemData.imageUrl=   R.drawable.rsz_school_one;
            itemData.block= item.getString("BLOCK_NAME");
            itemData.village= item.getString("VILLAGE_NAME");
            itemData.latlong= item.getString("GEOPOINT");

            list.add(itemData);

        }

        myAdapter=new MyAdapter(getActivity(),list);

        mRecyclerView.setAdapter(myAdapter);
        Log.d("lol","lol");

    }
    public DialogInterface.OnClickListener getGoogleMapsListener() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps"));
                startActivity(intent);


            }
        };
    }
}
