package com.daniela.app.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daniela.app.R;
import com.daniela.app.base.fragment.BaseFragment;
import com.daniela.app.base.events.EventProgressDialog;
import com.daniela.app.base.events.EventSnackbarMessage;
import com.daniela.app.databinding.FragmentMainBinding;
import com.daniela.app.ui.main.adapter.AdapterAlbums;
import com.daniela.app.ui.EventHandlerNavigation;
import com.daniela.app.ui.detail.FrgAlbumDetail;
import com.daniela.app.ui.main.viewmodel.ViewModelMain;
import com.daniela.data.entities.Artist;
import com.daniela.data.entities.Album;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

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

    @Override
    protected void initListeners() {
        setRefreshListener();
        setAdapterListener();
        setEditNameListener();
    }

    private void setEditNameListener() {
        binding.name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.FLAG_EDITOR_ACTION)) || (actionId == EditorInfo.IME_ACTION_SEARCH)) {
                    EventBus.getDefault().post(EventProgressDialog.getBuilder()
                            .withMessage(getString(R.string.frg_main_message_finding)).build());
                    getArtist();
                }
                return false;
            }
        });

        binding.name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().isEmpty()) {
                    binding.searchClear.setVisibility(View.INVISIBLE);
                } else {
                    binding.searchClear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
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

    private void setupRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClickEvent() {
        binding.name.setText("");
        binding.artistCard.setVisibility(View.GONE);
        binding.recyclerView.setVisibility(View.GONE);
        binding.defaultCommand.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAlbumClick(Album album) {
        ((ActivityMain) getActivity())
                .navigateToAlbumDetail(
                        FrgAlbumDetail.newInstance(album.getExternalUrl(), album.getImageUrl()),
                        album.getName());
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
                        EventBus.getDefault().post(EventProgressDialog.getBuilder().
                                dismiss().build());
                        EventBus.getDefault().post(EventSnackbarMessage.getBuilder().
                                withMessage(getString(R.string.frg_main_error_artist)).build());
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


    private void setArtistInfo(Artist artist) {
        binding.artistName.setText(artist.getName());
        binding.popularity.setText(String.format(getString(R.string.popularity_value), artist.getPopularity()));
        binding.followers.setText(String.format(getString(R.string.followers_value), artist.getTotalFollowers()));
        binding.artistCard.setVisibility(View.VISIBLE);
        binding.recyclerView.setVisibility(View.VISIBLE);
        binding.defaultCommand.setVisibility(View.INVISIBLE);
        setArtistImage(artist.getImageUrl());
    }

    public void setArtistImage(String artistImage) {
        Glide.with(getActivity())
                .load(artistImage)
                .placeholder(R.drawable.placeholder)
                .fitCenter()
                .dontAnimate()
                .into(binding.artistImage);
    }
}
