package com.platon.sdk.constant.api.action;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Following actions can’t be made by request, they’re created by Payment Platform in certain
 * circumstances (e.g. issuer initiated chargeback) and you receive callback as a result.
 * <p>
 * CHARGEBACK	 		CHARGEBACK transaction was created in Payment Platform
 * SECOND_PRESENTMENT	SECOND_PRESENTMENT transaction was created in Payment Platform
 * SECOND_CHARGEBACK 	SECOND_CHARGEBACK (PRE ARBITRATION) transaction was created in Payment Platform
 * <p>
 * See also {@link PlatonAction}
 * <p>
 * https://chargeback.com/what-is-a-chargeback/
 * https://chargeback.com/chargeback-process/
 * https://chargebacks911.com/chargebacks/
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({
        PlatonAction.CHARGEBACK,
        PlatonAction.SECOND_PRESENTMENT,
        PlatonAction.SECOND_CHARGEBACK
})
public @interface PlatonResponseAction {
}
