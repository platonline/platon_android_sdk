package com.platon.sdk.model.response.base;

import android.support.annotation.Nullable;

import com.platon.sdk.deserializer.PlatonBaseDeserializer;
import com.platon.sdk.endpoint.adapter.PlatonBaseAdapter;

/**
 * Response for Platon SDK which hold {@param PlatonPayment} and {@link PlatonApiError}
 * <p>
 * See {@link PlatonBaseDeserializer} and {@link PlatonBaseAdapter} for more details
 *
 * @param <PlatonPayment> - payment model which will be in response
 */
public class PlatonBaseResponse<PlatonPayment extends PlatonBasePayment> {

    @Nullable
    private PlatonPayment mPlatonResponse;

    @Nullable
    private PlatonApiError mPlatonApiError;

    public PlatonBaseResponse(@Nullable final PlatonPayment platonResponse) {
        mPlatonResponse = platonResponse;
        mPlatonApiError = null;
    }

    public PlatonBaseResponse(@Nullable final PlatonApiError platonApiError) {
        mPlatonResponse = null;
        mPlatonApiError = platonApiError;
    }

    @Nullable
    public PlatonPayment getPlatonResponse() {
        return mPlatonResponse;
    }

    public void setPlatonResponse(@Nullable final PlatonPayment platonResponse) {
        mPlatonResponse = platonResponse;
    }

    @Nullable
    public PlatonApiError getPlatonApiError() {
        return mPlatonApiError;
    }

    public void setPlatonApiError(@Nullable final PlatonApiError platonApiError) {
        mPlatonApiError = platonApiError;
    }

    @Override
    public String toString() {
        return "PlatonBaseResponse{" +
                "mPlatonResponse=" + mPlatonResponse +
                ", mPlatonApiError=" + mPlatonApiError +
                '}';
    }
}