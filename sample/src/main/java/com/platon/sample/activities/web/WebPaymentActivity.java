package com.platon.sample.activities.web;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.platon.sample.R;
import com.platon.sdk.core.PlatonCredentials;
import com.platon.sdk.core.PlatonSdk;
import com.stanko.tools.Log;

public class WebPaymentActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_payment);

        // Init Platon SDK WebPayment
        PlatonSdk.init(
                getApplicationContext(),
                getString(R.string.platon_sdk_client_key_web),
                getString(R.string.platon_sdk_client_pass_web),
                getString(R.string.platon_sdk_payment_url_web)
        );

        // Logs
        Log.d(String.valueOf(PlatonSdk.isInited()));
        Log.d(PlatonCredentials.getClientKey());
        Log.d(PlatonCredentials.getClientPass());
        Log.d(PlatonCredentials.getPaymentUrl());

        findViewById(R.id.btn_web_sale).setOnClickListener(this);
        findViewById(R.id.btn_token_web_sale).setOnClickListener(this);
        findViewById(R.id.btn_web_one_click_sale).setOnClickListener(this);
        findViewById(R.id.btn_web_recurring_sale).setOnClickListener(this);
        findViewById(R.id.btn_web_schedule).setOnClickListener(this);
        findViewById(R.id.btn_web_deschedule).setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        int id = v.getId();
        if (id == R.id.btn_web_sale) {
            startActivity(new Intent(WebPaymentActivity.this, WebSaleActivity.class));
        } else if (id == R.id.btn_token_web_sale) {
            startActivity(new Intent(WebPaymentActivity.this, WebTokenSaleActivity.class));
        } else if (id == R.id.btn_web_one_click_sale) {
            startActivity(new Intent(WebPaymentActivity.this, WebOneClickSaleActivity.class));
        } else if (id == R.id.btn_web_recurring_sale) {// !!! Will be available in next releases !!!
            Toast.makeText(this, "Will be available in next releases", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(WebPaymentActivity.this, WebRecurringActivity.class));
        } else if (id == R.id.btn_web_schedule) {// !!! Will be available in next releases !!!
            Toast.makeText(this, "Will be available in next releases", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(WebPaymentActivity.this, WebScheduleActivity.class));
        } else if (id == R.id.btn_web_deschedule) {// !!! Will be available in next releases !!!
            Toast.makeText(this, "Will be available in next releases", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(WebPaymentActivity.this, WebDecheduleActivity.class));
        }
    }
}
