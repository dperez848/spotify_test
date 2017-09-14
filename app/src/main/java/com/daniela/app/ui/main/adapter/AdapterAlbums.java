package com.daniela.app.ui.main.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.daniela.app.R;
import com.daniela.app.base.fragment.BaseAdapter;
import com.daniela.app.databinding.ListItemBinding;
import com.daniela.data.entities.Album;

/**
 * @author Daniela Perez danielaperez@kogimobile.com on 9/13/17.
 */


public class AdapterAlbums extends BaseAdapter<Album, BaseAdapter.BaseViewHolder<Album>> {

    private ListenerAlbum listener;

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding;
        switch (viewType) {
            case EMPTY_VIEW: {
                binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_empty, parent, false);
                return new EmptyViewHolder(binding.getRoot());
            }
            case LOAD_MORE_VIEW: {
                binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_loading_more, parent, false);
                return new EmptyViewHolder(binding.getRoot());
            }
            case LOADING_VIEW: {
                binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_loading, parent, false);
                return new EmptyViewHolder(binding.getRoot());
            }
            default:
                binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item, parent, false);
                return new ThemeHolder(binding.getRoot());
        }
    }

    class ThemeHolder extends BaseViewHolder<Album> implements View.OnClickListener {

        public ThemeHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        protected ListItemBinding getBinding() {
            return super.getBinding();
        }

        @Override
        protected void bindView(Album album) {
            getBinding().itemName.setText(album.getName());
            setAlbumImage(album);
            setAvailableMarkets(album);
        }

        @Override
        public void onClick(View v) {
            if (getListener() != null) {
                getListener().onAlbumClick(getItems().get(getAdapterPosition()));
            }
        }

        private void setAlbumImage(Album album) {
            Glide.with(getBinding().artistImage.getContext())
                    .load(album.getImageUrl())
                    .placeholder(R.drawable.album_preview)
                    .fitCenter()
                    .dontAnimate()
                    .into(getBinding().artistImage);
        }

        private void setAvailableMarkets(Album album) {
            if (hasAvailableMarkets(album)) {
                getBinding().itemMarkets.setVisibility(View.VISIBLE);
                getBinding().itemMarkets.setText(
                        getBinding().itemMarkets.getContext().getResources().getString(
                                R.string.value_string, album.getCountriesMarkets()));
            }else{
                getBinding().itemMarkets.setVisibility(View.GONE);
            }
        }

        private boolean hasAvailableMarkets(Album album) {
            return album.getAvailableMarkets() != null && !album.getAvailableMarkets().isEmpty();
        }
    }

    public ListenerAlbum getListener() {
        return listener;
    }

    public void setListener(ListenerAlbum listener) {
        this.listener = listener;
    }

    public interface ListenerAlbum {
        void onAlbumClick(Album album);
    }
}