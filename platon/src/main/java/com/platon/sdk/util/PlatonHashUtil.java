package com.platon.sdk.util;

import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.widget.BaseAdapter;

import com.platon.sdk.callback.PlatonWebCallback;
import com.platon.sdk.core.PlatonCredentials;
import com.platon.sdk.endpoint.adapter.post.PlatonSaleAdapter;
import com.platon.sdk.endpoint.adapter.web.PlatonWebOneClickSaleAdapter;
import com.platon.sdk.endpoint.adapter.web.PlatonWebRecurringAdapter;
import com.platon.sdk.endpoint.adapter.web.PlatonWebSaleAdapter;
import com.platon.sdk.endpoint.adapter.web.PlatonWebScheduleAdapter;
import com.platon.sdk.model.request.option.schedule.PlatonScheduleWebOptions;
import com.platon.sdk.model.request.order.product.PlatonProduct;
import com.platon.sdk.model.request.order.product.PlatonProductRecurring;
import com.platon.sdk.model.request.recurring.PlatonRecurring;
import com.platon.sdk.model.request.recurring.PlatonRecurringWeb;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Hash is signature rule used either to validate your requests to POST payment platform or to
 * validate callback from payment platform to your system
 * <p>
 * Sign is signature rule used either to validate your requests to WEB payment platform or to
 * validate callback from payment platform to your system
 */
public class PlatonHashUtil {

	/**
	 * md5(strtoupper(strrev(CLIENT_KEY).strrev(payment).strrev(data).strrev(successUrl).strrev(CLIENT_PASS)))
	 *
	 * @param payment    - payment code
	 * @param data       - encoded base64 products list
	 * @param successUrl - successful url after sale transaction
	 * @return md5 hash for {@link PlatonWebSaleAdapter} requests
	 */
	public static String encryptSaleWeb(final String payment, final String data, final String successUrl) {
		if (TextUtils.isEmpty(payment) || TextUtils.isEmpty(data) || TextUtils.isEmpty(successUrl)) return null;

		final String reverseClientKey =
				new StringBuilder(PlatonCredentials.getClientKey()).reverse().toString();
		final String reversePayment =
				new StringBuilder(payment).reverse().toString();
		final String reverseData =
				new StringBuilder(data).reverse().toString();
		final String reverseSuccessUrl =
				new StringBuilder(successUrl).reverse().toString();
		final String reverseClientPass =
				new StringBuilder(PlatonCredentials.getClientPass()).reverse().toString();

		return md5(
				reverseClientKey
						.concat(reversePayment)
						.concat(reverseData)
						.concat(reverseSuccessUrl)
						.concat(reverseClientPass)
						.toUpperCase()
		);
	}

	public static String encryptSaleTokenWeb(final String payment, final String data, final String successUrl, final String card_token) {
		if (TextUtils.isEmpty(payment) || TextUtils.isEmpty(data) || TextUtils.isEmpty(successUrl) || TextUtils.isEmpty(card_token)) return null;

		final String reverseClientKey =
				new StringBuilder(PlatonCredentials.getClientKey()).reverse().toString();
		final String reversePayment =
				new StringBuilder(payment).reverse().toString();
		final String reverseData =
				new StringBuilder(data).reverse().toString();
		final String reverseSuccessUrl =
				new StringBuilder(successUrl).reverse().toString();
		final String reverseToken =
				new StringBuilder(card_token).reverse().toString();
		final String reverseClientPass =
				new StringBuilder(PlatonCredentials.getClientPass()).reverse().toString();

		return md5(
				reverseClientKey
						.concat(reversePayment)
						.concat(reverseData)
						.concat(reverseSuccessUrl)
						.concat(reverseToken)
						.concat(reverseClientPass)
						.toUpperCase()
		);
	}

	/**
	 * md5(strtoupper(strrev(CLIENT_KEY).strrev(data).strrev(rc_id).strrev(rc_token).strrev(url).strrev(CLIENT_PASS)))
	 *
	 * @param recurringWeb - holder for rc_id and rc_token
	 * @param data         - encoded base64 product sale
	 * @param successUrl   - successful url after one-click payment
	 * @return md5 hash for {@link PlatonWebOneClickSaleAdapter} requests
	 */
	public static String encryptOneClickSaleWeb(
			final String data, final PlatonRecurringWeb recurringWeb, final String successUrl
	) {
		if (TextUtils.isEmpty(data) || recurringWeb == null || TextUtils.isEmpty(successUrl)) return null;

		final String reverseClientKey =
				new StringBuilder(PlatonCredentials.getClientKey()).reverse().toString();
		final String reverseData =
				new StringBuilder(data).reverse().toString();
		final String reverseTransId =
				new StringBuilder(recurringWeb.getFirstTransId()).reverse().toString();
		final String reverseToken =
				new StringBuilder(recurringWeb.getToken()).reverse().toString();
		final String reverseSuccessUrl =
				new StringBuilder(successUrl).reverse().toString();
		final String reverseClientPass =
				new StringBuilder(PlatonCredentials.getClientPass()).reverse().toString();

		return md5(
				reverseClientKey
						.concat(reverseData)
						.concat(reverseTransId)
						.concat(reverseToken)
						.concat(reverseSuccessUrl)
						.concat(reverseClientPass)
						.toUpperCase()
		);
	}

