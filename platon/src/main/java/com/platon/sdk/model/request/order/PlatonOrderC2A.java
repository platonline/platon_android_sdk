package com.platon.sdk.model.request.order;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Size;

import com.platon.sdk.constant.api.PlatonApiConstants;
import com.platon.sdk.endpoint.adapter.post.PlatonSaleAdapter;
import com.platon.sdk.endpoint.adapter.web.PlatonWebC2AAdapter;

import org.jetbrains.annotations.NotNull;

import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MAX_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MIN_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.CURRENCY_CODE;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT_LONG;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT_SHORT;

/**
 * Request model that extend {@link PlatonOrder} and used in {@link PlatonWebC2AAdapter}
 */
public class PlatonOrderC2A implements Parcelable {

    /**
     * The amount of the transaction
     * <p>
     * Value: Numbers in the form XXXX.XX (without leading zeros)
     * <p>
     * See {@link PlatonApiConstants.MethodProperties#ORDER_AMOUNT}
     */
    protected float mAmount;

    /**
     * The amount of the transaction
     * <p>
     * Value: Currency 3-letter code (ISO 4217).
     * <p>
     * See {@link PlatonApiConstants.MethodProperties#ORDER_CURRENCY}
     */
    @Size(CURRENCY_CODE)
    protected String mCurrencyCode;

    /**
     * Description of the transaction (product name)
     * <p>
     * Value: String up to 1024 characters
     * <p>
     * See {@link PlatonApiConstants.MethodProperties#ORDER_DESCRIPTION}
     */
    @Size(max = TEXT)
    protected String mDescription;

    private PlatonOrderC2A() {

    }

    public PlatonOrderC2A(
            @FloatRange(from = MIN_AMOUNT, to = MAX_AMOUNT) final float amount,
            @NonNull @Size(CURRENCY_CODE) final String currencyCode,
            @NonNull @Size(max = TEXT) final String description
    ) {
        mAmount = amount;
        mCurrencyCode = currencyCode;
        mDescription = description;
    }

    protected PlatonOrderC2A(final Parcel in) {
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

    @FloatRange(from = MIN_AMOUNT, to = MAX_AMOUNT)
    public float getAmount() {
        return mAmount;
    }

    public void setAmount(@FloatRange(from = MIN_AMOUNT, to = MAX_AMOUNT) final float amount) {
        mAmount = amount;
    }

    @Size(CURRENCY_CODE)
    public String getCurrencyCode() {
        return mCurrencyCode;
    }

    public void setCurrencyCode(@Size(CURRENCY_CODE) final String currencyCode) {
        mCurrencyCode = currencyCode;
    }

    @NonNull
    @Size(max = TEXT)
    public String getDescription() {
        return mDescription;
    }

    public void setDescription(@NonNull @Size(max = TEXT) final String description) {
        mDescription = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int i) {
        parcel.writeFloat(mAmount);
        parcel.writeString(mCurrencyCode);
        parcel.writeString(mDescription);
    }

    @NotNull
    @Override
    public String toString() {
        return "PlatonOrderC2A{" +
                "mAmount=" + mAmount +
                ", mCurrencyCode='" + mCurrencyCode + '\'' +
                ", mDescription='" + mDescription + '\'' +
                '}';
    }
}