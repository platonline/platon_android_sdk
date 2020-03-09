package com.platon.sdk.endpoint.adapter.post;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.Size;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.platon.sdk.callback.PlatonSaleCallback;
import com.platon.sdk.constant.api.PlatonOption;
import com.platon.sdk.constant.api.action.PlatonAction;
import com.platon.sdk.core.PlatonCredentials;
import com.platon.sdk.deserializer.PlatonSaleDeserializer;
import com.platon.sdk.endpoint.adapter.PlatonBaseAdapter;
import com.platon.sdk.endpoint.service.post.PlatonRecurringService;
import com.platon.sdk.model.request.order.PlatonOrderRecurring;
import com.platon.sdk.model.request.recurring.PlatonRecurring;
import com.platon.sdk.model.response.base.PlatonBasePayment;
import com.platon.sdk.model.response.base.PlatonBaseResponse;
import com.platon.sdk.model.response.sale.PlatonSale;
import com.platon.sdk.model.response.sale.PlatonSale3DSecure;
import com.platon.sdk.model.response.sale.PlatonSaleDecline;
import com.platon.sdk.model.response.sale.PlatonSaleSuccess;
import com.platon.sdk.model.response.sale.PlatonSaleRecurringInit;
import com.platon.sdk.model.response.sale.PlatonSaleResponse;
import com.platon.sdk.util.PlatonHashUtil;
import com.platon.sdk.util.PlatonSdkUtil;

import retrofit2.Call;

import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Card.MAX_CARD_NUMBER;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Card.MIN_CARD_NUMBER;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Payer.EMAIL;

/**
 * API adapter to facilitate PlatonRecurring payments commonly used to create new transactions
 * based on already stored cardholder information from previous operations.
 * <p>
 * See also {@link PlatonRecurringService} and {@link PlatonSaleAdapter}
 */
