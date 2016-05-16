package by.ingvarskogen.contacts.activity.base;

import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Base class for activities. Contains common methods for all activities
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
