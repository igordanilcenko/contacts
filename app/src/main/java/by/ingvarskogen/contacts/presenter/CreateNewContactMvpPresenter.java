package by.ingvarskogen.contacts.presenter;

import javax.inject.Inject;

import by.ingvarskogen.contacts.di.PerFragment;
import by.ingvarskogen.contacts.entity.Contact;
import by.ingvarskogen.contacts.model.ContactModel;
import by.ingvarskogen.contacts.presenter.base.BaseLoadingMvpPresenter;
import by.ingvarskogen.contacts.view.CreateNewContactMvpView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@PerFragment
public class CreateNewContactMvpPresenter extends BaseLoadingMvpPresenter<CreateNewContactMvpView> {

    @Inject ContactModel mContactModel;

    private String mNewsUserName;
    private String mNewsUserPhone;
    private ViewState mViewState = ViewState.NORMAL;

    @Inject public CreateNewContactMvpPresenter() {
    }

    @Override public void attachView(CreateNewContactMvpView mvpView) {
        super.attachView(mvpView);
        showViewState();
    }

    public void onBtnCreateClick() {
        if (!getMvpView().validateFields()) {
            getMvpView().addValidatingTextWatchers();
            return;
        }
        sendNewContact();
    }

    private ViewState getViewState() {
        return mViewState;
    }

    private void setViewState(ViewState state) {
        mViewState = state;
        showViewState();
    }

    public void setNewUserName(String name) {
        mNewsUserName = name;
    }

    public void setNewUserPhone(String phone) {
        mNewsUserPhone = phone;
    }

    private void sendNewContact() {
        setViewState(ViewState.LOADING);
        addSubscription(
                mContactModel.createNewContact(mNewsUserName, mNewsUserPhone)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Contact>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                if (isViewAttached()) {
                                    getMvpView().showCreatingFailedError();
                                }
                                setViewState(ViewState.NORMAL);
                            }

                            @Override
                            public void onNext(Contact contact) {
                                if (isViewAttached()) {
                                    getMvpView().onContactCreated();
                                }
                                setViewState(ViewState.NORMAL);
                            }
                        }));
    }

    private void showViewState() {
        switch (mViewState) {
            case NORMAL:
                getMvpView().showNormal();
                break;
            case LOADING:
                getMvpView().showLoading();
                break;
        }
    }

    enum ViewState {NORMAL, LOADING}
}
