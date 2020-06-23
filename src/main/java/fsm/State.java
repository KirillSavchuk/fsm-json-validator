package fsm;

public interface State {

    /**
     * Adds new possible Transition from this state.
     */
    State with(final Transition tr);

    /**
     * Choose possible transition, to go to the next state.
     */
    State transit(final CharSequence c);

    /**
     * Check if the FSM can stop on this state
     */
    boolean isFinal();
}