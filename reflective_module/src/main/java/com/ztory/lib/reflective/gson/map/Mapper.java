package com.ztory.lib.reflective.gson.map;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jonruna on 26/09/16.
 */
public class Mapper extends LinkedHashMap<String, Object> {

    public Number optNumber(String... keys) {
        try {
            return getValue(keys);
        } catch (Exception e) {
            //do nothing
        }
        return null;
    }

    public Number getNumber(String... keys) {
        return getValue(keys);
    }

    public String optString(String... keys) {
        try {
            return getValue(keys);
        } catch (Exception e) {
            //do nothing
        }
        return null;
    }

    public String getString(String... keys) {
        return getValue(keys);
    }

    public <T> T getValue(String... keys) {
        try {
            Map<String, Object> iterMap = this;
            for (int i = 0; i < keys.length - 1; i++) {
                iterMap = (Map<String, Object>) iterMap.get(keys[i]);
            }
            return (T) iterMap.get(keys[keys.length - 1]);
        } catch (Exception e) {
            //do nothing
        }
        return null;
    }

    public <T> Map<String, T> getMap(String... keys) {
        try {
            Map<String, Object> iterMap = this;
            for (String iterKey : keys) {
                iterMap = (Map<String, Object>) iterMap.get(iterKey);
            }
            return (Map<String, T>) iterMap;
        } catch (Exception e) {
            //do nothing
        }
        return null;
    }

    public <T> List<T> getList(String... keys) {
        try {
            Map<String, Object> iterMap = this;
            for (int i = 0; i < keys.length - 1; i++) {
                iterMap = (Map<String, Object>) iterMap.get(keys[i]);
            }
            List<T> returnList = (List<T>) iterMap.get(keys[keys.length - 1]);
            return returnList;
        } catch (Exception e) {
            //do nothing
        }
        return null;
    }

}
