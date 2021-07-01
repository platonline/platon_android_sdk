package com.platon.sdk.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;
import com.platon.sdk.callback.PlatonGooglePayCallback;
import com.platon.sdk.constant.api.PlatonOption;
import com.platon.sdk.core.PlatonCredentials;
import com.platon.sdk.core.PlatonSdk;
import com.platon.sdk.model.request.option.PlatonGooglePayOptions;
import com.platon.sdk.model.request.order.PlatonOrderGooglePay;
import com.platon.sdk.model.request.payer.PlatonPayerGooglePay;
import com.platon.sdk.widget.GooglePayReadyToPayListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Util class which used for configures and calls Google Pay
 */
public class GooglePayHelper {

    private static final int API_VERSION = 2;
    private static final int API_VERSION_MINOR = 0;

    private static final String GATEWAY_NAME = "platon";
    private static final String GATEWAY_TYPE = "PAYMENT_GATEWAY";
    private static final String PRICE_STATUS = "FINAL";

    public static final int ENVIRONMENT_TEST = WalletConstants.ENVIRONMENT_TEST;
    public static final int ENVIRONMENT_PRODUCTION = WalletConstants.ENVIRONMENT_PRODUCTION;
    public static final int GOOGLE_PAY_REQUEST_CODE = 5;

    private PaymentsClient paymentsClient;
    private PlatonOrderGooglePay platonOrderGooglePay;
    private PlatonPayerGooglePay platonPayerGooglePay;
    private PlatonGooglePayOptions platonGooglePayOptions;
    private PlatonGooglePayCallback platonGooglePayCallback;
    private String auth;

