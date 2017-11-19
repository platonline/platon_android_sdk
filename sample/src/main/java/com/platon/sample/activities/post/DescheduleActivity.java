package com.platon.sample.activities.post;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.platon.sample.R;
import com.platon.sample.activities.BaseActivity;
import com.platon.sample.db.DBHelper;
import com.platon.sample.db.models.Trans;
import com.platon.sdk.callback.PlatonScheduleCallback;
import com.platon.sdk.core.PlatonSdk;
import com.platon.sdk.model.request.recurring.PlatonRecurring;
import com.platon.sdk.model.response.base.PlatonBasePayment;
import com.platon.sdk.model.response.base.PlatonApiError;
import com.stanko.tools.Log;

import java.util.Random;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;

public class DescheduleActivity extends BaseActivity implements
		PlatonScheduleCallback,
		View.OnClickListener {

	private final Realm fRealm = DBHelper.getRealmInstance();
	private Button mBtnRandomize;
	private EditText mEtxtPayerEmail;
	private EditText mEtxtCardNumber;
	private EditText mEtxtFirstTransId;
	private EditText mEtxtRecurringToken;
	private EditText mEtxtResponse;
	private Button mBtnDeschedule;

	@Override
	protected void onCreate(@Nullable final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deschedule);

		assignViews();
		configureViews();
	}

	private void assignViews() {
		mBtnRandomize = findViewById(R.id.btn_randomize);
		mEtxtPayerEmail = findViewById(R.id.etxt_payer_email);
		mEtxtCardNumber = findViewById(R.id.etxt_card_number);
		mEtxtFirstTransId = findViewById(R.id.etxt_first_trans_id);
		mEtxtRecurringToken = findViewById(R.id.etxt_recurring_token);
		mEtxtResponse = findViewById(R.id.etxt_response);
		mBtnDeschedule = findViewById(R.id.btn_deschedule);
	}

	private void configureViews() {
		mBtnRandomize.setOnClickListener(this);
		mBtnDeschedule.setOnClickListener(this);

		randomize();
	}

	private void fillResponse(final String response) {
		hideProgress();
		Log.d(response);
		mEtxtResponse.setText(response);
	}

	private void randomize() {
		final RealmResults<Trans> transes = fRealm.where(Trans.class).isNotNull(Trans.RECURRING_TOKEN).findAll();
		if (transes.isEmpty()) return;

		final Trans randomTrans = transes.get(new Random().nextInt(transes.size()));

		mEtxtPayerEmail.setText(randomTrans.getPayerEmail());
		mEtxtCardNumber.setText(randomTrans.getCardNumber());

		mEtxtFirstTransId.setText(randomTrans.getTransId());
		mEtxtRecurringToken.setText(randomTrans.getRecurringToken());

		mEtxtResponse.setText("");
	}

	@Override
	public void onClick(final View v) {
		switch (v.getId()) {
			case R.id.btn_randomize:
				randomize();
				break;
			case R.id.btn_deschedule:
				mEtxtResponse.setText("");

				final PlatonRecurring platonRecurring = new PlatonRecurring(
						String.valueOf(mEtxtFirstTransId.getText()),
						String.valueOf(mEtxtRecurringToken.getText())
				);

				final String payerEmail = String.valueOf(mEtxtPayerEmail.getText());
				final String cardNumber = String.valueOf(mEtxtCardNumber.getText());

				showProgress();
				PlatonSdk.PostPayments.getScheduleAdapter().deschedule(
						platonRecurring, payerEmail, cardNumber, this
				);
				break;
			default:
				break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		DBHelper.closeRealmInstance(fRealm);
	}

	@Override
	public void onResponse(final Call call, final PlatonBasePayment response) {
		fillResponse(response.toString());
	}

	@Override
	public void onError(final Call call, final PlatonApiError platonApiError) {
		fillResponse(platonApiError.toString());
	}

	@Override
	public void onFailure(final Call call, final Throwable t) {
		fillResponse(t.getMessage());
	}
}
