package com.platon.sample.db;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.log.RealmLog;

public class DBHelper {

    private final static String REALM_NAME = "platon_sdk_sample.realm";
    private final static int SCHEMA_VERSION = 0;

    public static void init(@NonNull final Context context) {
        Realm.init(context);
        RealmLog.setLevel(Log.DEBUG);

        final RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(SCHEMA_VERSION)
                .name(REALM_NAME)
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public static Realm getRealmInstance() {
        return Realm.getDefaultInstance();
    }


    public static void closeRealmInstance(final Realm realmInstanceToClose) {
        realmInstanceToClose.close();
    }

    public static void performTransaction(final Realm.Transaction transaction) {
        if (transaction == null) return;
        final Realm realm = Realm.getDefaultInstance(); //!!! must be separate realm
        realm.executeTransaction(transaction);
        DBHelper.closeRealmInstance(realm);
    }

    public static void performTransactionAsync(
            final Realm.Transaction transaction,
            @Nullable final Realm.Transaction.OnSuccess onSuccess,
            @Nullable final Realm.Transaction.OnError onError) {
        if (transaction == null) return;
        final Realm realm = getRealmInstance(); //!!! must be separate realm
        if (onSuccess == null && onError == null) {
            realm.executeTransactionAsync(transaction);
        } else if (onSuccess != null && onError == null) {
            realm.executeTransactionAsync(transaction, onSuccess);
        } else if (onSuccess == null) {
            realm.executeTransactionAsync(transaction, onError);
        } else {
            realm.executeTransactionAsync(transaction, onSuccess, onError);
        }
        DBHelper.closeRealmInstance(realm);
    }

    public static <E extends RealmModel> RealmResults<E> findAllOff(final Class<E> clazz) {
        final Realm realm = Realm.getDefaultInstance();
        RealmResults<E> results = findAllOff(realm, clazz);
        DBHelper.closeRealmInstance(realm);
        return results;
    }

    public static <E extends RealmModel> E findFirst(final Class<E> clazz) {
        final Realm realm = Realm.getDefaultInstance();
        E results = findFirst(realm, clazz);
        DBHelper.closeRealmInstance(realm);
        return results;
    }

    private static <E extends RealmModel> RealmResults<E> findAllOff(
            final Realm realm, final Class<E> clazz
    ) {
        return realm.where(clazz).findAll();
    }

    private static <E extends RealmModel> E findFirst(final Realm realm, final Class<E> clazz) {
        return realm.where(clazz).findFirst();
    }

    public static <E extends RealmModel> void copyToRealmOrUpdateAsync(
            @NonNull final List<E> objects,
            @Nullable final Realm.Transaction.OnSuccess successCallback) {

        final Realm realm = Realm.getDefaultInstance();
        final Realm.Transaction transaction = bgRealm -> bgRealm.copyToRealmOrUpdate(objects);
        if (successCallback != null) realm.executeTransactionAsync(transaction, successCallback);
        else realm.executeTransactionAsync(transaction);
    }

    public static <E extends RealmModel> void copyToRealmOrUpdateAsync(
            @NonNull final E object,
            @Nullable final Realm.Transaction.OnSuccess successCallback) {

        final Realm realm = Realm.getDefaultInstance();
        final Realm.Transaction transaction = bgRealm -> bgRealm.copyToRealmOrUpdate(object);
        if (successCallback != null) realm.executeTransactionAsync(transaction, successCallback);
        else realm.executeTransactionAsync(transaction);
    }

    public static <E extends RealmModel> void copyToRealmOrUpdate(@NonNull final E object) {
        final Realm realm = Realm.getDefaultInstance();
        DBHelper.closeRealmInstance(realm);
    }

    private static <E extends RealmModel> E copyToRealmOrUpdate(
            final Realm realm, @NonNull final E object
    ) {
        realm.beginTransaction();
        E realmObject = realm.copyToRealmOrUpdate(object);
        realm.commitTransaction();
        return realmObject;
    }

    public static <E extends RealmModel> List<E> copyToRealmOrUpdate(
            @NonNull final List<E> objects
    ) {
        final Realm realm = Realm.getDefaultInstance();
        copyToRealmOrUpdate(realm, objects);
        DBHelper.closeRealmInstance(realm);
        return objects;
    }

    private static <E extends RealmModel> void copyToRealmOrUpdate(
            final Realm realm, @NonNull final List<E> objects
    ) {
        realm.beginTransaction();
        for (E object : objects) realm.copyToRealmOrUpdate(object);
        realm.commitTransaction();
    }

    public static void createObjectFromJson(
            @NonNull final Class<? extends RealmModel> clazz, @NonNull final String json
    ) {
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.createAllFromJson(clazz, json);
        realm.commitTransaction();
        DBHelper.closeRealmInstance(realm);
    }

    public static <E extends RealmObject> void createAllFromJson(
            @Nullable final Class<E> clazz, @Nullable final JSONArray jsonArray
    ) {
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.createAllFromJson(clazz, jsonArray);
        realm.commitTransaction();
        DBHelper.closeRealmInstance(realm);
    }

    public static <E extends RealmObject> void createOrUpdateObjectFromJson(
            @Nullable final Class<E> clazz, @Nullable final JSONObject jsonObject
    ) {
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.createOrUpdateObjectFromJson(clazz, jsonObject);
        realm.commitTransaction();
        DBHelper.closeRealmInstance(realm);
    }

    public static <E extends RealmObject> void createOrUpdateObjectFromJson(
            @Nullable final Class<E> clazz, @Nullable final String json
    ) {
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.createOrUpdateObjectFromJson(clazz, json);
        realm.commitTransaction();
        DBHelper.closeRealmInstance(realm);
    }

    public static <E extends RealmModel> RealmResults<E> findAllOfWhere(
            final Class<E> clazz, final String fieldName, final String value
    ) {
        final Realm realm = Realm.getDefaultInstance();
        return realm.where(clazz).equalTo(fieldName, value, Case.INSENSITIVE).findAll();
    }

    public static <E extends RealmModel> E findFirstOfWhere(
            final Class<E> clazz, final String fieldName, final String value
    ) {
        final Realm realm = Realm.getDefaultInstance();
        return realm.where(clazz).equalTo(fieldName, value, Case.INSENSITIVE).findFirst();
    }

    public static <E extends RealmModel> boolean isExist(
            final Class<E> clazz, final String fieldName, final String value
    ) {
        final Realm realm = Realm.getDefaultInstance();
        return realm.where(clazz).equalTo(fieldName, value, Case.INSENSITIVE).findFirst() != null;
    }

    public static <E extends RealmModel> E find(
            final Class<E> clazz, final String fieldName, final String value
    ) {
        final Realm realm = Realm.getDefaultInstance();
        return realm.where(clazz).equalTo(fieldName, value).findFirst();
    }

    public static <E extends RealmModel> E find(
            final Class<E> clazz, final String fieldName, final int value
    ) {
        final Realm realm = Realm.getDefaultInstance();
        return realm.where(clazz).equalTo(fieldName, value).findFirst();
    }

    public static <E extends RealmModel> E find(
            final Class<E> clazz, final String fieldName, final long value
    ) {
        final Realm realm = Realm.getDefaultInstance();
        return realm.where(clazz).equalTo(fieldName, value).findFirst();
    }

    public static <T extends RealmModel> T copyFromRealm(@Nullable final T realmModel) {
        if (realmModel == null) return null;
        return Realm.getDefaultInstance().copyFromRealm(realmModel);
    }

    public static <T extends RealmModel> List<T> copyFromRealm(final Iterable<T> realmModels) {
        return Realm.getDefaultInstance().copyFromRealm(realmModels);
    }

}