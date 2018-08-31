package com.fidenz.chami.dev.bhooztapp.helpers.softkeybordhide;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by fidenz on 8/3/18.
 */

public class KeyboardHide {

    public static Activity activityContext;

    public KeyboardHide(Activity activityContext) {

        this.activityContext = activityContext;
    }

    public void softKeyboardHide(){
        activityContext.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public void buttonClickKeyBordHide(View view){


        InputMethodManager in = (InputMethodManager)activityContext.getSystemService(activityContext.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
