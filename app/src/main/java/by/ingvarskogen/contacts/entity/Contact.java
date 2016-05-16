package by.ingvarskogen.contacts.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Contact implements Parcelable {

    public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>() {
        @Override public Contact createFromParcel(Parcel source) {
            return new Contact(source);
        }

        @Override public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
    /**
     * id : 6306064257515520
     * name : xxx  344  2
     * phone : N/A  344  2
     * pictureUrl : https://openclipart.org/image/800px/svg_to_png/224584/FightingSkeleton.png
     */

    @SerializedName("id") private String id;
    @SerializedName("name") private String name;
    @SerializedName("phone") private String phone;
    @SerializedName("pictureUrl") private String pictureUrl;

    public Contact() {
    }

    protected Contact(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.phone = in.readString();
        this.pictureUrl = in.readString();
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.phone);
        dest.writeString(this.pictureUrl);
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (getId() != null ? !getId().equals(contact.getId()) : contact.getId() != null)
            return false;
        if (!getName().equals(contact.getName()))
            return false;
        if (getPhone() != null ? !getPhone().equals(contact.getPhone()) : contact.getPhone() != null)
            return false;
        return getPictureUrl() != null ? getPictureUrl().equals(contact.getPictureUrl()) : contact.getPictureUrl() == null;

    }

    @Override public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName().hashCode());
        result = 31 * result + (getPhone() != null ? getPhone().hashCode() : 0);
        result = 31 * result + (getPictureUrl() != null ? getPictureUrl().hashCode() : 0);
        return result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NonNull public String getName() {
        return name == null ? "" : name;
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

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
