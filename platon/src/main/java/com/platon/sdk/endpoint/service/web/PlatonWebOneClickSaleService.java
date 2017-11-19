package com.platon.sdk.endpoint.service.web;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Size;

import com.platon.sdk.constant.api.PlatonApiConstants.MethodProperties;
import com.platon.sdk.endpoint.adapter.web.PlatonWebOneClickSaleAdapter;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static com.platon.sdk.constant.api.PlatonApiConstants.CalledMethod.BASE;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.LANG;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT_MIN;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT_SHORT;

/**
 * API service for creating procedures and WebPaymentsOneClick protocol transaction
 * <p>
 * See {@link PlatonWebOneClickSaleAdapter} for it realisation
 */
public interface PlatonWebOneClickSaleService {

	@FormUrlEncoded
	@POST(BASE)
	Call<String> oneClickSale(
			@Field(MethodProperties.KEY) @NonNull final String key,
			@Field(MethodProperties.PAYMENT) @NonNull final String payment,
			@Field(MethodProperties.DATA) @NonNull final String data,
			@Field(MethodProperties.URL) @NonNull final String url,
			@Field(MethodProperties.RC_ID) @NonNull final String rcId,
			@Field(MethodProperties.RC_TOKEN) @NonNull @Size(max = TEXT_SHORT) final String rcToken,

			@Field(MethodProperties.ORDER) @Nullable @Size(max = TEXT_MIN) final String order,
			@Field(MethodProperties.LANG) @Nullable @Size(max = LANG) final String language,
			@Field(MethodProperties.ERROR_URL) @Nullable final String errorUrl,
			@Field(MethodProperties.FORM_ID) @Nullable final String formId,
			@Field(MethodProperties.EXT_1) @Nullable final String ext1,
			@Field(MethodProperties.EXT_2) @Nullable final String ext2,
			@Field(MethodProperties.EXT_3) @Nullable final String ext3,
			@Field(MethodProperties.EXT_4) @Nullable final String ext4,

			@Field(MethodProperties.SIGN) @NonNull final String sign
	);

}
