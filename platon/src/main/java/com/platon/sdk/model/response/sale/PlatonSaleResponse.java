package com.platon.sdk.model.response.sale;

import com.platon.sdk.callback.PlatonSaleCallback;
import com.platon.sdk.deserializer.PlatonSaleDeserializer;
import com.platon.sdk.model.response.base.PlatonBaseResponse;
import com.platon.sdk.model.response.base.PlatonApiError;

/**
 * Response which used for {@link PlatonSaleDeserializer} and {@link PlatonSaleCallback}
 */
public class PlatonSaleResponse extends PlatonBaseResponse<PlatonSale> {

    public PlatonSaleResponse(final PlatonSale platonSale) {
        super(platonSale);
    }

    public PlatonSaleResponse(final PlatonApiError platonApiError) {
        super(platonApiError);
    }

}