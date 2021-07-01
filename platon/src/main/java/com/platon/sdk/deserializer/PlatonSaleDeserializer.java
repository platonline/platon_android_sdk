package com.platon.sdk.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.platon.sdk.constant.api.PlatonApiConstants.SerializedNames;
import com.platon.sdk.constant.api.PlatonResult;
import com.platon.sdk.constant.api.PlatonStatus;
import com.platon.sdk.endpoint.adapter.post.PlatonSaleAdapter;
import com.platon.sdk.model.response.base.PlatonApiError;
import com.platon.sdk.model.response.sale.PlatonSale;
import com.platon.sdk.model.response.sale.PlatonSaleSuccess;
import com.platon.sdk.model.response.sale.PlatonSale3DSecure;
import com.platon.sdk.model.response.sale.PlatonSaleDecline;
import com.platon.sdk.model.response.sale.PlatonSaleRecurringInit;
import com.platon.sdk.model.response.sale.PlatonSaleResponse;

/**
 * Used for deserialize {@link PlatonSale} models in {@link PlatonSaleAdapter}
 */
public class PlatonSaleDeserializer extends PlatonBaseDeserializer<PlatonSale, PlatonSaleResponse> {

    public PlatonSaleDeserializer() {
        super(PlatonSale.class);
    }

    @Override
    public PlatonSaleResponse getError(final PlatonApiError platonApiError) {
        return new PlatonSaleResponse(platonApiError);
    }

    @Override
    public PlatonSaleResponse getResponse(final JsonObject jsonObject, final JsonDeserializationContext context) {
        final PlatonSale platonSale;
        if (jsonObject.has(SerializedNames.STATUS)) {
            @PlatonStatus final String status = jsonObject.get(SerializedNames.STATUS).getAsString();
            @PlatonResult final String result = jsonObject.get(SerializedNames.RESULT).getAsString();

            if (status.equalsIgnoreCase(PlatonStatus.SECURE_3D))
                platonSale = deserializeClass(jsonObject, PlatonSale3DSecure.class, context);
            else {
                if (result.equalsIgnoreCase(PlatonResult.DECLINED))
                    platonSale = deserializeClass(jsonObject, PlatonSaleDecline.class, context);
                else {
                    if (jsonObject.has(SerializedNames.RECURRING_TOKEN))
                        platonSale = deserializeClass(jsonObject, PlatonSaleRecurringInit.class, context);
                    else platonSale = deserializeClass(jsonObject, PlatonSaleSuccess.class, context);
                }
            }
        } else platonSale = deserializeClass(jsonObject, PlatonSale.class, context);

        return new PlatonSaleResponse(platonSale);
    }

}