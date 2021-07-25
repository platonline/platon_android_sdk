package com.platon.sample.activities.web;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;

import com.platon.sample.R;
import com.platon.sample.activities.BaseActivity;
import com.platon.sample.utils.DecimalDigitsInputFilter;
import com.platon.sdk.callback.PlatonWebCallback;
import com.platon.sdk.core.PlatonSdk;
import com.platon.sdk.model.request.option.web.PlatonC2AOptions;
import com.platon.sdk.model.request.order.PlatonOrderC2A;
import com.platon.sdk.model.request.payer.PlatonPayerC2A;
import com.platon.sdk.util.PlatonSdkUtil;
import com.slmyldz.random.Randoms;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import io.kimo.lib.faker.Faker;
import retrofit2.Call;
import retrofit2.Response;

import static com.platon.sample.app.PlatonApp.isValidURL;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MAX_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MIN_AMOUNT;

public class WebC2AOneClickActivity extends BaseActivity implements
        View.OnClickListener,
        PlatonWebCallback {

    private Button mBtnRandomize;
    private EditText mEtxtAmount;
    private EditText mEtxtCurrencyCode;
    private EditText mEtxtDescription;
    private EditText mEtxtOrderId;
    private EditText mEtxtCardToken;
    private EditText mEtxtSuccessUrl;
    private EditText mEtxtErrorUrl;
    private EditText mEtxtPayerFirstName;
    private EditText mEtxtPayerLastName;
    private EditText mEtxtPayerAddress;
    private EditText mEtxtPayerCountryCode;
    private EditText mEtxtPayerState;
    private EditText mEtxtPayerCity;
    private EditText mEtxtPayerZip;
    private EditText mEtxtPayerEmail;
    private EditText mEtxtPayerPhone;
    private EditText mEtxtLanguage;
    private EditText mEtxtFormId;
    private EditText mEtxtExt1;
    private EditText mEtxtExt2;
    private EditText mEtxtExt3;
    private EditText mEtxtExt4;
    private Button mBtnSale;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_c2a_one_click);

        assignViews();
        configureViews();
    }

    private void assignViews() {
        mBtnRandomize = findViewById(R.id.btn_randomize);
        mEtxtSuccessUrl = findViewById(R.id.etxt_success_url);
        mEtxtAmount = findViewById(R.id.etxt_order_amount);
        mEtxtCurrencyCode = findViewById(R.id.etxt_order_currency_code);
        mEtxtDescription = findViewById(R.id.etxt_order_description);
        mEtxtCardToken = findViewById(R.id.etxt_card_token);
        mEtxtSuccessUrl = findViewById(R.id.etxt_url);
        mEtxtOrderId = findViewById(R.id.etxt_order_id);
        mEtxtPayerFirstName = findViewById(R.id.etxt_payer_first_name);
        mEtxtPayerLastName = findViewById(R.id.etxt_payer_last_name);
        mEtxtPayerAddress = findViewById(R.id.etxt_payer_address);
        mEtxtPayerCountryCode = findViewById(R.id.etxt_payer_country_code);
        mEtxtPayerState = findViewById(R.id.etxt_payer_state);
        mEtxtPayerCity = findViewById(R.id.etxt_payer_city);
        mEtxtPayerZip = findViewById(R.id.etxt_payer_zip);
        mEtxtPayerEmail = findViewById(R.id.etxt_payer_email);
        mEtxtPayerPhone = findViewById(R.id.etxt_payer_phone);
        mEtxtLanguage = findViewById(R.id.etxt_lang);
        mEtxtErrorUrl = findViewById(R.id.etxt_error_url);
        mEtxtFormId = findViewById(R.id.etxt_form_id);
        mEtxtExt1 = findViewById(R.id.etxt_ext_1);
        mEtxtExt2 = findViewById(R.id.etxt_ext_2);
        mEtxtExt3 = findViewById(R.id.etxt_ext_3);
        mEtxtExt4 = findViewById(R.id.etxt_ext_4);
        mBtnSale = findViewById(R.id.btn_sale);

        mEtxtAmount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(2)});
    }

    private void configureViews() {
        mBtnRandomize.setOnClickListener(this);
        mBtnSale.setOnClickListener(this);
        randomize();
    }

    @SuppressLint("DefaultLocale")
    private void randomize() {
        final Random random = new Random();
        mEtxtAmount.setText(String.format(Locale.US, "%.2f", (Randoms.Float(MIN_AMOUNT, MAX_AMOUNT * 2.0F))));
        mEtxtDescription.setText(Faker.with(this).Lorem.sentences());
        mEtxtSuccessUrl.setText(Faker.Internet.url());
        mEtxtOrderId.setText(String.valueOf(UUID.randomUUID()));
        mEtxtCurrencyCode.setText("UAH");

        mEtxtPayerFirstName.setText(Faker.Name.firstName());
        mEtxtPayerLastName.setText(Faker.Name.lastName());
        mEtxtPayerAddress.setText(Faker.Address.secondaryAddress());
        mEtxtPayerCountryCode.setText(Faker.Address.countryAbbreviation());
        mEtxtPayerState.setText(Faker.Address.state());
        mEtxtPayerCity.setText(Faker.Address.city());
        mEtxtPayerZip.setText(Faker.Address.zipCode());
        mEtxtPayerEmail.setText(Faker.Internet.email());
        mEtxtPayerPhone.setText(Faker.Phone.phoneWithAreaCode());

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
        mEtxtErrorUrl.setText(Faker.Internet.url());
        mEtxtExt1.setText(Faker.Url.avatar());
        mEtxtExt2.setText(Faker.Url.avatar());
        mEtxtExt3.setText(Faker.Url.avatar());
        mEtxtExt4.setText(Faker.Url.avatar());
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

            final PlatonOrderC2A order = new PlatonOrderC2A(
                    Float.parseFloat(mEtxtAmount.getText().toString()),
                    mEtxtCurrencyCode.getText().toString(),
                    mEtxtDescription.getText().toString()
            );

            final PlatonPayerC2A payer = new PlatonPayerC2A.Builder()
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

            final PlatonC2AOptions webC2AOptions = new PlatonC2AOptions.Builder()
                    .language(String.valueOf(mEtxtLanguage.getText()))
                    .errorUrl(String.valueOf(mEtxtErrorUrl.getText()))
                    .formId(String.valueOf(mEtxtFormId.getText()))
                    .ext1(String.valueOf(mEtxtExt1.getText()))
                    .ext2(String.valueOf(mEtxtExt2.getText()))
                    .ext3(String.valueOf(mEtxtExt3.getText()))
                    .ext4(String.valueOf(mEtxtExt4.getText()))
                    .build();

            showProgress();
            PlatonSdk.WebPayments.getC2AOneClickAdapter().saleC2AOneClick(
                    order,
                    mEtxtCardToken.getText().toString(),
                    successUrl,
                    String.valueOf(mEtxtOrderId.getText()),
                    payer,
                    webC2AOptions,
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
