package com.think.linxuanxuan.sampleapplication.custompassword;

/**
 * Created by Think on 2015/8/16.
 */
public interface PasswordInterface {

//void setError(String error);

    String getPassWord();

    void clearPassword();

    void setPassword(String password);

    void setPasswordVisibility(boolean visible);

    void togglePasswordVisibility();

//    void setOnPasswordChangedListener(GridPasswordView.OnMyPasswordChangedListener listener);

    void setPasswordType(PasswordType passwordType);

}
