package com.platon.sdk.model.request.payer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Size;

import com.platon.sdk.constant.api.PlatonApiConstants;
import com.platon.sdk.endpoint.adapter.post.PlatonGooglePayAdapter;

import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT_DATE;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT_SHORT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Payer.COUNTRY_CODE;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Payer.EMAIL;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Payer.MAX_IP;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Payer.MIN_IP;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.State.STATE;

/**
 * Request model that is used to store payer data
 * <p>
 * See @{@link PlatonGooglePayAdapter} for usages
 */
public class PlatonPayerGooglePay extends PlatonPayer {

    /**
     * Customer’s middle name
     * <p>
     * Value: String up to 32 characters
     * <p>
     * See {@link PlatonApiConstants.MethodProperties#PAYER_MIDDLE_NAME}
     */
    @Size(max = TEXT_SHORT)
    String mMiddleName;

    /**
     * Customer’s birthday
     * <p>
     * Value: String format yyyy-MM-dd
     * <p>
     * See {@link PlatonApiConstants.MethodProperties#PAYER_BIRTHDAY}
     */
    @Size(max = TEXT_DATE)
    String mBirthday;

    private PlatonPayerGooglePay() {

    }

    public PlatonPayerGooglePay(
            @Nullable @Size(max = TEXT_SHORT) final String firstName,
            @Nullable @Size(max = TEXT_SHORT) final String lastName,
            @Nullable @Size(max = TEXT_SHORT) final String middleName,
            @Nullable @Size(max = TEXT_DATE) final String birthday,
            @Nullable @Size(max = TEXT) final String address,
            @Nullable @Size(COUNTRY_CODE) final String countryCode,
            @Nullable @Size(STATE) final String state,
            @Nullable @Size(max = TEXT_SHORT) final String city,
            @Nullable @Size(max = TEXT_SHORT) final String zip,
            @Nullable @Size(max = EMAIL) final String email,
            @Nullable @Size(max = TEXT_SHORT) final String phone,
            @Nullable @Size(min = MIN_IP, max = MAX_IP) final String ipAddress
    ) {
        mFirstName = firstName;
        mLastName = lastName;
        mMiddleName = middleName;
        mBirthday = birthday;
        mAddress = address;
        mCountryCode = countryCode;
        mState = state;
        mCity = city;
        mZip = zip;
        mEmail = email;
        mPhone = phone;
        mIpAddress = ipAddress;
    }

    @Nullable
    @Size(max = TEXT_SHORT)
    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(@NonNull @Size(max = TEXT_SHORT) final String firstName) {
        mFirstName = firstName;
    }

    @Nullable
    @Size(max = TEXT_SHORT)
    public String getLastName() {
        return mLastName;
    }

    public void setLastName(@NonNull @Size(max = TEXT_SHORT) final String lastName) {
        mLastName = lastName;
    }

    @Nullable
    @Size(max = TEXT_SHORT)
    public String getMiddleName() {
        return mMiddleName;
    }

    public void setMiddleName(@NonNull @Size(max = TEXT_SHORT) final String middleName) {
        mMiddleName = middleName;
    }

    @Nullable
    @Size(max = TEXT_DATE)
    public String getBirthday() {
        return mBirthday;
    }

    public void setBirthday(@NonNull @Size(max = TEXT_DATE) final String birthday) {
        mBirthday = birthday;
    }

    @Nullable
    @Size(max = TEXT)
    public String getAddress() {
        return mAddress;
    }

    public void setAddress(@NonNull @Size(max = TEXT) final String address) {
        mAddress = address;
    }

    @Nullable
    @Size(COUNTRY_CODE)
    public String getCountryCode() {
        return mCountryCode;
    }

    public void setCountryCode(@NonNull @Size(COUNTRY_CODE) final String countryCode) {
        mCountryCode = countryCode;
    }

    @Nullable
    @Size(STATE)
    public String getState() {
        return mState;
    }

    public void setState(@NonNull @Size(STATE) final String state) {
        mState = state;
    }

    @Nullable
    @Size(max = TEXT_SHORT)
    public String getCity() {
        return mCity;
    }

    public void setCity(@NonNull @Size(max = TEXT_SHORT) final String city) {
        mCity = city;
    }

    @Nullable
    @Size(max = TEXT_SHORT)
    public String getZip() {
        return mZip;
    }

    public void setZip(@NonNull @Size(max = TEXT_SHORT) final String zip) {
        mZip = zip;
    }

    @Nullable
    @Size(max = EMAIL)
    public String getEmail() {
        return mEmail;
    }

    public void setEmail(@NonNull @Size(max = EMAIL) final String email) {
        mEmail = email;
    }

    @Nullable
    @Size(max = TEXT_SHORT)
    public String getPhone() {
        return mPhone;
    }

    public void setPhone(@NonNull @Size(max = TEXT_SHORT) final String phone) {
        mPhone = phone;
    }

    @Nullable
    @Size(min = MIN_IP, max = MAX_IP)
    public String getIpAddress() {
        return mIpAddress;
    }

    public void setIpAddress(@NonNull @Size(min = MIN_IP, max = MAX_IP) final String ipAddress) {
        mIpAddress = ipAddress;
    }

    @Override
    public String toString() {
        return "PlatonPayerGooglePay{" +
                "mFirstName='" + mFirstName + '\'' +
                ", mLastName='" + mLastName + '\'' +
                ", mMiddleName='" + mMiddleName + '\'' +
                ", mBirthday='" + mBirthday + '\'' +
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