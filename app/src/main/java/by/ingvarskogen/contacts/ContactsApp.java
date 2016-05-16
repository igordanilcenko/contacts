package by.ingvarskogen.contacts;

import android.app.Application;

import javax.inject.Inject;

import by.ingvarskogen.contacts.di.ComponentManager;
import by.ingvarskogen.contacts.di.component.AppComponent;
import by.ingvarskogen.contacts.di.component.DaggerAppComponent;
import by.ingvarskogen.contacts.module.ApiModule;
import by.ingvarskogen.contacts.module.AppModule;
import io.paperdb.Paper;

public class ContactsApp extends Application {

    private static AppComponent sAppComponent;

    @Inject ComponentManager mComponentManager;

    @Override public void onCreate() {
        super.onCreate();
        initComponent();
        Paper.init(this);
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    private void initComponent() {
        sAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .apiModule(new ApiModule(ContactsApiConfig.ENDPOINT))
                .build();
    }
}
