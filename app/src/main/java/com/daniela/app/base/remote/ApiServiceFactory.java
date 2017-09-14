package com.daniela.app.base.remote;

/**
 * @author Daniela Perez danielaperez@kogimobile.com on 9/12/17.
 */
public class ApiServiceFactory<T> implements Factory<T> {

    private final Class<T> serviceClass;

    public ApiServiceFactory(Class<T> serviceClass){
        this.serviceClass = serviceClass;
    }

    @Override
    public T factory() throws IllegalAccessException, InstantiationException {
        return serviceClass.newInstance();
    }

}
