/**
 * Copyright (c) 2013 ueryu All Rights Reserved.
 */

package com.ueryu.android.blownlibrary.net;

import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Base64;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.BufferedHttpEntity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author ueryu
 */
public abstract class HttpClientLoader<T> extends AsyncTaskLoader<T> {

    /** Uri */
    private final String mAddress;

    /** BASIC認証する値. */
    private String mBasicAuthData;

    /** POSTする値. */
    private List<NameValuePair> mPostValue = null;

    /** HttpResponse. */
    private HttpResponse mHttpResponse = null;

    /** HttpResponseにおけるBufferedHttpEntity. */
    private BufferedHttpEntity mBufferedHttpEntity = null;

    /**
     * コンストラクタ.
     * 
     * @param context コンテキスト
     * @param address アドレス
     */
    public HttpClientLoader(final Context context, final String address) {
        super(context);
        this.mAddress = address;
    }

    /**
     * 受信時のHttpEntityを返す.受信完了まではnullを返す.
     * 
     * @return HttpEntity.
     */
    public final HttpEntity getHttpEntity() {
        return mBufferedHttpEntity;
    }

    /**
     * 受信時のHttpResponseを返す.受信完了まではnullを返す.
     * 
     * @return HttpResponse.
     */
    public final HttpResponse getResponse() {
        return mHttpResponse;
    }

    /*
     * (non-Javadoc)
     * @see android.support.v4.content.AsyncTaskLoader#loadInBackground()
     */
    @Override
    public final T loadInBackground() {
        final AndroidHttpClient httpclient = AndroidHttpClient.newInstance("android useragent");
        // final DefaultHttpClient httpclient = new DefaultHttpClient();

        final HttpUriRequest request;
        if (mPostValue != null) {
            final HttpPost post = new HttpPost(mAddress);
            try {
                post.setEntity(new UrlEncodedFormEntity(mPostValue));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            request = post;
        } else {
            request = new HttpGet(mAddress);
        }

        if (mBasicAuthData != null) {
            final String authBody = "Basic " + Base64.encodeToString(mBasicAuthData.getBytes(), Base64.NO_WRAP) + "=";
            request.addHeader("Authorization", authBody);
        }

        final ResponseHandler<HttpEntity> responseHandler = new ResponseHandler<HttpEntity>() {
            @Override
            public HttpEntity handleResponse(final HttpResponse response)
                    throws ClientProtocolException, IOException {

                mHttpResponse = response;
                mBufferedHttpEntity = new BufferedHttpEntity(response.getEntity());
                return mBufferedHttpEntity;
            }
        };

        HttpEntity entity = null;
        try {
            entity = httpclient.execute(request, responseHandler);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        T res = null;
        if (entity != null) {
            res = onHttpResponseInBackground(entity);
        }

        // クローズ.
        httpclient.close();
        return res;
    }

    /**
     * BASIC認証する値を設定する.
     * 
     * @param username ユーザ名
     * @param password パスワード
     */
    public final void setAuth(final String username, final String password) {
        this.mBasicAuthData = username + ":" + password;
    }

    /**
     * POSTする値を設定する.
     * 
     * @param postValue POSTする値
     */
    public final void setPostValue(final List<NameValuePair> postValue) {
        this.mPostValue = postValue;
    }

    /**
     * アクセスするアドレス.
     * 
     * @return アドレス
     */
    protected final String getAddress() {
        return mAddress;
    }

    protected abstract T onHttpResponseInBackground(final HttpEntity entity);

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
