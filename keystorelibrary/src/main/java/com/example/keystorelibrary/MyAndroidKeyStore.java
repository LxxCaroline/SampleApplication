package com.example.keystorelibrary;

import android.annotation.TargetApi;
import android.hardware.fingerprint.FingerprintManager;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Log;

import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

@TargetApi(23)
public class MyAndroidKeyStore {

    private KeyStore mStore;

    MyAndroidKeyStore() {
        try {
            mStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void generateKey() {
        Log.d("tag", "LocalAndroidKeyStore生成加密密钥...");
        //这里使用AES + CBC + PADDING_PKCS7，并且需要用户验证方能取出
        try {
            final KeyGenerator generator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            mStore.load(null);
            final int purpose = KeyProperties.PURPOSE_DECRYPT | KeyProperties.PURPOSE_ENCRYPT;
            final KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder("myaccount", purpose);
            builder.setUserAuthenticationRequired(true);
            builder.setBlockModes(KeyProperties.BLOCK_MODE_CBC);
            builder.setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7);
            generator.init(builder.build());
            generator.generateKey();
            Log.d("tag", "LocalAndroidKeyStore生成加密密钥成功");
        } catch (Exception e) {
            Log.d("tag", "LocalAndroidKeyStore生成加密密钥失败");
            e.printStackTrace();
        }
    }

    FingerprintManager.CryptoObject getCryptoObject(int purpose, byte[] IV) {
        Log.d("tag", "LocalAndroidKeyStore生成CryptoObject");
        try {
            mStore.load(null);
            final SecretKey key = (SecretKey) mStore.getKey("myaccount", null);
            if (key == null) {
                Log.d("tag", "LocalAndroidKeyStore生成CryptoObject为空，无key");
                return null;
            }
            final Cipher cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC
                    + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
            if (purpose == KeyProperties.PURPOSE_ENCRYPT) {
                cipher.init(purpose, key);
            } else {
                cipher.init(purpose, key, new IvParameterSpec(IV));
            }
            Log.d("tag", "LocalAndroidKeyStore生成CryptoObject成功");
            return new FingerprintManager.CryptoObject(cipher);
        } catch (Exception e) {
            Log.d("tag", "LocalAndroidKeyStore生成CryptoObject失败");
            e.printStackTrace();
            return null;
        }
    }
}
