package com.graduationproject.zakerly.intro.splash;

import androidx.lifecycle.ViewModel;

public class SplashViewModel extends ViewModel {

     private SplashRepository splashRepository ;
     public SplashViewModel(SplashRepository splashRepository){
       this.splashRepository = splashRepository ;
   }
}

