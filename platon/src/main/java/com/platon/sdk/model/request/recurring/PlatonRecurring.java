package com.platon.sdk.model.request.recurring;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Size;

import com.platon.sdk.constant.api.PlatonApiConstants.MethodProperties;
import com.platon.sdk.endpoint.adapter.post.PlatonRecurringAdapter;

import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT;

/**
 * Request model that is used to store recurring data
 * <p>
 * See @{@link PlatonRecurringAdapter} for usages
 */
public class PlatonRecurring implements Parcelable {

    /**
     * PlatonTransaction ID of the primary transaction in the Payment Platform
     * <p>
     * Format: Numbers in the form XXX
     * <p>
     * See @{@link MethodProperties#RECURRING_FIRST_TRANS_ID} for more details
     */
    @NonNull
    @Size(max = TEXT)
    String mFirstTransId;

    /**
     * Value obtained during the primary transaction
     * <p>
     * Format: Numbers in the form XXX
     * <p>
     * See @{@link MethodProperties#RECURRING_TOKEN} for more details
     */
    @NonNull
    String mToken;

    protected PlatonRecurring(final Parcel in) {
        mFirstTransId = in.readString();
        mToken = in.readString();
    }

    public PlatonRecurring(@NonNull @Size(max = TEXT) final String firstTransId, @NonNull final String token) {
        mFirstTransId = firstTransId;
        mToken = token;
    }

    public static final Creator<PlatonRecurring> CREATOR = new Creator<PlatonRecurring>() {
        @Override
        public PlatonRecurring createFromParcel(final Parcel in) {
            return new PlatonRecurring(in);
        }

        @Override
        public PlatonRecurring[] newArray(final int size) {
            return new PlatonRecurring[size];
        }
    };

    @Size(max = TEXT)
    @NonNull
    public String getFirstTransId() {
        return mFirstTransId;
    }

    public void setFirstTransId(@NonNull @Size(max = TEXT) final String firstTransId) {
        mFirstTransId = firstTransId;
    }

    @NonNull
    public String getToken() {
        return mToken;
    }

    public void setToken(@NonNull final String token) {
        mToken = token;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int i) {
        parcel.writeString(mFirstTransId);
        parcel.writeString(mToken);
    }

    @NonNull
    @Override
    public String toString() {
        return "PlatonRecurring{" +
                "mFirstTransId='" + mFirstTransId + '\'' +
                ", mToken='" + mToken + '\'' +
                '}';
    }
}
