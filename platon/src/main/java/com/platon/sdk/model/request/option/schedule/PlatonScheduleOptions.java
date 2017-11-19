package com.platon.sdk.model.request.option.schedule;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;

import com.platon.sdk.constant.api.PlatonApiConstants.MethodProperties;
import com.platon.sdk.constant.api.action.PlatonAction;
import com.platon.sdk.endpoint.adapter.post.PlatonScheduleAdapter;

import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Period.ASAP;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Period.MAX_DELAY;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Period.MAX_REPEAT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Period.UNLIMITED_REPEAT;

/**
 * Schedule options for {@link PlatonAction#SCHEDULE} request
 * <p>
 * See @{@link PlatonScheduleAdapter} for usages
 */
public class PlatonScheduleOptions implements Parcelable {

    /**
     * Delay in days before performing the first payment
     * If not provided, the first payment will be done as soon as possible
     * <p>
     * See {@link MethodProperties#INIT_PERIOD}
     */
    @Nullable
    @IntRange(from = ASAP, to = MAX_DELAY)
    private Integer mInitDelayDays;

    /**
     * The number of times the payments will be done
     * Not provided or zero value means unlimited number of payments
     * <p>
     * See {@link MethodProperties#REPEAT_TIMES}
     */
    @Nullable
    @IntRange(from = UNLIMITED_REPEAT, to = MAX_REPEAT)
    private Integer mRepeatTimes;

    public PlatonScheduleOptions() {
    }

    public PlatonScheduleOptions(
            @Nullable @IntRange(from = ASAP, to = MAX_DELAY) final Integer initDelayDays,
            @Nullable @IntRange(from = UNLIMITED_REPEAT, to = MAX_REPEAT) final Integer repeatTimes
    ) {
        mInitDelayDays = initDelayDays;
        mRepeatTimes = repeatTimes;
    }

    private PlatonScheduleOptions(final Builder builder) {
        setInitDelayDays(builder.mInitDelayDays);
        setRepeatTimes(builder.mRepeatTimes);
    }

    protected PlatonScheduleOptions(final Parcel in) {
        if (in.readByte() == 0) mInitDelayDays = null;
        else mInitDelayDays = in.readInt();

        if (in.readByte() == 0) mRepeatTimes = null;
        else mRepeatTimes = in.readInt();
    }

    public static final Creator<PlatonScheduleOptions> CREATOR = new Creator<PlatonScheduleOptions>() {
        @Override
        public PlatonScheduleOptions createFromParcel(final Parcel in) {
            return new PlatonScheduleOptions(in);
        }

        @Override
        public PlatonScheduleOptions[] newArray(final int size) {
            return new PlatonScheduleOptions[size];
        }
    };

    @Nullable
    public Integer getInitDelayDays() {
        return mInitDelayDays;
    }

    public void setInitDelayDays(
            @Nullable @IntRange(from = ASAP, to = MAX_DELAY) final Integer initDelayDays
    ) {
        mInitDelayDays = initDelayDays;
    }

    @Nullable
    public Integer getRepeatTimes() {
        return mRepeatTimes;
    }

    public void setRepeatTimes(
            @Nullable @IntRange(from = UNLIMITED_REPEAT, to = MAX_REPEAT) final Integer repeatTimes
    ) {
        mRepeatTimes = repeatTimes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int i) {
        if (mInitDelayDays == null) parcel.writeByte((byte) 0);
        else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(mInitDelayDays);
        }

        if (mRepeatTimes == null) parcel.writeByte((byte) 0);
        else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(mRepeatTimes);
        }
    }

    public static final class Builder {

        @Nullable
        @IntRange(from = ASAP, to = MAX_DELAY)
        private Integer mInitDelayDays;

        @Nullable
        @IntRange(from = UNLIMITED_REPEAT, to = MAX_REPEAT)
        private Integer mRepeatTimes;

        public Builder initDelayDays(
                @Nullable @IntRange(from = ASAP, to = MAX_DELAY) final Integer initDelayDays
        ) {
            mInitDelayDays = initDelayDays;
            return this;
        }

        public Builder repeatTimes(
                @Nullable @IntRange(from = UNLIMITED_REPEAT, to = MAX_REPEAT) final Integer repeatTimes
        ) {
            mRepeatTimes = repeatTimes;
            return this;
        }

        public PlatonScheduleOptions build() {
            return new PlatonScheduleOptions(this);
        }
    }

    @Override
    public String toString() {
        return "PlatonScheduleOptions{" +
                "mInitDelayDays=" + mInitDelayDays +
                ", mRepeatTimes=" + mRepeatTimes +
                '}';
    }
}
