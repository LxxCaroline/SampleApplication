package com.example.hzlinxuanxuan.refreshlistview.weibo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.example.hzlinxuanxuan.refreshlistview.R;
import com.example.hzlinxuanxuan.refreshlistview.bidirection.BiDirectionListViewFooter;
import com.example.hzlinxuanxuan.refreshlistview.bidirection.BiDirectionListViewHeader;

/**
 * Created by hzlinxuanxuan on 2015/10/29.
 * 该ListView模仿微博的列表，可下拉刷新，当滑到最后一个是自动加载
 */
public class WeiboListView extends ListView implements AbsListView.OnScrollListener {

    private float mLastY = -1; // save event y

    /**
     * Scroller
     */
    private Scroller mScroller;
    private final static int SCROLL_DURATION = 400; // scroll back duration
    private final static float OFFSET_RADIO = 2.8f; // support iOS like pull

    /**
     * Listeners
     */
    // the listener to notify user.
    private RefreshLoadListener mListViewListener;

    /**
     * header view
     */
    private WeiboListViewHeader mHeaderView;
    private RelativeLayout mHeaderViewContent;
    private int mHeaderViewHeight;
    private boolean mEnablePullRefresh = true;
    private boolean mPullRefreshing = false;

    /**
     * footer view
     */
    private WeiboListViewFooter mFooterView;
    private boolean mEnablePullLoad;
    private boolean mPullLoading;
    private boolean mIsFooterReady = false;

    public WeiboListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWithContext(context);
    }

    private void initWithContext(Context context) {
        mScroller = new Scroller(context, new DecelerateInterpolator());
        super.setOnScrollListener(this);

        // init header view
        mHeaderView = new WeiboListViewHeader(context);
        mHeaderViewContent = (RelativeLayout) mHeaderView.findViewById(R.id.xlistview_header_content);
        addHeaderView(mHeaderView);
        this.setHeaderDividersEnabled(false);

        // init footer view
        mFooterView = new WeiboListViewFooter(context);
        this.setFooterDividersEnabled(false);

        // init header height
        mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mHeaderViewHeight = mHeaderViewContent.getHeight();
                        getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        // make sure XListViewFooter is the last footer view, and only add once.
        if (mIsFooterReady == false) {
            mIsFooterReady = true;
            addFooterView(mFooterView);
        }
        super.setAdapter(adapter);
    }

    /**
     * 禁止下拉刷新
     */
    public void setPullRefreshEnable(boolean enable) {
        mEnablePullRefresh = enable;
        if (!mEnablePullRefresh) { // disable, hide the content
            mHeaderViewContent.setVisibility(View.INVISIBLE);
        } else {
            mHeaderViewContent.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 禁止上拉加载
     */
    public void setPullLoadEnable(boolean enable) {
        mEnablePullLoad = enable;
        if (mEnablePullLoad) {
            mPullLoading = false;
            mFooterView.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            mFooterView.setState(BiDirectionListViewFooter.STATE_NORMAL);
        } else {
            mFooterView.setHeight(0);
        }
    }

    /**
     * 停止下拉刷新
     */
    public void stopRefresh() {
        if (mPullRefreshing == true) {
            mPullRefreshing = false;
            resetHeaderHeight();
        }
    }

    /**
     * 停止上拉加载
     */
    public void stopLoadMore() {
        if (mPullLoading == true) {
            mPullLoading = false;
            mFooterView.setState(WeiboListViewFooter.STATE_NORMAL);
        }
    }

    /****
     * 列表为空时不能进行上拉
     */
    private void startLoadMore() {
        mPullLoading = true;
        mFooterView.setState(BiDirectionListViewFooter.STATE_LOADING);
        if (mListViewListener != null) {
            mListViewListener.onLoadMore();
        }
    }

    private boolean isRefreshing = false;

    /**
     * 监听手势的触摸，当手触摸页面，页面上的滑动都靠该函数
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mLastY == -1) {
            mLastY = ev.getRawY();
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getRawY();

                isRefreshing = false;
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaY = ev.getRawY() - mLastY;
                mLastY = ev.getRawY();
                if (getFirstVisiblePosition() == 0 && (mHeaderView.getVisiableHeight() > 0 || deltaY > 0)) {
                    // the first item is showing, header has shown or pull down.
                    updateHeaderHeight(deltaY / OFFSET_RADIO);
                    if (!isRefreshing) {
                        isRefreshing = true;
                    }
                }
                break;
            default:
                mLastY = -1;
                //如果是下拉刷新
                if (getFirstVisiblePosition() == 0) {
                    if (mEnablePullRefresh && mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
                        mPullRefreshing = true;
                        mHeaderView.setState(BiDirectionListViewHeader.STATE_REFRESHING);
                        if (mListViewListener != null) {
                            mListViewListener.onRefresh();
                        }
                    }
                    resetHeaderHeight();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 当手势触摸页面上对header高度的调整
     */
    private void updateHeaderHeight(float delta) {
        mHeaderView.setVisiableHeight((int) delta + mHeaderView.getVisiableHeight());
        if (mEnablePullRefresh && !mPullRefreshing) { // 未处于刷新状态，更新箭头
            if (mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
                mHeaderView.setState(BiDirectionListViewHeader.STATE_READY);
            } else {
                mHeaderView.setState(BiDirectionListViewHeader.STATE_NORMAL);
            }
        }
        setSelection(0); // scroll to top each time
    }

    /**
     * reset header view's height.
     * 当松手之后，这里要通过scroller回滚回去
     */
    private void resetHeaderHeight() {
        int height = mHeaderView.getVisiableHeight();
        //若当前不可见
        if (height == 0) return;
        //当前在刷新，并且当前未显示全
        if (mPullRefreshing && height <= mHeaderViewHeight) return;
        int finalHeight = 0; // default: scroll back to dismiss header.
        // is refreshing, just scroll back to show all the header.
        if (mPullRefreshing && height > mHeaderViewHeight) {
            finalHeight = mHeaderViewHeight;
        }
        mScroller.startScroll(0, height, 0, finalHeight - height, SCROLL_DURATION);
        invalidate();
    }

    /**
     * 为了易于控制滑屏控制，Android框架提供了computeScroll()方法去控制这个流程。
     * 在绘制View时，会在draw()过程调用该方法。因此，再配合使用Scroller实例，我们就可以获得当前应该的偏移坐标，手动使View/ViewGroup偏移至该处。
     * computeScroll()方法原型如下，该方法位于ViewGroup.java类中
     * Called by a parent to request that a child update its values for mScrollX and mScrollY if necessary.
     * This will typically be done if the child is animating a scroll using a {@link android.widget.Scroller }
     * 由父视图调用用来请求子视图根据偏移值 mScrollX,mScrollY重新绘制
     */
    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            mHeaderView.setVisiableHeight(mScroller.getCurrY());
            postInvalidate();
        }
        super.computeScroll();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    //当滑至footer的时候，自动加载
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (!mPullLoading && visibleItemCount != 0 && getLastVisiblePosition() == (totalItemCount - 1)) {
            startLoadMore();
        }
    }

    public void setXListViewListener(RefreshLoadListener l) {
        mListViewListener = l;
    }

    /**
     * implements this interface to get refresh/load more event.
     */
    public interface RefreshLoadListener {
        void onRefresh();

        void onLoadMore();
    }
}
