package com.example.websitetrivia.presentation.ui.activities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.websitetrivia.R;
import com.example.websitetrivia.presentation.ui.fragments.AddWebsiteFragment;
import com.example.websitetrivia.presentation.ui.fragments.WebsiteDetailFragment;

public class AddWebsiteActivity extends AppCompatActivity{
   public static final String FRAGMENT_TAG = "ADDWEBSITE";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);



        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(WebsiteDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(WebsiteDetailFragment.ARG_ITEM_ID));
            AddWebsiteFragment fragment = new AddWebsiteFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.website_detail_container, fragment, FRAGMENT_TAG)
                    .commit();
        }
    }
}
