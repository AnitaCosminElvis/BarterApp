package com.example.barterapp.utility;

import com.example.barterapp.data.EPassState;

import java.util.regex.Pattern;

/**
 * The Authentification utility.
 */
public final class AuthentificationUtility {

    private static final Pattern LOWER_CASE = Pattern.compile("[a-z]");
    private static final Pattern UPPER_CASE = Pattern.compile("[A-Z]");
    private static final Pattern DECIMAL_DIGIT = Pattern.compile("[0-9]");
    private static final Pattern SPECIAL_CHARS = Pattern.compile("[^A-Za-z0-9 ]");

    /**
     * method is used for checking valid email id format.
     *
     * @param email the email
     * @return boolean true for valid false for invalid
     */
    public static boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    /**
     * method is used for checking password validity format.
     *
     * @param password the password
     * @return boolean true for valid false for invalid
     */
    public static EPassState isPasswordValid(String password) {
        if (password.length() < 8) return EPassState.SHORT;
        if (!UPPER_CASE.matcher(password).find() ) return EPassState.NO_UPPER;
        if (!LOWER_CASE.matcher(password).find()) return EPassState.NO_LOWER;
        if (!DECIMAL_DIGIT.matcher(password).find()) return EPassState.NO_DIGIT;
        if (!SPECIAL_CHARS.matcher(password).find()) return EPassState.NO_SPECIAL_CHARS;
        return EPassState.VALID;
    }
}
