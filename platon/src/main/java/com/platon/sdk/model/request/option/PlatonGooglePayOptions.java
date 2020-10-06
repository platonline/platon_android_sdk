package com.platon.sdk.model.request.option;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.annotation.Size;

import com.platon.sdk.constant.api.PlatonApiConstants;
import com.platon.sdk.constant.api.PlatonOption;
import com.platon.sdk.constant.api.action.PlatonAction;
import com.platon.sdk.endpoint.adapter.post.PlatonGooglePayAdapter;

import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.CHANNEL;

/**
 * Sale options for Single Message System (SMS) or Dual Message System (DMS) ({@link PlatonAction#GOOGLE_PAY})
 * <p>
 * See @{@link PlatonGooglePayAdapter} for usages
 */
public class PlatonGooglePayOptions implements Parcelable {

    /**
     * Asynchronous ({@link PlatonOption#YES}) or synchronous (default) mode ({@link PlatonOption#NO})
     * <p>
     * See {@link PlatonApiConstants.MethodProperties#ASYNC}
     */
    @Nullable
    @PlatonOption
    private String mAsync;

    /**
     * Payment channel (Sub-account)
     * <p>
     * If you want to send a payment for the specific sub-account (channel),
     * you need to use channel ID that specified in your Payment Platform account settings
     * <p>
     * See {@link PlatonApiConstants.MethodProperties#CHANNEL_ID}
     */
    @Nullable
    @Size(max = CHANNEL)
    private String mChannelId;

    public PlatonGooglePayOptions() {
    }

    public PlatonGooglePayOptions(
            @Nullable @PlatonOption final String async,
            @Nullable @Size(max = CHANNEL) final String channelId
    ) {
        mAsync = async;
        mChannelId = channelId;
    }

    private PlatonGooglePayOptions(final PlatonGooglePayOptions.Builder builder) {
        mAsync = builder.mAsync;
        mChannelId = builder.mChannelId;
    }

    protected PlatonGooglePayOptions(Parcel in) {
        mAsync = in.readString();
        mChannelId = in.readString();
    }

    public static final Creator<PlatonGooglePayOptions> CREATOR = new Creator<PlatonGooglePayOptions>() {
        @Override
        public PlatonGooglePayOptions createFromParcel(Parcel in) {
            return new PlatonGooglePayOptions(in);
        }

        @Override
        public PlatonGooglePayOptions[] newArray(int size) {
            return new PlatonGooglePayOptions[size];
        }
    };

    @Nullable
    @PlatonOption
    public String getAsync() {
        return mAsync;
    }

    public boolean isAsync() {
        return !TextUtils.isEmpty(mAsync) && mAsync.equalsIgnoreCase(PlatonOption.YES);
    }

    public void setAsync(@Nullable @PlatonOption final String async) {
        mAsync = async;
    }

    @Nullable
    @Size(max = CHANNEL)
    public String getChannelId() {
        return mChannelId;
    }

    public void setChannelId(@Nullable @Size(max = CHANNEL) final String channelId) {
        mChannelId = channelId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAsync);
        dest.writeString(mChannelId);
    }

    public static final class Builder {

        @Nullable
        @PlatonOption
        private String mAsync;

        @Nullable
        @Size(max = CHANNEL)
        private String mChannelId;

        public PlatonGooglePayOptions.Builder async(@Nullable @PlatonOption final String async) {
            mAsync = async;
            return this;
        }

        public PlatonGooglePayOptions.Builder channelId(@Nullable @Size(max = CHANNEL) final String channelId) {
            mChannelId = channelId;
            return this;
        }

        public PlatonGooglePayOptions build() {
            return new PlatonGooglePayOptions(this);
        }
    }

    @Override
    public String toString() {
        return "PlatonGooglePayOptions{" +
                "mAsync='" + mAsync + '\'' +
                ", mChannelId='" + mChannelId + '\'' +
                '}';
    }
}
