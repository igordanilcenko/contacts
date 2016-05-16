package by.ingvarskogen.contacts.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import by.ingvarskogen.contacts.R;
import by.ingvarskogen.contacts.adapter.OrderListAdapter;
import by.ingvarskogen.contacts.fragment.base.BaseMvpFragment;
import by.ingvarskogen.contacts.entity.Contact;
import by.ingvarskogen.contacts.entity.Order;
import by.ingvarskogen.contacts.presenter.ContactDetailMvpPresenter;
import by.ingvarskogen.contacts.ui.LoadingView;
import by.ingvarskogen.contacts.view.ContactDetailMvpView;

public class ContactDetailFragment extends BaseMvpFragment<ContactDetailMvpView, ContactDetailMvpPresenter> implements ContactDetailMvpView {

    private static final String KEY_CONTACT = "KEY_CONTACT";
    @Inject OrderListAdapter mOrderListAdapter;

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    @Bind(R.id.loadingView) LoadingView mLoadingView;

    public static ContactDetailFragment newInstance(Contact contact) {
        ContactDetailFragment f = new ContactDetailFragment();
        Bundle b = new Bundle();
        b.putParcelable(KEY_CONTACT, contact);
        f.setArguments(b);
        return f;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentComponent().inject(this);

        getPresenter().setContact(getArguments().getParcelable(KEY_CONTACT));
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_detail, container, false);
        ButterKnife.bind(this, view);
        initRecyclerView();
        return view;
    }

    @Override public void showOrderList(ArrayList<Order> orderList) {
        mOrderListAdapter.setItems(orderList);
        mOrderListAdapter.notifyDataSetChanged();
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
        mLoadingView.setOnReloadClickListener(() -> getPresenter().onReloadClick());
    }

    private void initRecyclerView() {
        mRecyclerView.setAdapter(mOrderListAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
