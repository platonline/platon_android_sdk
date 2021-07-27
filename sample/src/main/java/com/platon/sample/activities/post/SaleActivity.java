package com.platon.sample.activities.post;

import android.os.Bundle;
import androidx.annotation.Nullable;

import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.platon.sample.R;
import com.platon.sample.activities.BaseActivity;
import com.platon.sample.db.DBHelper;
import com.platon.sample.db.models.Trans;
import com.platon.sample.utils.DecimalDigitsInputFilter;
import com.platon.sdk.callback.PlatonSaleCallback;
import com.platon.sdk.constant.api.PlatonOption;
import com.platon.sdk.core.PlatonSdk;
import com.platon.sdk.model.request.PlatonCard;
import com.platon.sdk.model.request.option.PlatonSaleOptions;
import com.platon.sdk.model.request.order.PlatonOrderSale;
import com.platon.sdk.model.request.payer.PlatonPayerSale;
import com.platon.sdk.model.response.base.PlatonApiError;
import com.platon.sdk.model.response.sale.PlatonSale;
import com.platon.sdk.model.response.sale.PlatonSale3DSecure;
import com.platon.sdk.model.response.sale.PlatonSaleDecline;
import com.platon.sdk.model.response.sale.PlatonSaleSuccess;
import com.platon.sdk.model.response.sale.PlatonSaleRecurringInit;
import com.platon.sdk.util.Platon3dsSubmitUtil;
import com.slmyldz.random.Randoms;
import com.stanko.tools.Log;

import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import io.kimo.lib.faker.Faker;
import retrofit2.Call;

import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MAX_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MIN_AMOUNT;

