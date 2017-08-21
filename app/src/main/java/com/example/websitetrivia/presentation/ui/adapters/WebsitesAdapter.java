package com.example.websitetrivia.presentation.ui.adapters;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.websitetrivia.R;
import com.example.websitetrivia.domain.model.Website;
import com.example.websitetrivia.presentation.ui.activities.WebsiteDetailActivity;
import com.example.websitetrivia.presentation.ui.fragments.WebsiteDetailFragment;
import com.example.websitetrivia.presentation.ui.fragments.PlaceholderFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebsitesAdapter extends RecyclerView.Adapter<WebsitesAdapter.ViewHolder> {


    public interface Callback{
        void deleteSelectedWebsite(String id);
    }

    WebsitesAdapter.Callback mCallback;
    private final List<Website> mValues;
    private boolean mTwoPane = false;
    private FragmentManager mSupportFragmentManager;
    public WebsitesAdapter(List<Website> websites,
                           FragmentManager fragmentManager, boolean twopane, WebsitesAdapter.Callback callback){

        mValues = websites;
        mTwoPane = twopane;
        mCallback = callback;
        mSupportFragmentManager = fragmentManager;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.website_list_content, parent, false);
        return new WebsitesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        Resources resources = holder.itemView.getContext().getResources();
        holder.mName.setText(holder.mItem.getName());
        holder.mFoundingYear.setText(String.format(resources.getString(R.string.founding_year_text),
              Integer.toString(holder.mItem.getFoundingYear())));
        holder.mFounders.setText(String.format(resources.getString(R.string.founders_text), holder.mItem.getFounders()));
        holder.mLocation.setText(String.format(resources.getString(R.string.location_text),holder.mItem.getLocation()));
        holder.mCEO.setText(String.format(resources.getString(R.string.CEO_text),holder.mItem.getCEO()));
        holder.mRank.setText(String.format(resources.getString(R.string.rank_text),Integer.toString(holder.mItem.getRank())));
        holder.mTimeSpent.setText(String.format(resources.getString(R.string.time_spent_text),holder.mItem.getTimeSpent()));


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(WebsiteDetailFragment.ARG_ITEM_ID, holder.mItem.getId());
                    WebsiteDetailFragment fragment = new WebsiteDetailFragment();
                    fragment.setArguments(arguments);
                    mSupportFragmentManager.beginTransaction()
                            .replace(R.id.website_detail_container, fragment)
                            .commit();
                } else {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, WebsiteDetailActivity.class);
                    intent.putExtra(WebsiteDetailFragment.ARG_ITEM_ID, holder.mItem.getId());

                    context.startActivity(intent);
                }
            }
        });
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showPopupMenu(v, holder);
                return true;
            }
        });
    }
    private void showPopupMenu(final View anchor, final ViewHolder holder){
        PopupMenu popupMenu = new PopupMenu(anchor.getContext(), anchor);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.deletepopup, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                processDeleteOperation(holder);
                return true;
            }
        });
        popupMenu.show();
    }
    private void processDeleteOperation(ViewHolder holder){
        if(mTwoPane){
            informDeleteToActivity(holder.mItem.getId());
            mSupportFragmentManager.beginTransaction()
                    .replace(R.id.website_detail_container, new PlaceholderFragment())
                    .commit();

        } else {
            informDeleteToActivity(holder.mItem.getId());
        }

    }
    private void informDeleteToActivity(String id){
        if(mCallback!=null){
            mCallback.deleteSelectedWebsite(id);
        }
    }
    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        @BindView(R.id.website_list_tv_name) TextView mName;
        @BindView(R.id.website_list_tv_founding_year) TextView mFoundingYear;
        @BindView(R.id.website_list_tv_founders) TextView mFounders;
        @BindView(R.id.website_list_tv_location) TextView mLocation;
        @BindView(R.id.website_list_tv_CEO) TextView mCEO;
        @BindView(R.id.website_list_tv_rank) TextView mRank;
        @BindView(R.id.website_list_tv_time_spent) TextView mTimeSpent;

        Website mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mName.getText() + "'";
        }
    }
}