public class PlatonRecurringAdapter extends PlatonBaseAdapter<PlatonRecurringService> implements
        PlatonBaseAdapter.OnConfigureResponseListener<PlatonSaleCallback, PlatonSaleSuccess> {

    private static PlatonRecurringAdapter sInstance;

    private PlatonRecurringAdapter() {
    }

    public synchronized static PlatonRecurringAdapter getInstance() {
        if (sInstance == null) sInstance = new PlatonRecurringAdapter();
        return sInstance;
    }

    @Override
    protected Class<PlatonRecurringService> getServiceClass() {
        return PlatonRecurringService.class;
    }

    @Override
    protected void configureGson(@NonNull final GsonBuilder builder) {
        super.configureGson(builder);
        builder.registerTypeAdapter(
                new TypeToken<PlatonSaleResponse>() {
                }.getType(), new PlatonSaleDeserializer()
        );
    }

    /**
     * For params description see {@link #performRecurring(
     *String, PlatonOrderRecurring, PlatonRecurring, String, String, PlatonSaleCallback)}
     */
    @SuppressWarnings("ConstantConditions")
    public Call recurringAsyncSale(
            @NonNull final PlatonOrderRecurring order,
            @NonNull final PlatonRecurring platonRecurring,
            @NonNull @Size(max = EMAIL) final String payerEmail,
            @NonNull @Size(min = MIN_CARD_NUMBER, max = MAX_CARD_NUMBER) final String cardNumber,
            @NonNull final PlatonSaleCallback callback
    ) {
        return recurringAsyncSale(order, platonRecurring, PlatonHashUtil.encryptSale(payerEmail, cardNumber), callback);
    }

    /**
     * For params description see {@link #performRecurring(
     *String, PlatonOrderRecurring, PlatonRecurring, String, String, PlatonSaleCallback)}
     */
    public Call recurringAsyncSale(
            @NonNull final PlatonOrderRecurring order,
            @NonNull final PlatonRecurring platonRecurring,
            @NonNull final String hash,
            @NonNull final PlatonSaleCallback callback
    ) {
        return performRecurring(PlatonOption.YES, order, platonRecurring, hash, PlatonOption.NO, callback);
    }

    /**
     * For params description see {@link #performRecurring(
     *String, PlatonOrderRecurring, PlatonRecurring, String, String, PlatonSaleCallback)}
     */
    @SuppressWarnings("ConstantConditions")
    public Call recurringSale(
            @NonNull final PlatonOrderRecurring order,
            @NonNull final PlatonRecurring platonRecurring,
            @NonNull @Size(max = EMAIL) final String payerEmail,
            @NonNull @Size(min = MIN_CARD_NUMBER, max = MAX_CARD_NUMBER) final String cardNumber,
            @NonNull final PlatonSaleCallback callback
    ) {
        return recurringSale(order, platonRecurring, PlatonHashUtil.encryptSale(payerEmail, cardNumber), callback);
    }

    /**
     * For params description see {@link #performRecurring(
     *String, PlatonOrderRecurring, PlatonRecurring, String, String, PlatonSaleCallback)}
     * <p>
     * Convenient method from {@link #performRecurring(
     *String, PlatonOrderRecurring, PlatonRecurring, String, String, PlatonSaleCallback)}
     * for immediate payments (@{@link PlatonAction#SALE}) during recurring payment (transaction)
     */
    public Call recurringSale(
            @NonNull final PlatonOrderRecurring order,
            @NonNull final PlatonRecurring platonRecurring,
            @NonNull final String hash,
            @NonNull final PlatonSaleCallback callback
    ) {
        return performRecurring(null, order, platonRecurring, hash, PlatonOption.NO, callback);
    }

    /**
     * For params description see {@link #performRecurring(
     *String, PlatonOrderRecurring, PlatonRecurring, String, String, PlatonSaleCallback)}
     */
    @SuppressWarnings("ConstantConditions")
    public Call recurringAsyncAuth(
            @NonNull final PlatonOrderRecurring order,
            @NonNull final PlatonRecurring platonRecurring,
            @NonNull @Size(max = EMAIL) final String payerEmail,
            @NonNull @Size(min = MIN_CARD_NUMBER, max = MAX_CARD_NUMBER) final String cardNumber,
            @NonNull final PlatonSaleCallback callback
    ) {
        return recurringAsyncAuth(order, platonRecurring, PlatonHashUtil.encryptSale(payerEmail, cardNumber), callback);
    }

    /**
     * For params description see {@link #performRecurring(
     *String, PlatonOrderRecurring, PlatonRecurring, String, String, PlatonSaleCallback)}
     */
    public Call recurringAsyncAuth(
            @NonNull final PlatonOrderRecurring order,
            @NonNull final PlatonRecurring platonRecurring,
            @NonNull final String hash,
            @NonNull final PlatonSaleCallback callback
    ) {
        return performRecurring(PlatonOption.YES, order, platonRecurring, hash, PlatonOption.YES, callback);
    }

    /**
     * For params description see {@link #performRecurring(
     *String, PlatonOrderRecurring, PlatonRecurring, String, String, PlatonSaleCallback)}
     */
    @SuppressWarnings("ConstantConditions")
    public Call recurringAuth(
            @NonNull final PlatonOrderRecurring order,
            @NonNull final PlatonRecurring platonRecurring,
            @NonNull @Size(max = EMAIL) final String payerEmail,
            @NonNull @Size(min = MIN_CARD_NUMBER, max = MAX_CARD_NUMBER) final String cardNumber,
            @NonNull final PlatonSaleCallback callback
    ) {
        return recurringAuth(order, platonRecurring, PlatonHashUtil.encryptSale(payerEmail, cardNumber), callback);
    }

    /**
     * For params description see {@link #performRecurring(
     *String, PlatonOrderRecurring, PlatonRecurring, String, String, PlatonSaleCallback)}
     * <p>
     * Convenient method from {@link #performRecurring(
     *String, PlatonOrderRecurring, PlatonRecurring, String, String, PlatonSaleCallback)}
     * for recurring Authentication only without following capture
     * <p>
     * Dual Message System (DMS) - DMS is represented by AUTH and CAPTURE transactions
     * AUTH is used for authorization only, without capture
     * This operation used to hold the funds on card account (for example to check card validity)
     * In other words this is the first step of two step transaction
     * Funds are deducted and held from the customer’s credit card
     * Note that the money is not transferred to the merchant account yet
     */
    public Call recurringAuth(
            @NonNull final PlatonOrderRecurring order,
            @NonNull final PlatonRecurring platonRecurring,
            @NonNull final String hash,
            @NonNull final PlatonSaleCallback callback
    ) {
        return performRecurring(null, order, platonRecurring, hash, PlatonOption.YES, callback);
    }

    /**
     * Used for recurring for both Single Message System (SMS) and Dual Message System (DMS)
     * <p>
     * RECURRING SALE request has same logic as SALE request (see @{@link PlatonSaleAdapter}),
     * the only difference is that you need to provide primary PlatonTransaction ID of the primary transaction
     * in the Payment Platform (firstTransId), and this request will create
     * a secondary transaction with previously used cardholder data from primary transaction
     * <p>
     * Response from Payment Platform is the same as by {@link PlatonSaleAdapter} methods
     *
     * @param order     - order info holder for id, amount and description (Required)
     *                  See {@link PlatonOrderRecurring} for variables description
     * @param platonRecurring - platonRecurring info holder for first transaction ID and token (Required)
     *                  See {@link PlatonRecurring} for variables description
     * @param hash      - special signature to validate your request to Payment Platform (Required)
     * @param auth      - indicates that transaction must be only authenticated, but not captured
     *                  See {@link PlatonOption}) for possible values
     *                  Default {@link PlatonOption#NO}
     * @param callback  - callback that will return response (Required)
     */
    @SuppressWarnings("unchecked")
    private Call performRecurring(
            @Nullable @PlatonOption final String async,
            @NonNull final PlatonOrderRecurring order,
            @NonNull final PlatonRecurring platonRecurring,
            @NonNull final String hash,
            @NonNull @PlatonOption final String auth,
            @NonNull final PlatonSaleCallback callback
    ) {
        final Call call = mService.recurring(
                PlatonAction.RECURRING_SALE,
                async,
                PlatonCredentials.getClientKey(),
                order.getId(),
                PlatonSdkUtil.getAmountFormat(order.getAmount()),
                order.getDescription(),
                platonRecurring.getFirstTransId(),
                platonRecurring.getToken(),
                hash,
                auth
        );
        call.enqueue(enqueueWithCallback(callback, this));

        return call;
    }

    @Override
    public void onConfiguredResponse(
            final PlatonSaleCallback callback, final Call call, final PlatonBaseResponse response
    ) {
        final PlatonBasePayment sale = response.getPlatonResponse();
        if (sale instanceof PlatonSaleRecurringInit)
            callback.onRecurringInitResponse(call, (PlatonSaleRecurringInit) sale);
        else if (sale instanceof PlatonSaleDecline)
            callback.onDeclineResponse(call, (PlatonSaleDecline) sale);
        else if (sale instanceof PlatonSale3DSecure)
            callback.on3dSecureResponse(call, (PlatonSale3DSecure) sale);
        else if (sale instanceof PlatonSaleSuccess)
            callback.onResponse(call, (PlatonSaleSuccess) sale);
        else callback.onAsyncResponse(call, (PlatonSale) sale);
    }
}
