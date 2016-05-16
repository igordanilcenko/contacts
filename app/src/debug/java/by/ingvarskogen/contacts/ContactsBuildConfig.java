package by.ingvarskogen.contacts;

import okhttp3.logging.HttpLoggingInterceptor;

public class ContactsBuildConfig {

    final public static HttpLoggingInterceptor.Level RETROFIT_LOG_LEVEL = HttpLoggingInterceptor.Level.BASIC;
    final public static long CONNECTION_TIMEOUT_IN_MILLIS = 20_000;
}
