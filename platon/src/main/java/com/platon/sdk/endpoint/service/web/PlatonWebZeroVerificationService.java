package com.platon.sdk.endpoint.service.web;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Size;

import com.platon.sdk.constant.api.PlatonApiConstants.MethodProperties;
import com.platon.sdk.endpoint.adapter.web.PlatonWebZeroVerificationAdapter;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static com.platon.sdk.constant.api.PlatonApiConstants.CalledMethod.BASE;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.LANG;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT_MIN;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Payer.COUNTRY_CODE;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.State.STATE;

/**
 * <p>
 * See {@link PlatonWebZeroVerificationAdapter} for it realisation
 */
public interface PlatonWebZeroVerificationService {

    @FormUrlEncoded
    @POST(BASE)
    Call<String> zeroVerification(
            @Field(MethodProperties.KEY) @NonNull final String key,
            @Field(MethodProperties.PAYMENT) @NonNull final String payment,
            @Field(MethodProperties.DATA) @NonNull final String data,
            @Field(MethodProperties.URL) @NonNull final String url,

            @Field(MethodProperties.FORM_ID) @NonNull final String formId,
            @Field(MethodProperties.REQ_TOKEN) @NotNull final String reqToken,
            @Field(MethodProperties.SIGN) @NonNull final String sign,

            @Field(MethodProperties.LANG) @Nullable @Size(max = LANG) final String language,
            @Field(MethodProperties.EMAIL) @Nullable final String email,
            @Field(MethodProperties.FIRST_NAME) @Nullable final String firstName,
            @Field(MethodProperties.LAST_NAME) @Nullable final String lastName,
            @Field(MethodProperties.PHONE) @Nullable final String phone,
            @Field(MethodProperties.ADDRESS) @Nullable final String address,
            @Field(MethodProperties.ZIP) @Nullable final String zip,
            @Field(MethodProperties.CITY) @Nullable final String city,
            @Field(MethodProperties.COUNTRY) @Nullable @Size(max = COUNTRY_CODE) final String country,
            @Field(MethodProperties.STATE) @Nullable @Size(max = STATE) final String state,
            @Field(MethodProperties.CUSTOMER_WALLET) @Nullable final String customerWallet,
            @Field(MethodProperties.ORDER) @Nullable @Size(max = TEXT_MIN) final String order,
            @Field(MethodProperties.ERROR_URL) @Nullable final String errorUrl,
            @Field(MethodProperties.EXT_1) @Nullable final String ext1,
            @Field(MethodProperties.EXT_2) @Nullable final String ext2,
            @Field(MethodProperties.EXT_3) @Nullable final String ext3,
            @Field(MethodProperties.EXT_4) @Nullable final String ext4,
            @Field(MethodProperties.EXT_5) @Nullable final String ext5,
            @Field(MethodProperties.EXT_6) @Nullable final String ext6,
            @Field(MethodProperties.EXT_7) @Nullable final String ext7,
            @Field(MethodProperties.EXT_8) @Nullable final String ext8,
            @Field(MethodProperties.EXT_9) @Nullable final String ext9,
            @Field(MethodProperties.EXT_10) @Nullable final String ext10,
            @Field(MethodProperties.BANK_ID) @Nullable final String bankId,
            @Field(MethodProperties.PAYER_ID) @Nullable final String payerId
    );
}
