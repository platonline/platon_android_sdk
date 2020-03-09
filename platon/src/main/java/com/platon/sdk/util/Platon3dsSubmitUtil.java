package com.platon.sdk.util;

import androidx.annotation.NonNull;
import android.text.TextUtils;

import com.platon.sdk.constant.api.PlatonApiConstants.SerializedNames;
import com.platon.sdk.constant.api.PlatonApiConstants.UrlEncodedContentType;
import com.platon.sdk.endpoint.adapter.post.PlatonSaleAdapter;
import com.platon.sdk.model.response.sale.PlatonRedirectParams;
import com.platon.sdk.model.response.sale.PlatonSale3DSecure;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Util class which used for submitting data after successful 3DSecure request
 * <p>
 * See {@link PlatonSaleAdapter} and {@link PlatonSale3DSecure}
 */
public class Platon3dsSubmitUtil {

	/**
	 * Get your Submit3dsData HTML page
	 *
	 * @param sale3dSecure            - model by which content page will be generated after successful call
	 * @param onSubmit3dsDataListener - listener which gives a HTML Submit3dsData content
	 */
	@SuppressWarnings("ConstantConditions")
	public static void submit3dsData(
			@NonNull final PlatonSale3DSecure sale3dSecure,
			@NonNull final OnSubmit3dsDataListener onSubmit3dsDataListener
	) {
		if (sale3dSecure == null) return;
		if (onSubmit3dsDataListener == null) return;

		final String redirectUrl = sale3dSecure.getRedirectUrl();
		final String redirectMethod = sale3dSecure.getRedirectMethod();
		final PlatonRedirectParams platonRedirectParams = sale3dSecure.getPlatonRedirectParams();

		if (TextUtils.isEmpty(redirectUrl) || TextUtils.isEmpty(redirectMethod) || platonRedirectParams == null) return;

		final RequestBody formBody = new FormBody.Builder()
				.addEncoded(SerializedNames.PA_REQ, platonRedirectParams.getPaymentRequisites())
				.addEncoded(SerializedNames.MD, platonRedirectParams.getMd())
				.addEncoded(SerializedNames.TERM_URL, platonRedirectParams.getTermUrl())
				.build();

		final Request request = new Request.Builder()
				.header(UrlEncodedContentType.NAME, UrlEncodedContentType.VALUE)
				.url(redirectUrl)
				.method(redirectMethod, formBody)
				.build();

		new OkHttpClient().newCall(request).enqueue(
				new Callback() {
					@Override
					public void onFailure(final Call call, final IOException e) {
						onSubmit3dsDataListener.onFailure(e);
					}

					@Override
					public void onResponse(final Call call, final Response response) throws IOException {
						try {
							onSubmit3dsDataListener.on3dsDataSubmitted(response.body().string());
						} catch (final Exception e) {
							onSubmit3dsDataListener.onFailure(e);
						}
					}
				}
		);
	}

	/**
	 * Interface which gives a HTML page with content which based on {@link PlatonSale3DSecure} model
	 */
	public interface OnSubmit3dsDataListener {
		void on3dsDataSubmitted(final String htmlData);

		void onFailure(final Throwable throwable);
	}

}
