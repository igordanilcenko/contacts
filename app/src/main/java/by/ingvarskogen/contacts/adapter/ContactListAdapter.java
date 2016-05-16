package by.ingvarskogen.contacts.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import butterknife.Bind;
import by.ingvarskogen.contacts.R;
import by.ingvarskogen.contacts.adapter.base.SimpleRecyclerAdapter;
import by.ingvarskogen.contacts.di.PerFragment;
import by.ingvarskogen.contacts.misc.CropCircleTransformation;
import by.ingvarskogen.contacts.entity.Contact;
import by.ingvarskogen.contacts.util.Utils;

@PerFragment
public class ContactListAdapter extends SimpleRecyclerAdapter<Contact> {

    @Inject public ContactListAdapter(Context context) {
        super(context);
    }

    @Override public SimpleRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactViewHolder(getContext(), getViewHolderView(parent, R.layout.row_contact));
    }

    public class ContactViewHolder extends SimpleRecyclerViewHolder<Contact> {
        @Bind(R.id.imgUserIcon) ImageView mImgUserIcon;
        @Bind(R.id.txtName) TextView mTxtName;
        @Bind(R.id.txtPhone) TextView mTxtPhone;

        public ContactViewHolder(Context context, View itemView) {
            super(context, itemView);
        }

        @Override protected void bind(Contact contact) {
            super.bind(contact);
            mTxtName.setText(contact.getName());
            mTxtPhone.setText(contact.getPhone() == null ? "" : contact.getPhone());

            if (contact.getPictureUrl() != null) {
                Glide.with(getContext())
                        .load(Utils.getContactPictureUrl(contact.getPictureUrl()))
                        .bitmapTransform(new CropCircleTransformation(getContext()))
                        .into(mImgUserIcon);
            } else {
                Glide.with(getContext())
                        .load(R.drawable.placeholder_person)
                        .bitmapTransform(new CropCircleTransformation(getContext()))
                        .into(mImgUserIcon);
            }
        }
    }
}
