package by.ingvarskogen.contacts.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.Bind;
import by.ingvarskogen.contacts.R;
import by.ingvarskogen.contacts.adapter.base.SimpleRecyclerAdapter;
import by.ingvarskogen.contacts.di.PerFragment;
import by.ingvarskogen.contacts.entity.Order;

@PerFragment
public class OrderListAdapter extends SimpleRecyclerAdapter<Order> {

    @Inject public OrderListAdapter(Context context) {
        super(context);
    }

    @Override public SimpleRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderViewHolder(getContext(), getViewHolderView(parent, R.layout.row_order));
    }

    public class OrderViewHolder extends SimpleRecyclerViewHolder<Order> {
        @Bind(R.id.txtName) TextView mTxtName;
        @Bind(R.id.txtCount) TextView mTxtCount;

        public OrderViewHolder(Context context, View itemView) {
            super(context, itemView);
        }

        @Override protected void bind(Order order) {
            super.bind(order);
            mTxtName.setText(order.getName() == null ? "" : order.getName());
            mTxtCount.setText(getContext().getString(R.string.order_count, order.getCount()));
        }
    }
}
