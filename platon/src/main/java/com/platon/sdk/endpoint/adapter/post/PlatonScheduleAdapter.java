package com.platon.sdk.endpoint.adapter.post;

import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.Size;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.platon.sdk.callback.PlatonScheduleCallback;
import com.platon.sdk.constant.api.action.PlatonAction;
import com.platon.sdk.core.PlatonCredentials;
import com.platon.sdk.deserializer.PlatonBaseDeserializer;
import com.platon.sdk.endpoint.adapter.PlatonBaseAdapter;
import com.platon.sdk.endpoint.service.post.PlatonRecurringService;
import com.platon.sdk.endpoint.service.post.PlatonScheduleService;
import com.platon.sdk.model.request.option.schedule.PlatonScheduleOptions;
import com.platon.sdk.model.request.order.PlatonOrder;
import com.platon.sdk.model.request.recurring.PlatonRecurring;
import com.platon.sdk.model.response.base.PlatonBasePayment;
import com.platon.sdk.model.response.base.PlatonBaseResponse;
import com.platon.sdk.util.PlatonHashUtil;
import com.platon.sdk.util.PlatonSdkUtil;

import retrofit2.Call;

import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Card.MAX_CARD_NUMBER;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Card.MIN_CARD_NUMBER;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Payer.EMAIL;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Period.MAX_PERIOD;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Period.MIN_PERIOD;

/**
 * API adapter to facilitate Scheduling/Deschedule payments (recurring)
 * <p>
 * Payer can make next variant of payment:
 * - regular with period between payments
 * - restrict payment to specified amount (transaction)
 * - can set delay after which first and further payments with specified period starts
 * <p>
 * See also {@link PlatonRecurringService} and {@link PlatonSaleAdapter}
 */
public class PlatonScheduleAdapter extends PlatonBaseAdapter<PlatonScheduleService> {

	private static PlatonScheduleAdapter sInstance;

	private PlatonScheduleAdapter() {
	}

	public synchronized static PlatonScheduleAdapter getInstance() {
		if (sInstance == null) sInstance = new PlatonScheduleAdapter();
		return sInstance;
	}

	@Override
	protected Class<PlatonScheduleService> getServiceClass() {
		return PlatonScheduleService.class;
	}

	@Override
	protected void configureGson(@NonNull final GsonBuilder builder) {
		super.configureGson(builder);
		builder.registerTypeAdapter(
				new TypeToken<PlatonBaseResponse<PlatonBasePayment>>() {
				}.getType(),
				new PlatonBaseDeserializer<>(PlatonBasePayment.class)
		);
	}

	/**
	 * For params description see {@link #schedule(
	 *PlatonOrder, PlatonRecurring, Integer, String, PlatonScheduleOptions, PlatonScheduleCallback)}
	 */
	public Call schedule(
			@NonNull final PlatonOrder platonOrder,
			@NonNull final PlatonRecurring platonRecurring,
			@NonNull @IntRange(from = MIN_PERIOD, to = MAX_PERIOD) final Integer periodDays,
			@NonNull @Size(max = EMAIL) final String payerEmail,
			@NonNull @Size(min = MIN_CARD_NUMBER, max = MAX_CARD_NUMBER) final String cardNumber,
			@NonNull final PlatonScheduleCallback callback
	) {
		return schedule(platonOrder, platonRecurring, periodDays, payerEmail, cardNumber, null, callback);
	}

	/**
	 * For params description see {@link #schedule(
	 *PlatonOrder, PlatonRecurring, Integer, String, PlatonScheduleOptions, PlatonScheduleCallback)}
	 */
	@SuppressWarnings("ConstantConditions")
	public Call schedule(
			@NonNull final PlatonOrder platonOrder,
			@NonNull final PlatonRecurring platonRecurring,
			@NonNull @IntRange(from = MIN_PERIOD, to = MAX_PERIOD) final Integer periodDays,
			@NonNull @Size(max = EMAIL) final String payerEmail,
			@NonNull @Size(min = MIN_CARD_NUMBER, max = MAX_CARD_NUMBER) final String cardNumber,
			@Nullable final PlatonScheduleOptions platonScheduleOptions,
			@NonNull final PlatonScheduleCallback callback
	) {
		return schedule(
				platonOrder,
				platonRecurring,
				periodDays,
				PlatonHashUtil.encryptSale(payerEmail, platonRecurring.getFirstTransId(), cardNumber),
				platonScheduleOptions,
				callback
		);
	}

