package by.ingvarskogen.contacts.di.component;

import by.ingvarskogen.contacts.di.PerFragment;
import by.ingvarskogen.contacts.fragment.ContactDetailFragment;
import by.ingvarskogen.contacts.fragment.ContactListFragment;
import by.ingvarskogen.contacts.fragment.CreateNewContactFragment;
import dagger.Component;

/**
 * This component inject dependencies to all Fragments across the application
 */
@PerFragment
@Component(dependencies = AppComponent.class)
public interface FragmentComponent {

    void inject(ContactListFragment fragment);

    void inject(ContactDetailFragment fragment);

    void inject(CreateNewContactFragment fragment);

}
