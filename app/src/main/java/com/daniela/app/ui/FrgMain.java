package com.daniela.app.ui;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.daniela.app.ActivityMain;
import com.daniela.app.R;
import com.daniela.app.base.BaseFragment;
import com.daniela.app.base.events.EventAlertDialog;
import com.daniela.app.base.events.EventProgressDialog;
import com.daniela.app.databinding.FragmentMainBinding;
import com.daniela.data.managers.remote.Artist;
import com.daniela.data.repositories.album.Album;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * @author Daniela Perez danielaperez@kogimobile.com on 9/13/17.
 */

public class FrgMain extends BaseFragment implements EventHandlerNavigation, AdapterAlbums.ListenerAlbum {

    private FragmentMainBinding binding;
    private ViewModelMain viewModel;
    private AdapterAlbums adapter;

    public static FrgMain newInstance() {
        return new FrgMain();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewModel = ViewModelProviders.of(this).get(ViewModelMain.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        this.binding.setEventHandler(this);
        return this.binding.getRoot();
    }

    @Override
    protected void initVars() {
        adapter = new AdapterAlbums();
    }

    @Override
    protected void initViews() {
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initListeners() {
        setRefreshListener();
        setAdapterListener();
    }

    private void setAdapterListener() {
        adapter.setListener(this);
    }

    private void setRefreshListener() {
        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getArtist();
            }
        });
    }


    @Override
    public void onClickEvent() {
        EventBus.getDefault().post(EventProgressDialog.getBuilder()
                .withMessage(getString(R.string.frg_main_message_finding)).build());
        getArtist();
    }

    private void getArtist() {
        viewModel.searchArtist(binding.name.getText().toString())
                .subscribe(new Consumer<Artist>() {
                    @Override
                    public void accept(Artist artist) throws Exception {
                        setArtistInfo(artist);
                        getAlbums(artist);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        EventBus.getDefault().post(EventProgressDialog.getBuilder().dismiss().build());
                        EventBus.getDefault().post(EventAlertDialog.getBuilder()
                                .withMessage(getString(R.string.frg_main_error_artist)).build());
                    }
                });
    }



    private void getAlbums(Artist artist) {
        viewModel.getArtistAlbums(artist.getId())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        hideRefresh();
                        EventBus.getDefault().post(EventProgressDialog.getBuilder().dismiss().build());
                    }
                })
                .subscribe(new Consumer<List<Album>>() {
                    @Override
                    public void accept(List<Album> alba) throws Exception {
                        adapter.refreshItems(alba);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
    }

    private void hideRefresh() {
        this.binding.swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onAlbumClick(Album album) {
        ((ActivityMain) getActivity())
                .navigateToAlbumDetail(
                        FrgExternalLink.newInstance(album.getExternalUrl(), album.getImageUrl()),
                        album.getName());
    }

    private void setArtistInfo(Artist artist) {
        binding.artistName.setText(artist.getName());
        binding.popularity.setText(String.format(getString(R.string.value_int), artist.getPopularity()));
        binding.followers.setText(String.format(getString(R.string.value_int), artist.getTotalFollowers()));
        setArtistImage(artist.getImageUrl());
    }

    public void setArtistImage(String artistImage) {
        Glide.with(getActivity())
                .load(artistImage)
                .placeholder(R.mipmap.ic_launcher)
                .fitCenter()
                .dontAnimate()
                .into(binding.artistImage);
    }
}
