package fsm;

import java.util.ArrayList;
import java.util.List;

public final class StateImpl implements State {

    private List<Transition> transitions;
    private boolean isFinal;

    public StateImpl() {
        this(false);
    }

    public StateImpl(final boolean isFinal) {
        this.transitions = new ArrayList<>();
        this.isFinal = isFinal;
    }

    public State transit(final CharSequence c) {
        return transitions
                .stream()
                .filter(t -> t.isPossible(c))
                .map(Transition::state)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.valueOf(c)));
    }

    public boolean isFinal() {
        return this.isFinal;
    }

    public State with(Transition tr) {
        this.transitions.add(tr);
        return this;
    }

}