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
import com.platon.sdk.model.request.option.web.PlatonWebOptions;
import com.platon.sdk.model.request.order.product.PlatonProductRecurring;
import com.platon.sdk.model.request.recurring.PlatonRecurringWeb;
import com.slmyldz.random.Randoms;

import java.util.UUID;

import io.kimo.lib.faker.Faker;
import retrofit2.Call;
import retrofit2.Response;

import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MAX_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MIN_AMOUNT;

public class WebRecurringActivity extends BaseActivity implements
        View.OnClickListener,
        PlatonWebCallback {

    private Button mBtnRandomize;
    private EditText mEtxtOrderId;
    private EditText mEtxtOrderAmount;
    private EditText mEtxtOrderDescription;
    private EditText mEtxtFirstTransId;
    private EditText mEtxtRecurringToken;
    private EditText mEtxtExt1;
    private EditText mEtxtExt2;
    private EditText mEtxtExt3;
    private EditText mEtxtExt4;
    private EditText mEtxtExt10;
    private Button mBtnRecurringSale;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_recurring);

        assignViews();
        configureViews();
    }

    private void assignViews() {
        mBtnRandomize = findViewById(R.id.btn_randomize);
        mEtxtOrderId = findViewById(R.id.etxt_order_id);
        mEtxtOrderAmount = findViewById(R.id.etxt_order_amount);
        mEtxtOrderDescription = findViewById(R.id.etxt_order_description);
        mEtxtFirstTransId = findViewById(R.id.etxt_first_trans_id);
        mEtxtRecurringToken = findViewById(R.id.etxt_recurring_token);
        mEtxtExt1 = findViewById(R.id.etxt_ext_1);
        mEtxtExt2 = findViewById(R.id.etxt_ext_2);
        mEtxtExt3 = findViewById(R.id.etxt_ext_3);
        mEtxtExt4 = findViewById(R.id.etxt_ext_4);
        mEtxtExt10 = findViewById(R.id.etxt_ext_10);
        mBtnRecurringSale = findViewById(R.id.btn_sale);
    }

    private void configureViews() {
        mBtnRandomize.setOnClickListener(this);
        mBtnRecurringSale.setOnClickListener(this);

        randomize();
    }

    private void randomize() {
        mEtxtOrderId.setText(String.valueOf(UUID.randomUUID()));
        mEtxtOrderAmount.setText(String.valueOf(Randoms.Float(MIN_AMOUNT, MAX_AMOUNT * 2.0F)));
        mEtxtOrderDescription.setText(Faker.with(this).Lorem.sentences());

        mEtxtFirstTransId.setText(String.valueOf(UUID.randomUUID()));
        mEtxtRecurringToken.setText(String.valueOf(UUID.randomUUID()));

        mEtxtExt1.setText(Faker.with(this).Url.avatar());
        mEtxtExt2.setText(Faker.with(this).Url.avatar());
        mEtxtExt3.setText(Faker.with(this).Url.avatar());
        mEtxtExt4.setText(Faker.with(this).Url.avatar());
        mEtxtExt10.setText(Faker.with(this).Url.avatar());
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.btn_randomize:
                randomize();
                break;
            case R.id.btn_sale:

                float amount;
                try {
                    amount = Float.parseFloat(String.valueOf(mEtxtOrderAmount.getText()));
                } catch (final Exception e) {
                    amount = Randoms.Float(MIN_AMOUNT, MAX_AMOUNT * 2.0F);
                }

                final PlatonProductRecurring productRecurring = new PlatonProductRecurring(
                        amount, String.valueOf(mEtxtOrderDescription.getText())
                );
                productRecurring.setId(String.valueOf(mEtxtOrderId.getText()));

                final PlatonRecurringWeb recurring = new PlatonRecurringWeb(
                        String.valueOf(mEtxtFirstTransId.getText()),
                        String.valueOf(mEtxtRecurringToken.getText())
                );

                final PlatonWebOptions platonWebOptions = new PlatonWebOptions.Builder()
                        .ext1(String.valueOf(mEtxtExt1.getText()))
                        .ext2(String.valueOf(mEtxtExt2.getText()))
                        .ext3(String.valueOf(mEtxtExt3.getText()))
                        .ext4(String.valueOf(mEtxtExt4.getText()))
                        .ext10(String.valueOf(mEtxtExt10.getText()))
                        .build();

                showProgress();
                PlatonSdk.WebPayments.getRecurringAdapter().recurringSale(
                        productRecurring,
                        recurring,
                        platonWebOptions,
                        this
                );
                break;
            default:
                break;
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
