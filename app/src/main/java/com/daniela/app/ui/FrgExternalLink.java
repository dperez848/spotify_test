package com.daniela.app.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.daniela.app.R;
import com.daniela.app.base.BaseFragment;
import com.daniela.app.databinding.FragmentExternalBinding;

/**
 * @author Daniela Perez danielaperez@kogimobile.com on 9/13/17.
 */

public class FrgExternalLink extends BaseFragment implements EventHandlerNavigation {

    public static final String ARG_EXTERNAL_URL = "ARG_EXTERNAL_URL";
    public static final String ARG_IMAGE_URL = "ARG_IMAGE_URL";

    private ViewModelExternalLink viewModel;

    private FragmentExternalBinding binding;
    public static FrgExternalLink newInstance(String externalUrl, String imageUrl) {
        FrgExternalLink fragment = new FrgExternalLink();
        Bundle args = new Bundle();
        args.putString(ARG_EXTERNAL_URL, externalUrl);
        args.putString(ARG_IMAGE_URL, imageUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewModel = ViewModelProviders.of(this).get(ViewModelExternalLink.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_external, container, false);
        this.binding.setEventHandler(this);
        return this.binding.getRoot();
    }

    @Override
    protected void initVars() {
    }

    @Override
    protected void initViews() {
        viewModel.doSetArguments(getArguments());

        setAlbumImage();
    }

    @Override
    protected void initListeners() {

    }


    @Override
    public void onClickEvent() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(viewModel.getExternalUrl())));
    }

    private void setAlbumImage() {
        Glide.with(getActivity())
                .load(viewModel.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fitCenter()
                .dontAnimate()
                .into(binding.imageAlbum);
    }
}