public class SaleActivity extends BaseActivity implements
		View.OnClickListener,
		PlatonSaleCallback {

	private Button mBtnRandomize;
	private EditText mEtxtOrderId;
	private EditText mEtxtOrderAmount;
	private EditText mEtxtOrderDescription;
	private EditText mEtxtOrderCurrencyCode;
	private EditText mEtxtPayerFirstName;
	private EditText mEtxtPayerLastName;
	private EditText mEtxtPayerAddress;
	private EditText mEtxtPayerCountryCode;
	private EditText mEtxtPayerState;
	private EditText mEtxtPayerCity;
	private EditText mEtxtPayerZip;
	private EditText mEtxtPayerEmail;
	private EditText mEtxtPayerPhone;
	private EditText mEtxtPayerIpAddress;
	private RadioGroup mRgCard;
	private CheckBox mCbAsync;
	private CheckBox mCbRecurringInit;
	private EditText mEtxtChannelId;
	private EditText mEtxtResponse;
	private Button mBtnSale;
	private Button mBtnAuth;

	private Trans mTrans;

	@Override
	protected void onCreate(@Nullable final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sale);

		assignViews();
		configureViews();
	}

	private void assignViews() {
		mBtnRandomize = findViewById(R.id.btn_randomize);
		mEtxtOrderId = findViewById(R.id.etxt_order_id);
		mEtxtOrderAmount = findViewById(R.id.etxt_order_amount);
		mEtxtOrderDescription = findViewById(R.id.etxt_order_description);
		mEtxtOrderCurrencyCode = findViewById(R.id.etxt_order_currency_code);
		mEtxtPayerFirstName = findViewById(R.id.etxt_payer_first_name);
		mEtxtPayerLastName = findViewById(R.id.etxt_payer_last_name);
		mEtxtPayerAddress = findViewById(R.id.etxt_payer_address);
		mEtxtPayerCountryCode = findViewById(R.id.etxt_payer_country_code);
		mEtxtPayerState = findViewById(R.id.etxt_payer_state);
		mEtxtPayerCity = findViewById(R.id.etxt_payer_city);
		mEtxtPayerZip = findViewById(R.id.etxt_payer_zip);
		mEtxtPayerEmail = findViewById(R.id.etxt_payer_email);
		mEtxtPayerPhone = findViewById(R.id.etxt_payer_phone);
		mEtxtPayerIpAddress = findViewById(R.id.etxt_payer_ip_address);
		mRgCard = findViewById(R.id.rg_card);
		mCbAsync = findViewById(R.id.cb_async);
		mCbRecurringInit = findViewById(R.id.cb_recurring_init);
		mEtxtChannelId = findViewById(R.id.etxt_channel_id);
		mEtxtResponse = findViewById(R.id.etxt_response);
		mBtnSale = findViewById(R.id.btn_sale);
		mBtnAuth = findViewById(R.id.btn_auth);
	}

	private void configureViews() {
		mBtnRandomize.setOnClickListener(this);
		mBtnSale.setOnClickListener(this);
		mBtnAuth.setOnClickListener(this);

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
		final Random random = new Random();


		mEtxtOrderId.setText(String.valueOf(UUID.randomUUID()));
		mEtxtOrderAmount.setText(String.format(Locale.US, "%.2f", (Randoms.Float(MIN_AMOUNT, MAX_AMOUNT * 2.0F))));
		mEtxtOrderDescription.setText(Faker.with(this).Lorem.sentences());
		mEtxtOrderCurrencyCode.setText(R.string.uah_currency);

		mEtxtPayerFirstName.setText(Faker.with(this).Name.lastName());
		mEtxtPayerLastName.setText(Faker.with(this).Name.lastName());
		mEtxtPayerAddress.setText(Faker.with(this).Address.secondaryAddress());
		mEtxtPayerCountryCode.setText(Faker.with(this).Address.countryAbbreviation());
		mEtxtPayerState.setText(Faker.with(this).Address.state());
		mEtxtPayerCity.setText(Faker.with(this).Address.city());
		mEtxtPayerZip.setText(Faker.with(this).Address.zipCode());
		mEtxtPayerEmail.setText(Faker.with(this).Internet.email());
		mEtxtPayerPhone.setText(Faker.with(this).Phone.phoneWithAreaCode());
		String address = random.nextInt(256) + "." + random.nextInt(256) + "." + random.nextInt(256) + "." + random.nextInt(256);
		mEtxtPayerIpAddress.setText(address);

		mRgCard.check(mRgCard.getChildAt(random.nextInt(mRgCard.getChildCount())).getId());

		mCbAsync.setChecked(random.nextBoolean());
		mCbRecurringInit.setChecked(random.nextBoolean());
		mEtxtChannelId.setText(String.valueOf(UUID.randomUUID()));

		mEtxtResponse.setText("");
		mEtxtOrderAmount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(2)});
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
			final PlatonOrderSale order = new PlatonOrderSale(
					String.valueOf(mEtxtOrderId.getText()),
					amount,
					String.valueOf(mEtxtOrderCurrencyCode.getText()),
					String.valueOf(mEtxtOrderDescription.getText())
			);

			final PlatonPayerSale payer = new PlatonPayerSale(
					String.valueOf(mEtxtPayerFirstName.getText()),
					String.valueOf(mEtxtPayerLastName.getText()),
					String.valueOf(mEtxtPayerAddress.getText()),
					String.valueOf(mEtxtPayerCountryCode.getText()),
					String.valueOf(mEtxtPayerState.getText()),
					String.valueOf(mEtxtPayerCity.getText()),
					String.valueOf(mEtxtPayerZip.getText()),
					String.valueOf(mEtxtPayerEmail.getText()),
					String.valueOf(mEtxtPayerPhone.getText()),
					String.valueOf(mEtxtPayerIpAddress.getText())
			);

			final PlatonCard platonCard;
			int checkedRadioButtonId = mRgCard.getCheckedRadioButtonId();
			if (checkedRadioButtonId == R.id.rb_card_unsuccess) {
				platonCard = PlatonCard.Test.UNSUCCESS;
			} else if (checkedRadioButtonId == R.id.rb_card_3ds_success) {
				platonCard = PlatonCard.Test.SUCCESS_3D;
			} else if (checkedRadioButtonId == R.id.rb_card_3ds_unsuccess) {
				platonCard = PlatonCard.Test.UNSUCCESS_3D;
			} else {
				platonCard = PlatonCard.Test.SUCCESS;
			}

			final PlatonSaleOptions platonSaleOptions = new PlatonSaleOptions.Builder()
					.async(mCbAsync.isChecked() ? PlatonOption.YES : PlatonOption.NO)
					.recurringInit(mCbRecurringInit.isChecked() ? PlatonOption.YES : PlatonOption.NO)
					.channelId(String.valueOf(mEtxtChannelId.getText()))
					.build();

			mTrans = new Trans(payer.getEmail(), platonCard.getNumber());

			showProgress();
			if (v.getId() == R.id.btn_sale) PlatonSdk.PostPayments.getSaleAdapter().sale(
					order, platonCard, payer, platonSaleOptions, this
			);
			else PlatonSdk.PostPayments.getSaleAdapter().auth(
					order, platonCard, payer, platonSaleOptions, this
			);
		}
	}

	@Override
	public void onDeclineResponse(final Call call, final PlatonSaleDecline response) {
		fillResponse(response.toString());
		saveTrans(response.getTransactionId());
	}

	@Override
	public void onRecurringInitResponse(final Call call, final PlatonSaleRecurringInit response) {
		fillResponse(response.toString());

		mTrans.setRecurringToken(response.getRecurringToken());
		saveTrans(response.getTransactionId());
	}

	@Override
	public void on3dSecureResponse(final Call call, final PlatonSale3DSecure response) {
		fillResponse(response.toString());
		saveTrans(response.getTransactionId());

		showProgress();
		Platon3dsSubmitUtil.submit3dsData(
				response, new Platon3dsSubmitUtil.OnSubmit3dsDataListener() {
					@Override
					public void on3dsDataSubmitted(final String htmlData) {
						Submit3dsDataActivity.performTransaction(SaleActivity.this, htmlData);
						hideProgress();
					}

					@Override
					public void onFailure(final Throwable throwable) {
						fillResponse(throwable.toString());
						hideProgress();
					}
				}
		);
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
	public void onError(final Call call, final PlatonApiError platonApiError) {
		fillResponse(platonApiError.toString());
	}

	@Override
	public void onFailure(final Call call, final Throwable t) {
		fillResponse(t.toString());
	}
}
