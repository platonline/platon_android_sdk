package com.platon.sdk.constant.api;

import com.platon.sdk.constant.PlatonSdkConstants;
import com.platon.sdk.constant.PlatonSdkConstants.Credentials;
import com.platon.sdk.constant.api.action.PlatonAction;
import com.platon.sdk.constant.api.action.PlatonRequestAction;
import com.platon.sdk.core.PlatonCredentials;
import com.platon.sdk.core.PlatonSdk;
import com.platon.sdk.endpoint.adapter.post.PlatonScheduleAdapter;
import com.platon.sdk.endpoint.adapter.web.PlatonWebRecurringAdapter;
import com.platon.sdk.endpoint.adapter.web.PlatonWebSaleAdapter;
import com.platon.sdk.model.request.PlatonCard;
import com.platon.sdk.model.request.option.schedule.PlatonScheduleOptions;
import com.platon.sdk.model.request.order.product.PlatonProductSale;
import com.platon.sdk.model.response.sale.PlatonRedirectParams;
import com.platon.sdk.model.response.sale.PlatonSale3DSecure;
import com.platon.sdk.model.response.transaction.PlatonTransaction;
import com.platon.sdk.util.Platon3dsSubmitUtil;
import com.platon.sdk.util.PlatonBase64Util;
import com.platon.sdk.util.PlatonHashUtil;

/**
 * Holder of all constants which used in all classes that have links to API part
 */
public interface PlatonApiConstants {

	/**
	 * Used as format and limits variables in requests
	 */
	interface Formats {

		/**
		 * Date format in the Payment Platform
		 * <p>
		 * Format: YYYY-MM-DD HH:mm:ss ("2012-04-03 16:02:02")
		 */
		interface Date {
			String PATTERN = "yyyy-MM-dd HH:mm:ss";
		}

		/**
		 * See throughout Service classes for its usages
		 */
		interface General {
			int TEXT_MIN = 30;
			int TEXT_SHORT = 32;
			int TEXT = 255;
			int TEXT_LONG = 1024;
			int TEXT_DATE = 10;

			int LANG = 2;
			int CHANNEL = 16;
			int CURRENCY_CODE = 3;
			int COUNTRY_CODE = 2;
		}

		/**
		 * See {@link PlatonCard} for more details
		 */
		interface Card {
			int MIN_CARD_NUMBER = 12;
			int MAX_CARD_NUMBER = 19;

			int MIN_CVV = 3;
			int MAX_CVV = 4;

			int MIN_MONTH = 1;
			int MAX_MONTH = 12;

			int MIN_YEAR = 1000;
			int MAX_YEAR = 9999;

			int MONTH_FORMAT = 2;
			int YEAR_FORMAT = 4;

			String MONTH_FORMAT_PATTERN = "00";
		}

		/**
		 * See {@link PlatonScheduleOptions} and
		 * {@link PlatonScheduleAdapter} for more details
		 */
		interface Period {
			int ASAP = 0;
			int MAX_DELAY = 999; /*365?*/

			int MIN_PERIOD = 1;
			int MAX_PERIOD = 999; /*365?*/

			int UNLIMITED_REPEAT = 0;
			int MAX_REPEAT = 999;
		}

		/**
		 * See {@link //PlatonPayer} for more details
		 */
		interface State {
			int STATE = 2;
			String NA = "NA";
		}

		/**
		 * See throughout Service and Adapter classes for its usages
		 */
		interface Amount {
			int MIN_FORMAT_AMOUNT = 4;
			int MAX_FORMAT_AMOUNT = 7;

			float MIN_AMOUNT = 0.00F;
			float MAX_AMOUNT = 9999.99F;

			int MIN_INTEGER_DIGITS = 1;
			int FRACTION_DIGITS = 2;
		}

		/**
		 * See {@link //PlatonPayer} for more details
		 */
		interface Payer {
			int COUNTRY_CODE = 2;
			int EMAIL = 256;

			int MIN_IP = 7;
			int MAX_IP = 45;
		}

		/**
		 * Payment method code
		 * <p>
		 * CC – for payment cards
		 * RF – for one-click payment
		 * <p>
		 * See {@link PlatonWebSaleAdapter} for usages
		 */
		interface Payment {
			String CC = "CC";
			String RF = "RF";
			String CCT = "CCT";
		}
	}

