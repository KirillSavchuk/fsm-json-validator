package fsm;

public class FiniteStateMachineImpl implements FiniteStateMachine {

    private State currentState;

    /**
     * @param initialState Initial state of this machine.
     */
    public FiniteStateMachineImpl(final State initialState) {
        this.currentState = initialState;
    }


    public FiniteStateMachineImpl switchState(final CharSequence c) {
        return new FiniteStateMachineImpl(this.currentState.transit(c));
    }


    public boolean canStop() {
        return this.currentState.isFinal();
    }

}