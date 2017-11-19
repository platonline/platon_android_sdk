package com.platon.sdk.endpoint.adapter;

import android.support.annotation.NonNull;

import com.google.gson.GsonBuilder;
import com.platon.sdk.callback.PlatonBaseCallback;
import com.platon.sdk.callback.PlatonSaleCallback;
import com.platon.sdk.constant.api.PlatonApiConstants;
import com.platon.sdk.core.PlatonCredentials;
import com.platon.sdk.deserializer.PlatonBaseDeserializer;
import com.platon.sdk.endpoint.adapter.post.PlatonSaleAdapter;
import com.platon.sdk.model.response.base.PlatonBasePayment;
import com.platon.sdk.model.response.base.PlatonBaseResponse;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit adapter which unify all logic between its service
 * with a {@link PlatonBaseDeserializer} and {@link PlatonBaseCallback} extensions
 * <p>
 * Platon SDK adapter automatically parse all date string of models
 * to {@link java.util.Date} by {@link PlatonApiConstants.Formats.Date#PATTERN} pattern
 *
 * @param <Service> - specified service for the specified adapter
 */
public abstract class PlatonBaseAdapter<Service> {

	protected final Service mService;

	protected PlatonBaseAdapter() {
		final HttpLoggingInterceptor bodyLoggingInterceptor = new HttpLoggingInterceptor();
		bodyLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

		final OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
		okHttpClient.addInterceptor(bodyLoggingInterceptor);
		configureOkHttpClient(okHttpClient);

		final GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setPrettyPrinting();
		gsonBuilder.setDateFormat(PlatonApiConstants.Formats.Date.PATTERN);
		configureGson(gsonBuilder);

		final Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
				.addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
				.client(okHttpClient.build())
				.baseUrl(getBaseUrl());
		configureRetrofit(retrofitBuilder);

		mService = retrofitBuilder.build().create(getServiceClass());
	}

	protected abstract Class<Service> getServiceClass();

	protected String getBaseUrl() {
		return PlatonCredentials.getPaymentUrl();
	}

	@SuppressWarnings("WeakerAccess")
	protected void configureOkHttpClient(@NonNull final OkHttpClient.Builder builder) {
		// stub
	}

	protected void configureGson(@NonNull final GsonBuilder builder) {
		// stub
	}

	@SuppressWarnings("WeakerAccess")
	protected void configureRetrofit(@NonNull final Retrofit.Builder builder) {
		// stub
	}

	/**
	 * See {@link #enqueueWithCallback(PlatonBaseCallback, OnConfigureResponseListener)} for method description
	 */
	protected <PlatonCallback extends PlatonBaseCallback<PlatonPayment>, PlatonPayment extends PlatonBasePayment>
	Callback enqueueWithCallback(final PlatonCallback callback) {
		return enqueueWithCallback(callback, null);
	}

	/**
	 * Enqueue Retrofit callback with {@param callback} for configuring and receiving Platon SDK
	 * required and optional callback which presented in {@param callback}
	 *
	 * @param callback                    - Platon SDK callback
	 * @param onConfigureResponseListener - custom response configurator listener
	 * @return Retrofit callback with inside {@param callback}
	 */
	@SuppressWarnings("unchecked")
	protected <PlatonCallback extends PlatonBaseCallback<PlatonPayment>, PlatonPayment extends PlatonBasePayment>
	Callback enqueueWithCallback(
			final PlatonCallback callback,
			final OnConfigureResponseListener<PlatonCallback, PlatonPayment> onConfigureResponseListener
	) {
		return new Callback<PlatonBaseResponse>() {

			@Override
			public void onResponse(final Call<PlatonBaseResponse> call, final Response<PlatonBaseResponse> response) {
				if (callback == null) return;

				final PlatonBaseResponse baseResponse = response.body();
				if (baseResponse == null) {
					onFailure(call, new NullPointerException());
					return;
				}

				if (baseResponse.getPlatonApiError() == null) {
					if (onConfigureResponseListener == null)
						callback.onResponse(call, (PlatonPayment) baseResponse.getPlatonResponse());
					else onConfigureResponseListener.onConfiguredResponse(callback, call, baseResponse);
				} else callback.onError(call, baseResponse.getPlatonApiError());
			}

			@Override
			public void onFailure(final Call<PlatonBaseResponse> call, final Throwable t) {
				if (callback != null) callback.onFailure(call, t);
			}

		};
	}

	/**
	 * Listener which used to override configuring callback logic in adapter
	 * {@link #enqueueWithCallback(PlatonBaseCallback, OnConfigureResponseListener)}
	 * <p>
	 * For example, see {@link PlatonSaleAdapter#onConfiguredResponse(PlatonSaleCallback, Call, PlatonBaseResponse)}
	 */
	public interface OnConfigureResponseListener
			<PlatonCallback extends PlatonBaseCallback<PlatonPayment>, PlatonPayment extends PlatonBasePayment> {
		void onConfiguredResponse(final PlatonCallback callback, final Call call, final PlatonBaseResponse response);
	}

}
