package com.platon.sdk.callback;

import com.platon.sdk.endpoint.adapter.post.PlatonGooglePayAdapter;
import com.platon.sdk.model.response.google_pay.PlatonGooglePay3DSecure;

import retrofit2.Call;

/**
 * Used as callback in {@link PlatonGooglePayAdapter} methods
 */
public interface PlatonGooglePayCallback extends PlatonGooglePayBaseCallback {

    /**
     * Called when google pay was completed and need to be confirmed with 3DS redirect params
     * <p>
     * See {@link PlatonGooglePay3DSecure} for documentation and further usage
     * See {@link com.platon.sdk.util.Platon3dsSubmitUtil} for generating verify data
     *
     * @param call     - original call
     * @param response - google pay model with 3DS redirect info
     */
    void on3dSecureResponse(final Call call, final PlatonGooglePay3DSecure response);

}