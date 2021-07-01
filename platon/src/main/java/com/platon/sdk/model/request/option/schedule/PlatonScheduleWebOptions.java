package com.platon.sdk.model.request.option.schedule;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import com.platon.sdk.constant.api.PlatonApiConstants.MethodProperties;
import com.platon.sdk.endpoint.adapter.web.PlatonWebScheduleAdapter;

import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Period.ASAP;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Period.MAX_DELAY;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Period.MAX_PERIOD;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Period.MAX_REPEAT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Period.MIN_PERIOD;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Period.UNLIMITED_REPEAT;

/**
 * Schedule options for web SCHEDULE request
 * <p>
 * See @{@link PlatonWebScheduleAdapter} for usages
 */
public class PlatonScheduleWebOptions implements Parcelable {

	/**
	 * Initial period in days before the first recurring payment to be created
	 * <p>
	 * See {@link MethodProperties#INITIAL_DELAY}
	 */
	@IntRange(from = ASAP, to = MAX_DELAY)
	private int mInitialDelay;

	/**
	 * Period in days between further recurring payments
	 * <p>
	 * See {@link MethodProperties#PERIOD}
	 */
	@IntRange(from = MIN_PERIOD, to = MAX_PERIOD)
	private int mPeriod;

	/**
	 * Total number of recurring payments
	 * The zero value means unlimited number of recurring payments
	 * <p>
	 * See {@link MethodProperties#REPEAT_TIMES}
	 */
	@IntRange(from = UNLIMITED_REPEAT, to = MAX_REPEAT)
	private int mRepeatTimes;

	private PlatonScheduleWebOptions() {

	}

	public PlatonScheduleWebOptions(
			@IntRange(from = ASAP, to = MAX_DELAY) final int initialDelay,
			@IntRange(from = MIN_PERIOD, to = MAX_PERIOD) final int period,
			@IntRange(from = UNLIMITED_REPEAT, to = MAX_REPEAT) final int repeatTimes
	) {
		mInitialDelay = initialDelay;
		mPeriod = period;
		mRepeatTimes = repeatTimes;
	}

	protected PlatonScheduleWebOptions(Parcel in) {
		mInitialDelay = in.readInt();
		mPeriod = in.readInt();
		mRepeatTimes = in.readInt();
	}

	public static final Creator<PlatonScheduleWebOptions> CREATOR = new Creator<PlatonScheduleWebOptions>() {
		@Override
		public PlatonScheduleWebOptions createFromParcel(Parcel in) {
			return new PlatonScheduleWebOptions(in);
		}

		@Override
		public PlatonScheduleWebOptions[] newArray(int size) {
			return new PlatonScheduleWebOptions[size];
		}
	};

	@IntRange(from = ASAP, to = MAX_DELAY)
	public int getInitialDelay() {
		return mInitialDelay;
	}

	public void setInitialDelay(@IntRange(from = ASAP, to = MAX_DELAY) final int initialDelay) {
		mInitialDelay = initialDelay;
	}

	@IntRange(from = MIN_PERIOD, to = MAX_PERIOD)
	public int getPeriod() {
		return mPeriod;
	}

	public void setPeriod(@IntRange(from = MIN_PERIOD, to = MAX_PERIOD) final int period) {
		mPeriod = period;
	}

	@IntRange(from = UNLIMITED_REPEAT, to = MAX_REPEAT)
	public int getRepeatTimes() {
		return mRepeatTimes;
	}

	public void setRepeatTimes(@IntRange(from = UNLIMITED_REPEAT, to = MAX_REPEAT) final int repeatTimes) {
		mRepeatTimes = repeatTimes;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(final Parcel parcel, final int i) {
		parcel.writeInt(mInitialDelay);
		parcel.writeInt(mPeriod);
		parcel.writeInt(mRepeatTimes);
	}

	@NonNull
	@Override
	public String toString() {
		return "PlatonScheduleWebOptions{" +
				"mInitialDelay=" + mInitialDelay +
				", mPeriod=" + mPeriod +
				", mRepeatTimes=" + mRepeatTimes +
				'}';
	}
}
