package com.txyz.policyhack;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
 * Created by naman on 18/04/15.
 */
public class DistrictsFragment  extends Fragment {

    private GoogleMap mMap;
    private int resultCode;
    private RecyclerView mRecyclerView;
    MyAdapter2 myAdapter;
    Spinner spinner;
    Toolbar toolbar;


    ArrayList<DistrictData> list1 = new ArrayList<DistrictData>();
    ArrayList<DistrictData> list2 = new ArrayList<DistrictData>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View v = inflater.inflate(R.layout.fragment_maps, container, false);

        mRecyclerView=(RecyclerView) v.findViewById(R.id.recycler_view);
        spinner=(Spinner)getActivity().findViewById(R.id.spinner_nav);
        spinner.setVisibility(View.VISIBLE);
        final List<String> list = new ArrayList<String>();
        list.add("ARUNACHAL PRADESH");
        list.add("PUDUCHERRY");
        list.add("JHARKHAND");list.add("HARYANA");list.add("MANIPUR");list.add("GOA");list.add("MEGHALAYA");
        list.add("CHHATTISGARH");list.add("LAKSHADWEEP");list.add("DAMAN & DIU");list.add("KERALA");list.add("TAMIL NADU");
        list.add("RAJASTHAN");list.add("DELHI");list.add("UTTAR PRADESH");list.add("NAGALAND");list.add("MAHARASHTRA");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                fetchData1(list.get(i));
                fetchData2(list.get(i));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        toolbar=(Toolbar) getActivity().findViewById(R.id.toolbar);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setHasFixedSize(true);

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

