package fsm.character;

import org.junit.Test;

import static org.junit.Assert.*;

public class JsonValidatorFSMTest {

    @Test
    public void emptyJsonIsOK() {
        String json = "{}";
        JsonValidatorFSM jsonValidatorFSM = new JsonValidatorFSM();
        assertTrue(jsonValidatorFSM.validate(json));
    }

    @Test
    public void onePairJsonIsOK() {
        String json = "{\"key\":\"value\"}";
        JsonValidatorFSM jsonValidatorFSM = new JsonValidatorFSM();
        assertTrue(jsonValidatorFSM.validate(json));
    }

    @Test
    public void multiplePairJsonIsOK() {
        String json = "{\"a\":\"b\",\"c\":\"d\",\"e\":\"f\"}";
        JsonValidatorFSM jsonValidatorFSM = new JsonValidatorFSM();
        assertTrue(jsonValidatorFSM.validate(json));
    }

    @Test(expected = IllegalArgumentException.class)
    public void onlyJsonKeyIsFail() {
        String json = "{\"a\"}";
        JsonValidatorFSM jsonValidatorFSM = new JsonValidatorFSM();
        assertTrue(jsonValidatorFSM.validate(json));
    }

    @Test(expected = IllegalArgumentException.class)
    public void jsonWithCommaIsFail() {
        String json = "{\"a\"}";
        JsonValidatorFSM jsonValidatorFSM = new JsonValidatorFSM();
        assertTrue(jsonValidatorFSM.validate(json));
    }

}