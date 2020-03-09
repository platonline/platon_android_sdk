package com.platon.sdk.model.request.order;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Size;

import com.platon.sdk.constant.api.PlatonApiConstants.MethodProperties;
import com.platon.sdk.endpoint.adapter.post.PlatonScheduleAdapter;
import com.platon.sdk.model.response.transaction.PlatonTransaction;

import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MAX_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MIN_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.CURRENCY_CODE;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT_LONG;

/**
 * Request model that is used to store order data
 * Analogue of {@link PlatonTransaction} in Payment System
 * <p>
 * See {@link PlatonScheduleAdapter} for usages
 */
public class PlatonOrder implements Parcelable {

    /**
     * PlatonTransaction ID in the Clients system
     * <p>
     * Value: String up to 255 characters
     * <p>
     * See {@link MethodProperties#ORDER_ID}
     */
    @Size(max = TEXT)
    protected String mId;

    /**
     * The amount of the transaction
     * <p>
     * Value: Numbers in the form XXXX.XX (without leading zeros)
     * <p>
     * See {@link MethodProperties#ORDER_AMOUNT}
     */
    @FloatRange(from = MIN_AMOUNT, to = MAX_AMOUNT)
    protected float mAmount;

    /**
     * The amount of the transaction
     * <p>
     * Value: Currency 3-letter code (ISO 4217).
     * <p>
     * See {@link MethodProperties#ORDER_CURRENCY}
     */
    @Size(CURRENCY_CODE)
    protected String mCurrencyCode;

    /**
     * Description of the transaction (product name)
     * <p>
     * Value: String up to 1024 characters
     * <p>
     * See {@link MethodProperties#ORDER_DESCRIPTION}
     */
    @Size(max = TEXT_LONG)
    protected String mDescription;

    private PlatonOrder() {

    }

    public PlatonOrder(
            @FloatRange(from = MIN_AMOUNT, to = MAX_AMOUNT) final float amount,
            @NonNull @Size(max = TEXT_LONG) final String description
    ) {
        mAmount = amount;
        mDescription = description;
    }

    protected PlatonOrder(final Parcel in) {
        mId = in.readString();
        mAmount = in.readFloat();
        mCurrencyCode = in.readString();
        mDescription = in.readString();
    }

    public static final Creator<PlatonOrder> CREATOR = new Creator<PlatonOrder>() {
        @Override
        public PlatonOrder createFromParcel(final Parcel in) {
            return new PlatonOrder(in);
        }

        @Override
        public PlatonOrder[] newArray(final int size) {
            return new PlatonOrder[size];
        }
    };

    @Size(max = TEXT)
    protected String getId() {
        return mId;
    }

    protected void setId(@Size(max = TEXT) final String id) {
        mId = id;
    }

    @FloatRange(from = MIN_AMOUNT, to = MAX_AMOUNT)
    public float getAmount() {
        return mAmount;
    }

    public void setAmount(@FloatRange(from = MIN_AMOUNT, to = MAX_AMOUNT) final float amount) {
        mAmount = amount;
    }

    @Size(CURRENCY_CODE)
    protected String getCurrencyCode() {
        return mCurrencyCode;
    }

    protected void setCurrencyCode(@Size(CURRENCY_CODE) final String currencyCode) {
        mCurrencyCode = currencyCode;
    }

    @NonNull
    @Size(max = TEXT_LONG)
    public String getDescription() {
        return mDescription;
    }

    public void setDescription(@NonNull @Size(max = TEXT_LONG) final String description) {
        mDescription = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int i) {
        parcel.writeString(mId);
        parcel.writeFloat(mAmount);
        parcel.writeString(mCurrencyCode);
        parcel.writeString(mDescription);
    }

    @Override
    public String toString() {
        return "PlatonOrder{" +
                "mAmount=" + mAmount +
                ", mDescription='" + mDescription + '\'' +
                '}';
    }
}
