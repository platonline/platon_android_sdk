package com.platon.sdk.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.platon.sdk.constant.api.PlatonApiConstants;
import com.platon.sdk.constant.api.PlatonResult;
import com.platon.sdk.constant.api.PlatonStatus;
import com.platon.sdk.endpoint.adapter.post.PlatonGooglePayAdapter;
import com.platon.sdk.model.response.base.PlatonApiError;
import com.platon.sdk.model.response.google_pay.PlatonGooglePay;
import com.platon.sdk.model.response.google_pay.PlatonGooglePay3DSecure;
import com.platon.sdk.model.response.google_pay.PlatonGooglePayDecline;
import com.platon.sdk.model.response.google_pay.PlatonGooglePayResponse;
import com.platon.sdk.model.response.google_pay.PlatonGooglePaySuccess;

/**
 * Used for deserialize {@link PlatonGooglePay} models in {@link PlatonGooglePayAdapter}
 */
public class PlatonGooglePayDeserializer extends PlatonBaseDeserializer<PlatonGooglePay, PlatonGooglePayResponse> {

    public PlatonGooglePayDeserializer() {
        super(PlatonGooglePay.class);
    }

    @Override
    public PlatonGooglePayResponse getError(final PlatonApiError platonApiError) {
        return new PlatonGooglePayResponse(platonApiError);
    }

    @Override
    public PlatonGooglePayResponse getResponse(JsonObject jsonObject, JsonDeserializationContext context) {
        final PlatonGooglePay platonGooglePay;
        if (jsonObject.has(PlatonApiConstants.SerializedNames.STATUS)) {
            @PlatonStatus final String status = jsonObject.get(PlatonApiConstants.SerializedNames.STATUS).getAsString();
            @PlatonResult final String result = jsonObject.get(PlatonApiConstants.SerializedNames.RESULT).getAsString();

            if (status.equalsIgnoreCase(PlatonStatus.SECURE_3D))
                platonGooglePay = deserializeClass(jsonObject, PlatonGooglePay3DSecure.class, context);
            else {
                if (result.equalsIgnoreCase(PlatonResult.DECLINED))
                    platonGooglePay = deserializeClass(jsonObject, PlatonGooglePayDecline.class, context);
                else {
                    platonGooglePay = deserializeClass(jsonObject, PlatonGooglePaySuccess.class, context);
                }
            }
        } else platonGooglePay = deserializeClass(jsonObject, PlatonGooglePay.class, context);

        return new PlatonGooglePayResponse(platonGooglePay);
    }
}
