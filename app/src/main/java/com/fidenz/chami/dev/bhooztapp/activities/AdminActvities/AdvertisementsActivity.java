package com.fidenz.chami.dev.bhooztapp.activities.AdminActvities;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.fidenz.chami.dev.bhooztapp.helpers.swipetouch.OnSwipeTouchListener;
import com.fidenz.chami.dev.bhooztapp.helpers.softkeybordhide.KeyboardHide;
import com.fidenz.chami.dev.bhooztapp.helpers.statusbar.StatusBarColor;
import com.fidenz.chami.dev.bhooztapp.models.Advertisements;
import com.fidenz.chami.dev.bhooztapp.R;
import com.fidenz.chami.dev.bhooztapp.adapter.AdvertistmnetAdapter;
import com.fidenz.chami.dev.bhooztapp.database.DBHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdvertisementsActivity extends AppCompatActivity implements AdvertistmnetAdapter.ItemListener {


    @BindView(R.id.idetheader)
    EditText etAdHeader;

    @BindView(R.id.idetbody)
    EditText etAdBody;

    @BindView(R.id.idetlink)
    EditText etAdLink;

    @BindView(R.id.idetlinkname)
    EditText etAdLinkName;

    @BindView(R.id.idbtnsave)
    Button btnSave;

    @BindView(R.id.idbackarrow)
    ImageView imgBackArrow;

    @BindView(R.id.toobarname)
    TextView tvToolbar;

    @BindView(R.id.idlinearoutside)
    LinearLayout linOutside;

    @BindView(R.id.idscroll)
    ScrollView sclScroll;

    @BindView(R.id.idadsadvertistments)
    RecyclerView recAdvertisement;

    private String adHeader, adBody, adLink, adLinkName;
    private int adIdNew;
    RecyclerView.Adapter adapterAdvertisement;
    DBHelper dbHelper;
    List advertisementList = new ArrayList<Advertisements>();
    Boolean aBoolean = false;
    StatusBarColor barColor;
    KeyboardHide keyboardHide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisements);

        init();
        swipeTouchListener();
        outSideForcesRemove();
        editTextFocusableEnable();
        initAdvertisement();

        tvToolbar.setText("Advertisements");

        etAdHeader.setFocusable(false);
        etAdBody.setFocusable(false);
        etAdLink.setFocusable(false);
        etAdLinkName.setFocusable(false);
    }

    private void init() {
        ButterKnife.bind(this);

        dbHelper = new DBHelper(this);

        barColor = new StatusBarColor(this);
        barColor.setStatusBarColour();

        keyboardHide = new KeyboardHide(this);
        keyboardHide.softKeyboardHide();

        recAdvertisement.setHasFixedSize(true);
        recAdvertisement.setLayoutManager(new LinearLayoutManager(this));
    }

    private void swipeTouchListener() {

        sclScroll.setOnTouchListener(new OnSwipeTouchListener(AdvertisementsActivity.this) {
            public void onSwipeTop() {
            }

            public void onSwipeRight() {
                Intent intent = new Intent(AdvertisementsActivity.this, SettingsActvity.class);
                startActivity(intent);
                finish();
            }

            public void onSwipeLeft() {
            }

            public void onSwipeBottom() {
            }

        });
    }

    private void outSideForcesRemove() {
        linOutside.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                etAdHeader.setFocusable(false);
                etAdBody.setFocusable(false);
                etAdLink.setFocusable(false);
                etAdLinkName.setFocusable(false);

                return false;
            }
        });
    }

    private void editTextFocusableEnable() {
        etAdHeader.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.setFocusableInTouchMode(true);
                return false;
            }
        });

        etAdBody.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.setFocusableInTouchMode(true);
                return false;
            }
        });

        etAdLink.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.setFocusableInTouchMode(true);
                return false;
            }
        });

        etAdLinkName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.setFocusableInTouchMode(true);
                return false;
            }
        });
    }

    private void initAdvertisement() {
        advertisementList = getAllAdvertistmnets();
        adapterAdvertisement = new AdvertistmnetAdapter(advertisementList, this, this);
        recAdvertisement.setAdapter(adapterAdvertisement);
        adapterAdvertisement.notifyDataSetChanged();
    }

    private void updateAdvertisement() {
        adHeader = etAdHeader.getText().toString().trim();
        adBody = etAdBody.getText().toString().trim();
        adLink = etAdLink.getText().toString().trim();
        adLinkName = etAdLinkName.getText().toString().trim();

        Advertisements advertisements = new Advertisements();

        advertisements.setId(adIdNew);
        advertisements.setAdheader(adHeader);
        advertisements.setAdbody(adBody);
        advertisements.setAdlink(adLink);
        advertisements.setAdlinkname(adLinkName);

        try {
            dbHelper.createOrUpdate(advertisements);
            etAdHeader.setText("");
            etAdBody.setText("");
            etAdLink.setText("");
            etAdLinkName.setText("");

            etAdHeader.setFocusable(false);
            etAdBody.setFocusable(false);
            etAdLink.setFocusable(false);
            etAdLinkName.setFocusable(false);

            initAdvertisement();
            recAdvertisement.scrollToPosition(advertisementList.size() - 1);

            btnSave.setText("Save");
            Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_LONG).show();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        aBoolean = false;

    }

    private void addAdvertisement() {

        Advertisements advertisements = new Advertisements();

        adHeader = etAdHeader.getText().toString().trim();
        adBody = etAdBody.getText().toString().trim();
        adLink = etAdLink.getText().toString().trim();
        adLinkName = etAdLinkName.getText().toString().trim();


        if (TextUtils.isEmpty(adHeader)) {

            etAdHeader.setError("Please enter AdHeader");
            return;
        }

        if (TextUtils.isEmpty(adBody)) {

            etAdBody.setError("Please enter AdBody");
            return;
        }

        if (TextUtils.isEmpty(adLink)) {

            etAdLink.setError("Please enter AdLink");
            return;
        }

        if (TextUtils.isEmpty(adLinkName)) {

            etAdLinkName.setError("Please enter AdLinkname");
            return;
        }

        advertisements.setAdheader(etAdHeader.getText().toString());
        advertisements.setAdbody(etAdBody.getText().toString());
        advertisements.setAdlink(etAdLink.getText().toString());
        advertisements.setAdlinkname(etAdLinkName.getText().toString());

        try {

            if (advertisementList.size() < 5) {

                dbHelper.createOrUpdate(advertisements);

                etAdHeader.setFocusable(false);
                etAdBody.setFocusable(false);
                etAdLink.setFocusable(false);
                etAdLinkName.setFocusable(false);

                etAdHeader.setText("");
                etAdBody.setText("");
                etAdLink.setText("");
                etAdLinkName.setText("");


            } else {
                Toast.makeText(getApplicationContext(), "Maximum Ads are limited to 5", Toast.LENGTH_LONG).show();

                etAdHeader.setText("");
                etAdBody.setText("");
                etAdLink.setText("");
                etAdLinkName.setText("");
            }


            initAdvertisement();
            recAdvertisement.scrollToPosition(advertisementList.size() - 1);
            Log.e("ADD", "ADD TO DB");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

    }

    public List getAllAdvertistmnets() {

        List madList = new ArrayList<>();
        try {
            madList.addAll(dbHelper.getAll(Advertisements.class));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return madList;
    }

    @OnClick(R.id.idbackarrow)
    public void backArrowFunction(View view) {
        Intent intent = new Intent(AdvertisementsActivity.this, SettingsActvity.class);
        startActivity(intent);
    }

    @OnClick(R.id.idbtnsave)
    public void adUpdateCreate(View view) {
        if (!aBoolean) {
            addAdvertisement();
        } else {
            updateAdvertisement();

        }
        keyboardHide.buttonClickKeyBordHide(view);
    }

    @Override
    public void onclickedit(int position) {

        Advertisements ad = (Advertisements) advertisementList.get(position);

        int id = ad.getId();
        String adheader = ad.getAdheader();
        String adbody = ad.getAdbody();
        String adlink = ad.getAdlink();
        String adlinkname = ad.getAdlinkname();

        etAdHeader.setText(adheader);
        etAdBody.setText(adbody);
        etAdLink.setText(adlink);
        etAdLinkName.setText(adlinkname);

        adIdNew = id;
        aBoolean = true;
        btnSave.setText("Update");


    }
}
