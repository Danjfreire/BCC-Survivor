package com.ufrpe.bccsurvivor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Dan on 25/01/2018.
 */

public class Encrypter {

    private static Encrypter instance;

    private Encrypter(){

    }

    public static Encrypter getInstance(){
        if(instance == null){
            instance = new Encrypter();
        }

        return instance;
    }

    public String md5(String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";

    }

}