    /**
     * Initializes Google Pay API, determines the availability of making payments on the device
     * *
     *
     * @param context                     context for initialization
     * @param environment                 The parameter value indicates in which mode the server is running -
     *                                    production or test. Test mode is set by default
     * @param googlePayReadyToPayListener See @{@link GooglePayReadyToPayListener} for details (Required)
     */
    public void initGooglePay(final Context context, int environment, final GooglePayReadyToPayListener googlePayReadyToPayListener) {
        try {
            JSONObject isReadyToPayRequest = getBaseRequest()
                    .put("allowedPaymentMethods", new JSONArray()
                            .put(getBaseCardPaymentMethod()));
            final IsReadyToPayRequest request = IsReadyToPayRequest.fromJson(isReadyToPayRequest.toString());
            Wallet.WalletOptions options = new Wallet.WalletOptions.Builder()
                    .setEnvironment(environment)
                    .setTheme(WalletConstants.THEME_LIGHT)
                    .build();

            paymentsClient = Wallet.getPaymentsClient(context, options);
            paymentsClient.isReadyToPay(request).addOnCompleteListener(new OnCompleteListener<Boolean>() {
                @Override
                public void onComplete(@NonNull Task<Boolean> task) {
                    if (task.isSuccessful()) {
                        googlePayReadyToPayListener.showGooglePayButton(task.getResult() != null && task.getResult());
                    } else {
                        Log.w("isReadyToPay failed", task.getException());
                        googlePayReadyToPayListener.showGooglePayButton(false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Launches the Google Pay screen
     *
     * @param activity context to launch the screen
     */
    public void openGooglePay(
            Activity activity,
            @NonNull final PlatonOrderGooglePay platonOrderGooglePay,
            @Nullable final PlatonPayerGooglePay platonPayerGooglePay,
            @Nullable final PlatonGooglePayOptions platonGooglePayOptions,
            @NonNull final PlatonGooglePayCallback platonGooglePayCallback,
            @Nullable @PlatonOption final String auth
    ) {
        this.platonOrderGooglePay = platonOrderGooglePay;
        this.platonPayerGooglePay = platonPayerGooglePay;
        this.platonGooglePayOptions = platonGooglePayOptions;
        this.platonGooglePayCallback = platonGooglePayCallback;
        if (auth == null) {
            this.auth = PlatonOption.NO;
        } else {
            this.auth = auth;
        }
        try {
            final String orderAmountFormat = PlatonSdkUtil.getAmountFormat(platonOrderGooglePay.getAmount());
            PaymentDataRequest request = PaymentDataRequest.fromJson(createPaymentDataRequest(
                    orderAmountFormat, platonOrderGooglePay.getCurrencyCode(), platonOrderGooglePay.getCountryCode()
            ).toString());
            AutoResolveHelper.resolveTask(paymentsClient.loadPaymentData(request), activity, GOOGLE_PAY_REQUEST_CODE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle a resolved activity from the Google Pay payment sheet.
     *
     * @param requestCode Request code originally supplied to AutoResolveHelper in requestPayment().
     * @param resultCode  Result code returned by the Google Pay API.
     * @param data        Intent from the Google Pay API containing payment or error data.
     */
    public void resultGooglePay(int requestCode, int resultCode, Intent data) {
        if (requestCode == GooglePayHelper.GOOGLE_PAY_REQUEST_CODE) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    handlePaymentSuccess(PaymentData.getFromIntent(data));
                    break;
                case Activity.RESULT_CANCELED:
                    Log.w("loadPaymentData", "canceled");
                    break;
                case AutoResolveHelper.RESULT_ERROR:
                    Status status = AutoResolveHelper.getStatusFromIntent(data);
                    if (status != null) {
                        handleError(status.getStatusCode());
                    }
                    break;
                default:
            }
        }
    }

    /**
     * Create a Google Pay API base request object with properties used in all requests.
     *
     * @return Google Pay API base request object.
     *
     */
    private JSONObject getBaseRequest() throws JSONException {
        return new JSONObject()
                .put("apiVersion", API_VERSION)
                .put("apiVersionMinor", API_VERSION_MINOR);
    }

    /**
     * Gateway Integration: Identify your gateway and your app's gateway merchant identifier.
     *
     * <p>The Google Pay API response will return an encrypted payment method capable of being charged
     * by a supported gateway after payer authorization.
     *
     * @return Payment data tokenization for the CARD payment method.
     *
     */
    private JSONObject getGatewayTokenizationSpecification() throws JSONException {
        return new JSONObject() {
            {
                put("type", GATEWAY_TYPE);
                put("parameters", new JSONObject() {
                    {
                        put("gateway", GATEWAY_NAME);
                        put("gatewayMerchantId", PlatonCredentials.getClientKey());
                    }
                });
            }
        };
    }

    /**
     * Card networks supported by your app and your gateway.
     *
     * @return Allowed card networks
     */
    private JSONArray getAllowedCardNetworks() {
        return new JSONArray()
                .put("VISA")
                .put("MASTERCARD");
    }

    /**
     * Card authentication methods supported by your app and your gateway.
     *
     * @return Allowed card authentication methods.
     */
    private JSONArray getAllowedCardAuthMethods() {
        return new JSONArray()
                .put("PAN_ONLY")
                .put("CRYPTOGRAM_3DS");
    }

    /**
     * Describe your app's support for the CARD payment method.
     *
     * <p>The provided properties are applicable to both an IsReadyToPayRequest and a
     * PaymentDataRequest.
     *
     * @return A CARD PaymentMethod object describing accepted cards.
     *
     */
    private JSONObject getBaseCardPaymentMethod() throws JSONException {
        return new JSONObject()
                .put("type", "CARD")
                .put("parameters", new JSONObject() {
                    {
                        put("allowedAuthMethods", getAllowedCardAuthMethods());
                        put("allowedCardNetworks", getAllowedCardNetworks());
                    }
                });
    }

    /**
     * Describe the expected returned payment data for the CARD payment method
     *
     * @return A CARD PaymentMethod describing accepted cards and optional fields.
     *
     */
    private JSONObject getCardPaymentMethod() throws JSONException {
        return getBaseCardPaymentMethod()
                .put("tokenizationSpecification", getGatewayTokenizationSpecification());
    }

    /**
     * Provide Google Pay API with a payment amount, currency, and amount status.
     *
     * @param price        - Numbers in the form XXXX.XX (without leading zeros)
     * @param currencyCode - Currency 3-letter code (ISO 4217)
     * @param countryCode  - Country 2-letter code (ISO 3166-1 alpha-2)
     * @return information about the requested payment.
     *
     */
    private JSONObject getTransactionInfo(String price, String currencyCode, String countryCode) throws JSONException {
        return new JSONObject()
                .put("totalPrice", price)
                .put("totalPriceStatus", PRICE_STATUS)
                .put("currencyCode", currencyCode)
                .put("countryCode", countryCode);
    }

    /**
     * An object describing information requested in a Google Pay payment sheet
     *
     * @param price        - Numbers in the form XXXX.XX (without leading zeros)
     * @param currencyCode - Currency 3-letter code (ISO 4217)
     * @param countryCode  - Country 2-letter code (ISO 3166-1 alpha-2)
     * @return Payment data expected by your app.
     *
     */
    private JSONObject createPaymentDataRequest(String price, String currencyCode, String countryCode) throws JSONException {
        return getBaseRequest()
                .put("allowedPaymentMethods", new JSONArray()
                        .put(getCardPaymentMethod()))
                .put("transactionInfo", getTransactionInfo(price, currencyCode, countryCode));
    }

    /**
     * PaymentData response object contains the payment information, as well as any additional
     * requested information, such as billing.
     *
     * @param paymentData A response object returned by Google after a payer approves payment.
     */
    private void handlePaymentSuccess(@Nullable PaymentData paymentData) {
        String paymentInformation = null;
        if (paymentData != null) {
            paymentInformation = paymentData.toJson();
        }

        if (paymentInformation == null) {
            return;
        }

        JSONObject paymentMethodData;
        try {
            paymentMethodData = new JSONObject(paymentInformation).getJSONObject("paymentMethodData");
            String paymentToken = paymentMethodData.getJSONObject("tokenizationData").getString("token");

            if (auth.equals(PlatonOption.NO)) {
                PlatonSdk.PostPayments.getGooglePayAdapter().sale(
                        platonOrderGooglePay,
                        paymentToken,
                        platonPayerGooglePay,
                        platonGooglePayOptions,
                        platonGooglePayCallback
                );
            } else {
                PlatonSdk.PostPayments.getGooglePayAdapter().auth(
                        platonOrderGooglePay,
                        paymentToken,
                        platonPayerGooglePay,
                        platonGooglePayOptions,
                        platonGooglePayCallback
                );
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * At this stage, the user has already seen a popup informing them an error occurred. Normally,
     * only logging is required.
     *
     * @param statusCode will hold the value of any constant from CommonStatusCode or one of the
     *                   WalletConstants.ERROR_CODE_* constants.
     */
    private void handleError(int statusCode) {
        Log.w("loadPaymentData failed", String.format("Error code: %d", statusCode));
    }
}
