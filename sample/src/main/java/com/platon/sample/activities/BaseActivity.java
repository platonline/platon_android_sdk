package com.platon.sample.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog = new ProgressDialog(this);
    }

    protected void showProgress() {
        if (mProgressDialog != null) mProgressDialog.show();
    }

    protected void hideProgress() {
        if (mProgressDialog != null) mProgressDialog.dismiss();
    }

}
