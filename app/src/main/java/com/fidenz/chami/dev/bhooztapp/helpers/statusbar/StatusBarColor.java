package com.fidenz.chami.dev.bhooztapp.helpers.statusbar;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.fidenz.chami.dev.bhooztapp.R;

/**
 * Created by fidenz on 8/2/18.
 */

public class StatusBarColor {

    public static Activity activityContext;


    public StatusBarColor(Activity activityContext) {
        this.activityContext =activityContext;
    }

    public void setStatusBarColour(){

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = activityContext.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(activityContext.getResources().getColor(R.color.splashcolor));
        }
    }
}
