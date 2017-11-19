package com.platon.sdk.endpoint.adapter.web;

import android.support.annotation.NonNull;

import com.google.gson.GsonBuilder;
import com.platon.sdk.callback.PlatonWebCallback;
import com.platon.sdk.core.PlatonCredentials;
import com.platon.sdk.endpoint.adapter.PlatonBaseAdapter;
import com.platon.sdk.endpoint.adapter.post.PlatonScheduleAdapter;
import com.platon.sdk.endpoint.service.web.PlatonWebScheduleService;
import com.platon.sdk.model.request.option.schedule.PlatonScheduleWebOptions;
import com.platon.sdk.model.request.order.product.PlatonProduct;
import com.platon.sdk.model.request.recurring.PlatonRecurringWeb;
import com.platon.sdk.util.PlatonHashUtil;
import com.platon.sdk.util.PlatonSdkUtil;

import retrofit2.Call;

/**
 * API adapter for creating SCHEDULE and DESCHEDULE options subscriptions in web payments platform
 * <p>
 * If the Client's account supports recurring operations, the Client will be granted with special
 * SCHEDULE_URL and DESCHEDULE_URL to which the POST request with special
 * parameters must be sent to manage the schedule-based recurring payments
 * <p>
 * See {@link PlatonScheduleAdapter} for post methods
 */
public class PlatonWebScheduleAdapter extends PlatonBaseAdapter<PlatonWebScheduleService> {

	private static PlatonWebScheduleAdapter sInstance;

	public synchronized static PlatonWebScheduleAdapter getInstance() {
		if (sInstance == null) sInstance = new PlatonWebScheduleAdapter();
		return sInstance;
	}

	@Override
	protected Class<PlatonWebScheduleService> getServiceClass() {
		return PlatonWebScheduleService.class;
	}

	@Override
	protected void configureGson(@NonNull final GsonBuilder builder) {
		super.configureGson(builder);
		builder.setLenient();
	}

	/**
	 * Schedule-based recurring payments allow you to make payments with stored cardholder data on regular basis
	 *
	 * @param platonProduct         - info holder of platonProduct
	 *                        See {@link PlatonProduct}
	 * @param recurring       - info holder of rc_id and rc_token
	 *                        See {@link PlatonRecurringWeb}
	 * @param scheduleOptions - schedule options which controls delay, period and repeat times
	 *                        See {@link PlatonScheduleWebOptions} for params
	 * @param callback        - callback which will hold url for web request
	 * @return original call of {@link PlatonWebScheduleService schedule(...)} request
	 */
	@SuppressWarnings("unchecked")
	public Call schedule(
			@NonNull final PlatonProduct platonProduct,
			@NonNull final PlatonRecurringWeb recurring,
			@NonNull final PlatonScheduleWebOptions scheduleOptions,
			@NonNull final PlatonWebCallback callback
	) {
		final Call call = mService.schedule(
				PlatonCredentials.getClientKey(),

				PlatonSdkUtil.getAmountFormat(platonProduct.getAmount()),
				platonProduct.getDescription(),

				recurring.getFirstTransId(),
				recurring.getToken(),

				scheduleOptions.getInitialDelay(),
				scheduleOptions.getPeriod(),
				scheduleOptions.getRepeatTimes(),

				PlatonHashUtil.encryptScheduleWeb(platonProduct, recurring, scheduleOptions)
		);
		call.enqueue(callback);

		return call;
	}

	/**
	 * To remove existing recurring schedule,
	 * the POST request with the parameters listed below should be sent to the DESCHEDULE_URL
	 *
	 * @param recurring - info holder of rc_id and rc_token
	 *                  See {@link PlatonRecurringWeb}
	 * @param callback  - callback which will hold url for web request
	 * @return original call of {@link PlatonWebScheduleService deschedule(...)} request
	 */
	@SuppressWarnings("unchecked")
	public Call deschedule(
			@NonNull final PlatonRecurringWeb recurring,
			@NonNull final PlatonWebCallback callback
	) {
		final Call call = mService.deschedule(
				PlatonCredentials.getClientKey(),

				recurring.getFirstTransId(),
				recurring.getToken(),

				PlatonHashUtil.encryptDescheduleWeb(recurring)
		);
		call.enqueue(callback);

		return call;
	}

}
