package com.platon.sdk.callback;

import com.platon.sdk.endpoint.adapter.post.PlatonSaleAdapter;
import com.platon.sdk.model.response.sale.PlatonSale3DSecure;
import com.platon.sdk.model.response.sale.PlatonSaleRecurringInit;

import retrofit2.Call;

/**
 * Used as callback in {@link PlatonSaleAdapter} methods
 */
public interface PlatonSaleCallback extends PlatonSaleBaseCallback {

	/**
	 * Called when sale was inited as recurring
	 *
	 * @param call     - original call
	 * @param response - sale model with recurring token
	 */
	void onRecurringInitResponse(final Call call, final PlatonSaleRecurringInit response);

	/**
	 * Called when sale was completed and need to be confirmed with 3DS redirect params
	 * <p>
	 * See {@link PlatonSale3DSecure} for documentation and further usage
	 * See {@link com.platon.sdk.util.Platon3dsSubmitUtil} for generating verify data
	 *
	 * @param call     - original call
	 * @param response - sale model with 3DS redirect info
	 */
	void on3dSecureResponse(final Call call, final PlatonSale3DSecure response);

}
