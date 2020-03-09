package com.platon.sdk.endpoint.service.post;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.Size;


import com.platon.sdk.constant.api.PlatonApiConstants.MethodProperties;
import com.platon.sdk.constant.api.action.PlatonAction;
import com.platon.sdk.constant.api.action.PlatonRequestAction;
import com.platon.sdk.endpoint.adapter.post.PlatonCaptureAdapter;
import com.platon.sdk.endpoint.adapter.post.PlatonSaleAdapter;
import com.platon.sdk.model.response.capture.PlatonCaptureResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static com.platon.sdk.constant.api.PlatonApiConstants.CalledMethod.BASE;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MAX_FORMAT_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MIN_FORMAT_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT;

/**
 * PlatonCapture payment API service to handle DMS second part of transaction
 * {@link PlatonAction#CAPTURE} after {@link MethodProperties#AUTH}
 * <p>
 * See {@link PlatonCaptureAdapter} and {@link PlatonSaleAdapter}
 */
public interface PlatonCaptureService {

    @FormUrlEncoded
    @POST(BASE)
    Call<PlatonCaptureResponse> capture(
            @Field(MethodProperties.ACTION) @NonNull @PlatonRequestAction final String action,
            @Field(MethodProperties.CLIENT_KEY) @NonNull final String clientKey,
            @Field(MethodProperties.TRANS_ID) @NonNull @Size(max = TEXT) final String transactionId,
            @Field(MethodProperties.AMOUNT)
            @Nullable @Size(min = MIN_FORMAT_AMOUNT, max = MAX_FORMAT_AMOUNT) final String partialAmount,
            @Field(MethodProperties.HASH) @NonNull final String hash
    );
}
