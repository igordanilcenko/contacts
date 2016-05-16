package by.ingvarskogen.contacts.entity.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import by.ingvarskogen.contacts.entity.Contact;

/**
 * created by Ihar Danilchanka [ihar.danilchanka@anywhere.cz] on 05-May-16.
 */
public class ContactListResponse {

    /**
     * id : 5071001851265024
     * name : Ggg-easy
     * phone : 663556
     * kind : contactendpoint#resourcesItem
     */

    @SerializedName("items") private ArrayList<Contact> items;

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactListResponse that = (ContactListResponse) o;

        return getItems() != null ? getItems().equals(that.getItems()) : that.getItems() == null;

    }

    @Override public int hashCode() {
        return getItems() != null ? getItems().hashCode() : 0;
    }

    public ArrayList<Contact> getItems() {
        return items;
    }

    public void setItems(ArrayList<Contact> items) {
        this.items = items;
    }
}
