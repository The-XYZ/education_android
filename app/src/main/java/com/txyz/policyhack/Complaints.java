package com.txyz.policyhack;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.ParseObject;

import java.util.Calendar;


public class Complaints extends Fragment {

    private TextView displayTime;
    private LinearLayout pickTime;

    private int pHour;
    private int pMinute;
    /**
     * This integer will uniquely define the dialog to be used for displaying time picker.
     */
    static final int TIME_DIALOG_ID = 1;



    AutoCompleteTextView projectname;
    AutoCompleteTextView membername;
    EditText description;

    String returnname = null;
    String returnprojectname = null;

    String returndescription = null;
    Button invitepoolfriends;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View v = inflater.inflate(R.layout.fragment_complaints, container, false);



        invitepoolfriends = (Button) v.findViewById(R.id.invitefriends);

//        invitepoolfriends.setBackgroundDrawable(new ColorDrawable(Color.BLACK));

        projectname = (AutoCompleteTextView) v.findViewById(R.id.poolname);
        membername = (AutoCompleteTextView) v.findViewById(R.id.phoneorganizer);

        description = (EditText) v.findViewById(R.id.description);


        String possibleEmail = "";

        try {
            Account[] accounts = AccountManager.get(getActivity()).getAccountsByType("com.google");

            for (Account account : accounts) {
                possibleEmail = account.name;

            }
        } catch (Exception e) {
            Log.i("Exception", "Exception:" + e);
        }
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("email", possibleEmail);
        installation.saveInBackground();


        final Drawable originalDrawable = projectname.getBackground();





        projectname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View arg0, boolean arg1) {
                if (projectname.isFocused()) {
                    projectname.setBackgroundDrawable(originalDrawable);
                }
            }
        });

        membername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View arg0, boolean arg1) {
                if (membername.isFocused()) {
                    membername.setBackgroundDrawable(originalDrawable);
                }
            }
        });



        description.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View arg0, boolean arg1) {
                if (description.isFocused()) {
                    description.setBackgroundDrawable(originalDrawable);
                }
            }
        });




        invitepoolfriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                returnprojectname = projectname.getText().toString();
                returnname = membername.getText().toString();
                returndescription = description.getText().toString();


                ParseObject gameScore = new ParseObject("Complaints");
                gameScore.put("Pincode", returnprojectname);
                gameScore.put("Name", returnname);
                gameScore.put("Description", returndescription);
                gameScore.saveEventually();

                Toast.makeText(getActivity(), "Data added successfully!", Toast.LENGTH_LONG).show();
            }

        });


        /** Capture our View elements */

        /** Listener for click event of the button */

        return v;


    }



    /**
     * Create a new dialog for date picker
     */


}
