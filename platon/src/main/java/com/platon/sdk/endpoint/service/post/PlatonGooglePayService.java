package com.platon.sdk.endpoint.service.post;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Size;

import com.platon.sdk.constant.api.PlatonApiConstants.MethodProperties;
import com.platon.sdk.constant.api.PlatonOption;
import com.platon.sdk.constant.api.action.PlatonRequestAction;
import com.platon.sdk.model.response.google_pay.PlatonGooglePayResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static com.platon.sdk.constant.api.PlatonApiConstants.CalledMethod.BASE;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MAX_FORMAT_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MIN_FORMAT_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.CHANNEL;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.CURRENCY_CODE;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT_DATE;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT_LONG;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT_SHORT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Payer.COUNTRY_CODE;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Payer.EMAIL;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Payer.MAX_IP;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Payer.MIN_IP;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.State.STATE;

/**
 * Google Pay payment API service service
 */
public interface PlatonGooglePayService {

    @FormUrlEncoded
    @POST(BASE)
    Call<PlatonGooglePayResponse> googlePay(
            @Field(MethodProperties.ACTION) @NonNull @PlatonRequestAction final String action,
            @Field(MethodProperties.ASYNC) @Nullable @PlatonOption final String async,
            @Field(MethodProperties.CLIENT_KEY) @NonNull final String clientKey,
            @Field(MethodProperties.CHANNEL_ID) @Nullable @Size(max = CHANNEL) final String channelId,
            @Field(MethodProperties.ORDER_ID) @NonNull @Size(max = TEXT) final String orderId,
            @Field(MethodProperties.ORDER_AMOUNT)
            @Nullable @Size(min = MIN_FORMAT_AMOUNT, max = MAX_FORMAT_AMOUNT) final String orderAmount,
            @Field(MethodProperties.ORDER_CURRENCY) @NonNull @Size(CURRENCY_CODE) final String currencyIso,
            @Field(MethodProperties.ORDER_DESCRIPTION) @NonNull @Size(max = TEXT_LONG) final String description,
            @Field(MethodProperties.PAYMENT_TOKEN) @NonNull final String paymentToken,
            @Field(MethodProperties.PAYER_FIRST_NAME) @Nullable @Size(max = TEXT_SHORT) final String name,
            @Field(MethodProperties.PAYER_LAST_NAME) @Nullable @Size(max = TEXT_SHORT) final String surname,
            @Field(MethodProperties.PAYER_MIDDLE_NAME) @Nullable @Size(max = TEXT_SHORT) final String middleName,
            @Field(MethodProperties.PAYER_BIRTHDAY) @Nullable @Size(max = TEXT_DATE) final String birthday,
            @Field(MethodProperties.PAYER_ADDRESS) @Nullable @Size(max = TEXT) final String address,
            @Field(MethodProperties.PAYER_COUNTRY) @Size(COUNTRY_CODE) final String countryCode,
            @Field(MethodProperties.PAYER_STATE) @Nullable @Size(STATE) final String state,
            @Field(MethodProperties.PAYER_CITY) @Nullable @Size(max = TEXT_SHORT) final String city,
            @Field(MethodProperties.PAYER_ZIP) @Nullable @Size(max = TEXT_SHORT) final String zip,
            @Field(MethodProperties.PAYER_EMAIL) @Nullable @Size(max = EMAIL) final String email,
            @Field(MethodProperties.PAYER_PHONE) @Nullable @Size(max = TEXT_SHORT) final String phone,
            @Field(MethodProperties.PAYER_IP) @Nullable @Size(min = MIN_IP, max = MAX_IP) final String ipAddress,
            @Field(MethodProperties.TERM_URL_3DS) @Nullable @Size(max = TEXT_LONG) final String url3ds,
            @Field(MethodProperties.AUTH) @Nullable @PlatonOption final String authenticateOnly,
            @Field(MethodProperties.HASH) @NonNull final String hash
    );
}
