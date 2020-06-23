package fsm;

public interface Transition {

    /**
     * Check transition possibility with the given CharSequence
     */
    boolean isPossible(final CharSequence c);

    /**
     * The state to which this transition leads.
     */
    State state();
}