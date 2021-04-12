package com.graduationproject.zakerly.core.cache.Realm;

import io.realm.Realm;

public class RealmClient{

private  static RealmClient mRealmClient = null;
private  final  ThreadLocal<Realm> localRealm = new ThreadLocal<>();

    public static RealmClient getInstance(){
        if(mRealmClient == null){
            mRealmClient = new RealmClient();
        }
        return mRealmClient;
    }

    public Realm openLocalInstance(){
        Realm realm = Realm.getDefaultInstance();
        if (localRealm.get()==null){
            localRealm.set(realm);
        }
        return realm;
    }

    public Realm getLocalInstance(){
        Realm realm = localRealm.get();
        if (realm==null){
            throw new IllegalStateException("No open Realms were found on this thread. ");
        }
        return  realm;
    }

    public void closeLocalInstance(){
        Realm realm = localRealm.get();
        if (realm == null){
            throw new IllegalStateException("Cannot Close a Realm that is not open. ");
        }
        realm.close();
        if (Realm.getLocalInstanceCount(Realm.getDefaultConfiguration()) <= 0){
            localRealm.set(null);
        }
    }


}






