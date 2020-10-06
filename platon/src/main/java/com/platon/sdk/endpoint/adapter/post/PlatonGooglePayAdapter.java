package com.platon.sdk.endpoint.adapter.post;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.platon.sdk.callback.PlatonGooglePayCallback;
import com.platon.sdk.constant.api.PlatonOption;
import com.platon.sdk.constant.api.action.PlatonAction;
import com.platon.sdk.core.PlatonCredentials;
import com.platon.sdk.deserializer.PlatonGooglePayDeserializer;
import com.platon.sdk.endpoint.adapter.PlatonBaseAdapter;
import com.platon.sdk.endpoint.service.post.PlatonGooglePayService;
import com.platon.sdk.model.request.option.PlatonGooglePayOptions;
import com.platon.sdk.model.request.order.PlatonOrderGooglePay;
import com.platon.sdk.model.request.payer.PlatonPayerGooglePay;
import com.platon.sdk.model.response.base.PlatonBasePayment;
import com.platon.sdk.model.response.base.PlatonBaseResponse;
import com.platon.sdk.model.response.google_pay.PlatonGooglePay;
import com.platon.sdk.model.response.google_pay.PlatonGooglePay3DSecure;
import com.platon.sdk.model.response.google_pay.PlatonGooglePayDecline;
import com.platon.sdk.model.response.google_pay.PlatonGooglePayResponse;
import com.platon.sdk.model.response.google_pay.PlatonGooglePaySuccess;
import com.platon.sdk.util.PlatonHashUtil;
import com.platon.sdk.util.PlatonSdkUtil;

import retrofit2.Call;

/**
 * API adapter to complete transaction payment using Google Pay
 */
