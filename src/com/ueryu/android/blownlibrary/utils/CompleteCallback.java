/**
 * Copyright (c) 2013 ueryu All Rights Reserved.
 */

package com.ueryu.android.blownlibrary.utils;

import android.os.Handler;

/**
 * @author ueryu
 */
public abstract class CompleteCallback<T> {

    private final Handler mHandler;

    public CompleteCallback() {
        this.mHandler = new Handler();
    }

    public final void complete(final T result) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                onCompleted(result);
            }
        });
    }

    public final void error(final int errorCode) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                notifyError(errorCode);
            }
        });
    }

    protected abstract void notifyError(final int errorCode);

    protected abstract void onCompleted(final T result);
}
