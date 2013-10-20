/**
 * Copyright (c) 2013 ueryu All Rights Reserved.
 */

package com.ueryu.android.blownlibrary.internals.log;

import com.ueryu.android.blownlibrary.log.BlownLogUtil;

/**
 * @author ueryu
 */
public class ExLog extends BlownLogUtil {

    public static final BlownLog app = new ImplNormal("blownlib.app");
    public static final BlownLog net = new ImplNormal("blownlib.net");
}
