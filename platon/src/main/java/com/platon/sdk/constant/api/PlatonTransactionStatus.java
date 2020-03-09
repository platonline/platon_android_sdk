package com.platon.sdk.constant.api;

import androidx.annotation.IntDef;

import com.platon.sdk.model.response.transaction.PlatonTransaction;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Used when fetch transaction data
 * <p>
 * Also see {@link PlatonTransaction}
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({
        PlatonTransactionStatus.FAILURE,
        PlatonTransactionStatus.SUCCESS
})
public @interface PlatonTransactionStatus {
    /**
     * Transaction was failed
     */
    int FAILURE = 0;
    /**
     * Transaction was successful
     */
    int SUCCESS = 1;
}
