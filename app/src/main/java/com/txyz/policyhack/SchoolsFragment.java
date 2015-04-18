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
import android.widget.Spinner;

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
public class SchoolsFragment extends Fragment {

    private GoogleMap mMap;
    private int resultCode;
    private RecyclerView mRecyclerView;
    MyAdapter myAdapter;
    Spinner spinner;

    ArrayList<ItemData> list = new ArrayList<ItemData>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View v = inflater.inflate(R.layout.fragment_maps, container, false);
        spinner=(Spinner)getActivity().findViewById(R.id.spinner_nav);
        spinner.setVisibility(View.GONE);
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


        for (ParseObject item : objects) {

            ItemData itemData = new ItemData();
            itemData.title= item.getString("SCHOOL_NAME");
            itemData.imageUrl=   R.drawable.rsz_school_one;
            itemData.block= item.getString("BLOCK_NAME");
            itemData.village= item.getString("VILLAGE_NAME");
            try {
            itemData.latlong= ""+item.getParseGeoPoint("lat").getLatitude()+","+item.getParseGeoPoint("lat").getLongitude();

                addToMap(itemData.getLatlong().toString(),itemData.getTitle());
                Log.d("lol",itemData.getLatlong().toString());
            }
           catch (NullPointerException e){

           }
            list.add(itemData);

        }

        myAdapter=new MyAdapter(getActivity(),list);

        mRecyclerView.setAdapter(myAdapter);
        Log.d("lol", "lol");

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
    private void addToMap(String latlong,String title){
        mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();


        MarkerOptions markerOptions;
        LatLng position;
        String lati=latlong.substring(0,latlong.indexOf(",")),longi=latlong.substring(latlong.indexOf(",")+1,latlong.length());
        Log.d("lol2",lati+","+longi);

        markerOptions = new MarkerOptions();


        position = new LatLng(Double.parseDouble(lati), Double.parseDouble(longi));
        markerOptions.position(position);
        markerOptions.title(title);
        mMap.addMarker(markerOptions);
        CameraUpdate cameraPosition = CameraUpdateFactory.newLatLngZoom(position, 6.0f);
        mMap.animateCamera(cameraPosition);

    }
}
