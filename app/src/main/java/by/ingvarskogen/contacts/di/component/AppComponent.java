package by.ingvarskogen.contacts.di.component;

import android.content.Context;

import by.ingvarskogen.contacts.di.ComponentManager;
import by.ingvarskogen.contacts.di.PerApplication;
import by.ingvarskogen.contacts.model.ContactModel;
import by.ingvarskogen.contacts.module.ApiModule;
import by.ingvarskogen.contacts.module.AppModule;
import dagger.Component;

@PerApplication
@Component(modules = {AppModule.class, ApiModule.class})
public interface AppComponent {

    ContactModel contactModel();

    Context context();

    ComponentManager componentManager();

}
