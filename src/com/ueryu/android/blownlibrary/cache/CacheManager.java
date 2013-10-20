/**
 * Copyright (c) 2013 ueryu All Rights Reserved.
 */

package com.ueryu.android.blownlibrary.cache;

import android.support.v4.util.LruCache;

import com.ueryu.android.blownlibrary.R;
import com.ueryu.android.blownlibrary.utils.CompleteCallback;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * キャッシュ管理クラス.
 * 
 * @author ueryu
 */
public class CacheManager<K, V extends SizeMeasurable> {

    public static final int NO_CACHE = R.id.no_cache;

    private final LruCache<K, V> mCache;

    private final Map<K, List<WeakReference<CompleteCallback<V>>>> mCallbacks = new HashMap<K, List<WeakReference<CompleteCallback<V>>>>();

    /**
     * コンストラクタ.
     * 
     * @param res リソース
     */
    protected CacheManager(final int size) {
        this.mCache = new LruCache<K, V>(size) {
            @Override
            protected int sizeOf(K key, V value) {
                return value.getSize();
            }
        };
    }

    /**
     * キャッシュ取得.
     * 
     * @param path パス
     * @return キャッシュデータ
     */
    public final synchronized void get(final K path,
            final CompleteCallback<V> callback) {
        final V imageFile = mCache.get(path);
        if (imageFile != null) {
            // キャッシュヒット.
            callback.complete(imageFile);
        } else {
            // キャッシュに無い.
            callback.error(NO_CACHE);
        }

        // 変更通知を受ける為のコールバック登録.
        List<WeakReference<CompleteCallback<V>>> weakCallbackList = mCallbacks.get(path);
        if (weakCallbackList == null) {
            weakCallbackList = new LinkedList<WeakReference<CompleteCallback<V>>>();
            mCallbacks.put(path, weakCallbackList);
        }
        weakCallbackList.add(new WeakReference<CompleteCallback<V>>(callback));
    }

    /**
     * キャッシュ.
     * 
     * @param data データ
     */
    public final synchronized void put(final K key, final V data) {
        if (key == null || data == null) {
            return;
        }

        mCache.put(key, data);

        // 変化通知を通知する.
        final List<WeakReference<CompleteCallback<V>>> weakCallbackList = mCallbacks.get(key);
        if (weakCallbackList != null) {
            final ListIterator<WeakReference<CompleteCallback<V>>> it = weakCallbackList.listIterator();
            while (it.hasNext()) {
                WeakReference<CompleteCallback<V>> weakCallback = it.next();
                final CompleteCallback<V> callback = weakCallback.get();
                if (callback != null) {
                    callback.complete(data);
                } else {
                    // もうなくなってるなら、削除しておく.
                    it.remove();
                }
            }
            if (weakCallbackList.size() == 0) {
                // もうなくなってるなら、削除しておく.
                mCallbacks.remove(key);
            }
        }
    }
}
