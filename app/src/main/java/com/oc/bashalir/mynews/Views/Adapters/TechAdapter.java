package com.oc.bashalir.mynews.Views.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oc.bashalir.mynews.Models.ArticleSearch;
import com.oc.bashalir.mynews.R;
import com.oc.bashalir.mynews.Views.SearchHolder;

import java.util.List;

public class TechAdapter extends RecyclerView.Adapter<SearchHolder>{

    private List<ArticleSearch.Response.Doc> mTechnology;

    public TechAdapter(List<ArticleSearch.Response.Doc> technology) {

        mTechnology=technology;
    }

    @NonNull
    @Override
    public SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_page_news,parent,false);
        return new SearchHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHolder holder, int position) {

        holder.updateWithNews(mTechnology.get(position));
    }

    @Override
    public int getItemCount() {
        return mTechnology.size();
    }
}
