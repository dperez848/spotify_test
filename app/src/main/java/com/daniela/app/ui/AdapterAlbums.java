package com.daniela.app.ui;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daniela.app.R;
import com.daniela.app.base.BaseAdapter;
import com.daniela.app.databinding.ListItemBinding;
import com.daniela.data.repositories.album.Album;

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
        }

        @Override
        public void onClick(View v) {
            if (getListener() != null) {
                getListener().onAlbumClick(getItems().get(getAdapterPosition()));
            }
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