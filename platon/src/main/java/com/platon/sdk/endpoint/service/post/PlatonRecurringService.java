package com.platon.sdk.endpoint.service.post;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Size;

import com.platon.sdk.constant.api.PlatonApiConstants.MethodProperties;
import com.platon.sdk.constant.api.PlatonOption;
import com.platon.sdk.constant.api.action.PlatonAction;
import com.platon.sdk.constant.api.action.PlatonRequestAction;
import com.platon.sdk.endpoint.adapter.post.PlatonRecurringAdapter;
import com.platon.sdk.model.response.sale.PlatonSaleResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static com.platon.sdk.constant.api.PlatonApiConstants.CalledMethod.BASE;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MAX_FORMAT_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MIN_FORMAT_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT_LONG;

/**
 * PlatonRecurring payment API service to handle {@link PlatonAction#RECURRING_SALE}
 * <p>
 * See {@link PlatonRecurringAdapter}
 */
public interface PlatonRecurringService {

    @FormUrlEncoded
    @POST(BASE)
    Call<PlatonSaleResponse> recurring(
            @Field(MethodProperties.ACTION) @NonNull @PlatonRequestAction final String action,
            @Field(MethodProperties.ASYNC) @Nullable @PlatonOption final String async,
            @Field(MethodProperties.CLIENT_KEY) @NonNull final String clientKey,
            @Field(MethodProperties.ORDER_ID) @NonNull @Size(max = TEXT) final String orderId,
            @Field(MethodProperties.ORDER_AMOUNT)
            @Nullable @Size(min = MIN_FORMAT_AMOUNT, max = MAX_FORMAT_AMOUNT) final String orderAmount,
            @Field(MethodProperties.ORDER_DESCRIPTION) @NonNull @Size(max = TEXT_LONG) final String description,
            @Field(MethodProperties.RECURRING_FIRST_TRANS_ID) @NonNull @Size(max = TEXT) final String firstTransId,
            @Field(MethodProperties.RECURRING_TOKEN) @NonNull final String recurringToken,
            @Field(MethodProperties.HASH) @NonNull final String hash,
            @Field(MethodProperties.AUTH) @Nullable @PlatonOption final String authenticateOnly
    );
}
