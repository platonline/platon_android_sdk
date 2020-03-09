package com.platon.sdk.endpoint.service.post;

import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.Size;

import com.platon.sdk.constant.api.PlatonApiConstants.MethodProperties;
import com.platon.sdk.constant.api.action.PlatonAction;
import com.platon.sdk.constant.api.action.PlatonScheduleAction;
import com.platon.sdk.endpoint.adapter.post.PlatonScheduleAdapter;
import com.platon.sdk.model.response.base.PlatonBasePayment;
import com.platon.sdk.model.response.base.PlatonBaseResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static com.platon.sdk.constant.api.PlatonApiConstants.CalledMethod.BASE;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MAX_FORMAT_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MIN_FORMAT_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT_LONG;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Period.ASAP;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Period.MAX_DELAY;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Period.MAX_PERIOD;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Period.MAX_REPEAT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Period.MIN_PERIOD;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Period.UNLIMITED_REPEAT;

/**
 * Schedule payment API service to handle @{@link PlatonAction#SCHEDULE}
 * <p>
 * See @{@link PlatonScheduleAdapter}
 */
public interface PlatonScheduleService {

    @FormUrlEncoded
    @POST(BASE)
    Call<PlatonBaseResponse<PlatonBasePayment>> schedule(
            @Field(MethodProperties.ACTION) @NonNull @PlatonScheduleAction final String action,
            @Field(MethodProperties.CLIENT_KEY) @NonNull final String clientKey,
            @Field(MethodProperties.ORDER_AMOUNT)
            @Nullable @Size(min = MIN_FORMAT_AMOUNT, max = MAX_FORMAT_AMOUNT) final String orderAmount,
            @Field(MethodProperties.ORDER_DESCRIPTION) @NonNull @Size(max = TEXT_LONG) final String description,
            @Field(MethodProperties.RECURRING_FIRST_TRANS_ID) @NonNull final String firstTransId,
            @Field(MethodProperties.RECURRING_TOKEN) @NonNull final String recurringToken,
            @Field(MethodProperties.PERIOD)
            @NonNull @IntRange(from = MIN_PERIOD, to = MAX_PERIOD) final Integer periodDays,
            @Field(MethodProperties.INIT_PERIOD)
            @Nullable @IntRange(from = ASAP, to = MAX_DELAY) final Integer initDelayDays,
            @Field(MethodProperties.REPEAT_TIMES)
            @Nullable @IntRange(from = UNLIMITED_REPEAT, to = MAX_REPEAT) final Integer repeatTimes,
            @Field(MethodProperties.HASH) @NonNull final String hash
    );

    @FormUrlEncoded
    @POST(BASE)
    Call<PlatonBaseResponse<PlatonBasePayment>> deschedulePayment(
            @Field(MethodProperties.ACTION) @NonNull @PlatonScheduleAction final String action,
            @Field(MethodProperties.CLIENT_KEY) @NonNull final String clientKey,
            @Field(MethodProperties.RECURRING_FIRST_TRANS_ID) @NonNull final String firstTransId,
            @Field(MethodProperties.RECURRING_TOKEN) @NonNull final String recurringToken,
            @Field(MethodProperties.HASH) @NonNull final String hash
    );
}