    public void fetchData1(String StateName){
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                "District2012");
        query.whereMatches("statename",StateName);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                doneFetching1(parseObjects);
            }
        });
    }

    public void fetchData2(String StateName){
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                "District2013");
        query.whereMatches("statename",StateName);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                doneFetching2(parseObjects);
            }
        });
    }


    public void doneFetching1(List<ParseObject> objects) {

        Log.d("lol", objects.toString());

        for (ParseObject item : objects) {

            DistrictData itemData = new DistrictData();

            itemData.statename= item.getString("statename");
            itemData.imageUrl=   R.drawable.rsz_school_one;
            itemData.distname= item.getString("distname");
            itemData.totschools= item.getString("totschools");
            itemData.totpopulation= item.getString("totpopulation");
            itemData.p_06_pop= item.getString("p_06_pop");
            itemData.p_urb_pop= item.getString("p_urb_pop");
            itemData.sexratio= item.getString("sexratio");
            itemData.sexratio_06= item.getString("sexratio_06");
            itemData.growthrate= item.getString("growthrate");
            itemData.p_sc_pop= item.getString("p_sc_pop");
            itemData.overall_lit= item.getString("overall_lit");
            itemData.female_lit= item.getString("female_lit");

            try {
                itemData.latlong = "" + item.getParseGeoPoint("lat").getLatitude() + "," + item.getParseGeoPoint("lat").getLongitude();
                addToMap(itemData.getLatlong().toString(), itemData.getDistname());
                Log.d("lol", itemData.getLatlong().toString());
            }
            catch (NullPointerException e){

            }

            list1.add(itemData);
            Log.d("lol2","lol");
        }

//        myAdapter=new MyAdapter2(getActivity(),list1);
//
//        mRecyclerView.setAdapter(myAdapter);
//        Log.d("lol","lol");

    }

    public void doneFetching2(List<ParseObject> objects) {

        Log.d("lol", objects.toString());

        for (ParseObject item : objects) {

            DistrictData itemData = new DistrictData();

            itemData.statename= item.getString("statename");
            itemData.imageUrl=   R.drawable.rsz_school_one;
            itemData.distname= item.getString("distname");
            itemData.totschools= item.getString("totschools");
            itemData.totpopulation= item.getString("totpopulation");
            itemData.p_06_pop= item.getString("p_06_pop");
            itemData.p_urb_pop= item.getString("p_urb_pop");
            itemData.sexratio= item.getString("sexratio");
            itemData.sexratio_06= item.getString("sexratio_06");
            itemData.growthrate= item.getString("growthrate");
            itemData.p_sc_pop= item.getString("p_sc_pop");
            itemData.overall_lit= item.getString("overall_lit");
            itemData.female_lit= item.getString("female_lit");
            try {
                itemData.latlong = "" + item.getParseGeoPoint("lat").getLatitude() + "," + item.getParseGeoPoint("lat").getLongitude();
                addToMap(itemData.getLatlong().toString(), itemData.getDistname());
                Log.d("lol", itemData.getLatlong().toString());
            }
            catch (NullPointerException e){

            }

            list2.add(itemData);
            Log.d("lol2","lol");
        }

        myAdapter=new MyAdapter2(getActivity(),list2);

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



    public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.ViewHolder> {
        ArrayList<DistrictData> itemsData;
        private Context context;

        private int lastPosition = -1;

        public MyAdapter2(Context context, ArrayList<DistrictData> itemsData) {
            this.itemsData = itemsData;
            this.context = context;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
            // create a new view
            View itemLayoutView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_view_district, null);

            // create ViewHolder

            ViewHolder viewHolder = new ViewHolder(itemLayoutView);
            return viewHolder;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {

            // - get data from your itemsData at this position
            // - replace the contents of the view with that itemsData
            setAnimation(viewHolder.itemView, position);

            viewHolder.disname.setText(itemsData.get(position).getDistname());
            viewHolder.imgViewIcon.setImageResource(itemsData.get(position).getImageUrl());
            viewHolder.state.setText(itemsData.get(position).getStatename());

            viewHolder.imgViewIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), DistrictDetailActivity.class);
                    intent.putExtra("getDistname1",list1.get(position).getDistname());
                    intent.putExtra("getFemale_lit1",list1.get(position).getFemale_lit());
                    intent.putExtra("getOverall_lit1",list1.get(position).getOverall_lit());
                    intent.putExtra("getGrowthrate1",list1.get(position).getGrowthrate());
                    intent.putExtra("getP_06_pop1",list1.get(position).getP_06_pop());
                    intent.putExtra("getP_sc_pop1",list1.get(position).getP_sc_pop());
                    intent.putExtra("getP_urb_pop1",list1.get(position).getP_urb_pop());
                    intent.putExtra("getSexratio1",list1.get(position).getSexratio());
                    intent.putExtra("getSexratio_061",list1.get(position).getSexratio_06());
                    intent.putExtra("getTotschools1",list1.get(position).getTotschools());
                    intent.putExtra("getTotpopulation1z",list1.get(position).getTotpopulation());

                    intent.putExtra("getDistname2",list2.get(position).getDistname());
                    intent.putExtra("getFemale_lit2",list2.get(position).getFemale_lit());
                    intent.putExtra("getOverall_lit2",list2.get(position).getOverall_lit());
                    intent.putExtra("getGrowthrate2",list2.get(position).getGrowthrate());
                    intent.putExtra("getP_06_pop2",list2.get(position).getP_06_pop());
                    intent.putExtra("getP_sc_pop2",list2.get(position).getP_sc_pop());
                    intent.putExtra("getP_urb_pop2",list2.get(position).getP_urb_pop());
                    intent.putExtra("getSexratio2",list2.get(position).getSexratio());
                    intent.putExtra("getSexratio_062",list2.get(position).getSexratio_06());
                    intent.putExtra("getTotschools2",list2.get(position).getTotschools());
                    intent.putExtra("getTotpopulation2z",list2.get(position).getTotpopulation());

                    startActivity(intent);
                }
            });



        }

        // inner class to hold a reference to each item of RecyclerView
        public  class ViewHolder extends RecyclerView.ViewHolder {

            public TextView disname, state;
            public ImageView imgViewIcon;

            public ViewHolder(View itemLayoutView) {
                super(itemLayoutView);
                disname = (TextView) itemLayoutView.findViewById(R.id.item_title);
                imgViewIcon = (ImageView) itemLayoutView.findViewById(R.id.item_icon);
                state = (TextView) itemLayoutView.findViewById(R.id.item_block);
            }
        }


        // Return the size of your itemsData (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return itemsData.size();
        }

        private void setAnimation(View viewToAnimate, int position) {
            // If the bound view wasn't previously displayed on screen, it's animated
            if (position > lastPosition) {
                Animation animation = AnimationUtils.loadAnimation(context, R.anim.scale);
                viewToAnimate.startAnimation(animation);
                lastPosition = position;
            }
        }

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

