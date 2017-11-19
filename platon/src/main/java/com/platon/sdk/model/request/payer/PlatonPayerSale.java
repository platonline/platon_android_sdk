package com.platon.sdk.model.request.payer;

import android.support.annotation.NonNull;
import android.support.annotation.Size;

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
public class PlatonPayerSale extends PlatonPayer {

    private PlatonPayerSale() {

    }

    public PlatonPayerSale(
            @NonNull @Size(max = TEXT_SHORT) final String firstName,
            @NonNull @Size(max = TEXT_SHORT) final String lastName,
            @NonNull @Size(max = TEXT) final String address,
            @NonNull @Size(COUNTRY_CODE) final String countryCode,
            @NonNull @Size(STATE) final String state,
            @NonNull @Size(max = TEXT_SHORT) final String city,
            @NonNull @Size(max = TEXT_SHORT) final String zip,
            @NonNull @Size(max = EMAIL) final String email,
            @NonNull @Size(max = TEXT_SHORT) final String phone,
            @NonNull @Size(min = MIN_IP, max = MAX_IP) final String ipAddress
    ) {
        mFirstName = firstName;
        mLastName = lastName;
        mAddress = address;
        mCountryCode = countryCode;
        mState = state;
        mCity = city;
        mZip = zip;
        mEmail = email;
        mPhone = phone;
        mIpAddress = ipAddress;
    }

    @NonNull
    @Size(max = TEXT_SHORT)
    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(@NonNull @Size(max = TEXT_SHORT) final String firstName) {
        mFirstName = firstName;
    }

    @NonNull
    @Size(max = TEXT_SHORT)
    public String getLastName() {
        return mLastName;
    }

    public void setLastName(@NonNull @Size(max = TEXT_SHORT) final String lastName) {
        mLastName = lastName;
    }

    @NonNull
    @Size(max = TEXT)
    public String getAddress() {
        return mAddress;
    }

    public void setAddress(@NonNull @Size(max = TEXT) final String address) {
        mAddress = address;
    }

    @NonNull
    @Size(COUNTRY_CODE)
    public String getCountryCode() {
        return mCountryCode;
    }

    public void setCountryCode(@NonNull @Size(COUNTRY_CODE) final String countryCode) {
        mCountryCode = countryCode;
    }

    @NonNull
    @Size(STATE)
    public String getState() {
        return mState;
    }

    public void setState(@NonNull @Size(STATE) final String state) {
        mState = state;
    }

    @NonNull
    @Size(max = TEXT_SHORT)
    public String getCity() {
        return mCity;
    }

    public void setCity(@NonNull @Size(max = TEXT_SHORT) final String city) {
        mCity = city;
    }

    @NonNull
    @Size(max = TEXT_SHORT)
    public String getZip() {
        return mZip;
    }

    public void setZip(@NonNull @Size(max = TEXT_SHORT) final String zip) {
        mZip = zip;
    }

    @NonNull
    @Size(max = EMAIL)
    public String getEmail() {
        return mEmail;
    }

    public void setEmail(@NonNull @Size(max = EMAIL) final String email) {
        mEmail = email;
    }

    @NonNull
    @Size(max = TEXT_SHORT)
    public String getPhone() {
        return mPhone;
    }

    public void setPhone(@NonNull @Size(max = TEXT_SHORT) final String phone) {
        mPhone = phone;
    }

    @NonNull
    @Size(min = MIN_IP, max = MAX_IP)
    public String getIpAddress() {
        return mIpAddress;
    }

    public void setIpAddress(@NonNull @Size(min = MIN_IP, max = MAX_IP) final String ipAddress) {
        mIpAddress = ipAddress;
    }

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
