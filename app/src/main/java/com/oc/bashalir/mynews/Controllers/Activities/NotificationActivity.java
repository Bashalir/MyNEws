package com.oc.bashalir.mynews.Controllers.Activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        //load the view
        ButterKnife.bind(this);
        //1 - Configuring Toolbar
        this.configureToolbar();

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
                if (checked){
                    Log.d(mTag, "Arts");

                }else
                {
                }
                break;
            case R.id.category_checkbox_business:
                if (checked){
                    Log.d(mTag, "Business");
                }
                break;
            case R.id.category_checkbox_politics:
                if (checked){
                    Log.d(mTag, "Politics");
                }
                break;
            case R.id.category_checkbox_sports:
                if (checked){
                    Log.d(mTag, "Sports");
                }
                break;
            case R.id.category_checkbox_travel:
                if (checked){
                    Log.d(mTag, "Travel");
                }
                break;
            case R.id.category_checkbox_technology:
                if (checked){
                    Log.d(mTag, "Technology");
                }

        }
    }


}
