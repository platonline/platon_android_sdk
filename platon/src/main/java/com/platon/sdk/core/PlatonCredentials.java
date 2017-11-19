package com.platon.sdk.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.platon.sdk.constant.PlatonSdkConstants;
import com.platon.sdk.constant.PlatonSdkConstants.Credentials;
import com.platon.sdk.constant.PlatonSdkConstants.SharedPrefs;
import com.platon.sdk.exception.PlatonSdkNotInitializedException;

import java.io.File;

import static com.platon.sdk.constant.PlatonSdkConstants.Credentials.CLIENT_PASS;
import static com.platon.sdk.constant.PlatonSdkConstants.Credentials.PAYMENT_URL;

/**
 * Class which holds all Platon credentials which was passed from {@link PlatonSdk init(...)} methods
 * <p>
 * If even one of required Platon credentials is not presented
 * the {@link PlatonSdkNotInitializedException} will occurs
 * <p>
 * See {@link PlatonSdk} for more details
 */
public class PlatonCredentials {

	/**
	 * See {@link PlatonSdkConstants.Credentials#CLIENT_KEY}
	 */
	private static String sClientKey;
	/**
	 * {@link PlatonSdkConstants.Credentials#CLIENT_PASS}
	 */
	private static String sClientPass;
	/**
	 * {@link PlatonSdkConstants.Credentials#PAYMENT_URL}
	 */
	private static String sPaymentUrl;

	/**
	 * {@link PlatonSdkConstants.Credentials#TERM_URL_3DS}
	 */
	private static String sTermUrl3Ds;

	/**
	 * Provide Platon SDK credentials and store them here
	 *
	 * @param clientKey  - client key
	 * @param clientPass - client password
	 * @param paymentUrl - payment url
	 * @param termUrl3Ds - url for 3DSecure supported account
	 */
	static void init(
			@NonNull final String clientKey,
			@NonNull final String clientPass,
			@NonNull final String paymentUrl,

			@Nullable final String termUrl3Ds
	) {
		setClientKey(clientKey);
		setClientPass(clientPass);
		setPaymentUrl(paymentUrl);
		setTermUrl3Ds(termUrl3Ds);
	}

	@NonNull
	public static String getClientKey() {
		PlatonSdk.checkIsInited();

		if (sClientKey == null) {
			final String clientKey = CredentialsStorage.getCredential(Credentials.CLIENT_KEY);
			if (TextUtils.isEmpty(clientKey)) PlatonSdkNotInitializedException.invoke();
			else setClientKey(clientKey);
		}

		return sClientKey;
	}

	private static void setClientKey(@NonNull final String clientKey) {
		sClientKey = clientKey;
		CredentialsStorage.setCredential(Credentials.CLIENT_KEY, clientKey);
	}

	@NonNull
	public static String getClientPass() {
		PlatonSdk.checkIsInited();

		if (sClientPass == null) {
			final String clientPass = CredentialsStorage.getCredential(CLIENT_PASS);
			if (TextUtils.isEmpty(clientPass)) PlatonSdkNotInitializedException.invoke();
			else setClientPass(clientPass);
		}

		return sClientPass;
	}

	private static void setClientPass(@NonNull final String clientPass) {
		sClientPass = clientPass;
		CredentialsStorage.setCredential(CLIENT_PASS, clientPass);
	}

	@NonNull
	public static String getPaymentUrl() {
		PlatonSdk.checkIsInited();

		if (sPaymentUrl == null) {
			final String paymentUrl = CredentialsStorage.getCredential(PAYMENT_URL);
			if (TextUtils.isEmpty(paymentUrl)) PlatonSdkNotInitializedException.invoke();
			else setPaymentUrl(paymentUrl);
		}

		return sPaymentUrl;
	}

	private static void setPaymentUrl(@NonNull final String paymentUrl) {
		if (paymentUrl.charAt(paymentUrl.length() - 1) == File.separatorChar)
			sPaymentUrl = paymentUrl;
		else sPaymentUrl = paymentUrl.concat(String.valueOf(File.separatorChar));

		CredentialsStorage.setCredential(PAYMENT_URL, sPaymentUrl);
	}

	@NonNull
	public static String getTermUrl3Ds() {
		PlatonSdk.checkIsInited();

		if (sTermUrl3Ds == null) setTermUrl3Ds(CredentialsStorage.getCredential(Credentials.TERM_URL_3DS));

		return sTermUrl3Ds;
	}

	private static void setTermUrl3Ds(@Nullable final String termUrl3Ds) {
		sTermUrl3Ds = termUrl3Ds;
		CredentialsStorage.setCredential(Credentials.TERM_URL_3DS, termUrl3Ds);
	}

	/**
	 * Stands for recovering {@link PlatonCredentials} fields
	 */
	private final static class CredentialsStorage {

		private static SharedPreferences getSharedPrefs() {
			return PlatonSdk.getAppContext()
					.getSharedPreferences(SharedPrefs.SHARED_PREFS, Context.MODE_PRIVATE);
		}

		private static void setCredential(@NonNull final String key, @Nullable final String value) {
			final SharedPreferences.Editor editor = getSharedPrefs().edit();
			editor.putString(key, value);
			editor.apply();
		}

		@Nullable
		private static String getCredential(@NonNull final String key) {
			return getSharedPrefs().getString(key, null);
		}

	}

}
