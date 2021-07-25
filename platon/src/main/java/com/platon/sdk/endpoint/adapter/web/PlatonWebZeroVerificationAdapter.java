package com.platon.sdk.endpoint.adapter.web;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.GsonBuilder;
import com.platon.sdk.callback.PlatonWebCallback;
import com.platon.sdk.constant.api.PlatonApiConstants.Formats.Payment;
import com.platon.sdk.core.PlatonCredentials;
import com.platon.sdk.endpoint.adapter.PlatonBaseAdapter;
import com.platon.sdk.endpoint.service.web.PlatonWebZeroVerificationService;
import com.platon.sdk.model.request.option.web.PlatonWebZeroVerificationOptions;
import com.platon.sdk.model.request.order.product.PlatonProductSale;
import com.platon.sdk.model.request.order.product.PlatonProductZeroVerification;
import com.platon.sdk.model.request.payer.PlatonPayerWebSale;
import com.platon.sdk.util.PlatonBase64Util;
import com.platon.sdk.util.PlatonHashUtil;

import retrofit2.Call;

/**
 * API adapter for creating zero verification transaction in web payments platform
 * <p>
 * See {@link PlatonWebZeroVerificationAdapter} for post zero verification payments
 */
public class PlatonWebZeroVerificationAdapter extends PlatonBaseAdapter<PlatonWebZeroVerificationService> {

	private static PlatonWebZeroVerificationAdapter sInstance;

	private PlatonWebZeroVerificationAdapter() {
	}

	public synchronized static PlatonWebZeroVerificationAdapter getInstance() {
		if (sInstance == null) sInstance = new PlatonWebZeroVerificationAdapter();
		return sInstance;
	}

	@Override
	protected Class<PlatonWebZeroVerificationService> getServiceClass() {
		return PlatonWebZeroVerificationService.class;
	}

	@Override
	protected void configureGson(@NonNull final GsonBuilder builder) {
		super.configureGson(builder);
		builder.setLenient();
	}

	/**
	 * For params description see {@link #zeroVerification(
	 *PlatonProductZeroVerification, String, PlatonPayerWebSale, PlatonWebZeroVerificationOptions, PlatonWebCallback)}
	 * <p>
	 * PlatonSale request with single {@link PlatonProductZeroVerification} and without optional fields
	 */
	public Call zeroVerification(
			@NonNull final PlatonProductZeroVerification productZero,
			@NonNull final String successUrl,
			@NonNull final PlatonWebCallback callback
	) {
		return zeroVerification(productZero, successUrl, null, null, callback);
	}


	@SuppressWarnings({"ConstantConditions", "unchecked"})
	public Call zeroVerification(
			@NonNull final PlatonProductZeroVerification platonProductZeroVerification,
			@NonNull final String successUrl,
			@Nullable final PlatonPayerWebSale payerWebSale,
			@Nullable final PlatonWebZeroVerificationOptions zeroVerificationOptions,
			@NonNull final PlatonWebCallback callback
	) {
		final String payment = Payment.CC;
		final String data = PlatonBase64Util.encodeZeroVerification(platonProductZeroVerification);

		final boolean isPayerWebSaleNull = payerWebSale == null;
		final boolean isSaleFormOptionsNull = zeroVerificationOptions == null;

		final Call call = mService.zeroVerification(
				PlatonCredentials.getClientKey(),
				payment,
				data,
				successUrl,
				"verify",
				"Y",
				PlatonHashUtil.encryptSaleWeb(payment, data, successUrl),

				isSaleFormOptionsNull || TextUtils.isEmpty(zeroVerificationOptions.getLanguage()) ?
						null : zeroVerificationOptions.getLanguage(),
				isPayerWebSaleNull || TextUtils.isEmpty(payerWebSale.getEmail()) ?
						null : payerWebSale.getEmail(),
				isPayerWebSaleNull || TextUtils.isEmpty(payerWebSale.getFirstName()) ?
						null : payerWebSale.getFirstName(),
				isPayerWebSaleNull || TextUtils.isEmpty(payerWebSale.getLastName()) ?
						null : payerWebSale.getLastName(),
				isPayerWebSaleNull || TextUtils.isEmpty(payerWebSale.getPhone()) ?
						null : payerWebSale.getPhone(),
				isPayerWebSaleNull || TextUtils.isEmpty(payerWebSale.getAddress()) ?
						null : payerWebSale.getAddress(),
				isPayerWebSaleNull || TextUtils.isEmpty(payerWebSale.getZip()) ?
						null : payerWebSale.getZip(),
				isPayerWebSaleNull || TextUtils.isEmpty(payerWebSale.getCity()) ?
						null : payerWebSale.getCity(),
				isPayerWebSaleNull || TextUtils.isEmpty(payerWebSale.getCountryCode()) ?
						null : payerWebSale.getCountryCode(),
				isPayerWebSaleNull || TextUtils.isEmpty(payerWebSale.getState()) ?
						null : payerWebSale.getState(),
				isSaleFormOptionsNull || TextUtils.isEmpty(zeroVerificationOptions.getCustomerWallet()) ?
						null : zeroVerificationOptions.getCustomerWallet(),
				isSaleFormOptionsNull || TextUtils.isEmpty(zeroVerificationOptions.getOrder()) ?
						null : zeroVerificationOptions.getOrder(),
				isSaleFormOptionsNull || TextUtils.isEmpty(zeroVerificationOptions.getErrorUrl()) ?
						null : zeroVerificationOptions.getErrorUrl(),
				isSaleFormOptionsNull || TextUtils.isEmpty(zeroVerificationOptions.getExt1()) ?
						null : zeroVerificationOptions.getExt1(),
				isSaleFormOptionsNull || TextUtils.isEmpty(zeroVerificationOptions.getExt2()) ?
						null : zeroVerificationOptions.getExt2(),
				isSaleFormOptionsNull || TextUtils.isEmpty(zeroVerificationOptions.getExt3()) ?
						null : zeroVerificationOptions.getExt3(),
				isSaleFormOptionsNull || TextUtils.isEmpty(zeroVerificationOptions.getExt4()) ?
						null : zeroVerificationOptions.getExt4(),
				isSaleFormOptionsNull || TextUtils.isEmpty(zeroVerificationOptions.getExt5()) ?
						null : zeroVerificationOptions.getExt5(),
				isSaleFormOptionsNull || TextUtils.isEmpty(zeroVerificationOptions.getExt6()) ?
						null : zeroVerificationOptions.getExt6(),
				isSaleFormOptionsNull || TextUtils.isEmpty(zeroVerificationOptions.getExt7()) ?
						null : zeroVerificationOptions.getExt7(),
				isSaleFormOptionsNull || TextUtils.isEmpty(zeroVerificationOptions.getExt8()) ?
						null : zeroVerificationOptions.getExt8(),
				isSaleFormOptionsNull || TextUtils.isEmpty(zeroVerificationOptions.getExt9()) ?
						null : zeroVerificationOptions.getExt9(),
				isSaleFormOptionsNull || TextUtils.isEmpty(zeroVerificationOptions.getExt10()) ?
						null : zeroVerificationOptions.getExt10(),
				isSaleFormOptionsNull || TextUtils.isEmpty(zeroVerificationOptions.getBankId()) ?
						null : zeroVerificationOptions.getBankId(),
				isSaleFormOptionsNull || TextUtils.isEmpty(zeroVerificationOptions.getPayerId()) ?
						null : zeroVerificationOptions.getPayerId()
		);
		call.enqueue(callback);

		return call;
	}

}
