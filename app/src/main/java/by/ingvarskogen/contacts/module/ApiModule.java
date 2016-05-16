package by.ingvarskogen.contacts.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import by.ingvarskogen.contacts.ContactsBuildConfig;
import by.ingvarskogen.contacts.di.PerApplication;
import by.ingvarskogen.contacts.model.RestInterface;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    private String mBaseUrl;

    public ApiModule(String baseUrl) {
        this.mBaseUrl = baseUrl;
    }

    @Provides @PerApplication OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(ContactsBuildConfig.RETROFIT_LOG_LEVEL);
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(ContactsBuildConfig.CONNECTION_TIMEOUT_IN_MILLIS, TimeUnit.MILLISECONDS)
                .build();
    }

    @Provides @PerApplication Gson provideGson() {
        return new GsonBuilder()
                .create();
    }

    @Provides @PerApplication Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(mBaseUrl)
                .client(okHttpClient)
                .build();
    }

    @Provides @PerApplication RestInterface provideRestInterface(Retrofit retrofit) {
        return retrofit.create(RestInterface.class);
    }

}