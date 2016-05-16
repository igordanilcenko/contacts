package by.ingvarskogen.contacts.presenter.base;

import by.ingvarskogen.contacts.view.base.MvpView;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Base class for presenters using rx loading.
 */
public abstract class BaseLoadingMvpPresenter<T extends MvpView> extends BaseMvpPresenter<T> {

    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    @Override public void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.unsubscribe();
    }

    protected void addSubscription(Subscription subscription) {
        mCompositeSubscription.add(subscription);
    }

}
