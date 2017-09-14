package com.daniela.app.ui.main.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.daniela.data.Injection;
import com.daniela.data.entities.Artist;
import com.daniela.data.entities.Album;


import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author Daniela Perez danielaperez@kogimobile.com on 9/13/17.
 */

public class ViewModelMain extends ViewModel {

    public Single<Artist> searchArtist(String artistName){
       return Injection.getArtistRepository().searchArtist(artistName).observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<Album>> getArtistAlbums(String artistId){
        return Injection.getAlbumRepository().getArtistAlbums(artistId).observeOn(AndroidSchedulers.mainThread());
    }

}
