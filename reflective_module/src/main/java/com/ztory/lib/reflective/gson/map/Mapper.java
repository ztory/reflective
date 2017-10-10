package com.ztory.lib.reflective.gson.map;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Can be used to parse JSON-objects with GSON-lib.
 * Getting nested values is as simple as:
 * <code>mapper.getVal(String.class, "nest_list", 0, "nestX", "nestX_list", 0, "deepz")</code>
 * The above code would get the "nest_list"-value, 0-index-item, "nestX"-value, "nestX_list"-value,
 * 0-index-item, "deepz"-value.
 * Created by jonruna on 26/09/16.
 */
public class Mapper extends LinkedHashMap<String, Object> {

  public Number optNumber(Object... keys) {
    return optVal(Number.class, keys);
  }

  public Number getNumber(Object... keys) {
    return getVal(Number.class, keys);
  }

  public String optString(Object... keys) {
    return optVal(String.class, keys);
  }

  public String getString(Object... keys) {
    return getVal(String.class, keys);
  }

  public Boolean optBoolean(Object... keys) {
    return optVal(Boolean.class, keys);
  }

  public Boolean getBoolean(Object... keys) {
    return getVal(Boolean.class, keys);
  }

  public <T> T optVal(Class<T> clazz, Object... keys) {
    return getValue(true, clazz, keys);
  }

  public <T> T getVal(Class<T> clazz, Object... keys) {
    return getValue(false, clazz, keys);
  }

  public <T> T getValue(boolean safeMode, Class<T> clazz, Object... keys) {
    try {
      Object returnObject = this;
      Object iterKey;
      for (int i = 0; i < keys.length; i++) {
        iterKey = keys[i];
        if (iterKey instanceof String) {
          returnObject = ((Map<String, Object>) returnObject).get(iterKey);
        } else if (iterKey instanceof Integer) {
          returnObject = ((List<Object>) returnObject).get((Integer) iterKey);
        } else if (!safeMode) {
          throw new IllegalStateException("iterKey is not String or Integer: " + iterKey);
        }
      }
      if (!safeMode || (returnObject != null && clazz.isAssignableFrom(returnObject.getClass()))) {
        return (T) returnObject;
      } else {
        return null;
      }
    } catch (Exception e) {
      if (!safeMode) {
        throw e;
      } else {
        return null;
      }
    }
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