	/**
	 * md5(strtoupper(strrev(CLIENT_KEY).strrev(amount).strrev(description)
	 * .strrev(rc_id).strrev(rc_token).strrev(CLIENT_PASS)));
	 *
	 * @param productRecurring - product recurring info
	 *                         See {@link PlatonProductRecurring}
	 * @param recurring        - recurring info with id and token
	 *                         See {@link PlatonRecurringWeb}
	 * @return md5 hash for {@link PlatonWebRecurringAdapter} requests
	 */
	public static String encryptRecurringWeb(
			final PlatonProductRecurring productRecurring, final PlatonRecurringWeb recurring
	) {
		if (productRecurring == null || recurring == null) return null;

		final String formatAmount = PlatonSdkUtil.getAmountFormat(productRecurring.getAmount());
		if (TextUtils.isEmpty(formatAmount) || TextUtils.isEmpty(productRecurring.getDescription()) ||
				TextUtils.isEmpty(recurring.getFirstTransId()) || TextUtils.isEmpty(recurring.getToken())) return null;

		final String reverseClientKey =
				new StringBuilder(PlatonCredentials.getClientKey()).reverse().toString();
		final String reverseAmount =
				new StringBuilder(formatAmount).reverse().toString();
		final String reverseDescription =
				new StringBuilder(productRecurring.getDescription()).reverse().toString();
		final String reverseTransId =
				new StringBuilder(recurring.getFirstTransId()).reverse().toString();
		final String reverseToken =
				new StringBuilder(recurring.getToken()).reverse().toString();
		final String reverseClientPass =
				new StringBuilder(PlatonCredentials.getClientPass()).reverse().toString();

		return md5(
				reverseClientKey
						.concat(reverseAmount)
						.concat(reverseDescription)
						.concat(reverseTransId)
						.concat(reverseToken)
						.concat(reverseClientPass)
						.toUpperCase()
		);
	}

	/**
	 * - md5(strtoupper(strrev(CLIENT_KEY).strrev(amount).strrev(description)
	 * .strrev(rc_id).strrev(rc_token).strrev(initial_delay).strrev(period).strrev(times).strrev(CLIENT_PASS)));
	 *
	 * @param platonProduct            - product recurring
	 *                                 See {@link PlatonProduct}
	 * @param recurring                - recurring info with id and token
	 *                                 See {@link PlatonRecurringWeb}
	 * @param platonScheduleWebOptions - schedule options
	 *                                 See {@link PlatonScheduleWebOptions}
	 * @return md5 hash for
	 * {@link PlatonWebScheduleAdapter#schedule(
	 *PlatonProduct, PlatonRecurringWeb, PlatonScheduleWebOptions, PlatonWebCallback)} request
	 */
	public static String encryptScheduleWeb(
			final PlatonProduct platonProduct, final PlatonRecurringWeb recurring, final PlatonScheduleWebOptions platonScheduleWebOptions
	) {
		if (platonProduct == null || recurring == null || platonScheduleWebOptions == null) return null;

		final String formatAmount = PlatonSdkUtil.getAmountFormat(platonProduct.getAmount());
		if (TextUtils.isEmpty(formatAmount) || TextUtils.isEmpty(platonProduct.getDescription()) ||
				TextUtils.isEmpty(recurring.getFirstTransId()) || TextUtils.isEmpty(recurring.getToken())) return null;

		final String reverseClientKey =
				new StringBuilder(PlatonCredentials.getClientKey()).reverse().toString();
		final String reverseAmount =
				new StringBuilder(formatAmount).reverse().toString();
		final String reverseDescription =
				new StringBuilder(platonProduct.getDescription()).reverse().toString();
		final String reverseTransId =
				new StringBuilder(recurring.getFirstTransId()).reverse().toString();
		final String reverseToken =
				new StringBuilder(recurring.getToken()).reverse().toString();
		final String reverseInitialDelay =
				new StringBuilder(String.valueOf(platonScheduleWebOptions.getInitialDelay())).reverse().toString();
		final String reversePeriod =
				new StringBuilder(String.valueOf(platonScheduleWebOptions.getPeriod())).reverse().toString();
		final String reverseRepeatTimes =
				new StringBuilder(String.valueOf(platonScheduleWebOptions.getRepeatTimes())).reverse().toString();
		final String reverseClientPass =
				new StringBuilder(PlatonCredentials.getClientPass()).reverse().toString();

		return md5(
				reverseClientKey
						.concat(reverseAmount)
						.concat(reverseDescription)
						.concat(reverseTransId)
						.concat(reverseToken)
						.concat(reverseInitialDelay)
						.concat(reversePeriod)
						.concat(reverseRepeatTimes)
						.concat(reverseClientPass)
						.toUpperCase()
		);
	}

