package com.fidenz.chami.dev.bhooztapp.helpers.TickerText;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fidenz.chami.dev.bhooztapp.helpers.DipPixConverter;

/**
 * Created by fidenz on 7/18/18.
 */

public class MarqueeLayout {

    private Context context;
    private MarqueeAnimationCallback callback;

    private TextView view;
    private RelativeLayout parentLayout;

    private int duration;

    public MarqueeLayout(Context context, MarqueeAnimationCallback callback) {
        this.context = context;
        this.callback = callback;
        this.view = new TextView(context);
    }

    public void setupMarquee(RelativeLayout parentLayout, int duration) {
        this.parentLayout = parentLayout;
        this.duration = duration;

        parentLayout.addView(view);
    }

    public void addMarquee(boolean visibility, String message) {

        //background = (background != null ? background : (context.getResources().getString(0 + R.color.ticker_background)));
       // font = (font != null ? font : "#ffffff");

        if (visibility)
            parentLayout.setVisibility(View.VISIBLE);
        else
            parentLayout.setVisibility(View.GONE);

        view.setText(message);
        view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        //view.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/melbourne_regular_basic.otf"));
        view.setTextColor(Color.WHITE);
        view.setSingleLine(true);
        view.setTextSize(18f);
        view.setGravity(Gravity.CENTER_VERTICAL);
        view.setEllipsize(null);

        Context context = view.getContext();    // gets the context of the view
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);   // measure textview before it's draw

        float width = view.getMeasuredWidth();  // takes the unconstrained width of the view
        float screenWidth = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth(); // gets the screen width

        System.out.println(view.getText());

        Animation mAnimation = new TranslateAnimation(screenWidth, (-width), 0, 0);
        mAnimation.setDuration(duration * 1000);
        mAnimation.setRepeatMode(Animation.RESTART);
        mAnimation.setRepeatCount(Animation.INFINITE);
        mAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                callback.onMarqueeAnimationStart();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                callback.onMarqueeAnimationFinish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                callback.onMarqueeAnimationRepeat(view.getText().toString());
            }
        });

        view.setAnimation(mAnimation);

        parentLayout.setLayoutParams(new LinearLayout.LayoutParams((int) screenWidth, (int)(DipPixConverter.dipToPixels(context, 34))));
       // parentLayout.setBackgroundColor(Color.parseColor(background));
    }

    public interface MarqueeAnimationCallback {
        void onMarqueeAnimationStart();

        void onMarqueeAnimationFinish();

        void onMarqueeAnimationRepeat(String currentText);
    }
}