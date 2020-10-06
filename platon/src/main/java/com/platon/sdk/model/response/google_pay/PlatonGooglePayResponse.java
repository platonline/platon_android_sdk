package com.platon.sdk.model.response.google_pay;

import androidx.annotation.Nullable;

import com.platon.sdk.callback.PlatonGooglePayCallback;
import com.platon.sdk.deserializer.PlatonGooglePayDeserializer;
import com.platon.sdk.model.response.base.PlatonApiError;
import com.platon.sdk.model.response.base.PlatonBaseResponse;

/**
 * Response which used for {@link PlatonGooglePayDeserializer} and {@link PlatonGooglePayCallback}
 */
public class PlatonGooglePayResponse extends PlatonBaseResponse<PlatonGooglePay> {

    public PlatonGooglePayResponse(@Nullable PlatonGooglePay platonResponse) {
        super(platonResponse);
    }

    public PlatonGooglePayResponse(@Nullable PlatonApiError platonApiError) {
        super(platonApiError);
    }
}
