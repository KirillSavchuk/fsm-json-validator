package fsm.character;

import fsm.common.Symbols;

public class JsonValidatorFSM {

    private FiniteStateMachine fsm;

    public JsonValidatorFSM() {
        this.fsm = buildJsonValidationFSM();
    }

    public boolean validate(String jsonString) {
        for (int i = 0; i < jsonString.length(); i++) {
            fsm = fsm.switchState(jsonString.charAt(i));
        }
        return fsm.canStop();
    }

    private FiniteStateMachine buildJsonValidationFSM() {
        State sateInitial = new StateImpl();
        State stateOpenedCurlyBrace = new StateImpl();
        State stateKeyContent = new StateImpl();
        State stateKeyClosedQuotationMark = new StateImpl();
        State stateKeyValueColon = new StateImpl();
        State stateValueContent = new StateImpl();
        State stateValueClosedQuotationMark = new StateImpl();
        State stateClosedCurlyBrace = new StateImpl(true);

        sateInitial.with(new TransitionImpl(Symbols.CURLY_OPEN_BRACKET, stateOpenedCurlyBrace));
        stateOpenedCurlyBrace.with(new TransitionImpl(Symbols.QUOTATION_MARK, stateKeyContent));
        stateOpenedCurlyBrace.with(new TransitionImpl(Symbols.CURLY_CLOSE_BRACKET, stateClosedCurlyBrace));

        //Add transitions with chars 0-9 and a-z
        for (int i = 0; i < 26; i++) {
            if (i < 10) {
                stateKeyContent = stateKeyContent.with(new TransitionImpl((char) i, stateKeyContent));
                stateValueContent.with(new TransitionImpl((char) i, stateValueContent));
            }
            stateKeyContent.with(new TransitionImpl((char) ('a' + i), stateKeyContent));
            stateValueContent.with(new TransitionImpl((char) ('a' + i), stateValueContent));
        }

        stateKeyContent.with(new TransitionImpl(Symbols.QUOTATION_MARK, stateKeyClosedQuotationMark));
        stateKeyClosedQuotationMark.with(new TransitionImpl(Symbols.COLON, stateKeyValueColon));
        stateKeyValueColon.with(new TransitionImpl(Symbols.QUOTATION_MARK, stateValueContent));
        stateValueContent.with(new TransitionImpl(Symbols.QUOTATION_MARK, stateValueClosedQuotationMark));
        stateValueClosedQuotationMark.with(new TransitionImpl(Symbols.COMMA, stateOpenedCurlyBrace));
        stateValueClosedQuotationMark.with(new TransitionImpl(Symbols.CURLY_CLOSE_BRACKET, stateClosedCurlyBrace));
        return new FiniteStateMachineImpl(sateInitial);
    }

}