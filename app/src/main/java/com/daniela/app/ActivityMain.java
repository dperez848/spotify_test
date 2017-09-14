package com.daniela.app;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.daniela.app.base.BaseActivityInnerNavigation;
import com.daniela.app.base.BaseFragment;
import com.daniela.app.base.BaseFragmentNavigator;

import com.daniela.app.databinding.ActivityMainBinding;
import com.daniela.app.ui.NavigationControllerActivityMain;

public class ActivityMain extends BaseActivityInnerNavigation {

    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @NonNull
    @Override
    public NavigationControllerActivityMain getNavigationController() {
        return (NavigationControllerActivityMain) super.getNavigationController();
    }

    @Override
    protected void initVars() {
        setNavigationController(new NavigationControllerActivityMain(this));
    }

    @Override
    protected void initViews() {
        initToolbar();
        navigateToSection(1);
    }

    private void initToolbar() {
        setSupportActionBar(binding.includeToolbar.toolbar);
    }

    @Override
    protected void initListeners() {

    }


    private void navigateToSection(int itemId){
        BaseFragmentNavigator.cleanFragmentStack(getSupportFragmentManager());
        getTitleStack().clear();
        switch (itemId) {
            case 1:
                getNavigationController().navigateToSection1();
                break;

        }
        updateActionBarTitle();
    }

    public void navigateToAlbumDetail(BaseFragment frg, String title){
        getNavigationController().navigateToSection1LowLevel(frg,title);
        updateActionBarTitle();
    }


}
