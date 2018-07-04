package com.oc.bashalir.mynews.Controllers.Activities;

import android.app.DatePickerDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.oc.bashalir.mynews.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SearchActivity extends AppCompatActivity {

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
    @BindView(R.id.activity_search_btn)
    Button mSearchButton;

    @BindView(R.id.activity_search_begin_et)
    TextView mBeginDate;
    @BindView(R.id.activity_search_end_et)
    TextView mEndDate;

    int beginDay, beginMonth, beginYear;
    int endDay, endMonth, endYear;

    boolean[] mCheckboxTab = {false,false,false,false,false,false};

    Calendar mCurrentDate;
    Calendar mFirstDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //load the view
        ButterKnife.bind(this);
        //1 - Configuring Toolbar
        this.configureToolbar();

        this.configureDatePicker();

        this.configureSearch();

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

    private void configureSearch(){
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String category="";

                if (mCheckboxTab[0]==true) {category +="arts";}
                if (mCheckboxTab[1]==true) {category +="business";}
                if (mCheckboxTab[2]==true) {category +="politics";}
                if (mCheckboxTab[3]==true) {category +="sports";}
                if (mCheckboxTab[4]==true) {category +="travel";}
                if (mCheckboxTab[5]==true) {category +="technology";}

                Log.d(mTag, category);
            }
        });
    }


    private void configureDatePicker() {

       mCurrentDate=Calendar.getInstance();
        mFirstDate=Calendar.getInstance();
        mFirstDate.set(1900,Calendar.JANUARY,1);

       beginDay=mFirstDate.get(Calendar.DAY_OF_MONTH);
       beginMonth=mFirstDate.get(Calendar.MONTH);
       beginYear=mFirstDate.get(Calendar.YEAR);

        int beginMonth1=beginMonth+1;

        mBeginDate.setText(beginDay+"/"+beginMonth1+"/"+beginYear);

        mBeginDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(SearchActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mBeginDate.setText(dayOfMonth+"/"+month+"/"+year);
                    }
                },beginYear,beginMonth,beginDay);
                datePickerDialog.show();
            }
        });

       endDay=mCurrentDate.get(Calendar.DAY_OF_MONTH);
       endMonth=mCurrentDate.get(Calendar.MONTH);
       endYear=mCurrentDate.get(Calendar.YEAR);
       int endMonth1=endMonth+1;


       mEndDate.setText(endDay+"/"+endMonth1+"/"+endYear);

        mEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(SearchActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mEndDate.setText(dayOfMonth+"/"+month+"/"+year);
                    }
                },endYear,endMonth+1,endDay);
                datePickerDialog.show();
            }
        });

    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.category_checkbox_arts:
                if (checked){
                    Log.d(mTag, "Arts");

                    mCheckboxTab[0]=true;
                }else
                {
                    mCheckboxTab[0]=false;
                }
                break;
            case R.id.category_checkbox_business:
                if (checked){
                    Log.d(mTag, "Business");
                    mCheckboxTab[1]=true;
                }else
                {
                    mCheckboxTab[1]=false;
                }
                break;
            case R.id.category_checkbox_politics:
                if (checked){
                    Log.d(mTag, "Politics");
                    mCheckboxTab[2]=true;
                }else
                {
                    mCheckboxTab[2]=false;
                }
                break;
            case R.id.category_checkbox_sports:
                if (checked){
                    Log.d(mTag, "Sports");
                    mCheckboxTab[3]=true;
                }else
                {
                    mCheckboxTab[3]=false;
                }
                break;
            case R.id.category_checkbox_travel:
                if (checked){
                    Log.d(mTag, "Travel");
                    mCheckboxTab[4]=true;
                }else
                {
                    mCheckboxTab[4]=false;
                }
                break;
            case R.id.category_checkbox_technology:
                if (checked){
                    Log.d(mTag, "Technology");
                 mCheckboxTab[5]=true;
        }else
        {
            mCheckboxTab[5]=false;
        }
        break;

        }
    }


}
