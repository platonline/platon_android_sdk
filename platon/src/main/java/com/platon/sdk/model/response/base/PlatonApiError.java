package com.platon.sdk.model.response.base;

import com.google.gson.annotations.SerializedName;
import com.platon.sdk.constant.api.PlatonResult;
import com.platon.sdk.deserializer.PlatonBaseDeserializer;
import com.platon.sdk.endpoint.adapter.PlatonBaseAdapter;

import static com.platon.sdk.constant.api.PlatonApiConstants.SerializedNames.ERROR_MESSAGE;
import static com.platon.sdk.constant.api.PlatonApiConstants.SerializedNames.RESULT;

/**
 * Platon API error which deserialize from request when something went wrong
 * The {@link #mMessage} indicates reason of error
 * <p>
 * See {@link PlatonBaseDeserializer} and {@link PlatonBaseAdapter}
 */
@SuppressWarnings("DanglingJavadoc")
public class PlatonApiError {

    /**
     * result : ERROR
     * error_message : Error description
     */

    /**
     * See {@link PlatonResult}
     */
    @SerializedName(RESULT)
    @PlatonResult
    private String mResult;

    /**
     * Specified message
     */
    @SerializedName(ERROR_MESSAGE)
    private String mMessage;

    @PlatonResult
    public String getResult() {
        return mResult;
    }

    public void setResult(@PlatonResult final String result) {
        mResult = result;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(final String message) {
        mMessage = message;
    }

    @Override
    public String toString() {
        return "PlatonApiError{" +
                "mResult='" + mResult + '\'' +
                ", mMessage='" + mMessage + '\'' +
                '}';
    }

}
