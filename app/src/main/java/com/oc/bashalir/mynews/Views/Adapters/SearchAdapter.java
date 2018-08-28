package com.oc.bashalir.mynews.Views.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oc.bashalir.mynews.Models.ArticleSearch;
import com.oc.bashalir.mynews.R;
import com.oc.bashalir.mynews.Views.SearchHolder;

import java.util.List;

/**
 *
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchHolder> {

    private List<ArticleSearch.Response.Doc> mSearch;

    /**
     * Constructor
     *
     * @param search
     */
    public SearchAdapter(List<ArticleSearch.Response.Doc> search) {

        mSearch = search;
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
    public SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_page_news, parent, false);
        return new SearchHolder(view);
    }

    /**
     * UPDATE VIEW HOLDER WITH mTechnology
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull SearchHolder holder, int position) {

        holder.updateWithNews(mSearch.get(position));
    }

    /**
     * Get the size of list
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mSearch.size();
    }
}
