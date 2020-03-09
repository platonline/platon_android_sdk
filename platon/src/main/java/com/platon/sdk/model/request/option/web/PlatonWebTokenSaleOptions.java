package com.platon.sdk.model.request.option.web;

import android.os.Parcel;
import androidx.annotation.Nullable;

import com.platon.sdk.constant.api.PlatonApiConstants.MethodProperties;
import com.platon.sdk.endpoint.adapter.web.PlatonWebSaleAdapter;

/**
 * This class extends {@link PlatonWebOptions} and provide some new fields which handle representation of
 * requests from {@link PlatonWebSaleAdapter}
 */
public class PlatonWebTokenSaleOptions extends PlatonWebOptions {

	/**
	 * Localization language to be selected on the payment page by default
	 * <p>
	 * See {@link MethodProperties#LANG}
	 */
	@Nullable
	private String mLanguage;

	/**
	 * Optional URL to which the Buyer will be returned after three unsuccessful purchase attempts
	 * <p>
	 * See {@link MethodProperties#ERROR_URL}
	 */
	@Nullable
	private String mErrorUrl;

	/**
	 * Specific payment page identifier for web payments
	 * (In case the Client's account has multiple payment pages configured)
	 * <p>
	 * See {@link MethodProperties#FORM_ID}
	 */
	@Nullable
	private String mFormId;

	@Nullable
	private String mCardToken;

	public PlatonWebTokenSaleOptions() {
	}

	public PlatonWebTokenSaleOptions(
			@Nullable final String language,
			@Nullable final String errorUrl,
			@Nullable final String formId,
			@Nullable final String cardToken,
			@Nullable final String ext2,
			@Nullable final String ext3,
			@Nullable final String ext4
	) {
		mLanguage = language;
		mErrorUrl = errorUrl;
		mFormId = formId;
		mCardToken = cardToken;
		mExt2 = ext2;
		mExt3 = ext3;
		mExt4 = ext4;
	}

	private PlatonWebTokenSaleOptions(final Builder builder) {
		mLanguage = builder.mLanguage;
		mErrorUrl = builder.mErrorUrl;
		mFormId = builder.mFormId;
		mCardToken = builder.mCardToken;
		mExt2 = builder.mExt2;
		mExt3 = builder.mExt3;
		mExt4 = builder.mExt4;
	}

	protected PlatonWebTokenSaleOptions(final Parcel in) {
		super(in);
		mLanguage = in.readString();
		mErrorUrl = in.readString();
		mFormId = in.readString();
	}

	@Nullable
	public String getLanguage() {
		return mLanguage;
	}

	public void setLanguage(@Nullable  final String language) {
		mLanguage = language;
	}

	@Nullable
	public String getErrorUrl() {
		return mErrorUrl;
	}

	public void setErrorUrl(@Nullable final String errorUrl) {
		mErrorUrl = errorUrl;
	}

	@Nullable
	public String getFormId() {
		return mFormId;
	}

	public void setFormId(@Nullable final String formId) {
		mFormId = formId;
	}

	@Nullable
	public String getCardToken() {
		return mCardToken;
	}

	public void setCardToken(@Nullable final String cardToken) {
		mCardToken = cardToken;
	}

	@Override
	public void writeToParcel(final Parcel parcel, final int i) {
		super.writeToParcel(parcel, i);
		parcel.writeString(mLanguage);
		parcel.writeString(mErrorUrl);
		parcel.writeString(mFormId);
	}

	public static final class Builder {

		@Nullable
		private String mLanguage;

		@Nullable
		private String mErrorUrl;

		@Nullable
		private String mFormId;

		@Nullable
		private String mExt2;

		@Nullable
		private String mExt3;

		@Nullable
		private String mExt4;
		@Nullable
		private String mCardToken;


		public Builder language(@Nullable final String language) {
			mLanguage = language;
			return this;
		}

		public Builder errorUrl(@Nullable final String errorUrl) {
			mErrorUrl = errorUrl;
			return this;
		}

		public Builder formId(@Nullable final String formId) {
			mFormId = formId;
			return this;
		}

		public Builder cardToken(@Nullable final String cardToken) {
			mCardToken = cardToken;
			return this;
		}

		public Builder ext2(@Nullable final String ext2) {
			mExt2 = ext2;
			return this;
		}

		public Builder ext3(@Nullable final String ext3) {
			mExt3 = ext3;
			return this;
		}

		public Builder ext4(@Nullable final String ext4) {
			mExt4 = ext4;
			return this;
		}

		public PlatonWebTokenSaleOptions build() {
			return new PlatonWebTokenSaleOptions(this);
		}
	}

	@Override
	public String toString() {
		return "PlatonWebSaleOptions{" +
				"mLanguage='" + mLanguage + '\'' +
				", mErrorUrl='" + mErrorUrl + '\'' +
				", mFormId='" + mFormId + '\'' +
				", mCardToken='" + mCardToken + '\'' +
				", mExt2='" + mExt2 + '\'' +
				", mExt3='" + mExt3 + '\'' +
				", mExt4='" + mExt4 + '\'' +
				'}';
	}
}
