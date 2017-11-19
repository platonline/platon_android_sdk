package com.platon.sdk.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.platon.sdk.constant.api.PlatonApiConstants.SerializedNames;
import com.platon.sdk.constant.api.PlatonResult;
import com.platon.sdk.endpoint.adapter.PlatonBaseAdapter;
import com.platon.sdk.model.response.base.PlatonBasePayment;
import com.platon.sdk.model.response.base.PlatonBaseResponse;
import com.platon.sdk.model.response.base.PlatonApiError;

import java.lang.reflect.Type;

/**
 * Class which used for deserialization of all {@link PlatonBasePayment} model or {@link PlatonApiError} from API
 * <p>
 * See {@link PlatonBaseAdapter} for more details
 *
 * @param <PlatonPayment>  - payment model
 * @param <PlatonResponse> - payment response
 */
public class PlatonBaseDeserializer
		<PlatonPayment extends PlatonBasePayment, PlatonResponse extends PlatonBaseResponse<PlatonPayment>>
		implements JsonDeserializer<PlatonResponse> {

	private final Class<PlatonPayment> mClassOfT;

	public PlatonBaseDeserializer(final Class<PlatonPayment> classOfT) {
		mClassOfT = classOfT;
	}

	@Override
	public PlatonResponse deserialize(
			final JsonElement json, Type typeOfT, JsonDeserializationContext context
	) throws JsonParseException {
		final JsonObject jsonObject = json.getAsJsonObject();

		@PlatonResult final String result = jsonObject.get(SerializedNames.RESULT).getAsString();
		if (result.equalsIgnoreCase(PlatonResult.ERROR))
			return getError(deserializeClass(jsonObject, PlatonApiError.class, context));
		else {
			try {
				return getResponse(jsonObject, context);
			} catch (final Exception e) {
				return null;
			}
		}
	}

	/**
	 * @param platonApiError - deserialize error model from api
	 * @return {@link PlatonResponse} which contains only {@link PlatonApiError}
	 */
	@SuppressWarnings("unchecked")
	PlatonResponse getError(final PlatonApiError platonApiError) {
		return (PlatonResponse) new PlatonBaseResponse<>(platonApiError);
	}

	/**
	 * @param jsonObject - deserialize platon payment model from api
	 * @return {@link PlatonResponse} which contains only {@link PlatonPayment}
	 */
	@SuppressWarnings("unchecked")
	PlatonResponse getResponse(final JsonObject jsonObject, final JsonDeserializationContext context) {
		return (PlatonResponse) new PlatonBaseResponse<>(deserializeClass(jsonObject, mClassOfT, context));
	}

	/**
	 * Deserialize provided class with predefined context
	 *
	 * @return deserialize object
	 */
	<ClassToDeserialize> ClassToDeserialize deserializeClass(
			final JsonObject jsonObject,
			final Class<ClassToDeserialize> toDeserializeClass,
			final JsonDeserializationContext context
	) {
		return context.deserialize(jsonObject, toDeserializeClass);
	}

}