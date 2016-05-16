package by.ingvarskogen.contacts.presenter.base;

import by.ingvarskogen.contacts.view.base.MvpView;

public interface MvpPresenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();
}
