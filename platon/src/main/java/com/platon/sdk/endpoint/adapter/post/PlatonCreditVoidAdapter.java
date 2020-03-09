package com.platon.sdk.endpoint.adapter.post;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.Size;
import androidx.annotation.FloatRange;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.platon.sdk.callback.PlatonCreditVoidCallback;
import com.platon.sdk.constant.api.PlatonStatus;
import com.platon.sdk.constant.api.action.PlatonAction;
import com.platon.sdk.core.PlatonCredentials;
import com.platon.sdk.deserializer.PlatonBaseDeserializer;
import com.platon.sdk.endpoint.adapter.PlatonBaseAdapter;
import com.platon.sdk.endpoint.service.post.PlatonCreditVoidService;
import com.platon.sdk.model.request.PlatonCard;
import com.platon.sdk.model.request.payer.PlatonPayerSale;
import com.platon.sdk.model.response.base.PlatonBaseResponse;
import com.platon.sdk.model.response.credit_void.PlatonCreditVoid;
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
 * API adapter to facilitate both fund {@link PlatonStatus#REFUND} and fund {@link PlatonStatus#REVERSAL} operations
 * <p>
 * See also {@link PlatonCreditVoidService}
 */
public class PlatonCreditVoidAdapter extends PlatonBaseAdapter<PlatonCreditVoidService> {

	private static PlatonCreditVoidAdapter sInstance;

	private PlatonCreditVoidAdapter() {
	}

	public synchronized static PlatonCreditVoidAdapter getInstance() {
		if (sInstance == null) sInstance = new PlatonCreditVoidAdapter();
		return sInstance;
	}

	@Override
	protected Class<PlatonCreditVoidService> getServiceClass() {
		return PlatonCreditVoidService.class;
	}

	@Override
	protected void configureGson(@NonNull final GsonBuilder builder) {
		super.configureGson(builder);
		builder.registerTypeAdapter(
				new TypeToken<PlatonBaseResponse<PlatonCreditVoid>>() {
				}.getType(), new PlatonBaseDeserializer<>(PlatonCreditVoid.class)
		);
	}

	/**
	 * For params description see {@link #creditVoid(String, Float, String, PlatonCreditVoidCallback)}
	 * and {@link #creditVoid(String, Float, String, String, PlatonCreditVoidCallback)}
	 */
	public Call creditVoid(
			@NonNull @Size(max = TEXT) final String transactionId,
			@NonNull @Size(max = EMAIL) final String payerEmail,
			@NonNull @Size(min = MIN_CARD_NUMBER, max = MAX_CARD_NUMBER) final String cardNumber,
			@NonNull final PlatonCreditVoidCallback callback
	) {
		return creditVoid(transactionId, null, payerEmail, cardNumber, callback);
	}

	/**
	 * For params description see {@link #creditVoid(String, Float, String, PlatonCreditVoidCallback)}
	 * and {@link #creditVoid(String, Float, String, String, PlatonCreditVoidCallback)}
	 */
	public Call creditVoid(
			@NonNull @Size(max = TEXT) final String transactionId,
			@NonNull final String hash,
			@NonNull final PlatonCreditVoidCallback callback
	) {
		return creditVoid(transactionId, (Float) null, hash, callback);
	}

	/**
	 * For params description see {@link #creditVoid(String, Float, String, PlatonCreditVoidCallback)}
	 *
	 * @param payerEmail - customer’s email
	 *                   See {@link PlatonPayerSale} for the details
	 * @param cardNumber - payer card number
	 *                   See {@link PlatonCard} for the details
	 */
	@SuppressWarnings("ConstantConditions")
	public Call creditVoid(
			@NonNull @Size(max = TEXT) final String transactionId,
			@Nullable @FloatRange(from = MIN_AMOUNT, to = MAX_AMOUNT) final Float partialAmount,
			@NonNull @Size(max = EMAIL) final String payerEmail,
			@NonNull @Size(min = MIN_CARD_NUMBER, max = MAX_CARD_NUMBER) final String cardNumber,
			@NonNull final PlatonCreditVoidCallback callback
	) {
		return creditVoid(
				transactionId,
				partialAmount,
				PlatonHashUtil.encryptSale(payerEmail, transactionId, cardNumber),
				callback
		);
	}

	/**
	 * {@link PlatonAction#CREDITVOID} request is used to complete both
	 * {@link PlatonStatus#REFUND} and {@link PlatonStatus#REVERSAL} transactions
	 * <p>
	 * {@link PlatonStatus#REVERSAL} transaction is used to cancel hold from funds on card account,
	 * previously authorized by AUTH transaction
	 * <p>
	 * {@link PlatonStatus#REFUND} transaction is used to return funds to card account,
	 * previously submitted by {@link PlatonAction#SALE} or {@link PlatonAction#CAPTURE} transactions
	 *
	 * @param transactionId - transaction ID in the Payment Platform (Required)
	 * @param partialAmount - the amount for partial refund. Several partial refunds allowed
	 * @param hash          - special signature to validate your request to Payment Platform (Required)
	 * @param callback      - callback that will return response (Required)
	 */

	@SuppressWarnings("unchecked")
	public Call creditVoid(
			@NonNull @Size(max = TEXT) final String transactionId,
			@Nullable @FloatRange(from = MIN_AMOUNT, to = MAX_AMOUNT) final Float partialAmount,
			@NonNull final String hash,
			@NonNull final PlatonCreditVoidCallback callback
	) {
		final Call call = mService.creditVoid(
				PlatonAction.CREDITVOID,
				PlatonCredentials.getClientKey(),
				transactionId,
				PlatonSdkUtil.getAmountFormat(partialAmount),
				hash
		);
		call.enqueue(enqueueWithCallback(callback));

		return call;
	}
}
