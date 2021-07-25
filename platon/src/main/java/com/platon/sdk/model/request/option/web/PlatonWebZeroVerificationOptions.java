package com.platon.sdk.model.request.option.web;

import android.os.Parcel;

import androidx.annotation.Nullable;

import com.platon.sdk.constant.api.PlatonApiConstants.MethodProperties;
import com.platon.sdk.endpoint.adapter.web.PlatonWebZeroVerificationAdapter;

import org.jetbrains.annotations.NotNull;

/**
 * This class extends {@link PlatonWebZeroVerificationOptions} and provide some new fields which handle representation of
 * requests from {@link PlatonWebZeroVerificationAdapter}
 */
public class PlatonWebZeroVerificationOptions extends PlatonWebOptions {

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

	@Nullable
	private String mCustomerWallet;

	@Nullable
	private String mOrder;

	@Nullable
	private String mBankId;

	@Nullable
	private String mPayerId;

	public PlatonWebZeroVerificationOptions() {
	}

	public PlatonWebZeroVerificationOptions(
			@Nullable final String language,
			@Nullable final String errorUrl,
			@Nullable final String customerWallet,
			@Nullable final String order,
			@Nullable final String bankId,
			@Nullable final String payerId,
			@Nullable final String ext1,
			@Nullable final String ext2,
			@Nullable final String ext3,
			@Nullable final String ext4,
			@Nullable final String ext5,
			@Nullable final String ext6,
			@Nullable final String ext7,
			@Nullable final String ext8,
			@Nullable final String ext9,
			@Nullable final String ext10
	) {
		mLanguage = language;
		mErrorUrl = errorUrl;
		mCustomerWallet = customerWallet;
		mOrder = order;
		mBankId = bankId;
		mPayerId = payerId;
		mExt1 = ext1;
		mExt2 = ext2;
		mExt3 = ext3;
		mExt4 = ext4;
		mExt5 = ext5;
		mExt6 = ext6;
		mExt7 = ext7;
		mExt8 = ext8;
		mExt9 = ext9;
		mExt10 = ext10;
	}

	private PlatonWebZeroVerificationOptions(final Builder builder) {
		mLanguage = builder.mLanguage;
		mErrorUrl = builder.mErrorUrl;
		mCustomerWallet = builder.mCustomerWaller;
		mOrder = builder.mOrder;
		mPayerId = builder.mPayerId;
		mBankId = builder.mBankId;
		mExt1 = builder.mExt1;
		mExt2 = builder.mExt2;
		mExt3 = builder.mExt3;
		mExt4 = builder.mExt4;
		mExt5 = builder.mExt5;
		mExt6 = builder.mExt6;
		mExt7 = builder.mExt7;
		mExt8 = builder.mExt8;
		mExt9 = builder.mExt9;
		mExt10 = builder.mExt10;
	}

	protected PlatonWebZeroVerificationOptions(final Parcel in) {
		super(in);
		mLanguage = in.readString();
		mErrorUrl = in.readString();
		mCustomerWallet = in.readString();
		mOrder = in.readString();
		mBankId = in.readString();
		mPayerId = in.readString();
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
	public String getCustomerWallet() {
		return mCustomerWallet;
	}

	public void setCustomerWallet(@Nullable final String customerWallet) {
		mCustomerWallet = customerWallet;
	}

	@Nullable
	public String getOrder() {
		return mOrder;
	}

	public void setOrder(@Nullable final String order) {
		mOrder = order;
	}

	@Nullable
	public String getBankId() {
		return mBankId;
	}

	public void setBankId(@Nullable final String bankId) {
		mBankId = bankId;
	}

	@Nullable
	public String getPayerId() {
		return mPayerId;
	}

	public void setPayerId(@Nullable final String payerId) {
		mPayerId = payerId;
	}

	@Override
	public void writeToParcel(final Parcel parcel, final int i) {
		super.writeToParcel(parcel, i);
		parcel.writeString(mLanguage);
		parcel.writeString(mErrorUrl);
		parcel.writeString(mCustomerWallet);
		parcel.writeString(mOrder);
		parcel.writeString(mBankId);
		parcel.writeString(mPayerId);
	}

	public static final class Builder {

		@Nullable
		private String mLanguage;

		@Nullable
		private String mErrorUrl;

		@Nullable
		private String mCustomerWaller;

		@Nullable
		private String mOrder;

		@Nullable
		private String mBankId;

		@Nullable
		private String mPayerId;

		@Nullable
		private String mExt1;

		@Nullable
		private String mExt2;

		@Nullable
		private String mExt3;

		@Nullable
		private String mExt4;

		@Nullable
		private String mExt5;

		@Nullable
		private String mExt6;

		@Nullable
		private String mExt7;

		@Nullable
		private String mExt8;

		@Nullable
		private String mExt9;

		@Nullable
		private String mExt10;

		public Builder language(@Nullable final String language) {
			mLanguage = language;
			return this;
		}

		public Builder errorUrl(@Nullable final String errorUrl) {
			mErrorUrl = errorUrl;
			return this;
		}

		public Builder customerWallet(@Nullable final String customerWallet) {
			mCustomerWaller = customerWallet;
			return this;
		}

		public Builder order(@Nullable final String order) {
			mOrder = order;
			return this;
		}

		public Builder bankId(@Nullable final String bankId) {
			mBankId = bankId;
			return this;
		}

		public Builder payerId(@Nullable final String payerId) {
			mPayerId = payerId;
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

		public Builder ext5(@Nullable final String ext5) {
			mExt5 = ext5;
			return this;
		}

		public Builder ext6(@Nullable final String ext6) {
			mExt6 = ext6;
			return this;
		}

		public Builder ext7(@Nullable final String ext7) {
			mExt7 = ext7;
			return this;
		}

		public Builder ext8(@Nullable final String ext8) {
			mExt8 = ext8;
			return this;
		}

		public Builder ext9(@Nullable final String ext9) {
			mExt9 = ext9;
			return this;
		}

		public Builder ext10(@Nullable final String ext10) {
			mExt10 = ext10;
			return this;
		}

		public PlatonWebZeroVerificationOptions build() {
			return new PlatonWebZeroVerificationOptions(this);
		}
	}

	@NotNull
	@Override
	public String toString() {
		return "PlatonWebZeroVerificationOptions{" +
				"mLanguage='" + mLanguage + '\'' +
				", mErrorUrl='" + mErrorUrl + '\'' +
				", mCustomerWallet='" + mCustomerWallet + '\'' +
				", mOrder='" + mOrder + '\'' +
				", mBankId='" + mBankId + '\'' +
				", mPayerId='" + mPayerId + '\'' +
				", mExt1='" + mExt1 + '\'' +
				", mExt2='" + mExt2 + '\'' +
				", mExt3='" + mExt3 + '\'' +
				", mExt4='" + mExt4 + '\'' +
				", mExt5='" + mExt5 + '\'' +
				", mExt6='" + mExt6 + '\'' +
				", mExt7='" + mExt7 + '\'' +
				", mExt8='" + mExt8 + '\'' +
				", mExt9='" + mExt9 + '\'' +
				", mExt10='" + mExt10 + '\'' +
				'}';
	}
}
