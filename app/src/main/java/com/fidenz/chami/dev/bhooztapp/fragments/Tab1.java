package com.fidenz.chami.dev.bhooztapp.fragments;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fidenz.chami.dev.bhooztapp.R;
import com.fidenz.chami.dev.bhooztapp.activities.WinTryAgainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class Tab1 extends Fragment {

    @BindView(R.id.coin)
    ImageView coinImage;

    @BindView(R.id.usemecoin)
    TextView tvBhooztMe;

    private OnFragmentInteractionListener mListener;
    private int clickCount = 0;
    private Handler handler = new Handler();

    public Tab1() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab1, container, false);

        ButterKnife.bind(this,v);

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Romy.ttf");
        tvBhooztMe.setTypeface(typeface);

        return v;
    }

    private void animationHandle() {

        (getActivity()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        handler.removeCallbacks(runnable);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                (getActivity()).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                Intent intent = new Intent(getActivity(), WinTryAgainActivity.class);
                intent.putExtra("gametryagain", "Tryagain");
                getActivity().startActivity(intent);


            }
        }, 0);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            ObjectAnimator flipper = ObjectAnimator.ofFloat(coinImage, "rotationY", 0f, 360f);
            flipper.setDuration(3000);
            flipper.start();
            Log.e("ThredRUN", "RUNNING");
            handler.postDelayed(runnable, 3000);
        }
    };

    @OnClick(R.id.coin)
    public void OnClickCoinCount(View view) {

        clickCount = clickCount + 1;

        if (clickCount == 1) {
            animationHandle();
        } else if (clickCount == 2) {
            animationHandle();
        } else if (clickCount == 3) {

            (getActivity()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            handler.removeCallbacks(runnable);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    (getActivity()).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    final Intent mainIntent = new Intent(getActivity(), WinTryAgainActivity.class);
                    mainIntent.putExtra("Gamecoca", "CocaCola");
                    mainIntent.putExtra("passMessage", "Coca-Cola Game");
                    getActivity().startActivity(mainIntent);
                    Toast.makeText(getActivity(), "Winning piece", Toast.LENGTH_LONG).show();
                }
            }, 0);

        } else if (clickCount == 4) {
            animationHandle();
        } else if (clickCount == 5) {
            animationHandle();
        } else if (clickCount == 6) {
            (getActivity()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            handler.removeCallbacks(runnable);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    (getActivity()).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    final Intent mainIntent = new Intent(getActivity(), WinTryAgainActivity.class);
                    mainIntent.putExtra("bhoozt", "Bhoozt.com");
                    mainIntent.putExtra("passMessage", "Bhoozt Game");
                    getActivity().startActivity(mainIntent);
                    getActivity().finish();
                    Toast.makeText(getActivity(), "Winning piece", Toast.LENGTH_LONG).show();
                }
            }, 0);

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.post(runnable);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        handler.removeCallbacks(runnable);
    }
}
