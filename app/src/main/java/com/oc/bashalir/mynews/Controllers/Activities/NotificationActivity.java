package com.oc.bashalir.mynews.Controllers.Activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.oc.bashalir.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationActivity extends AppCompatActivity {

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

    boolean[] mCheckboxTab = {false, false, false, false, false, false};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        //load the view
        ButterKnife.bind(this);
        //1 - Configuring Toolbar
        this.configureToolbar();
        this.configureNotification();

    }

    private void configureNotification() {
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    //Do something when Switch button is on/checked
                    Log.d(mTag, "ON");

                    String category = "news_desk:(";

                    int cmpt=0;
                    if (mCheckboxTab[0] == true) {
                        category += "\"arts\"";
                        cmpt++;
                    }
                    if (mCheckboxTab[1] == true) {
                        category += "\"business\"";
                        cmpt++;
                    }
                    if (mCheckboxTab[2] == true) {
                        category += "\"politics\"";
                        cmpt++;
                    }
                    if (mCheckboxTab[3] == true) {
                        category += "\"sports\"";
                        cmpt++;
                    }
                    if (mCheckboxTab[4] == true) {
                        category += "\"travel\"";
                        cmpt++;
                    }
                    if (mCheckboxTab[5] == true) {
                        category += "\"technology\"";
                        cmpt++;
                    }
                    category +=")";

                    if (cmpt<1){category="";}
                    Log.e(mTag, category);
                    

                }
                else
                {
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
