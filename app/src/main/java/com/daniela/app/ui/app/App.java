package com.daniela.app.ui.app;


import com.daniela.app.base.BaseApp;

import timber.log.Timber;

/**
 * @author Daniela Perez danielaperez@kogimobile.com on 5/24/17.
 */

public class App extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
        initTimber();
    }

    private void initTimber(){
        Timber.plant(
                new Timber.DebugTree() {
                    // Add the line number to the tag.
                    @Override
                    protected String createStackElementTag(StackTraceElement element) {
                        return super.createStackElementTag(element) + ':' + element.getLineNumber();
                    }
                });
    }

}