package com.daniela.data.managers.remote.restclient;

import android.support.annotation.NonNull;


import com.daniela.app.R;
import com.daniela.app.base.remote.ApiServiceFactory;
import com.daniela.app.base.remote.ApiServiceInterface;
import com.daniela.app.base.remote.RestClientManager;
import com.daniela.app.base.remote.ServiceConfiguration;
import com.daniela.app.ui.app.App;
import com.daniela.data.managers.remote.RestManagerPrivateService;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.http2.Header;

/**
 * @author Daniela Perez danielaperez@kogimobile.com on 5/26/17.
 */

public class RestClient {

    private static RestClient instance = null;
    private static RestClientManager restClientManager;

    @NonNull
    public static RestClient getInstance() {
        if (instance == null) {
            instance = new RestClient();
            ServiceConfiguration<RestManagerPrivateService, ApiServiceInterface> privateServiceConfiguration =
                    new ServiceConfiguration<>(
                            App.getGlobalContext().getString(R.string.base_url),
                            new ApiServiceFactory<>(RestManagerPrivateService.class),
                            ApiServiceInterface.class);


            privateServiceConfiguration.setRequestInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    Request.Builder builder = (new Request.Builder()).url(request.url())
                            .method(request.method(), request.body());

                    Header currentHeader = new Header(App.getGlobalContext().getResources().getString(R.string.header_auth),
                            App.getGlobalContext().getResources().getString(R.string.token));
                    builder.addHeader(currentHeader.name.utf8(), currentHeader.value.utf8());
                    return chain.proceed(builder.build());
                }
            });
            restClientManager = new RestClientManager();
            restClientManager.addServiceConfiguration(privateServiceConfiguration);
        }
        return instance;
    }

    public static RestClientManager getRestClientManager() {
        return restClientManager;
    }
}
