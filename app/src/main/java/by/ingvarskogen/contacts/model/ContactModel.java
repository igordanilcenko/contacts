package by.ingvarskogen.contacts.model;


import java.util.ArrayList;

import javax.inject.Inject;

import by.ingvarskogen.contacts.di.PerApplication;
import by.ingvarskogen.contacts.entity.Contact;
import by.ingvarskogen.contacts.entity.Order;
import by.ingvarskogen.contacts.entity.request.CreateContactRequest;
import by.ingvarskogen.contacts.entity.response.ContactDetailResponse;
import by.ingvarskogen.contacts.entity.response.ContactListResponse;
import by.ingvarskogen.contacts.util.RxPaper;
import by.ingvarskogen.contacts.util.RxPaperHelper;
import io.paperdb.Paper;
import rx.Observable;

@PerApplication
public class ContactModel {

    protected RestInterface mRestInterface;

    @Inject public ContactModel(RestInterface restInterface) {
        this.mRestInterface = restInterface;
    }

    /**
     * Concating reading from cache and from API. At first will be emited
     * cache data (if are available) and after will be emited loaded from API data.
     *
     * @return Observable emiting list of contacts loaded from cache or API
     */
    public Observable<ArrayList<Contact>> loadContactList() {
        return Observable.concat(
                loadContactListFromCache()
                        .onErrorResumeNext(throwable -> Observable.empty()),
                loadContactListFromApi()
                        .onErrorResumeNext(throwable -> Observable.empty()))
                .defaultIfEmpty(null);
    }

    public Observable<ArrayList<Contact>> loadContactListFromCache() {
        return RxPaper.<ArrayList<Contact>>read(RxPaperHelper.BOOK_DEFAULT, RxPaperHelper.CONTACT_LIST);
    }

    public Observable<ArrayList<Contact>> loadContactListFromApi() {
        return mRestInterface.getContactList()
                .map(ContactListResponse::getItems)
                // sorting by name
                .flatMap(Observable::from)
                .toSortedList((contact, contact2) -> contact.getName().compareToIgnoreCase(contact2.getName()))
                .map(ArrayList::new)
                .doOnNext(contacts -> Paper.book(RxPaperHelper.BOOK_DEFAULT).write(RxPaperHelper.CONTACT_LIST, contacts));
    }

    public Observable<ArrayList<Order>> loadOrderList(String contactId) {
        return mRestInterface.getOrderList(contactId)
                .map(ContactDetailResponse::getOrderList);
    }

    public Observable<Contact> createNewContact(String name, String phone) {
        return mRestInterface.createNewContact(new CreateContactRequest(name, phone));
    }
}