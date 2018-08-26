package com.oc.bashalir.mynews.Controllers.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.oc.bashalir.mynews.Controllers.Utils.AlarmReceiver;
import com.oc.bashalir.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "NOTIFY";
    final String SEARCH = "SEARCH";
    final String ARTS = "ARTS";
    final String BUSINESS = "BUSINESS";
    final String POLITICS = "POLITICS";
    final String SPORTS = "SPORTS";
    final String TRAVEL = "TRAVEL";
    final String TECHNOLOGY = "TECHNOLOGY";
    final String SWITCH = "SWITCH";
    final String NOTIFY = "NOTIFY";
    final String CATEGORY = "CATEGORY";
    private final String mTag = getClass().getSimpleName();
    @BindView(R.id.search_bar_et)
    EditText mSearchBar;
    @BindView(R.id.category_checkbox_arts)
    CheckBox mArts;
    @BindView(R.id.category_checkbox_business)
    CheckBox mBusiness;
    @BindView(R.id.category_checkbox_politics)
    CheckBox mPolitics;
    @BindView(R.id.category_checkbox_sports)
    CheckBox mSports;
    @BindView(R.id.category_checkbox_travel)
    CheckBox mTravel;
    @BindView(R.id.category_checkbox_technology)
    CheckBox mTechnology;
    @BindView(R.id.activity_notification_sw)
    Switch mSwitch;

    private PendingIntent mPendingIntent;
    private SharedPreferences mSharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        //load the view
        ButterKnife.bind(this);


        mSharedPref = getApplication().getSharedPreferences(NOTIFY, Context.MODE_PRIVATE);

        String searchQuery = mSharedPref.getString(SEARCH, "");
        mSearchBar.setText(searchQuery);
        mArts.setChecked(mSharedPref.getBoolean(ARTS, false));
        mBusiness.setChecked(mSharedPref.getBoolean(BUSINESS, false));
        mPolitics.setChecked(mSharedPref.getBoolean(POLITICS, false));
        mSports.setChecked(mSharedPref.getBoolean(SPORTS, false));
        mTravel.setChecked(mSharedPref.getBoolean(TRAVEL, false));
        mTechnology.setChecked(mSharedPref.getBoolean(TECHNOLOGY, false));
        mSwitch.setChecked(mSharedPref.getBoolean(SWITCH, false));

        if (searchQuery != "") {
            this.configureEnableUI(false);
        }
        // this.configureNotificationChannel();
        //1 - Configuring Toolbar
        this.configureAlarmManager();
        this.configureToolbar();
        this.configureNotification();

    }

    private void configureAlarmManager() {
        Intent alarmIntent = new Intent(NotificationActivity.this, AlarmReceiver.class);
        mPendingIntent = PendingIntent.getBroadcast(NotificationActivity.this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void startAlarm() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 0, 24*60*60*1000, mPendingIntent);
        Log.e(mTag, "Alarm Start");
    }

    private void stopAlarm() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(mPendingIntent);
        Log.e(mTag, "Alarm Stop");
    }

    private void configureNotification() {
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                String[] tabsCategory = getResources().getStringArray(R.array.category);

                SharedPreferences.Editor editor = mSharedPref.edit();

                Boolean jump = false;

                if (mSharedPref.getBoolean(SWITCH, false)) {
                    jump = true;
                }

                if (isChecked) {
                    if (!jump) {
                        //Do something when Switch button is on/checked
                        Log.d(mTag, "ON");

                        String category = "news_desk:(";

                        editor.putString(SEARCH, String.valueOf(mSearchBar.getText()));
                        editor.putBoolean(SWITCH, true);

                        int cmpt = 0;
                        if (mArts.isChecked()) {
                            category += tabsCategory[0];
                            editor.putBoolean(ARTS, true);
                            cmpt++;
                        }
                        if (mBusiness.isChecked()) {
                            category += tabsCategory[1];
                            editor.putBoolean(BUSINESS, true);
                            cmpt++;
                        }
                        if (mPolitics.isChecked()) {
                            category += tabsCategory[2];
                            editor.putBoolean(POLITICS, true);
                            cmpt++;
                        }
                        if (mSports.isChecked()) {
                            category += tabsCategory[3];
                            editor.putBoolean(SPORTS, true);
                            cmpt++;
                        }
                        if (mTravel.isChecked()) {
                            category += tabsCategory[4];
                            editor.putBoolean(TRAVEL, true);
                            cmpt++;
                        }
                        if (mTechnology.isChecked()) {
                            category += tabsCategory[5];
                            editor.putBoolean(TECHNOLOGY, true);
                            cmpt++;
                        }
                        category += ")";


                        int lengthSearch = mSearchBar.getText().toString().length();

                        if (cmpt >= 1 && lengthSearch > 0) {


                            editor.putString(CATEGORY, category);
                            editor.commit();
                            Log.e(mTag, category);
                            configureEnableUI(false);

                            startAlarm();

                            startSearch(category);
                        }

                        if (cmpt==0 || lengthSearch==0) {

                            mSwitch.setChecked(false);
                            String alertText = "Choose at least one category";
                            if (lengthSearch == 0) {
                                alertText = "Enter a text in the search bar";
                            }

                            mSearchBar.isFocused();
                            Toast toast = Toast.makeText(getApplicationContext(), alertText, Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                            toast.show();

                        }
                    }
                } else {
                    stopAlarm();
                    editor.putBoolean(SWITCH, false);
                    editor.clear();
                    editor.commit();

                    configureEnableUI(true);
                    //Do something when Switch is off/unchecked
                    Log.d(mTag, "OFF");
                }
            }


        });
    }

    private void startSearch(String category) {
        Intent intent = new Intent(NotificationActivity.this, ListSearchActivity.class);
        intent.putExtra("category", category);
        intent.putExtra("query", (String.valueOf(mSearchBar.getText())));
        intent.putExtra("notif", true);

        NotificationActivity.this.startActivity(intent);
    }


    private void configureEnableUI(Boolean enable) {
        mSearchBar.setEnabled(enable);
        mTechnology.setEnabled(enable);
        mTravel.setEnabled(enable);
        mSports.setEnabled(enable);
        mPolitics.setEnabled(enable);
        mBusiness.setEnabled(enable);
        mArts.setEnabled(enable);
    }

    private void configureToolbar() {

        //Get the toolbar (Serialise)
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Set the toolbar
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

    }

}
