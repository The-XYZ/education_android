package com.txyz.policyhack;

/**
 * Created by sikkavayu2013 on 15-01-2015.
 */
import android.app.Application;
import android.text.TextUtils;

import com.parse.Parse;
import com.parse.ParseInstallation;

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();


    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Parse.initialize(this, "P3N8sjOcUB7pMF3G1miWIfswdXGx6MN2MzBJ8I3M", "SpsSaUXXDCzMB3ZDxzUAJaXpLgvbvC1gemBsMAd0");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }


}