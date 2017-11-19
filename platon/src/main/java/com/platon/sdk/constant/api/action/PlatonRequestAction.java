package com.platon.sdk.constant.api.action;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * When you make request to Payment Platform, you need to specify action, that needs to be done
 * <p>
 * SALE 				Creates SALE or AUTH transaction
 * CAPTURE 				Creates CAPTURE transaction
 * CREDITVOID 			Creates REVERSAL or REFUND transaction
 * GET_TRANS_STATUS 	Gets status of transaction in Payment Platform
 * GET_TRANS_DETAILS	Gets details of the order from Payment platform
 * RECURRING_SALE 		Creates SALE transaction using previously used cardholder data
 * SCHEDULE 			Creates schedule for recurring transactions
 * DESCHEDULE 			Disables schedule for recurring transactions
 * <p>
 * See also {@link PlatonAction}
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
        PlatonAction.DESCHEDULE
})
public @interface PlatonRequestAction {
}
