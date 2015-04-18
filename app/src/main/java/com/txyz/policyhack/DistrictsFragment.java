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

/**
 * Created by naman on 18/04/15.
 */
public class DistrictsFragment extends Fragment {

    private GoogleMap mMap;
    private int resultCode;
    private RecyclerView mRecyclerView;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View v = inflater.inflate(R.layout.fragment_maps, container, false);

        mRecyclerView=(RecyclerView) v.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        mRecyclerView.setHasFixedSize(true);

        ItemData itemsData[] = { new ItemData("LOl",R.drawable.rsz_school_one,"LOL","LOL"),
                new ItemData("lol",R.drawable.rsz_school_two,"LOL","LOL"),
                new ItemData("lol",R.drawable.rsz_school_three,"LOL","LOL"),
                new ItemData("lol",R.drawable.rsz_school_four,"LOL","LOL"),
                new ItemData("lol",R.drawable.rsz_school_one,"LOL","LOL"),
                new ItemData("lol",R.drawable.rsz_school_two,"LOL","LOL"),
                new ItemData("lol",R.drawable.rsz_school_three,"LOL","LOL"),
                new ItemData("lol",R.drawable.rsz_school_four,"LOL","LOL"), new ItemData("lol",R.drawable.rsz_school_one,"LOL","LOL"), new ItemData("lol",R.drawable.rsz_school_three,"LOL","LOL")
                , new ItemData("lol",R.drawable.ic_launcher,"LOL","LOL")};

        MyAdapter myAdapter=new MyAdapter(getActivity(),itemsData);
        mRecyclerView.setAdapter(myAdapter);


        resultCode= GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
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