	/**
	 * "*" - against field means that it is REQUIRED by request
	 */
	interface MethodProperties {
		/**
		 * Value : {@link PlatonRequestAction}
		 */
		String ACTION = "action"; //*

		/**
		 * Asynchronous or synchronous mode
		 * Used in conjunction with {@link PlatonOption} only. Default {@link PlatonOption#NO}
		 * <p>
		 * Value : {@link PlatonRequestAction}
		 */
		String ASYNC = "async";

		/**
		 * Unique client key which is stored in {@link PlatonCredentials}
		 * <p>
		 * See {@link PlatonSdk} for more details
		 */
		String CLIENT_KEY = "client_key"; //*

		/**
		 * Payment channel (Sub-account)
		 * <p>
		 * Value: String up to 16 characters
		 */
		String CHANNEL_ID = "channel_id"; //*

		/**
		 * PlatonTransaction ID in the Clients system
		 * <p>
		 * Value: String up to 255 characters
		 */
		String ORDER_ID = "order_id"; //*

		/**
		 * The amount of the transaction
		 * <p>
		 * Value: Numbers in the form XXXX.XX (without leading zeros)
		 */
		String ORDER_AMOUNT = "order_amount"; //*

		/**
		 * The amount of the transaction
		 * <p>
		 * Value: Currency 3-letter code (ISO 4217).
		 */
		String ORDER_CURRENCY = "order_currency"; //*

		/**
		 * Description of the transaction (product name)
		 * <p>
		 * Value: String up to 1024 characters
		 */
		String ORDER_DESCRIPTION = "order_description"; //*

		/**
		 * Credit PlatonCard
		 * <p>
		 * https://en.wikipedia.org/wiki/Payment_card_number
		 * <p>
		 * Value: Number
		 */
		String CARD_NUMBER = "card_number"; //*

		/**
		 * Month of expiry of the credit card
		 * <p>
		 * Value: Month in the form XX (begin with 1)
		 */
		String CARD_EXP_MONTH = "card_exp_month"; //*

		/**
		 * Year of expiry of the credit card
		 * <p>
		 * Value: Year in the form XXXX
		 */
		String CARD_EXP_YEAR = "card_exp_year"; //*

		/**
		 * CVV/CVC2 credit card verification
		 * <p>
		 * Value: 3-4 symbols
		 */
		String CARD_CVV2 = "card_cvv2"; //*

		/**
		 * Customer’s first name
		 * <p>
		 * Value: String up to 32 characters
		 */
		String PAYER_FIRST_NAME = "payer_first_name"; //*

		/**
		 * Customer’s surname
		 * <p>
		 * Value: String up to 32 characters
		 */
		String PAYER_LAST_NAME = "payer_last_name"; //*

		/**
		 * Customer’s middle name
		 * <p>
		 * Value: String up to 32 characters
		 */
		String PAYER_MIDDLE_NAME = "payer_middle_name"; //*

		/**
		 * Customer’s birthday
		 * <p>
		 * Value: String format yyyy-MM-dd
		 */
		String PAYER_BIRTHDAY = "payer_birthday"; //*

		/**
		 * Customer’s address
		 * <p>
		 * Value: String up to 255 characters
		 */
		String PAYER_ADDRESS = "payer_address"; //*

		/**
		 * Customer’s country
		 * <p>
		 * Value: country 2-letter code (ISO 3166-1 alpha-2)
		 */
		String PAYER_COUNTRY = "payer_country"; //*

		/**
		 * Customer’s state
		 * <p>
		 * https://en.wikipedia.org/wiki/List_of_U.S._state_abbreviations
		 * <p>
		 * Value: 2-letter code (or {@link Formats.State#NA} for countries without states)
		 */
		String PAYER_STATE = "payer_state"; //*

		/**
		 * Customer’s city
		 * <p>
		 * Value: String up to 32 characters
		 */
		String PAYER_CITY = "payer_city"; //*

		/**
		 * ZIP-code of the Customer
		 * <p>
		 * Value: String up to 32 characters
		 */
		String PAYER_ZIP = "payer_zip"; //*

		/**
		 * Customer’s email
		 * <p>
		 * Value: String up to 256 characters
		 */
		String PAYER_EMAIL = "payer_email"; //*

		/**
		 * Customer’s phone
		 * <p>
		 * Value: String up to 32 characters
		 */
		String PAYER_PHONE = "payer_phone"; //*

