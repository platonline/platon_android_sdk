package com.platon.sdk.model.request.order.product;

import android.os.Parcel;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.Size;
import androidx.annotation.FloatRange;

import com.platon.sdk.constant.api.PlatonApiConstants.MethodProperties;
import com.platon.sdk.endpoint.adapter.web.PlatonWebSaleAdapter;
import com.platon.sdk.util.PlatonBase64Util;

import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MAX_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MIN_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.CURRENCY_CODE;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT_MIN;

/**
 * Extension from {@link PlatonProduct} which used in {@link PlatonWebSaleAdapter} requests
 * <p>
 * See {@link PlatonBase64Util} for more details
 */
public class PlatonProductSale extends PlatonProduct {

	/**
	 * PlatonProduct, selected by default in products list
	 * <p>
	 * See {@link MethodProperties#SELECTED}
	 */
	private boolean mIsSelected;
	/**
	 * Flag to initialize the possibility of the further recurring payments
	 * <p>
	 * See {@link MethodProperties#RECURRING}
	 */
	private boolean mIsRecurring;

	public PlatonProductSale(
			@FloatRange(from = MIN_AMOUNT, to = MAX_AMOUNT) final float amount,
			@NonNull @Size(max = TEXT_MIN) final String description
	) {
		super(amount, description);
	}

	public PlatonProductSale(final Parcel in) {
		super(in);
		mIsSelected = in.readByte() != 0;
		mIsRecurring = in.readByte() != 0;
	}

	@Nullable
	@Size(CURRENCY_CODE)
	public String getCurrencyCode() {
		return mCurrencyCode;
	}

	public void setCurrencyCode(@Nullable @Size(CURRENCY_CODE) final String currencyCode) {
		mCurrencyCode = currencyCode;
	}

	public boolean isSelected() {
		return mIsSelected;
	}

	public void setSelected(final boolean selected) {
		mIsSelected = selected;
	}

	public boolean isRecurring() {
		return mIsRecurring;
	}

	public void setRecurring(final boolean recurring) {
		mIsRecurring = recurring;
	}

	@Override
	public void writeToParcel(final Parcel parcel, final int i) {
		super.writeToParcel(parcel, i);
		parcel.writeByte((byte) (mIsSelected ? 1 : 0));
		parcel.writeByte((byte) (mIsRecurring ? 1 : 0));
	}

	@NonNull
	@Override
	public String toString() {
		return "PlatonProductSale{" +
				"mIsSelected=" + mIsSelected +
				", mIsRecurring=" + mIsRecurring +
				", mAmount=" + mAmount +
				", mCurrencyCode='" + mCurrencyCode + '\'' +
				", mDescription='" + mDescription + '\'' +
				'}';
	}
}
