package by.ingvarskogen.contacts.presenter;

import android.view.View;

import java.util.ArrayList;

import javax.inject.Inject;

import by.ingvarskogen.contacts.di.PerFragment;
import by.ingvarskogen.contacts.entity.Contact;
import by.ingvarskogen.contacts.model.ContactModel;
import by.ingvarskogen.contacts.presenter.base.BaseLoadingMvpPresenter;
import by.ingvarskogen.contacts.view.ContactListMvpView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@PerFragment
public class ContactListMvpPresenter extends BaseLoadingMvpPresenter<ContactListMvpView> {

    @Inject ContactModel mContactModel;

    private ArrayList<Contact> mContactList;
    private ViewState mViewState = ViewState.INIT;

    @Inject public ContactListMvpPresenter() {
    }

    @Override public void attachView(ContactListMvpView mvpView) {
        super.attachView(mvpView);
        if (getViewState() == ViewState.INIT && mContactList == null) {
            loadAndRefreshContacts();
        } else {
            showViewState();
        }
    }

    public void onContactClick(Contact contact, View contactView) {
        if (isViewAttached()) {
            getMvpView().startContactDetail(contact, contactView);
        }
    }

    public void onReloadClick() {
        loadAndRefreshContacts();
    }

    public void onSwipeToRefresh() {
        refreshContacts();
    }

    public void onActionAddClick() {
        if (isViewAttached()) {
            getMvpView().startCreateNewContact();
        }
    }

    public void refreshContactList() {
        refreshContacts();
    }

    private ViewState getViewState() {
        return mViewState;
    }

    private void setViewState(ViewState state) {
        mViewState = state;
        showViewState();
    }

    /**
     * Load data from API.
     */
    private void refreshContacts() {
        addSubscription(
                mContactModel.loadContactListFromApi()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ArrayList<Contact>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                getMvpView().showRefreshingError();
                            }

                            @Override
                            public void onNext(ArrayList<Contact> contacts) {
                                mContactList = contacts;
                                setViewState(ViewState.NORMAL);
                            }
                        }));
    }

    /**
     * Load data from cache OR api. If both cache and api return no data, show error.
     */
    private void loadAndRefreshContacts() {
        setViewState(ViewState.LOADING);
        addSubscription(
                mContactModel.loadContactList()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ArrayList<Contact>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                setViewState(ViewState.ERROR);
                            }

                            @Override
                            public void onNext(ArrayList<Contact> contacts) {
                                mContactList = contacts;
                                if (contacts == null) {
                                    setViewState(ViewState.ERROR);
                                } else {
                                    setViewState(ViewState.NORMAL);
                                }
                            }
                        }));
    }

    private void showViewState() {
        if (isViewAttached()) {
            switch (mViewState) {
                case NORMAL:
                    if (mContactList.isEmpty()) {
                        getMvpView().showEmpty();
                    } else {
                        getMvpView().showContactList(mContactList);
                    }
                    break;
                case LOADING:
                    getMvpView().showLoading();
                    break;
                case ERROR:
                    getMvpView().showError();
            }
        }
    }

    enum ViewState {INIT, NORMAL, LOADING, ERROR}
}
