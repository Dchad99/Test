package com.ukrainer.infostroy;

import java.util.regex.Pattern;

public final class Source {
    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-z0-9]+\\@+[a-z]+\\.+[a-z]{2,5}");


    //User_register credentials
    public static final String USER_ALREADY_EXIST = "Email is already exist!";
    public static final String SUCCESS_REGISTRATION = "Successfully signed up";
    public static final String THERE_IS_NO_MATCHES_BETWEEN_LIST = "One or more subject don't match ...";
    public static final String DECLARATION_IS_EXIST = "This row is exist!";
    public static final String SUCCESSFULLY_REGISTERED = "Successfully registered";
    public static final String DECLARATION_LIMIT = "Declaration limit is 7 rows!";


    public static final String INCORRECT_EMAIL = "Email is not valid...";
    public static final String INCORRECT_PASS = "Password is not valid...";
    public static final String NULL = "Input cannot be null..";

}
