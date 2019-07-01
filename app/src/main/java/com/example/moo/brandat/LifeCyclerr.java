package com.example.moo.brandat;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ProcessLifecycleOwner;
import android.util.Log;

public class LifeCyclerr extends Application implements LifecycleObserver {


    ///////////////////////////////////////////////
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onEnterForeground() {
        Log.d("AppController", "Foreground");
        isAppInBackground(false);
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onEnterBackground() {
        Log.d("AppController", "Background");
        isAppInBackground(true);
    }
///////////////////////////////////////////////



    // Adding some callbacks for test and log
    public interface ValueChangeListener {
        void onChanged(Boolean value);
    }
    private ValueChangeListener visibilityChangeListener;
    public void setOnVisibilityChangeListener(ValueChangeListener listener) {
        this.visibilityChangeListener = listener;
    }
    private void isAppInBackground(Boolean isBackground) {
        if (null != visibilityChangeListener) {
            visibilityChangeListener.onChanged(isBackground);
        }
    }
    private static LifeCyclerr mInstance;
    public static LifeCyclerr getInstance() {
        return mInstance;
    }



    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        // addObserver
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

}