package com.platon.sdk.endpoint.adapter.post;

import androidx.annotation.NonNull;
import androidx.annotation.Size;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.platon.sdk.callback.PlatonTransactionDetailsCallback;
import com.platon.sdk.callback.PlatonTransactionStatusCallback;
import com.platon.sdk.constant.api.PlatonStatus;
import com.platon.sdk.constant.api.action.PlatonAction;
import com.platon.sdk.core.PlatonCredentials;
import com.platon.sdk.deserializer.PlatonBaseDeserializer;
import com.platon.sdk.endpoint.adapter.PlatonBaseAdapter;
import com.platon.sdk.endpoint.service.post.PlatonTransactionService;
import com.platon.sdk.model.request.PlatonCard;
import com.platon.sdk.model.request.payer.PlatonPayerSale;
import com.platon.sdk.model.response.base.PlatonBaseResponse;
import com.platon.sdk.model.response.transaction.PlatonTransactionDetails;
import com.platon.sdk.model.response.transaction.PlatonTransactionStatus;
import com.platon.sdk.util.PlatonHashUtil;

import retrofit2.Call;

import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Card.MAX_CARD_NUMBER;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Card.MIN_CARD_NUMBER;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Payer.EMAIL;

/**
 * API adapter to facilitate retrieving transaction data
 * <p>
 * See also {@link PlatonTransactionService}
 */
public class PlatonTransactionAdapter extends PlatonBaseAdapter<PlatonTransactionService> {

	private static PlatonTransactionAdapter sInstance;

	private PlatonTransactionAdapter() {
	}

	public synchronized static PlatonTransactionAdapter getInstance() {
		if (sInstance == null) sInstance = new PlatonTransactionAdapter();
		return sInstance;
	}

	@Override
	protected Class<PlatonTransactionService> getServiceClass() {
		return PlatonTransactionService.class;
	}

	@Override
	protected void configureGson(@NonNull final GsonBuilder builder) {
		super.configureGson(builder);
		builder.registerTypeAdapter(
				new TypeToken<PlatonBaseResponse<PlatonTransactionDetails>>() {
				}.getType(),
				new PlatonBaseDeserializer<>(PlatonTransactionDetails.class)
		);
		builder.registerTypeAdapter(
				new TypeToken<PlatonBaseResponse<PlatonTransactionStatus>>() {
				}.getType(),
				new PlatonBaseDeserializer<>(PlatonTransactionStatus.class)
		);
	}

	/**
	 * For params description see {@link #getTransactionStatus(String, String, PlatonTransactionStatusCallback)}
	 *  @param payerEmail - Customer’s email
	 *                   See {@link PlatonPayerSale} for the details
	 * @param cardNumber - Payer card number
	 *                   See {@link PlatonCard} for the details
	 */
	@SuppressWarnings({"ConstantConditions"})
	public void getTransactionStatus(
			@NonNull @Size(max = TEXT) final String transactionId,
			@NonNull @Size(max = EMAIL) final String payerEmail,
			@NonNull @Size(min = MIN_CARD_NUMBER, max = MAX_CARD_NUMBER) final String cardNumber,
			@NonNull final PlatonTransactionStatusCallback callback
	) {
		getTransactionStatus(transactionId, PlatonHashUtil.encryptNoSale(payerEmail, transactionId, cardNumber), callback);
	}

	/**
	 * Gets order status ({@link PlatonStatus}) from Payment Platform
	 *  @param transactionId - PlatonTransaction ID in the Payment Platform (Required)
	 * @param hash          - Special signature to validate your request to Payment Platform (Required)
	 * @param callback      - callback that will return response (Required)
	 */
	@SuppressWarnings("unchecked")
	public void getTransactionStatus(
			@NonNull @Size(max = TEXT) final String transactionId,
			@NonNull final String hash,
			@NonNull final PlatonTransactionStatusCallback callback
	) {
		final Call call = mService.getStatus(
				PlatonAction.GET_TRANS_STATUS,
				PlatonCredentials.getClientKey(),
				transactionId,
				hash
		);
		call.enqueue(enqueueWithCallback(callback));

	}

	/**
	 * For params description see {@link #getTransactionStatus(String, String, String, PlatonTransactionStatusCallback)}
	 */
	@SuppressWarnings({"ConstantConditions"})
	public void getTransactionDetails(
			@NonNull @Size(max = TEXT) final String transactionId,
			@NonNull @Size(max = EMAIL) final String payerEmail,
			@NonNull @Size(min = MIN_CARD_NUMBER, max = MAX_CARD_NUMBER) final String cardNumber,
			@NonNull final PlatonTransactionDetailsCallback callback
	) {
		getTransactionDetails(
				transactionId, PlatonHashUtil.encryptNoSale(payerEmail, transactionId, cardNumber), callback
		);
	}

	/**
	 * Gets all history of transactions by the order.
	 *  @param transactionId - PlatonTransaction ID in the Payment Platform (Required)
	 * @param hash          - Special signature to validate your request to Payment Platform (Required)
	 * @param callback      - callback that will return response (Required)
	 */
	@SuppressWarnings("unchecked")
	public void getTransactionDetails(
			@NonNull @Size(max = TEXT) final String transactionId,
			@NonNull final String hash,
			@NonNull final PlatonTransactionDetailsCallback callback
	) {
		final Call call = mService.getDetails(
				PlatonAction.GET_TRANS_DETAILS,
				PlatonCredentials.getClientKey(),
				transactionId,
				hash
		);
		call.enqueue(enqueueWithCallback(callback));

	}

}
