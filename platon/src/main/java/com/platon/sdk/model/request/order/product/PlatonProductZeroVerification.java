package com.platon.sdk.model.request.order.product;

import android.os.Parcel;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Size;

import com.platon.sdk.endpoint.adapter.web.PlatonWebSaleAdapter;
import com.platon.sdk.endpoint.adapter.web.PlatonWebZeroVerificationAdapter;
import com.platon.sdk.util.PlatonBase64Util;

import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MAX_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MIN_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.CURRENCY_CODE;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT_MIN;

/**
 * Extension from {@link PlatonProductZeroVerification} which used in {@link PlatonWebZeroVerificationAdapter} requests
 * <p>
 * See {@link PlatonBase64Util} for more details
 */
public class PlatonProductZeroVerification extends PlatonProduct {

    public PlatonProductZeroVerification(
            @FloatRange(from = MIN_AMOUNT, to = MAX_AMOUNT) final float amount,
            @NonNull @Size(max = TEXT_MIN) final String description
    ) {
        super(amount, description);
    }

    public PlatonProductZeroVerification(final Parcel in) {
        super(in);
    }

    @Nullable
    @Size(CURRENCY_CODE)
    public String getCurrencyCode() {
        return mCurrencyCode;
    }

    public void setCurrencyCode(@Nullable @Size(CURRENCY_CODE) final String currencyCode) {
        mCurrencyCode = currencyCode;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int i) {
        super.writeToParcel(parcel, i);
    }

    @NonNull
    @Override
    public String toString() {
        return "PlatonProductSale{" +
                ", mAmount=" + mAmount +
                ", mCurrencyCode='" + mCurrencyCode + '\'' +
                ", mDescription='" + mDescription + '\'' +
                '}';
    }
}
