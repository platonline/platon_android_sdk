package com.platon.sdk.endpoint.adapter.post;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.FloatRange;
import androidx.annotation.Size;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.platon.sdk.callback.PlatonCaptureCallback;
import com.platon.sdk.constant.api.action.PlatonAction;
import com.platon.sdk.core.PlatonCredentials;
import com.platon.sdk.deserializer.PlatonCaptureDeserializer;
import com.platon.sdk.endpoint.adapter.PlatonBaseAdapter;
import com.platon.sdk.endpoint.service.post.PlatonCaptureService;
import com.platon.sdk.model.request.PlatonCard;
import com.platon.sdk.model.request.payer.PlatonPayerSale;
import com.platon.sdk.model.response.base.PlatonBasePayment;
import com.platon.sdk.model.response.base.PlatonBaseResponse;
import com.platon.sdk.model.response.capture.PlatonCaptureDecline;
import com.platon.sdk.model.response.capture.PlatonCaptureResponse;
import com.platon.sdk.model.response.capture.PlatonCaptureSuccess;
import com.platon.sdk.util.PlatonHashUtil;
import com.platon.sdk.util.PlatonSdkUtil;

import retrofit2.Call;

import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MAX_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MIN_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Card.MAX_CARD_NUMBER;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Card.MIN_CARD_NUMBER;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Payer.EMAIL;

/**
 * API adapter to facilitate transaction payment capture during DMS
 * <p>
 * See also {@link PlatonSaleAdapter} and {@link PlatonCaptureService}
 */
public class PlatonCaptureAdapter extends PlatonBaseAdapter<PlatonCaptureService> implements
		PlatonBaseAdapter.OnConfigureResponseListener<PlatonCaptureCallback, PlatonCaptureSuccess> {

	private static PlatonCaptureAdapter sInstance;

	private PlatonCaptureAdapter() {
	}

	public synchronized static PlatonCaptureAdapter getInstance() {
		if (sInstance == null) sInstance = new PlatonCaptureAdapter();
		return sInstance;
	}

	@Override
	protected Class<PlatonCaptureService> getServiceClass() {
		return PlatonCaptureService.class;
	}

	@Override
	protected void configureGson(@NonNull final GsonBuilder builder) {
		super.configureGson(builder);
		builder.registerTypeAdapter(
				new TypeToken<PlatonCaptureResponse>() {
				}.getType(), new PlatonCaptureDeserializer()
		);
	}

	/**
	 * For params description see {@link #capture(String, String, Float, PlatonCaptureCallback)}
	 * and {@link #capture(String, String, String, Float, PlatonCaptureCallback)}
	 */
	public Call capture(
			@NonNull @Size(max = TEXT) final String transactionId,
			@NonNull @Size(max = EMAIL) final String payerEmail,
			@NonNull @Size(min = MIN_CARD_NUMBER, max = MAX_CARD_NUMBER) final String cardNumber,
			@NonNull final PlatonCaptureCallback callback
	) {
		return capture(transactionId, payerEmail, cardNumber, null, callback);
	}

	/**
	 * For params description see {@link #capture(String, String, Float, PlatonCaptureCallback)}
	 * and {@link #capture(String, String, String, Float, PlatonCaptureCallback)}
	 */
	public Call capture(
			@NonNull @Size(max = TEXT) final String transactionId,
			@NonNull final String hash,
			@NonNull final PlatonCaptureCallback callback
	) {
		return capture(transactionId, hash, (Float) null, callback);
	}

	/**
	 * For params description see {@link #capture(String, String, Float, PlatonCaptureCallback)}
	 *
	 * @param payerEmail - customer’s email
	 *                   See {@link PlatonPayerSale} for the details
	 * @param cardNumber - payer card number
	 *                   See {@link PlatonCard} for the details
	 */
	@SuppressWarnings("ConstantConditions")
	public Call capture(
			@NonNull @Size(max = TEXT) final String transactionId,
			@NonNull @Size(max = EMAIL) final String payerEmail,
			@NonNull @Size(min = MIN_CARD_NUMBER, max = MAX_CARD_NUMBER) final String cardNumber,
			@Nullable @FloatRange(from = MIN_AMOUNT, to = MAX_AMOUNT) final Float partialAmount,
			@NonNull final PlatonCaptureCallback callback
	) {
		return capture(
				transactionId,
				PlatonHashUtil.encryptSale(payerEmail, transactionId, cardNumber),
				partialAmount,
				callback
		);
	}

	/**
	 * Dual Message System (DMS) - DMS is represented by AUTH and CAPTURE transactions
	 * {@link PlatonAction#CAPTURE} request is used to submit previously authorized transaction (created by AUTH request)
	 * Hold funds will be transferred to Merchants account
	 *
	 * @param transactionId - transaction ID in the Payment Platform (Required)
	 * @param partialAmount - the amount for partial capture. Only one partial capture allowed
	 * @param hash          - special signature to validate your request to Payment Platform (Required)
	 * @param callback      - callback that will return response (Required)
	 */
	@SuppressWarnings("unchecked")
	public Call capture(
			@NonNull @Size(max = TEXT) final String transactionId,
			@NonNull final String hash,
			@Nullable @FloatRange(from = MIN_AMOUNT, to = MAX_AMOUNT) final Float partialAmount,
			@NonNull final PlatonCaptureCallback callback
	) {
		final Call call = mService.capture(
				PlatonAction.CAPTURE,
				PlatonCredentials.getClientKey(),
				transactionId,
				PlatonSdkUtil.getAmountFormat(partialAmount),
				hash
		);
		call.enqueue(enqueueWithCallback(callback, this));

		return call;
	}

	@Override
	public void onConfiguredResponse(
			final PlatonCaptureCallback callback, final Call call, final PlatonBaseResponse response
	) {
		final PlatonBasePayment capture = response.getPlatonResponse();

		if (capture instanceof PlatonCaptureDecline) callback.onDeclineResponse(call, (PlatonCaptureDecline) capture);
		else callback.onResponse(call, (PlatonCaptureSuccess) capture);
	}
}
