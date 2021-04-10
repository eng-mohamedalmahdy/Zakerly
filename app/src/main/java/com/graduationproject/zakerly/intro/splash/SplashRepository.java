package com.graduationproject.zakerly.intro.splash;

import android.content.Context;

import com.graduationproject.zakerly.core.cache.DataStoreManger;

import io.reactivex.rxjava3.core.Flowable;

public class SplashRepository{

   private Context context;
   private DataStoreManger dataStoreManger;

    public SplashRepository(Context context){
        this.context = context ;
        this.dataStoreManger = DataStoreManger.getInstance(context);
    }
    public Flowable<Boolean> getIsFirstLaunch(){
        return dataStoreManger.getIsFirstLaunch();
    }
}
