package by.ingvarskogen.contacts.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;
import by.ingvarskogen.contacts.R;
import by.ingvarskogen.contacts.activity.base.BaseFragmentActivity;
import by.ingvarskogen.contacts.fragment.CreateNewContactFragment;

public class CreateNewContactActivity extends BaseFragmentActivity {

    public static final int REQUEST_CODE = 0;
    public static final String ARG_NEED_TO_REFRESH = "ARG_NEED_TO_REFRESH";

    @Bind(R.id.toolbar) Toolbar mToolbar;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_contact);
        ButterKnife.bind(this);
        setUpAppBar();

        if (savedInstanceState == null) {
            addFragment(CreateNewContactFragment.newInstance());
        }
    }

    @Override protected int getFragmentContainerId() {
        return R.id.content;
    }

    private void setUpAppBar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
