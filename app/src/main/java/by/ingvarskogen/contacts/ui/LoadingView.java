package by.ingvarskogen.contacts.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import java.util.Arrays;
import java.util.List;

import by.ingvarskogen.contacts.R;


/**
 * View contains different states for easy displaying error or loading progress
 */
public class LoadingView extends FrameLayout {

    private State mLastState;
    private View mView;
    private View mProgressView;
    private View mErrorView;
    private View mEmptyView;
    private OnReloadClickListener mOnReloadClickListener;

    @LayoutRes private int mEmptyLayout = R.layout.layout_empty;
    @LayoutRes private int mErrorLayout = R.layout.layout_error;
    @LayoutRes private int mProgressLayout = R.layout.layout_progress;

    public LoadingView(Context context) {
        super(context);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.LoadingView,
                0, 0);

        try {
            mEmptyLayout = a.getResourceId(R.styleable.LoadingView_emptyLayout, R.layout.layout_empty);
            mErrorLayout = a.getResourceId(R.styleable.LoadingView_errorLayout, R.layout.layout_error);
            mProgressLayout = a.getResourceId(R.styleable.LoadingView_progressLayout, R.layout.layout_progress);
        } finally {
            a.recycle();
        }
    }

    private boolean isInitNeeded() {
        return mProgressView == null;
    }

    @Nullable private View getCurrentShowingView() {
        if (mLastState == null) {
            return null;
        }
        switch (mLastState) {
            case LOADING:
                return mProgressView;
            case ERROR:
                return mErrorView;
            case NORMAL:
                return mView;
            case EMPTY:
                return mEmptyView;
        }
        return null;
    }

    public void setState(State state) {
        if (state == mLastState) {
            return;
        }
        if (isInitNeeded()) {
            init();
        }
        View newShowingView;
        View oldShowingView = getCurrentShowingView();
        switch (state) {
            case LOADING:
                newShowingView = mProgressView;
                break;
            case ERROR:
                newShowingView = mErrorView;
                break;
            case NORMAL:
                newShowingView = mView;
                break;
            case EMPTY:
                newShowingView = mEmptyView;
                break;
            default:
                return;
        }
        if ((oldShowingView == null || state == State.LOADING) && newShowingView != null) {//no crossfading needed, just show new view and hide all others
            newShowingView.setVisibility(VISIBLE);
            setViewsShownExcept(new View[]{newShowingView}, false, mProgressView, mErrorView, mView, mEmptyView);
        } else {
            crossFadeViews(oldShowingView, newShowingView);
            setViewsShownExcept(new View[]{oldShowingView, newShowingView}, false, mProgressView, mErrorView, mView, mEmptyView);
        }
        mLastState = state;
    }

    public void setOnReloadClickListener(OnReloadClickListener onReloadClickListener) {
        mOnReloadClickListener = onReloadClickListener;
    }

    private void init() {
        mView = getChildAt(0);
        mProgressView = LayoutInflater.from(getContext()).inflate(mProgressLayout, this, false);
        mErrorView = LayoutInflater.from(getContext()).inflate(mErrorLayout, this, false);
        mEmptyView = LayoutInflater.from(getContext()).inflate(mEmptyLayout, this, false);
        mErrorView.findViewById(R.id.reload).setOnClickListener(v -> {
            if (mOnReloadClickListener != null) {
                mOnReloadClickListener.onReloadClick();
            }
        });

        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mProgressView, lp);
        addView(mErrorView, lp);
        addView(mEmptyView, lp);
    }

    /**
     * Sets visibility defined by visible to all views except the ones defined in ignoreViews
     */
    private void setViewsShownExcept(@NonNull View[] ignoredViews, boolean visible, @NonNull View... views) {
        List<View> ignoredList = Arrays.asList(ignoredViews);
        for (View view : views) {
            if (!ignoredList.contains(view) && view != null) {
                view.setVisibility(visible ? VISIBLE : INVISIBLE);
            }
        }
    }

    private void crossFadeViews(View viewToHide, View viewToShow) {
        if (viewToHide != null) {
            viewToHide.startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out));
            viewToHide.setVisibility(View.INVISIBLE);
        }
        if (viewToShow != null) {
            viewToShow.startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in));
            viewToShow.setVisibility(View.VISIBLE);
        }
    }

    public enum State {
        NORMAL, LOADING, ERROR, EMPTY
    }

    public interface OnReloadClickListener {
        void onReloadClick();
    }

}
