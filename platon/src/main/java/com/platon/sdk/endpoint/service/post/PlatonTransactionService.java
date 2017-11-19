package com.platon.sdk.endpoint.service.post;

import android.support.annotation.NonNull;
import android.support.annotation.Size;

import com.platon.sdk.constant.api.PlatonApiConstants.MethodProperties;
import com.platon.sdk.constant.api.action.PlatonTransactionAction;
import com.platon.sdk.endpoint.adapter.post.PlatonTransactionAdapter;
import com.platon.sdk.model.response.base.PlatonBaseResponse;
import com.platon.sdk.model.response.transaction.PlatonTransactionDetails;
import com.platon.sdk.model.response.transaction.PlatonTransactionStatus;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static com.platon.sdk.constant.api.PlatonApiConstants.CalledMethod.BASE;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT;

/**
 * Payment transaction API service that describe method that provide
 * additional transaction details according to {@link PlatonTransactionAction}
 * <p>
 * See @{@link PlatonTransactionAdapter}
 */
public interface PlatonTransactionService {

    @FormUrlEncoded
    @POST(BASE)
    Call<PlatonBaseResponse<PlatonTransactionStatus>> getStatus(
            @Field(MethodProperties.ACTION) @NonNull @PlatonTransactionAction final String action,
            @Field(MethodProperties.CLIENT_KEY) @NonNull final String clientKey,
            @Field(MethodProperties.TRANS_ID) @NonNull @Size(max = TEXT) final String transactionId,
            @Field(MethodProperties.HASH) @NonNull final String hash
    );

    @FormUrlEncoded
    @POST(BASE)
    Call<PlatonBaseResponse<PlatonTransactionDetails>> getDetails(
            @Field(MethodProperties.ACTION) @NonNull @PlatonTransactionAction final String action,
            @Field(MethodProperties.CLIENT_KEY) @NonNull final String clientKey,
            @Field(MethodProperties.TRANS_ID) @NonNull @Size(max = TEXT) final String transactionId,
            @Field(MethodProperties.HASH) @NonNull final String hash
    );
}
