package com.daniela.app.ui.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MenuItem;

import com.daniela.app.R;
import com.daniela.app.base.activity.BaseActivityInnerNavigation;
import com.daniela.app.base.fragment.BaseFragment;
import com.daniela.app.base.fragment.BaseFragmentNavigator;

import com.daniela.app.databinding.ActivityMainBinding;
import com.daniela.app.ui.main.navigation.NavigationControllerActivityMain;

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
        enableHomeBackArrowIndicator();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                disableHomeBackArrowIndicator();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        disableHomeBackArrowIndicator();
    }
}
