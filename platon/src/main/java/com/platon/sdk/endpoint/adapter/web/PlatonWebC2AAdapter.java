package com.platon.sdk.endpoint.adapter.web;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Size;

import com.google.gson.GsonBuilder;
import com.platon.sdk.callback.PlatonWebCallback;
import com.platon.sdk.constant.api.PlatonApiConstants.Formats.Payment;
import com.platon.sdk.core.PlatonCredentials;
import com.platon.sdk.endpoint.adapter.PlatonBaseAdapter;
import com.platon.sdk.endpoint.adapter.post.PlatonSaleAdapter;
import com.platon.sdk.endpoint.service.web.PlatonWebC2AService;
import com.platon.sdk.model.request.option.web.PlatonC2AOptions;
import com.platon.sdk.model.request.order.PlatonOrderC2A;
import com.platon.sdk.model.request.payer.PlatonPayerC2A;
import com.platon.sdk.util.PlatonHashUtil;
import com.platon.sdk.util.PlatonSdkUtil;

import retrofit2.Call;

import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT_SHORT;

/**
 * API adapter for creating C2A transaction in web payments platform
 */
public class PlatonWebC2AAdapter extends PlatonBaseAdapter<PlatonWebC2AService> {

    private static PlatonWebC2AAdapter sInstance;

    private PlatonWebC2AAdapter() {
    }

    public synchronized static PlatonWebC2AAdapter getInstance() {
        if (sInstance == null) sInstance = new PlatonWebC2AAdapter();
        return sInstance;
    }

    @Override
    protected Class<PlatonWebC2AService> getServiceClass() {
        return PlatonWebC2AService.class;
    }

    @Override
    protected void configureGson(@NonNull final GsonBuilder builder) {
        super.configureGson(builder);
        builder.setLenient();
    }

    /**
     * For params description see {@link #saleC2A(
     *PlatonOrderC2A, String, String, String, PlatonPayerC2A, PlatonC2AOptions, PlatonWebCallback)}
     * <p>
     */
    public Call saleC2A(
            @NonNull final PlatonOrderC2A order,
            @NonNull final String reqToken,
            @NonNull final String successUrl,
            @NonNull final PlatonWebCallback callback
    ) {
        return saleC2A(order, reqToken, successUrl, null, null, null, callback);
    }

    /**
     * Following requests creates SALE transaction in payment platform
     * It is used for authorization and capture at a time
     * This operation is used for immediate web payments
     *
     * @param order            -for sale transaction
     *                         See {@link PlatonOrderC2A} for the details
     * @param successUrl       - url by which you proceed after successful payment
     * @param orderId          - order id in payment system
     * @param payerC2A         - info holder of payer
     *                         See {@link PlatonPayerC2A}
     * @param platonC2AOptions - options to control web form representation
     *                         See {@link PlatonC2AOptions} for its params
     * @param callback         - callback which will hold url for web request
     * @return original call of {@link PlatonWebC2AService sale(...)} request
     */
    @SuppressWarnings({"ConstantConditions", "unchecked"})
    public Call saleC2A(
            @NonNull final PlatonOrderC2A order,
            @NonNull final String reqToken,
            @NonNull final String successUrl,
            @Nullable @Size(max = TEXT_SHORT) final String orderId,
            @Nullable final PlatonPayerC2A payerC2A,
            @Nullable final PlatonC2AOptions platonC2AOptions,
            @NonNull final PlatonWebCallback callback
    ) {
        final String payment = Payment.C2A_CC;

        final boolean isPayerWebC2ANull = payerC2A == null;
        final boolean isC2AFormOptionsNull = platonC2AOptions == null;
        final String orderAmountFormat = PlatonSdkUtil.getAmountFormat(order.getAmount());
        final Call call = mService.saleC2A(
                PlatonCredentials.getClientKey(),
                payment,
                orderAmountFormat,
                order.getCurrencyCode(),
                order.getDescription(),
                orderId,
                reqToken,
                successUrl,
                isC2AFormOptionsNull || TextUtils.isEmpty(platonC2AOptions.getErrorUrl()) ?
                        null : platonC2AOptions.getErrorUrl(),
                isPayerWebC2ANull || TextUtils.isEmpty(payerC2A.getEmail()) ?
                        null : payerC2A.getEmail(),
                isPayerWebC2ANull || TextUtils.isEmpty(payerC2A.getFirstName()) ?
                        null : payerC2A.getFirstName(),
                isPayerWebC2ANull || TextUtils.isEmpty(payerC2A.getLastName()) ?
                        null : payerC2A.getLastName(),
                isPayerWebC2ANull || TextUtils.isEmpty(payerC2A.getPhone()) ?
                        null : payerC2A.getPhone(),
                isPayerWebC2ANull || TextUtils.isEmpty(payerC2A.getAddress()) ?
                        null : payerC2A.getAddress(),
                isPayerWebC2ANull || TextUtils.isEmpty(payerC2A.getZip()) ?
                        null : payerC2A.getZip(),
                isPayerWebC2ANull || TextUtils.isEmpty(payerC2A.getCity()) ?
                        null : payerC2A.getCity(),
                isPayerWebC2ANull || TextUtils.isEmpty(payerC2A.getCountryCode()) ?
                        null : payerC2A.getCountryCode(),
                isPayerWebC2ANull || TextUtils.isEmpty(payerC2A.getState()) ?
                        null : payerC2A.getState(),
                isC2AFormOptionsNull || TextUtils.isEmpty(platonC2AOptions.getLanguage()) ?
                        null : platonC2AOptions.getLanguage(),
                PlatonHashUtil.encryptC2A(payment, orderAmountFormat, order.getCurrencyCode(), order.getDescription(), successUrl),
                isC2AFormOptionsNull || TextUtils.isEmpty(platonC2AOptions.getFormId()) ?
                        null : platonC2AOptions.getFormId(),
                isC2AFormOptionsNull || TextUtils.isEmpty(platonC2AOptions.getExt1()) ?
                        null : platonC2AOptions.getExt1(),
                isC2AFormOptionsNull || TextUtils.isEmpty(platonC2AOptions.getExt2()) ?
                        null : platonC2AOptions.getExt2(),
                isC2AFormOptionsNull || TextUtils.isEmpty(platonC2AOptions.getExt3()) ?
                        null : platonC2AOptions.getExt3(),
                isC2AFormOptionsNull || TextUtils.isEmpty(platonC2AOptions.getExt4()) ?
                        null : platonC2AOptions.getExt4()
        );
        call.enqueue(callback);

        return call;
    }
}
