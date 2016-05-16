package by.ingvarskogen.contacts.adapter.base;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public abstract class SimpleRecyclerAdapter<T> extends RecyclerView.Adapter<SimpleRecyclerAdapter.SimpleRecyclerViewHolder> {

    protected List<T> mItemList;
    private OnItemClickListener<T> mOnItemClickListener;
    private Context mContext;

    public SimpleRecyclerAdapter(Context context) {
        mItemList = new ArrayList<>();
        mContext = context;
    }

    @Override
    public abstract SimpleRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    @Override public void onBindViewHolder(SimpleRecyclerViewHolder holder, int position) {
        T item = mItemList.get(position);
        holder.bind(item);
    }

    @Override public int getItemCount() {
        return mItemList.size();
    }

    public T getItem(int position) {
        return mItemList.get(position);
    }

    protected Context getContext() {
        return mContext;
    }

    public void setItems(List<T> items) {
        if (items == null) {
            mItemList = new ArrayList<>();
        } else {
            mItemList = items;
        }
    }

    public void setOnItemClickListener(OnItemClickListener<T> mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    /**
     * Return view with given layout item id.
     *
     * @param parent       parent view
     * @param itemLayoutId item layout id
     * @return inflated view
     */
    protected View getViewHolderView(ViewGroup parent, @LayoutRes int itemLayoutId) {
        return LayoutInflater.from(parent.getContext()).inflate(itemLayoutId, parent, false);
    }

    public interface OnItemClickListener<T> {
        void onItemClick(T item, View v);
    }

    protected abstract class SimpleRecyclerViewHolder<D> extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected View mItemView;
        private D mData;

        public SimpleRecyclerViewHolder(Context context, View itemView) {
            super(itemView);
            mItemView = itemView;
            mContext = context;
            ButterKnife.bind(this, itemView);
        }

        @Override public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getItem(getAdapterPosition()), v);
            }
        }

        protected D getData() {
            return mData;
        }

        protected void bind(D data) {
            mData = data;
            mItemView.setOnClickListener(this);
        }
    }

}
