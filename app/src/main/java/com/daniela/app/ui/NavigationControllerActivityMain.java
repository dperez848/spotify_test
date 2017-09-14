package com.daniela.app.ui;

import com.daniela.app.R;
import com.daniela.app.base.BaseActivityInnerNavigation;
import com.daniela.app.base.BaseActivityInnerNavigationController;
import com.daniela.app.base.BaseFragment;

import java.util.HashMap;

public class NavigationControllerActivityMain extends BaseActivityInnerNavigationController {

    private HashMap<String,BaseFragment> navFragments;

    public NavigationControllerActivityMain(BaseActivityInnerNavigation activity) {
        super(activity, R.id.container);
        initFragments();
    }

    private void initFragments(){
        this.navFragments = new HashMap<>();
        this.navFragments.put(getSection1Title(),FrgMain.newInstance());
    }

    public void navigateToSection1(){
        navigateToRootLevel(this.navFragments.get(getSection1Title()), getSection1Title());
    }

    public void navigateToSection1LowLevel(BaseFragment frg,String title){
        navigateToLowLevel(frg,title);
    }

    public String getSection1Title(){
        return getContext().getString(R.string.main_title);
    }

}