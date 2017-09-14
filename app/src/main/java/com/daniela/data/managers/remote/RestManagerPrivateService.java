package com.daniela.data.managers.remote;

import com.daniela.app.base.remote.ApiServiceInterface;
import com.daniela.app.base.remote.BaseService;
import com.daniela.data.managers.remote.response.artist.ResponseArtistAlbums;
import com.daniela.data.managers.remote.response.artist.ResponseArtists;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Daniela Perez danielaperez@kogimobile.com on 9/12/17.
 */
public class RestManagerPrivateService extends BaseService<ApiServiceInterface> {

    private static final String ITEM_TYPE = "artist";

    public Single<ResponseArtists> searchArtist(String artistName) {
        return getApiService()
                .searchArtist(artistName, ITEM_TYPE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ResponseArtistAlbums> getArtistAlbums(String artistId) {
        return getApiService()
                .getArtistAlbums(artistId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
