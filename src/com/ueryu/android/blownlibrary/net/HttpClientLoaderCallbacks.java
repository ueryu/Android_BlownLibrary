/**
 * Copyright (c) 2013 ueryu All Rights Reserved.
 */

package com.ueryu.android.blownlibrary.net;

import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;

import com.ueryu.android.blownlibrary.internals.log.ExLog;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

/**
 * @author ueryu
 */
public abstract class HttpClientLoaderCallbacks<D> implements LoaderCallbacks<D> {
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";

    /**
     * 認証情報.
     * 
     * @author ueryu
     */
    public static final class AuthData {
        /** ユーザ名. */
        public String mUsername;
        /** パスワード. */
        public String mPassword;
    }

    /**
     * Bundleに認証情報を設定するユーティリティ関数.
     * 
     * @param bundle Bundle
     * @param authData 認証情報
     */
    public static final void setAuthDataToBundle(final Bundle bundle, final AuthData authData) {
        if (bundle != null && authData != null) {
            bundle.putString(KEY_USERNAME, authData.mUsername);
            bundle.putString(KEY_PASSWORD, authData.mPassword);
        }
    }

    @Override
    public final Loader<D> onCreateLoader(final int id, final Bundle bundle) {
        ExLog.net.d("httpclientloader create");

        final HttpClientLoader<D> loader = onCreateHttpClientLoader(id, bundle);

        if (bundle != null) {
            final String username = bundle.getString(KEY_USERNAME);
            final String password = bundle.getString(KEY_PASSWORD);

            if (username != null && password != null) {
                loader.setAuth(username, password);
            }
        }

        return loader;
    }

    @Override
    public final void onLoaderReset(final Loader<D> loader) {
        ExLog.net.d("httpclientloader reset");
    }

    @Override
    public void onLoadFinished(final Loader<D> loader, final D data) {
        final HttpClientLoader<D> httpClientLoader = (HttpClientLoader<D>) loader;
        if (httpClientLoader.getResponse() != null) {
            ExLog.net.d("httpclientloader finished: " + httpClientLoader.getResponse().getStatusLine());

            switch (httpClientLoader.getResponse().getStatusLine().getStatusCode()) {
                case HttpStatus.SC_UNAUTHORIZED:
                    HttpResponse response = httpClientLoader.getResponse();
                    String message = "";
                    final Header[] headers = response.getHeaders("WWW-Authenticate");
                    if (headers != null && headers.length > 0) {
                        message = headers[0].getValue();
                    }
                    onHttpClientAuthorizationRequired(httpClientLoader, message);
                    break;
                default:
                    onHttpClientLoadFinished(httpClientLoader, data);
                    break;
            }
        } else {
            ExLog.net.w("httpclientloader error finished: null");
            onHttpClientLoadError(httpClientLoader);
        }
    }

    protected abstract HttpClientLoader<D> onCreateHttpClientLoader(final int id, final Bundle bundle);

    protected abstract void onHttpClientLoaderReset(final HttpClientLoader<D> loader);

    protected abstract void onHttpClientLoadFinished(final HttpClientLoader<D> loader, final D data);

    protected abstract void onHttpClientAuthorizationRequired(final HttpClientLoader<D> loader, final String message);

    protected abstract void onHttpClientLoadError(final HttpClientLoader<D> loader);
}
