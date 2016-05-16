package by.ingvarskogen.contacts.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import by.ingvarskogen.contacts.R;
import by.ingvarskogen.contacts.activity.base.BaseFragmentActivity;
import by.ingvarskogen.contacts.fragment.ContactDetailFragment;
import by.ingvarskogen.contacts.misc.CropCircleTransformation;
import by.ingvarskogen.contacts.entity.Contact;
import by.ingvarskogen.contacts.util.Utils;

public class ContactDetailActivity extends BaseFragmentActivity {

    public static final String ARG_CONTACT = "ARG_CONTACT";

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.imgUserIcon) ImageView mImgUserIcon;
    @Bind(R.id.txtName) TextView mTxtName;
    @Bind(R.id.txtPhone) TextView mTxtPhone;

    private Contact mContact;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        ButterKnife.bind(this);
        mContact = getIntent().getParcelableExtra(ARG_CONTACT);
        setUpAppBar();

        if (savedInstanceState == null) {
            addFragment(ContactDetailFragment.newInstance(mContact));
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

        setTitle(mContact.getName());

        if (mContact.getPictureUrl() != null) {
            Glide.with(this)
                    .load(Utils.getContactPictureUrl(mContact.getPictureUrl()))
                    .bitmapTransform(new CropCircleTransformation(this))
                    .into(mImgUserIcon);
        } else {
            Glide.with(this)
                    .load(R.drawable.placeholder_person)
                    .bitmapTransform(new CropCircleTransformation(this))
                    .into(mImgUserIcon);
        }
        mTxtName.setText(mContact.getName());
        mTxtPhone.setText(mContact.getPhone() == null ? "" : mContact.getPhone());
    }

}
