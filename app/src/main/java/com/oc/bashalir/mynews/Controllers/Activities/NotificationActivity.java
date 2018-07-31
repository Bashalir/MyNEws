package com.oc.bashalir.mynews.Controllers.Activities;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
    private final String mTag = getClass().getSimpleName();
    private PendingIntent mPendingIntent;
    private SharedPreferences mSharedPref;

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

    final String SEARCH = "SEARCH";
    final String ARTS ="ARTS";
    final String BUSINESS ="BUSINESS";
    final String POLITICS ="POLITICS";
    final String SPORTS ="SPORTS";
    final String TRAVEL ="TRAVEL";
    final String TECHNOLOGY ="TECHNOLOGY";
    final String SWITCH ="SWITCH";

    boolean[] mCheckboxTab = {false, false, false, false, false, false};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        //load the view
        ButterKnife.bind(this);


        mSharedPref = getApplication().getSharedPreferences("notification", Context.MODE_PRIVATE);

        mSearchBar.setText(mSharedPref.getString(SEARCH,""));
        mArts.setChecked(mSharedPref.getBoolean(ARTS,false));
        mBusiness.setChecked(mSharedPref.getBoolean(BUSINESS,false));
        mPolitics.setChecked(mSharedPref.getBoolean(POLITICS,false));
        mSports.setChecked(mSharedPref.getBoolean(SPORTS,false));
        mTravel.setChecked(mSharedPref.getBoolean(TRAVEL,false));
        mTechnology.setChecked(mSharedPref.getBoolean(TECHNOLOGY,false));
        mSwitch.setChecked(mSharedPref.getBoolean(SWITCH,false));

       // this.configureNotificationChannel();
        //1 - Configuring Toolbar
        this.configureAlarmManager();
        this.configureToolbar();
        this.configureNotification();

    }

    private void  configureAlarmManager(){
        Intent alarmIntent = new Intent(NotificationActivity.this, AlarmReceiver.class);
        mPendingIntent=PendingIntent.getBroadcast(NotificationActivity.this,0,alarmIntent,PendingIntent.FLAG_UPDATE_CURRENT);
    }
    private void startAlarm() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
       manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,0,10000, mPendingIntent);
        Toast.makeText(this,"alarm start",Toast.LENGTH_SHORT).show();
    }
    private void stopAlarm() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(mPendingIntent);
        Toast.makeText(this,"alarm Stop",Toast.LENGTH_SHORT).show();
    }

    private void configureNotification() {
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                String[] tabsCategory = getResources().getStringArray(R.array.category);

                SharedPreferences.Editor editor =mSharedPref.edit();

                Boolean jump=false;

                if (mSharedPref.getBoolean(SWITCH,false)) {
                    jump=true;
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

                            if (cmpt < 1) {
                                category = "";
                            }
                            editor.commit();
                            Log.e(mTag, category);

                            startAlarm();
                        }
                    } else {
                        stopAlarm();
                        editor.putBoolean(SWITCH, false);
                        editor.clear();
                        editor.commit();
                        //Do something when Switch is off/unchecked
                        Log.d(mTag, "OFF");
                    }
                }



        });
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

    private void newNotification(){
        final NotificationManager mNotification = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        final Intent launchNotifiactionIntent = new Intent(this, NotificationActivity.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this,
                1, launchNotifiactionIntent,
                PendingIntent.FLAG_ONE_SHOT);

        Notification.Builder builder = new Notification.Builder(this)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_menu)
                .setContentTitle("Titre")
                .setContentText("Texte")
                .setContentIntent(pendingIntent);

        mNotification.notify(1, builder.build());
    }


    private void createNotification(){
    // Create an explicit intent for an Activity in your app
    Intent intent = new Intent(this, NotificationActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_menu)
            .setContentTitle("My notification")
            .setContentText("Hello World!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, mBuilder.build());
}


    private void configureNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name ="channel_name";
            String description = "channel_description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }



    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.category_checkbox_arts:
                if (checked) {
                    Log.d(mTag, "Arts");

                    mCheckboxTab[0] = true;
                } else {
                    mCheckboxTab[0] = false;
                }
                break;
            case R.id.category_checkbox_business:
                if (checked) {
                    Log.d(mTag, "Business");
                    mCheckboxTab[1] = true;
                } else {
                    mCheckboxTab[1] = false;
                }
                break;
            case R.id.category_checkbox_politics:
                if (checked) {
                    Log.d(mTag, "Politics");
                    mCheckboxTab[2] = true;
                } else {
                    mCheckboxTab[2] = false;
                }
                break;
            case R.id.category_checkbox_sports:
                if (checked) {
                    Log.d(mTag, "Sports");
                    mCheckboxTab[3] = true;
                } else {
                    mCheckboxTab[3] = false;
                }
                break;
            case R.id.category_checkbox_travel:
                if (checked) {
                    Log.d(mTag, "Travel");
                    mCheckboxTab[4] = true;
                } else {
                    mCheckboxTab[4] = false;
                }
                break;
            case R.id.category_checkbox_technology:
                if (checked) {
                    Log.d(mTag, "Technology");
                    mCheckboxTab[5] = true;
                } else {
                    mCheckboxTab[5] = false;
                }
                break;


        }
    }



}
