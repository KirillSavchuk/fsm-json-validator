package fsm.function;

import fsm.function.JsonValidator;
import org.junit.Test;

import static org.junit.Assert.*;

public class JsonValidatorTest {

    @Test
    public void emptyJsonOK() {
        String json = "{}";
        JsonValidator jsonValidator = new JsonValidator(json);
        assertTrue(jsonValidator.validate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyJsonFail() {
        String json = "{]";
        JsonValidator jsonValidator = new JsonValidator(json);
        assertTrue(jsonValidator.validate());
    }

    @Test
    public void nestedJsonOK() {
        String json = "{ \"a\" : \"b\",\"c\":{\"d\":{\"e\":\"f\"}}}";
        JsonValidator jsonValidator = new JsonValidator(json);
        assertTrue(jsonValidator.validate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void nestedJsonFail() {
        String json = "{\"a\":\"b\",\"c\":{\"d\":{\"e\"}}}";
        JsonValidator jsonValidator = new JsonValidator(json);
        jsonValidator.validate();
    }
}