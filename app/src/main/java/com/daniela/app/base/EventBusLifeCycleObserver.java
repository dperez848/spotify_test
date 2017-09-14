package com.daniela.app.base;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * @author Daniela Perez danielaperez@kogimobile.com on 9/12/17.
 */
public class EventBusLifeCycleObserver implements LifecycleObserver {

    private Object subscriber;

    public EventBusLifeCycleObserver(Object subscriber){
        this.subscriber = subscriber;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume(){
        EventBus.getDefault().register(subscriber);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause(){
        EventBus.getDefault().unregister(subscriber);
    }

}
