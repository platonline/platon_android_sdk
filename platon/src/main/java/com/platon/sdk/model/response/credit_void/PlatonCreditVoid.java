package com.platon.sdk.model.response.credit_void;

import android.os.Parcel;

import androidx.annotation.NonNull;

import com.platon.sdk.callback.PlatonCaptureCallback;
import com.platon.sdk.deserializer.PlatonBaseDeserializer;
import com.platon.sdk.endpoint.adapter.post.PlatonCreditVoidAdapter;
import com.platon.sdk.model.response.base.PlatonBasePayment;
import com.platon.sdk.model.response.base.PlatonBaseResponse;

/**
 * Model which used in {@link PlatonCreditVoidAdapter} in {@link PlatonBaseResponse} from {@link PlatonCaptureCallback}
 * which deserialize from {@link PlatonBaseDeserializer}
 */
public class PlatonCreditVoid extends PlatonBasePayment {

    /**
     * action : CREDITVOID
     * result : ACCEPTED
     * trans_id : 03346-89211-86461
     * order_id : ORDER-12345
     */

    protected PlatonCreditVoid(final Parcel in) {
        super(in);
    }

    @NonNull
    @Override
    public String toString() {
        return "PlatonCreditVoid{" +
                "mAction='" + mAction + '\'' +
                ", mResult='" + mResult + '\'' +
                ", mOrderId='" + mOrderId + '\'' +
                ", mTransactionId='" + mTransactionId + '\'' +
                '}';
    }

}
