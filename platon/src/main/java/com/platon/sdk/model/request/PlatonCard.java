package com.platon.sdk.model.request;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Size;

import com.platon.sdk.constant.api.PlatonApiConstants;
import com.platon.sdk.constant.api.PlatonApiConstants.MethodProperties;
import com.platon.sdk.endpoint.adapter.post.PlatonSaleAdapter;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Card.MAX_CARD_NUMBER;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Card.MAX_CVV;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Card.MAX_MONTH;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Card.MAX_YEAR;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Card.MIN_CARD_NUMBER;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Card.MIN_CVV;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Card.MIN_MONTH;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Card.MIN_YEAR;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Card.MONTH_FORMAT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Card.MONTH_FORMAT_PATTERN;

/**
 * Request model that is used to store credit card data
 * <p>
 * See @{@link PlatonSaleAdapter} for usages
 */
public class PlatonCard implements Parcelable {

    /**
     * !!! FOR DEVELOPERS !!!
     */
    private final static String TEST_CARD_NUMBER = "4111111111111111";
    private final static String TEST_CARD_CVV = "411";

    /**
     * Test cards which simulates 4 types of result
     */
    public interface Test {
        /**
         * Successful sale with successful result
         */
        PlatonCard SUCCESS = new PlatonCard(TEST_CARD_NUMBER, 1, 2020, TEST_CARD_CVV);
        /**
         * Unsuccessful sale/recurring payment
         */
        PlatonCard UNSUCCESS = new PlatonCard(TEST_CARD_NUMBER, 2, 2020, TEST_CARD_CVV);
        /**
         * Successful sale after 3DS verification with a manual sending of the result (HTML form)
         */
        PlatonCard SUCCESS_3D = new PlatonCard(TEST_CARD_NUMBER, 5, 2020, TEST_CARD_CVV);
        /**
         * Unsuccessful sale after 3DS verification with a manual sending of the result (HTML form)
         */
        PlatonCard UNSUCCESS_3D = new PlatonCard(TEST_CARD_NUMBER, 6, 2020, TEST_CARD_CVV);
    }

    /**
     * CVV/CVC2 credit card verification
     * <p>
     * Value: 3-4 symbols
     * <p>
     * https://www.cvvnumber.com/cvv.html
     * <p>
     * See {@link PlatonApiConstants.MethodProperties#CARD_CVV2}
     */
    @NonNull
    @Size(min = MIN_CVV, max = MAX_CVV)
    private String mCvv2;

    /**
     * Credit Card
     * <p>
     * Value: Number
     * <p>
     * https://en.wikipedia.org/wiki/Payment_card_number
     * <p>
     * See {@link PlatonApiConstants.MethodProperties#CARD_NUMBER}
     */
    @NonNull
    @Size(min = MIN_CARD_NUMBER, max = MAX_CARD_NUMBER)
    private String mNumber;

    /**
     * Month of expiry of the credit card
     * <p>
     * Value: Month in the form XX (begin with 1)
     * <p>
     * See {@link MethodProperties#CARD_EXP_MONTH}
     */
    @IntRange(from = MIN_MONTH, to = MAX_MONTH)
    private int mExpireMonth;

    /**
     * Year of expiry of the credit card
     * <p>
     * Value: Year in the form XXXX
     * <p>
     * See {@link MethodProperties#CARD_EXP_YEAR}
     */
    @IntRange(from = MIN_YEAR, to = MAX_YEAR)
    private int mExpireYear;

    private PlatonCard() {

    }

    public PlatonCard(
            @NonNull @Size(min = MIN_CARD_NUMBER, max = MAX_CARD_NUMBER) final String number,
            @IntRange(from = MIN_MONTH, to = MAX_MONTH) final int expireMonth,
            @IntRange(from = MIN_YEAR, to = MAX_YEAR) final int expireYear,
            @NonNull @Size(min = MIN_CVV, max = MAX_CVV) final String cvv2
    ) {
        mNumber = number;
        mExpireMonth = expireMonth;
        mExpireYear = expireYear;
        mCvv2 = cvv2;
    }

    protected PlatonCard(final Parcel in) {
        mCvv2 = in.readString();
        mNumber = in.readString();
        mExpireMonth = in.readInt();
        mExpireYear = in.readInt();
    }

    public static final Creator<PlatonCard> CREATOR = new Creator<PlatonCard>() {
        @Override
        public PlatonCard createFromParcel(final Parcel in) {
            return new PlatonCard(in);
        }

        @Override
        public PlatonCard[] newArray(final int size) {
            return new PlatonCard[size];
        }
    };

    @NonNull
    @Size(min = MIN_CARD_NUMBER, max = MAX_CARD_NUMBER)
    public String getNumber() {
        return mNumber;
    }

    public void setNumber(@NonNull @Size(min = MIN_CARD_NUMBER, max = MAX_CARD_NUMBER) final String number) {
        mNumber = number;
    }

    public void setNumber(final long number) {
        mNumber = String.valueOf(number);
    }

    @IntRange(from = MIN_MONTH, to = MAX_MONTH)
    public int getExpireMonth() {
        return mExpireMonth;
    }

    public void setExpireMonth(@IntRange(from = MIN_MONTH, to = MAX_MONTH) final int expireMonth) {
        mExpireMonth = expireMonth;
    }

    @NonNull
    @Size(MONTH_FORMAT)
    public String getExpireMonthFormat() {
        return CardFormatter.getTwoDigit(mExpireMonth);
    }

    @IntRange(from = MIN_YEAR, to = MAX_YEAR)
    public int getExpireYear() {
        return mExpireYear;
    }

    public void setExpireYear(@IntRange(from = MIN_YEAR, to = MAX_YEAR) final int expireYear) {
        mExpireYear = expireYear;
    }

    @NonNull
    @Size(min = MIN_CVV, max = MAX_CVV)
    public String getCvv2() {
        return mCvv2;
    }

    public void setCvv2(@NonNull @Size(min = MIN_CVV, max = MAX_CVV) final String cvv2) {
        mCvv2 = cvv2;
    }

    public void setCvv2(final long cvv2) {
        mCvv2 = String.valueOf(cvv2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int i) {
        parcel.writeString(mCvv2);
        parcel.writeString(mNumber);
        parcel.writeInt(mExpireMonth);
        parcel.writeInt(mExpireYear);
    }

    /**
     * Stands for getting formatted output for {@link #mExpireMonth}
     */
    private final static class CardFormatter {

        private final static NumberFormat FORMAT_TWO_DIGIT_INT = new DecimalFormat(MONTH_FORMAT_PATTERN);

        @NonNull
        @Size(MONTH_FORMAT)
        private static String getTwoDigit(@IntRange(from = MIN_MONTH, to = MAX_MONTH) final int digit) {
            return FORMAT_TWO_DIGIT_INT.format(Math.max(MIN_MONTH, Math.min(MAX_MONTH, digit)));
        }
    }

    @Override
    public String toString() {
        return "PlatonCard{" +
                "mCvv2='" + mCvv2 + '\'' +
                ", mNumber='" + mNumber + '\'' +
                ", mExpireMonth=" + mExpireMonth +
                ", mExpireYear=" + mExpireYear +
                '}';
    }
}
