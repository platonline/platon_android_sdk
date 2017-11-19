package com.platon.sdk.constant.api;

import android.support.annotation.StringDef;

import com.platon.sdk.constant.api.PlatonApiConstants.SerializedNames;
import com.platon.sdk.model.response.sale.PlatonSale3DSecure;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Helper {@link StringDef} for response
 * Used with {@link SerializedNames#REDIRECT_METHOD} and {@link PlatonSale3DSecure#mRedirectMethod}
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({
        PlatonRedirectMethod.GET,
        PlatonRedirectMethod.POST
})
public @interface PlatonRedirectMethod {
    String GET = "GET";
    String POST = "POST";
}
