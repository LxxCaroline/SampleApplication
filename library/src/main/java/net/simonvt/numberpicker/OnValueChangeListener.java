package net.simonvt.numberpicker;

/**
 * Interface to listen for changes of the current value.
 */
public interface OnValueChangeListener {

    /**
     * Called upon a change of the current value.
     *
     * @param picker The NumberPicker associated with this listener.
     * @param oldVal The previous value.
     * @param newVal The new value.
     */
    void onValueChange(ListPicker picker, int oldVal, int newVal);
}
