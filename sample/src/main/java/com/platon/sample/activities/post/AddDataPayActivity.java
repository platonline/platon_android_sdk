package com.platon.sample.activities.post;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.platon.sample.R;
import com.platon.sdk.util.GooglePayHelper;

public class AddDataPayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_data_pay);

        final EditText merchandId = findViewById(R.id.merchand_id);
        final EditText clientPassword = findViewById(R.id.client_passsword);
        Button btnNext = findViewById(R.id.next);

        btnNext.setOnClickListener(v -> {
            if (merchandId.getText().length() != 0 && clientPassword.getText().length() != 0){
                Intent intent = new Intent(AddDataPayActivity.this, GooglePayActivity.class);
                intent.putExtra("enviroment", GooglePayHelper.ENVIRONMENT_PRODUCTION);
                intent.putExtra("merchandId", merchandId.getText().toString());
                intent.putExtra("clientPassword", clientPassword.getText().toString());
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Enter data!", Toast.LENGTH_LONG).show();
            }
        });
    }
}