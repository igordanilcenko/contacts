package by.ingvarskogen.contacts.presenter;

import java.util.ArrayList;

import javax.inject.Inject;

import by.ingvarskogen.contacts.di.PerFragment;
import by.ingvarskogen.contacts.model.ContactModel;
import by.ingvarskogen.contacts.entity.Contact;
import by.ingvarskogen.contacts.entity.Order;
import by.ingvarskogen.contacts.presenter.base.BaseLoadingMvpPresenter;
import by.ingvarskogen.contacts.view.ContactDetailMvpView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@PerFragment
public class ContactDetailMvpPresenter extends BaseLoadingMvpPresenter<ContactDetailMvpView> {

    @Inject ContactModel mContactModel;
    private ArrayList<Order> mOrderList;
    private Contact mContact;
    private ViewState mViewState = ViewState.INIT;

    @Inject public ContactDetailMvpPresenter() {
    }

    @Override public void attachView(ContactDetailMvpView mvpView) {
        super.attachView(mvpView);
        if (getViewState() == ViewState.INIT && mOrderList == null) {
            loadOrderList();
        } else {
            showViewState();
        }
    }

    public void onReloadClick() {
        loadOrderList();
    }

    private ViewState getViewState() {
        return mViewState;
    }

    private void setViewState(ViewState state) {
        mViewState = state;
        showViewState();
    }

    public void setContact(Contact contact) {
        this.mContact = contact;
    }

    private void loadOrderList() {
        setViewState(ViewState.LOADING);
        addSubscription(
                mContactModel.loadOrderList(mContact.getId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ArrayList<Order>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                setViewState(ViewState.ERROR);
                            }

                            @Override
                            public void onNext(ArrayList<Order> orders) {
                                mOrderList = orders;
                                setViewState(ViewState.NORMAL);
                            }
                        }));
    }

    private void showViewState() {
        if (isViewAttached()) {
            switch (mViewState) {
                case NORMAL:
                    if (mOrderList.isEmpty()) {
                        getMvpView().showEmpty();
                    } else {
                        getMvpView().showOrderList(mOrderList);
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
