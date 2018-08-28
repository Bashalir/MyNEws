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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.oc.bashalir.mynews.Controllers.Utils.NYTStreams;
import com.oc.bashalir.mynews.Controllers.Utils.NotificationReceiver;
import com.oc.bashalir.mynews.Controllers.Utils.Utilities;
import com.oc.bashalir.mynews.Models.ArticleSearch;
import com.oc.bashalir.mynews.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * schedule a notification
 */
public class NotificationActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "NOTIFY";
    final String ID_SEARCH = "ID_SEARCH";
    final String DATE_SEARCH = "DATE_SEARCH";
    final String NOTIFY = "NOTIFY";
    final String SEARCH = "SEARCH";
    final String ARTS = "ARTS";
    final String BUSINESS = "BUSINESS";
    final String POLITICS = "POLITICS";
    final String SPORTS = "SPORTS";
    final String TRAVEL = "TRAVEL";
    final String TECHNOLOGY = "TECHNOLOGY";
    final String SWITCH = "SWITCH";
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
    private List<ArticleSearch.Response.Doc> mSearch;
    private Disposable mDisp;
    private PendingIntent mPendingIntent;
    private SharedPreferences mSharedPref;

    /**
     * at startup configure the notification
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        //load the view
        ButterKnife.bind(this);

        //defined or load attributes
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

        this.configureAlarmManager();
        this.configureToolbar();
        this.configureNotification();

    }

    /**
     * Configure AlarmManager
     */
    private void configureAlarmManager() {
        Intent alarmIntent = new Intent(NotificationActivity.this, NotificationReceiver.class);
        mPendingIntent = PendingIntent.getBroadcast(NotificationActivity.this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * Launch the alarm
     */
    private void startAlarm() {

        // The notification starts at 9 am
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, 9);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);


        //configure the alarmmanager
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 0, cal.getTimeInMillis(), mPendingIntent);
        Log.d(mTag, "Alarm Start");
    }

    /**
     * unschedule the alarm
     */
    private void stopAlarm() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(mPendingIntent);
        Log.d(mTag, "Alarm Stop");
    }

    /**
     * manage UI of the activity
     */
    private void configureNotification() {

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            /**
             * Manage Switch button
             * @param buttonView
             * @param isChecked
             */
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

                        //Configure search and start alarm when Switch button is on/checked
                        Log.d(mTag, "ON");


                        //define and save the category choices
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


                        //check that the request is not empty
                        int lengthSearch = mSearchBar.getText().toString().length();
                        if (cmpt >= 1 && lengthSearch > 0) {

                            editor.putString(CATEGORY, category);
                            editor.commit();
                            Log.d(mTag, category);
                            configureEnableUI(false);

                            //launch the Alarm and the Search request
                            startAlarm();
                            startSearch(category);
                        }

                        //check that the request or category checkboxes is not empty
                        if (cmpt == 0 || lengthSearch == 0) {

                            mSwitch.setChecked(false);
                            String alertText = "Choose at least one category";
                            if (lengthSearch == 0) {
                                alertText = "Enter a text in the search bar";
                            }

                            mSearchBar.isFocused();

                            //send a pop-up to inform
                            Toast toast = Toast.makeText(getApplicationContext(), alertText, Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                            toast.show();

                        }
                    }
                    //Clear the search and stop alarm when Switch is off/unchecked
                } else {
                    stopAlarm();
                    editor.putBoolean(SWITCH, false);
                    editor.clear();
                    editor.commit();

                    configureEnableUI(true);

                    Log.d(mTag, "OFF");
                }
            }


        });
    }

    /**
     * Launch the stream request
     *
     * @param category
     */
    private void startSearch(String category) {

        mSearch = new ArrayList<>();

        mDisp = NYTStreams.streamFetchSearch(String.valueOf(mSearchBar.getText()), category, null, null).subscribeWith(new DisposableObserver<ArticleSearch>() {


            @Override
            public void onNext(ArticleSearch articleSearch) {
                Log.d(mTag, "NEXT");
                updateUIWithList(articleSearch);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(mTag, "On Error " + Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.d(mTag, "On Complete !!");

                //save the date and the id of the first article if it's not empty
                if (!mSearch.isEmpty()) {

                    String idFirstSearch = mSearch.get(0).getId();
                    String dateFistSearch = new Utilities().DateFormatterSearch(mSearch.get(0).getPubDate(), "yyyy-MM-dd'T'HH:mm:ssZZZZZ");

                    Log.d(mTag, idFirstSearch + " " + dateFistSearch);
                    SharedPreferences sharedPref = getApplication().getSharedPreferences(NOTIFY, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(ID_SEARCH, idFirstSearch);
                   editor.putString(DATE_SEARCH, dateFistSearch);

                    editor.commit();
                }
            }
        });
    }

    /**
     * Load the Stream
     *
     * @param articleSearch
     */
    private void updateUIWithList(ArticleSearch articleSearch) {

        mSearch.addAll(articleSearch.getResponse().getDocs());
    }

    /**
     * activate checkboxes
     *
     * @param enable
     */
    private void configureEnableUI(Boolean enable) {
        mSearchBar.setEnabled(enable);
        mTechnology.setEnabled(enable);
        mTravel.setEnabled(enable);
        mSports.setEnabled(enable);
        mPolitics.setEnabled(enable);
        mBusiness.setEnabled(enable);
        mArts.setEnabled(enable);
    }

    /**
     * Configure Toolbar
     */
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