		/**
		 * IP-address of the Customer
		 * <p>
		 * min length IP address
		 * https://stackoverflow.com/questions/22288483/whats-the-minimum-length-of-an-ip-address-in-string-representation
		 * <p>
		 * max length IP address
		 * https://stackoverflow.com/questions/1076714/max-length-for-client-ip-address#answer-7477384
		 * <p>
		 * Value: Format XXX.XXX.XXX.XXX
		 */
		String PAYER_IP = "payer_ip"; //*

		/**
		 * URL to which Customer should be returned after 3D-Secure
		 * <p>
		 * Note: '*' - If your account support 3D-Secure this parameter is required
		 * <p>
		 * Value: String up to 1024 characters
		 */
		String TERM_URL_3DS = "term_url_3ds"; //**

		/**
		 * Initialization of the transaction with possible following recurring
		 * Used in conjunction with {@link PlatonOption} only
		 * Default {@link PlatonOption#NO}
		 * <p>
		 * Value : {@link PlatonOption}
		 */
		String RECURRING_INIT = "recurring_init";

		/**
		 * Indicates that transaction must be only authenticated, but not captured
		 * Used in conjunction with {@link PlatonOption} only
		 * Default {@link PlatonOption#NO}
		 * <p>
		 * Value : {@link PlatonOption}
		 */
		String AUTH = "auth";

		/**
		 * Special signature to validate your request to Payment Platform
		 * <p>
		 * Value : {@link PlatonHashUtil#encryptSaleWeb} return hash
		 */
		String HASH = "hash"; //*

		/**
		 * PlatonTransaction ID in the Payment Platform
		 * <p>
		 * Value : String up to 255 characters
		 */
		String TRANS_ID = "trans_id"; //*

		/**
		 * The amount for partial capture/ partial refund
		 * <p>
		 * Note: that ONLY ONE partial capture allowed in {@link PlatonAction#CAPTURE}
		 * <p>
		 * Note: that SEVERAL partial refunds allowed in {@link PlatonAction#CREDITVOID}
		 * <p>
		 * Value : Numbers in the form XXXX.XX (without leading zeros)
		 */
		String AMOUNT = "amount";

		/**
		 * PlatonTransaction ID of the primary transaction in the Payment Platform
		 * <p>
		 * See also {@link #TRANS_ID}
		 * <p>
		 * Value : String up to 255 characters
		 */
		String RECURRING_FIRST_TRANS_ID = "recurring_first_trans_id"; //*

		/**
		 * Value obtained during the primary transaction
		 * <p>
		 * See also {@link #TRANS_ID}
		 * <p>
		 * Value : String up to 255 characters
		 */
		String RECURRING_TOKEN = "recurring_token"; //*

		/**
		 * Period in days to perform the payments
		 * <p>
		 * Value : Numbers in the form XXX (without leading zeros)
		 */
		String PERIOD = "period"; //*

		/**
		 * Delay in days before performing the first payment
		 * <p>
		 * Note: If not provided, the first payment will be done as soon as possible
		 * <p>
		 * Value : Numbers in the form XXX (without leading zeros)
		 */
		String INIT_PERIOD = "init_period";

		/**
		 * The number of times the payments will be done
		 * Not provided or zero value means unlimited number of payments
		 * <p>
		 * Value : Numbers in the form XXX (without leading zeros)
		 */
		String REPEAT_TIMES = "times";

		/**
		 * Key for Client identification
		 * See {@link Credentials#CLIENT_KEY} for more details
		 */
		String KEY = "key"; //*

		/**
		 * Payment method code
		 * See {@link PlatonApiConstants.Formats.Payment} for its params
		 */
		String PAYMENT = "payment"; //*

		/**
		 * Properties of the product (price, title, description)
		 * <p>
		 * Value: Base64-encoded data
		 * <p>
		 * See {@link PlatonBase64Util#//base64(String)} for its generation
		 * See {@link PlatonWebSaleAdapter} for its usage
		 */
		String DATA = "data"; //*

		/**
		 * URL to which the Buyer will be redirected after the successful payment
		 * <p>
		 * See {@link PlatonWebSaleAdapter} for its usage
		 */
		String URL = "url"; //*

		/**
		 * Special signature to validate your request to Payment Platform
		 * <p>
		 * See {@link PlatonHashUtil} class for all types of signing
		 */
		String SIGN = "sign"; //*

