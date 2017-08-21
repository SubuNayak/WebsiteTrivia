package com.example.websitetrivia.presentation.ui.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.websitetrivia.R;
import com.example.websitetrivia.domain.executor.impl.ThreadExecutor;
import com.example.websitetrivia.domain.model.Website;
import com.example.websitetrivia.domain.repository.WebsitesRepository;
import com.example.websitetrivia.presentation.presenters.AddWebsitePresenter;
import com.example.websitetrivia.presentation.presenters.UpdateWebsitePresenter;
import com.example.websitetrivia.presentation.presenters.impl.AddWebsitePresenterImpl;
import com.example.websitetrivia.presentation.presenters.impl.UpdatePresenterImpl;
import com.example.websitetrivia.presentation.ui.activities.WebsitesTriviaHome;
import com.example.websitetrivia.storage.database.WebsitesDatabase;
import com.example.websitetrivia.threading.MainThreadImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddWebsiteFragment extends Fragment implements AddWebsitePresenter.View{
    public static final String ARG_ITEM_ID = "item_id";
    public static final String HOST_CLASS_NAME="com.example.websitetrivia.presentation.ui.activities.WebsitesTriviaHome";

    private Website mItem;
    private AddWebsitePresenter mPresenter;

    @BindView(R.id.website_detail_et_name) TextInputEditText mName;
    @BindView(R.id.website_detail_et_founding_year) TextInputEditText mFoundingYear;
    @BindView(R.id.website_detail_et_founders) TextInputEditText mFounders;
    @BindView(R.id.website_detail_et_location) TextInputEditText mLocation;
    @BindView(R.id.website_detail_et_CEO) TextInputEditText mCEO;
    @BindView(R.id.website_detail_et_rank) TextInputEditText mRank;
    @BindView(R.id.website_detail_et_time_spent) TextInputEditText mTimeSpent;
    @BindView(R.id.website_detail_btn_save) Button mSave;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new AddWebsitePresenterImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this,
                WebsitesRepository.getInstance(WebsitesDatabase.getInstance(getContext())));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.website_detail_element, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;   }


    @OnClick(R.id.website_detail_btn_save)
    void createWebsite(){
        if(validateEmptyInput(mName)&&validateEmptyInput(mFoundingYear)&& validateEmptyInput(mFounders)&&
            validateEmptyInput(mLocation)&&validateEmptyInput(mCEO)&&validateEmptyInput(mRank)
        &&validateEmptyInput(mTimeSpent)) {
            mPresenter.addWebsite(mName.getText().toString(), mFoundingYear.getText().toString(), mFounders.getText().toString(),
                    mLocation.getText().toString(), mCEO.getText().toString(), mRank.getText().toString(),
                    mTimeSpent.getText().toString());

        }
    }

    private void handleCompletion(){
        if(getActivity().getClass().getName().equals(HOST_CLASS_NAME)){
            ((WebsitesTriviaHome)getActivity()).refreshUI();
            getFragmentManager().beginTransaction().replace(R.id.website_detail_container, new PlaceholderFragment()).commit();
            return;
        }
        getActivity().finish();
    }

    private boolean validateEmptyInput(TextInputEditText textInputEditText){
        if(textInputEditText.getText().toString().equals("")){
            textInputEditText.setError("Please enter the appropriate value");
            return false;
        }
        return true;
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
    public void onWebsiteAdded() {
        handleCompletion();
    }


}
