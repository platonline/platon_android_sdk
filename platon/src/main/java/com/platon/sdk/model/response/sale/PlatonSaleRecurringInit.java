package com.platon.sdk.model.response.sale;

import android.os.Parcel;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.platon.sdk.callback.PlatonCaptureCallback;
import com.platon.sdk.constant.api.PlatonStatus;
import com.platon.sdk.deserializer.PlatonSaleDeserializer;
import com.platon.sdk.endpoint.adapter.post.PlatonSaleAdapter;

import static com.platon.sdk.constant.api.PlatonApiConstants.SerializedNames.RECURRING_TOKEN;


/**
 * Model which used in {@link PlatonSaleAdapter} in {@link PlatonSaleResponse} from {@link PlatonCaptureCallback}
 * which deserialize from {@link PlatonSaleDeserializer}
 */
@SuppressWarnings("DanglingJavadoc")
public class PlatonSaleRecurringInit extends PlatonSaleSuccess {

    /**
     * action : SALE
     * result : SUCCESS
     * status : SETTLED
     * trans_id : 03346-89217- 70541
     * order_id : ORDER-12345
     * descriptor : test
     * trans_date : 2012-04-03 16:02:01
     * recurring_token : a1a6de416405ada72bb47a49176471dc
     */

    /**
     * PlatonRecurring token (get if account support recurring sales and transaction was initialized for recurring)
     */
    @SerializedName(RECURRING_TOKEN)
    private String mRecurringToken;

    protected PlatonSaleRecurringInit(final Parcel in) {
        super(in);
        mRecurringToken = in.readString();
    }

    @PlatonStatus
    public String getStatus() {
        return mStatus;
    }

    public void setStatus(@PlatonStatus final String status) {
        mStatus = status;
    }

    public String getRecurringToken() {
        return mRecurringToken;
    }

    public void setRecurringToken(final String recurringToken) {
        mRecurringToken = recurringToken;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(mRecurringToken);
    }

    @NonNull
    @Override
    public String toString() {
        return "PlatonSaleRecurringInit{" +
                "mAction='" + mAction + '\'' +
                ", mResult='" + mResult + '\'' +
                ", mStatus='" + mStatus + '\'' +
                ", mOrderId='" + mOrderId + '\'' +
                ", mTransactionId='" + mTransactionId + '\'' +
                ", mTransactionDate='" + mTransactionDate + '\'' +
                ", mDescriptor='" + mDescriptor + '\'' +
                ", mRecurringToken='" + mRecurringToken + '\'' +
                '}';
    }
}
