package com.platon.sdk.model.request.option;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.text.TextUtils;

import com.platon.sdk.constant.api.PlatonApiConstants.MethodProperties;
import com.platon.sdk.constant.api.PlatonOption;
import com.platon.sdk.constant.api.action.PlatonAction;
import com.platon.sdk.endpoint.adapter.post.PlatonSaleAdapter;

import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.General.CHANNEL;

/**
 * Sale options for Single Message System (SMS) or Dual Message System (DMS) ({@link PlatonAction#SALE})
 * <p>
 * See @{@link PlatonSaleAdapter} for usages
 */
public class PlatonSaleOptions implements Parcelable {

    /**
     * Asynchronous ({@link PlatonOption#YES}) or synchronous (default) mode ({@link PlatonOption#NO})
     * <p>
     * See {@link MethodProperties#ASYNC}
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
     * See {@link MethodProperties#CHANNEL_ID}
     */
    @Nullable
    @Size(max = CHANNEL)
    private String mChannelId;

    /**
     * Initialization of the transaction with possible following recurring
     * <p>
     * See {@link PlatonOption}) for possible values. Default {@link PlatonOption#NO}
     * <p>
     * * See {@link MethodProperties#RECURRING_INIT}
     */
    @Nullable
    @PlatonOption
    private String mRecurringInit;

    public PlatonSaleOptions() {
    }

    public PlatonSaleOptions(
            @Nullable @PlatonOption final String async,
            @Nullable @Size(max = CHANNEL) final String channelId,
            @Nullable @PlatonOption final String recurringInit
    ) {
        mAsync = async;
        mChannelId = channelId;
        mRecurringInit = recurringInit;
    }

    private PlatonSaleOptions(final Builder builder) {
        mAsync = builder.mAsync;
        mChannelId = builder.mChannelId;
        mRecurringInit = builder.mRecurringInit;
    }

    protected PlatonSaleOptions(final Parcel in) {
        mAsync = in.readString();
        mChannelId = in.readString();
        mRecurringInit = in.readString();
    }

    public static final Creator<PlatonSaleOptions> CREATOR = new Creator<PlatonSaleOptions>() {
        @Override
        public PlatonSaleOptions createFromParcel(final Parcel in) {
            return new PlatonSaleOptions(in);
        }

        @Override
        public PlatonSaleOptions[] newArray(final int size) {
            return new PlatonSaleOptions[size];
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

    @Nullable
    @PlatonOption
    public String getRecurringInit() {
        return mRecurringInit;
    }

    public void setRecurringInit(@Nullable @PlatonOption final String recurringInit) {
        mRecurringInit = recurringInit;
    }

    public boolean isReccuringInit() {
        return !TextUtils.isEmpty(mRecurringInit) && mRecurringInit.equalsIgnoreCase(PlatonOption.YES);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int i) {
        parcel.writeString(mAsync);
        parcel.writeString(mChannelId);
        parcel.writeString(mRecurringInit);
    }

    public static final class Builder {

        @Nullable
        @PlatonOption
        private String mAsync;

        @Nullable
        @Size(max = CHANNEL)
        private String mChannelId;

        @Nullable
        @PlatonOption
        private String mRecurringInit;

        public Builder async(@Nullable @PlatonOption final String async) {
            mAsync = async;
            return this;
        }

        public Builder channelId(@Nullable @Size(max = CHANNEL) final String channelId) {
            mChannelId = channelId;
            return this;
        }

        public Builder recurringInit(@Nullable @PlatonOption final String recurringInit) {
            mRecurringInit = recurringInit;
            return this;
        }

        public PlatonSaleOptions build() {
            return new PlatonSaleOptions(this);
        }
    }

    @Override
    public String toString() {
        return "PlatonSaleOptions{" +
                "mAsync='" + mAsync + '\'' +
                ", mChannelId='" + mChannelId + '\'' +
                ", mRecurringInit='" + mRecurringInit + '\'' +
                '}';
    }
}
