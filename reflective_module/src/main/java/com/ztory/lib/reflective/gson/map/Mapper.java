package com.ztory.lib.reflective.gson.map;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jonruna on 26/09/16.
 */
public class Mapper extends LinkedHashMap<String, Object> {

  public Number optNumber(String... keys) {
    return getVal(true, Number.class, keys);
  }

  public Number getNumber(String... keys) {
    return getVal(false, Number.class, keys);
  }

  public String optString(String... keys) {
    return getVal(true, String.class, keys);
  }

  public String getString(String... keys) {
    return getVal(false, String.class, keys);
  }

  public Boolean optBoolean(String... keys) {
    return getVal(true, Boolean.class, keys);
  }

  public Boolean getBoolean(String... keys) {
    return getVal(false, Boolean.class, keys);
  }

  public <T> T getVal(boolean safeMode, Class<T> clazz, String... keys) {
    try {
      Map<String, Object> iterMap = this;
      for (int i = 0; i < keys.length - 1; i++) {
        iterMap = (Map<String, Object>) iterMap.get(keys[i]);
      }
      Object returnObject = iterMap.get(keys[keys.length - 1]);
      if (!safeMode || (returnObject != null && clazz.isAssignableFrom(returnObject.getClass()))) {
        return (T) returnObject;
      } else {
        return null;
      }
    } catch (Exception e) {
      if (!safeMode) {
        throw e;
      }
    }
    return null;
  }

  public <T> Map<String, T> getMap(Class<T> clazz, String... keys) {
    try {
      Map<String, Object> iterMap = this;
      for (String iterKey : keys) {
        iterMap = (Map<String, Object>) iterMap.get(iterKey);
      }
      if (iterMap instanceof Map) {
        if (iterMap.size() > 0) {
          for (T iterVal : (Collection<T>) iterMap.values()) {
            if (!clazz.isAssignableFrom(iterVal.getClass())) {
              return null;
            }
          }
          return (Map<String, T>) iterMap;
        } else {
          return (Map<String, T>) iterMap;
        }
      }
    } catch (Exception e) {
      //do nothing
    }
    return null;
  }

  public <T> List<T> getList(Class<T> clazz, String... keys) {
    try {
      Map<String, Object> iterMap = this;
      for (int i = 0; i < keys.length - 1; i++) {
        iterMap = (Map<String, Object>) iterMap.get(keys[i]);
      }
      Object returnObject = iterMap.get(keys[keys.length - 1]);
      if (returnObject instanceof List) {
        List returnList = (List) returnObject;
        for (T iterVal : (List<T>) returnList) {
          if (!clazz.isAssignableFrom(iterVal.getClass())) {
            return null;
          }
        }
        return (List<T>) returnList;
      }
    } catch (Exception e) {
      //do nothing
    }
    return null;
  }

}
