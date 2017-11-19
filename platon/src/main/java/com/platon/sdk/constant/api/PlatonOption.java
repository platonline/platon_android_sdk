package com.platon.sdk.constant.api;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Helper @{@link StringDef} for requests
 * Used as convenient variable while creating different requests
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({
        PlatonOption.YES,
        PlatonOption.NO
})
public @interface PlatonOption {
    String YES = "Y";

    /**
     * Usually is default
     */
    String NO = "N";
}
