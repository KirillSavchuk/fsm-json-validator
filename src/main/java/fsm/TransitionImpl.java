package fsm;

public final class TransitionImpl implements Transition {

    private String rule;
    private State next;

    /**
     * @param rule Rule that a character has to meet in order to get to the next State.
     */
    //TODO: Change rule to RegEx validation
    public TransitionImpl(String rule, State next) {
        this.rule = rule;
        this.next = next;
    }

    public State state() {
        return this.next;
    }

    public boolean isPossible(CharSequence c) {
        return this.rule.equalsIgnoreCase(String.valueOf(c));
    }

}