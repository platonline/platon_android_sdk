package com.platon.sdk.widget;


public interface GooglePayReadyToPayListener {

    /**
     * Determine the viewer's ability to pay with a payment method supported by your app and display a Google Pay payment button.
     *
     * @param isShow display a Google Pay payment button
     */
    void showGooglePayButton(boolean isShow);
}
