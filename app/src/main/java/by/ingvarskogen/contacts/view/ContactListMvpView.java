package by.ingvarskogen.contacts.view;

import android.view.View;

import java.util.ArrayList;

import by.ingvarskogen.contacts.entity.Contact;
import by.ingvarskogen.contacts.view.base.MvpView;

public interface ContactListMvpView extends MvpView {

    void showContactList(ArrayList<Contact> contactList);

    void showLoading();

    void showEmpty();

    void showError();

    void startCreateNewContact();

    void startContactDetail(Contact contact, View sharedView);

    void showRefreshingError();
}
