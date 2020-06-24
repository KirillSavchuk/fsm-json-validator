package fsm.character;

public final class TransitionImpl implements Transition {

    private char transitionRule;
    private State nextState;

    /**
     * @param transitionRule Rule that a character has to meet in order to get to the next State.
     */
    //TODO: Change rule to RegEx validation
    public TransitionImpl(char transitionRule, State nextState) {
        this.transitionRule = transitionRule;
        this.nextState = nextState;
    }

    public State getNextState() {
        return this.nextState;
    }

    public boolean isPossible(char c) { return this.transitionRule == c; }

}