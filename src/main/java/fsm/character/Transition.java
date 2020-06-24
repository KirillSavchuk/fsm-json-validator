package fsm.character;

public interface Transition {

    /**
     * Check transition possibility with the given CharSequence
     */
    boolean isPossible(final char c);

    /**
     * The state to which this transition leads.
     */
    State getNextState();
}