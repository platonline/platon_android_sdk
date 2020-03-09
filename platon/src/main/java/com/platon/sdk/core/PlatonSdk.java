package com.platon.sdk.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import android.text.TextUtils;

import com.platon.sdk.constant.PlatonSdkConstants;
import com.platon.sdk.constant.PlatonSdkConstants.Credentials;
import com.platon.sdk.constant.PlatonSdkConstants.SharedPrefs;
import com.platon.sdk.constant.api.action.PlatonAction;
import com.platon.sdk.endpoint.adapter.post.PlatonCaptureAdapter;
import com.platon.sdk.endpoint.adapter.post.PlatonCreditVoidAdapter;
import com.platon.sdk.endpoint.adapter.post.PlatonRecurringAdapter;
import com.platon.sdk.endpoint.adapter.post.PlatonSaleAdapter;
import com.platon.sdk.endpoint.adapter.post.PlatonScheduleAdapter;
import com.platon.sdk.endpoint.adapter.post.PlatonTransactionAdapter;
import com.platon.sdk.endpoint.adapter.web.PlatonWebOneClickSaleAdapter;
import com.platon.sdk.endpoint.adapter.web.PlatonWebRecurringAdapter;
import com.platon.sdk.endpoint.adapter.web.PlatonWebSaleAdapter;
import com.platon.sdk.endpoint.adapter.web.PlatonWebScheduleAdapter;
import com.platon.sdk.endpoint.adapter.web.PlatonWebTokenSaleAdapter;
import com.platon.sdk.exception.PlatonSdkNotInitializedException;
import com.platon.sdk.model.request.PlatonCard;

/**
 * The start point of usage Platon SDK is here
 * <p>
 * <p>
 * Before you get an account to access Payment Platform (Platon SDK),
 * you must provide the following data to the Payment Platform administrator:
 * IP list       - List of your IP addresses, from which requests to Payment Platform will be sent
 * Callback URL  - URL which will be receiving the notifications of the processing results of your request to Payment Platform
 * Contact email - Client’s contact email
 * <p>
 * Note for Post client:
 * Callback URL is mandatory if you work in asynchronous mode, or if your account
 * supports 3D-Secure. The length of Callback URL shouldn’t be more than 255 symbols.
 * With all Payment Platform POST requests at Callback URL the Client must return the
 * string "OK" if he successfully received data or return "ERROR".
 * <p>
 * Note for Web client:
 * The Client must provide an URL callback (Callback) to which the notifications will be sent
 * in case of successfully completed payments as well as the refunds and chargeback
 * notices. In any case when the system send a request to Callback URL, it should return
 * HTTP 200 code, otherwise the system will try to send a request again up to 5 times.
 * <p>
 * You should get the following information from administrator and
 * set them in AndroidManifest.xml to begin working with the Payment Platform:
 * {@link PlatonSdkConstants.Credentials#CLIENT_KEY} - client key
 * {@link PlatonSdkConstants.Credentials#CLIENT_PASS} - client password
 * {@link PlatonSdkConstants.Credentials#PAYMENT_URL} - payment url
 * <p>
 * Next field is required when your account support 3DSecure:
 * {@link PlatonSdkConstants.Credentials#TERM_URL_3DS} - user 3ds callback url
 * <p>
 * Then you should call {@link #init(Context)} to init Platon SDK
 * <p>
 * If above mentioned fields is not provided in AndroidManifest.xml of you project the
 * {@link PlatonSdkNotInitializedException#invoke()} will be called
 * <p>
 * Or you can init this SDK with following methods {@link #init(Context, String, String, String)}
 * or if your account support 3DSecure {@link #init(Context, String, String, String, String)}
 * <p>
 * <p>
 * To test/simulate your requests to Platon Payment System use {@link PlatonCard.Test} environment
 */
@SuppressWarnings("DanglingJavadoc")
public final class PlatonSdk {

	/**
	 * This is an instance of ApplicationContext of the app, from where it was inited
	 */
	@SuppressLint("StaticFieldLeak")
	private static Context sAppContext;

	/**
	 * Stands for indicating is Platon SDK was inited
	 */
	private static Boolean sIsInited;

