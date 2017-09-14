package com.daniela.data.repositories.artist;

import android.support.annotation.NonNull;


import com.daniela.data.managers.remote.Artist;
import com.daniela.data.repositories.artist.source.RestArtistSource;

import io.reactivex.Single;


/**
 * @author Daniela Perez danielaperez@kogimobile.com on 5/24/17.
 */

public class RepositoryArtist implements RepositoryArtistDataSource {

    private static RepositoryArtist instance = null;
    private static RestArtistSource sourceRest;

    @NonNull
    public static RepositoryArtist newInstance() {
        if (instance == null) {
            instance = new RepositoryArtist();
        }
        return instance;
    }


    private RestArtistSource getRestSource() {
        if (sourceRest == null) {
            sourceRest = RestArtistSource.newInstance();
        }
        return sourceRest;
    }

    @Override
    public Single<Artist> searchArtist(String artistName) {
        return getRestSource().searchArtist(artistName);
    }


//    @Override
//    public Observable<LogonResponse> logon(LogonRequest logonRequest) {
//        return getRestSource().logon(logonRequest);
//    }
//
//    @Override
//    public Observable<Artist> get(String userId) {
//        return getRestSource().get(userId);
//    }
//
//    @Override
//    public Observable<List<Artist>> getUsers() {
//        return getRestSource().getUsers();
//    }
//
//    @Override
//    public Observable<Artist> getUser() {
//        return getRestSource().getUser();
//    }
//
//    @Override
//    public Observable<RestArtist> updateUser(UserRequest userRequest) {
//        return getRestSource().updateUser(userRequest);
//    }
//
//    @Override
//    public Observable<List<RestQueueByMember>> getQueues() {
//        return getRestSource().getQueues();
//    }
//
//    @Override
//    public Observable<List<String>> getConsultantsEmails() {
//        return getRestSource().getConsultantsEmails();
//    }
//
//
//    @Override
//    public Observable<List<RestMemberBC>> getMyMembersInLine() {
//        return getRestSource().getMyMembersInLine();
//    }
//
//    @Override
//    public Observable<Void> getInLine(String queueId) {
//        return getRestSource().getInLine(queueId);
//    }
//
//    @Override
//    public Observable<Void> removeFromLine(String queueId) {
//        return getRestSource().removeFromLine(queueId);
//    }
//
//    @Override
//    public Observable<Void> getMemberInLine(String memberId) {
//        return getRestSource().getMemberInLine(memberId);
//    }
//
//    @Override
//    public Observable<Void> setMemberDone(String memberId) {
//        return getRestSource().setMemberDone(memberId);
//    }
//
//    @Override
//    public Observable<Void> removeMemberFromLine(String memberId) {
//        return getRestSource().removeMemberFromLine(memberId);
//    }
//
//    @Override
//    public Observable<Artist> create(String id, String name) {
//        return getRestSource().create(id, name);
//    }
//
//    @Override
//    public Observable<Artist> update(String id, String name) {
//        return getRestSource().update(id, name);
//    }
//
//    @Override
//    public Observable<Void> sendDeviceToken(String deviceToken) {
//        return getRestSource().sendDeviceToken(deviceToken);
//    }
//
//    @Override
//    public Observable<Void> removeDeviceToken(String deviceToken) {
//        return getRestSource().removeDeviceToken(deviceToken);
//    }

}
