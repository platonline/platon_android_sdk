package com.platon.sdk.endpoint.adapter.post;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.platon.sdk.callback.PlatonSaleCallback;
import com.platon.sdk.constant.api.PlatonOption;
import com.platon.sdk.constant.api.PlatonApiConstants.MethodProperties;
import com.platon.sdk.constant.api.action.PlatonAction;
import com.platon.sdk.core.PlatonCredentials;
import com.platon.sdk.deserializer.PlatonSaleDeserializer;
import com.platon.sdk.endpoint.adapter.PlatonBaseAdapter;
import com.platon.sdk.endpoint.service.post.PlatonSaleService;
import com.platon.sdk.model.request.PlatonCard;
import com.platon.sdk.model.request.option.PlatonSaleOptions;
import com.platon.sdk.model.request.order.PlatonOrderSale;
import com.platon.sdk.model.request.payer.PlatonPayerSale;
import com.platon.sdk.model.response.base.PlatonBasePayment;
import com.platon.sdk.model.response.base.PlatonBaseResponse;
import com.platon.sdk.model.response.sale.PlatonSale;
import com.platon.sdk.model.response.sale.PlatonSale3DSecure;
import com.platon.sdk.model.response.sale.PlatonSaleDecline;
import com.platon.sdk.model.response.sale.PlatonSaleSuccess;
import com.platon.sdk.model.response.sale.PlatonSaleRecurringInit;
import com.platon.sdk.model.response.sale.PlatonSaleResponse;
import com.platon.sdk.util.PlatonHashUtil;
import com.platon.sdk.util.PlatonSdkUtil;

import retrofit2.Call;

/**
 * API adapter to facilitate transaction payment capture during SMS and first stage of DMS
 * <p>
 * See also {@link PlatonSaleService} and {@link PlatonCaptureAdapter}
 */
