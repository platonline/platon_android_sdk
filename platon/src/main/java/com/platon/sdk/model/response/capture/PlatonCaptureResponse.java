package com.platon.sdk.model.response.capture;

import com.platon.sdk.callback.PlatonCaptureCallback;
import com.platon.sdk.deserializer.PlatonCaptureDeserializer;
import com.platon.sdk.model.response.base.PlatonBaseResponse;
import com.platon.sdk.model.response.base.PlatonApiError;

/**
 * Response which used for {@link PlatonCaptureDeserializer} and {@link PlatonCaptureCallback}
 */
public class PlatonCaptureResponse extends PlatonBaseResponse<PlatonCapture> {

    public PlatonCaptureResponse(final PlatonCapture platonCapture) {
        super(platonCapture);
    }

    public PlatonCaptureResponse(final PlatonApiError platonApiError) {
        super(platonApiError);
    }

}