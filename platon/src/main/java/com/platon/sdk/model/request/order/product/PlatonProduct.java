package com.platon.sdk.model.request.order.product;

import android.annotation.SuppressLint;
import android.os.Parcel;

import androidx.annotation.Size;
import androidx.annotation.NonNull;
import androidx.annotation.FloatRange;

import com.platon.sdk.endpoint.adapter.post.PlatonScheduleAdapter;
import com.platon.sdk.model.request.order.PlatonOrder;

import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MAX_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MIN_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT_MIN;


/**
 * Extension from {@link PlatonOrder} which used in Web Payments Platform requests
 * <p>
 * See {@link PlatonScheduleAdapter} for usage
 */
public class PlatonProduct extends PlatonOrder {

	public PlatonProduct(
			@FloatRange(from = MIN_AMOUNT, to = MAX_AMOUNT) final float amount,
			@NonNull @Size(max = TEXT_MIN)  final String description
	) {
		super(amount, description);
	}

	PlatonProduct(final Parcel in) {
		super(in);
	}

	@SuppressLint("Range")
	@NonNull
	@Override
	@Size(max = TEXT_MIN)
	public String getDescription() {
		return super.getDescription();
	}

	@Override
	public void setDescription(@NonNull @Size(max = TEXT_MIN) final String description) {
		super.setDescription(description);
	}

	@NonNull
	@Override
	public String toString() {
		return "PlatonProduct{" +
				"mAmount=" + mAmount +
				", mDescription='" + mDescription + '\'' +
				'}';
	}
}
