package com.platon.sdk.model.request.order;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Size;

import com.platon.sdk.endpoint.adapter.post.PlatonSaleAdapter;

import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MAX_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MIN_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.CURRENCY_CODE;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT_LONG;

/**
 * Request model that extend {@link PlatonOrder} and used in {@link PlatonSaleAdapter}
 */
public class PlatonOrderSale extends PlatonOrder {

    public PlatonOrderSale(
            @NonNull @Size(max = TEXT) final String id,
            @FloatRange(from = MIN_AMOUNT, to = MAX_AMOUNT) final float amount,
            @NonNull @Size(CURRENCY_CODE) final String currencyCode,
            @NonNull @Size(max = TEXT_LONG) final String description
    ) {
        super(amount, description);
        mId = id;
        mCurrencyCode = currencyCode;
    }

    @NonNull
    @Size(max = TEXT)
    public String getId() {
        return mId;
    }

    public void setId(@NonNull @Size(max = TEXT) final String id) {
        mId = id;
    }

    @NonNull
    @Size(CURRENCY_CODE)
    public String getCurrencyCode() {
        return mCurrencyCode;
    }

    public void setCurrencyCode(@NonNull @Size(CURRENCY_CODE) final String currencyCode) {
        mCurrencyCode = currencyCode;
    }

    @NonNull
    @Override
    public String toString() {
        return "PlatonOrderSale{" +
                "mId='" + mId + '\'' +
                ", mAmount=" + mAmount +
                ", mCurrencyCode='" + mCurrencyCode + '\'' +
                ", mDescription='" + mDescription + '\'' +
                '}';
    }

}
