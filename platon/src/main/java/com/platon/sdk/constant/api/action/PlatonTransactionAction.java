package com.platon.sdk.constant.api.action;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Actions to get transaction additional info or check status, etc.
 * <p>
 * GET_TRANS_STATUS 	Gets status of transaction in Payment Platform
 * GET_TRANS_DETAILS	Gets details of the order from Payment platform
 * <p>
 * See also {@link PlatonAction}
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({
        PlatonAction.GET_TRANS_STATUS,
        PlatonAction.GET_TRANS_DETAILS,
})
public @interface PlatonTransactionAction {
}
