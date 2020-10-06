package com.platon.sdk.util;

import android.util.Log;

import androidx.annotation.Nullable;

import com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Set of Platon SDK util
 */
public class PlatonSdkUtil {

    private final static NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance(Locale.US);
    private final static String DATE_FORMAT = "yyyy-MM-dd";

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

    public static boolean isValidFormat(String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
            Log.e("PlatonSDK", "Invalid date format " + ex);
        }
        return date != null;
    }

    public static String getDateFormat(String value){
        if(isValidFormat(value)){
            return value;
        } else {
            return null;
        }
    }
}