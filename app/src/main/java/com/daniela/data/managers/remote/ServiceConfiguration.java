package com.daniela.data.managers.remote;

import android.webkit.URLUtil;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.internal.http2.Header;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * @author Daniela Perez danielaperez@kogimobile.com on 9/12/17.
 */
public class ServiceConfiguration<S extends BaseService<I>, I> {

    private final String baseURL;

    private final ApiServiceFactory<S> apiServiceFactory;
    private final Class<I> apiInterfaceServiceClass;
    private final Class<? extends BaseService> apiServiceClass;

    private GsonConverterFactory converter = GsonConverterFactory.create(new GsonBuilder()
            .registerTypeHierarchyAdapter(Collection.class, new CollectionTypedAdapter())
            .registerTypeHierarchyAdapter(Long.class, new LongTypedAdapter())
            .registerTypeHierarchyAdapter(Double.class, new DoubleTypedAdapter())
            .excludeFieldsWithoutExposeAnnotation()
            .create());
    private ArrayList<Header> headers = new ArrayList<>();
    private HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private OkHttpClient client;
    private Interceptor requestInterceptor;

    /**
     * @param baseURL           must be a valid URL
     * @param apiServiceFactory must extend from the BaseService¡
     */
    public ServiceConfiguration(String baseURL, ApiServiceFactory<S> apiServiceFactory,
                                Class apiInterfaceServiceClass) {

        if (isValidUrl(baseURL)) {
            throw new RuntimeException("url cannot be empty!!!");
        } else if (!URLUtil.isValidUrl(baseURL)) {
            throw new RuntimeException("url is not valid!!!");
        }
        this.baseURL = baseURL;
//            apiInterfaceService = apiInterfaceServiceFactory.factory();
        this.apiInterfaceServiceClass = apiInterfaceServiceClass;
        if (!apiInterfaceServiceClass.isInterface()) {
            throw new RuntimeException("Interface is not an interface!!!");
        }
        try {
            apiServiceClass = apiServiceFactory.factory().getClass();
        } catch (IllegalAccessException e) {
            throw new RuntimeException("IllegalAccessException", e);
        } catch (InstantiationException e) {
            throw new RuntimeException("InstantiationException", e);
        }
        this.apiServiceFactory = apiServiceFactory;
    }

    private boolean isValidUrl(String baseURL) {
        return baseURL == null || baseURL.trim().length() == 0;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public Class<I> getInterfaceClass() {
        return apiInterfaceServiceClass;
    }

    public Class<? extends BaseService> getApiServiceClass() {
        return apiServiceClass;
    }

    /**
     * Remember to subclass the ServiceConfiguration instance for runtime operations.
     * Suggested for this field
     *
     * @return a log level for retrofit @see retrofit.RestAdapter.LogLevel
     */
    public HttpLoggingInterceptor.Level getLogLevel() {
        return loggingInterceptor.getLevel();
    }

    public void setLogLevel(HttpLoggingInterceptor.Level logLevel) {
        this.loggingInterceptor.setLevel(logLevel);
    }

    /**
     * @return by Default an OkClient
     */
    public OkHttpClient getClient() {
        if (client == null) {
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            builder.readTimeout(30, TimeUnit.SECONDS).addInterceptor(loggingInterceptor);
            if (requestInterceptor != null) {
                builder.addInterceptor(requestInterceptor);
            }
            client = builder.build();
        }
        return client;
    }

    public void setClient(OkHttpClient client) {
        this.client = client;
    }

    /**
     * Remember to subclass the ServiceConfiguration instance for runtime operations.
     * Suggested for this field
     *
     * @return an arrayList of headers
     * @deprecated use setInterceptor instead
     */
    public ArrayList<Header> getHeaders() {
        return headers;
    }

    /**
     * deprecated use setInterceptor instead
     *
     * @param headers
     */
    public void setHeaders(ArrayList<Header> headers) {
        this.headers = headers;
    }

    public GsonConverterFactory getConverter() {
        return converter;
    }

    public void setConverter(GsonConverterFactory converter) {
        this.converter = converter;
    }

    public ApiServiceFactory<S> getApiServiceFactory() {
        return apiServiceFactory;
    }

    public Interceptor getRequestInterceptor() {
        return requestInterceptor;
    }

    public void setRequestInterceptor(Interceptor requestInterceptor) {
        this.requestInterceptor = requestInterceptor;
    }


}
