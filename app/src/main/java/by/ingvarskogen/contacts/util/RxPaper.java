package by.ingvarskogen.contacts.util;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.paperdb.Paper;
import rx.Observable;
import rx.Subscriber;

public class RxPaper {

    /**
     * Saves any types of POJOs or collections in Book storage.
     *
     * @param bookName name of book
     * @param key      object key is used as part of object's file name
     * @param value    object to save, must have no-arg constructor, can't be null.
     * @return status of saving
     */
    public static <T> Observable<Boolean> write(final String bookName, final String key, final T value) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override public void call(Subscriber<? super Boolean> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    try {
                        Paper.book(bookName).write(key, value);
                        subscriber.onNext(true);
                    } catch (Exception e) {
                        subscriber.onError(new UnableToPerformOperationException("Can't write"));
                    }
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * Instantiates saved object using original object class (e.g. LinkedList) or return default
     * value. Support limited backward and forward compatibility: removed fields are ignored,
     * new fields have their default values.
     * <p>
     * All instantiated objects must have no-arg constructors.
     *
     * @param bookName     name of book
     * @param key          object key to read
     * @param defaultValue will be returned if key doesn't exist
     * @return the saved object instance observable or null
     */
    public static <T> Observable<T> read(final String bookName, final String key, final T defaultValue) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override public void call(Subscriber<? super T> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    T value = Paper.book(bookName).read(key, defaultValue);

                    if (value == null) {
                        subscriber.onError(new UnableToPerformOperationException(key + " is empty"));
                    } else {
                        subscriber.onNext(value);
                    }
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * Instantiates saved object using original object class (e.g. LinkedList). Support limited
     * backward and forward compatibility: removed fields are ignored, new fields have their
     * default values.
     * <p>
     * All instantiated objects must have no-arg constructors.
     *
     * @param bookName name of book
     * @param key      object key to read
     * @return an Observable with the saved object instance or null
     */
    public static <T> Observable<T> read(final String bookName, final String key) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override public void call(Subscriber<? super T> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    T value = Paper.book(bookName).read(key);

                    if (value == null) {
                        subscriber.onError(new UnableToPerformOperationException(key + " is empty"));
                    } else {
                        subscriber.onNext(value);
                    }

                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * Delete saved object for given key if it is exist.
     *
     * @param bookName name of book
     * @param key      object key
     */
    public static Observable<Boolean> delete(final String bookName, final String key) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override public void call(Subscriber<? super Boolean> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    try {
                        Paper.book(bookName).delete(key);
                        subscriber.onNext(true);
                    } catch (Exception e) {
                        subscriber.onError(new UnableToPerformOperationException("Can't delete"));
                    }

                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * Check if given key exist.
     *
     * @param bookName name of book
     * @param key      object key
     * @return true if item exist
     */
    public static Observable<Boolean> exists(final String bookName, final String key) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override public void call(Subscriber<? super Boolean> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    try {
                        subscriber.onNext(Paper.book(bookName).exist(key));
                    } catch (Exception e) {
                        subscriber.onError(new UnableToPerformOperationException("Can't check if key exists"));
                    }

                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * Retrieve all keys for this book.
     *
     * @param bookName name of book
     * @return list of keys
     */
    public static Observable<List<String>> getAllKeys(final String bookName) {
        return Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    try {
                        subscriber.onNext(Paper.book(bookName).getAllKeys());
                    } catch (Exception e) {
                        subscriber.onError(new UnableToPerformOperationException("Can't collect all keys"));
                    }

                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * Destroy this book.
     *
     * @param bookName name of book
     * @return status of destroying
     */
    public static Observable<Boolean> destroy(final String bookName) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override public void call(Subscriber<? super Boolean> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    try {
                        Paper.book(bookName).destroy();
                        subscriber.onNext(true);
                    } catch (Exception e) {
                        subscriber.onError(new UnableToPerformOperationException("Can't destroy"));
                    }
                    subscriber.onCompleted();

                }
            }
        });
    }

    public static <T> Observable<ArrayList<T>> readAllItems(final String bookName) {
        return getAllKeys(bookName)
                .flatMap(Observable::from)
                .flatMap(key -> read(bookName, key))
                .toList()
                .map(objects -> new ArrayList<>((Collection<? extends T>) objects));
    }

    /**
     * If input value isn't null, saves it.
     * Returns saved value from book.
     *
     * @param bookName name of book
     * @param key      object key is used as part of object's file name
     * @param value    object to save, must have no-arg constructor
     * @return cached value
     */
    public static <T> Observable<T> cache(final String bookName, final String key, @Nullable final T value) {

        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override public void call(Subscriber<? super T> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    if (value != null) {
                        Paper.book(bookName).write(key, value);
                        subscriber.onNext(value);
                    } else {
                        subscriber.onNext(Paper.book(bookName).<T>read(key));
                    }
                    subscriber.onCompleted();
                }
            }
        });
    }

    public static class UnableToPerformOperationException extends Exception {
        public UnableToPerformOperationException(String detailMessage) {
            super(detailMessage);
        }
    }
}
