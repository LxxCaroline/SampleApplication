package com.example.keystorelibrary;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.security.keystore.KeyProperties;
import android.util.Base64;
import android.widget.Toast;

import java.security.KeyStore;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

@TargetApi(23)
public class KeyStoreHelper extends FingerprintManager.AuthenticationCallback {

    private FingerprintManager manager;
    private CancellationSignal mCancellationSignal;
    private SimpleAuthenticationCallback callback;
    private MyLocalStore mLocalKeyStore;
    private MyAndroidKeyStore mLocalAndroidKeyStore;
    //PURPOSE_ENCRYPT,则表示生成token，否则为取出token
    private int purpose = KeyProperties.PURPOSE_ENCRYPT;
    private String data;
    private int authTimes = 0;       //计数验证次数

    public KeyStoreHelper(Context context) {
        manager = context.getSystemService(FingerprintManager.class);
        mLocalKeyStore = new MyLocalStore(context);
        mLocalAndroidKeyStore = new MyAndroidKeyStore();
    }

    public void generateToken() {
        //生成随机数
        data = mLocalKeyStore.generateToken();
        //在keystore中生成加密密钥
        mLocalAndroidKeyStore.generateKey();
        setPurpose(KeyProperties.PURPOSE_ENCRYPT);
    }

    public boolean checkFingerprintAvailable(Context ctx) {
        if (!manager.isHardwareDetected()) {
            Toast.makeText(ctx, "该设备尚未检测到指纹硬件",Toast.LENGTH_SHORT).show();
            return false;
        } else if (!manager.hasEnrolledFingerprints()) {
            Toast.makeText(ctx, "该设备未录入指纹，请去系统->设置中添加指纹",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean containsToken() {
        return mLocalKeyStore.containsKey(mLocalKeyStore.getKey(mLocalKeyStore.dataKeyName));
    }

    public void setCallback(SimpleAuthenticationCallback callback) {
        this.callback = callback;
    }

    public void setPurpose(int purpose) {
        this.purpose = purpose;
    }

    public boolean authenticate() {
        try {
            authTimes = 0;
            FingerprintManager.CryptoObject object = null;
            if (purpose == KeyProperties.PURPOSE_DECRYPT) {
                //取出secret key
                KeyStore.SecretKeyEntry entry = mLocalKeyStore.getSecretKeyEntry(mLocalKeyStore.getKey(mLocalKeyStore.IVKeyName));
                if (entry != null) {
                    String temp = new String(entry.getSecretKey().getEncoded());
                    object = mLocalAndroidKeyStore.getCryptoObject(Cipher.DECRYPT_MODE, Base64.decode(temp, Base64.URL_SAFE));
                }
                if (object == null) {
                    return false;
                }
            } else {
                object = mLocalAndroidKeyStore.getCryptoObject(Cipher.ENCRYPT_MODE, null);
            }
            mCancellationSignal = new CancellationSignal();
            manager.authenticate(object, mCancellationSignal, 0, this, null);
            return true;
        } catch (SecurityException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void stopAuthenticate() {
        if (mCancellationSignal != null) {
            mCancellationSignal.cancel();
            mCancellationSignal = null;
        }
        callback = null;
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        if (callback == null) {
            return;
        }
        if (result.getCryptoObject() == null) {
            callback.onAuthenticationFail(true);
            return;
        }
        final Cipher cipher = result.getCryptoObject().getCipher();
        if (purpose == KeyProperties.PURPOSE_DECRYPT) {
            //取出secret key并返回
            KeyStore.SecretKeyEntry entry = mLocalKeyStore.getSecretKeyEntry(mLocalKeyStore.getKey(mLocalKeyStore.dataKeyName));
            if (entry == null) {
                callback.onAuthenticationFail(true);
                return;
            }
            try {
                String temp = new String(entry.getSecretKey().getEncoded());
                byte[] decrypted = cipher.doFinal(Base64.decode(temp, Base64.URL_SAFE));
                callback.onAuthenticationSucceeded(new String(decrypted));
            } catch (BadPaddingException | IllegalBlockSizeException e) {
                e.printStackTrace();
                callback.onAuthenticationFail(true);
            }
        } else {
            //将前面生成的data包装成secret key，存入沙盒
            try {
                byte[] encrypted = cipher.doFinal(data.getBytes());
                byte[] IV = cipher.getIV();
                String se = Base64.encodeToString(encrypted, Base64.URL_SAFE);
                String siv = Base64.encodeToString(IV, Base64.URL_SAFE);
                mLocalKeyStore.loadKeyStore();
                if (mLocalKeyStore.storeData(se, mLocalKeyStore.getKey(mLocalKeyStore.dataKeyName)) &&
                        mLocalKeyStore.storeData(siv, mLocalKeyStore.getKey(mLocalKeyStore.IVKeyName))) {
                    callback.onAuthenticationSucceeded(data);
                } else {
                    callback.onAuthenticationFail(true);
                }
            } catch (BadPaddingException | IllegalBlockSizeException e) {
                e.printStackTrace();
                callback.onAuthenticationFail(true);
            }
        }
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        if (callback != null) {
            callback.onAuthenticationFail(true);
        }
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        ++authTimes;
        if (callback != null) {
            callback.onAuthenticationFail(authTimes >= 3 ? true : false);
        }
    }

    @Override
    public void onAuthenticationFailed() {
        ++authTimes;
        if (callback != null) {
            callback.onAuthenticationFail(authTimes >= 3 ? true : false);
        }
    }

    public interface SimpleAuthenticationCallback {
        void onAuthenticationSucceeded(String value);

        void onAuthenticationFail(boolean isLocked);
    }
}
