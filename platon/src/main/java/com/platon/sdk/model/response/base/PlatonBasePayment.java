package com.platon.sdk.model.response.base;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.platon.sdk.constant.api.PlatonResult;
import com.platon.sdk.constant.api.PlatonStatus;
import com.platon.sdk.constant.api.PlatonTransactionStatus;
import com.platon.sdk.constant.api.action.PlatonAction;
import com.platon.sdk.deserializer.PlatonBaseDeserializer;

import static com.platon.sdk.constant.api.PlatonApiConstants.SerializedNames.ACTION;
import static com.platon.sdk.constant.api.PlatonApiConstants.SerializedNames.ORDER_ID;
import static com.platon.sdk.constant.api.PlatonApiConstants.SerializedNames.RESULT;
import static com.platon.sdk.constant.api.PlatonApiConstants.SerializedNames.STATUS;
import static com.platon.sdk.constant.api.PlatonApiConstants.SerializedNames.TRANS_ID;

/**
 * Base model of Platon Payment Platform which hold data that included in all API responses
 * <p>
 * See {@link PlatonBaseDeserializer} and {@link PlatonBaseResponse} for details
 */
@SuppressWarnings("DanglingJavadoc")
public class PlatonBasePayment implements Parcelable {

    /**
     * action : CAPTURE
     * result : SUCCESS
     * trans_id : 03346-89217- 70541
     * order_id : ORDER-12345
     */

    /**
     * See {@link PlatonAction}
     */
    @PlatonAction
    @SerializedName(ACTION)
    protected String mAction;

    /**
     * See {@link PlatonResult}
     */
    @PlatonResult
    @SerializedName(RESULT)
    protected String mResult;

    /**
     * See {@link PlatonStatus} for main transaction and response
     * See {@link PlatonTransactionStatus} for sub transactions
     */
    @PlatonStatus
    @SerializedName(STATUS)
    protected String mStatus;

    /**
     * PlatonTransaction ID in the Client’s system
     */
    @SerializedName(ORDER_ID)
    protected String mOrderId;

    /**
     * PlatonTransaction ID in the Payment Platform
     * Usually can be used as main identifier when creating recurring and/or scheduled payments
     */
    @SerializedName(TRANS_ID)
    protected String mTransactionId;

    protected PlatonBasePayment(final Parcel in) {
        mAction = in.readString();
        mResult = in.readString();
        mStatus = in.readString();
        mOrderId = in.readString();
        mTransactionId = in.readString();
    }

    public static final Creator<PlatonBasePayment> CREATOR = new Creator<PlatonBasePayment>() {
        @Override
        public PlatonBasePayment createFromParcel(final Parcel in) {
            return new PlatonBasePayment(in);
        }

        @Override
        public PlatonBasePayment[] newArray(final int size) {
            return new PlatonBasePayment[size];
        }
    };

    @PlatonAction
    public String getAction() {
        return mAction;
    }

    public void setAction(@PlatonAction final String action) {
        mAction = action;
    }

    @PlatonResult
    public String getResult() {
        return mResult;
    }

    public void setResult(@PlatonResult final String result) {
        mResult = result;
    }

    @PlatonStatus
    String getStatus() {
        return mStatus;
    }

    void setStatus(@PlatonStatus final String status) {
        mStatus = status;
    }

    public String getOrderId() {
        return mOrderId;
    }

    public void setOrderId(final String orderId) {
        mOrderId = orderId;
    }

    public String getTransactionId() {
        return mTransactionId;
    }

    public void setTransactionId(final String transactionId) {
        mTransactionId = transactionId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int i) {
        parcel.writeString(mAction);
        parcel.writeString(mResult);
        parcel.writeString(mStatus);
        parcel.writeString(mOrderId);
        parcel.writeString(mTransactionId);
    }

    @NonNull
    @Override
    public String toString() {
        return "PlatonBasePayment{" +
                "mAction='" + mAction + '\'' +
                ", mResult='" + mResult + '\'' +
                ", mOrderId='" + mOrderId + '\'' +
                ", mTransactionId='" + mTransactionId + '\'' +
                '}';
    }
}
