package com.joshualorett.networksample.network;

/**
 * Take in status code, spit out readable message.
 */

public class StatusCodeTranslator {
    public static String getMessage(int statusCode) {
        switch (statusCode) {
            case 200:
                return "Success";
            case 400:
                return "Bad Request";
            case 404:
                return "Not Found";
            case 500:
                return "Internal Server Error";
            case 406:
                return "Not Acceptable";
            case 401:
                return "Unauthorized";
            default:
                return "Undefined Error";
        }
    }
}
