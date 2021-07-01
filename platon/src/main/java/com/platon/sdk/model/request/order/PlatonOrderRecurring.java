package com.platon.sdk.model.request.order;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Size;

import com.platon.sdk.endpoint.adapter.post.PlatonRecurringAdapter;

import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MAX_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MIN_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT_LONG;

/**
 * Request model that extend {@link PlatonOrder} and used in {@link PlatonRecurringAdapter}
 */
public class PlatonOrderRecurring extends PlatonOrder {

    public PlatonOrderRecurring(
            @NonNull @Size(max = TEXT) final String id,
            @FloatRange(from = MIN_AMOUNT, to = MAX_AMOUNT) final float amount,
            @NonNull @Size(max = TEXT_LONG) final String description
    ) {
        super(amount, description);
        mId = id;
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
    @Override
    public String toString() {
        return "PlatonOrderRecurring{" +
                "mId='" + mId + '\'' +
                ", mAmount=" + mAmount +
                ", mDescription='" + mDescription + '\'' +
                '}';
    }

}
