package com.platon.sdk.model.response.sale;

import android.os.Parcel;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.platon.sdk.callback.PlatonCaptureCallback;
import com.platon.sdk.constant.api.PlatonStatus;
import com.platon.sdk.deserializer.PlatonSaleDeserializer;
import com.platon.sdk.endpoint.adapter.post.PlatonSaleAdapter;

import static com.platon.sdk.constant.api.PlatonApiConstants.SerializedNames.DECLINE_REASON;

/**
 * Model which used in {@link PlatonSaleAdapter} in {@link PlatonSaleResponse} from {@link PlatonCaptureCallback}
 * which deserialize from {@link PlatonSaleDeserializer}
 */
@SuppressWarnings("DanglingJavadoc")
public class PlatonSaleDecline extends PlatonSale {

    /**
     * action : SALE
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

    protected PlatonSaleDecline(final Parcel in) {
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
        return "PlatonSaleDecline{" +
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
