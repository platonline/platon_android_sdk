package com.platon.sdk.model.request.recurring;

import androidx.annotation.NonNull;
import androidx.annotation.Size;

import com.platon.sdk.endpoint.adapter.web.PlatonWebRecurringAdapter;
import com.platon.sdk.endpoint.adapter.web.PlatonWebScheduleAdapter;

import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT_SHORT;

/**
 * Request model that is used to store recurring data
 * <p>
 * See {@link PlatonWebRecurringAdapter} and {@link PlatonWebScheduleAdapter} for usages
 */
public class PlatonRecurringWeb extends PlatonRecurring {

    public PlatonRecurringWeb(
            @NonNull final String firstTransId,
            @NonNull @Size(max = TEXT_SHORT) final String token
    ) {
        super(firstTransId, token);
    }

    @NonNull
    @Size(max = TEXT_SHORT)
    public String getToken() {
        return mToken;
    }

    public void setToken(@NonNull @Size(max = TEXT_SHORT) final String token) {
        mToken = token;
    }

    @NonNull
    @Override
    public String toString() {
        return "PlatonRecurringWeb{" +
                "mFirstTransId='" + mFirstTransId + '\'' +
                ", mToken='" + mToken + '\'' +
                '}';
    }
}
