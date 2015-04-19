package com.txyz.policyhack;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.animation.AnticipateInterpolator;
import android.widget.ImageView;


/**
 * Created by sthukral on 4/19/2015.
 */
public class CompareActivity extends ActionBarActivity {

    ImageView imageView,imageView2;
    CircularProgressDrawable drawable,drawable2;
    Animator animator,animator2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Compare");
        imageView=(ImageView) findViewById(R.id.iv_drawable);
        imageView2=(ImageView) findViewById(R.id.iv_drawable2);
        drawable = new CircularProgressDrawable.Builder()
                .setRingWidth(getResources().getDimensionPixelSize(R.dimen.drawable_ring_size))
                .setOutlineColor(getResources().getColor(android.R.color.darker_gray))
                .setRingColor(getResources().getColor(android.R.color.holo_green_light))
                .setCenterColor(getResources().getColor(android.R.color.holo_blue_dark))
                .create();
        drawable2 = new CircularProgressDrawable.Builder()
                .setRingWidth(getResources().getDimensionPixelSize(R.dimen.drawable_ring_size))
                .setOutlineColor(getResources().getColor(android.R.color.darker_gray))
                .setRingColor(getResources().getColor(android.R.color.holo_green_light))
                .setCenterColor(getResources().getColor(android.R.color.holo_blue_dark))
                .create();
        imageView.setImageDrawable(drawable);
        imageView2.setImageDrawable(drawable2);
        animator = prepareStyle3Animation();
        animator2 = prepareStyle3Animation2();
        animator.start();
        animator2.start();

    }

    private Animator prepareStyle3Animation() {
        AnimatorSet animation = new AnimatorSet();

        ObjectAnimator progressAnimation = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.PROGRESS_PROPERTY, 0f, 0.70f);
        progressAnimation.setDuration(1200);
        progressAnimation.setInterpolator(new AnticipateInterpolator());

        Animator innerCircleAnimation = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.CIRCLE_SCALE_PROPERTY, 0f, 0.70f);
        innerCircleAnimation.setDuration(1200);
        innerCircleAnimation.setInterpolator(new AnticipateInterpolator());



        animation.playTogether(progressAnimation, innerCircleAnimation);
        return animation;
    }
    private Animator prepareStyle3Animation2() {
        AnimatorSet animation = new AnimatorSet();

        ObjectAnimator progressAnimation = ObjectAnimator.ofFloat(drawable2, CircularProgressDrawable.PROGRESS_PROPERTY, 0f, 0.60f);
        progressAnimation.setDuration(1200);
        progressAnimation.setInterpolator(new AnticipateInterpolator());

        Animator innerCircleAnimation = ObjectAnimator.ofFloat(drawable2, CircularProgressDrawable.CIRCLE_SCALE_PROPERTY, 0f, 0.60f);
        innerCircleAnimation.setDuration(1200);
        innerCircleAnimation.setInterpolator(new AnticipateInterpolator());



        animation.playTogether(progressAnimation, innerCircleAnimation);
        return animation;
    }

}
