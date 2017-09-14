package com.daniela.app.ui.detail;

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
import com.daniela.app.base.fragment.BaseFragment;
import com.daniela.app.databinding.FragmentExternalBinding;
import com.daniela.app.ui.EventHandlerNavigation;
import com.daniela.app.ui.detail.viewmodel.ViewModelExternalLink;

/**
 * @author Daniela Perez danielaperez@kogimobile.com on 9/13/17.
 */

public class FrgAlbumDetail extends BaseFragment implements EventHandlerNavigation {

    public static final String ARG_EXTERNAL_URL = "ARG_EXTERNAL_URL";
    public static final String ARG_IMAGE_URL = "ARG_IMAGE_URL";

    private ViewModelExternalLink viewModel;

    private FragmentExternalBinding binding;
    public static FrgAlbumDetail newInstance(String externalUrl, String imageUrl) {
        FrgAlbumDetail fragment = new FrgAlbumDetail();
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
                .placeholder(R.drawable.album_preview)
                .fitCenter()
                .dontAnimate()
                .into(binding.imageAlbum);
    }
}
