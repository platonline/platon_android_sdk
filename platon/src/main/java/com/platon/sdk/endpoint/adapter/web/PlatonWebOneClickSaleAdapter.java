package com.platon.sdk.endpoint.adapter.web;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.Size;
import android.text.TextUtils;

import com.google.gson.GsonBuilder;
import com.platon.sdk.callback.PlatonWebCallback;
import com.platon.sdk.constant.api.PlatonApiConstants.Formats.Payment;
import com.platon.sdk.core.PlatonCredentials;
import com.platon.sdk.endpoint.adapter.PlatonBaseAdapter;
import com.platon.sdk.endpoint.adapter.post.PlatonSaleAdapter;
import com.platon.sdk.endpoint.service.web.PlatonWebOneClickSaleService;
import com.platon.sdk.endpoint.service.web.PlatonWebSaleService;
import com.platon.sdk.model.request.option.web.PlatonWebSaleOptions;
import com.platon.sdk.model.request.order.product.PlatonProductRecurring;
import com.platon.sdk.model.request.order.product.PlatonProductSale;
import com.platon.sdk.model.request.payer.PlatonPayerWebSale;
import com.platon.sdk.model.request.recurring.PlatonRecurringWeb;
import com.platon.sdk.util.PlatonBase64Util;
import com.platon.sdk.util.PlatonHashUtil;

import retrofit2.Call;

import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.TEXT_MIN;

/**
 * API adapter for integration procedures and WebPaymentsOneClick protocol usage for e-commerce merchants
 * <p>
 * See {@link PlatonSaleAdapter} for post SALE payments
 * See {@link PlatonWebSaleAdapter} for initial sale requests
 * See {@link PlatonWebRecurringAdapter} for recurring sale requests, which is similar to this one
 */
public class PlatonWebOneClickSaleAdapter extends PlatonBaseAdapter<PlatonWebOneClickSaleService> {

	private static PlatonWebOneClickSaleAdapter sInstance;

	private PlatonWebOneClickSaleAdapter() {
	}

	public synchronized static PlatonWebOneClickSaleAdapter getInstance() {
		if (sInstance == null) sInstance = new PlatonWebOneClickSaleAdapter();
		return sInstance;
	}

	@Override
	protected Class<PlatonWebOneClickSaleService> getServiceClass() {
		return PlatonWebOneClickSaleService.class;
	}

	@Override
	protected void configureGson(@NonNull final GsonBuilder builder) {
		super.configureGson(builder);
		builder.setLenient();
	}

	/**
	 * For params description see {@link #oneClickSale(
	 *PlatonProductSale, PlatonRecurringWeb, String, String, PlatonPayerWebSale, PlatonWebSaleOptions, PlatonWebCallback)}
	 * <p>
	 * One-Click request with required fields and without optional fields
	 */
	public Call oneClickSale(
			@NonNull final PlatonProductSale productSale,
			@NonNull final PlatonRecurringWeb recurringWeb,
			@NonNull final String successUrl,
			@NonNull final PlatonWebCallback callback
	) {
		return oneClickSale(productSale, recurringWeb, successUrl, null, null, null, callback);
	}

