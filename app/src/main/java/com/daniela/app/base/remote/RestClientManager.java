package com.daniela.app.base.remote;

import java.util.Collection;
import java.util.Hashtable;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * @author Daniela Perez danielaperez@kogimobile.com on 9/12/17.
 */

public class RestClientManager<T> {

    private Hashtable<Class, BaseService> apiServicesInstances = new Hashtable<>();

    public RestClientManager(ServiceConfiguration... serviceConfigurations) {
        setServiceConfigurations(serviceConfigurations);
    }

    public RestClientManager addServiceConfiguration(ServiceConfiguration serviceConfiguration) {
        initializeServiceConfiguration(serviceConfiguration);
        return this;
    }

    public T getService(Class<T> baseServiceClass) {
        return baseServiceClass.cast(apiServicesInstances.get(baseServiceClass));
    }

    public Collection<BaseService> getServices() {
        return apiServicesInstances.values();
    }

    public RestClientManager deleteService(Class serviceConfigurationClass) {
        apiServicesInstances.remove(serviceConfigurationClass);
        return this;
    }

    public RestClientManager deleteServiceConfigurations() {
        apiServicesInstances = new Hashtable<>();
        return this;
    }

    private void setServiceConfigurations(ServiceConfiguration[] serviceConfigurations) {
        apiServicesInstances = new Hashtable<>();
        for (ServiceConfiguration currentServiceConfiguration : serviceConfigurations) {
            initializeServiceConfiguration(currentServiceConfiguration);
        }
    }

    private void initializeServiceConfiguration(final ServiceConfiguration currentServiceConfiguration) {
        Retrofit retrofit = configRetrofit(currentServiceConfiguration);
        Object apiServiceInterface = retrofit.create(currentServiceConfiguration.getInterfaceClass());
        try {
            addApisServicesToRetrofit(currentServiceConfiguration, apiServiceInterface);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private void addApisServicesToRetrofit(ServiceConfiguration currentServiceConfiguration, Object apiServiceInterface) throws IllegalAccessException, InstantiationException {
        BaseService apiService = (BaseService) currentServiceConfiguration.getApiServiceFactory().factory();
        apiService.setApiServiceInterface(apiServiceInterface);
        apiServicesInstances.put(currentServiceConfiguration.getApiServiceClass(), apiService);
    }

    private Retrofit configRetrofit(ServiceConfiguration currentServiceConfiguration) {
        return new Retrofit.Builder()
                .baseUrl(currentServiceConfiguration.getBaseURL())
                .addConverterFactory(currentServiceConfiguration.getConverter())
                .client(currentServiceConfiguration.getClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

}
