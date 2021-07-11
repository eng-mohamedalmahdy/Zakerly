package com.graduationproject.zakerly.intro.splash;

import android.content.Context;

import com.graduationproject.zakerly.core.cache.DataStoreManger;

import io.reactivex.rxjava3.core.Flowable;

public class SplashRepository{

    private final DataStoreManger dataStoreManger;

    public SplashRepository(Context context){
        this.dataStoreManger = DataStoreManger.getInstance(context);
    }
    public Flowable<Boolean> getIsFirstLaunch(){
        return dataStoreManger.getIsFirstLaunch();
    }
}
