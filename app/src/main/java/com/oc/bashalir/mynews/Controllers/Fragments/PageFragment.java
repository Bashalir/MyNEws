package com.oc.bashalir.mynews.Controllers.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oc.bashalir.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import icepick.Icepick;
import icepick.State;

/**
 * A simple {@link Fragment} subclass.
 */
public class PageFragment extends Fragment {

    private static final String KEY_POSITION="position";
    private final String mTag = getClass().getSimpleName();

    @BindView(R.id.fragment_page_tv) TextView textView;
  //  @State int mPosition;

    public PageFragment() {
        // Required empty public constructor
    }

    public static PageFragment newInstance(int position) {

        PageFragment frag = new PageFragment();

        Bundle args=new Bundle();
        args.putInt(KEY_POSITION,position);
        frag.setArguments(args);
      //  mPosition=position;
        return (frag);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_page, container, false);
        ButterKnife.bind(this, result);
        Icepick.restoreInstanceState(this, savedInstanceState);
        
        int position=getArguments().getInt(KEY_POSITION,-1);

        textView.setText("Page n° "+position);

        Log.d(mTag, "Page n° "+position);

        return result;
    }

}
