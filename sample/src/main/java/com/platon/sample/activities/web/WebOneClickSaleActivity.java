package com.platon.sample.activities.web;

import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.platon.sample.R;
import com.platon.sample.activities.BaseActivity;
import com.platon.sdk.callback.PlatonWebCallback;
import com.platon.sdk.core.PlatonSdk;
import com.platon.sdk.model.request.option.web.PlatonWebSaleOptions;
import com.platon.sdk.model.request.order.product.PlatonProductSale;
import com.platon.sdk.model.request.recurring.PlatonRecurringWeb;
import com.slmyldz.random.Randoms;

import java.util.Random;
import java.util.UUID;

import io.kimo.lib.faker.Faker;
import retrofit2.Call;
import retrofit2.Response;

import static com.platon.sample.app.PlatonApp.isValidURL;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MAX_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MIN_AMOUNT;

public class WebOneClickSaleActivity extends BaseActivity implements
		View.OnClickListener,
		PlatonWebCallback {

	private Button mBtnRandomize;
	private EditText mEtxtFirstTransId;
	private EditText mEtxtRecurringToken;
	private EditText mEtxtOrderAmount;
	private EditText mEtxtOrderDescription;
	private EditText mEtxtOrderCurrencyCode;
	private EditText mEtxtSuccessUrl;
	private EditText mEtxtOrderId;
	private EditText mEtxtLanguage;
	private EditText mEtxtErrorUrl;
	private EditText mEtxtFormId;
	private EditText mEtxtExt1;
	private EditText mEtxtExt2;
	private EditText mEtxtExt3;
	private EditText mEtxtExt4;
	private EditText mEtxtExt10;
	private Button mBtnOneClickSale;

	@Override
	protected void onCreate(@Nullable final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_one_click_sale);

		assignViews();
		configureViews();
	}

	private void assignViews() {
		mBtnRandomize = findViewById(R.id.btn_randomize);
		mEtxtFirstTransId = findViewById(R.id.etxt_first_trans_id);
		mEtxtRecurringToken = findViewById(R.id.etxt_recurring_token);
		mEtxtOrderAmount = findViewById(R.id.etxt_order_amount);
		mEtxtOrderDescription = findViewById(R.id.etxt_order_description);
		mEtxtOrderCurrencyCode = findViewById(R.id.etxt_order_currency_code);
		mEtxtSuccessUrl = findViewById(R.id.etxt_success_url);
		mEtxtOrderId = findViewById(R.id.etxt_order_id);
		mEtxtLanguage = findViewById(R.id.etxt_language);
		mEtxtErrorUrl = findViewById(R.id.etxt_error_url);
		mEtxtFormId = findViewById(R.id.etxt_form_id);
		mEtxtExt1 = findViewById(R.id.etxt_ext_1);
		mEtxtExt2 = findViewById(R.id.etxt_ext_2);
		mEtxtExt3 = findViewById(R.id.etxt_ext_3);
		mEtxtExt4 = findViewById(R.id.etxt_ext_4);
		mEtxtExt10 = findViewById(R.id.etxt_ext_10);
		mBtnOneClickSale = findViewById(R.id.btn_one_click_sale);
	}

	private void configureViews() {
		mBtnRandomize.setOnClickListener(this);
		mBtnOneClickSale.setOnClickListener(this);

		randomize();
	}

	private void randomize() {
		final Random random = new Random();

		mEtxtFirstTransId.setText(String.valueOf(UUID.randomUUID()));
		mEtxtRecurringToken.setText(String.valueOf(UUID.randomUUID()));

		mEtxtOrderId.setText(String.valueOf(UUID.randomUUID()));
		mEtxtOrderAmount.setText(String.valueOf(Randoms.Float(MIN_AMOUNT, MAX_AMOUNT * 2.0F)));
		mEtxtOrderDescription.setText(Faker.with(this).Lorem.sentences());
		mEtxtOrderCurrencyCode.setText("UAH");

		mEtxtSuccessUrl.setText(Faker.with(this).Internet.url());
		final int randomLanguage = random.nextInt(3);
		final String language;
		switch (randomLanguage) {
			case 0:
				language = "RU";
				break;
			case 1:
				language = "EN";
				break;
			case 2:
			default:
				language = "UK";
				break;
		}
		mEtxtLanguage.setText(language);
		mEtxtErrorUrl.setText(Faker.with(this).Internet.url());
//        mEtxtFormId.setText(UUID.randomUUID().toString());
		mEtxtExt1.setText(Faker.with(this).Url.avatar());
		mEtxtExt2.setText(Faker.with(this).Url.avatar());
		mEtxtExt3.setText(Faker.with(this).Url.avatar());
		mEtxtExt4.setText(Faker.with(this).Url.avatar());
		mEtxtExt10.setText(Faker.with(this).Url.avatar());
	}

	@Override
	public void onClick(final View v) {
		switch (v.getId()) {
			case R.id.btn_randomize:
				randomize();
				break;
			case R.id.btn_one_click_sale:
				final String successUrl = String.valueOf(mEtxtSuccessUrl.getText());
				if (!isValidURL(successUrl)) {
					Toast.makeText(this, "Invalid success url", Toast.LENGTH_SHORT).show();
					return;
				}

				final PlatonRecurringWeb recurringWeb = new PlatonRecurringWeb(
						String.valueOf(mEtxtFirstTransId.getText()),
						String.valueOf(mEtxtRecurringToken.getText())
				);

				float amount;
				try {
					amount = Float.parseFloat(String.valueOf(mEtxtOrderAmount.getText()));
				} catch (final Exception e) {
					amount = Randoms.Float(MIN_AMOUNT, MAX_AMOUNT * 2.0F);
				}
				final PlatonProductSale platonProductSale = new PlatonProductSale(
						amount, String.valueOf(mEtxtOrderDescription.getText())
				);
				platonProductSale.setCurrencyCode(String.valueOf(mEtxtOrderCurrencyCode.getText()));

				final PlatonWebSaleOptions webSaleOptions = new PlatonWebSaleOptions.Builder()
						.language(String.valueOf(mEtxtLanguage.getText()))
						.errorUrl(String.valueOf(mEtxtErrorUrl.getText()))
						.formId(String.valueOf(mEtxtFormId.getText()))
						.ext1(String.valueOf(mEtxtExt1.getText()))
						.ext2(String.valueOf(mEtxtExt2.getText()))
						.ext3(String.valueOf(mEtxtExt3.getText()))
						.ext4(String.valueOf(mEtxtExt4.getText()))
						.ext10(String.valueOf(mEtxtExt10.getText()))
						.build();

				showProgress();
				PlatonSdk.WebPayments.getOneClickSaleAdapter().oneClickSale(
						platonProductSale,
						recurringWeb,
						successUrl,
						String.valueOf(mEtxtOrderId.getText()),
						webSaleOptions,
						this
				);
				break;
			default:
				break;
		}
	}

	@Override
	public void onResponse(final Call<String> call, final Response<String> response) {
		hideProgress();
		Log.d("Response: ", response.toString());

		final CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
		final CustomTabsIntent customTabsIntent = builder.build();
		customTabsIntent.launchUrl(this, Uri.parse(response.raw().request().url().toString()));
	}

	@Override
	public void onFailure(final Call<String> call, final Throwable t) {
		hideProgress();
		Toast.makeText(this, t.getMessage(), Toast.LENGTH_LONG).show();
	}

}
