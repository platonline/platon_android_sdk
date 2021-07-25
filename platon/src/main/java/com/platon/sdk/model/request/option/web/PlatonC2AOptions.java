package com.platon.sdk.model.request.option.web;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.platon.sdk.constant.api.PlatonApiConstants.MethodProperties;
import com.platon.sdk.endpoint.adapter.web.PlatonWebSaleAdapter;

import org.jetbrains.annotations.NotNull;

public class PlatonC2AOptions implements Parcelable {

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

	/**
	 * Client Parameter 1
	 * <p>
	 * See {@link MethodProperties#EXT_1}
	 */
	@Nullable
	String mExt1;

	/**
	 * Client Parameter 2
	 * <p>
	 * See {@link MethodProperties#EXT_2}
	 */
	@Nullable
	String mExt2;

	/**
	 * Client Parameter 3
	 * <p>
	 * See {@link MethodProperties#EXT_3}
	 */
	@Nullable
	String mExt3;

	/**
	 * Client Parameter 4
	 * <p>
	 * See {@link MethodProperties#EXT_4}
	 */
	@Nullable
	String mExt4;

	public PlatonC2AOptions() {
	}

	public PlatonC2AOptions(
			@Nullable final String language,
			@Nullable final String errorUrl,
			@Nullable final String formId,
			@Nullable final String ext1,
			@Nullable final String ext2,
			@Nullable final String ext3,
			@Nullable final String ext4
	) {
		mLanguage = language;
		mErrorUrl = errorUrl;
		mFormId = formId;
		mExt1 = ext1;
		mExt2 = ext2;
		mExt3 = ext3;
		mExt4 = ext4;
	}

	private PlatonC2AOptions(final Builder builder) {
		mLanguage = builder.mLanguage;
		mErrorUrl = builder.mErrorUrl;
		mFormId = builder.mFormId;
		mExt1 = builder.mExt1;
		mExt2 = builder.mExt2;
		mExt3 = builder.mExt3;
		mExt4 = builder.mExt4;
	}

	protected PlatonC2AOptions(final Parcel in) {
		mLanguage = in.readString();
		mErrorUrl = in.readString();
		mFormId = in.readString();
		mExt1 = in.readString();
		mExt2 = in.readString();
		mExt3 = in.readString();
		mExt4 = in.readString();
	}

	public static final Parcelable.Creator<PlatonC2AOptions> CREATOR = new Parcelable.Creator<PlatonC2AOptions>() {
		@Override
		public PlatonC2AOptions createFromParcel(Parcel in) {
			return new PlatonC2AOptions(in);
		}

		@Override
		public PlatonC2AOptions[] newArray(int size) {
			return new PlatonC2AOptions[size];
		}
	};

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
	public String getExt1() {
		return mExt1;
	}

	public void setExt1(@Nullable final String ext1) {
		mExt1 = ext1;
	}

	@Nullable
	public String getExt2() {
		return mExt2;
	}

	public void setExt2(@Nullable final String ext2) {
		mExt2 = ext2;
	}

	@Nullable
	public String getExt3() {
		return mExt3;
	}

	public void setExt3(@Nullable final String ext3) {
		mExt3 = ext3;
	}

	@Nullable
	public String getExt4() {
		return mExt4;
	}

	public void setExt4(@Nullable final String ext4) {
		mExt4 = ext4;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(final Parcel parcel, final int i) {
		parcel.writeString(mLanguage);
		parcel.writeString(mErrorUrl);
		parcel.writeString(mFormId);
		parcel.writeString(mExt1);
		parcel.writeString(mExt2);
		parcel.writeString(mExt3);
		parcel.writeString(mExt4);
	}

	public static final class Builder {

		@Nullable
		private String mLanguage;

		@Nullable
		private String mErrorUrl;

		@Nullable
		private String mFormId;

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

		public PlatonC2AOptions build() {
			return new PlatonC2AOptions(this);
		}
	}

	@NotNull
	@Override
	public String toString() {
		return "PlatonC2AOptions{" +
				"mLanguage='" + mLanguage + '\'' +
				", mErrorUrl='" + mErrorUrl + '\'' +
				", mFormId='" + mFormId + '\'' +
				", mExt1='" + mExt1 + '\'' +
				", mExt2='" + mExt2 + '\'' +
				", mExt3='" + mExt3 + '\'' +
				", mExt4='" + mExt4 + '\'' +
				'}';
	}
}
