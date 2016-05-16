package by.ingvarskogen.contacts.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import by.ingvarskogen.contacts.ContactsApiConfig;

public class Utils {

    public static void hideSoftKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void requestFocus(View view, Activity activity) {
        if (view.requestFocus() && activity != null) {
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    /**
     * Image URL is relative with prefix: http://inloop-contacts.appspot.com/
     *
     * @param pictureUrl picture relative URL, e.g. "profile1.jpg"
     * @return image URL, e.g. http://inloop-contacts.appspot.com/profile1.jpg
     */
    public static String getContactPictureUrl(String pictureUrl) {
        return ContactsApiConfig.CONTACT_PICTURE_URL_PREFIX + pictureUrl;
    }
}
