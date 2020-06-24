package fsm;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public final class FiniteStateMachineImplImplTest {

    @Test
    public void numberSequenceIsCorrect() {
        String numbers = "12345";
        FiniteStateMachine fsm = this.buildNumberSequenceFSM();
        for (int i = 0; i < numbers.length(); i++) {
            fsm = fsm.switchState(String.valueOf(numbers.charAt(i)));
        }
        assertTrue(fsm.canStop());
    }

    @Test(expected = IllegalArgumentException.class)
    public void numberSequenceIsFailure() {
        String numbers = "12346";
        FiniteStateMachine fsm = this.buildNumberSequenceFSM();
        for (int i = 0; i < numbers.length(); i++) {
            fsm = fsm.switchState(String.valueOf(numbers.charAt(i)));
        }
    }

    @Test
    public void jsonIsCorrect() {
        String json = "{\"key\":\"value\"}";
        FiniteStateMachine fsm = this.buildJsonValidationFSM();
        for (int i = 0; i < json.length(); i++) {
            fsm = fsm.switchState(String.valueOf(json.charAt(i)));
        }
        assertTrue(fsm.canStop());
    }

    @Test
    public void jsonWithMorePairsIsCorrect() {
        String json = "{\"key1\":\"value1\",\"key2\":\"value2\"}";
        FiniteStateMachine fsm = this.buildJsonValidationFSM();
        for (int i = 0; i < json.length(); i++) {
            fsm = fsm.switchState(String.valueOf(json.charAt(i)));
        }
        assertTrue(fsm.canStop());
    }

    @Test(expected = IllegalArgumentException.class)
    public void jsonWithMissingColonIsFailure() {
        String json = "{\"key\"\"value\"}";
        FiniteStateMachine fsm = this.buildJsonValidationFSM();
        for (int i = 0; i < json.length(); i++) {
            fsm = fsm.switchState(String.valueOf(json.charAt(i)));
        }
    }

    private FiniteStateMachine buildNumberSequenceFSM() {
        State one = new StateImpl();
        State two = new StateImpl();
        State tree = new StateImpl();
        State four = new StateImpl();
        State fife = new StateImpl();
        State end = new StateImpl(true);

        one.with(new TransitionImpl("1", two));
        two.with(new TransitionImpl("2", tree));
        tree.with(new TransitionImpl("3", four));
        four.with(new TransitionImpl("4", fife));
        fife.with(new TransitionImpl("5", end));

        return new FiniteStateMachineImpl(one);
    }

    private FiniteStateMachine buildJsonValidationFSM() {
        State first = new StateImpl();
        State second = new StateImpl();
        State third = new StateImpl();
        State fourth = new StateImpl();
        State fifth = new StateImpl();
        State sixth = new StateImpl();
        State seventh = new StateImpl();
        State eighth = new StateImpl(true);

        first.with(new TransitionImpl("{", second));
        second.with(new TransitionImpl("\"", third));

        //Add transitions with chars 0-9 and a-z
        for (int i = 0; i < 26; i++) {
            if (i < 10) {
                third.with(new TransitionImpl(String.valueOf(i), third));
                sixth.with(new TransitionImpl(String.valueOf(i), sixth));
            }
            third.with(new TransitionImpl(String.valueOf((char) ('a' + i)), third));
            sixth.with(new TransitionImpl(String.valueOf((char) ('a' + i)), sixth));
        }

        third.with(new TransitionImpl("\"", fourth));
        fourth.with(new TransitionImpl(":", fifth));
        fifth.with(new TransitionImpl("\"", sixth));
        sixth.with(new TransitionImpl("\"", seventh));
        seventh.with(new TransitionImpl(",", second));
        seventh.with(new TransitionImpl("}", eighth));

        return new FiniteStateMachineImpl(first);
    }
}