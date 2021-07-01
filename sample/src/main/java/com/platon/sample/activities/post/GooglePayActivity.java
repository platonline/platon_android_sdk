package com.platon.sample.activities.post;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.platon.sample.R;
import com.platon.sample.activities.BaseActivity;
import com.platon.sdk.callback.PlatonGooglePayCallback;
import com.platon.sdk.constant.api.PlatonOption;
import com.platon.sdk.core.PlatonSdk;
import com.platon.sdk.model.request.option.PlatonGooglePayOptions;
import com.platon.sdk.model.request.order.PlatonOrderGooglePay;
import com.platon.sdk.model.request.payer.PlatonPayerGooglePay;
import com.platon.sdk.model.response.base.PlatonApiError;
import com.platon.sdk.model.response.google_pay.PlatonGooglePay;
import com.platon.sdk.model.response.google_pay.PlatonGooglePay3DSecure;
import com.platon.sdk.model.response.google_pay.PlatonGooglePayDecline;
import com.platon.sdk.model.response.google_pay.PlatonGooglePaySuccess;
import com.platon.sdk.util.GooglePayHelper;
import com.platon.sdk.widget.GooglePayButton;

import retrofit2.Call;

public class GooglePayActivity extends BaseActivity implements PlatonGooglePayCallback {