	/**
	 * For params description see {@link #schedule(
	 *PlatonOrder, PlatonRecurring, Integer, String, PlatonScheduleOptions, PlatonScheduleCallback)}
	 */
	@SuppressWarnings("unchecked")
	public Call<PlatonBaseResponse<PlatonBasePayment>> schedule(
			@NonNull final PlatonOrder platonOrder,
			@NonNull final PlatonRecurring platonRecurring,
			@NonNull @IntRange(from = MIN_PERIOD, to = MAX_PERIOD) final Integer periodDays,
			@NonNull final String hash,
			@NonNull final PlatonScheduleCallback callback
	) {
		return schedule(platonOrder, platonRecurring, periodDays, hash, (PlatonScheduleOptions) null, callback);
	}

	/**
	 * Registers a schedule for the regular SALE secondary transactions
	 * Transactions are created by Payment Platform, based on data taken from primary transaction
	 *
	 * @param platonOrder           - platonOrder info holder for id, amount and description (Required)
	 *                        See {@link PlatonOrder} for variables description
	 * @param platonRecurring       - platonRecurring info holder for first transaction ID and token (Required)
	 *                        See {@link PlatonRecurring} for variables description
	 * @param periodDays      - period in days to perform the payments (Required)
	 *                        Format: Numbers in the form XXX
	 * @param hash            - special signature to validate your request to Payment Platform (Required)
	 * @param platonScheduleOptions - schedule info holder for init delay days and repeat times
	 *                        See {@link PlatonScheduleOptions} for variables description
	 * @param callback        - callback that will return response (Required)
	 */
	@SuppressWarnings("unchecked")
	public Call schedule(
			@NonNull final PlatonOrder platonOrder,
			@NonNull final PlatonRecurring platonRecurring,
			@NonNull @IntRange(from = MIN_PERIOD, to = MAX_PERIOD) final Integer periodDays,
			@NonNull final String hash,
			@Nullable final PlatonScheduleOptions platonScheduleOptions,
			@NonNull final PlatonScheduleCallback callback
	) {
		final boolean isScheduleOptionsNull = platonScheduleOptions == null;
		final Call call = mService.schedule(
				PlatonAction.SCHEDULE,

				PlatonCredentials.getClientKey(),
				PlatonSdkUtil.getAmountFormat(platonOrder.getAmount()),

				platonOrder.getDescription(),
				platonRecurring.getFirstTransId(),
				platonRecurring.getToken(),
				periodDays,

				isScheduleOptionsNull || platonScheduleOptions.getInitDelayDays() == null ?
						null : platonScheduleOptions.getInitDelayDays(),
				isScheduleOptionsNull || platonScheduleOptions.getRepeatTimes() == null ?
						null : platonScheduleOptions.getRepeatTimes(),

				hash
		);
		call.enqueue(enqueueWithCallback(callback));

		return call;
	}

	/**
	 * For params description see {@link #deschedule(PlatonRecurring, String, PlatonScheduleCallback)}
	 */
	@SuppressWarnings("ConstantConditions")
	public Call deschedule(
			@NonNull final PlatonRecurring platonRecurring,
			@NonNull @Size(max = EMAIL) final String payerEmail,
			@NonNull @Size(min = MIN_CARD_NUMBER, max = MAX_CARD_NUMBER) final String cardNumber,
			@NonNull final PlatonScheduleCallback callback
	) {
		return deschedule(
				platonRecurring, PlatonHashUtil.encryptSale(payerEmail, platonRecurring.getFirstTransId(), cardNumber), callback
		);
	}

	/**
	 * For params description see {@link #schedule(
	 *PlatonOrder, PlatonRecurring, Integer, String, PlatonScheduleOptions, PlatonScheduleCallback)}}
	 * <p>
	 * Deschedule payment for transaction
	 * Transactions are created by Payment Platform, based on data taken from primary transaction
	 */
	@SuppressWarnings("unchecked")
	public Call deschedule(
			@NonNull final PlatonRecurring platonRecurring,
			@NonNull final String hash,
			@NonNull final PlatonScheduleCallback callback
	) {
		final Call call = mService.deschedulePayment(
				PlatonAction.DESCHEDULE,
				PlatonCredentials.getClientKey(),
				platonRecurring.getFirstTransId(),
				platonRecurring.getToken(),
				hash
		);
		call.enqueue(enqueueWithCallback(callback));

		return call;
	}
}
