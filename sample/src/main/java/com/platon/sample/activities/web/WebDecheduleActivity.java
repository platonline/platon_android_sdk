package com.platon.sample.activities.web;

import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.platon.sample.R;
import com.platon.sample.activities.BaseActivity;
import com.platon.sdk.callback.PlatonWebCallback;
import com.platon.sdk.core.PlatonSdk;
import com.platon.sdk.model.request.recurring.PlatonRecurringWeb;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Response;

public class WebDecheduleActivity extends BaseActivity implements
        View.OnClickListener,
        PlatonWebCallback {

    private Button mBtnRandomize;
    private EditText mEtxtFirstTransId;
    private EditText mEtxtRecurringToken;
    private Button mBtnDeschedule;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_deschedule);

        assignViews();
        configureViews();
    }

    private void assignViews() {
        mBtnRandomize = findViewById(R.id.btn_randomize);
        mEtxtFirstTransId = findViewById(R.id.etxt_first_trans_id);
        mEtxtRecurringToken = findViewById(R.id.etxt_recurring_token);
        mBtnDeschedule = findViewById(R.id.btn_deschedule);
    }

    private void configureViews() {
        mBtnRandomize.setOnClickListener(this);
        mBtnDeschedule.setOnClickListener(this);

        randomize();
    }

    private void randomize() {
        mEtxtFirstTransId.setText(String.valueOf(UUID.randomUUID()));
        mEtxtRecurringToken.setText(String.valueOf(UUID.randomUUID()));
    }

    @Override
    public void onClick(final View v) {
        int id = v.getId();
        if (id == R.id.btn_randomize) {
            randomize();
        } else if (id == R.id.btn_deschedule) {
            final PlatonRecurringWeb recurring = new PlatonRecurringWeb(
                    String.valueOf(mEtxtFirstTransId.getText()),
                    String.valueOf(mEtxtRecurringToken.getText())
            );

            showProgress();
            PlatonSdk.WebPayments.getScheduleAdapter().deschedule(recurring, this);
        }
    }


    @Override
    public void onResponse(final Call<String> call, final Response<String> response) {
        hideProgress();
        Log.d("Response: ", response.toString());

        final CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        final CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(response.raw().request().url().toString()));
    }

    @Override
    public void onFailure(final Call<String> call, final Throwable t) {
        hideProgress();
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_LONG).show();
    }
}
