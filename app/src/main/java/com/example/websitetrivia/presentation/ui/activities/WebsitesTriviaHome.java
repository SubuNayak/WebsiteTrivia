package com.example.websitetrivia.presentation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.websitetrivia.R;

import com.example.websitetrivia.domain.executor.impl.ThreadExecutor;
import com.example.websitetrivia.domain.model.Website;
import com.example.websitetrivia.domain.repository.WebsitesRepository;
import com.example.websitetrivia.presentation.presenters.MainPresenter;
import com.example.websitetrivia.presentation.presenters.impl.MainPresenterImpl;
import com.example.websitetrivia.presentation.ui.adapters.WebsitesAdapter;
import com.example.websitetrivia.presentation.ui.fragments.AddWebsiteFragment;
import com.example.websitetrivia.presentation.ui.fragments.PlaceholderFragment;
import com.example.websitetrivia.storage.database.WebsitesDatabase;
import com.example.websitetrivia.threading.MainThreadImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Websites. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link WebsiteDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class WebsitesTriviaHome extends AppCompatActivity implements MainPresenter.View,
        WebsitesAdapter.Callback{

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private List<Website> mData;
    private MainPresenter mPresenter;
    private WebsitesAdapter mAdapter;

    private static final String FIRSTRUNKEY = "firstrun";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website_trivia_home);

        if (findViewById(R.id.website_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.website_detail_container, new PlaceholderFragment()).commit();
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        View recyclerView = findViewById(R.id.website_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        mPresenter = new MainPresenterImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(),
                this, WebsitesRepository.getInstance(WebsitesDatabase.getInstance(this)));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.homemenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home_menu_add:
                if(!mTwoPane) {
                    Intent startAdd = new Intent(this, AddWebsiteActivity.class);
                    startActivity(startAdd);
                }
                else{
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.website_detail_container, new AddWebsiteFragment()).commit();
                }
                return true;
                default:
                    return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFirstRun()) {
            mPresenter.runFirstTimeOps(this);
        } else {
            mPresenter.resume();
        }
    }

    private boolean isFirstRun(){
        return PreferenceManager.getDefaultSharedPreferences(this).getBoolean(FIRSTRUNKEY, true);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        mData = new ArrayList<Website>();
        mAdapter = new WebsitesAdapter(mData, getSupportFragmentManager(), mTwoPane, this);
        recyclerView.setAdapter(mAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                ((LinearLayoutManager)recyclerView.getLayoutManager()).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void updateRecyclerView(List<Website> websites){
        if(websites!=null) {
            mData.clear();
            mData.addAll(websites);
            mAdapter.notifyDataSetChanged();
        }
    }
    public void refreshUI(){
        updateRecyclerView();
    }
    private void updateRecyclerView(){
        mPresenter.getWebsites();
    }
    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void firstTimeOpsFinished() {

        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean(FIRSTRUNKEY, false).apply();
        updateRecyclerView();

    }

    @Override
    public void showWebsites(List<Website> websites) {
        updateRecyclerView(websites);

    }


    @Override
    public void onWebsiteDeleted() {
        updateRecyclerView();
    }

    @Override
    public void deleteSelectedWebsite(String id) {
        mPresenter.deleteWebsite(id);
    }



}
