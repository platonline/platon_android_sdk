package com.platon.sdk.constant.api;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Result – value that system returns on request
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({
        PlatonResult.SUCCESS,
        PlatonResult.DECLINED,
        PlatonResult.REDIRECT,
        PlatonResult.ACCEPTED,
        PlatonResult.ERROR
})
public @interface PlatonResult {

    /**
     * Action was successfully completed in Payment Platform
     */
    String SUCCESS = "SUCCESS";

    /**
     * Result of unsuccessful action in Payment Platform
     */
    String DECLINED = "DECLINED";

    /**
     * Additional action required from requester (redirect to 3ds)
     */
    String REDIRECT = "REDIRECT";

    /**
     * Action was accepted by Payment Platform, but will be completed later
     */
    String ACCEPTED = "ACCEPTED";

    /**
     * Request has errors and was not validated by Payment Platform
     */
    String ERROR = "ERROR";
}
