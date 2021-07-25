package com.platon.sdk.endpoint.service.web;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Size;

import com.platon.sdk.constant.api.PlatonApiConstants.MethodProperties;
import com.platon.sdk.endpoint.adapter.web.PlatonWebC2AOneClickAdapter;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static com.platon.sdk.constant.api.PlatonApiConstants.CalledMethod.BASE;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.LANG;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT_LONG;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT_MIN;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT_SHORT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Payer.COUNTRY_CODE;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.State.STATE;

/**
 * <p>
 * See {@link PlatonWebC2AOneClickAdapter} for it realisation
 */
public interface PlatonWebC2AOneClickService {

    @FormUrlEncoded
    @POST(BASE)
    Call<String> saleC2AOneClick(
            @Field(MethodProperties.KEY) @NonNull final String key,
            @Field(MethodProperties.PAYMENT) @NonNull final String payment,
            @Field(MethodProperties.AMOUNT) @NonNull final String amount,
            @Field(MethodProperties.CURRENCY) @NonNull final String currency,
            @Field(MethodProperties.DESCRIPTION) @NonNull @Size(max = TEXT) final String description,
            @Field(MethodProperties.ORDER) @Nullable @Size(max = TEXT_MIN) final String order,
            @Field(MethodProperties.CARD_TOKEN) @NonNull final String cardToken,
            @Field(MethodProperties.URL) @NonNull @Size(max = TEXT_LONG) final String url,
            @Field(MethodProperties.ERROR_URL) @Nullable final String errorUrl,
            @Field(MethodProperties.EMAIL) @Nullable final String email,
            @Field(MethodProperties.FIRST_NAME) @Nullable @Size(max = TEXT_SHORT) final String firstName,
            @Field(MethodProperties.LAST_NAME) @Nullable @Size(max = TEXT_SHORT) final String lastName,
            @Field(MethodProperties.PHONE) @Nullable final String phone,
            @Field(MethodProperties.ADDRESS) @Nullable @Size(max = TEXT) final String address,
            @Field(MethodProperties.ZIP) @Nullable @Size(max = TEXT_SHORT) final String zip,
            @Field(MethodProperties.CITY) @Nullable @Size(max = TEXT_SHORT) final String city,
            @Field(MethodProperties.COUNTRY) @Nullable @Size(max = COUNTRY_CODE) final String country,
            @Field(MethodProperties.STATE) @Nullable @Size(max = STATE) final String state,
            @Field(MethodProperties.LANG) @Nullable @Size(max = LANG) final String language,
            @Field(MethodProperties.SIGN) @NonNull final String sign,
            @Field(MethodProperties.FORM_ID) @Nullable @Size(max = TEXT) final String formId,
            @Field(MethodProperties.EXT_1) @Nullable @Size(max = TEXT_LONG) final String ext1,
            @Field(MethodProperties.EXT_2) @Nullable @Size(max = TEXT_LONG) final String ext2,
            @Field(MethodProperties.EXT_3) @Nullable @Size(max = TEXT_LONG) final String ext3,
            @Field(MethodProperties.EXT_4) @Nullable @Size(max = TEXT_LONG) final String ext4
    );
}
