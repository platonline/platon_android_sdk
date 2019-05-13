package com.platon.sdk.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Base64;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.platon.sdk.model.request.order.product.PlatonProductSale;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static com.platon.sdk.constant.api.PlatonApiConstants.MethodProperties.AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.MethodProperties.CURRENCY;
import static com.platon.sdk.constant.api.PlatonApiConstants.MethodProperties.DESCRIPTION;
import static com.platon.sdk.constant.api.PlatonApiConstants.MethodProperties.PRODUCT_INDEX_FORMAT;
import static com.platon.sdk.constant.api.PlatonApiConstants.MethodProperties.RECURRING;
import static com.platon.sdk.constant.api.PlatonApiConstants.MethodProperties.SELECTED;

/**
 * The product's information should be presented as the json encoded or
 * serialized associated array in base64-encoding
 * <p>
 * For the simple payment form there can be only one product at one time, so the encoding
 * should looks like in following PHP example:
 * base64_encode(
 * json_encode(
 * array(
 * 'amount'=>'1.99',
 * 'currency'=>'USD',
 * 'description'=>' Description of PlatonProduct', 'recurring')
 * ));
 * <p>
 * For the complex payment form there should be the list of products instead.
 * Below is the PHP example of the products list encoding:
 * base64_encode(
 * json_encode(
 * array(
 * 'p1' => array('amount'=> '1.99',
 * 'currency'=>'USD',
 * 'description'=> 'Description of PlatonProduct 1',
 * 'recurring'),
 * 'p2' => array('amount'=>'20.05',
 * 'description'=>' Description of PlatonProduct 2',
 * 'selected') ,
 * 'p3' => array('amount'=>'35.45',
 * 'currency'=>'EUR',
 * 'description'=>' Description of PlatonProduct 3')
 * )));
 * <p>
 * p1, p2, p3 are the identifiers of products
 * <p>
 * Example illustrates the use of the properties of 'recurring' on a single product, namely the
 * identifier p1. The remaining products p2, p3 do not have this property.
 * The Client defines the PlatonProduct ID by himself. The main requirement is only a unique
 * identifier in the list of products.
 * <p>
 * The property 'currency' is not necessarily. By default the 'USD' is being used. The Client
 * can specify the transaction currency by himself according to the currencies allowed and
 * supported for his account.
 * <p>
 * The property 'recurring' is not necessarily and only used if the Client's account supports
 * the recurring operations and the Client will continue to use the recurring payments for the
 * certain products.
 * <p>
 * The property 'selected' is defined to make the product already selected in the payment
 * form when the Buyer is redirected to it.
 */
public class PlatonBase64Util {

	public static String encodeProduct(final PlatonProductSale productSale) {
		return encodeProducts(Collections.singletonList(productSale));
	}

	@SuppressLint("LongLogTag")
	public static String encodeProducts(final List<PlatonProductSale> productSales) {
		if (productSales.isEmpty()) return null;
		else if (productSales.size() == 1) {
			final JsonObject jsonObject = createJsonObjectFromProduct(productSales.get(0));
			return base64(jsonObject.toString());
		} else {
			final JsonObject jsonObject = new JsonObject();
			for (int i = 0; i < productSales.size(); i++) {
				jsonObject.add(
						String.format(Locale.US, PRODUCT_INDEX_FORMAT, i + 1),
						createJsonObjectFromProduct(productSales.get(i))
				);
			}

			return base64(jsonObject.toString());
		}
	}

	@SuppressLint("LongLogTag")
	public static String encodeProductsToken(final List<PlatonProductSale> productSales) {
		if (productSales.isEmpty()) return null;
		else if (productSales.size() == 1) {
			final JsonObject jsonObject = createJsonObjectFromProduct(productSales.get(0));
			return base64(jsonObject.toString());
		} else {
			final JsonArray jsonObject = new JsonArray();
			StringBuilder params = new StringBuilder("");
			for (int i = 0; i < productSales.size(); i++) {
				jsonObject.add(createJsonObjectFromProduct(productSales.get(i)));
			}
			return base64(jsonObject.toString());
		}
	}

	private static JsonObject createJsonObjectFromProduct(final PlatonProductSale productSale) {
		final JsonObject jsonObject = new JsonObject();

		jsonObject.addProperty(AMOUNT, PlatonSdkUtil.getAmountFormat(productSale.getAmount()));
		jsonObject.addProperty(DESCRIPTION, productSale.getDescription());

		if (!TextUtils.isEmpty(productSale.getCurrencyCode()))
			jsonObject.addProperty(CURRENCY, productSale.getCurrencyCode());
		if (productSale.isSelected()) jsonObject.addProperty(SELECTED, SELECTED);
		if (productSale.isRecurring()) jsonObject.addProperty(RECURRING, RECURRING);

		return jsonObject;
	}

	private static String base64(final String message) {
		byte[] data = new byte[0];

		try {
			data = message.getBytes("UTF-8");
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return Base64.encodeToString(data, Base64.NO_WRAP);
	}

}
