package com.platon.sample.activities.post;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.platon.sample.R;
import com.platon.sample.activities.BaseActivity;
import com.platon.sample.db.DBHelper;
import com.platon.sample.db.models.Trans;
import com.platon.sdk.callback.PlatonCaptureCallback;
import com.platon.sdk.core.PlatonSdk;
import com.platon.sdk.model.response.base.PlatonApiError;
import com.platon.sdk.model.response.capture.PlatonCaptureDecline;
import com.platon.sdk.model.response.capture.PlatonCaptureSuccess;
import com.slmyldz.random.Randoms;
import com.stanko.tools.Log;

import java.util.Random;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;

import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MAX_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MIN_AMOUNT;

public class CaptureActivity extends BaseActivity implements
		View.OnClickListener,
		PlatonCaptureCallback {

	private final Realm fRealm = DBHelper.getRealmInstance();
	private Button mBtnRandomize;
	private EditText mEtxtTransId;
	private EditText mEtxtPayerEmail;
	private EditText mEtxtCardNumber;
	private EditText mEtxtPartialAmount;
	private EditText mEtxtResponse;
	private Button mBtnCapture;
	private Trans mTrans;

	@Override
	protected void onCreate(@Nullable final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_capture);

		assignViews();
		configureViews();
	}

	private void assignViews() {
		mBtnRandomize = findViewById(R.id.btn_randomize);
		mEtxtTransId = findViewById(R.id.etxt_trans_id);
		mEtxtPayerEmail = findViewById(R.id.etxt_payer_email);
		mEtxtCardNumber = findViewById(R.id.etxt_card_number);
		mEtxtPartialAmount = findViewById(R.id.etxt_partial_amount);
		mEtxtResponse = findViewById(R.id.etxt_response);
		mBtnCapture = findViewById(R.id.btn_capture);
	}

	private void configureViews() {
		mBtnRandomize.setOnClickListener(this);
		mBtnCapture.setOnClickListener(this);

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
		mEtxtPartialAmount.setText(String.valueOf(Randoms.Float(MIN_AMOUNT, MAX_AMOUNT * 2.0F)));

		mEtxtResponse.setText("");
	}

	@Override
	public void onClick(final View v) {
		int id = v.getId();
		if (id == R.id.btn_randomize) {
			randomize();
		} else if (id == R.id.btn_capture) {
			mEtxtResponse.setText("");

			final String transId = String.valueOf(mEtxtTransId.getText());
			final String payerEmail = String.valueOf(mEtxtPayerEmail.getText());
			final String cardNumber = String.valueOf(mEtxtCardNumber.getText());
			float partialAmount;
			try {
				partialAmount = Float.parseFloat(String.valueOf(mEtxtPartialAmount.getText()));
			} catch (final Exception e) {
				partialAmount = Float.parseFloat(
						String.valueOf(Randoms.Float(MIN_AMOUNT, MAX_AMOUNT))
				);
			}

			showProgress();
			if (new Random().nextBoolean())
				PlatonSdk.PostPayments.getCaptureAdapter().capture(
						transId, payerEmail, cardNumber, partialAmount, this
				);
			else PlatonSdk.PostPayments.getCaptureAdapter().capture(
					transId, mTrans.getHash(), partialAmount, this
			);
		}
	}

	@Override
	public void onResponse(final Call call, final PlatonCaptureSuccess response) {
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
	public void onDeclineResponse(final Call call, final PlatonCaptureDecline response) {
		fillResponse(response.toString());
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		DBHelper.closeRealmInstance(fRealm);
	}
}
