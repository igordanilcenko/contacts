package by.ingvarskogen.contacts.view;

import java.util.ArrayList;

import by.ingvarskogen.contacts.entity.Order;
import by.ingvarskogen.contacts.view.base.MvpView;

public interface ContactDetailMvpView extends MvpView {

    void showOrderList(ArrayList<Order> orderList);

    void showLoading();

    void showEmpty();

    void showError();
}
