package com.platon.sdk.endpoint.adapter.web;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.Size;
import android.text.TextUtils;

import com.google.gson.GsonBuilder;
import com.platon.sdk.callback.PlatonWebCallback;
import com.platon.sdk.constant.api.PlatonApiConstants.Formats.Payment;
import com.platon.sdk.core.PlatonCredentials;
import com.platon.sdk.endpoint.adapter.PlatonBaseAdapter;
import com.platon.sdk.endpoint.adapter.post.PlatonSaleAdapter;
import com.platon.sdk.endpoint.service.web.PlatonWebSaleService;
import com.platon.sdk.model.request.option.web.PlatonWebSaleOptions;
import com.platon.sdk.model.request.order.product.PlatonProductSale;
import com.platon.sdk.model.request.payer.PlatonPayerSale;
import com.platon.sdk.model.request.payer.PlatonPayerWebSale;
import com.platon.sdk.util.PlatonBase64Util;
import com.platon.sdk.util.PlatonHashUtil;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;

import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT_MIN;

/**
 * API adapter for creating SALE transaction in web payments platform
 * <p>
 * See {@link PlatonSaleAdapter} for post SALE payments
 */
public class PlatonWebSaleAdapter extends PlatonBaseAdapter<PlatonWebSaleService> {

	private static PlatonWebSaleAdapter sInstance;

	private PlatonWebSaleAdapter() {
	}

	public synchronized static PlatonWebSaleAdapter getInstance() {
		if (sInstance == null) sInstance = new PlatonWebSaleAdapter();
		return sInstance;
	}

	@Override
	protected Class<PlatonWebSaleService> getServiceClass() {
		return PlatonWebSaleService.class;
	}

	@Override
	protected void configureGson(@NonNull final GsonBuilder builder) {
		super.configureGson(builder);
		builder.setLenient();
	}

	/**
	 * For params description see {@link #sale(
	 *List, String, String, PlatonPayerWebSale, PlatonWebSaleOptions, PlatonWebCallback)}
	 * <p>
	 * PlatonSale request with single {@link PlatonProductSale} and without optional fields
	 */
	public Call sale(
			@NonNull final PlatonProductSale productSale,
			@NonNull final String successUrl,
			@NonNull final PlatonWebCallback callback
	) {
		return sale(productSale, successUrl, null, null, null, callback);
	}

	/**
	 * For params description see {@link #sale(
	 *List, String, String, PlatonPayerWebSale, PlatonWebSaleOptions, PlatonWebCallback)}
	 * <p>
	 * PlatonSale request without optional fields
	 */
	public Call sale(
			@NonNull final List<PlatonProductSale> productSales,
			@NonNull final String successUrl,
			@NonNull final PlatonWebCallback callback
	) {
		return sale(productSales, successUrl, null, null, null, callback);
	}

	/**
	 * For params description see {@link #sale(
	 *List, String, String, PlatonPayerWebSale, PlatonWebSaleOptions, PlatonWebCallback)}
	 * <p>
	 * PlatonSale request with single {@link PlatonProductSale}
	 */
	public Call sale(
			@NonNull final PlatonProductSale productSale,
			@NonNull final String successUrl,
			@Nullable @Size(max = TEXT_MIN) final String orderId,
			@Nullable final PlatonPayerWebSale payerWebSale,
			@Nullable final PlatonWebSaleOptions webSaleOptions,
			@NonNull final PlatonWebCallback callback
	) {
		return sale(
				Collections.singletonList(productSale),
				successUrl,
				orderId,
				payerWebSale,
				webSaleOptions,
				callback
		);
	}

	/**
	 * Following requests creates SALE transaction in payment platform
	 * It is used for authorization and capture at a time
	 * This operation is used for immediate web payments
	 *
	 * @param productSales   - list of products for sale transaction
	 *                       See {@link PlatonProductSale} for the details
	 * @param successUrl     - url by which you proceed after successful payment
	 * @param orderId        - order id in payment system
	 * @param payerWebSale   - info holder of payer
	 *                       See {@link PlatonPayerWebSale} or {@link PlatonPayerSale}
	 * @param webSaleOptions - options to control web form representation
	 *                       See {@link PlatonWebSaleOptions} for its params
	 * @param callback       - callback which will hold url for web request
	 * @return original call of {@link PlatonWebSaleService sale(...)} request
	 */
	@SuppressWarnings({"ConstantConditions", "unchecked"})
	public Call sale(
			@NonNull final List<PlatonProductSale> productSales,
			@NonNull final String successUrl,
			@Nullable @Size(max = TEXT_MIN) final String orderId,
			@Nullable final PlatonPayerWebSale payerWebSale,
			@Nullable final PlatonWebSaleOptions webSaleOptions,
			@NonNull final PlatonWebCallback callback
	) {
		final String payment = Payment.CC;
		final String data = PlatonBase64Util.encodeProducts(productSales);

		final boolean isPayerWebSaleNull = payerWebSale == null;
		final boolean isSaleFormOptionsNull = webSaleOptions == null;

		final Call call = mService.sale(
				PlatonCredentials.getClientKey(),
				payment,
				data,
				successUrl,

				orderId,

				isPayerWebSaleNull || TextUtils.isEmpty(payerWebSale.getFirstName()) ?
						null : payerWebSale.getFirstName(),
				isPayerWebSaleNull || TextUtils.isEmpty(payerWebSale.getLastName()) ?
						null : payerWebSale.getLastName(),
				isPayerWebSaleNull || TextUtils.isEmpty(payerWebSale.getEmail()) ?
						null : payerWebSale.getEmail(),
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
				isPayerWebSaleNull || TextUtils.isEmpty(payerWebSale.getPhone()) ?
						null : payerWebSale.getPhone(),

				isSaleFormOptionsNull || TextUtils.isEmpty(webSaleOptions.getLanguage()) ?
						null : webSaleOptions.getLanguage(),
				isSaleFormOptionsNull || TextUtils.isEmpty(webSaleOptions.getErrorUrl()) ?
						null : webSaleOptions.getErrorUrl(),
				isSaleFormOptionsNull || TextUtils.isEmpty(webSaleOptions.getFormId())
						? null : webSaleOptions.getFormId(),
				isSaleFormOptionsNull || TextUtils.isEmpty(webSaleOptions.getReqToken())
						? null : webSaleOptions.getReqToken(),
				isSaleFormOptionsNull || TextUtils.isEmpty(webSaleOptions.getExt1())
						? null : webSaleOptions.getExt1(),
				isSaleFormOptionsNull || TextUtils.isEmpty(webSaleOptions.getExt2())
						? null : webSaleOptions.getExt2(),
				isSaleFormOptionsNull || TextUtils.isEmpty(webSaleOptions.getExt3())
						? null : webSaleOptions.getExt3(),
				isSaleFormOptionsNull || TextUtils.isEmpty(webSaleOptions.getExt4())
						? null : webSaleOptions.getExt4(),
				isSaleFormOptionsNull || TextUtils.isEmpty(webSaleOptions.getExt10())
						? null : webSaleOptions.getExt10(),

				PlatonHashUtil.encryptSaleWeb(payment, data, successUrl)
		);
		call.enqueue(callback);

		return call;
	}

}
