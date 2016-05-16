package by.ingvarskogen.contacts.fragment;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import by.ingvarskogen.contacts.R;
import by.ingvarskogen.contacts.activity.ContactDetailActivity;
import by.ingvarskogen.contacts.activity.CreateNewContactActivity;
import by.ingvarskogen.contacts.adapter.ContactListAdapter;
import by.ingvarskogen.contacts.fragment.base.BaseMvpFragment;
import by.ingvarskogen.contacts.entity.Contact;
import by.ingvarskogen.contacts.presenter.ContactListMvpPresenter;
import by.ingvarskogen.contacts.ui.LoadingView;
import by.ingvarskogen.contacts.view.ContactListMvpView;

public class ContactListFragment extends BaseMvpFragment<ContactListMvpView, ContactListMvpPresenter> implements ContactListMvpView {

    @Inject ContactListAdapter mContactListAdapter;

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    @Bind(R.id.swipeToRefreshLayout) SwipeRefreshLayout mSwipeToRefreshLayout;
    @Bind(R.id.loadingView) LoadingView mLoadingView;

    public static ContactListFragment newInstance() {
        return new ContactListFragment();
    }

    public ContactListFragment() {
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentComponent().inject(this);
        setHasOptionsMenu(true);
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // handle returning from #CreateNewContactActivity, reload contacts list
        // if new one was added successfully
        if (resultCode == Activity.RESULT_OK && requestCode == CreateNewContactActivity.REQUEST_CODE) {
            boolean needToRefresh = data.getBooleanExtra(CreateNewContactActivity.ARG_NEED_TO_REFRESH, false);
            if (needToRefresh) {
                getPresenter().refreshContactList();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);
        ButterKnife.bind(this, view);
        initRecyclerView();
        mSwipeToRefreshLayout.setOnRefreshListener(() -> {
            mSwipeToRefreshLayout.setRefreshing(false);
            getPresenter().onSwipeToRefresh();
        });
        return view;
    }

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_contact_list, menu);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_add:
                getPresenter().onActionAddClick();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override public void showContactList(ArrayList<Contact> contactList) {
        mContactListAdapter.setItems(contactList);
        mContactListAdapter.notifyDataSetChanged();
        mLoadingView.setState(LoadingView.State.NORMAL);
    }

    @Override public void showLoading() {
        mLoadingView.setState(LoadingView.State.LOADING);
    }

    @Override public void showEmpty() {
        mLoadingView.setState(LoadingView.State.EMPTY);
    }

    @Override public void showError() {
        mLoadingView.setState(LoadingView.State.ERROR);
        mLoadingView.setOnReloadClickListener(getPresenter()::onReloadClick);
    }

    @Override public void startCreateNewContact() {
        Intent intent = new Intent(getBaseActivity(), CreateNewContactActivity.class);
        startActivityForResult(intent, CreateNewContactActivity.REQUEST_CODE);
    }

    @Override public void startContactDetail(Contact contact, View contactView) {
        Intent intent = new Intent(getBaseActivity(), ContactDetailActivity.class);
        intent.putExtra(ContactDetailActivity.ARG_CONTACT, contact);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String transitionName = getString(R.string.user_icon_transition);
            View sharedView = contactView.findViewById(R.id.imgUserIcon);

            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(getBaseActivity(), sharedView, transitionName);
            startActivity(intent, transitionActivityOptions.toBundle());

        } else {
            getBaseActivity().startActivity(intent);
        }
    }

    @Override public void showRefreshingError() {
        Toast.makeText(getContext(), R.string.error_refreshing_contact_list, Toast.LENGTH_LONG).show();
    }

    private void initRecyclerView() {
        mRecyclerView.setAdapter(mContactListAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mContactListAdapter.setOnItemClickListener(getPresenter()::onContactClick);
    }
}
