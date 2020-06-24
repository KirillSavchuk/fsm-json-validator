package fsm.character;

public interface FiniteStateMachine {

    /**
     * Follow a transition, switch the state of the machine.
     */
    FiniteStateMachine switchState(final char c);

    /**
     * Is the current state a final one?
     */
    boolean canStop();
}