    private GooglePayButton googlePayButton;
    private GooglePayHelper googlePayHelper;
    private CheckBox cbAuth, cbAsync, cbBlack, cbWithText, cbShadow;
    private EditText etxtOrderId, etxtOrderAmount, etxtOrderDescription, etxtOrderCurrencyCode, etxtPayerFirstName,
            etxtPayerLastName, etxtPayerMiddleName, etxtPayerBirthday, etxtPayerAddress, etxtPayerCountryCode,
            etxtPayerState, etxtPayerCity, etxtPayerZip, etxtPayerEmail, etxtPayerPhone, etxtPayerIpAddress, etxtChannelId;
    private TextView txResponse;
    private int enviroment = GooglePayHelper.ENVIRONMENT_TEST;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_pay);

        enviroment = getIntent().getIntExtra("enviroment", enviroment);
        String merchandId = getIntent().getStringExtra("merchandId");
        String clientPassword = getIntent().getStringExtra("clientPassword");
        if (merchandId != null && clientPassword != null) {
            PlatonSdk.init(
                    getApplicationContext(),
                    merchandId,
                    clientPassword,
                    getString(R.string.platon_sdk_payment_url_post),
                    getString(R.string.platon_sdk_term_url_3ds_post)
            );
        } else {
            PlatonSdk.init(
                    getApplicationContext(),
                    "M7HJ4VAUA8",
                    "rVDMSZh0upVbMsuhrwKu8aSFUSTcmKew",
                    getString(R.string.platon_sdk_payment_url_post),
                    getString(R.string.platon_sdk_term_url_3ds_post)
            );
        }

        cbAuth = (CheckBox) findViewById(R.id.cb_auth);
        cbAsync = (CheckBox) findViewById(R.id.cb_async);
        cbBlack = (CheckBox) findViewById(R.id.cb_black);
        cbShadow = (CheckBox) findViewById(R.id.cb_shadow);
        cbWithText = (CheckBox) findViewById(R.id.cb_with_text);
        txResponse = (TextView) findViewById(R.id.tx_response);
        etxtOrderId = findViewById(R.id.etxt_order_id);
        etxtOrderAmount = findViewById(R.id.etxt_order_amount);
        etxtOrderDescription = findViewById(R.id.etxt_order_description);
        etxtOrderCurrencyCode = findViewById(R.id.etxt_order_currency_code);
        etxtPayerFirstName = findViewById(R.id.etxt_payer_first_name);
        etxtPayerLastName = findViewById(R.id.etxt_payer_last_name);
        etxtPayerMiddleName = findViewById(R.id.etxt_payer_middle_name);
        etxtPayerBirthday = findViewById(R.id.etxt_payer_birthday);
        etxtPayerAddress = findViewById(R.id.etxt_payer_address);
        etxtPayerCountryCode = findViewById(R.id.etxt_payer_country_code);
        etxtPayerState = findViewById(R.id.etxt_payer_state);
        etxtPayerCity = findViewById(R.id.etxt_payer_city);
        etxtPayerZip = findViewById(R.id.etxt_payer_zip);
        etxtPayerEmail = findViewById(R.id.etxt_payer_email);
        etxtPayerPhone = findViewById(R.id.etxt_payer_phone);
        etxtPayerIpAddress = findViewById(R.id.etxt_payer_ip_address);
        etxtChannelId = findViewById(R.id.etxt_channel_id);

        setupGooglePay();
        changeButton();
        cbBlack.setOnCheckedChangeListener((buttonView, isChecked) -> {
            cbShadow.setEnabled(!isChecked);
            googlePayButton.setBlack(isChecked);
        });

        cbShadow.setOnCheckedChangeListener((buttonView, isChecked) -> googlePayButton.setShadow(isChecked));
        cbWithText.setOnCheckedChangeListener((buttonView, isChecked) -> googlePayButton.setWithText(isChecked));
    }

    private void setupGooglePay() {
        googlePayButton = findViewById(R.id.google_pay_button);

        googlePayHelper = new GooglePayHelper();
        googlePayHelper.initGooglePay(this, enviroment, isShow -> {
            if (isShow) {
                googlePayButton.setVisibility(View.VISIBLE);
                googlePayButton.setOnClickListener(v -> {
                    googlePayButton.setEnabled(false);
                    startGooglePay();
                });
            } else {
                googlePayButton.setVisibility(View.GONE);
            }
        });
    }

    private void changeButton() {
        googlePayButton.setBlack(cbBlack.isChecked());
        googlePayButton.setShadow(cbShadow.isChecked());
        googlePayButton.setWithText(cbWithText.isChecked());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        googlePayHelper.resultGooglePay(requestCode, resultCode, data);
        googlePayButton.setEnabled(true);
    }

    @Override
    public void on3dSecureResponse(Call call, PlatonGooglePay3DSecure response) {
        fillResponse(response.toString());
    }

    @Override
    public void onDeclineResponse(Call call, PlatonGooglePayDecline response) {
        fillResponse(response.toString());
    }

    @Override
    public void onAsyncResponse(Call call, PlatonGooglePay response) {
        fillResponse(response.toString());
    }

    @Override
    public void onResponse(Call call, PlatonGooglePaySuccess response) {
        fillResponse(response.toString());
    }

    @Override
    public void onError(Call call, PlatonApiError platonApiError) {
        fillResponse(platonApiError.toString());
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        fillResponse(t.toString());
    }

    private void fillResponse(final String response) {
        hideProgress();
        com.stanko.tools.Log.d(response);
        txResponse.setText(response);
    }

    private void startGooglePay() {
        try {
            final PlatonOrderGooglePay order = new PlatonOrderGooglePay(
                    etxtOrderId.getText().toString(),
                    Float.parseFloat(etxtOrderAmount.getText().toString()),
                    etxtOrderCurrencyCode.getText().toString().toUpperCase(),
                    etxtPayerCountryCode.getText().toString().toUpperCase(),
                    etxtOrderDescription.getText().toString()
            );

            final PlatonPayerGooglePay platonPayerGooglePay = new PlatonPayerGooglePay(
                    etxtPayerFirstName.getText().toString(),
                    etxtPayerLastName.getText().toString(),
                    etxtPayerMiddleName.getText().toString(),
                    etxtPayerBirthday.getText().toString(),
                    etxtPayerAddress.getText().toString(),
                    etxtPayerCountryCode.getText().toString().toUpperCase(),
                    etxtPayerState.getText().toString(),
                    etxtPayerCity.getText().toString(),
                    etxtPayerZip.getText().toString(),
                    etxtPayerEmail.getText().toString(),
                    etxtPayerPhone.getText().toString(),
                    etxtPayerIpAddress.getText().toString()
            );

            final PlatonGooglePayOptions platonGooglePayOptions = new PlatonGooglePayOptions.Builder()
                    .async(cbAsync.isChecked() ? PlatonOption.YES : PlatonOption.NO)
                    .channelId(etxtChannelId.getText().toString())
                    .build();

            final String auth = cbAuth.isChecked() ? PlatonOption.YES : PlatonOption.NO;
            googlePayHelper.openGooglePay(this, order, platonPayerGooglePay, platonGooglePayOptions, this, auth);
        } catch (Exception e) {
            googlePayButton.setEnabled(true);
            Toast.makeText(this, "Incorrectly filled in data!", Toast.LENGTH_LONG).show();
        }
    }
}
