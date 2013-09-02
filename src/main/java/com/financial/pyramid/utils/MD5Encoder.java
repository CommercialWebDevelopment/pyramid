package com.financial.pyramid.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * User: dbudunov
 * Date: 01.09.13
 * Time: 19:21
 */
public class MD5Encoder {

    public static String encode(String str) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (md != null) {
            md.update(str.getBytes());
        }

        byte byteData[] = md != null ? md.digest() : new byte[0];

        //convert the byte to hex format method 1
        StringBuilder sb = new StringBuilder();
        for (byte aByteData : byteData) {
            sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
        }
        StringBuilder hexString = new StringBuilder();
        for (byte aByteData : byteData) {
            String hex = Integer.toHexString(0xff & aByteData);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