	/**
	 * If the client's account supports recurring operations,
	 * it is possible for client to make “one-click payments with CVV re-entering”.
	 * It is similar to recurring payments processing, but it requires the customer's
	 * presence to re-enter his CVC2/CVV2 at the special “reduced” hosted payment form.
	 * <p>
	 * All other payment data like payment card number and customer's
	 * details already stored in the PCI storage of Payment Platform.
	 * To make such payments, the client should send the customer
	 * to the same URL as for normal payments, but with following POST parameters.
	 * <p>
	 * After the successful payment the client will be redirected to the URL that was specified
	 * during payment request, as well as on this URL will be sent parameter “order” by the GET method
	 *
	 * @param productSale    - product for one-click transaction
	 *                       See {@link PlatonProductRecurring} for the details
	 * @param successUrl     - url by which you proceed after successful payment
	 * @param webSaleOptions - options to control web form representation
	 *                       See {@link PlatonWebSaleOptions} for its params
	 * @param callback       - callback which will hold url for web request
	 * @return original call of {@link PlatonWebSaleService sale(...)} request
	 */
	@SuppressWarnings({"ConstantConditions", "unchecked"})
	public Call oneClickSale(
			@NonNull final PlatonProductSale productSale,
			@NonNull final PlatonRecurringWeb recurringWeb,
			@NonNull final String successUrl,
			@Nullable @Size(max = TEXT_MIN) final String orderId,
			@Nullable final PlatonPayerWebSale platonPayerWebSale,
			@Nullable final PlatonWebSaleOptions webSaleOptions,
			@NonNull final PlatonWebCallback callback
	) {
		productSale.setSelected(false);
		productSale.setRecurring(false);

		final String payment = Payment.RF;
		final String data = PlatonBase64Util.encodeProduct(productSale);

		final boolean isSaleFormOptionsNull = webSaleOptions == null;
		final boolean isSalePayerNull = platonPayerWebSale == null;

		final Call call = mService.oneClickSale(
				PlatonCredentials.getClientKey(),
				payment,
				data,
				successUrl,

				recurringWeb.getFirstTransId(),
				recurringWeb.getToken(),

				orderId,
				isSalePayerNull || TextUtils.isEmpty(platonPayerWebSale.getFirstName()) ?
						null : platonPayerWebSale.getFirstName(),
				isSalePayerNull || TextUtils.isEmpty(platonPayerWebSale.getLastName()) ?
						null : platonPayerWebSale.getLastName(),
				isSalePayerNull || TextUtils.isEmpty(platonPayerWebSale.getEmail()) ?
						null : platonPayerWebSale.getEmail(),
				isSalePayerNull || TextUtils.isEmpty(platonPayerWebSale.getAddress()) ?
						null : platonPayerWebSale.getAddress(),
				isSalePayerNull || TextUtils.isEmpty(platonPayerWebSale.getZip()) ?
						null : platonPayerWebSale.getZip(),
				isSalePayerNull || TextUtils.isEmpty(platonPayerWebSale.getCity()) ?
						null : platonPayerWebSale.getCity(),
				isSalePayerNull || TextUtils.isEmpty(platonPayerWebSale.getCountryCode()) ?
						null : platonPayerWebSale.getCountryCode(),
				isSalePayerNull || TextUtils.isEmpty(platonPayerWebSale.getState()) ?
						null : platonPayerWebSale.getState(),
				isSalePayerNull || TextUtils.isEmpty(platonPayerWebSale.getPhone()) ?
						null : platonPayerWebSale.getPhone(),
				isSaleFormOptionsNull || TextUtils.isEmpty(webSaleOptions.getLanguage()) ?
						null : webSaleOptions.getLanguage(),
				isSaleFormOptionsNull || TextUtils.isEmpty(webSaleOptions.getErrorUrl()) ?
						null : webSaleOptions.getErrorUrl(),
				isSaleFormOptionsNull || TextUtils.isEmpty(webSaleOptions.getFormId())
						? null : webSaleOptions.getFormId(),
				isSaleFormOptionsNull || TextUtils.isEmpty(webSaleOptions.getExt1())
						? null : webSaleOptions.getExt1(),
				isSaleFormOptionsNull || TextUtils.isEmpty(webSaleOptions.getExt2())
						? null : webSaleOptions.getExt2(),
				isSaleFormOptionsNull || TextUtils.isEmpty(webSaleOptions.getExt3())
						? null : webSaleOptions.getExt3(),
				isSaleFormOptionsNull || TextUtils.isEmpty(webSaleOptions.getExt4())
						? null : webSaleOptions.getExt4(),
				isSaleFormOptionsNull || TextUtils.isEmpty(webSaleOptions.getExt5())
						? null : webSaleOptions.getExt5(),
				isSaleFormOptionsNull || TextUtils.isEmpty(webSaleOptions.getExt6())
						? null : webSaleOptions.getExt6(),
				isSaleFormOptionsNull || TextUtils.isEmpty(webSaleOptions.getExt7())
						? null : webSaleOptions.getExt7(),
				isSaleFormOptionsNull || TextUtils.isEmpty(webSaleOptions.getExt8())
						? null : webSaleOptions.getExt8(),
				isSaleFormOptionsNull || TextUtils.isEmpty(webSaleOptions.getExt9())
						? null : webSaleOptions.getExt9(),
				isSaleFormOptionsNull || TextUtils.isEmpty(webSaleOptions.getExt10())
						? null : webSaleOptions.getExt10(),

				PlatonHashUtil.encryptOneClickSaleWeb(data, recurringWeb, successUrl)
		);
		call.enqueue(callback);

		return call;
	}

}
