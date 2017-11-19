package com.platon.sdk.callback;

import com.platon.sdk.endpoint.adapter.post.PlatonRecurringAdapter;
import com.platon.sdk.model.response.sale.PlatonSale;
import com.platon.sdk.model.response.sale.PlatonSaleDecline;
import com.platon.sdk.model.response.sale.PlatonSaleSuccess;

import retrofit2.Call;

/**
 * Used as callback in {@link PlatonRecurringAdapter} methods
 * Used as base callback for {@link PlatonSaleCallback}
 */
public interface PlatonSaleBaseCallback extends PlatonBaseCallback<PlatonSaleSuccess> {

    /**
     * Called when sale was declined
     *
     * @param call     - original sale call
     * @param response - sale model with decline reason subscribe
     */
    void onDeclineResponse(final Call call, final PlatonSaleDecline response);

    /**
     * Called when sale is was completed in async mode
     *
     * @param call     - original sale call
     * @param response - info about async sale
     */
    void onAsyncResponse(final Call call, final PlatonSale response);

}
