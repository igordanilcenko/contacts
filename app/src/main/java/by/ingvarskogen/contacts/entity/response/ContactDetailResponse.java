package by.ingvarskogen.contacts.entity.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import by.ingvarskogen.contacts.entity.Order;

public class ContactDetailResponse {

    /**
     * id : {"kind":"Order","appId":"s~inloop-contacts","id":"5629499534213120","parent":{"kind":"Contact","appId":"s~inloop-contacts","id":"5071001851265024","complete":true},"complete":true}
     * name : HDD
     * count : 4
     * kind : orderendpoint#resourcesItem
     */

    @SerializedName("items") private ArrayList<Order> orderList;

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactDetailResponse that = (ContactDetailResponse) o;

        return getOrderList() != null ? getOrderList().equals(that.getOrderList()) : that.getOrderList() == null;

    }

    @Override public int hashCode() {
        return getOrderList() != null ? getOrderList().hashCode() : 0;
    }

    public ArrayList<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(ArrayList<Order> orderList) {
        this.orderList = orderList;
    }
}
