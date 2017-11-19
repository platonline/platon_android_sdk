package com.platon.sdk.constant.api.action;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Actions to schedule payment (make payment regular)
 * <p>
 * SCHEDULE 			Creates schedule for recurring transactions
 * DESCHEDULE 			Disables schedule for recurring transactions
 * <p>
 * See also {@link PlatonAction}
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({
        PlatonAction.SCHEDULE,
        PlatonAction.DESCHEDULE,
})
public @interface PlatonScheduleAction {
}
