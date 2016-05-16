package by.ingvarskogen.contacts;

import okhttp3.logging.HttpLoggingInterceptor;

public class ContactsBuildConfig {

    final public static String ENDPOINT = "https://inloop-contacts.appspot.com/_ah/api/";
    final public static String CONTACT_PICTURE_URL_PREFIX = "https://inloop-contacts.appspot.com/";

    final public static HttpLoggingInterceptor.Level RETROFIT_LOG_LEVEL = HttpLoggingInterceptor.Level.NONE;
    final public static long CONNECTION_TIMEOUT_IN_MILLIS = 10_000;
}
