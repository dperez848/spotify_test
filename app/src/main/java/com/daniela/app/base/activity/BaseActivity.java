package com.daniela.app.base.activity;

import android.app.ProgressDialog;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.daniela.app.base.events.BaseEventBusListener;
import com.daniela.app.base.EventBusLifeCycleObserver;
import com.daniela.app.base.RxLifeObserver;
import com.daniela.app.base.events.EventAlertDialog;
import com.daniela.app.base.events.EventProgressDialog;
import com.daniela.app.base.events.EventSnackbarMessage;
import com.daniela.app.base.events.SnackbarEventBuilder;
import com.daniela.app.utils.StringUtils;

import org.greenrobot.eventbus.Subscribe;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author Daniela Perez danielaperez@kogimobile.com on 9/12/17.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseEventBusListener,LifecycleRegistryOwner {

    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);
    private final RxLifeObserver rxLifeObserver = new RxLifeObserver();
    private final EventBusLifeCycleObserver busLifeObserver = new EventBusLifeCycleObserver(this);

    private Object navigationController;
    private ProgressDialog progress;

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

    protected <N extends BaseActivityNavigationController> N getNavigationController(){
        if(navigationController == null){
            navigationController = new BaseActivityNavigationController(getSupportFragmentManager());
        }
        return (N) navigationController;
    }

    protected <N extends BaseActivityNavigationController> void setNavigationController(N navigationController) {
        this.navigationController = navigationController;
    }

    abstract protected void initVars();

    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLifeCycleObservers();
        initVars();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initViews();
        initListeners();
    }

    private void initLifeCycleObservers(){
        getLifecycle().addObserver(rxLifeObserver);
        getLifecycle().addObserver(busLifeObserver);
    }

    abstract protected void initViews();

    abstract protected void initListeners();

    @CallSuper
    @Override
    @Subscribe
    public void onProgressDialogEvent(EventProgressDialog event) {
        buildProgressDialog(event);
    }

    private void buildProgressDialog(EventProgressDialog event){
        getProgress().dismiss();
        if(event.isDismiss()){
            return;
        }
        clearKeyboardFromScreen();
        getProgress().setCancelable(event.isCancelable());
        getProgress().setMessage(
                StringUtils.isEmpty(event.getMessage()) ? getString(event.getMessageId()) : event.getMessage()
        );
        getProgress().show();
    }

    @CallSuper
    @Override
    @Subscribe
    public void onAlertDialogEvent(EventAlertDialog alert) {
        buildAlertDialog(alert);
    }

    private void buildAlertDialog(EventAlertDialog alert){

        String title = StringUtils.isEmpty(alert.getTitle()) ? getString(alert.getTitleId()) : alert.getTitle();
        String message = StringUtils.isEmpty(alert.getMessage()) ? getString(alert.getMessageId()) : alert.getMessage();
        String positive = StringUtils.isEmpty(alert.getPositiveButtonText()) ? getString(alert.getPositiveTextId()) : alert.getPositiveButtonText();
        if(StringUtils.isEmpty(positive)){
            positive = getString(android.R.string.ok);
        }

        String negative = StringUtils.isEmpty(alert.getNegativeButtonText()) ? getString(alert.getNegativeTextId()) : alert.getNegativeButtonText();
        if(StringUtils.isEmpty(negative)){
            negative = getString(android.R.string.cancel);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(alert.isCancellable())
                .setPositiveButton(positive, alert.getPositiveListener() == null ? new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                } : alert.getPositiveListener());
        if (alert.getNegativeListener() != null) {
            builder.setNegativeButton(negative, alert.getNegativeListener() == null ? new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            } : alert.getNegativeListener());
        }
        builder.show();
    }

    @CallSuper
    @Override
    @Subscribe
    public void onSnackbarMessageEvent(EventSnackbarMessage event) {
        clearKeyboardFromScreen();
        SnackbarEventBuilder snackbarEventBuilder = new SnackbarEventBuilder(event, getViewDecorator());
        snackbarEventBuilder.showSnackbar();
    }

    private View getViewDecorator() {
        return getWindow().getDecorView();
    }

    public void clearKeyboardFromScreen() {
        if (getWindow().getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void openUrlWebPage(String url, int colorId) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ContextCompat.getColor(this, colorId));
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }

    @CallSuper
    @Override
    public void onBackPressed() {
        clearKeyboardFromScreen();
        super.onBackPressed();
    }

    private ProgressDialog getProgress() {
        if(progress == null){
            progress = new ProgressDialog(this);
        }
        return progress;
    }

    public void sendSuccessResult(Intent data) {
        setResult(RESULT_OK, data);
        finish();
    }

}
