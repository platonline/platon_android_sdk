package com.platon.sdk.model.response.google_pay;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;
import com.platon.sdk.constant.api.PlatonApiConstants.SerializedNames;
import com.platon.sdk.constant.api.PlatonStatus;
import com.platon.sdk.deserializer.PlatonGooglePayDeserializer;
import com.platon.sdk.endpoint.adapter.post.PlatonGooglePayAdapter;
import com.platon.sdk.model.request.order.PlatonOrder;

import static com.platon.sdk.constant.api.PlatonApiConstants.SerializedNames.DESCRIPTOR;

/**
 * Model which used in {@link PlatonGooglePayAdapter} in {@link PlatonGooglePayResponse}
 * which deserialize from {@link PlatonGooglePayDeserializer}
 */
@SuppressWarnings("DanglingJavadoc")
public class PlatonGooglePaySuccess extends PlatonGooglePay {

    /**
     * action : GOOGLEPAY
     * result : SUCCESS
     * status : SETTLED
     * trans_id : 03346-89217- 70541
     * order_id : ORDER-12345
     * descriptor : test
     * trans_date : 2012-04-03 16:02:01
     * amount : 1.95
     * currency : UAH
     */

    /**
     * This is a string which the owner of the credit card will see in the statement from the bank
     * In most cases, this is the Customers support web-site
     */
    @SerializedName(DESCRIPTOR)
    String mDescriptor;

    /**
     * Order amount
     * <p>
     * See {@link PlatonOrder} for the details
     */
    @SerializedName(SerializedNames.AMOUNT)
    float mAmount;

    /**
     * Order currency
     * <p>
     * See {@link PlatonOrder} for the details
     */
    @SerializedName(SerializedNames.CURRENCY)
    String mCurrency;

    protected PlatonGooglePaySuccess(Parcel in) {
        super(in);
        mDescriptor = in.readString();
        mAmount = in.readFloat();
        mCurrency = in.readString();
    }

    @PlatonStatus
    public String getStatus() {
        return mStatus;
    }

    public void setStatus(@PlatonStatus final String status) {
        mStatus = status;
    }

    public String getDescriptor() {
        return mDescriptor;
    }

    public void setDescriptor(final String descriptor) {
        mDescriptor = descriptor;
    }

    public float getAmount() {
        return mAmount;
    }

    public void setAmount(final float amount) {
        mAmount = amount;
    }

    public String getCurrency() {
        return mCurrency;
    }

    public void setCurrency(final String currency) {
        mCurrency = currency;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(mDescriptor);
        parcel.writeFloat(mAmount);
        parcel.writeString(mCurrency);
    }

    @Override
    public String toString() {
        return "PlatonGooglePaySuccess{" +
                "mAction='" + mAction + '\'' +
                ", mResult='" + mResult + '\'' +
                ", mStatus='" + mStatus + '\'' +
                ", mOrderId='" + mOrderId + '\'' +
                ", mTransactionId='" + mTransactionId + '\'' +
                ", mTransactionDate='" + mTransactionDate + '\'' +
                ", mDescriptor='" + mDescriptor + '\'' +
                ", mAmount=" + mAmount +
                ", mCurrency='" + mCurrency + '\'' +
                '}';
    }
}
