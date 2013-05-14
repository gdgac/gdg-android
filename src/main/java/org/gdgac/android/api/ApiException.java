package org.gdgac.android.api;

/**
 * GDG Aachen
 * org.gdgac.android.api
 * <p/>
 * User: maui
 * Date: 21.04.13
 * Time: 22:24
 */
public class ApiException extends Exception {
    public ApiException(Throwable e) {
        super(e);
    }

    public ApiException(String msg) {
        super(msg);
    }

    public ApiException(String msg, int code) {
        super(code+" "+msg);
    }
}
