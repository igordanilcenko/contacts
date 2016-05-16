package by.ingvarskogen.contacts.view;

import by.ingvarskogen.contacts.view.base.MvpView;

public interface CreateNewContactMvpView extends MvpView {

    void showNormal();

    void showLoading();

    boolean validateFields();

    void addValidatingTextWatchers();

    void onContactCreated();

    void showCreatingFailedError();
}