		/**
		 * Localization language to be selected on the payment page by default
		 * <p>
		 * Value: en, fr, de (ISO 639-1)
		 */
		String LANG = "lang";

		/**
		 * Buyer's email address for web payments
		 */
		String EMAIL = "email";

		/**
		 * Buyer's first name for web payments
		 * <p>
		 * Value: John
		 */
		String FIRST_NAME = "first_name";

		/**
		 * Buyer's last name for web payments
		 * <p>
		 * Value: Doe
		 */
		String LAST_NAME = "last_name";

		/**
		 * Buyer's phone number for web payments
		 */
		String PHONE = "phone";

		/**
		 * PlatonOrder ID for web payments
		 * <p>
		 * Value: String up to 30 characters
		 */
		String ORDER = "order";

		/**
		 * Optional URL to which the Buyer will be returned after three unsuccessful purchase attempts
		 */
		String ERROR_URL = "error_url";

		/**
		 * Specific payment page identifier for web payments
		 * (In case the Client's account has multiple payment pages configured)
		 */
		String FORM_ID = "formid";

		String REQ_TOKEN = "req_token";

		String CARD_TOKEN = "card_token";

		/**
		 * Client Parameter 1
		 */
		String EXT_1 = "ext1";

		/**
		 * Client Parameter 2
		 */
		String EXT_2 = "ext2";

		/**
		 * Client Parameter 3
		 */
		String EXT_3 = "ext3";

		/**
		 * Client Parameter 4
		 */
		String EXT_4 = "ext4";

		/**
		 * Client Parameter 10
		 */
		String EXT_10 = "ext10";

		/**
		 * Buyer's street address for web payments
		 * <p>
		 * Value: 123 Sample Street
		 */
		String ADDRESS = "address";

		/**
		 * Buyer's zip code for web payments
		 * <p>
		 * Value: 12345
		 */
		String ZIP = "zip";

		/**
		 * Buyer's city for web payments
		 * <p>
		 * Value: New York
		 */
		String CITY = "city";

		/**
		 * Buyer's country code for web payments
		 * <p>
		 * Value: US (ISO 3166-1 alpha-2)
		 */
		String COUNTRY = "country";

		/**
		 * Buyer's country region code (state, provice, etc.) for web payments
		 * Applied only for US, CA and AU
		 * <p>
		 * Value: TX (ISO 3166-2)
		 */
		String STATE = "state";

		/**
		 * PlatonProduct currency
		 * <p>
		 * Value: 3-characters string (USD,EUR, etc.)
		 */
		String CURRENCY = "currency"; //*

		/**
		 * PlatonProduct name in web payments
		 * <p>
		 * Value: String up to 30 characters
		 */
		String DESCRIPTION = "description"; //*

		/**
		 * PlatonProduct, selected by default in products list
		 * <p>
		 * Value: 'selected'
		 * <p>
		 * See {@link PlatonProductSale} for usage
		 */
		String SELECTED = "selected";

		/**
		 * Flag to initialize the possibility of the further recurring payments
		 * <p>
		 * Value: 'recurring'
		 * <p>
		 * See {@link PlatonProductSale} for usage
		 */
		String RECURRING = "recurring";

		/**
		 * Used for iterating and indexing products for web SALE request
		 * <p>
		 * See {@link PlatonBase64Util} for usage
		 */
		String PRODUCT_INDEX_FORMAT = "p%d"; //*

		/**
		 * PlatonRecurring ID (will be received with the first payment) in web payments
		 */
		String RC_ID = "rc_id"; //*

		/**
		 * Additional parameter for further recurring (will be received with the first payment) in web payments
		 * <p>
		 * Value: String 32 characters
		 */
		String RC_TOKEN = "rc_token"; //*

		/**
		 * Initial period in days before the first recurring payment to be created
		 * <p>
		 * Value: Number
		 */
		String INITIAL_DELAY = "initial_delay"; //*

		/**
		 * Token received by a merchant from Google Play
		 */
		String PAYMENT_TOKEN = "payment_token"; //*

	}

	/**
	 * Used as identifiers for GSON deserialization
	 */
	interface SerializedNames {

		/**
		 * See {@link PlatonAction}
		 */
		String ACTION = "action";

		/**
		 * See {@link PlatonResult}
		 */
		String RESULT = "result";

