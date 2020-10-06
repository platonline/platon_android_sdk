package com.platon.sdk.callback;

import com.platon.sdk.model.response.google_pay.PlatonGooglePay;
import com.platon.sdk.model.response.google_pay.PlatonGooglePayDecline;
import com.platon.sdk.model.response.google_pay.PlatonGooglePaySuccess;

import retrofit2.Call;

/**
 * Used as base callback for {@link PlatonGooglePayCallback}
 */
public interface PlatonGooglePayBaseCallback extends PlatonBaseCallback<PlatonGooglePaySuccess> {

    /**
     * Called when google pay was declined
     *
     * @param call     - original google pay call
     * @param response - google pay model with decline reason subscribe
     */
    void onDeclineResponse(final Call call, final PlatonGooglePayDecline response);

    /**
     * Called when google pay is was completed in async mode
     *
     * @param call     - original google pay call
     * @param response - info about async google pay
     */
    void onAsyncResponse(final Call call, final PlatonGooglePay response);

}
