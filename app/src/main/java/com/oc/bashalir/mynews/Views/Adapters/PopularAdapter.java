package com.oc.bashalir.mynews.Views.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oc.bashalir.mynews.Models.MostPopular;
import com.oc.bashalir.mynews.R;
import com.oc.bashalir.mynews.Views.PopularHolder;

import java.util.List;


public class PopularAdapter extends RecyclerView.Adapter<PopularHolder> {

    private List<MostPopular.Result> mMostPopular;


    /**
     * Constructor
     *
     * @param mostPopular
     */
    public PopularAdapter(List<MostPopular.Result> mostPopular) {
        mMostPopular = mostPopular;
    }

    /**
     * CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public PopularHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_page_news, parent, false);
        return new PopularHolder(view);
    }

    /**
     * UPDATE VIEW HOLDER WITH mMostPopular
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull PopularHolder holder, int position) {
        holder.updateWithPopular(mMostPopular.get(position));
    }


    /***
     *  Get the size of list
     * @return
     */
    @Override
    public int getItemCount() {
        return mMostPopular.size();
    }


}
