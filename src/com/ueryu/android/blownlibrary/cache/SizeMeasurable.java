/**
 * Copyright (c) 2013 ueryu All Rights Reserved.
 */

package com.ueryu.android.blownlibrary.cache;

/**
 * インスタンスのサイズを量ることができるインターフェイス.
 * 
 * @author ueryu
 */
public interface SizeMeasurable {
    /**
     * インスタンスのサイズを取得する.
     * 
     * @return インスタンスのサイズ
     */
    public int getSize();
}
