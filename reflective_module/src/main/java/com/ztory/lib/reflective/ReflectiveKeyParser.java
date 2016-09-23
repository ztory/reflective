package com.ztory.lib.reflective;

/**
 * Interface that abstracts key-parsing.
 * Created by jonruna on 26/04/16.
 */
public interface ReflectiveKeyParser {
    /**
     * Returns a String from the supplied startOffset and key.
     * @param startOffset startOffset to use for String key
     * @param key String that is the key before parse
     * @return String that is the key after the parse operation
     */
    String getParsedKey(int startOffset, String key);
}
