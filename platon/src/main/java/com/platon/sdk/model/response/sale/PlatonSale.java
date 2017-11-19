package com.platon.sdk.model.response.sale;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;
import com.platon.sdk.endpoint.adapter.post.PlatonRecurringAdapter;
import com.platon.sdk.model.response.base.PlatonBasePayment;

import java.util.Date;

import static com.platon.sdk.constant.api.PlatonApiConstants.SerializedNames.TRANS_DATE;


/**
 * Base sale model of response which used for extended sale models and in callback of {@link PlatonRecurringAdapter} requests
 */
@SuppressWarnings("DanglingJavadoc")
public class PlatonSale extends PlatonBasePayment {

    /**
     * action : SALE
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

    protected PlatonSale(final Parcel in) {
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

    @Override
    public String toString() {
        return "PlatonSale{" +
                "mAction='" + mAction + '\'' +
                ", mResult='" + mResult + '\'' +
                ", mOrderId='" + mOrderId + '\'' +
                ", mTransactionId='" + mTransactionId + '\'' +
                ", mTransactionDate='" + mTransactionDate + '\'' +
                '}';
    }

}
