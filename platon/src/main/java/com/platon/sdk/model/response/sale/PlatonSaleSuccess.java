package com.platon.sdk.model.response.sale;

import android.os.Parcel;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.platon.sdk.callback.PlatonCaptureCallback;
import com.platon.sdk.constant.api.PlatonStatus;
import com.platon.sdk.deserializer.PlatonSaleDeserializer;
import com.platon.sdk.endpoint.adapter.post.PlatonSaleAdapter;

import static com.platon.sdk.constant.api.PlatonApiConstants.SerializedNames.DESCRIPTOR;

/**
 * Model which used in {@link PlatonSaleAdapter} in {@link PlatonSaleResponse} from {@link PlatonCaptureCallback}
 * which deserialize from {@link PlatonSaleDeserializer}
 */
@SuppressWarnings("DanglingJavadoc")
public class PlatonSaleSuccess extends PlatonSale {

    /**
     * action : SALE
     * result : SUCCESS
     * status : SETTLED
     * trans_id : 03346-89217- 70541
     * order_id : ORDER-12345
     * descriptor : test
     * trans_date : 2012-04-03 16:02:01
     */

    /**
     * This is a string which the owner of the credit card will see in the statement from the bank
     * In most cases, this is the Customers support web-site
     */
    @SerializedName(DESCRIPTOR)
    String mDescriptor;

    protected PlatonSaleSuccess(final Parcel in) {
        super(in);
        mDescriptor = in.readString();
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

    @Override
    public void writeToParcel(final Parcel parcel, final int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(mDescriptor);
    }

    @NonNull
    @Override
    public String toString() {
        return "PlatonSaleSuccess{" +
                "mAction='" + mAction + '\'' +
                ", mResult='" + mResult + '\'' +
                ", mStatus='" + mStatus + '\'' +
                ", mOrderId='" + mOrderId + '\'' +
                ", mTransactionId='" + mTransactionId + '\'' +
                ", mTransactionDate='" + mTransactionDate + '\'' +
                ", mDescriptor='" + mDescriptor + '\'' +
                '}';
    }
}
