package by.ingvarskogen.contacts.fragment.base;

import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

import by.ingvarskogen.contacts.activity.base.BaseActivity;

/**
 * Base class for fragments. Contains common methods for all fragments
 */
public abstract class BaseFragment extends Fragment {

    public BaseActivity getBaseActivity() {
        try {
            return (BaseActivity) getActivity();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setTitle(String title) {
        if (getActivity() != null && ((BaseActivity) getActivity()).getSupportActionBar() != null && title != null) {
            ((BaseActivity) getActivity()).getSupportActionBar().setTitle(title);
        }
    }

    public void setTitle(@StringRes int titleResId) {
        if (getActivity() != null && ((BaseActivity) getActivity()).getSupportActionBar() != null) {
            ((BaseActivity) getActivity()).getSupportActionBar().setTitle(titleResId);
        }
    }
}
