package com.platon.sdk.constant;

import com.platon.sdk.constant.api.action.PlatonAction;
import com.platon.sdk.core.PlatonCredentials;
import com.platon.sdk.core.PlatonSdk;

/**
 * General SDK constants for outer and inner usages
 * <p>
 * See {@link PlatonSdk} and {@link PlatonCredentials} for the details
 */
public interface PlatonSdkConstants {

	/**
	 * Used as meta-data name for initialization
	 * <p>
	 * See {@link PlatonCredentials} for more details and its usages
	 */
	interface Credentials {
		/**
		 * Unique key to identify the account in Payment Platform (used as request parameter)
		 */
		String CLIENT_KEY = "com.platon.sdk.CLIENT_KEY";

		/**
		 * Password for Client authentication in Payment Platform (used for calculating hash parameter)
		 */
		String CLIENT_PASS = "com.platon.sdk.CLIENT_PASS";

		/**
		 * URL to request the Payment Platform
		 */
		String PAYMENT_URL = "com.platon.sdk.PAYMENT_URL";

		/**
		 * URL to which Customer should be returned after 3D-Secure
		 * This field is (Required) when your account support 3DSecure (string up to 1024 symbols)
		 * Used as request parameter in {@link PlatonAction#SALE}.
		 */
		String TERM_URL_3DS = "com.platon.sdk.TERM_URL_3DS";
	}

	interface SharedPrefs {
		/**
		 * Used as SharedPreferences context name in {@link PlatonCredentials}
		 */
		String SHARED_PREFS = "PLATON_SHARED_PREFS";

		/**
		 * Used as init identifier in {@link PlatonSdk}
		 */
		String IS_INITED = "IS_INITED";
	}

}
