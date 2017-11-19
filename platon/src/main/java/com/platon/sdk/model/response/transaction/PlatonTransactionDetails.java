package com.platon.sdk.model.response.transaction;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;
import com.platon.sdk.callback.PlatonBaseCallback;
import com.platon.sdk.constant.api.PlatonApiConstants.SerializedNames;
import com.platon.sdk.constant.api.PlatonStatus;
import com.platon.sdk.deserializer.PlatonBaseDeserializer;
import com.platon.sdk.endpoint.adapter.post.PlatonTransactionAdapter;
import com.platon.sdk.model.request.PlatonCard;
import com.platon.sdk.model.request.order.PlatonOrder;
import com.platon.sdk.model.request.payer.PlatonPayerSale;
import com.platon.sdk.model.response.base.PlatonBaseResponse;

import java.util.List;

/**
 * Model which used in {@link PlatonTransactionAdapter} in {@link PlatonBaseResponse} from {@link PlatonBaseCallback}
 * which deserialize from {@link PlatonBaseDeserializer}
 * <p>
 * Extends {@link PlatonTransactionStatus} and provide all details about transaction
 */
@SuppressWarnings("DanglingJavadoc")
public class PlatonTransactionDetails extends PlatonTransactionStatus {

    /**
     * action : GET_TRANS_STATUS
     * result : SUCCESS
     * status : PENDING
     * trans_id : 03346-89211-86461
     * order_id : ORDER-12345
     * name : Payer
     * mail : payer.mail@gmail.com
     * ip : 255.255.255.255
     * amount : 123.23
     * currency : UAH
     * card : 4111111111111111
     * transactions : [{"date":"2012-01-01 01:10:25","type":"AUTH","status":"1","amount":"1.95"},{"date":"2012-01-01 01:11:30","type":"CAPTURE","status":"1","amount":"1.95"},{"date":"2012-02-06 10:25:06","type":"REFUND","status":"1","amount":"1.95"}]
     */

    /**
     * Payer name
     * <p>
     * See {@link PlatonPayerSale} for the details
     */
    @SerializedName(SerializedNames.NAME)
    private String mName;

    /**
     * Payer email
     * <p>
     * See {@link PlatonPayerSale} for the details
     */
    @SerializedName(SerializedNames.MAIL)
    private String mEmail;

    /**
     * Payer IP address
     * <p>
     * See {@link PlatonPayerSale} for the details
     */
    @SerializedName(SerializedNames.IP)
    private String mIp;

    /**
     * Order amount
     * <p>
     * See {@link PlatonOrder} for the details
     */
    @SerializedName(SerializedNames.AMOUNT)
    private float mAmount;

    /**
     * Order currency
     * <p>
     * See {@link PlatonOrder} for the details
     */
    @SerializedName(SerializedNames.CURRENCY)
    private String mCurrency;

    /**
     * Card in the format XXXXXX****XXXX
     * <p>
     * See {@link PlatonCard} for the details
     */
    @SerializedName(SerializedNames.CARD)
    private String mCard;

    /**
     * Array of transactions
     * <p>
     * See {@link PlatonTransaction} for the details
     */
    @SerializedName(SerializedNames.TRANSACTIONS)
    private List<PlatonTransaction> mPlatonTransactions;

    protected PlatonTransactionDetails(final Parcel in) {
        super(in);
        mName = in.readString();
        mEmail = in.readString();
        mIp = in.readString();
        mAmount = in.readFloat();
        mCurrency = in.readString();
        mCard = in.readString();
        in.readTypedList(mPlatonTransactions, PlatonTransaction.CREATOR);
    }

    @PlatonStatus
    public String getStatus() {
        return mStatus;
    }

    public void setStatus(@PlatonStatus final String status) {
        mStatus = status;
    }

    public String getName() {
        return mName;
    }

    public void setName(final String name) {
        mName = name;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(final String email) {
        mEmail = email;
    }

    public String getIp() {
        return mIp;
    }

    public void setIp(final String ip) {
        mIp = ip;
    }

    public float getAmount() {
        return mAmount;
    }

    public void setAmount(final float amount) {
        mAmount = amount;
    }

    public String getCurrency() {
        return mCurrency;
    }

    public void setCurrency(final String currency) {
        mCurrency = currency;
    }

    public String getCard() {
        return mCard;
    }

    public void setCard(final String card) {
        mCard = card;
    }

    public List<PlatonTransaction> getPlatonTransactions() {
        return mPlatonTransactions;
    }

    public void setPlatonTransactions(final List<PlatonTransaction> platonTransactions) {
        mPlatonTransactions = platonTransactions;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(mName);
        parcel.writeString(mEmail);
        parcel.writeString(mIp);
        parcel.writeFloat(mAmount);
        parcel.writeString(mCurrency);
        parcel.writeString(mCard);
        parcel.writeTypedList(mPlatonTransactions);
    }

    @Override
    public String toString() {
        return "PlatonTransactionDetails{" +
                "mAction='" + mAction + '\'' +
                ", mResult='" + mResult + '\'' +
                ", mStatus='" + mStatus + '\'' +
                ", mOrderId='" + mOrderId + '\'' +
                ", mTransactionId='" + mTransactionId + '\'' +
                ", mName='" + mName + '\'' +
                ", mEmail='" + mEmail + '\'' +
                ", mIp='" + mIp + '\'' +
                ", mAmount=" + mAmount +
                ", mCurrency='" + mCurrency + '\'' +
                ", mCard='" + mCard + '\'' +
                ", mPlatonTransactions=" + mPlatonTransactions +
                '}';
    }

}
