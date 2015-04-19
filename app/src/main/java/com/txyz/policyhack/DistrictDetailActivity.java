package com.txyz.policyhack;


import android.animation.TimeInterpolator;

import android.content.Intent;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.lid.lib.LabelView;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import org.eazegraph.lib.charts.StackedBarChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.StackedBarModel;


public class DistrictDetailActivity extends ActionBarActivity implements ObservableScrollViewCallbacks {

    private static final float MAX_TEXT_SCALE_DELTA = 0.3f;
    private static final boolean TOOLBAR_IS_STICKY = false;
    private final TimeInterpolator enterInterpolator = new DecelerateInterpolator(1.5f);
    private final TimeInterpolator exitInterpolator = new AccelerateInterpolator();

    private StackedBarChart mStackedBarChart;
    private View mToolbar;
    private View mImageView;
    private View mOverlayView;
    private ObservableScrollView mScrollView;
    private TextView mTitleView;
    private View mFab;
    private int mActionBarSize;
    private int mFlexibleSpaceShowFabOffset;
    private int mFlexibleSpaceImageHeight;
    private int mFabMargin;
    private int mToolbarColor;
    private boolean mFabIsShown;



    String statename1;
    String distname1;
    String totschools1;
    String totpopulation1;
    String p_06_pop1;
    String p_urb_pop1;
    String sexratio1;
    String sexratio_061;
    String growthrate1;
    String p_sc_pop1;
    String overall_lit1;
    String female_lit1;

    String statename2;
    String distname2;
    String totschools2;
    String totpopulation2;
    String p_06_pop2;
    String p_urb_pop2;
    String sexratio2;
    String sexratio_062;
    String growthrate2;
    String p_sc_pop2;
    String overall_lit2;
    String female_lit2;

