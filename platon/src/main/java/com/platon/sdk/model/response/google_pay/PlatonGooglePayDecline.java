package com.platon.sdk.model.response.google_pay;

import android.os.Parcel;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.platon.sdk.constant.api.PlatonStatus;
import com.platon.sdk.deserializer.PlatonGooglePayDeserializer;
import com.platon.sdk.endpoint.adapter.post.PlatonGooglePayAdapter;

import static com.platon.sdk.constant.api.PlatonApiConstants.SerializedNames.DECLINE_REASON;

/**
 * Model which used in {@link PlatonGooglePayAdapter} in {@link PlatonGooglePayResponse}
 * which deserialize from {@link PlatonGooglePayDeserializer}
 */
@SuppressWarnings("DanglingJavadoc")
public class PlatonGooglePayDecline extends PlatonGooglePay {

    /**
     * action : GOOGLEPAY
     * result : DECLINED
     * status : DECLINED
     * trans_id : 03346-89214-54141
     * order_id : ORDER-12345
     * trans_date : 2012-04-03 16:02:01
     * decline_reason : Declined by processing
     */

    /**
     * The reason why the transaction was declined
     * <p>
     * ex. "Declined by processing"
     */
    @SerializedName(DECLINE_REASON)
    private String mDeclineReason;

    protected PlatonGooglePayDecline(Parcel in) {
        super(in);
        mDeclineReason = in.readString();
    }

    @PlatonStatus
    public String getStatus() {
        return mStatus;
    }

    public void setStatus(@PlatonStatus final String status) {
        mStatus = status;
    }

    public String getDeclineReason() {
        return mDeclineReason;
    }

    public void setDeclineReason(final String declineReason) {
        mDeclineReason = declineReason;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(mDeclineReason);
    }

    @NonNull
    @Override
    public String toString() {
        return "PlatonGooglePayDecline{" +
                "mAction='" + mAction + '\'' +
                ", mResult='" + mResult + '\'' +
                ", mStatus='" + mStatus + '\'' +
                ", mOrderId='" + mOrderId + '\'' +
                ", mTransactionId='" + mTransactionId + '\'' +
                ", mTransactionDate='" + mTransactionDate + '\'' +
                ", mDeclineReason='" + mDeclineReason + '\'' +
                '}';
    }
}
