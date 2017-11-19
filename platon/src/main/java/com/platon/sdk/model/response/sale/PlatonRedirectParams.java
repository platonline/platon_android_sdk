package com.platon.sdk.model.response.sale;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import static com.platon.sdk.constant.api.PlatonApiConstants.SerializedNames.MD;
import static com.platon.sdk.constant.api.PlatonApiConstants.SerializedNames.PA_REQ;
import static com.platon.sdk.constant.api.PlatonApiConstants.SerializedNames.TERM_URL;

/**
 * Holder of specific 3DS redirect parameters
 * <p>
 * This model only included in {@link PlatonSale3DSecure} response
 */
public class PlatonRedirectParams implements Parcelable {

    /**
     * PaReq : bc5865698ae46de4eba4c51f0359a714
     * MD : 111111111111111111111
     * TermUrl : https://term_url.com/3ds/67c14e5?trans_id=03346-89225-87891&hash=8b98db60fb3c24c14a6d7075241da38b
     */

    @SerializedName(PA_REQ)
    private String mPaymentRequisites;

    @SerializedName(MD)
    private String mMd;

    @SerializedName(TERM_URL)
    private String mTermUrl;

    protected PlatonRedirectParams(final Parcel in) {
        mPaymentRequisites = in.readString();
        mMd = in.readString();
        mTermUrl = in.readString();
    }

    public static final Creator<PlatonRedirectParams> CREATOR = new Creator<PlatonRedirectParams>() {
        @Override
        public PlatonRedirectParams createFromParcel(final Parcel in) {
            return new PlatonRedirectParams(in);
        }

        @Override
        public PlatonRedirectParams[] newArray(final int size) {
            return new PlatonRedirectParams[size];
        }
    };

    public String getPaymentRequisites() {
        return mPaymentRequisites;
    }

    public void setPaymentRequisites(final String paymentRequisites) {
        mPaymentRequisites = paymentRequisites;
    }

    public String getMd() {
        return mMd;
    }

    public void setMd(final String md) {
        mMd = md;
    }

    public String getTermUrl() {
        return mTermUrl;
    }

    public void setTermUrl(final String termUrl) {
        mTermUrl = termUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int i) {
        parcel.writeString(mPaymentRequisites);
        parcel.writeString(mMd);
        parcel.writeString(mTermUrl);
    }

    @Override
    public String toString() {
        return "PlatonRedirectParams{" +
                "mPaymentRequisites='" + mPaymentRequisites + '\'' +
                ", mMd='" + mMd + '\'' +
                ", mTermUrl='" + mTermUrl + '\'' +
                '}';
    }
}
