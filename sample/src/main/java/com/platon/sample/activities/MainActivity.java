package com.platon.sample.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.platon.sample.R;
import com.platon.sample.activities.post.PostPaymentActivity;
import com.platon.sample.activities.web.WebPaymentActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_platon_sdk_web_payment).setOnClickListener(this);
        findViewById(R.id.btn_platon_sdk_post_payment).setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        int id = v.getId();
        if (id == R.id.btn_platon_sdk_web_payment) {
            startActivity(new Intent(MainActivity.this, WebPaymentActivity.class));
        } else if (id == R.id.btn_platon_sdk_post_payment) {
            startActivity(new Intent(MainActivity.this, PostPaymentActivity.class));
        }
    }
}
