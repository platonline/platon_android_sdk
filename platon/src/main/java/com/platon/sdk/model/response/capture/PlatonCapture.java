package com.platon.sdk.model.response.capture;

import android.os.Parcel;

import androidx.annotation.NonNull;

import com.platon.sdk.constant.api.PlatonStatus;
import com.platon.sdk.endpoint.adapter.post.PlatonCaptureAdapter;
import com.platon.sdk.model.response.base.PlatonBasePayment;

/**
 * Payment model which used in {@link PlatonCaptureAdapter}
 */
public class PlatonCapture extends PlatonBasePayment {

    /**
     * action : CAPTURE
     * result : SUCCESS
     * status : SETTLED
     * trans_id : 03346-89217- 70541
     * order_id : ORDER-12345
     */

    protected PlatonCapture(final Parcel in) {
        super(in);
    }

    @PlatonStatus
    public String getStatus() {
        return mStatus;
    }

    public void setStatus(@PlatonStatus final String status) {
        mStatus = status;
    }

    @NonNull
    @Override
    public String toString() {
        return "PlatonCapture{" +
                "mAction='" + mAction + '\'' +
                ", mResult='" + mResult + '\'' +
                ", mStatus='" + mStatus + '\'' +
                ", mOrderId='" + mOrderId + '\'' +
                ", mTransactionId='" + mTransactionId + '\'' +
                '}';
    }

}
