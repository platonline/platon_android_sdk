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
import com.platon.sdk.model.request.option.schedule.PlatonScheduleWebOptions;
import com.platon.sdk.model.request.order.product.PlatonProduct;
import com.platon.sdk.model.request.recurring.PlatonRecurringWeb;
import com.slmyldz.random.Randoms;

import java.util.UUID;

import io.kimo.lib.faker.Faker;
import retrofit2.Call;
import retrofit2.Response;

import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MAX_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MIN_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Period.ASAP;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Period.MAX_DELAY;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Period.MAX_PERIOD;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Period.MAX_REPEAT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Period.MIN_PERIOD;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Period.UNLIMITED_REPEAT;

public class WebScheduleActivity extends BaseActivity implements
        View.OnClickListener,
        PlatonWebCallback {

    private Button mBtnRandomize;
    private EditText mEtxtOrderAmount;
    private EditText mEtxtOrderDescription;
    private EditText mEtxtFirstTransId;
    private EditText mEtxtRecurringToken;
    private EditText mEtxtInitialDelay;
    private EditText mEtxtPeriod;
    private EditText mEtxtRepeatTimes;
    private Button mBtnSchedule;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_schedule);

        assignViews();
        configureViews();
    }

    private void assignViews() {
        mBtnRandomize = findViewById(R.id.btn_randomize);
        mEtxtOrderAmount = findViewById(R.id.etxt_order_amount);
        mEtxtOrderDescription = findViewById(R.id.etxt_order_description);
        mEtxtFirstTransId = findViewById(R.id.etxt_first_trans_id);
        mEtxtRecurringToken = findViewById(R.id.etxt_recurring_token);
        mEtxtInitialDelay = findViewById(R.id.etxt_initial_delay);
        mEtxtPeriod = findViewById(R.id.etxt_period);
        mEtxtRepeatTimes = findViewById(R.id.etxt_repeat_times);
        mBtnSchedule = findViewById(R.id.btn_schedule);
    }

    private void configureViews() {
        mBtnRandomize.setOnClickListener(this);
        mBtnSchedule.setOnClickListener(this);

        randomize();
    }

    private void randomize() {
        mEtxtOrderAmount.setText(String.valueOf(Randoms.Float(MIN_AMOUNT, MAX_AMOUNT * 2.0F)));
        mEtxtOrderDescription.setText(Faker.Lorem.sentences());

        mEtxtFirstTransId.setText(String.valueOf(UUID.randomUUID()));
        mEtxtRecurringToken.setText(String.valueOf(UUID.randomUUID()));

        mEtxtInitialDelay.setText(String.valueOf(Randoms.Integer(ASAP, MAX_DELAY * 2)));
        mEtxtPeriod.setText(String.valueOf(Randoms.Integer(MIN_PERIOD, MAX_PERIOD * 2)));
        mEtxtRepeatTimes.setText(String.valueOf(Randoms.Integer(UNLIMITED_REPEAT, MAX_REPEAT * 2)));
    }

    @Override
    public void onClick(final View v) {
        int id = v.getId();
        if (id == R.id.btn_randomize) {
            randomize();
        } else if (id == R.id.btn_schedule) {
            float amount;
            try {
                amount = Float.parseFloat(String.valueOf(mEtxtOrderAmount.getText()));
            } catch (final Exception e) {
                amount = Randoms.Float(MIN_AMOUNT, MAX_AMOUNT * 2.0F);
            }

            final PlatonProduct platonProduct = new PlatonProduct(
                    amount, String.valueOf(mEtxtOrderDescription.getText())
            );

            final PlatonRecurringWeb recurring = new PlatonRecurringWeb(
                    String.valueOf(mEtxtFirstTransId.getText()),
                    String.valueOf(mEtxtRecurringToken.getText())
            );

            int initialDelay;
            int period;
            int repeatTimes;
            try {
                initialDelay = Integer.parseInt(String.valueOf(mEtxtInitialDelay.getText()));
            } catch (final Exception e) {
                initialDelay = Randoms.Integer(ASAP, MAX_DELAY * 2);
            }
            try {
                period = Integer.parseInt(String.valueOf(mEtxtPeriod.getText()));
            } catch (final Exception e) {
                period = Randoms.Integer(MIN_PERIOD, MAX_PERIOD * 2);
            }
            try {
                repeatTimes = Integer.parseInt(String.valueOf(mEtxtRepeatTimes.getText()));
            } catch (final Exception e) {
                repeatTimes = Randoms.Integer(UNLIMITED_REPEAT, MAX_REPEAT * 2);
            }

            final PlatonScheduleWebOptions scheduleOptions = new PlatonScheduleWebOptions(
                    initialDelay, period, repeatTimes
            );

            showProgress();
            PlatonSdk.WebPayments.getScheduleAdapter().schedule(
                    platonProduct,
                    recurring,
                    scheduleOptions,
                    this
            );
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
