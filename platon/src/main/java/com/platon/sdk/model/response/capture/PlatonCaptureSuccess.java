package com.platon.sdk.model.response.capture;

import android.os.Parcel;

import androidx.annotation.NonNull;

import com.platon.sdk.callback.PlatonCaptureCallback;
import com.platon.sdk.deserializer.PlatonCaptureDeserializer;
import com.platon.sdk.endpoint.adapter.post.PlatonCaptureAdapter;

/**
 * Model which used in {@link PlatonCaptureAdapter} in {@link PlatonCaptureResponse} from {@link PlatonCaptureCallback}
 * which deserialize from {@link PlatonCaptureDeserializer}
 */
public class PlatonCaptureSuccess extends PlatonCapture {

    /**
     * action : CAPTURE
     * result : SUCCESS
     * status : SETTLED
     * trans_id : 03346-89217- 70541
     * order_id : ORDER-12345
     */

    protected PlatonCaptureSuccess(final Parcel in) {
        super(in);
    }

    @NonNull
    @Override
    public String toString() {
        return "PlatonCaptureSuccess{" +
                "mAction='" + mAction + '\'' +
                ", mResult='" + mResult + '\'' +
                ", mStatus='" + mStatus + '\'' +
                ", mOrderId='" + mOrderId + '\'' +
                ", mTransactionId='" + mTransactionId + '\'' +
                '}';
    }

}
