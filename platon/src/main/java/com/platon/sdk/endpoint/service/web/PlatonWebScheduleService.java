package com.platon.sdk.endpoint.service.web;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Size;

import com.platon.sdk.constant.api.PlatonApiConstants.MethodProperties;
import com.platon.sdk.endpoint.adapter.web.PlatonWebScheduleAdapter;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static com.platon.sdk.constant.api.PlatonApiConstants.CalledMethod.BASE;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MAX_FORMAT_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MIN_FORMAT_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT_MIN;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT_SHORT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Period.ASAP;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Period.MAX_DELAY;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Period.MAX_PERIOD;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Period.MAX_REPEAT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Period.MIN_PERIOD;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Period.UNLIMITED_REPEAT;

/**
 * API service for creating SCHEDULE and DESCHEDULE options subscriptions in web payments platform
 * <p>
 * See {@link PlatonWebScheduleAdapter} for it realisation
 */
public interface PlatonWebScheduleService {

	@FormUrlEncoded
	@POST(BASE)
	Call<String> schedule(
			@Field(MethodProperties.KEY) @NonNull final String key,
			@Field(MethodProperties.AMOUNT)
			@NonNull @Size(min = MIN_FORMAT_AMOUNT, max = MAX_FORMAT_AMOUNT) final String amount,
			@Field(MethodProperties.DESCRIPTION) @NonNull @Size(max = TEXT_MIN) final String description,
			@Field(MethodProperties.RC_ID) @NonNull final String rcId,
			@Field(MethodProperties.RC_TOKEN) @NonNull @Size(max = TEXT_SHORT) final String rcToken,

			@Field(MethodProperties.INITIAL_DELAY) @IntRange(from = ASAP, to = MAX_DELAY) final int initialDelay,
			@Field(MethodProperties.PERIOD) @IntRange(from = MIN_PERIOD, to = MAX_PERIOD) final int period,
			@Field(MethodProperties.REPEAT_TIMES)
			@IntRange(from = UNLIMITED_REPEAT, to = MAX_REPEAT) final int repeatTimes,

			@Field(MethodProperties.SIGN) @NonNull final String sign

	);

	@FormUrlEncoded
	@POST(BASE)
	Call<String> deschedule(
			@Field(MethodProperties.KEY) @NonNull final String key,
			@Field(MethodProperties.RC_ID) @NonNull final String rcId,
			@Field(MethodProperties.RC_TOKEN) @NonNull @Size(max = TEXT_SHORT) final String rcToken,
			@Field(MethodProperties.SIGN) @NonNull final String sign
	);

}
