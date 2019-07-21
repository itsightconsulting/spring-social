package com.example.springsocial.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

public class Utilitarios {

    private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static SecureRandom rnd = new SecureRandom();

    public static String generarRandomPassword(int len){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }

    public static String parseFormalUUID(String noParsed) {
        noParsed = noParsed.substring(0, 8).toUpperCase() + "-" +
                noParsed.substring(8, 12).toUpperCase() + "-" +
                noParsed.substring(12, 16).toUpperCase() + "-" +
                noParsed.substring(16, 20).toUpperCase() + "-" +
                noParsed.substring(20).toUpperCase();
        return noParsed;
    }

    public static boolean validateOnlyNumbers(String cadena) {
        char[] array = cadena.toCharArray();
        int i = 0;

        for (i = 0; i < array.length; i++) {
            if (!Character.isDigit(array[i])) {
                i--;
                break;
            }
        }

        if (i == array.length) {
            return true;
        } else {
            return false;
        }
    }

    public static final String encoderPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    public static boolean objExists(Object object){
        Optional<Object> cxtObject = Optional.ofNullable(object);
        if(cxtObject.isPresent())
            return true;
        return false;
    }

    public static Optional<Integer> parseInt(String toParse) {
        try {
            return Optional.of(Integer.parseInt(toParse));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public static boolean onlyIntegers(String value){
        try {
            if(value.split(",").length == 7)
                Arrays.stream(value.split(",")).forEach(x-> Integer.parseInt(x));
            else
                return false;
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isInteger(String value){
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String jsonResponse(String r) {
        return "{\"res\":\""+ r +"\"}";
    }

    public static String jsonResponse(String r, String uuid) {
        return "{\"res\":\""+ r +"\",\"rdm\":\""+ uuid +"\"}";
    }

    public static String getRandomString(int length){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = length;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }
}

