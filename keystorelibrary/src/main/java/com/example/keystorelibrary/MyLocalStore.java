package com.example.keystorelibrary;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * 本地存储一段重要内容，例如token，该例子使用SecureRandom来模拟
 * 还要存储AES解密的向量
 */
public class MyLocalStore {

    private final String psw = "123456";
    public static final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCaC38bxZw9Ybsh5kk6Cb5rCkNG5CNoH5Oiu3DnmpTNOq89lvUBC6OijTA+MQ3X5PUZpwoC2fLB28zNXtg+dWRT777FK5KdKMFHVF2BQkVV+hMx3zvLSljzsk18DXiRbip1HSlHJs5UfNf/WZUYEDEdhmU+T2M8uXDov2b4rikLwIDAQAB";
    //作为静态变量，整个应用启动只用load一次，否则容易出错
    private static KeyStore keyStore;
    final String dataKeyName = "data";
    final String IVKeyName = "IV";
    private File file;
    private static boolean isLoaded = false;

    MyLocalStore(Context context) {
        try {
            if (keyStore == null) {
                synchronized (MyLocalStore.class) {
                    if (keyStore == null) {
                        keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                    }
                }
            }
            if (context != null) {
                file = new File(context.getFilesDir(), "file");
            }
            Log.d("tag", "初始化LocalKeyStore成功");
        } catch (KeyStoreException e) {
            Log.d("tag", "device don't support Android KeyStore");
            e.printStackTrace();
            return;
        }
    }

    KeyStore.SecretKeyEntry getSecretKeyEntry(String alias) {
        try {
            return (KeyStore.SecretKeyEntry) keyStore.getEntry(alias, new KeyStore.PasswordProtection(psw.toCharArray()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    boolean containsKey(String alias) {
        boolean isContained;
        try {
            loadKeyStore();
            isContained = keyStore.containsAlias(alias);
        } catch (Exception e) {
            e.printStackTrace();
            isContained = false;
        }
        Log.d("tag", "MyLocalStore" + (isContained ? "" : "不") + "包含" + alias);
        return isContained;
    }

    //第一次使用指纹，需要生成key，如果生成不成功，返回false。
    boolean storeData(String content, String keyName) {
        Log.d("tag", "LocalKeyStore存储" + keyName + ":" + content + "....");
        boolean isSucc = true;
        FileOutputStream out = null;
        try {
            //根据内容生成secret key
            PBEKeySpec keySpec = new PBEKeySpec(content.toCharArray());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWITHSHA1ANDDES");
            SecretKey key = keyFactory.generateSecret(keySpec);
            KeyStore.SecretKeyEntry entry = new KeyStore.SecretKeyEntry(key);
            //存储key
            keyStore.setEntry(keyName, entry, new KeyStore.PasswordProtection(psw.toCharArray()));
            out = new FileOutputStream(file);
            keyStore.store(out, psw.toCharArray());
            Log.d("tag", "LocalKeyStore存储" + content + "成功");
        } catch (Exception e) {
            e.printStackTrace();
            isSucc = false;
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (Exception e) {
                Log.d("tag", "LocalKeyStore存储关闭文件时出错.");
                e.printStackTrace();
            }
        }
        return isSucc;
    }

    //用公钥加密随机生成数
    String generateToken() {
        SecureRandom random;
        try {
            //生成随机数
            random = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
        random.setSeed((long) (Math.random() * 1234567));
        String result = new String(random.nextLong() + "");
        Log.d("tag", "generate key : " + result);
        return result;
    }

    synchronized void loadKeyStore() {
        //每次调用只需要load一次就好
        if (isLoaded) {
            return;
        }
        isLoaded = true;
        if (!file.exists()) {
            try {
                keyStore.load(null, null);
                file.createNewFile();
                Log.d("tag", "生成LocalKeyStore本地文件成功");
            } catch (Exception e) {
                Log.d("tag", "生成LocalKeyStore本地文件失败");
                e.printStackTrace();
            }
            return;
        }
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            keyStore.load(in, psw.toCharArray());
            Log.d("tag", "导入LocalKeyStore本地文件成功");
        } catch (Exception e) {
            Log.d("tag", "导入LocalKeyStore本地文件失败");
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void deleteInvalidToken() {
        if (!file.exists()) {
            return;
        }
        try {
            loadKeyStore();
            keyStore.deleteEntry(getKey(dataKeyName));
            keyStore.deleteEntry(getKey(IVKeyName));
            Log.d("tag", "删除LocalKeyStore本地数据成功");
        } catch (Exception e) {
            Log.d("tag", "删除LocalKeyStore本地数据失败");
            e.printStackTrace();
        }
    }

    String getKey(String content) {
        return "myaccount_" + content;
    }
}