	/**
	 * - md5(strtoupper(strrev(CLIENT_KEY).strrev(rc_id).strrev(rc_token).strrev(CLIENT_PASS)
	 *
	 * @param recurring - recurring info with id and token
	 *                  See {@link PlatonRecurring}
	 * @return md5 hash for {@link PlatonWebScheduleAdapter#deschedule(PlatonRecurringWeb, PlatonWebCallback)} request
	 */
	public static String encryptDescheduleWeb(final PlatonRecurringWeb recurring) {
		if (recurring == null) return null;
		if (TextUtils.isEmpty(recurring.getFirstTransId()) || TextUtils.isEmpty(recurring.getToken())) return null;

		final String reverseClientKey =
				new StringBuilder(PlatonCredentials.getClientKey()).reverse().toString();
		final String reverseTransId =
				new StringBuilder(recurring.getFirstTransId()).reverse().toString();
		final String reverseToken =
				new StringBuilder(recurring.getToken()).reverse().toString();
		final String reverseClientPass =
				new StringBuilder(PlatonCredentials.getClientPass()).reverse().toString();

		return md5(
				reverseClientKey
						.concat(reverseTransId)
						.concat(reverseToken)
						.concat(reverseClientPass)
						.toUpperCase()
		);
	}

	/**
	 * md5(strtoupper(strrev(email).CLIENT_PASS.strrev(substr(card_number,0,6).substr(card_number,-4))))
	 *
	 * @param email      - payer email
	 * @param cardNumber - payer card number
	 * @return md5 hash for {@link PlatonSaleAdapter} requests
	 */
	@Nullable
	public static String encryptSale(final String email, final String cardNumber) {
		return encryptSale(email, "", cardNumber);
	}

	/**
	 * md5(strtoupper(strrev(email).CLIENT_PASS.trans_id.strrev(substr(card_number,0,6).substr(card_number,-4))))
	 *
	 * @param email      - payer email
	 * @param transId    - initial transaction id
	 * @param cardNumber - payer card number
	 * @return md5 hash for all {@link BaseAdapter} requests
	 */
	@Nullable
	public static String encryptSale(final String email, final String transId, final String cardNumber) {
		if (TextUtils.isEmpty(email) || TextUtils.isEmpty(cardNumber)) return null;

		final int cardNumberLength = cardNumber.length();
		if (cardNumberLength < 6) return null;

		final String reverseEmail = new StringBuilder(email).reverse().toString();
		final String clientPass = PlatonCredentials.getClientPass();

		final String cardNumberFirstPart = cardNumber.substring(0, 6);
		final String cardNumberSecondPart = cardNumber.substring(cardNumberLength - 4, cardNumberLength);

		final String cardNumberCombination = cardNumberFirstPart.concat(cardNumberSecondPart);
		final String reverseCardNumberCombination = new StringBuilder(cardNumberCombination).reverse().toString();

		return md5(
				reverseEmail
						.concat(clientPass)
						.concat(transId)
						.concat(reverseCardNumberCombination)
						.toUpperCase()
		);
	}

	/**
	 * The MD5 algorithm is a widely used hash function producing a 128-bit hash value
	 *
	 * @param message - input message
	 * @return md5 hash function
	 */
	private static String md5(final String message) {
		try {
			final MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
			digest.update(message.getBytes());

			final byte[] md5sum = digest.digest();
			final BigInteger bigInteger = new BigInteger(1, md5sum);
			final String hash = bigInteger.toString(16);

			return String.format("%32s", hash).replace(' ', '0');
		} catch (final NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return "";
	}

}