		/**
		 * See {@link PlatonStatus} for main transaction and response
		 * See {@link PlatonTransactionStatus} for sub transactions
		 */
		String STATUS = "status";

		/**
		 * PlatonApiError message of error request model
		 */
		String ERROR_MESSAGE = "error_message";

		/**
		 * PlatonTransaction ID in the Payment Platform.
		 * Usually can be used as main identifier when creating recurring and/or scheduled payments
		 */
		String TRANS_ID = "trans_id";

		/**
		 * PlatonTransaction ID in the Client system
		 */
		String ORDER_ID = "order_id";

		/**
		 * PlatonTransaction date in the Payment Platform
		 * <p>
		 * Format: YYYY-MM-DD HH:mm:ss ("2012-04-03 16:02:02")
		 * <p>
		 * See {@link PlatonApiConstants.Formats.Date#PATTERN} for its realization
		 */
		String TRANS_DATE = "trans_date";

		/**
		 * The reason why the transaction was declined
		 * <p>
		 * ex. "Declined by processing"
		 */
		String DECLINE_REASON = "decline_reason";

		/**
		 * This is a string which the owner of the credit card will see in the statement from the bank.
		 * In most cases, this is the Customers support web-site
		 */
		String DESCRIPTOR = "descriptor";

		/**
		 * PlatonRecurring token (get if account support recurring sales and
		 * was initialization transaction for following recurring)
		 */
		String RECURRING_TOKEN = "recurring_token";

		/**
		 * URL to which the Client should redirect the Customer
		 */
		String REDIRECT_URL = "redirect_url";

		/**
		 * Array of specific 3DS parameters
		 * <p>
		 * See also {@link PlatonRedirectParams}
		 */
		String REDIRECT_PARAMS = "redirect_params";

		/**
		 * The method of transferring parameters
		 * <p>
		 * See also {@link PlatonRedirectMethod}
		 */
		String REDIRECT_METHOD = "redirect_method";

		/**
		 * Bank approval code
		 */
		String AUTH_CODE = "auth_code";

		/**
		 * Set of redirect params which used for 3DS
		 */
		String PA_REQ = "PaReq";
		String MD = "MD";
		String TERM_URL = "TermUrl";

		/**
		 * Date of the refund/reversal
		 */
		String CREDITVOID_DATE = "creditvoid_date";

		/**
		 * Payer name
		 */
		String NAME = "name";

		/**
		 * Payer mail
		 */
		String MAIL = "mail";

		/**
		 * Payer IP
		 */
		String IP = "ip";

		/**
		 * Amount of funds processed in transaction
		 * <p>
		 * Used in order and in transactions details, etc.
		 */
		String AMOUNT = "amount";

		/**
		 * The amount of the transaction
		 * <p>
		 * Value: Currency 3-letter code (ISO 4217).
		 */
		String CURRENCY = "currency";

		/**
		 * PlatonCard number from response
		 * <p>
		 * Format: XXXXXX****XXXX
		 */
		String CARD = "card";

		/**
		 * Array list of {@link PlatonTransaction}
		 */
		String TRANSACTIONS = "transactions";

		/**
		 * Date of {@link PlatonTransaction}
		 * <p>
		 * Format: YYYY-MM-DD HH:mm:ss ("2012-02-06 01:11:030")
		 * <p>
		 * See {@link PlatonApiConstants.Formats.Date#PATTERN} for its realization
		 */
		String DATE = "date";

		/**
		 * {@link PlatonTransactionType}
		 */
		String TYPE = "type";

	}

	/**
	 * Platon SDK uses only one called method - base url
	 * {@link PlatonAction} indicates which method to call
	 */
	interface CalledMethod {
		String BASE = ".";
	}


	/**
	 * {@link PlatonWebRecurringAdapter} use another unique url for request,
	 * except standard {@link PlatonSdkConstants.Credentials#PAYMENT_URL}
	 */
	interface Url {
		String WEB_RECURRING_SALE = "https://secure.platononline.com/post-unq/";
	}

	/**
	 * Content-Type name and value for* {@link Platon3dsSubmitUtil#submit3dsData(
	 *PlatonSale3DSecure, Platon3dsSubmitUtil.OnSubmit3dsDataListener)} request
	 */
	interface UrlEncodedContentType {
		String NAME = "Content-Type";
		String VALUE = "application/x-www-form-urlencoded";
	}

}
