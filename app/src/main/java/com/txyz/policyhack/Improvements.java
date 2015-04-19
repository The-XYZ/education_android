package com.txyz.policyhack;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class Improvements extends ActionBarActivity {

    TextView name , one, two , three, four , five, six;
    TextView  qone, qtwo , qthree, qfour , qfive, qsix;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_improvements);

        Intent intent = getIntent();
        String name1 = intent.getStringExtra("name");

        name=(TextView)findViewById(R.id.name);
        one=(TextView)findViewById(R.id.one);
        two=(TextView)findViewById(R.id.two);
        three=(TextView)findViewById(R.id.three);
        four=(TextView)findViewById(R.id.four);
        five=(TextView)findViewById(R.id.five);
        six=(TextView)findViewById(R.id.six);

        name.setText(name1);

        one.setText("% Children (6-14) OOS : " +  "6.2");
        two.setText("% Children (6-14) in pvt. School : " +  "72.8");
        three.setText("% Children (Std 1- II) who CAN READ letters, words or more : " +  "95.4");
        four.setText("% Children (Std III- V) who CAN READ Level 1 (Std 1) text or more : " +  "95.2");
        five.setText("who CAN RECOGNIZE numbers (1-9) or more : " +  "75.5");
        six.setText("% Children (Std III- V) who CAN READ Level 1 (Std 1) text or more : " +  "57");

        qone=(TextView)findViewById(R.id.qone);
        qtwo=(TextView)findViewById(R.id.qtwo);
        qthree=(TextView)findViewById(R.id.qthree);
        qfour=(TextView)findViewById(R.id.qfour);
        qfive=(TextView)findViewById(R.id.qfive);
        qsix=(TextView)findViewById(R.id.qsix);

        qone.setText("% Children (6-14) OOS : " +  "7.1");
        qtwo.setText("% Children (6-14) in pvt. School : " +  "74.3");
        qthree.setText("% Children (Std 1- II) who CAN READ letters, words or more : " +  "98.1");
        qfour.setText("% Children (Std III- V) who CAN READ Level 1 (Std 1) text or more : " +  "96.3");
        qfive.setText("who CAN RECOGNIZE numbers (1-9) or more : " +  "79.7");
        qsix.setText("% Children (Std III- V) who CAN READ Level 1 (Std 1) text or more : " +  "63");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_improvements, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
