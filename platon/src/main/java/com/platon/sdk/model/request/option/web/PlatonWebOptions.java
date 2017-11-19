package com.platon.sdk.model.request.option.web;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.platon.sdk.constant.api.PlatonApiConstants.MethodProperties;
import com.platon.sdk.endpoint.adapter.web.PlatonWebRecurringAdapter;
import com.platon.sdk.endpoint.adapter.web.PlatonWebSaleAdapter;

/**
 * Web options provides fields by which developer can customize displayed web form in web SALE, RECURRING_SALE request
 * <p>
 * See {@link PlatonWebSaleAdapter} and {@link PlatonWebRecurringAdapter} for its usages
 */
public class PlatonWebOptions implements Parcelable {

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

	public PlatonWebOptions() {
	}

	public PlatonWebOptions(
			@Nullable final String ext1,
			@Nullable final String ext2,
			@Nullable final String ext3,
			@Nullable final String ext4
	) {
		mExt1 = ext1;
		mExt2 = ext2;
		mExt3 = ext3;
		mExt4 = ext4;
	}

	private PlatonWebOptions(final Builder builder) {
		mExt1 = builder.mExt1;
		mExt2 = builder.mExt2;
		mExt3 = builder.mExt3;
		mExt4 = builder.mExt4;
	}

	protected PlatonWebOptions(Parcel in) {
		mExt1 = in.readString();
		mExt2 = in.readString();
		mExt3 = in.readString();
		mExt4 = in.readString();
	}

	public static final Creator<PlatonWebOptions> CREATOR = new Creator<PlatonWebOptions>() {
		@Override
		public PlatonWebOptions createFromParcel(Parcel in) {
			return new PlatonWebOptions(in);
		}

		@Override
		public PlatonWebOptions[] newArray(int size) {
			return new PlatonWebOptions[size];
		}
	};

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
		parcel.writeString(mExt1);
		parcel.writeString(mExt2);
		parcel.writeString(mExt3);
		parcel.writeString(mExt4);
	}

	public static final class Builder {

		@Nullable
		private String mExt1;

		@Nullable
		private String mExt2;

		@Nullable
		private String mExt3;

		@Nullable
		private String mExt4;

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

		public PlatonWebOptions build() {
			return new PlatonWebOptions(this);
		}
	}

	@Override
	public String toString() {
		return "PlatonWebOptions{" +
				"mExt1='" + mExt1 + '\'' +
				", mExt2='" + mExt2 + '\'' +
				", mExt3='" + mExt3 + '\'' +
				", mExt4='" + mExt4 + '\'' +
				'}';
	}
}
