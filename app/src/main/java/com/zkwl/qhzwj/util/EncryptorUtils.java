package com.zkwl.qhzwj.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptorUtils {

    public final static String KEYAES = "DJTggiIeOBu3blSX";
    public final static String IVAES = "2oFtRtKzfnkxLB18";
    public static String encrypt(String key, String initVector, String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(value.getBytes());
//            System.out.println("encrypted string: " + Base64.getEncoder().encodeToString(encrypted));
//            return Base64.getEncoder().encodeToString(encrypted);
            return Base64Encoder.encode(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public static String zgPwd(String pwd) {
        return encrypt(KEYAES, IVAES, pwd);
    }
}