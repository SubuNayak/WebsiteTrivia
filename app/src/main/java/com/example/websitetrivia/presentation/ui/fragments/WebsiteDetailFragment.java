package com.example.websitetrivia.presentation.ui.fragments;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.websitetrivia.R;
import com.example.websitetrivia.domain.executor.impl.ThreadExecutor;
import com.example.websitetrivia.domain.model.Website;
import com.example.websitetrivia.domain.repository.WebsitesRepository;
import com.example.websitetrivia.presentation.presenters.UpdateWebsitePresenter;
import com.example.websitetrivia.presentation.presenters.impl.UpdatePresenterImpl;
import com.example.websitetrivia.presentation.ui.activities.WebsiteDetailActivity;
import com.example.websitetrivia.presentation.ui.activities.WebsitesTriviaHome;
import com.example.websitetrivia.storage.database.WebsitesDatabase;
import com.example.websitetrivia.threading.MainThreadImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A fragment representing a single Website detail screen.
 * This fragment is either contained in a {@link WebsitesTriviaHome}
 * in two-pane mode (on tablets) or a {@link WebsiteDetailActivity}
 * on handsets.
 */
public class WebsiteDetailFragment extends Fragment implements UpdateWebsitePresenter.View{
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    public static final String HOST_CLASS_NAME="com.example.websitetrivia.presentation.ui.activities.WebsitesTriviaHome";

    private Website mItem;
    private UpdateWebsitePresenter mPresenter;

    @BindView(R.id.website_detail_et_name) TextInputEditText mName;
    @BindView(R.id.website_detail_et_founding_year) TextInputEditText mFoundingYear;
    @BindView(R.id.website_detail_et_founders) TextInputEditText mFounders;
    @BindView(R.id.website_detail_et_location) TextInputEditText mLocation;
    @BindView(R.id.website_detail_et_CEO) TextInputEditText mCEO;
    @BindView(R.id.website_detail_et_rank) TextInputEditText mRank;
    @BindView(R.id.website_detail_et_time_spent) TextInputEditText mTimeSpent;
    @BindView(R.id.website_detail_btn_save) Button mSave;
    private CollapsingToolbarLayout appBarLayout;

    public WebsiteDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {


            mPresenter = new UpdatePresenterImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this,
                    WebsitesRepository.getInstance(WebsitesDatabase.getInstance(getContext())));

            Activity activity = this.getActivity();
            appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);

            mPresenter.getWebsiteById(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.website_detail_element, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    private void updateUI(Website website){
        updateToolbarTitle(website.getName());
        mName.setText(website.getName());
        mFoundingYear.setText(Integer.toString(website.getFoundingYear()));
        mFounders.setText(website.getFounders());
        mLocation.setText(website.getLocation());
        mCEO.setText(website.getCEO());
        mRank.setText(Integer.toString(website.getRank()));
        mTimeSpent.setText(website.getTimeSpent());
    }
    private void updateToolbarTitle(String title){
        if (appBarLayout != null) {
           appBarLayout.setTitle(title==""?"Details":title);
        }
    }
    @OnClick(R.id.website_detail_btn_save)
    void updateWebsiteDetails(){
        int rank = getIntegerFromEditText(mRank);
        int foundingYear = getIntegerFromEditText(mFoundingYear);
        if(foundingYear==-1){
            Toast.makeText(getActivity(), "Please enter a valid founding year", Toast.LENGTH_SHORT).show();
            return;

        }
        if(rank==-1){
            Toast.makeText(getActivity(), "Please enter a valid rank", Toast.LENGTH_SHORT).show();
            return;
        }
        mPresenter.updateWebsite(mItem, mName.getText().toString(), Integer.parseInt(mFoundingYear.getText().toString()), mFounders.getText().toString(),
                mLocation.getText().toString(), mCEO.getText().toString(), Integer.parseInt(mRank.getText().toString()),
                    mTimeSpent.getText().toString());


        if(getActivity().getClass().getName().equals(HOST_CLASS_NAME)){
            ((WebsitesTriviaHome)getActivity()).refreshUI();
        }
    }
    private int getIntegerFromEditText(EditText editText){
        if(checkIntValidity(editText.getText().toString())){
            return Integer.parseInt(editText.getText().toString());
        }
        else{
            return -1;
        }
    }
    private boolean checkIntValidity(String input){
        int tmp = -1;
        try {
            tmp = Integer.parseInt(input);
        }
        catch(NumberFormatException e){
            e.printStackTrace();
        }
        if(tmp<Integer.MAX_VALUE&&tmp>=0){
            return true;
        }
        return false;

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
    public void onGetWebsite(Website website) {
        mItem = website;
        updateUI(mItem);
    }

    @Override
    public void onWebsiteUpdated(Website website) {
        mItem = website;
        updateUI(website);
        Toast.makeText(getActivity(), "Website Details updated", Toast.LENGTH_SHORT).show();
    }
}
