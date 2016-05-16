package by.ingvarskogen.contacts.entity.request;

import com.google.gson.annotations.SerializedName;

public class CreateContactRequest {

    /**
     * name : Dddd
     * phone : 420123456789
     */

    @SerializedName("name") private String name;
    @SerializedName("phone") private String phone;

    public CreateContactRequest(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreateContactRequest that = (CreateContactRequest) o;

        return getName() != null ? getName().equals(that.getName()) : that.getName() == null
                && (getPhone() != null ? getPhone().equals(that.getPhone()) : that.getPhone() == null);

    }

    @Override public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getPhone() != null ? getPhone().hashCode() : 0);
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
