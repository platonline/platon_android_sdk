package com.platon.sdk.util;

import android.support.annotation.Nullable;

import com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Set of Platon SDK util
 */
public class PlatonSdkUtil {

    private final static NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance(Locale.US);

    // Configure number formatter with Platon SDK amount format rules
    static {
        NUMBER_FORMAT.setGroupingUsed(false);
        NUMBER_FORMAT.setParseIntegerOnly(false);

        NUMBER_FORMAT.setMinimumFractionDigits(Amount.FRACTION_DIGITS);
        NUMBER_FORMAT.setMaximumFractionDigits(Amount.FRACTION_DIGITS);
        NUMBER_FORMAT.setMinimumIntegerDigits(Amount.MIN_INTEGER_DIGITS);
    }

    /**
     * @param amount - float amount
     * @return unified amount string by Platon SDK rules
     */
    public static String getAmountFormat(@Nullable final Float amount) {
        if (amount == null) return null;
        else {
            try {
                return NUMBER_FORMAT.format(amount);
            } catch (final Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

}