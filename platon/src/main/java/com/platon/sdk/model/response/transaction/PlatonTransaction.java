package com.platon.sdk.model.response.transaction;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.platon.sdk.constant.api.PlatonTransactionStatus;
import com.platon.sdk.constant.api.PlatonTransactionType;

import java.util.Date;

import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MAX_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MIN_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.SerializedNames.AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.SerializedNames.DATE;
import static com.platon.sdk.constant.api.PlatonApiConstants.SerializedNames.STATUS;
import static com.platon.sdk.constant.api.PlatonApiConstants.SerializedNames.TYPE;

/**
 * PlatonTransaction model which included in {@link PlatonTransactionDetails#//mPlatonTransactions} list
 */
@SuppressWarnings("DanglingJavadoc")
public class PlatonTransaction implements Parcelable {

    /**
     * date : 2012-01-01 01:10:25
     * type : AUTH
     * status : 1
     * amount : 1.95
     */

    /**
     * Date of transaction
     * <p>
     * Format: YYYY-MM-DD HH:mm:ss ("2012-02-06 01:11:030")
     */
    @SerializedName(DATE)
    private Date mDate;

    /**
     * Type of transaction
     * <p>
     * See {@link PlatonTransactionType}
     */
    @PlatonTransactionType
    @SerializedName(TYPE)
    private String mType;

    /**
     * Identify status of transaction
     * Can be either {@link PlatonTransactionStatus#SUCCESS} or {@link PlatonTransactionStatus#FAILURE}
     * <p>
     * See {@link PlatonTransactionStatus}
     */
    @PlatonTransactionStatus
    @SerializedName(STATUS)
    private int mStatus;

    /**
     * Amount of funds that is proceeded during transaction
     * <p>
     * Format: XXXX.XX (without leading zeros). Ex. Right - 234.97. Wrong - 0023.34, 234.345
     */
    @SerializedName(AMOUNT)
    private float mAmount;

    protected PlatonTransaction(final Parcel in) {
        mDate = (Date) in.readSerializable();
        mType = in.readString();
        mStatus = in.readInt();
        mAmount = in.readFloat();
    }

    public static final Creator<PlatonTransaction> CREATOR = new Creator<PlatonTransaction>() {
        @Override
        public PlatonTransaction createFromParcel(final Parcel in) {
            return new PlatonTransaction(in);
        }

        @Override
        public PlatonTransaction[] newArray(final int size) {
            return new PlatonTransaction[size];
        }
    };

    public Date getDate() {
        return mDate;
    }

    public void setDate(final Date date) {
        mDate = date;
    }

    @PlatonTransactionType
    public String getType() {
        return mType;
    }

    public void setType(@PlatonTransactionType final String type) {
        mType = type;
    }

    @PlatonTransactionStatus
    public int getStatus() {
        return mStatus;
    }

    public void setStatus(@PlatonTransactionStatus final int status) {
        mStatus = status;
    }

    @FloatRange(from = MIN_AMOUNT, to = MAX_AMOUNT)
    public float getAmount() {
        return mAmount;
    }

    public void setAmount(@FloatRange(from = MIN_AMOUNT, to = MAX_AMOUNT) final float amount) {
        mAmount = amount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int i) {
        parcel.writeSerializable(mDate);
        parcel.writeString(mType);
        parcel.writeInt(mStatus);
        parcel.writeFloat(mAmount);
    }

    @NonNull
    @Override
    public String toString() {
        return "PlatonTransaction{" +
                "mDate='" + mDate + '\'' +
                ", mType='" + mType + '\'' +
                ", mStatus=" + mStatus +
                ", mAmount=" + mAmount +
                '}';
    }
}
