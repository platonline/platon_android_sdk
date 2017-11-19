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
import com.platon.sdk.callback.PlatonTransactionStatusCallback;
import com.platon.sdk.core.PlatonSdk;
import com.platon.sdk.model.response.base.PlatonApiError;
import com.platon.sdk.model.response.transaction.PlatonTransactionStatus;
import com.stanko.tools.Log;

import java.util.Random;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;

public class GetTransStatusActivity extends BaseActivity implements
		PlatonTransactionStatusCallback,
		View.OnClickListener {

	private final Realm fRealm = DBHelper.getRealmInstance();
	private Button mBtnRandomize;
	private EditText mEtxtTransId;
	private EditText mEtxtPayerEmail;
	private EditText mEtxtCardNumber;
	private EditText mEtxtResponse;
	private Button mBtnGetTransStatus;
	private Trans mTrans;

	@Override
	protected void onCreate(@Nullable final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_trans_status);

		assignViews();
		configureViews();
	}

	private void assignViews() {
		mBtnRandomize = findViewById(R.id.btn_randomize);
		mEtxtTransId = findViewById(R.id.etxt_trans_id);
		mEtxtPayerEmail = findViewById(R.id.etxt_payer_email);
		mEtxtCardNumber = findViewById(R.id.etxt_card_number);
		mEtxtResponse = findViewById(R.id.etxt_response);
		mBtnGetTransStatus = findViewById(R.id.btn_get_trans_status);
	}

	private void configureViews() {
		mBtnRandomize.setOnClickListener(this);
		mBtnGetTransStatus.setOnClickListener(this);

		randomize();
	}

	private void fillResponse(final String response) {
		hideProgress();
		Log.d(response);
		mEtxtResponse.setText(response);
	}

	private void randomize() {
		final RealmResults<Trans> transes = fRealm.where(Trans.class).findAll();
		if (transes.isEmpty()) return;
		mTrans = transes.get(new Random().nextInt(transes.size()));

		mEtxtTransId.setText(mTrans.getTransId());
		mEtxtPayerEmail.setText(mTrans.getPayerEmail());
		mEtxtCardNumber.setText(mTrans.getCardNumber());

		mEtxtResponse.setText("");
	}

	@Override
	public void onClick(final View v) {
		switch (v.getId()) {
			case R.id.btn_randomize:
				randomize();
				break;
			case R.id.btn_get_trans_status:
				mEtxtResponse.setText("");

				final String transId = String.valueOf(mEtxtTransId.getText());
				final String payerEmail = String.valueOf(mEtxtPayerEmail.getText());
				final String cardNumber = String.valueOf(mEtxtCardNumber.getText());

				showProgress();
				if (new Random().nextBoolean())
					PlatonSdk.PostPayments.getTransactionAdapter().getTransactionStatus(
							transId, payerEmail, cardNumber, this
					);
				else PlatonSdk.PostPayments.getTransactionAdapter().getTransactionStatus(
						transId, mTrans.getHash(), this
				);
				break;
			default:
				break;
		}
	}

	@Override
	public void onResponse(final Call call, final PlatonTransactionStatus response) {
		fillResponse(response.toString());
	}

	@Override
	public void onError(final Call call, final PlatonApiError platonApiError) {
		fillResponse(platonApiError.toString());
	}

	@Override
	public void onFailure(final Call call, final Throwable t) {
		fillResponse(t.toString());
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		DBHelper.closeRealmInstance(fRealm);
	}
}
