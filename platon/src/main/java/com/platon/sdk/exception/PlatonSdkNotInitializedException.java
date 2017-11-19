package com.platon.sdk.exception;

/**
 * Exception which appears when developer/user tries to use it, but Platon SDK is not initialized
 * <p>
 * See {@link com.platon.sdk.core.PlatonSdk} for more details
 */
public class PlatonSdkNotInitializedException extends IllegalAccessError {

    private PlatonSdkNotInitializedException() {
        super(
                "Platon SDK is not initialized. " +
                        "Don\'t forget to set your Payment Platform credentials in AndroidManifest.xml" +
                        " and than call PlatonSdk.init(yourAppContext) in YourApplication.class."
        );
    }

    public static void invoke() {
        throw new PlatonSdkNotInitializedException();
    }

}
