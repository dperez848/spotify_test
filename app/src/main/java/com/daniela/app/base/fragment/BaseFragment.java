package com.daniela.app.base.fragment;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.daniela.app.base.EventBusLifeCycleObserver;
import com.daniela.app.base.events.EventGhost;
import com.daniela.app.base.RxLifeObserver;

import org.greenrobot.eventbus.Subscribe;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author Daniela Perez danielaperez@kogimobile.com on 9/12/17.
 */

public abstract class BaseFragment extends Fragment implements LifecycleRegistryOwner {

    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);
    private final RxLifeObserver rxLifeObserver = new RxLifeObserver();
    private final EventBusLifeCycleObserver busLifeObserver = new EventBusLifeCycleObserver(this);

    @Override
    public LifecycleRegistry getLifecycle() {
        return this.mRegistry;
    }

    public void addDisposable(Disposable disposable){
        rxLifeObserver.addDisposable(disposable);
    }

    public void addDisposableForever(Disposable disposable){
        rxLifeObserver.addDisposableForever(disposable);
    }

    public CompositeDisposable getDisposables() {
        return rxLifeObserver.getDisposables();
    }

    public CompositeDisposable getDisposablesForever() {
        return rxLifeObserver.getDisposablesForever();
    }

    abstract protected void initVars();

    @CallSuper
    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        initLifeCycleObservers();
        initVars();
    }

    private void initLifeCycleObservers(){
        getLifecycle().addObserver(rxLifeObserver);
        getLifecycle().addObserver(busLifeObserver);
    }

    @CallSuper
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        initListeners();
    }

    abstract protected void initViews();

    abstract protected void initListeners();

    @Subscribe
    public void onEventGhost(EventGhost event) {}

}
