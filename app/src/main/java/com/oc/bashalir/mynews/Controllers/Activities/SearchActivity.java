package com.oc.bashalir.mynews.Controllers.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.oc.bashalir.mynews.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Define a search query
 */
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

    String mBegin = "", mEnd = "";
    int beginDay, beginMonth, beginYear;
    int endDay, endMonth, endYear;
    int cmpt = 0;

    Calendar mCurrentDate;
    Calendar mFirstDate;

    /**
     * at startup configure the ui
     *
     * @param savedInstanceState
     */
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

    /**
     * Define a search query and launch the list
     */
    private void configureSearch() {


        mSearchButton.setOnClickListener(new View.OnClickListener() {

            /**
             * launch query when search button is clicked
             * @param v
             */
            @Override
            public void onClick(View v) {
                String category = "news_desk:(";
                String[] tabsCategory = getResources().getStringArray(R.array.category);

                cmpt = 0;
                if (mArts.isChecked()) {
                    category += tabsCategory[0];
                    cmpt++;
                }
                if (mBusiness.isChecked()) {
                    category += tabsCategory[1];
                    cmpt++;
                }
                if (mPolitics.isChecked()) {
                    category += tabsCategory[2];
                    cmpt++;
                }
                if (mSports.isChecked()) {
                    category += tabsCategory[3];
                    cmpt++;
                }
                if (mTravel.isChecked()) {
                    category += tabsCategory[4];
                    cmpt++;
                }
                if (mTechnology.isChecked()) {
                    category += tabsCategory[5];
                    cmpt++;
                }
                category += ")";

                int lengthSearch = mSearchBar.getText().toString().length();

                if (cmpt >= 1 && lengthSearch > 0) {
                    Log.e(mTag, category + " *** " + mBegin + " *** " + mEnd + " **** ");

                    //launch ListSearchActivity with its attributes
                    Intent intent = new Intent(SearchActivity.this, ListSearchActivity.class);
                    intent.putExtra("category", category);
                    intent.putExtra("query", (String.valueOf(mSearchBar.getText())));
                    intent.putExtra("begin", mBegin);
                    intent.putExtra("end", mEnd);

                    SearchActivity.this.startActivity(intent);

                    //check that the request or category checkboxes is not empty
                } else {

                    String alertText = "Choose at least one category";
                    if (lengthSearch == 0) {
                        alertText = "Enter a text in the search bar";
                    }

                    //send a pop-up to inform
                    mSearchBar.isFocused();
                    Toast toast = Toast.makeText(getApplicationContext(), alertText, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();


                }


            }
        });
    }

    /**
     * Configure DatePicker
     */
    private void configureDatePicker() {

        mCurrentDate = Calendar.getInstance();
        mFirstDate = Calendar.getInstance();
        // mFirstDate.set(1900, Calendar.JANUARY, 1);

        beginDay = mFirstDate.get(Calendar.DAY_OF_MONTH);
        beginMonth = mFirstDate.get(Calendar.MONTH);
        beginYear = mFirstDate.get(Calendar.YEAR);

        mBegin = null;
        mEnd = null;

        mBeginDate.setOnClickListener(new View.OnClickListener() {

            /**
             *  select the selected begin date
             * @param v
             */
            @Override
            public void onClick(View v) {
                int beginMonth1 = beginMonth + 1;
                mBegin = beginYear + String.format("%02d", beginMonth + 1) + String.format("%02d", beginDay);
                mBeginDate.setText(beginDay + "/" + beginMonth1 + "/" + beginYear);

                DatePickerDialog datePickerDialog = new DatePickerDialog(SearchActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month++;
                        mBeginDate.setText(dayOfMonth + "/" + month + "/" + year);
                        mBegin = year + String.format("%02d", month) + String.format("%02d", dayOfMonth);
                    }
                }, beginYear, beginMonth, beginDay);
                datePickerDialog.show();
            }
        });

        endDay = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        endMonth = mCurrentDate.get(Calendar.MONTH);
        endYear = mCurrentDate.get(Calendar.YEAR);


        mEndDate.setOnClickListener(new View.OnClickListener() {

            /**
             * select the selected end date
             * @param v
             */
            @Override
            public void onClick(View v) {
                int endMonth1 = endMonth + 1;
                mEnd = endYear + String.format("%02d", endMonth + 1) + String.format("%02d", endDay);

                mEndDate.setText(endDay + "/" + endMonth1 + "/" + endYear);
                DatePickerDialog datePickerDialog = new DatePickerDialog(SearchActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month++;
                        mEndDate.setText(dayOfMonth + "/" + month + "/" + year);
                        mEnd = year + String.format("%02d", month) + String.format("%02d", dayOfMonth);
                    }
                }, endYear, endMonth, endDay);
                datePickerDialog.show();
            }
        });

    }


}
