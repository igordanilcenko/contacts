package by.ingvarskogen.contacts.model;


import by.ingvarskogen.contacts.entity.Contact;
import by.ingvarskogen.contacts.entity.request.CreateContactRequest;
import by.ingvarskogen.contacts.entity.response.ContactDetailResponse;
import by.ingvarskogen.contacts.entity.response.ContactListResponse;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface RestInterface {

    @GET("contactendpoint/v1/contact") Observable<ContactListResponse> getContactList();

    @POST("contactendpoint/v1/contact")
    Observable<Contact> createNewContact(@Body CreateContactRequest request);

    @GET("orderendpoint/v1/order/{contactId}")
    Observable<ContactDetailResponse> getOrderList(@Path("contactId") String contactId);
}
