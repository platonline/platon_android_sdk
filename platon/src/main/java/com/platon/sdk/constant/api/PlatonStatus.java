package com.platon.sdk.constant.api;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Status – actual status of transaction in Payment Platform
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({
        PlatonStatus.SECURE_3D,
        PlatonStatus.PENDING,
        PlatonStatus.SETTLED,
        PlatonStatus.REVERSAL,
        PlatonStatus.REFUND,
        PlatonStatus.CHARGEBACK,
        PlatonStatus.SECOND_PRESENTMENT,
        PlatonStatus.SECOND_CHARGEBACK,
        PlatonStatus.DECLINED,
        PlatonStatus.ENABLED,
        PlatonStatus.DISABLED
})
public @interface PlatonStatus {

    /**
     * The transaction awaits 3D-Secure validation
     */
    String SECURE_3D = "3DS";

    /**
     * The transaction awaits CAPTURE
     */
    String PENDING = "PENDING";

    /**
     * Successful transaction
     */
    String SETTLED = "SETTLED";

    /**
     * Transaction for which reversal was made
     */
    String REVERSAL = "REVERSAL";

    /**
     * Transaction for which refund was made
     */
    String REFUND = "REFUND";

    /**
     * Transaction for which chargeback was made
     */
    String CHARGEBACK = "CHARGEBACK";

    /**
     * Transaction for which second presentment was made
     */
    String SECOND_PRESENTMENT = "SECOND_PRESENTMENT";

    /**
     * Transaction for which second chargeback was made
     */
    String SECOND_CHARGEBACK = "SECOND_CHARGEBACK";

    /**
     * Not successful transaction
     */
    String DECLINED = "DECLINED";

    /**
     * Enabled scheduling option for order
     */
    String ENABLED = "ENABLED";

    /**
     * Disabled scheduling option for order (deschedule)
     */
    String DISABLED = "DISABLED";

}
