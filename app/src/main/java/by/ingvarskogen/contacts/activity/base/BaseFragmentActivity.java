package by.ingvarskogen.contacts.activity.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

/**
 * Base activity for work with fragments.
 */
public abstract class BaseFragmentActivity extends BaseActivity {

    private static final String DEFAULT_STACK = "DEFAULT_STACK";

    public void replaceFragment(Fragment fragment) {
        replaceFragment(fragment, true);
    }

    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(getFragmentContainerId(), fragment);
        if (addToBackStack) {
            transaction.addToBackStack(DEFAULT_STACK);
        }
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        transaction.commitAllowingStateLoss();
    }

    public void addFragment(Fragment fragment) {
        addFragment(fragment, false);
    }

    public void addFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .add(getFragmentContainerId(), fragment);
        if (addToBackStack) {
            transaction.addToBackStack(DEFAULT_STACK);
        }
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commitAllowingStateLoss();
    }

    protected Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(getFragmentContainerId());
    }

    /**
     * method is abstract because developer must not forget add fragment container layout to activity layout
     *
     * @return id of fragment container view
     */
    protected abstract int getFragmentContainerId();
}
