package fsm.character;

import fsm.character.*;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public final class FiniteStateMachineImplTest {

    @Test
    public void numberSequenceIsOK() {
        String numbers = "12345";
        FiniteStateMachine fsm = this.buildNumberSequenceFSM();
        for (int i = 0; i < numbers.length(); i++) {
            fsm = fsm.switchState(numbers.charAt(i));
        }
        assertTrue(fsm.canStop());
    }

    @Test(expected = IllegalArgumentException.class)
    public void numberSequenceIsFail() {
        String numbers = "12346";
        FiniteStateMachine fsm = this.buildNumberSequenceFSM();
        for (int i = 0; i < numbers.length(); i++) {
            fsm = fsm.switchState(numbers.charAt(i));
        }
    }

    private FiniteStateMachine buildNumberSequenceFSM() {
        State one = new StateImpl();
        State two = new StateImpl();
        State tree = new StateImpl();
        State four = new StateImpl();
        State fife = new StateImpl();
        State end = new StateImpl(true);

        one.with(new TransitionImpl('1', two));
        two.with(new TransitionImpl('2', tree));
        tree.with(new TransitionImpl('3', four));
        four.with(new TransitionImpl('4', fife));
        fife.with(new TransitionImpl('5', end));

        return new FiniteStateMachineImpl(one);
    }

}