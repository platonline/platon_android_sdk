package com.platon.sdk.callback;

import com.platon.sdk.endpoint.adapter.post.PlatonCaptureAdapter;
import com.platon.sdk.model.response.capture.PlatonCaptureDecline;
import com.platon.sdk.model.response.capture.PlatonCaptureSuccess;

import retrofit2.Call;

/**
 * Used as callback in {@link PlatonCaptureAdapter} methods
 */
public interface PlatonCaptureCallback extends PlatonBaseCallback<PlatonCaptureSuccess> {

    /**
     * Called when capture was declined
     *
     * @param call     - original capture call
     * @param response - capture model with decline reason subscribe
     */
    void onDeclineResponse(final Call call, final PlatonCaptureDecline response);

}
