package com.daniela.app.base;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

/**
 * @author Daniela Perez danielaperez@kogimobile.com on 9/12/17.
 */

public class BaseApp extends MultiDexApplication {

    private static Context globalContext;

    @Override
    public void onCreate() {
        super.onCreate();
        globalContext = this;
    }

    public static Context getGlobalContext() {
        return globalContext;
    }
}
