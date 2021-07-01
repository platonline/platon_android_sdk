package com.platon.sdk.model.response.capture;

import android.os.Parcel;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.platon.sdk.callback.PlatonCaptureCallback;
import com.platon.sdk.deserializer.PlatonCaptureDeserializer;
import com.platon.sdk.endpoint.adapter.post.PlatonCaptureAdapter;

import static com.platon.sdk.constant.api.PlatonApiConstants.SerializedNames.DECLINE_REASON;

/**
 * Model which used in {@link PlatonCaptureAdapter} in {@link PlatonCaptureResponse} from {@link PlatonCaptureCallback}
 * which deserialize from {@link PlatonCaptureDeserializer}
 */
@SuppressWarnings("DanglingJavadoc")
public class PlatonCaptureDecline extends PlatonCapture {

    /**
     * action : CAPTURE
     * result : SUCCESS
     * status : SETTLED
     * trans_id : 03346-89217- 70541
     * order_id : ORDER-12345
     * decline_reason : Declined by processing
     */

    /**
     * The reason why the transaction was declined
     * <p>
     * ex. "Declined by processing"
     */
    @SerializedName(DECLINE_REASON)
    private String mDeclineReason;

    protected PlatonCaptureDecline(final Parcel in) {
        super(in);
        mDeclineReason = in.readString();
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
        return "PlatonCaptureDecline{" +
                "mAction='" + mAction + '\'' +
                ", mResult='" + mResult + '\'' +
                ", mStatus='" + mStatus + '\'' +
                ", mOrderId='" + mOrderId + '\'' +
                ", mTransactionId='" + mTransactionId + '\'' +
                ", mDeclineReason='" + mDeclineReason +
                '}';
    }

}
