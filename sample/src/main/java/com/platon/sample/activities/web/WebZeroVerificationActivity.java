package com.platon.sample.activities.web;

import android.net.Uri;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;

import com.platon.sample.R;
import com.platon.sample.activities.BaseActivity;
import com.platon.sample.utils.DecimalDigitsInputFilter;
import com.platon.sdk.callback.PlatonWebCallback;
import com.platon.sdk.core.PlatonSdk;
import com.platon.sdk.model.request.option.web.PlatonWebZeroVerificationOptions;
import com.platon.sdk.model.request.order.product.PlatonProductZeroVerification;
import com.platon.sdk.model.request.payer.PlatonPayerWebSale;

import java.util.Random;
import java.util.UUID;

import io.kimo.lib.faker.Faker;
import retrofit2.Call;
import retrofit2.Response;

import static com.platon.sample.app.PlatonApp.isValidURL;

public class WebZeroVerificationActivity extends BaseActivity implements
        View.OnClickListener, PlatonWebCallback {

    private Button mBtnRandomize;
    private EditText mEtxtOrderAmount;
    private EditText mEtxtOrderCurrencyCode;
    private EditText mEtxtOrderDescription;
    private EditText mEtxtSuccessUrl;
    private EditText mEtxtLanguage;
    private EditText mEtxtPayerEmail;
    private EditText mEtxtPayerFirstName;
    private EditText mEtxtPayerLastName;
    private EditText mEtxtPayerPhone;
    private EditText mEtxtPayerAddress;
    private EditText mEtxtPayerCountryCode;
    private EditText mEtxtPayerState;
    private EditText mEtxtPayerCity;
    private EditText mEtxtPayerZip;
    private EditText mEtxtCustomerWallet;
    private EditText mEtxtOrderId;
    private EditText mEtxtErrorUrl;
    private EditText mEtxtExt1;
    private EditText mEtxtExt2;
    private EditText mEtxtExt3;
    private EditText mEtxtExt4;
    private EditText mEtxtExt5;
    private EditText mEtxtExt6;
    private EditText mEtxtExt7;
    private EditText mEtxtExt8;
    private EditText mEtxtExt9;
    private EditText mEtxtExt10;
    private CheckBox mCbBankId;
    private CheckBox mCbPayerId;
    private Button mBtnSale;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_zero_verification);

        assignViews();
        configureViews();
    }

    private void assignViews() {
        mBtnRandomize = findViewById(R.id.btn_randomize);
        mEtxtSuccessUrl = findViewById(R.id.etxt_success_url);
        mEtxtOrderAmount = findViewById(R.id.etxt_order_amount);
        mEtxtOrderCurrencyCode = findViewById(R.id.etxt_order_currency_code);
        mEtxtOrderDescription = findViewById(R.id.etxt_order_description);
        mEtxtOrderId = findViewById(R.id.etxt_order_id);
        mEtxtCustomerWallet = findViewById(R.id.etxt_customer_wallet);
        mCbBankId = findViewById(R.id.cb_bank_id);
        mCbPayerId = findViewById(R.id.cb_payer_id);
        mEtxtPayerFirstName = findViewById(R.id.etxt_payer_first_name);
        mEtxtPayerLastName = findViewById(R.id.etxt_payer_last_name);
        mEtxtPayerAddress = findViewById(R.id.etxt_payer_address);
        mEtxtPayerCountryCode = findViewById(R.id.etxt_payer_country_code);
        mEtxtPayerState = findViewById(R.id.etxt_payer_state);
        mEtxtPayerCity = findViewById(R.id.etxt_payer_city);
        mEtxtPayerZip = findViewById(R.id.etxt_payer_zip);
        mEtxtPayerEmail = findViewById(R.id.etxt_payer_email);
        mEtxtPayerPhone = findViewById(R.id.etxt_payer_phone);
        mEtxtLanguage = findViewById(R.id.etxt_language);
        mEtxtErrorUrl = findViewById(R.id.etxt_error_url);
        mEtxtExt1 = findViewById(R.id.etxt_ext_1);
        mEtxtExt2 = findViewById(R.id.etxt_ext_2);
        mEtxtExt3 = findViewById(R.id.etxt_ext_3);
        mEtxtExt4 = findViewById(R.id.etxt_ext_4);
        mEtxtExt5 = findViewById(R.id.etxt_ext_5);
        mEtxtExt6 = findViewById(R.id.etxt_ext_6);
        mEtxtExt7 = findViewById(R.id.etxt_ext_7);
        mEtxtExt8 = findViewById(R.id.etxt_ext_8);
        mEtxtExt9 = findViewById(R.id.etxt_ext_9);
        mEtxtExt10 = findViewById(R.id.etxt_ext_10);
        mBtnSale = findViewById(R.id.btn_sale);

        mEtxtOrderAmount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(2)});
    }

    private void configureViews() {
        mBtnRandomize.setOnClickListener(this);
        mBtnSale.setOnClickListener(this);
        randomize();
    }

    private void randomize() {
        final Random random = new Random();
        mEtxtOrderAmount.setText("1.00");
        mEtxtOrderDescription.setText(Faker.with(this).Lorem.sentences());
        mEtxtOrderCurrencyCode.setText("UAH");

        mEtxtSuccessUrl.setText(Faker.with(this).Internet.url());
        mEtxtOrderId.setText(String.valueOf(UUID.randomUUID()));
        mEtxtCustomerWallet.setText(String.valueOf(UUID.randomUUID()));

        mEtxtPayerFirstName.setText(Faker.with(this).Name.firstName());
        mEtxtPayerLastName.setText(Faker.with(this).Name.lastName());
        mEtxtPayerAddress.setText(Faker.with(this).Address.secondaryAddress());
        mEtxtPayerCountryCode.setText(Faker.with(this).Address.countryAbbreviation());
        mEtxtPayerState.setText(Faker.with(this).Address.state());
        mEtxtPayerCity.setText(Faker.with(this).Address.city());
        mEtxtPayerZip.setText(Faker.with(this).Address.zipCode());
        mEtxtPayerEmail.setText(Faker.with(this).Internet.email());
        mEtxtPayerPhone.setText(Faker.with(this).Phone.phoneWithAreaCode());

        final int randomLanguage = random.nextInt(3);
        final String language;
        switch (randomLanguage) {
            case 0:
                language = "RU";
                break;
            case 1:
                language = "EN";
                break;
            case 2:
            default:
                language = "UK";
                break;
        }
        mEtxtLanguage.setText(language);
        mEtxtErrorUrl.setText(Faker.with(this).Internet.url());
        mEtxtExt1.setText(Faker.with(this).Url.avatar());
        mEtxtExt2.setText(Faker.with(this).Url.avatar());
        mEtxtExt3.setText(Faker.with(this).Url.avatar());
        mEtxtExt4.setText(Faker.with(this).Url.avatar());
        mEtxtExt5.setText(Faker.with(this).Url.avatar());
        mEtxtExt6.setText(Faker.with(this).Url.avatar());
        mEtxtExt7.setText(Faker.with(this).Url.avatar());
        mEtxtExt8.setText(Faker.with(this).Url.avatar());
        mEtxtExt9.setText(Faker.with(this).Url.avatar());
        mEtxtExt10.setText(Faker.with(this).Url.avatar());
        mCbBankId.setChecked(true);
        mCbPayerId.setChecked(true);
    }

    @Override
    public void onClick(final View v) {
        int id = v.getId();
        if (id == R.id.btn_randomize) {
            randomize();
        } else if (id == R.id.btn_sale) {
            final String successUrl = String.valueOf(mEtxtSuccessUrl.getText());
            if (!isValidURL(successUrl)) {
                Toast.makeText(this, "Invalid success url", Toast.LENGTH_SHORT).show();
                return;
            }

            final PlatonProductZeroVerification zeroVerification = new PlatonProductZeroVerification(
                    Float.parseFloat(mEtxtOrderAmount.getText().toString()),
                    mEtxtOrderDescription.getText().toString()
            );

            zeroVerification.setCurrencyCode(mEtxtOrderCurrencyCode.getText().toString());

            String bankId;
            if (mCbBankId.isChecked()) {
                bankId = "yes";
            } else {
                bankId = "no";
            }

            String payerId;
            if (mCbPayerId.isChecked()) {
                payerId = "yes";
            } else {
                payerId = "no";
            }

            final PlatonPayerWebSale payer = new PlatonPayerWebSale.Builder()
                    .firstName(String.valueOf(mEtxtPayerFirstName.getText()))
                    .lastName(String.valueOf(mEtxtPayerLastName.getText()))
                    .address(String.valueOf(mEtxtPayerAddress.getText()))
                    .countryCode(String.valueOf(mEtxtPayerCountryCode.getText()))
                    .state(String.valueOf(mEtxtPayerState.getText()))
                    .city(String.valueOf(mEtxtPayerCity.getText()))
                    .zip(String.valueOf(mEtxtPayerZip.getText()))
                    .email(String.valueOf(mEtxtPayerEmail.getText()))
                    .phone(String.valueOf(mEtxtPayerPhone.getText()))
                    .build();

            final PlatonWebZeroVerificationOptions zeroVerificationOptions = new PlatonWebZeroVerificationOptions.Builder()
                    .language(String.valueOf(mEtxtLanguage.getText()))
                    .errorUrl(String.valueOf(mEtxtErrorUrl.getText()))
                    .ext1(String.valueOf(mEtxtExt1.getText()))
                    .ext2(String.valueOf(mEtxtExt2.getText()))
                    .ext3(String.valueOf(mEtxtExt3.getText()))
                    .ext4(String.valueOf(mEtxtExt4.getText()))
                    .ext5(String.valueOf(mEtxtExt5.getText()))
                    .ext6(String.valueOf(mEtxtExt6.getText()))
                    .ext7(String.valueOf(mEtxtExt7.getText()))
                    .ext8(String.valueOf(mEtxtExt8.getText()))
                    .ext9(String.valueOf(mEtxtExt9.getText()))
                    .ext10(String.valueOf(mEtxtExt10.getText()))
                    .bankId(bankId)
                    .payerId(payerId)
                    .build();

            showProgress();
            PlatonSdk.WebPayments.getZeroVerificationAdapter().zeroVerification(
                    zeroVerification,
                    successUrl,
                    payer,
                    zeroVerificationOptions,
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
