package by.ingvarskogen.contacts.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;
import by.ingvarskogen.contacts.R;
import by.ingvarskogen.contacts.activity.base.BaseFragmentActivity;
import by.ingvarskogen.contacts.fragment.ContactListFragment;

public class ContactListActivity extends BaseFragmentActivity {

    @Bind(R.id.toolbar) Toolbar mToolbar;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        ButterKnife.bind(this);
        setUpAppBar();

        if (savedInstanceState == null) {
            addFragment(ContactListFragment.newInstance());
        }
    }

    @Override protected int getFragmentContainerId() {
        return R.id.content;
    }

    private void setUpAppBar() {
        setSupportActionBar(mToolbar);
    }
}
