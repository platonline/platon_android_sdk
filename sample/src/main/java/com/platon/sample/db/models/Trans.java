package com.platon.sample.db.models;

import com.platon.sdk.util.PlatonHashUtil;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class Trans implements RealmModel {

    public final static String RECURRING_TOKEN = "mRecurringToken";

    @PrimaryKey
    private String mTransId;
    private String mPayerEmail;
    private String mCardNumber;
    private String mHash;

    private String mRecurringToken;

    public Trans() {
    }

    public Trans(final String payerEmail, final String cardNumber) {
        mPayerEmail = payerEmail;
        mCardNumber = cardNumber;
    }

    public String getRecurringToken() {
        return mRecurringToken;
    }

    public void setRecurringToken(final String recurringToken) {
        mRecurringToken = recurringToken;
    }

    public String getTransId() {
        return mTransId;
    }

    public void setTransId(final String id) {
        mTransId = id;
        mHash = PlatonHashUtil.encryptSale(mPayerEmail, mTransId, mCardNumber);
    }

    public String getPayerEmail() {
        return mPayerEmail;
    }

    public String getCardNumber() {
        return mCardNumber;
    }

    public String getHash() {
        return mHash;
    }
}