public class PlatonSaleAdapter extends PlatonBaseAdapter<PlatonSaleService> implements
		PlatonBaseAdapter.OnConfigureResponseListener<PlatonSaleCallback, PlatonSaleSuccess> {

	private static PlatonSaleAdapter sInstance;

	private PlatonSaleAdapter() {
	}

	public synchronized static PlatonSaleAdapter getInstance() {
		if (sInstance == null) sInstance = new PlatonSaleAdapter();
		return sInstance;
	}

	@Override
	protected Class<PlatonSaleService> getServiceClass() {
		return PlatonSaleService.class;
	}

	@Override
	protected void configureGson(@NonNull final GsonBuilder builder) {
		super.configureGson(builder);
		builder.registerTypeAdapter(
				new TypeToken<PlatonSaleResponse>() {
				}.getType(), new PlatonSaleDeserializer()
		);
	}

	/**
	 * For params description see {@link #sale(PlatonOrderSale, PlatonCard, PlatonPayerSale, PlatonSaleOptions, PlatonSaleCallback)}}
	 * PlatonSale request without optional fields.
	 */
	public Call sale(
			@NonNull final PlatonOrderSale order,
			@NonNull final PlatonCard platonCard,
			@NonNull final PlatonPayerSale payerSale,
			@NonNull final PlatonSaleCallback platonSaleCallback
	) {
		return sale(order, platonCard, payerSale, null, platonSaleCallback);
	}

	/**
	 * Single Message System (SMS)
	 * SMS is represented by SALE transaction. It is used for authorization and capture at a time.
	 * This operation is commonly used for immediate payments.
	 *
	 * @param order        - order data. See @{@link PlatonOrderSale} for details (Required)
	 * @param platonCard         - credit platonCard data. See @{@link PlatonCard} for details (Required)
	 * @param payerSale    - payer data. See @{@link PlatonPayerSale} for details (Required)
	 * @param platonSaleOptions  - sale options for your request to Payment Platform. See @{@link PlatonSaleOptions}
	 * @param platonSaleCallback - callback that will return response
	 */
	public Call sale(
			@NonNull final PlatonOrderSale order,
			@NonNull final PlatonCard platonCard,
			@NonNull final PlatonPayerSale payerSale,
			@Nullable final PlatonSaleOptions platonSaleOptions,
			@NonNull final PlatonSaleCallback platonSaleCallback
	) {
		return performSale(order, platonCard, payerSale, platonSaleOptions, platonSaleCallback, PlatonOption.NO);
	}

	/**
	 * For params description see {@link #sale(PlatonOrderSale, PlatonCard, PlatonPayerSale, PlatonSaleOptions, PlatonSaleCallback)}}
	 * Auth request without optional fields.
	 */
	public Call auth(
			@NonNull final PlatonOrderSale order,
			@NonNull final PlatonCard platonCard,
			@NonNull final PlatonPayerSale payerSale,
			@NonNull final PlatonSaleCallback platonSaleCallback
	) {
		return auth(order, platonCard, payerSale, null, platonSaleCallback);
	}

	/**
	 * For params description see {@link #sale(PlatonOrderSale, PlatonCard, PlatonPayerSale, PlatonSaleOptions, PlatonSaleCallback)}}
	 * <p>
	 * Dual Message System (DMS)
	 * DMS is represented by {@link MethodProperties#AUTH} and {@link PlatonAction#CAPTURE} transactions.
	 * <p>
	 * AUTH is used for authorization only, without capture.
	 * This operation used to hold the funds on platonCard account (for example to check platonCard validity).
	 * In other words this is the first step of two step transaction.
	 * Funds are deducted and held from the customer’s credit card.
	 * Note that the money is not transferred to the merchant account yet.
	 * This is when credit card capture comes in.
	 * <p>
	 * See @{@link PlatonCaptureAdapter}.
	 */
	public Call auth(
			@NonNull final PlatonOrderSale order,
			@NonNull final PlatonCard platonCard,
			@NonNull final PlatonPayerSale payerSale,
			@Nullable final PlatonSaleOptions platonSaleOptions,
			@NonNull final PlatonSaleCallback platonSaleCallback
	) {
		return performSale(order, platonCard, payerSale, platonSaleOptions, platonSaleCallback, PlatonOption.YES);
	}

	/**
	 * For params description see {@link #sale(PlatonOrderSale, PlatonCard, PlatonPayerSale, PlatonSaleOptions, PlatonSaleCallback)}}
	 *
	 * @param auth indicates whether the SALE request with AUTH.
	 */
	@SuppressWarnings({"unchecked", "ConstantConditions"})
	private Call performSale(
			@NonNull final PlatonOrderSale order,
			@NonNull final PlatonCard platonCard,
			@NonNull final PlatonPayerSale payerSale,
			@Nullable final PlatonSaleOptions platonSaleOptions,
			@NonNull final PlatonSaleCallback platonSaleCallback,
			@NonNull @PlatonOption final String auth
	) {
		final String orderAmountFormat = PlatonSdkUtil.getAmountFormat(order.getAmount());
		final String cardExpireYearFormat = String.valueOf(platonCard.getExpireYear());
		final boolean isSaleOptionsNull = platonSaleOptions == null;

		final Call call = mService.sale(
				PlatonAction.SALE,
				PlatonCredentials.getClientKey(),

				order.getId(),
				orderAmountFormat,
				order.getCurrencyCode(),
				order.getDescription(),

				platonCard.getNumber(),
				platonCard.getExpireMonthFormat(),
				cardExpireYearFormat,
				platonCard.getCvv2(),

				payerSale.getFirstName(),
				payerSale.getLastName(),
				payerSale.getAddress(),
				payerSale.getCountryCode(),
				payerSale.getState(),
				payerSale.getCity(),
				payerSale.getZip(),
				payerSale.getEmail(),
				payerSale.getPhone(),
				payerSale.getIpAddress(),

				PlatonHashUtil.encryptSale(payerSale.getEmail(), platonCard.getNumber()),

				isSaleOptionsNull || TextUtils.isEmpty(platonSaleOptions.getAsync()) ?
						null : platonSaleOptions.getAsync(),
				isSaleOptionsNull || TextUtils.isEmpty(platonSaleOptions.getChannelId()) ?
						null : platonSaleOptions.getChannelId(),
				PlatonCredentials.getTermUrl3Ds(),
				isSaleOptionsNull || TextUtils.isEmpty(platonSaleOptions.getRecurringInit()) ?
						null : platonSaleOptions.getRecurringInit(),

				auth
		);
		call.enqueue(enqueueWithCallback(platonSaleCallback, this));

		return call;
	}

	@Override
	public void onConfiguredResponse(final PlatonSaleCallback callback, final Call call, final PlatonBaseResponse response) {
		final PlatonBasePayment sale = response.getPlatonResponse();
		if (sale instanceof PlatonSaleRecurringInit)
			callback.onRecurringInitResponse(call, (PlatonSaleRecurringInit) sale);
		else if (sale instanceof PlatonSaleDecline)
			callback.onDeclineResponse(call, (PlatonSaleDecline) sale);
		else if (sale instanceof PlatonSale3DSecure)
			callback.on3dSecureResponse(call, (PlatonSale3DSecure) sale);
		else if (sale instanceof PlatonSaleSuccess)
			callback.onResponse(call, (PlatonSaleSuccess) sale);
		else callback.onAsyncResponse(call, (PlatonSale) sale);
	}
}
