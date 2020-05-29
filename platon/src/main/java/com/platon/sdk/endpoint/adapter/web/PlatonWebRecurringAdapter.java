package com.platon.sdk.endpoint.adapter.web;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.GsonBuilder;
import com.platon.sdk.callback.PlatonWebCallback;
import com.platon.sdk.core.PlatonCredentials;
import com.platon.sdk.endpoint.adapter.PlatonBaseAdapter;
import com.platon.sdk.endpoint.adapter.post.PlatonRecurringAdapter;
import com.platon.sdk.endpoint.service.web.PlatonWebRecurringService;
import com.platon.sdk.model.request.option.web.PlatonWebOptions;
import com.platon.sdk.model.request.order.product.PlatonProductRecurring;
import com.platon.sdk.model.request.recurring.PlatonRecurringWeb;
import com.platon.sdk.util.PlatonHashUtil;
import com.platon.sdk.util.PlatonSdkUtil;

import retrofit2.Call;

import static com.platon.sdk.constant.api.PlatonApiConstants.Url.WEB_RECURRING_SALE;

/**
 * API adapter for creating RECURRING_SALE transaction in web payments platform
 * <p>
 * PlatonRecurring payments used to create new transactions based on
 * already stored cardholder information from previous operations
 * <p>
 * See {@link PlatonRecurringAdapter} for post RECURRING_SALE payments
 */
public class PlatonWebRecurringAdapter extends PlatonBaseAdapter<PlatonWebRecurringService> {

	private static PlatonWebRecurringAdapter sInstance;

	public synchronized static PlatonWebRecurringAdapter getInstance() {
		if (sInstance == null) sInstance = new PlatonWebRecurringAdapter();
		return sInstance;
	}

	@Override
	protected Class<PlatonWebRecurringService> getServiceClass() {
		return PlatonWebRecurringService.class;
	}

	@Override
	protected String getBaseUrl() {
		return WEB_RECURRING_SALE;
	}

	@Override
	protected void configureGson(@NonNull final GsonBuilder builder) {
		super.configureGson(builder);
		builder.setLenient();
	}

	/**
	 * For params description see {@link #recurringSale(
	 *PlatonProductRecurring, PlatonRecurringWeb, PlatonWebOptions, PlatonWebCallback)}
	 * <p>
	 * PlatonRecurring sale request without optional field
	 */
	public Call recurringSale(
			@NonNull final PlatonProductRecurring productRecurring,
			@NonNull final PlatonRecurringWeb recurring,
			@NonNull final PlatonWebCallback callback
	) {
		return recurringSale(productRecurring, recurring, null, callback);
	}

	/**
	 * If the Client's account supports recurring operations,
	 * the Client will be granted with special RECURRING_URL to which the POST request
	 * with special parameters must be sent to make the direct recurring payments
	 *
	 * @param productRecurring - product info holder
	 *                         See {@link PlatonProductRecurring} for params
	 * @param recurring        - holder for rc_id and rc_token params
	 *                         See {@link PlatonRecurringWeb}
	 * @param platonWebOptions - options for web form representation
	 *                         See {@link PlatonWebOptions} for params
	 * @param callback         - callback which will hold url for web request
	 * @return original call of {@link PlatonWebRecurringService recurringSale(...)} request
	 */
	@SuppressWarnings("unchecked")
	public Call recurringSale(
			@NonNull final PlatonProductRecurring productRecurring,
			@NonNull final PlatonRecurringWeb recurring,
			@Nullable final PlatonWebOptions platonWebOptions,
			@NonNull final PlatonWebCallback callback
	) {
		final boolean isWebOptionsNull = platonWebOptions == null;

		final Call call = mService.recurringSale(
				PlatonCredentials.getClientKey(),

				TextUtils.isEmpty(productRecurring.getId()) ? null : productRecurring.getId(),
				PlatonSdkUtil.getAmountFormat(productRecurring.getAmount()),
				productRecurring.getDescription(),

				recurring.getFirstTransId(),
				recurring.getToken(),

				isWebOptionsNull || TextUtils.isEmpty(platonWebOptions.getExt1()) ? null : platonWebOptions.getExt1(),
				isWebOptionsNull || TextUtils.isEmpty(platonWebOptions.getExt2()) ? null : platonWebOptions.getExt2(),
				isWebOptionsNull || TextUtils.isEmpty(platonWebOptions.getExt3()) ? null : platonWebOptions.getExt3(),
				isWebOptionsNull || TextUtils.isEmpty(platonWebOptions.getExt4()) ? null : platonWebOptions.getExt4(),
				isWebOptionsNull || TextUtils.isEmpty(platonWebOptions.getExt10()) ? null : platonWebOptions.getExt10(),

				PlatonHashUtil.encryptRecurringWeb(productRecurring, recurring)
		);
		call.enqueue(callback);

		return call;
	}

}
