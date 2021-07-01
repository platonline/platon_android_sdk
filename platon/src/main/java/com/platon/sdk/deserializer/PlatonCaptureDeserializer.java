package com.platon.sdk.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.platon.sdk.constant.api.PlatonApiConstants.SerializedNames;
import com.platon.sdk.endpoint.adapter.post.PlatonCaptureAdapter;
import com.platon.sdk.model.response.base.PlatonApiError;
import com.platon.sdk.model.response.capture.PlatonCapture;
import com.platon.sdk.model.response.capture.PlatonCaptureResponse;
import com.platon.sdk.model.response.capture.PlatonCaptureDecline;
import com.platon.sdk.model.response.capture.PlatonCaptureSuccess;

/**
 * Used for deserialize {@link PlatonCapture} models in {@link PlatonCaptureAdapter}
 */
public class PlatonCaptureDeserializer extends PlatonBaseDeserializer<PlatonCapture, PlatonCaptureResponse> {

    public PlatonCaptureDeserializer() {
        super(PlatonCapture.class);
    }

    @Override
    public PlatonCaptureResponse getError(final PlatonApiError platonApiError) {
        return new PlatonCaptureResponse(platonApiError);
    }

    @Override
    public PlatonCaptureResponse getResponse(final JsonObject jsonObject, final JsonDeserializationContext context) {
        final PlatonCapture platonCapture;
        if (jsonObject.has(SerializedNames.DECLINE_REASON))
            platonCapture = deserializeClass(jsonObject, PlatonCaptureDecline.class, context);
        else platonCapture = deserializeClass(jsonObject, PlatonCaptureSuccess.class, context);

        return new PlatonCaptureResponse(platonCapture);
    }

}