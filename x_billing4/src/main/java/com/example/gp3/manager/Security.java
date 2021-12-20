package com.example.gp3.manager;

import android.text.TextUtils;
import android.util.Base64;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created on 2019-12-04.
 */
public class Security {
    public static final String TAG = "Security";
    private String KEY_FACTORY_ALGORITHM = "RSA";
    private String SIGNATURE_ALGORITHM = "SHA1withRSA";
    public static  String BASE_64_ENCODED_PUBLIC_KEY = "";

    private Security() {

    }

    private static class SecurityHolder {
        private static Security sInstance = new Security();
    }

    public static Security getInstance() {
        return SecurityHolder.sInstance;
    }

    public boolean verifyPurchase(String base64PublicKey, String signedData, String signature) {
        if ((TextUtils.isEmpty(signedData) || TextUtils.isEmpty(base64PublicKey)
                || TextUtils.isEmpty(signature))) {
            return false;
        }

        PublicKey key = generatePublicKey(base64PublicKey);
        return verify(key, signedData, signature);
    }

    private PublicKey generatePublicKey(String encodedPublicKey) {
        try {
            byte[] decodeKey = Base64.decode(encodedPublicKey, Base64.DEFAULT);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_FACTORY_ALGORITHM);
            return keyFactory.generatePublic(new X509EncodedKeySpec(decodeKey));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }


    private boolean verify(PublicKey publicKey, String signedData, String signature) {
        byte[] signatureBytes;
        try {
            signatureBytes = Base64.decode(signature, Base64.DEFAULT);
        } catch (IllegalArgumentException e) {
            return false;
        }

        try {
            Signature signatureAlgorithm = Signature.getInstance(SIGNATURE_ALGORITHM);
            signatureAlgorithm.initVerify(publicKey);
            signatureAlgorithm.update(signedData.getBytes());
            if (!signatureAlgorithm.verify(signatureBytes)) {
                return false;
            }
            return true;
        } catch (NoSuchAlgorithmException e) {
            // "RSA" is guaranteed to be available.
        } catch (InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }
        return false;
    }
}
