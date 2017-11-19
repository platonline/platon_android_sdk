package com.platon.sdk.model.request.order.product;

import android.annotation.SuppressLint;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Size;

import com.platon.sdk.endpoint.adapter.web.PlatonWebRecurringAdapter;

import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MAX_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MIN_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT_MIN;

/**
 * Extension from {@link PlatonProduct} which used in {@link PlatonWebRecurringAdapter}
 */
public class PlatonProductRecurring extends PlatonProduct {

	public PlatonProductRecurring(
			@FloatRange(from = MIN_AMOUNT, to = MAX_AMOUNT) final float amount,
			@NonNull @Size(max = TEXT_MIN) final String description
	) {
		super(amount, description);
	}

	@SuppressLint("Range")
	@Nullable
	@Size(max = TEXT_MIN)
	public String getId() {
		return mId;
	}

	public void setId(@Nullable @Size(max = TEXT_MIN) final String id) {
		mId = id;
	}

	@Override
	public String toString() {
		return "PlatonProductRecurring{" +
				"mId='" + mId + '\'' +
				", mAmount=" + mAmount +
				", mDescription='" + mDescription + '\'' +
				'}';
	}
}
