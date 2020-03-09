package com.platon.sdk.model.request.payer;

import androidx.annotation.Nullable;

import com.platon.sdk.endpoint.adapter.web.PlatonWebSaleAdapter;

/**
 * Request model that is used to store payer data
 * <p>
 * See @{@link PlatonWebSaleAdapter} for usages
 */
public class PlatonPayerWebSale extends PlatonPayer {

	private PlatonPayerWebSale(final Builder builder) {
		setFirstName(builder.mFirstName);
		setLastName(builder.mLastName);
		setAddress(builder.mAddress);
		setCountryCode(builder.mCountryCode);
		setState(builder.mState);
		setCity(builder.mCity);
		setZip(builder.mZip);
		setEmail(builder.mEmail);
		setPhone(builder.mPhone);
	}

	public static final class Builder {

		@Nullable
		private String mFirstName;

		@Nullable
		private String mLastName;

		@Nullable
		private String mAddress;

		@Nullable
		private String mCountryCode;

		@Nullable
		private String mState;

		@Nullable
		private String mCity;

		@Nullable
		private String mZip;

		@Nullable
		private String mEmail;

		@Nullable
		private String mPhone;

		public Builder firstName(@Nullable final String firstName) {
			mFirstName = firstName;
			return this;
		}

		public Builder lastName(@Nullable final String lastName) {
			mLastName = lastName;
			return this;
		}

		public Builder address(@Nullable final String address) {
			mAddress = address;
			return this;
		}

		public Builder countryCode(@Nullable final String countryCode) {
			mCountryCode = countryCode;
			return this;
		}

		public Builder state(@Nullable final String state) {
			mState = state;
			return this;
		}

		public Builder city(@Nullable final String city) {
			mCity = city;
			return this;
		}

		public Builder zip(@Nullable final String zip) {
			mZip = zip;
			return this;
		}

		public Builder email(@Nullable final String email) {
			mEmail = email;
			return this;
		}

		public Builder phone(@Nullable final String phone) {
			mPhone = phone;
			return this;
		}

		public PlatonPayerWebSale build() {
			return new PlatonPayerWebSale(this);
		}
	}

	@Override
	public String toString() {
		return "PlatonPayerWebSale{" +
				"mFirstName='" + mFirstName + '\'' +
				", mLastName='" + mLastName + '\'' +
				", mAddress='" + mAddress + '\'' +
				", mCountryCode='" + mCountryCode + '\'' +
				", mState='" + mState + '\'' +
				", mCity='" + mCity + '\'' +
				", mZip='" + mZip + '\'' +
				", mEmail='" + mEmail + '\'' +
				", mPhone='" + mPhone + '\'' +
				'}';
	}

}
