package com.platon.sdk.model.request.option.web;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.platon.sdk.constant.api.PlatonApiConstants.MethodProperties;
import com.platon.sdk.endpoint.adapter.web.PlatonWebRecurringAdapter;
import com.platon.sdk.endpoint.adapter.web.PlatonWebSaleAdapter;

import org.jetbrains.annotations.NotNull;

/**
 * Web options provides fields by which developer can customize displayed web form in web SALE, RECURRING_SALE request
 * <p>
 * See {@link PlatonWebSaleAdapter} and {@link PlatonWebRecurringAdapter} for its usages
 */
public class PlatonWebOptions implements Parcelable {

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

    public PlatonWebOptions() {
    }

    public PlatonWebOptions(
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

    private PlatonWebOptions(final Builder builder) {
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

    protected PlatonWebOptions(Parcel in) {
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

    public static final Creator<PlatonWebOptions> CREATOR = new Creator<PlatonWebOptions>() {
        @Override
        public PlatonWebOptions createFromParcel(Parcel in) {
            return new PlatonWebOptions(in);
        }

        @Override
        public PlatonWebOptions[] newArray(int size) {
            return new PlatonWebOptions[size];
        }
    };

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
        parcel.writeString(mExt1);
        parcel.writeString(mExt2);
        parcel.writeString(mExt3);
        parcel.writeString(mExt4);
        parcel.writeString(mExt5);
        parcel.writeString(mExt6);
        parcel.writeString(mExt7);
        parcel.writeString(mExt8);
        parcel.writeString(mExt9);
        parcel.writeString(mExt10);
    }

    public static final class Builder {

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

        public PlatonWebOptions build() {
            return new PlatonWebOptions(this);
        }
    }


    @NotNull
    @Override
    public String toString() {
        return "PlatonWebOptions{" +
                "mExt1='" + mExt1 + '\'' +
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
