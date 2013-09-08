package com.financial.pyramid.utils;

import java.util.Random;

/**
 * User: dbudunov
 * Date: 08.09.13
 * Time: 17:54
 */
public class Password {
    private static final String CHARSET = "0123456789abcdefghijklmnopqrstuvwxyz";
    private static final short PASSWORD_LENGTH = 10;

    public static synchronized String generate() {
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= PASSWORD_LENGTH; i++) {
            int pos = rand.nextInt(CHARSET.length());
            sb.append(CHARSET.charAt(pos));
        }
        return sb.toString();
    }

}
