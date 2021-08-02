package com.platon.sdk.endpoint.service.post;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.Size;

import com.platon.sdk.constant.api.PlatonOption;
import com.platon.sdk.constant.api.PlatonApiConstants.MethodProperties;
import com.platon.sdk.constant.api.action.PlatonAction;
import com.platon.sdk.constant.api.action.PlatonRequestAction;
import com.platon.sdk.endpoint.adapter.post.PlatonSaleAdapter;
import com.platon.sdk.model.response.sale.PlatonSaleResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static com.platon.sdk.constant.api.PlatonApiConstants.CalledMethod.BASE;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MAX_FORMAT_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MIN_FORMAT_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Card.MAX_CARD_NUMBER;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Card.MAX_CVV;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Card.MIN_CARD_NUMBER;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Card.MIN_CVV;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Card.MONTH_FORMAT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Card.YEAR_FORMAT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.CHANNEL;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.CURRENCY_CODE;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT_LONG;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT_SHORT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Payer.COUNTRY_CODE;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Payer.EMAIL;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Payer.MAX_IP;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Payer.MIN_IP;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.State.STATE;

/**
 * Sale payment API service to handle {@link MethodProperties#AUTH} and immediate {@link PlatonAction#SALE}
 * <p>
 * See @{@link PlatonSaleAdapter}
 */
public interface PlatonSaleService {

    @FormUrlEncoded
    @POST(BASE)
    Call<PlatonSaleResponse> sale(
            @Field(MethodProperties.ACTION) @NonNull @PlatonRequestAction final String action,
            @Field(MethodProperties.CLIENT_KEY) @NonNull final String clientKey,
            @Field(MethodProperties.ORDER_ID) @NonNull @Size(max = TEXT) final String orderId,
            @Field(MethodProperties.ORDER_AMOUNT)
            @NonNull @Size(min = MIN_FORMAT_AMOUNT, max = MAX_FORMAT_AMOUNT) final String orderAmount,
            @Field(MethodProperties.ORDER_CURRENCY) @NonNull @Size(CURRENCY_CODE) final String currencyIso,
            @Field(MethodProperties.ORDER_DESCRIPTION) @NonNull @Size(max = TEXT_LONG) final String description,
            @Field(MethodProperties.CARD_NUMBER)
            @NonNull @Size(min = MIN_CARD_NUMBER, max = MAX_CARD_NUMBER) final String cardNumber,
            @Field(MethodProperties.CARD_EXP_MONTH) @NonNull @Size(MONTH_FORMAT) final String monthExpire,
            @Field(MethodProperties.CARD_EXP_YEAR) @NonNull @Size(YEAR_FORMAT) final String yearExpire,
            @Field(MethodProperties.CARD_CVV2) @NonNull @Size(min = MIN_CVV, max = MAX_CVV) final String cvvCode,
            @Field(MethodProperties.PAYER_FIRST_NAME) @NonNull @Size(max = TEXT_SHORT) final String name,
            @Field(MethodProperties.PAYER_LAST_NAME) @NonNull @Size(max = TEXT_SHORT) final String surname,
            @Field(MethodProperties.PAYER_ADDRESS) @NonNull @Size(max = TEXT) final String address,
            @Field(MethodProperties.PAYER_COUNTRY) @Size(COUNTRY_CODE) final String countryCode,
            @Field(MethodProperties.PAYER_STATE) @NonNull @Size(STATE) final String state,
            @Field(MethodProperties.PAYER_CITY) @NonNull @Size(max = TEXT_SHORT) final String city,
            @Field(MethodProperties.PAYER_ZIP) @NonNull @Size(max = TEXT_SHORT) final String zip,
            @Field(MethodProperties.PAYER_EMAIL) @NonNull @Size(max = EMAIL) final String email,
            @Field(MethodProperties.PAYER_PHONE) @NonNull @Size(max = TEXT_SHORT) final String phone,
            @Field(MethodProperties.PAYER_IP) @NonNull @Size(min = MIN_IP, max = MAX_IP) final String ipAddress,
            @Field(MethodProperties.HASH) @NonNull final String hash,
            @Field(MethodProperties.ASYNC) @Nullable @PlatonOption final String async,
            @Field(MethodProperties.CHANNEL_ID) @Nullable @Size(max = CHANNEL) final String channelId,
            @Field(MethodProperties.TERM_URL_3DS) @Nullable @Size(max = TEXT_LONG) final String url3ds,
            @Field(MethodProperties.RECURRING_INIT) @Nullable @PlatonOption final String recurringInit,
            @Field(MethodProperties.AUTH) @Nullable @PlatonOption final String authenticateOnly,
            @Field(MethodProperties.REQ_TOKEN) @Nullable final String reqToken,
            @Field(MethodProperties.EXT_1) @Nullable final String ext1,
            @Field(MethodProperties.EXT_2) @Nullable final String ext2,
            @Field(MethodProperties.EXT_3) @Nullable final String ext3,
            @Field(MethodProperties.EXT_4) @Nullable final String ext4,
            @Field(MethodProperties.EXT_5) @Nullable final String ext5,
            @Field(MethodProperties.EXT_6) @Nullable final String ext6,
            @Field(MethodProperties.EXT_7) @Nullable final String ext7,
            @Field(MethodProperties.EXT_8) @Nullable final String ext8,
            @Field(MethodProperties.EXT_9) @Nullable final String ext9,
            @Field(MethodProperties.EXT_10) @Nullable final String ext10
    );
}
