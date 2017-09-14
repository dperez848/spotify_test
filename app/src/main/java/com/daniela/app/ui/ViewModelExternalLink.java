package com.daniela.app.ui;

import android.arch.lifecycle.ViewModel;
import android.os.Bundle;

/**
 * @author Daniela Perez danielaperez@kogimobile.com on 9/13/17.
 */

public class ViewModelExternalLink extends ViewModel {

    private String externalUrl;
    private String imageUrl;


    public void doSetArguments(Bundle bundle) {
        if (bundle != null) {
            this.externalUrl = bundle.getString(FrgExternalLink.ARG_EXTERNAL_URL);
            this.imageUrl = bundle.getString(FrgExternalLink.ARG_IMAGE_URL);
        }
    }

    public String getExternalUrl() {
        return externalUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
