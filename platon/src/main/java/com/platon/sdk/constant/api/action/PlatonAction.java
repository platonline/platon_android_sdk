package com.platon.sdk.constant.api.action;

import androidx.annotation.StringDef;

import com.platon.sdk.constant.api.PlatonApiConstants.MethodProperties;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * When you make request to Payment Platform, you need to specify action, that needs to be done
 * <p>
 * Note: that last 3 actions can’t be made by request, they’re created by Payment Platform in
 * certain circumstances (e.g. issuer initiated chargeback) and you receive callback as a
 * result
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({
        PlatonAction.SALE,
        PlatonAction.CAPTURE,
        PlatonAction.CREDITVOID,
        PlatonAction.GET_TRANS_STATUS,
        PlatonAction.GET_TRANS_DETAILS,
        PlatonAction.RECURRING_SALE,
        PlatonAction.SCHEDULE,
        PlatonAction.DESCHEDULE,
        PlatonAction.CHARGEBACK,
        PlatonAction.SECOND_PRESENTMENT,
        PlatonAction.SECOND_CHARGEBACK,
})
public @interface PlatonAction {

    /**
     * Creates SALE or {@link MethodProperties#AUTH} transaction
     */
    String SALE = "SALE";

    /**
     * Creates CAPTURE transaction
     */
    String CAPTURE = "CAPTURE";

    /**
     * Creates REVERSAL or REFUND transaction
     */
    String CREDITVOID = "CREDITVOID";

    /**
     * Gets status of transaction in Payment Platform
     */
    String GET_TRANS_STATUS = "GET_TRANS_STATUS";

    /**
     * Gets details of the order from Payment platform
     */
    String GET_TRANS_DETAILS = "GET_TRANS_DETAILS";

    /**
     * Creates SALE transaction using previously used cardholder data
     */
    String RECURRING_SALE = "RECURRING_SALE";

    /**
     * Creates schedule for recurring transactions
     */
    String SCHEDULE = "SCHEDULE";

    /**
     * Disables schedule for recurring transactions
     */
    String DESCHEDULE = "DESCHEDULE";

    /**
     * CHARGEBACK transaction was created in Payment Platform
     */
    String CHARGEBACK = "CHARGEBACK";

    /**
     * SECOND_PRESENTMENT transaction was created in Payment Platform
     */
    String SECOND_PRESENTMENT = "SECOND_PRESENTMENT";

    /**
     * SECOND_CHARGEBACK transaction was created in Payment Platform
     */
    String SECOND_CHARGEBACK = "SECOND_CHARGEBACK";
}