    TextView DisName,  StateName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district_detail);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));


        DisName = (TextView)findViewById(R.id.Disname);
        StateName = (TextView)findViewById(R.id.StateName);


        final LabelView label = new LabelView(this);
        label.setText("2012");
        label.setBackgroundColor(Color.parseColor("#FF9800"));
        label.setTargetView(findViewById(R.id.cardview_2012), 10, LabelView.Gravity.RIGHT_TOP);

        final LabelView label2 = new LabelView(this);
        label2.setText("2013");
        label2.setBackgroundColor(Color.parseColor("#FF9800"));
        label2.setTargetView(findViewById(R.id.cardview_2013), 10, LabelView.Gravity.RIGHT_TOP);

        mFlexibleSpaceImageHeight = getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height);
        mFlexibleSpaceShowFabOffset = getResources().getDimensionPixelSize(R.dimen.flexible_space_show_fab_offset);
        mActionBarSize = getActionBarSize();
        mToolbarColor = getResources().getColor(R.color.colorPrimary);

        mToolbar = findViewById(R.id.toolbar);
        if (!TOOLBAR_IS_STICKY) {
            mToolbar.setBackgroundColor(Color.TRANSPARENT);
        }

        Intent i= getIntent();
        statename2= i.getStringExtra("getStatename2");
        distname2= i.getStringExtra("getDistname2");
        totschools2= i.getStringExtra("getTotschools2");
        totpopulation2= i.getStringExtra("getTotpopulation2");
        p_06_pop2= i.getStringExtra("getP_06_pop2");
        p_urb_pop2= i.getStringExtra("getP_urb_pop2");
        sexratio2= i.getStringExtra("getSexratio2");
        sexratio_062= i.getStringExtra("getSexratio_062");
        growthrate2= i.getStringExtra("getGrowthrate2");
        p_sc_pop2= i.getStringExtra("getP_sc_pop2");

        Toast.makeText(getApplicationContext(), p_sc_pop2,Toast.LENGTH_LONG).show();
        overall_lit2= i.getStringExtra("getOverall_lit2");
        female_lit2= i.getStringExtra("getFemale_lit2");


        statename1= i.getStringExtra("getStatename1");
        distname1= i.getStringExtra("getDistname1");
        totschools1= i.getStringExtra("getTotschools1");
        totpopulation1= i.getStringExtra("getTotpopulation1");
        p_06_pop1= i.getStringExtra("getP_06_pop1");
        p_urb_pop1= i.getStringExtra("getP_urb_pop1");
        sexratio1= i.getStringExtra("getSexratio1");
        sexratio_061= i.getStringExtra("getSexratio_061");
        growthrate1= i.getStringExtra("getGrowthrate1");
        p_sc_pop1= i.getStringExtra("getP_sc_pop1");
        Toast.makeText(getApplicationContext(), p_sc_pop1,Toast.LENGTH_LONG).show();

        overall_lit1= i.getStringExtra("getOverall_lit1");
        female_lit1= i.getStringExtra("grtFemale_lit1");


        DisName.setText(distname1);
        StateName.setText(statename1);

        mImageView = findViewById(R.id.image);
        mOverlayView = findViewById(R.id.overlay);
        mScrollView = (ObservableScrollView) findViewById(R.id.scroll);
        mScrollView.setScrollViewCallbacks(this);
        mTitleView = (TextView) findViewById(R.id.title);
        mTitleView.setText(getTitle());
        mFab = findViewById(R.id.fab);
        mFabMargin = getResources().getDimensionPixelSize(R.dimen.margin_standard);
        ViewHelper.setScaleX(mFab, 0);
        ViewHelper.setScaleY(mFab, 0);
        setTitle(null);

        ScrollUtils.addOnGlobalLayoutListener(mScrollView, new Runnable() {
            @Override
            public void run() {
                mScrollView.scrollTo(0, mFlexibleSpaceImageHeight - mActionBarSize);

                // If you'd like to start from scrollY == 0, don't write like this:
                //mScrollView.scrollTo(0, 0);
                // The initial scrollY is 0, so it won't invoke onScrollChanged().
                // To do this, use the following:
                //onScrollChanged(0, false, false);

                // You can also achieve it with the following codes.
                // This causes scroll change from 1 to 0.
                //mScrollView.scrollTo(0, 1);
                //mScrollView.scrollTo(0, 0);
            }
        });

        mStackedBarChart = (StackedBarChart) findViewById(R.id.stackedbarchart);

        StackedBarModel s1 = new StackedBarModel("Total schools");
        s1.addBar(new BarModel(2.3f, 0xFF63CBB0));
        s1.addBar(new BarModel(2.3f, 0xFF56B7F1));

        StackedBarModel s2 = new StackedBarModel("Total pop.");
        s2.addBar(new BarModel(1.1f, 0xFF63CBB0));
        s2.addBar(new BarModel(2.7f, 0xFF56B7F1));

        StackedBarModel s3 = new StackedBarModel("%(0-6) pop.");

        s3.addBar(new BarModel(2.3f, 0xFF63CBB0));
        s3.addBar(new BarModel(2.f, 0xFF56B7F1));

        StackedBarModel s4 = new StackedBarModel("% urban pop.");
        s4.addBar(new BarModel(1.f, 0xFF63CBB0));
        s4.addBar(new BarModel(4.2f, 0xFF56B7F1));

        StackedBarModel s5 = new StackedBarModel("sex ratio");
        s5.addBar(new BarModel(2.3f, 0xFF63CBB0));
        s5.addBar(new BarModel(2.3f, 0xFF56B7F1));

        StackedBarModel s6 = new StackedBarModel("sex ratio(0-6)");
        s6.addBar(new BarModel(2.3f, 0xFF63CBB0));
        s6.addBar(new BarModel(2.3f, 0xFF56B7F1));

        StackedBarModel s7 = new StackedBarModel("% SC pop.");
        s7.addBar(new BarModel(2.3f, 0xFF63CBB0));
        s7.addBar(new BarModel(2.3f, 0xFF56B7F1));

        StackedBarModel s8 = new StackedBarModel("% ST pop.");
        s8.addBar(new BarModel(1.1f, 0xFF63CBB0));
        s8.addBar(new BarModel(2.7f, 0xFF56B7F1));

        StackedBarModel s9 = new StackedBarModel("overall pop.");

        s9.addBar(new BarModel(2.3f, 0xFF63CBB0));
        s9.addBar(new BarModel(2.f, 0xFF56B7F1));

        StackedBarModel s10 = new StackedBarModel("female pop.");
        s10.addBar(new BarModel(1.f, 0xFF63CBB0));
        s10.addBar(new BarModel(4.2f, 0xFF56B7F1));

        mStackedBarChart.addBar(s1);
        mStackedBarChart.addBar(s2);
        mStackedBarChart.addBar(s3);
        mStackedBarChart.addBar(s4);
        mStackedBarChart.addBar(s5);
        mStackedBarChart.addBar(s6);
        mStackedBarChart.addBar(s7);
        mStackedBarChart.addBar(s8);
        mStackedBarChart.addBar(s9);
        mStackedBarChart.addBar(s10);

        mStackedBarChart.startAnimation();
        //mStackedBarChart.setPaddingRelative();

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DistrictDetailActivity.this, CompareActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        // Translate overlay and image
        float flexibleRange = mFlexibleSpaceImageHeight - mActionBarSize;
        int minOverlayTransitionY = mActionBarSize - mOverlayView.getHeight();
        ViewHelper.setTranslationY(mOverlayView, ScrollUtils.getFloat(-scrollY, minOverlayTransitionY, 0));
        ViewHelper.setTranslationY(mImageView, ScrollUtils.getFloat(-scrollY / 2, minOverlayTransitionY, 0));

        // Change alpha of overlay
        ViewHelper.setAlpha(mOverlayView, ScrollUtils.getFloat((float) scrollY / flexibleRange, 0, 1));

        // Scale title text
        float scale = 1 + ScrollUtils.getFloat((flexibleRange - scrollY) / flexibleRange, 0, MAX_TEXT_SCALE_DELTA);
        ViewHelper.setPivotX(mTitleView, 0);
        ViewHelper.setPivotY(mTitleView, 0);
        ViewHelper.setScaleX(mTitleView, scale);
        ViewHelper.setScaleY(mTitleView, scale);

        // Translate title text
        int maxTitleTranslationY = (int) (mFlexibleSpaceImageHeight - mTitleView.getHeight() * scale);
        int titleTranslationY = maxTitleTranslationY - scrollY;
        if (TOOLBAR_IS_STICKY) {
            titleTranslationY = Math.max(0, titleTranslationY);
        }
        ViewHelper.setTranslationY(mTitleView, titleTranslationY);

        // Translate FAB
        int maxFabTranslationY = mFlexibleSpaceImageHeight - mFab.getHeight() / 2;
        float fabTranslationY = ScrollUtils.getFloat(
                -scrollY + mFlexibleSpaceImageHeight - mFab.getHeight() / 2,
                mActionBarSize - mFab.getHeight() / 2,
                maxFabTranslationY);
        ViewHelper.setTranslationX(mFab, mOverlayView.getWidth() - mFabMargin - mFab.getWidth());
        ViewHelper.setTranslationY(mFab, fabTranslationY);

        // Show/hide FAB
        if (ViewHelper.getTranslationY(mFab) < mFlexibleSpaceShowFabOffset) {
            hideFab();
        } else {
            showFab();
        }

        if (TOOLBAR_IS_STICKY) {
            // Change alpha of toolbar background
            if (-scrollY + mFlexibleSpaceImageHeight <= mActionBarSize) {
                mToolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(1, mToolbarColor));
            } else {
                mToolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, mToolbarColor));
            }
        } else {
            // Translate Toolbar
            if (scrollY < mFlexibleSpaceImageHeight) {
                ViewHelper.setTranslationY(mToolbar, 0);
            } else {
                ViewHelper.setTranslationY(mToolbar, -scrollY);
            }
        }
    }

    private void showFab() {
        if (!mFabIsShown) {
            ViewPropertyAnimator.animate(mFab).cancel();
            ViewPropertyAnimator.animate(mFab).scaleX(1).scaleY(1).setDuration(200).start();
            mFabIsShown = true;
        }
    }

    private void hideFab() {
        if (mFabIsShown) {
            ViewPropertyAnimator.animate(mFab).cancel();
            ViewPropertyAnimator.animate(mFab).scaleX(0).scaleY(0).setDuration(200).start();
            mFabIsShown = false;
        }
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    }

    protected int getActionBarSize() {
        TypedValue typedValue = new TypedValue();
        int[] textSizeAttr = new int[]{R.attr.actionBarSize};
        int indexOfAttrTextSize = 0;
        TypedArray a = obtainStyledAttributes(typedValue.data, textSizeAttr);
        int actionBarSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1);
        a.recycle();
        return actionBarSize;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_district_detail, menu);
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
