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
import com.platon.sdk.model.request.option.schedule.PlatonScheduleOptions;
import com.platon.sdk.model.request.order.PlatonOrder;
import com.platon.sdk.model.request.recurring.PlatonRecurring;
import com.platon.sdk.model.response.base.PlatonBasePayment;
import com.platon.sdk.model.response.base.PlatonApiError;
import com.slmyldz.random.Randoms;
import com.stanko.tools.Log;

import java.util.Random;

import io.kimo.lib.faker.Faker;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;

import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MAX_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MIN_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Period.ASAP;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Period.MAX_DELAY;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Period.MAX_PERIOD;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Period.MAX_REPEAT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Period.MIN_PERIOD;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Period.UNLIMITED_REPEAT;

public class ScheduleActivity extends BaseActivity implements
		PlatonScheduleCallback,
		View.OnClickListener {

	private final Realm fRealm = DBHelper.getRealmInstance();
	private Button mBtnRandomize;
	private EditText mEtxtOrderAmount;
	private EditText mEtxtOrderDescription;
	private EditText mEtxtPayerEmail;
	private EditText mEtxtCardNumber;
	private EditText mEtxtFirstTransId;
	private EditText mEtxtRecurringToken;
	private EditText mEtxtPeriod;
	private EditText mEtxtInitPeriod;
	private EditText mEtxtRepeatTimes;
	private EditText mEtxtResponse;
	private Button mBtnSchedule;

	@Override
	protected void onCreate(@Nullable final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule);

		assignViews();
		configureViews();
	}

	private void assignViews() {
		mBtnRandomize = findViewById(R.id.btn_randomize);
		mEtxtOrderAmount = findViewById(R.id.etxt_order_amount);
		mEtxtOrderDescription = findViewById(R.id.etxt_order_description);
		mEtxtPayerEmail = findViewById(R.id.etxt_payer_email);
		mEtxtCardNumber = findViewById(R.id.etxt_card_number);
		mEtxtFirstTransId = findViewById(R.id.etxt_first_trans_id);
		mEtxtRecurringToken = findViewById(R.id.etxt_recurring_token);
		mEtxtPeriod = findViewById(R.id.etxt_period);
		mEtxtInitPeriod = findViewById(R.id.etxt_init_period);
		mEtxtRepeatTimes = findViewById(R.id.etxt_repeat_times);
		mEtxtResponse = findViewById(R.id.etxt_response);
		mBtnSchedule = findViewById(R.id.btn_schedule);
	}

	private void configureViews() {
		mBtnRandomize.setOnClickListener(this);
		mBtnSchedule.setOnClickListener(this);

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
		mEtxtOrderAmount.setText(String.valueOf(Randoms.Float(MIN_AMOUNT, MAX_AMOUNT)));
		mEtxtOrderDescription.setText(Faker.with(this).Lorem.sentences());

		mEtxtPayerEmail.setText(randomTrans.getPayerEmail());
		mEtxtCardNumber.setText(randomTrans.getCardNumber());

		mEtxtFirstTransId.setText(randomTrans.getTransId());
		mEtxtRecurringToken.setText(randomTrans.getRecurringToken());

		mEtxtPeriod.setText(String.valueOf(Randoms.Integer(MIN_PERIOD, MAX_PERIOD * 2)));
		mEtxtInitPeriod.setText(String.valueOf(Randoms.Integer(ASAP, MAX_DELAY * 2)));
		mEtxtRepeatTimes.setText(String.valueOf(Randoms.Integer(UNLIMITED_REPEAT, MAX_REPEAT * 2)));

		mEtxtResponse.setText("");
	}

	@Override
	public void onClick(final View v) {
		switch (v.getId()) {
			case R.id.btn_randomize:
				randomize();
				break;
			case R.id.btn_schedule:
				mEtxtResponse.setText("");

				float amount;
				try {
					amount = Float.parseFloat(String.valueOf(mEtxtOrderAmount.getText()));
				} catch (final Exception e) {
					amount = Randoms.Float(MIN_AMOUNT, MAX_AMOUNT * 2.0F);
				}
				final PlatonOrder platonOrder = new PlatonOrder(amount, String.valueOf(mEtxtOrderDescription.getText()));

				final PlatonRecurring platonRecurring = new PlatonRecurring(
						String.valueOf(mEtxtFirstTransId.getText()),
						String.valueOf(mEtxtRecurringToken.getText())
				);

				final String payerEmail = String.valueOf(mEtxtPayerEmail.getText());
				final String cardNumber = String.valueOf(mEtxtCardNumber.getText());

				int period;
				try {
					period = Integer.parseInt(String.valueOf(mEtxtPeriod.getText()));
				} catch (final Exception e) {
					period = Randoms.Integer(MIN_PERIOD, MAX_PERIOD * 2);
				}

				final PlatonScheduleOptions platonScheduleOptions = new PlatonScheduleOptions() {
					{
						try {
							setInitDelayDays(Integer.parseInt(String.valueOf(mEtxtInitPeriod.getText())));
						} catch (final Exception e) {
							setInitDelayDays(
									Integer.parseInt(String.valueOf(Randoms.Integer(ASAP, MAX_DELAY * 2)))
							);
						}

						try {
							setRepeatTimes(Integer.parseInt(String.valueOf(mEtxtRepeatTimes.getText())));
						} catch (final Exception e) {
							setRepeatTimes(
									Integer.parseInt(String.valueOf(Randoms.Integer(UNLIMITED_REPEAT, MAX_REPEAT * 2)))
							);
						}
					}
				};

				showProgress();
				PlatonSdk.PostPayments.getScheduleAdapter().schedule(
						platonOrder,
						platonRecurring,
						period,
						payerEmail,
						cardNumber,
						platonScheduleOptions,
						this
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
