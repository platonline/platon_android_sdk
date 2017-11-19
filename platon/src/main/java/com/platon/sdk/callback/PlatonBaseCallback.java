package com.platon.sdk.callback;

import com.platon.sdk.endpoint.adapter.PlatonBaseAdapter;
import com.platon.sdk.model.response.base.PlatonApiError;
import com.platon.sdk.model.response.base.PlatonBasePayment;

import retrofit2.Call;

/**
 * Base callback which used as request callback and configure in {@link PlatonBaseAdapter}
 *
 * @param <PlatonPayment>  - payment model which used as response
 */
public interface PlatonBaseCallback<PlatonPayment extends PlatonBasePayment> {

    /**
     * Called when payment model is successfully deserialize
     *
     * @param call     - original call
     * @param response - successful response
     */
    void onResponse(final Call call, final PlatonPayment response);

    /**
     * Called when payment model is not exists in response
     *
     * @param call           - original call
     * @param platonApiError - error with code and message
     */
    void onError(final Call call, final PlatonApiError platonApiError);

    /**
     * Called when original call was cancelled or network error was occurred
     *
     * @param call - original call
     * @param t    - some exception
     */
    void onFailure(final Call call, final Throwable t);

}
