/**
 * Copyright (c) 2013 ueryu All Rights Reserved.
 */

package com.ueryu.android.blownlibrary.log;

import android.util.Log;

/**
 * @author ueryu
 */
public abstract class BlownLogUtil {

    public static abstract class BlownLog {

        /**
         * ログ出力. Lv:Debug
         * 
         * @param tag タグ名.
         * @param msg ログ出力メッセージ.
         * @return 不明(android.util.Logの戻り値).
         */

        public abstract int d(String msg);

        /**
         * ログ出力. Lv:Debug
         * 
         * @param tag タグ名.
         * @param msg ログ出力メッセージ.
         * @param th コールスタック出力するThrowable.
         * @return 不明(android.util.Logの戻り値).
         */

        public abstract int d(String msg, Throwable th);

        /* ----------------------------------------------------- */
        // ログ関数定義.
        /**
         * ログ出力. Lv:Error
         * 
         * @param tag タグ名
         * @param msg ログ出力メッセージ
         * @return 不明(android.util.Logの戻り値).
         */
        public abstract int e(String msg);

        /**
         * ログ出力. Lv:Error
         * 
         * @param tag タグ名.
         * @param msg ログ出力メッセージ.
         * @param th コールスタック出力するThrowable.
         * @return 不明(android.util.Logの戻り値).
         */
        public abstract int e(String msg, Throwable th);

        /**
         * ログ出力. Lv:Info
         * 
         * @param tag タグ名.
         * @param msg ログ出力メッセージ.
         * @return 不明(android.util.Logの戻り値).
         */

        public abstract int i(String msg);

        /**
         * ログ出力. Lv:Info
         * 
         * @param tag タグ名.
         * @param msg ログ出力メッセージ.
         * @param th コールスタック出力するThrowable.
         * @return 不明(android.util.Logの戻り値).
         */

        public abstract int i(String msg, Throwable th);

        /**
         * ログ出力. Lv:Verbose
         * 
         * @param tag タグ名.
         * @param msg ログ出力メッセージ.
         * @return 不明(android.util.Logの戻り値).
         */

        public abstract int v(String msg);

        /**
         * ログ出力. Lv:Verbose
         * 
         * @param tag タグ名.
         * @param msg ログ出力メッセージ.
         * @param th コールスタック出力するThrowable.
         * @return 不明(android.util.Logの戻り値).
         */

        public abstract int v(String msg, Throwable th);

        /**
         * ログ出力. Lv:Warning
         * 
         * @param tag タグ名.
         * @param msg ログ出力メッセージ.
         * @return 不明(android.util.Logの戻り値).
         */

        public abstract int w(String msg);

        /**
         * ログ出力. Lv:Warning
         * 
         * @param tag タグ名.
         * @param msg ログ出力メッセージ.
         * @param th コールスタック出力するThrowable.
         * @return 不明(android.util.Logの戻り値).
         */

        public abstract int w(String msg, Throwable th);
    }

    /* ----------------------------------------------------- */
    /**
     * 実ログ出力処理.
     * 
     * @author ueryu
     */
    protected static class ImplAlways extends BlownLog {

        /** tag/ */
        private final String mTag;

        /**
         * コンストラクタ.
         * 
         * @param tag tag
         */
        public ImplAlways(final String tag) {
            this.mTag = tag;
        }

        @Override
        public int d(final String msg) {
            return Log.d(mTag, msg);
        }

        @Override
        public int d(final String msg, final Throwable th) {
            return Log.d(mTag, msg, th);
        }

        @Override
        public int e(final String msg) {
            return Log.e(mTag, msg);
        }

        @Override
        public int e(final String msg, final Throwable th) {
            return Log.e(mTag, msg, th);
        }

        @Override
        public int i(final String msg) {
            return Log.i(mTag, msg);
        }

        @Override
        public int i(final String msg, final Throwable th) {
            return Log.i(mTag, msg, th);
        }

        @Override
        public int v(final String msg) {
            return Log.v(mTag, msg);
        }

        @Override
        public int v(final String msg, final Throwable th) {
            return Log.v(mTag, msg, th);
        }

        @Override
        public int w(final String msg) {
            return Log.w(mTag, msg);
        }

        @Override
        public int w(final String msg, final Throwable th) {
            return Log.w(mTag, msg, th);
        }
    }

    /**
     * ログ出力しない処理クラス. ただし、ErrorとWarningは出力する.
     * 
     * @author ueryu
     */
    protected static class ImplNo extends BlownLog {

        /**
         * コンストラクタ.
         * 
         * @param tag tag
         */
        public ImplNo(final String tag) {
        }

        @Override
        public int d(final String msg) {
            return 0;
        }

        @Override
        public int d(final String msg, final Throwable th) {
            return 0;
        }

        @Override
        public int e(final String msg) {
            return 0;
        }

        @Override
        public int e(final String msg, final Throwable th) {
            return 0;
        }

        @Override
        public int i(final String msg) {
            return 0;
        }

        @Override
        public int i(final String msg, final Throwable th) {
            return 0;
        }

        @Override
        public int v(final String msg) {
            return 0;
        }

        @Override
        public int v(final String msg, final Throwable th) {
            return 0;
        }

        @Override
        public int w(final String msg) {
            return 0;
        }

        @Override
        public int w(final String msg, final Throwable th) {
            return 0;
        }
    }

    /**
     * ログ出力しない処理クラス. ただし、ErrorとWarningは出力する.
     * 
     * @author ueryu
     */
    protected static class ImplNormal extends BlownLog {

        /** tag/ */
        private final String mTag;

        /**
         * コンストラクタ.
         * 
         * @param tag tag
         */
        public ImplNormal(final String tag) {
            this.mTag = tag;
        }

        @Override
        public int d(final String msg) {
            return 0;
        }

        @Override
        public int d(final String msg, final Throwable th) {
            return 0;
        }

        @Override
        public int e(final String msg) {
            return Log.e(mTag, msg);
        }

        @Override
        public int e(final String msg, final Throwable th) {
            return Log.e(mTag, msg, th);
        }

        @Override
        public int i(final String msg) {
            return Log.i(mTag, msg);
        }

        @Override
        public int i(final String msg, final Throwable th) {
            return Log.i(mTag, msg, th);
        }

        @Override
        public int v(final String msg) {
            return 0;
        }

        @Override
        public int v(final String msg, final Throwable th) {
            return 0;
        }

        @Override
        public int w(final String msg) {
            return Log.w(mTag, msg);
        }

        @Override
        public int w(final String msg, final Throwable th) {
            return Log.w(mTag, msg, th);
        }
    }
}
