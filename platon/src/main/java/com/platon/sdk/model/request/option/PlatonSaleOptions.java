package com.platon.sdk.model.request.option;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Size;
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

    /**
     * Client Parameter 1
     * <p>
     * See {@link MethodProperties#EXT_1}
     */
    @Nullable
    String mExt1;

    /**
     * Client Parameter 2
     * <p>
     * See {@link MethodProperties#EXT_2}
     */
    @Nullable
    String mExt2;

    /**
     * Client Parameter 3
     * <p>
     * See {@link MethodProperties#EXT_3}
     */
    @Nullable
    String mExt3;

    /**
     * Client Parameter 4
     * <p>
     * See {@link MethodProperties#EXT_4}
     */
    @Nullable
    String mExt4;

    /**
     * Client Parameter 5
     * <p>
     * See {@link MethodProperties#EXT_5}
     */
    @Nullable
    String mExt5;

    /**
     * Client Parameter 6
     * <p>
     * See {@link MethodProperties#EXT_6}
     */
    @Nullable
    String mExt6;

    /**
     * Client Parameter 7
     * <p>
     * See {@link MethodProperties#EXT_7}
     */
    @Nullable
    String mExt7;

    /**
     * Client Parameter 8
     * <p>
     * See {@link MethodProperties#EXT_8}
     */
    @Nullable
    String mExt8;

    /**
     * Client Parameter 9
     * <p>
     * See {@link MethodProperties#EXT_9}
     */
    @Nullable
    String mExt9;

    /**
     * Client Parameter 10
     * <p>
     * See {@link MethodProperties#EXT_10}
     */
    @Nullable
    String mExt10;

    public PlatonSaleOptions() {
    }

    public PlatonSaleOptions(
            @Nullable @PlatonOption final String async,
            @Nullable @Size(max = CHANNEL) final String channelId,
            @Nullable @PlatonOption final String recurringInit,
            @Nullable final String ext1,
            @Nullable final String ext2,
            @Nullable final String ext3,
            @Nullable final String ext4,
            @Nullable final String ext5,
            @Nullable final String ext6,
            @Nullable final String ext7,
            @Nullable final String ext8,
            @Nullable final String ext9,
            @Nullable final String ext10
    ) {
        mAsync = async;
        mChannelId = channelId;
        mRecurringInit = recurringInit;
        mExt1 = ext1;
        mExt2 = ext2;
        mExt3 = ext3;
        mExt4 = ext4;
        mExt5 = ext5;
        mExt6 = ext6;
        mExt7 = ext7;
        mExt8 = ext8;
        mExt9 = ext9;
        mExt10 = ext10;
    }

    private PlatonSaleOptions(final Builder builder) {
        mAsync = builder.mAsync;
        mChannelId = builder.mChannelId;
        mRecurringInit = builder.mRecurringInit;
        mExt1 = builder.mExt1;
        mExt2 = builder.mExt2;
        mExt3 = builder.mExt3;
        mExt4 = builder.mExt4;
        mExt5 = builder.mExt5;
        mExt6 = builder.mExt6;
        mExt7 = builder.mExt7;
        mExt8 = builder.mExt8;
        mExt9 = builder.mExt9;
        mExt10 = builder.mExt10;
    }

    protected PlatonSaleOptions(final Parcel in) {
        mAsync = in.readString();
        mChannelId = in.readString();
        mRecurringInit = in.readString();
        mExt1 = in.readString();
        mExt2 = in.readString();
        mExt3 = in.readString();
        mExt4 = in.readString();
        mExt5 = in.readString();
        mExt6 = in.readString();
        mExt7 = in.readString();
        mExt8 = in.readString();
        mExt9 = in.readString();
        mExt10 = in.readString();
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

    @Nullable
    public String getExt1() {
        return mExt1;
    }

    public void setExt1(@Nullable final String ext1) {
        mExt1 = ext1;
    }

    @Nullable
    public String getExt2() {
        return mExt2;
    }

    public void setExt2(@Nullable final String ext2) {
        mExt2 = ext2;
    }

    @Nullable
    public String getExt3() {
        return mExt3;
    }

    public void setExt3(@Nullable final String ext3) {
        mExt3 = ext3;
    }

    @Nullable
    public String getExt4() {
        return mExt4;
    }

    public void setExt4(@Nullable final String ext4) {
        mExt4 = ext4;
    }

    @Nullable
    public String getExt5() {
        return mExt5;
    }

    public void setExt5(@Nullable final String ext5) {
        mExt5 = ext5;
    }

    @Nullable
    public String getExt6() {
        return mExt6;
    }

    public void setExt6(@Nullable final String ext6) {
        mExt6 = ext6;
    }

    @Nullable
    public String getExt7() {
        return mExt7;
    }

    public void setExt7(@Nullable final String ext7) {
        mExt7 = ext7;
    }

    @Nullable
    public String getExt8() {
        return mExt8;
    }

    public void setExt8(@Nullable final String ext8) {
        mExt8 = ext8;
    }

    @Nullable
    public String getExt9() {
        return mExt9;
    }

    public void setExt9(@Nullable final String ext9) {
        mExt9 = ext9;
    }

    public void setExt10(@Nullable final String ext10) {
        mExt10 = ext10;
    }

    @Nullable
    public String getExt10() {
        return mExt10;
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
        parcel.writeString(mExt1);
        parcel.writeString(mExt2);
        parcel.writeString(mExt3);
        parcel.writeString(mExt4);
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

        @Nullable
        private String mExt1;

        @Nullable
        private String mExt2;

        @Nullable
        private String mExt3;

        @Nullable
        private String mExt4;

        @Nullable
        private String mExt5;

        @Nullable
        private String mExt6;

        @Nullable
        private String mExt7;

        @Nullable
        private String mExt8;

        @Nullable
        private String mExt9;

        @Nullable
        private String mExt10;

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

        public Builder ext1(@Nullable final String ext1) {
            mExt1 = ext1;
            return this;
        }

        public Builder ext2(@Nullable final String ext2) {
            mExt2 = ext2;
            return this;
        }

        public Builder ext3(@Nullable final String ext3) {
            mExt3 = ext3;
            return this;
        }

        public Builder ext4(@Nullable final String ext4) {
            mExt4 = ext4;
            return this;
        }

        public Builder ext5(@Nullable final String ext5) {
            mExt5 = ext5;
            return this;
        }

        public Builder ext6(@Nullable final String ext6) {
            mExt6 = ext6;
            return this;
        }

        public Builder ext7(@Nullable final String ext7) {
            mExt7 = ext7;
            return this;
        }

        public Builder ext8(@Nullable final String ext8) {
            mExt8 = ext8;
            return this;
        }

        public Builder ext9(@Nullable final String ext9) {
            mExt9 = ext9;
            return this;
        }

        public Builder ext10(@Nullable final String ext10) {
            mExt10 = ext10;
            return this;
        }

        public PlatonSaleOptions build() {
            return new PlatonSaleOptions(this);
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "PlatonSaleOptions{" +
                "mAsync='" + mAsync + '\'' +
                ", mChannelId='" + mChannelId + '\'' +
                ", mRecurringInit='" + mRecurringInit + '\'' +
                ", mExt1='" + mExt1 + '\'' +
                ", mExt2='" + mExt2 + '\'' +
                ", mExt3='" + mExt3 + '\'' +
                ", mExt4='" + mExt4 + '\'' +
                ", mExt5='" + mExt5 + '\'' +
                ", mExt6='" + mExt6 + '\'' +
                ", mExt7='" + mExt7 + '\'' +
                ", mExt8='" + mExt8 + '\'' +
                ", mExt9='" + mExt9 + '\'' +
                ", mExt10='" + mExt10 + '\'' +
                '}';
    }
}
