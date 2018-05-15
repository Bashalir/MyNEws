package com.oc.bashalir.mynews.Views.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oc.bashalir.mynews.Models.TopStories;
import com.oc.bashalir.mynews.R;
import com.oc.bashalir.mynews.Views.NewsViewHolder;

import java.util.List;

public class ListNewsAdapter extends RecyclerView.Adapter<NewsViewHolder>{

    private List<TopStories> topStories;

    public ListNewsAdapter(List<TopStories> topStories) {
        this.topStories=topStories;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_page_news,parent,false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        holder.updateWithNews(this.topStories.get(position));
    }

    @Override
    public int getItemCount() {
        return this.topStories.size();
    }
}
