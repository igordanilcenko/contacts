package by.ingvarskogen.contacts.presenter.base;

import by.ingvarskogen.contacts.view.base.MvpView;

/**
 * Base class for presenters.
 */
public abstract class BaseMvpPresenter<T extends MvpView> implements MvpPresenter<T> {

    private T mMvpView;

    @Override public void attachView(T mvpView) {
        mMvpView = mvpView;
    }

    @Override public void detachView() {
        mMvpView = null;
    }

    /**
     * Must be called on view destroyed without recreating (e.g. on leave fragment, but not on rotate screen)
     * Place here your destruction code.
     */
    public void onDestroy() {
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public T getMvpView() {
        return mMvpView;
    }
}

