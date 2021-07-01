package com.platon.sdk.model.response.google_pay;

import android.os.Parcel;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.platon.sdk.endpoint.adapter.post.PlatonGooglePayAdapter;
import com.platon.sdk.model.response.base.PlatonBasePayment;

import java.util.Date;

import static com.platon.sdk.constant.api.PlatonApiConstants.SerializedNames.TRANS_DATE;

/**
 * Base google pay model of response which used for extended google pay models and in callback of {@link PlatonGooglePayAdapter} requests
 */
@SuppressWarnings("DanglingJavadoc")
public class PlatonGooglePay extends PlatonBasePayment {

    /**
     * action : GOOGLEPAY
     * result : ACCEPTED
     * trans_id : 03346-89211-86461
     * order_id : ORDER-12345
     * trans_date : 2012-04-03 16:02:01
     */

    /**
     * PlatonTransaction date in the Payment Platform
     * <p>
     * Format: YYYY-MM-DD HH:mm:ss ("2012-04-03 16:02:02")
     */

    @SerializedName(TRANS_DATE)
    Date mTransactionDate;

    protected PlatonGooglePay(final Parcel in) {
        super(in);
        mTransactionDate = (Date) in.readSerializable();
    }

    public Date getTransactionDate() {
        return mTransactionDate;
    }

    public void setTransactionDate(final Date transactionDate) {
        mTransactionDate = transactionDate;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int i) {
        super.writeToParcel(parcel, i);
        parcel.writeSerializable(mTransactionDate);
    }

    @NonNull
    @Override
    public String toString() {
        return "PlatonGooglePay{" +
                "mAction='" + mAction + '\'' +
                ", mResult='" + mResult + '\'' +
                ", mOrderId='" + mOrderId + '\'' +
                ", mTransactionId='" + mTransactionId + '\'' +
                ", mTransactionDate='" + mTransactionDate + '\'' +
                '}';
    }
}
