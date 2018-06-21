package com.oc.bashalir.mynews.Views.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oc.bashalir.mynews.Models.TopStories;
import com.oc.bashalir.mynews.R;
import com.oc.bashalir.mynews.Views.NewsHolder;

import java.util.List;

public class TechAdapter extends RecyclerView.Adapter<NewsHolder>{

    private List<TopStories.Result> mTechnology;

    public TechAdapter(List<TopStories.Result> technology) {
        mTechnology=technology;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_page_news,parent,false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        holder.updateWithNews(mTechnology.get(position));
    }

    @Override
    public int getItemCount() {
        return mTechnology.size();
    }
}
