package com.platon.sdk.model.response.sale;

import android.os.Parcel;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.platon.sdk.callback.PlatonCaptureCallback;
import com.platon.sdk.callback.PlatonSaleCallback;
import com.platon.sdk.constant.api.PlatonStatus;
import com.platon.sdk.constant.api.PlatonRedirectMethod;
import com.platon.sdk.deserializer.PlatonSaleDeserializer;
import com.platon.sdk.endpoint.adapter.post.PlatonSaleAdapter;
import com.platon.sdk.util.Platon3dsSubmitUtil;
import com.platon.sdk.util.Platon3dsSubmitUtil.OnSubmit3dsDataListener;

import retrofit2.Call;

import static com.platon.sdk.constant.api.PlatonApiConstants.SerializedNames.REDIRECT_METHOD;
import static com.platon.sdk.constant.api.PlatonApiConstants.SerializedNames.REDIRECT_PARAMS;
import static com.platon.sdk.constant.api.PlatonApiConstants.SerializedNames.REDIRECT_URL;

/**
 * Model which used in {@link PlatonSaleAdapter} in {@link PlatonSaleResponse} from {@link PlatonCaptureCallback}
 * which deserialize from {@link PlatonSaleDeserializer}
 * <p>
 * After the successful request in {@link PlatonSaleCallback#on3dSecureResponse(Call, PlatonSale3DSecure)} you should
 * generate HTML page through {@link Platon3dsSubmitUtil#submit3dsData(PlatonSale3DSecure, OnSubmit3dsDataListener)}
 * where will be the button which will submit your 3ds data and verify payment
 */
@SuppressWarnings("DanglingJavadoc")
public class PlatonSale3DSecure extends PlatonSale {

	/**
	 * action : SALE
	 * result : REDIRECT
	 * status : 3DS
	 * trans_id : 03346-89225-87891
	 * order_id : ORDER-12345
	 * trans_date : 2012-04-03 16:02:02
	 * redirect_url : https://server_3ds.com/3ds.php
	 * redirect_params : {"PaReq":"bc5865698ae46de4eba4c51f0359a714","MD":"111111111111111111111","TermUrl":"https://term_url.com/3ds/67c14e5?trans_id=03346-8922587891&hash=8b98db60fb3c24c14a6d7075241da38b"}
	 * redirect_method : POST
	 */

	/**
	 * URL to which the Client should redirect the Customer
	 */
	@SerializedName(REDIRECT_URL)
	private String mRedirectUrl;

	/**
	 * Holder of specific 3DS redirect parameters
	 * <p>
	 * See also {@link PlatonRedirectParams}
	 */
	@SerializedName(REDIRECT_PARAMS)
	private PlatonRedirectParams mPlatonRedirectParams;

	/**
	 * The method of transferring parameters
	 * <p>
	 * See also {@link PlatonRedirectMethod}
	 */
	@PlatonRedirectMethod
	@SerializedName(REDIRECT_METHOD)
	private String mRedirectMethod;

	protected PlatonSale3DSecure(final Parcel in) {
		super(in);
		mRedirectUrl = in.readString();
		mPlatonRedirectParams = in.readParcelable(PlatonRedirectParams.class.getClassLoader());
		mRedirectMethod = in.readString();
	}

	@PlatonStatus
	public String getStatus() {
		return mStatus;
	}

	public void setStatus(@PlatonStatus final String status) {
		mStatus = status;
	}

	public String getRedirectUrl() {
		return mRedirectUrl;
	}

	public void setRedirectUrl(final String redirectUrl) {
		mRedirectUrl = redirectUrl;
	}

	public PlatonRedirectParams getPlatonRedirectParams() {
		return mPlatonRedirectParams;
	}

	public void setPlatonRedirectParams(final PlatonRedirectParams platonRedirectParams) {
		mPlatonRedirectParams = platonRedirectParams;
	}

	@PlatonRedirectMethod
	public String getRedirectMethod() {
		return mRedirectMethod;
	}

	public void setRedirectMethod(@PlatonRedirectMethod final String redirectMethod) {
		mRedirectMethod = redirectMethod;
	}

	@Override
	public void writeToParcel(final Parcel parcel, final int i) {
		super.writeToParcel(parcel, i);
		parcel.writeString(mRedirectUrl);
		parcel.writeParcelable(mPlatonRedirectParams, i);
		parcel.writeString(mRedirectMethod);
	}

	@NonNull
	@Override
	public String toString() {
		return "PlatonSale3DSecure{" +
				"mAction='" + mAction + '\'' +
				", mResult='" + mResult + '\'' +
				", mStatus='" + mStatus + '\'' +
				", mOrderId='" + mOrderId + '\'' +
				", mTransactionId='" + mTransactionId + '\'' +
				", mTransactionDate='" + mTransactionDate + '\'' +
				", mRedirectUrl='" + mRedirectUrl + '\'' +
				", mPlatonRedirectParams=" + mPlatonRedirectParams +
				", mRedirectMethod='" + mRedirectMethod + '\'' +
				'}';
	}
}
