package by.ingvarskogen.contacts.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import by.ingvarskogen.contacts.R;
import by.ingvarskogen.contacts.activity.CreateNewContactActivity;
import by.ingvarskogen.contacts.fragment.base.BaseMvpFragment;
import by.ingvarskogen.contacts.misc.OnTextChangedTextWatcher;
import by.ingvarskogen.contacts.presenter.CreateNewContactMvpPresenter;
import by.ingvarskogen.contacts.ui.LoadingView;
import by.ingvarskogen.contacts.util.Utils;
import by.ingvarskogen.contacts.view.CreateNewContactMvpView;

public class CreateNewContactFragment extends BaseMvpFragment<CreateNewContactMvpView, CreateNewContactMvpPresenter> implements CreateNewContactMvpView {

    @Bind(R.id.loadingView) LoadingView mLoadingView;
    @Bind(R.id.editName) EditText mEditName;
    @Bind(R.id.inputLayoutName) TextInputLayout mInputLayoutName;
    @Bind(R.id.editPhone) EditText mEditPhone;
    @Bind(R.id.inputLayoutPhone) TextInputLayout mInputLayoutPhone;

    public static CreateNewContactFragment newInstance() {
        return new CreateNewContactFragment();
    }

    public CreateNewContactFragment() {
        // Required empty public constructor
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentComponent().inject(this);
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_new_contact, container, false);
        ButterKnife.bind(this, view);

        mEditPhone.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                onBtnCreateClick();
                handled = true;
            }
            return handled;
        });
        mEditName.addTextChangedListener(new OnTextChangedTextWatcher() {
            @Override
            protected void onTextChanged(String s) {
                getPresenter().setNewUserName(s);
            }
        });
        mEditPhone.addTextChangedListener(new OnTextChangedTextWatcher() {
            @Override
            protected void onTextChanged(String s) {
                getPresenter().setNewUserPhone(s);
            }
        });
        return view;
    }

    @Override public void showNormal() {
        mLoadingView.setState(LoadingView.State.NORMAL);
    }

    @Override public void showLoading() {
        Utils.hideSoftKeyboard(getContext(), mLoadingView);
        mLoadingView.setState(LoadingView.State.LOADING);
    }

    /**
     * Validates text fields. Focus on last checked field with error.
     *
     * @return true if all fields are valid
     */
    @Override public boolean validateFields() {
        return validateNonEmptyField(mEditPhone, mInputLayoutPhone) &
                validateNonEmptyField(mEditName, mInputLayoutName);
    }

    @Override public void addValidatingTextWatchers() {
        mEditName.addTextChangedListener(new OnTextChangedTextWatcher() {
            @Override
            protected void onTextChanged(String s) {
                validateNonEmptyField(mEditName, mInputLayoutName);
            }
        });
        mEditPhone.addTextChangedListener(new OnTextChangedTextWatcher() {
            @Override
            protected void onTextChanged(String s) {
                validateNonEmptyField(mEditPhone, mInputLayoutPhone);
            }
        });
    }

    @Override public void onContactCreated() {
        Toast.makeText(getContext(), R.string.create_new_contact_success, Toast.LENGTH_LONG).show();

        Intent intent = new Intent();
        // on contact created successfully, need to refresh contacts list on resume
        intent.putExtra(CreateNewContactActivity.ARG_NEED_TO_REFRESH, true);
        getBaseActivity().setResult(Activity.RESULT_OK, intent);
        getBaseActivity().finish();
    }

    @Override public void showCreatingFailedError() {
        Toast.makeText(getContext(), R.string.error_create_new_user, Toast.LENGTH_SHORT).show();
    }

    /**
     * Validate text field and focus to it on error.
     *
     * @param editText    edittext for validation
     * @param inputLayout wrapper for editText with possibility to show error message
     * @return true if this field is valid
     */
    protected boolean validateNonEmptyField(EditText editText, TextInputLayout inputLayout) {
        String value = editText.getText().toString().trim();

        if (value.isEmpty()) {
            inputLayout.setErrorEnabled(true);
            inputLayout.setError(getString(R.string.error_empty_field));
            Utils.requestFocus(editText, getBaseActivity());
            return false;
        } else if (value.length() < 5) {
            inputLayout.setErrorEnabled(true);
            inputLayout.setError(getString(R.string.error_min_length_field));
            Utils.requestFocus(editText, getBaseActivity());
            return false;
        } else {
            inputLayout.setErrorEnabled(false);
        }

        return true;
    }

    @OnClick(R.id.btnCreate) void onBtnCreateClick() {
        getPresenter().onBtnCreateClick();
    }
}
