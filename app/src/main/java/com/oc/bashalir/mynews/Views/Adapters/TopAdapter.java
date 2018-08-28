package com.oc.bashalir.mynews.Views.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oc.bashalir.mynews.Models.TopStories;
import com.oc.bashalir.mynews.R;
import com.oc.bashalir.mynews.Views.TopHolder;

import java.util.List;


/**
 *
 */
public class TopAdapter extends RecyclerView.Adapter<TopHolder> {

    private List<TopStories.Result> mTopStories;

    /**
     * Constructor
     *
     * @param topStories
     */
    public TopAdapter(List<TopStories.Result> topStories) {
        mTopStories = topStories;
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
    public TopHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_page_news, parent, false);
        return new TopHolder(view);
    }

    /**
     * UPDATE VIEW HOLDER WITH mTopStories
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull TopHolder holder, int position) {
        holder.updateWithNews(mTopStories.get(position));
    }

    /**
     * Get the size of list
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mTopStories.size();
    }
}