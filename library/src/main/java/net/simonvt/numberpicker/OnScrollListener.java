package net.simonvt.numberpicker;

/**
 * Interface to listen for the picker scroll state.
 */
public interface OnScrollListener {

    /**
     * The view is not scrolling.
     */
    public static int SCROLL_STATE_IDLE = 0;

    /**
     * The user is scrolling using touch, and his finger is still on the screen.
     */
    public static int SCROLL_STATE_TOUCH_SCROLL = 1;

    /**
     * The user had previously been scrolling using touch and performed a fling.
     */
    public static int SCROLL_STATE_FLING = 2;

    /**
     * Callback invoked while the number picker scroll state has changed.
     *
     * @param view The view whose scroll state is being reported.
     * @param scrollState The current scroll state. One of
     *            {@link #SCROLL_STATE_IDLE},
     *            {@link #SCROLL_STATE_TOUCH_SCROLL} or
     *            {@link #SCROLL_STATE_IDLE}.
     */
    public void onScrollStateChange(ListPicker view, int scrollState);
}
