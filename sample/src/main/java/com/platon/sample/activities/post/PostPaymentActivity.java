package com.platon.sample.activities.post;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.platon.sample.R;
import com.platon.sdk.core.PlatonCredentials;
import com.platon.sdk.core.PlatonSdk;
import com.stanko.tools.Log;

public class PostPaymentActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_payment);

        // Init Platon SDK PostPayment
        PlatonSdk.init(
                getApplicationContext(),
                "M7HJ4VAUA8",
                "rVDMSZh0upVbMsuhrwKu8aSFUSTcmKew",
                getString(R.string.platon_sdk_payment_url_post),
                getString(R.string.platon_sdk_term_url_3ds_post)
        );

        // Logs
        Log.d(String.valueOf(PlatonSdk.isInited()));
        Log.d(PlatonCredentials.getClientKey());
        Log.d(PlatonCredentials.getClientPass());
        Log.d(PlatonCredentials.getPaymentUrl());

        findViewById(R.id.btn_sale).setOnClickListener(this);
        findViewById(R.id.btn_get_trans_status).setOnClickListener(this);
        findViewById(R.id.btn_get_trans_details).setOnClickListener(this);
        findViewById(R.id.btn_capture).setOnClickListener(this);
        findViewById(R.id.btn_credit_void).setOnClickListener(this);
        findViewById(R.id.btn_recurring_sale).setOnClickListener(this);
        findViewById(R.id.btn_schedule).setOnClickListener(this);
        findViewById(R.id.btn_deschedule).setOnClickListener(this);
        findViewById(R.id.btn_google_pay).setOnClickListener(this);
        findViewById(R.id.btn_google_pay_prod).setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.btn_sale:
                startActivity(new Intent(PostPaymentActivity.this, SaleActivity.class));
                break;
            case R.id.btn_get_trans_status:
                startActivity(new Intent(PostPaymentActivity.this, GetTransStatusActivity.class));
                break;
            case R.id.btn_get_trans_details:
                startActivity(new Intent(PostPaymentActivity.this, GetTransDetailsActivity.class));
                break;
            case R.id.btn_capture:
                startActivity(new Intent(PostPaymentActivity.this, CaptureActivity.class));
                break;
            case R.id.btn_credit_void:
                startActivity(new Intent(PostPaymentActivity.this, CreditVoidActivity.class));
                break;
            case R.id.btn_recurring_sale:
                startActivity(new Intent(PostPaymentActivity.this, RecurringActivity.class));
                break;
            case R.id.btn_schedule:
                startActivity(new Intent(PostPaymentActivity.this, ScheduleActivity.class));
                break;
            case R.id.btn_deschedule:
                startActivity(new Intent(PostPaymentActivity.this, DescheduleActivity.class));
                break;
            case R.id.btn_google_pay:
                startActivity(new Intent(PostPaymentActivity.this, GooglePayActivity.class));
                break;
            case R.id.btn_google_pay_prod:
                startActivity(new Intent(PostPaymentActivity.this, AddDataPayActivity.class));
                break;
            default:
                break;
        }
    }
}
