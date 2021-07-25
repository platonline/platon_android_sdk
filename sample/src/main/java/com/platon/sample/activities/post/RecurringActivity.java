package com.platon.sample.activities.post;

import android.os.Bundle;
import androidx.annotation.Nullable;

import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.platon.sample.R;
import com.platon.sample.activities.BaseActivity;
import com.platon.sample.db.DBHelper;
import com.platon.sample.db.models.Trans;
import com.platon.sample.utils.DecimalDigitsInputFilter;
import com.platon.sdk.callback.PlatonSaleCallback;
import com.platon.sdk.core.PlatonSdk;
import com.platon.sdk.model.request.order.PlatonOrderRecurring;
import com.platon.sdk.model.request.recurring.PlatonRecurring;
import com.platon.sdk.model.response.base.PlatonApiError;
import com.platon.sdk.model.response.sale.PlatonSale;
import com.platon.sdk.model.response.sale.PlatonSaleDecline;
import com.platon.sdk.model.response.sale.PlatonSaleRecurringInit;
import com.platon.sdk.model.response.sale.PlatonSaleSuccess;
import com.platon.sdk.model.response.sale.PlatonSale3DSecure;
import com.slmyldz.random.Randoms;
import com.stanko.tools.Log;

import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import io.kimo.lib.faker.Faker;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;

import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MAX_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MIN_AMOUNT;

