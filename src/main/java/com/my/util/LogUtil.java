package com.my.util;

/**
 * Created with IntelliJ IDEA.
 * User: Vlad
 * Date: 01.03.14
 * Time: 19:56
 * To change this template use File | Settings | File Templates.
 */
public class LogUtil {

    public static String getCurrentClass() {
        try {
            throw new RuntimeException();
        } catch (Exception e) {
            return e.getStackTrace()[1].getClassName();
        }
    }

}
