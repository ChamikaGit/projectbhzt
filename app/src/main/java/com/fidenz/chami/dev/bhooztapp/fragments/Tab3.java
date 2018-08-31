package com.fidenz.chami.dev.bhooztapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fidenz.chami.dev.bhooztapp.activities.CoinXFRActivity;
import com.fidenz.chami.dev.bhooztapp.R;
import com.fidenz.chami.dev.bhooztapp.database.SharedPreference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Tab3 extends Fragment {

    @BindView(R.id.cointab3)
    ImageView coinImage;

    @BindView(R.id.usemecoin)
    TextView tvBhooztMe;

    @BindView(R.id.textcoincount)
    TextView tvCoinCountFull;

    private SharedPreference sharedPreference;
    private String totalPointCount;
    private OnFragmentInteractionListener mListener;

    public Tab3() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_tab3, container, false);
        ButterKnife.bind(this, v);

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Romy.ttf");
        tvBhooztMe.setTypeface(typeface);

        sharedPreference = new SharedPreference();

        totalPointCount = sharedPreference.getTotalCoinCount(getActivity().getApplicationContext());
        tvCoinCountFull.setText(totalPointCount);

        return v;
    }

    @OnClick(R.id.cointab3)
    public void onClickCoinImage(View view) {
        Intent intent = new Intent(getActivity(), CoinXFRActivity.class);
        intent.putExtra("gametryagain", "Tryagain");
        getActivity().startActivity(intent);
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
        void onFragmentInteraction(Uri uri);
    }
}