public class RecurringActivity extends BaseActivity implements
        View.OnClickListener,
        PlatonSaleCallback {

    private final Realm fRealm = DBHelper.getRealmInstance();
    private Button mBtnRandomize;
    private EditText mEtxtOrderId;
    private EditText mEtxtOrderAmount;
    private EditText mEtxtOrderDescription;
    private EditText mEtxtPayerEmail;
    private EditText mEtxtCardNumber;
    private EditText mEtxtFirstTransId;
    private EditText mEtxtRecurringToken;
    private EditText mEtxtResponse;
    private CheckBox mCbAsyncRecurring;
    private Button mBtnRecurringSale;
    private Button mBtnRecurringAuth;
    private Trans mTrans;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recurring);

        assignViews();
        configureViews();
    }

    private void assignViews() {
        mBtnRandomize = findViewById(R.id.btn_randomize);
        mEtxtOrderId = findViewById(R.id.etxt_order_id);
        mEtxtOrderAmount = findViewById(R.id.etxt_order_amount);
        mEtxtOrderDescription = findViewById(R.id.etxt_order_description);
        mEtxtPayerEmail = findViewById(R.id.etxt_payer_email);
        mEtxtCardNumber = findViewById(R.id.etxt_card_number);
        mEtxtFirstTransId = findViewById(R.id.etxt_first_trans_id);
        mEtxtRecurringToken = findViewById(R.id.etxt_recurring_token);
        mEtxtResponse = findViewById(R.id.etxt_response);
        mCbAsyncRecurring = findViewById(R.id.cb_async);
        mBtnRecurringSale = findViewById(R.id.btn_sale);
        mBtnRecurringAuth = findViewById(R.id.btn_auth);
        mEtxtOrderAmount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(2)});
    }

    private void configureViews() {
        mBtnRandomize.setOnClickListener(this);
        mBtnRecurringAuth.setOnClickListener(this);
        mBtnRecurringSale.setOnClickListener(this);

        randomize();
    }

    private void fillResponse(final String response) {
        hideProgress();
        Log.d(response);
        mEtxtResponse.setText(response);
    }

    private void saveTrans(final String transId) {
        mTrans.setTransId(transId);
        DBHelper.copyToRealmOrUpdate(mTrans);
    }

    private void randomize() {
        final RealmResults<Trans> transes = fRealm.where(Trans.class).isNotNull(Trans.RECURRING_TOKEN).findAll();
        if (transes.isEmpty()) return;
        mTrans = transes.get(new Random().nextInt(transes.size()));

        mEtxtOrderId.setText(String.valueOf(UUID.randomUUID()));
        mEtxtOrderAmount.setText(String.format(Locale.US, "%.2f", (Randoms.Float(MIN_AMOUNT, MAX_AMOUNT * 2.0F))));
        mEtxtOrderDescription.setText(Faker.Lorem.sentences());

        mEtxtPayerEmail.setText(mTrans.getPayerEmail());
        mEtxtCardNumber.setText(mTrans.getCardNumber());

        mEtxtFirstTransId.setText(mTrans.getTransId());
        mEtxtRecurringToken.setText(mTrans.getRecurringToken());

        mCbAsyncRecurring.setChecked(new Random().nextBoolean());

        mEtxtResponse.setText("");
    }

    @Override
    public void onClick(final View v) {
        int id = v.getId();
        if (id == R.id.btn_randomize) {
            randomize();
        } else if (id == R.id.btn_sale || id == R.id.btn_auth) {
            mEtxtResponse.setText("");

            float amount;
            try {
                amount = Float.parseFloat(String.valueOf(mEtxtOrderAmount.getText()));
            } catch (final Exception e) {
                amount = Randoms.Float(MIN_AMOUNT, MAX_AMOUNT * 2.0F);
            }
            final PlatonOrderRecurring order = new PlatonOrderRecurring(
                    String.valueOf(mEtxtOrderId.getText()),
                    amount,
                    String.valueOf(mEtxtOrderDescription.getText())
            );

            final PlatonRecurring platonRecurring = new PlatonRecurring(
                    String.valueOf(mEtxtFirstTransId.getText()),
                    String.valueOf(mEtxtRecurringToken.getText())
            );

            final String payerEmail = String.valueOf(mEtxtPayerEmail.getText());
            final String cardNumber = String.valueOf(mEtxtCardNumber.getText());
            mTrans = new Trans(payerEmail, cardNumber);

            showProgress();
            if (v.getId() == R.id.btn_sale) {
                if (mCbAsyncRecurring.isChecked())
                    PlatonSdk.PostPayments.getRecurringAdapter().recurringAsyncSale(
                            order, platonRecurring, payerEmail, cardNumber, this
                    );
                else
                    PlatonSdk.PostPayments.getRecurringAdapter().recurringSale(
                            order, platonRecurring, payerEmail, cardNumber, this
                    );
            } else {
                if (mCbAsyncRecurring.isChecked())
                    PlatonSdk.PostPayments.getRecurringAdapter().recurringAsyncAuth(
                            order, platonRecurring, payerEmail, cardNumber, this
                    );
                else
                    PlatonSdk.PostPayments.getRecurringAdapter().recurringAuth(
                            order, platonRecurring, payerEmail, cardNumber, this
                    );
            }
        }
    }


    @Override
    public void onDeclineResponse(final Call call, final PlatonSaleDecline response) {
        fillResponse(response.toString());
        saveTrans(response.getTransactionId());
    }

    @Override
    public void onAsyncResponse(final Call call, final PlatonSale response) {
        fillResponse(response.toString());
        saveTrans(response.getTransactionId());
    }

    @Override
    public void onResponse(final Call call, final PlatonSaleSuccess response) {
        fillResponse(response.toString());
        saveTrans(response.getTransactionId());
    }

    @Override
    public void onRecurringInitResponse(final Call call, final PlatonSaleRecurringInit response) {
        fillResponse(response.toString());
        saveTrans(response.getTransactionId());
    }

    @Override
    public void on3dSecureResponse(final Call call, final PlatonSale3DSecure response) {
        fillResponse(response.toString());
        saveTrans(response.getTransactionId());
    }

    @Override
    public void onError(final Call call, final PlatonApiError platonApiError) {
        fillResponse(platonApiError.toString());
    }

    @Override
    public void onFailure(final Call call, final Throwable t) {
        fillResponse(t.getMessage());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DBHelper.closeRealmInstance(fRealm);
    }
}
