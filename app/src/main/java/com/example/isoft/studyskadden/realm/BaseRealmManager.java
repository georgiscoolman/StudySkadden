package com.example.isoft.studyskadden.realm;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by branavitski on 12.02.2015.
 */
public abstract class BaseRealmManager<T extends RealmObject> {
    protected Class<T> realmManagerClass;

    public BaseRealmManager(Class<T> realmManagerClass) {
        this.realmManagerClass = realmManagerClass;
    }

    public RealmObject save(Realm realm, T item) {
        realm.beginTransaction();
        RealmObject realmObject = realm.copyToRealmOrUpdate(item);
        realm.commitTransaction();

        return realmObject;
    }

    public void saveAll(Realm realm, List<T> items) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(items);
        realm.commitTransaction();
    }

    public RealmResults<T> getAll(Realm realm) {
        realm.beginTransaction();
        RealmResults<T> realmItems = realm.where(realmManagerClass).findAll();
        realm.commitTransaction();
        return realmItems;
    }

    public T getById(Realm realm, long id) {
        realm.beginTransaction();
        T result = realm.where(realmManagerClass).equalTo("id", id).findFirst();
        realm.commitTransaction();
        return result;
    }

    public T getById(Realm realm, String id) {
        realm.beginTransaction();
        T result = realm.where(realmManagerClass).equalTo("id", id).findFirst();
        realm.commitTransaction();
        return result;
    }

    public void remove(Realm realm, long id) {
        realm.beginTransaction();
        realm.where(realmManagerClass).equalTo("id", id).findFirst().deleteFromRealm();
        realm.commitTransaction();
    }

}