public class PlatonGooglePayAdapter extends PlatonBaseAdapter<PlatonGooglePayService> implements
        PlatonBaseAdapter.OnConfigureResponseListener<PlatonGooglePayCallback, PlatonGooglePaySuccess> {

    private static PlatonGooglePayAdapter sInstance;

    private PlatonGooglePayAdapter() {
    }

    public synchronized static PlatonGooglePayAdapter getInstance() {
        if (sInstance == null) sInstance = new PlatonGooglePayAdapter();
        return sInstance;
    }

    @Override
    protected void configureGson(@NonNull final GsonBuilder builder) {
        super.configureGson(builder);
        builder.registerTypeAdapter(
                new TypeToken<PlatonGooglePayResponse>() {
                }.getType(), new PlatonGooglePayDeserializer()
        );
    }

    /**
     * For params description see {@link #sale(PlatonOrderGooglePay, String, PlatonPayerGooglePay, PlatonGooglePayCallback)}}
     * PlatonGooglePay request without optional fields.
     */
    public Call sale(
            @NonNull final PlatonOrderGooglePay order,
            @NonNull final String paymentToken,
            @Nullable final PlatonPayerGooglePay payerSale,
            @NonNull final PlatonGooglePayCallback platonGooglePayCallback
    ) {
        return sale(order, paymentToken, payerSale, null, platonGooglePayCallback);
    }

    /**
     * This operation is commonly used for immediate payments.
     *
     * @param order                   - order data. See @{@link PlatonOrderGooglePay} for details (Required)
     * @param paymentToken            - payment token google pay
     * @param payerSale               - payer data. See @{@link PlatonPayerGooglePay} for details (Required)
     * @param platonSaleOptions       - google pay options for your request to Payment Platform. See @{@link PlatonGooglePayOptions}
     * @param platonGooglePayCallback - callback that will return response
     */
    public Call sale(
            @NonNull final PlatonOrderGooglePay order,
            @NonNull final String paymentToken,
            @Nullable final PlatonPayerGooglePay payerSale,
            @Nullable final PlatonGooglePayOptions platonSaleOptions,
            @NonNull final PlatonGooglePayCallback platonGooglePayCallback
    ) {
        return performGooglePay(order, paymentToken, payerSale, platonSaleOptions, platonGooglePayCallback, PlatonOption.NO);
    }

    /**
     * For params description see {@link #sale(PlatonOrderGooglePay, String, PlatonPayerGooglePay, PlatonGooglePayCallback)}}
     * Auth request without optional fields.
     */
    public Call auth(
            @NonNull final PlatonOrderGooglePay order,
            @NonNull final String paymentToken,
            @Nullable final PlatonPayerGooglePay payerSale,
            @NonNull final PlatonGooglePayCallback platonGooglePayCallback
    ) {
        return auth(order, paymentToken, payerSale, null, platonGooglePayCallback);
    }

    /**
     * For params description see {@link #sale(PlatonOrderGooglePay, String, PlatonPayerGooglePay, PlatonGooglePayOptions, PlatonGooglePayCallback)}}
     * AUTH is used for authorization only, without capture.
     * This operation used to hold the funds on platonCard account (for example to check platonCard validity).
     * In other words this is the first step of two step transaction.
     * Funds are deducted and held from the customer’s credit card.
     * Note that the money is not transferred to the merchant account yet.
     * This is when credit card capture comes in.
     */
    public Call auth(
            @NonNull final PlatonOrderGooglePay order,
            @NonNull final String paymentToken,
            @Nullable final PlatonPayerGooglePay payerSale,
            @Nullable final PlatonGooglePayOptions platonSaleOptions,
            @NonNull final PlatonGooglePayCallback platonGooglePayCallback
    ) {
        return performGooglePay(order, paymentToken, payerSale, platonSaleOptions, platonGooglePayCallback, PlatonOption.YES);
    }

    /**
     * For params description see {@link #sale(PlatonOrderGooglePay, String, PlatonPayerGooglePay, PlatonGooglePayOptions, PlatonGooglePayCallback)}}
     *
     * @param auth indicates whether the SALE request with AUTH.
     */
    private Call performGooglePay(
            @NonNull final PlatonOrderGooglePay platonOrderGooglePay,
            @NonNull final String paymentToken,
            @Nullable final PlatonPayerGooglePay platonPayerGooglePay,
            @Nullable final PlatonGooglePayOptions platonGooglePayOptions,
            @NonNull final PlatonGooglePayCallback platonGooglePayCallback,
            @NonNull @PlatonOption final String auth
    ) {

        final boolean isSaleOptionsNull = platonGooglePayOptions == null;
        final boolean isPayerNull = platonPayerGooglePay == null;
        final String orderAmountFormat = PlatonSdkUtil.getAmountFormat(platonOrderGooglePay.getAmount());
        String email = "";
        if (platonPayerGooglePay != null && platonPayerGooglePay.getEmail() != null)
            email = platonPayerGooglePay.getEmail();

        final Call call = mService.googlePay(
                PlatonAction.GOOGLE_PAY,
                isSaleOptionsNull || TextUtils.isEmpty(platonGooglePayOptions.getAsync()) ?
                        null : platonGooglePayOptions.getAsync(),
                PlatonCredentials.getClientKey(),
                isSaleOptionsNull || TextUtils.isEmpty(platonGooglePayOptions.getChannelId()) ?
                        null : platonGooglePayOptions.getChannelId(),
                platonOrderGooglePay.getId(),
                orderAmountFormat,
                platonOrderGooglePay.getCurrencyCode(),
                platonOrderGooglePay.getDescription(),
                paymentToken,
                isPayerNull || TextUtils.isEmpty(platonPayerGooglePay.getFirstName()) ?
                        null : platonPayerGooglePay.getFirstName(),
                isPayerNull || TextUtils.isEmpty(platonPayerGooglePay.getLastName()) ?
                        null : platonPayerGooglePay.getLastName(),
                isPayerNull || TextUtils.isEmpty(platonPayerGooglePay.getMiddleName()) ?
                        null : platonPayerGooglePay.getMiddleName(),
                isPayerNull || TextUtils.isEmpty(platonPayerGooglePay.getBirthday()) ?
                        null : PlatonSdkUtil.getDateFormat(platonPayerGooglePay.getBirthday()),
                isPayerNull || TextUtils.isEmpty(platonPayerGooglePay.getAddress()) ?
                        null : platonPayerGooglePay.getAddress(),
                isPayerNull || TextUtils.isEmpty(platonPayerGooglePay.getCountryCode()) ?
                        null : platonPayerGooglePay.getCountryCode(),
                isPayerNull || TextUtils.isEmpty(platonPayerGooglePay.getState()) ?
                        null : platonPayerGooglePay.getState(),
                isPayerNull || TextUtils.isEmpty(platonPayerGooglePay.getCity()) ?
                        null : platonPayerGooglePay.getCity(),
                isPayerNull || TextUtils.isEmpty(platonPayerGooglePay.getZip()) ?
                        null : platonPayerGooglePay.getZip(),
                isPayerNull || TextUtils.isEmpty(platonPayerGooglePay.getEmail()) ?
                        null : platonPayerGooglePay.getEmail(),
                isPayerNull || TextUtils.isEmpty(platonPayerGooglePay.getPhone()) ?
                        null : platonPayerGooglePay.getPhone(),
                isPayerNull || TextUtils.isEmpty(platonPayerGooglePay.getIpAddress()) ?
                        null : platonPayerGooglePay.getIpAddress(),
                PlatonCredentials.getTermUrl3Ds(),
                auth,
                PlatonHashUtil.encryptGooglePay(email, paymentToken)
        );
        call.enqueue(enqueueWithCallback(platonGooglePayCallback, this));

        return call;
    }


    @Override
    protected Class<PlatonGooglePayService> getServiceClass() {
        return PlatonGooglePayService.class;
    }

    @Override
    public void onConfiguredResponse(final PlatonGooglePayCallback callback, final Call call, final PlatonBaseResponse response) {
        final PlatonBasePayment googlePay = response.getPlatonResponse();
        if (googlePay instanceof PlatonGooglePayDecline) {
            callback.onDeclineResponse(call, (PlatonGooglePayDecline) googlePay);
        } else if (googlePay instanceof PlatonGooglePay3DSecure) {
            callback.on3dSecureResponse(call, (PlatonGooglePay3DSecure) googlePay);
        } else if (googlePay instanceof PlatonGooglePaySuccess) {
            callback.onResponse(call, (PlatonGooglePaySuccess) googlePay);
        } else {
            callback.onAsyncResponse(call, (PlatonGooglePay) googlePay);
        }
    }
}
