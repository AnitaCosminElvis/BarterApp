package com.example.barterapp.utility;

public final class AuthentificationUtility {


    /**
     * method is used for checking valid email id format.
     *
     * @param email
     * @return boolean true for valid false for invalid
     */
    public static boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    /**
     * method is used for checking password validity format.
     *
     * @param password
     * @return boolean true for valid false for invalid
     */
    public static boolean isPasswordValid(String password) {
        return ((password != null) && (password.trim().length() > 6));
    }
}
