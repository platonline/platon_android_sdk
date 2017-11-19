package com.platon.sdk.constant.api;

import android.support.annotation.StringDef;

import com.platon.sdk.model.response.transaction.PlatonTransaction;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Used when fetch transaction data
 * <p>
 * Also see {@link PlatonTransaction}
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({
        PlatonTransactionType.INIT,
        PlatonTransactionType.THREE_DS,
        PlatonTransactionType.SALE,
        PlatonTransactionType.AUTH,
        PlatonTransactionType.CAPTURE,
        PlatonTransactionType.REVERSAL,
        PlatonTransactionType.REFUND,
        PlatonTransactionType.CHARGEBACK,
        PlatonTransactionType.SECOND_PRESENTMENT,
        PlatonTransactionType.SECOND_CHARGEBACK
})
public @interface PlatonTransactionType {

    /**
     * Tech status when user will its data on payment page
     */
    String INIT = "INIT";

    /**
     * 3DS verification card sending
     */
    String THREE_DS = "3DS";

    /**
     * Status for transaction with successful immediate payment
     */
    String SALE = "SALE";

    /**
     * Transaction of authentication only without capturing. Customer may authenticate many transaction
     * (formed in batch) before they will be captured. First stage of Dual Message System (DMS).
     * On authorization stage in processing
     */
    String AUTH = "AUTH";

    /**
     * Transaction of payment capturing during second phase of DMS. Funds is transferred from Issuer Bank account
     * through Acquiring Bank down to Merchant Commercial Bank Account
     * On successful approval by manager
     */
    String CAPTURE = "CAPTURE";

    /**
     * Transaction of successfully transferring hold money back
     */
    String REVERSAL = "REVERSAL";

    /**
     * Transaction of refunding cost
     */
    String REFUND = "REFUND";

    /**
     * Holds that this dispute transaction at the stage of consideration (prior to obtaining evidences and documents)
     */
    String CHARGEBACK = "CHARGEBACK";

    /**
     * Holds that this dispute transaction at the stage of consideration (prior to obtaining evidences and documents)
     */
    String SECOND_PRESENTMENT = "SECOND PRESENTMENT";

    /**
     * Second request for refund OR transaction is on arbitrary stage (dispute on "dispute transaction")
     */
    String SECOND_CHARGEBACK = "SECOND CHARGEBACK";

}
