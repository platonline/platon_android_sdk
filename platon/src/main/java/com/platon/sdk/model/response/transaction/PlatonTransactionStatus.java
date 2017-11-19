package com.platon.sdk.model.response.transaction;

import android.os.Parcel;

import com.platon.sdk.callback.PlatonBaseCallback;
import com.platon.sdk.constant.api.PlatonStatus;
import com.platon.sdk.deserializer.PlatonBaseDeserializer;
import com.platon.sdk.endpoint.adapter.post.PlatonTransactionAdapter;
import com.platon.sdk.model.response.base.PlatonBasePayment;
import com.platon.sdk.model.response.base.PlatonBaseResponse;

/**
 * Model which used in {@link PlatonTransactionAdapter} in {@link PlatonBaseResponse} from {@link PlatonBaseCallback}
 * which deserialize from {@link PlatonBaseDeserializer}
 */
public class PlatonTransactionStatus extends PlatonBasePayment {

    /**
     * action : GET_TRANS_STATUS
     * result : SUCCESS
     * status : PENDING
     * trans_id : 03346-89211-86461
     * order_id : ORDER-12345
     */

    protected PlatonTransactionStatus(final Parcel in) {
        super(in);
    }

    @PlatonStatus
    public String getStatus() {
        return mStatus;
    }

    public void setStatus(@PlatonStatus final String status) {
        mStatus = status;
    }

    @Override
    public String toString() {
        return "PlatonTransactionStatus{" +
                "mAction='" + mAction + '\'' +
                ", mResult='" + mResult + '\'' +
                ", mStatus='" + mStatus + '\'' +
                ", mOrderId='" + mOrderId + '\'' +
                ", mTransactionId='" + mTransactionId +
                '}';
    }

}
