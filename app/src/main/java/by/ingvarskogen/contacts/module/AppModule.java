package by.ingvarskogen.contacts.module;

import android.app.Application;
import android.content.Context;

import by.ingvarskogen.contacts.di.ComponentManager;
import by.ingvarskogen.contacts.di.PerApplication;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides @PerApplication Application providesApplication() {
        return mApplication;
    }

    @Provides @PerApplication Context providesContext() {
        return mApplication;
    }

    @Provides @PerApplication ComponentManager providesComponentManager() {
        return new ComponentManager();
    }
}
