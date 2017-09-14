package com.daniela.data.managers.remote;

/**
 * @author Daniela Perez danielaperez@kogimobile.com on 9/12/17.
 */
public interface Factory<T> {
    T factory() throws IllegalAccessException, InstantiationException;
}
