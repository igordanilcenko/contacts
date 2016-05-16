package by.ingvarskogen.contacts.entity;

import com.google.gson.annotations.SerializedName;

/**
 * created by Ihar Danilchanka [ihar.danilchanka@anywhere.cz] on 06-May-16.
 */
public class Order {

    @SerializedName("name") private String name;
    @SerializedName("count") private int count;

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (getCount() != order.getCount()) return false;
        return getName() != null ? getName().equals(order.getName()) : order.getName() == null;

    }

    @Override public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + getCount();
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}