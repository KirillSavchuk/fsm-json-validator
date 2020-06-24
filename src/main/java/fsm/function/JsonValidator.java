package fsm.function;

import fsm.common.Symbols;

public class JsonValidator {

    private String jsonString;

    public JsonValidator(String jsonString) {
        this.jsonString = jsonString;
    }

    public boolean validate() {
        int startIndex = getNextNotEmptyCharIndex(-1);
        if (jsonString.charAt(startIndex) == Symbols.CURLY_OPEN_BRACKET) {
            return getJsonObjectEndIndexFrom(startIndex) == jsonString.length() - 1;
        } else return false;
    }

    /**
     * In case of JSON: <code>{~~~}</code>
     * @param startIndex index of <code>{</code>
     * @return index of <code>}</code>
     */
    private int getJsonObjectEndIndexFrom(int startIndex) {
        int possibleClosedCurlyBracketIndex = getNextNotEmptyCharIndex(startIndex);
        if (jsonString.charAt(possibleClosedCurlyBracketIndex) == Symbols.CURLY_CLOSE_BRACKET) return possibleClosedCurlyBracketIndex; // if empty JSON: {}

        int jsonKeyValuePairStartIndex = getNextNotEmptyCharIndex(startIndex);
        int jsonKeyValuePairEndIndex = jsonKeyValuePairStartIndex + 1;
        int jsonKeyValuePairCommaIndex;
        do {
            if (jsonString.charAt(jsonKeyValuePairStartIndex) == Symbols.QUOTATION_MARK) {
                jsonKeyValuePairEndIndex = getJsonKeyValuePairEndIndexFrom(jsonKeyValuePairStartIndex);
                jsonKeyValuePairCommaIndex = getNextNotEmptyCharIndex(jsonKeyValuePairEndIndex);
                jsonKeyValuePairStartIndex = getNextNotEmptyCharIndex(jsonKeyValuePairCommaIndex);
            } else throw new IllegalArgumentException();
        } while (jsonString.charAt(jsonKeyValuePairCommaIndex) == Symbols.COMMA);

        int jsonObjectEndIndex = getNextNotEmptyCharIndex(jsonKeyValuePairEndIndex);
        if (jsonString.charAt(jsonObjectEndIndex) == Symbols.CURLY_CLOSE_BRACKET) return jsonObjectEndIndex;
        else throw new IllegalArgumentException();
    }

    private int getJsonKeyValuePairEndIndexFrom(int startIndex) {
        int jsonKeyEndIndex = getJsonKeyEndIndexFrom(startIndex);
        int jsonKeyValueColonIndex = getNextNotEmptyCharIndex(jsonKeyEndIndex);

        if (jsonString.charAt(jsonKeyValueColonIndex) == Symbols.COLON) {
            int jsonValueStartIndex = getNextNotEmptyCharIndex(jsonKeyValueColonIndex);
            return getJsonValueEndIndexFrom(jsonValueStartIndex);
        } else throw new IllegalArgumentException();
    }

    private int getJsonKeyEndIndexFrom(int startIndex) {
        int jsonQuotesContentStartIndex = startIndex + 1;
        return getJsonEnclosedQuotesContentEndIndex(jsonQuotesContentStartIndex) + 1;
    }

    private int getJsonValueEndIndexFrom(int startIndex) {
        if (jsonString.charAt(startIndex) == Symbols.QUOTATION_MARK) {
            return getJsonEnclosedQuotesContentEndIndex(startIndex + 1) + 1;
        } else if (jsonString.charAt(startIndex) == Symbols.CURLY_OPEN_BRACKET) {
            return getJsonObjectEndIndexFrom(startIndex);
        } else throw new IllegalArgumentException();
    }


    /**
     * In case of JSON: <code>{~"ABCDE"~}</code>
     * @param startIndex index of quote " before A
     * @return index of quote " after E
     */
    private int getJsonEnclosedQuotesContentEndIndex(int startIndex) {
        int endIndex = startIndex;
        for (; endIndex <= jsonString.length(); endIndex++) {
            char c = jsonString.charAt(endIndex);
            if (c == Symbols.QUOTATION_MARK) break;
            if (!isValidJsonQuotesContent(c))
                throw new IllegalArgumentException(String.format("Illegal character [%s] at %d position",  c, endIndex));
        }
        return endIndex - 1;
    }

    private boolean isValidJsonQuotesContent(char c) {
        return c != Symbols.QUOTATION_MARK;
    }

    private int getNextNotEmptyCharIndex(int currentIndex) {
        if (currentIndex <= jsonString.length()) currentIndex++;
        for (; currentIndex < jsonString.length(); currentIndex++) {
            if (jsonString.charAt(currentIndex) != Symbols.SPACE) break;
        }
        return currentIndex;
    }

}


