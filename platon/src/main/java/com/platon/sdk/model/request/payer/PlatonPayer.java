package com.platon.sdk.model.request.payer;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Size;

import com.platon.sdk.constant.api.PlatonApiConstants.Formats.State;
import com.platon.sdk.constant.api.PlatonApiConstants.MethodProperties;
import com.platon.sdk.endpoint.adapter.post.PlatonSaleAdapter;

import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT_SHORT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Payer.COUNTRY_CODE;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Payer.EMAIL;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Payer.MAX_IP;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Payer.MIN_IP;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.State.STATE;

/**
 * Request model that is used to store payer data
 * <p>
 * See @{@link PlatonSaleAdapter} for usages
 */
class PlatonPayer implements Parcelable {

    /**
     * Customer’s first name
     * <p>
     * Value: String up to 32 characters
     * <p>
     * See {@link MethodProperties#PAYER_FIRST_NAME}
     */
    @Size(max = TEXT_SHORT)
    String mFirstName;

    /**
     * Customer’s surname
     * <p>
     * Value: String up to 32 characters
     * <p>
     * See {@link MethodProperties#PAYER_LAST_NAME}
     */
    @Size(max = TEXT_SHORT)
    String mLastName;

    /**
     * Customer’s address
     * <p>
     * Value: String up to 255 characters
     * <p>
     * See {@link MethodProperties#PAYER_ADDRESS}
     */
    @Size(max = TEXT)
    String mAddress;

    /**
     * Customer’s country
     * <p>
     * Value: country 2-letter code (ISO 3166-1 alpha-2)
     * <p>
     * See {@link MethodProperties#PAYER_COUNTRY}
     */
    @Size(COUNTRY_CODE)
    String mCountryCode;

    /**
     * Customer’s state
     * <p>
     * Value: 2-letter code (or {@link State#NA} for countries without states)
     * <p>
     * See {@link MethodProperties#PAYER_STATE}
     */
    @Size(STATE)
    String mState;

    /**
     * Customer’s city
     * <p>
     * Value: String up to 32 characters
     * <p>
     * See {@link MethodProperties#PAYER_CITY}
     */
    @Size(max = TEXT_SHORT)
    String mCity;

    /**
     * ZIP-code of the Customer
     * <p>
     * Value: String up to 32 characters
     * <p>
     * See {@link MethodProperties#PAYER_ZIP}
     */
    @Size(max = TEXT_SHORT)
    String mZip;

    /**
     * Customer’s email
     * <p>
     * Value: String up to 256 characters
     * <p>
     * See {@link MethodProperties#PAYER_EMAIL}
     */
    @Size(max = EMAIL)
    String mEmail;

    /**
     * Customer’s phone
     * <p>
     * Value: String up to 32 characters
     * <p>
     * See {@link MethodProperties#PAYER_PHONE}
     */
    @Size(max = TEXT_SHORT)
    String mPhone;

    /**
     * IP-address of the Customer
     * <p>
     * Value: Format XXX.XXX.XXX.XXX
     * <p>
     * ex. min 1.0.0.0, max = 123.123.123.123
     * <p>
     * See {@link MethodProperties#PAYER_IP}
     */
    @Size(min = MIN_IP, max = MAX_IP)
    String mIpAddress;

    PlatonPayer() {

    }

    PlatonPayer(final Parcel in) {
        mFirstName = in.readString();
        mLastName = in.readString();
        mAddress = in.readString();
        mCountryCode = in.readString();
        mState = in.readString();
        mCity = in.readString();
        mZip = in.readString();
        mEmail = in.readString();
        mPhone = in.readString();
        mIpAddress = in.readString();
    }

    public static final Creator<PlatonPayer> CREATOR = new Creator<PlatonPayer>() {
        @Override
        public PlatonPayer createFromParcel(final Parcel in) {
            return new PlatonPayer(in);
        }

        @Override
        public PlatonPayer[] newArray(final int size) {
            return new PlatonPayer[size];
        }
    };

    @Size(max = TEXT_SHORT)
    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(@Size(max = TEXT_SHORT) final String firstName) {
        mFirstName = firstName;
    }

    @Size(max = TEXT_SHORT)
    public String getLastName() {
        return mLastName;
    }

    public void setLastName(@Size(max = TEXT_SHORT) final String lastName) {
        mLastName = lastName;
    }

    @Size(max = TEXT)
    public String getAddress() {
        return mAddress;
    }

    public void setAddress(@Size(max = TEXT) final String address) {
        mAddress = address;
    }

    @Size(COUNTRY_CODE)
    public String getCountryCode() {
        return mCountryCode;
    }

    public void setCountryCode(@Size(COUNTRY_CODE) final String countryCode) {
        mCountryCode = countryCode;
    }

    @Size(STATE)
    public String getState() {
        return mState;
    }

    public void setState(@Size(STATE) final String state) {
        mState = state;
    }

    @Size(max = TEXT_SHORT)
    public String getCity() {
        return mCity;
    }

    public void setCity(@Size(max = TEXT_SHORT) final String city) {
        mCity = city;
    }

    @Size(max = TEXT_SHORT)
    public String getZip() {
        return mZip;
    }

    public void setZip(@Size(max = TEXT_SHORT) final String zip) {
        mZip = zip;
    }

    @Size(max = EMAIL)
    public String getEmail() {
        return mEmail;
    }

    public void setEmail(@Size(max = EMAIL) final String email) {
        mEmail = email;
    }

    @Size(max = TEXT_SHORT)
    public String getPhone() {
        return mPhone;
    }

    public void setPhone(@Size(max = TEXT_SHORT) final String phone) {
        mPhone = phone;
    }

    @Size(min = MIN_IP, max = MAX_IP)
    String getIpAddress() {
        return mIpAddress;
    }

    void setIpAddress(@Size(min = MIN_IP, max = MAX_IP) final String ipAddress) {
        mIpAddress = ipAddress;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int i) {
        parcel.writeString(mFirstName);
        parcel.writeString(mLastName);
        parcel.writeString(mAddress);
        parcel.writeString(mCountryCode);
        parcel.writeString(mState);
        parcel.writeString(mCity);
        parcel.writeString(mZip);
        parcel.writeString(mEmail);
        parcel.writeString(mPhone);
        parcel.writeString(mIpAddress);
    }

    @NonNull
    @Override
    public String toString() {
        return "PlatonPayer{" +
                "mFirstName='" + mFirstName + '\'' +
                ", mLastName='" + mLastName + '\'' +
                ", mAddress='" + mAddress + '\'' +
                ", mCountryCode='" + mCountryCode + '\'' +
                ", mState='" + mState + '\'' +
                ", mCity='" + mCity + '\'' +
                ", mZip='" + mZip + '\'' +
                ", mEmail='" + mEmail + '\'' +
                ", mPhone='" + mPhone + '\'' +
                ", mIpAddress='" + mIpAddress + '\'' +
                '}';
    }
}
