package com.backend.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;

public class RandomStringUtil {
    public static String randomString(int numberString){
        char[] possibleCharacters = (new String("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789")).toCharArray();
        String str = RandomStringUtils.random( numberString, 0, possibleCharacters.length-1, false, false, possibleCharacters, new SecureRandom() );
        return str;
    }
}