	/**
	 * This method initiate initializing of Platon SDK which grab Platon SDK credentials from AndroidManifest.xml
	 *
	 * @param context - app context
	 */
	public static void init(@NonNull final Context context) {
		sAppContext = context;

		boolean isInited = false;
		try {
			// Fetching meta data from main package
			final Bundle metaData = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA)
					.metaData;

			if (metaData != null) {
				final String clientKey = metaData.getString(Credentials.CLIENT_KEY);
				final String clientPass = metaData.getString(Credentials.CLIENT_PASS);
				final String paymentUrl = metaData.getString(Credentials.PAYMENT_URL);
				final String termUrl3Ds = metaData.getString(Credentials.TERM_URL_3DS);

				if (!TextUtils.isEmpty(clientKey) &&
						!TextUtils.isEmpty(clientPass) &&
						!TextUtils.isEmpty(paymentUrl)) {
					PlatonCredentials.init(clientKey, clientPass, paymentUrl, termUrl3Ds);
					isInited = true;
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
			isInited = false;
		} finally {
			setInited(isInited);
			checkIsInited();
		}
	}

	/**
	 * This method initiate initializing of Platon SDK from code
	 *
	 * @param context    - app context
	 * @param clientKey  - client key
	 *                   See {@link PlatonCredentials#sClientKey}
	 * @param clientPass - client password
	 *                   See {@link PlatonCredentials#sClientPass}
	 * @param paymentUrl - payment url
	 *                   See {@link PlatonCredentials#sPaymentUrl}
	 */
	public static void init(
			@NonNull final Context context,
			@NonNull final String clientKey,
			@NonNull final String clientPass,
			@NonNull final String paymentUrl
	) {
		init(context, clientKey, clientPass, paymentUrl, null);
	}

	/**
	 * This method initiate initializing of Platon SDK from code
	 *
	 * @param context    - app context
	 * @param clientKey  - client key
	 *                   See {@link PlatonCredentials#sClientKey}
	 * @param clientPass - client password
	 *                   See {@link PlatonCredentials#sClientPass}
	 * @param paymentUrl - payment url
	 *                   See {@link PlatonCredentials#sPaymentUrl}
	 * @param termUrl3Ds - url for 3DSecure supported account
	 *                   See {@link PlatonCredentials#sTermUrl3Ds}
	 */
	public static void init(
			@NonNull final Context context,
			@NonNull final String clientKey,
			@NonNull final String clientPass,
			@NonNull final String paymentUrl,

			@Nullable final String termUrl3Ds
	) {
		sAppContext = context;

		boolean isInited = false;
		if (!TextUtils.isEmpty(clientKey) && !TextUtils.isEmpty(clientPass) && !TextUtils.isEmpty(paymentUrl)) {
			PlatonCredentials.init(clientKey, clientPass, paymentUrl, termUrl3Ds);
			isInited = true;
		}

		setInited(isInited);
		checkIsInited();
	}

	static Context getAppContext() {
		return sAppContext;
	}

	/**
	 * Brief description of the interaction with Post Payment Platform:
	 * <p>
	 * 1. For the transaction, you must send the server to server HTTPS POST request with
	 * fields listed below to Payment Platform URL (PAYMENT_URL). In response
	 * Payment Platform will return the JSON (http://json.org/) encoded string.
	 * <p>
	 * 2. If your account supports 3D-Secure and credit card supports 3D-Secure, then
	 * Payment Platform will return the link to the 3D-Secure Access Control Server to
	 * 3 perform 3D-Secure verification. In this case, you need to redirect the card-holder at
	 * this link. If there are also some parameters except the link in the result, you will
	 * need to redirect the cardholder at this link together with the parameters using the
	 * method of data transmitting indicated in the same result.
	 * <p>
	 * 3. In the case of 3D-Secure after verification on the side of the 3D-Secure server, the
	 * owner of a credit card will come back to your site using the link you specify in the
	 * sale request, and Payment Platform will return the result of transaction processing
	 * to the Callback URL action.
	 */
	public static class PostPayments {

		/**
		 * @return adapter for {@link PlatonAction#CAPTURE} request
		 */
		public static PlatonCaptureAdapter getCaptureAdapter() {
			checkIsInited();
			return PlatonCaptureAdapter.getInstance();
		}

		/**
		 * @return adapter for {@link PlatonAction#CREDITVOID} request
		 */
		public static PlatonCreditVoidAdapter getCreditVoidAdapter() {
			checkIsInited();
			return PlatonCreditVoidAdapter.getInstance();
		}

		/**
		 * @return adapter for {@link PlatonAction#RECURRING_SALE} request
		 */
		public static PlatonRecurringAdapter getRecurringAdapter() {
			checkIsInited();
			return PlatonRecurringAdapter.getInstance();
		}

		/**
		 * @return adapter for {@link PlatonAction#SALE} request
		 */
		public static PlatonSaleAdapter getSaleAdapter() {
			checkIsInited();
			return PlatonSaleAdapter.getInstance();
		}

		/**
		 * @return adapter for {@link PlatonAction#SCHEDULE} and {@link PlatonAction#DESCHEDULE} requests
		 */
		public static PlatonScheduleAdapter getScheduleAdapter() {
			checkIsInited();
			return PlatonScheduleAdapter.getInstance();
		}

		/**
		 * @return adapter for {@link PlatonAction#GET_TRANS_DETAILS}  and {@link PlatonAction#GET_TRANS_STATUS} requests
		 */
		public static PlatonTransactionAdapter getTransactionAdapter() {
			checkIsInited();
			return PlatonTransactionAdapter.getInstance();
		}
	}

	/**
	 * Brief description of the interaction with Web Payment Platform:
	 * <p>
	 * To initiate transaction Client must prepare HTML form data according to this document and
	 * submit these fields as POST in Payer's browser to Payment Platform URL
	 * (PAYMENT_URL).
	 * <p>
	 * If transaction requires 3D-Secure or any other kind of verification procedures, Client don't
	 * need to change anything in request, as all verification processes would be managed using
	 * Hosted Payment Page on the Payment Platform's side.
	 * <p>
	 * After the successful payment the Payer's browser will be redirected to the URL, which was
	 * specified during payment request and the parameter “order” will be sent to this URL by the
	 * GET method.
	 */
	public static class WebPayments {

		/**
		 * @return adapter for web sale requests
		 */
		public static PlatonWebSaleAdapter getSaleAdapter() {
			checkIsInited();
			return PlatonWebSaleAdapter.getInstance();
		}

		/**
		 * @return adapter for web token sale requests
		 */
		public static PlatonWebTokenSaleAdapter getTokenSaleAdapter() {
			checkIsInited();
			return PlatonWebTokenSaleAdapter.getInstance();
		}

		/**
		 * @return adapter for web one-click sale requests
		 */
		public static PlatonWebOneClickSaleAdapter getOneClickSaleAdapter() {
			checkIsInited();
			return PlatonWebOneClickSaleAdapter.getInstance();
		}

		/**
		 * @return adapter for web recurring sale requests
		 */
		public static PlatonWebRecurringAdapter getRecurringAdapter() {
			checkIsInited();
			return PlatonWebRecurringAdapter.getInstance();
		}

		/**
		 * @return adapter for web schedule and deschedule options setting requests
		 */
		public static PlatonWebScheduleAdapter getScheduleAdapter() {
			checkIsInited();
			return PlatonWebScheduleAdapter.getInstance();
		}

	}

	/**
	 * Check for Platon SDK readiness
	 * {@link PlatonSdkNotInitializedException#invoke()} called when is not initialized
	 *
	 * @return is Platon SDK initialized
	 */
	public static boolean isInited() {
		if (sIsInited == null) {
			if (sAppContext == null) PlatonSdkNotInitializedException.invoke();
			else setInited(IsInitedStorage.isInited());
		}
		return sIsInited;
	}

	private static void setInited(final boolean inited) {
		sIsInited = inited;
		IsInitedStorage.setInited(inited);
	}

	static void checkIsInited() {
		if (!isInited()) PlatonSdkNotInitializedException.invoke();
	}

	/**
	 * Stands for recovering state of {@link #sIsInited} field
	 */
	private final static class IsInitedStorage {

		private static SharedPreferences getSharedPrefs() {
			return sAppContext.getSharedPreferences(SharedPrefs.SHARED_PREFS, Context.MODE_PRIVATE);
		}

		private static boolean isInited() {
			return getSharedPrefs().getBoolean(SharedPrefs.IS_INITED, false);
		}

		private static void setInited(final boolean inited) {
			final SharedPreferences.Editor editor = getSharedPrefs().edit();
			editor.putBoolean(SharedPrefs.IS_INITED, inited);
			editor.apply();
		}

	}
}

