package com.platon.sdk.model.request.option.web;

import android.os.Parcel;
import androidx.annotation.Nullable;

import com.platon.sdk.constant.api.PlatonApiConstants.MethodProperties;
import com.platon.sdk.endpoint.adapter.web.PlatonWebSaleAdapter;

/**
 * This class extends {@link PlatonWebOptions} and provide some new fields which handle representation of
 * requests from {@link PlatonWebSaleAdapter}
 */
public class PlatonWebSaleOptions extends PlatonWebOptions {

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
	private String mReqToken;

	public PlatonWebSaleOptions() {
	}

	public PlatonWebSaleOptions(
			@Nullable final String language,
			@Nullable final String errorUrl,
			@Nullable final String formId,
			@Nullable final String reqToken,
			@Nullable final String ext1,
			@Nullable final String ext2,
			@Nullable final String ext3,
			@Nullable final String ext4
	) {
		mLanguage = language;
		mErrorUrl = errorUrl;
		mFormId = formId;
		mReqToken = reqToken;
		mExt1 = ext1;
		mExt2 = ext2;
		mExt3 = ext3;
		mExt4 = ext4;
	}

	private PlatonWebSaleOptions(final Builder builder) {
		mLanguage = builder.mLanguage;
		mErrorUrl = builder.mErrorUrl;
		mFormId = builder.mFormId;
		mReqToken = builder.mReqToken;
		mExt1 = builder.mExt1;
		mExt2 = builder.mExt2;
		mExt3 = builder.mExt3;
		mExt4 = builder.mExt4;
	}

	protected PlatonWebSaleOptions(final Parcel in) {
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
	public String getReqToken() {
		return mReqToken;
	}

	public void setReqToken(@Nullable  final String reqToken) {
		mReqToken = reqToken;
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
		private String mReqToken;

		@Nullable
		private String mExt1;

		@Nullable
		private String mExt2;

		@Nullable
		private String mExt3;

		@Nullable
		private String mExt4;

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

		public Builder reqToken(@Nullable final String reqToken) {
			mReqToken = reqToken;
			return this;
		}

		public Builder ext1(@Nullable final String ext1) {
			mExt1 = ext1;
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

		public PlatonWebSaleOptions build() {
			return new PlatonWebSaleOptions(this);
		}
	}

	@Override
	public String toString() {
		return "PlatonWebSaleOptions{" +
				"mLanguage='" + mLanguage + '\'' +
				", mErrorUrl='" + mErrorUrl + '\'' +
				", mFormId='" + mFormId + '\'' +
				", mReqToken='" + mReqToken + '\'' +
				", mExt1='" + mExt1 + '\'' +
				", mExt2='" + mExt2 + '\'' +
				", mExt3='" + mExt3 + '\'' +
				", mExt4='" + mExt4 + '\'' +
				'}';
	}
}
