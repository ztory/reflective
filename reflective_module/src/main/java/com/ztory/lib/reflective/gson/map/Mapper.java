package com.ztory.lib.reflective.gson.map;

import com.ztory.lib.reflective.Reflective;
import com.ztory.lib.reflective.ReflectiveKeyParser;
import com.ztory.lib.reflective.ReflectiveMapBacked;
import java.util.ArrayList;
import java.util.Arrays;
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

  public static Mapper obj(Object... keyVals) {
    Mapper mapper = new Mapper();
    for (int i = 0; i < keyVals.length; i += 2) {
      mapper.put((String) keyVals[i], keyVals[i + 1]);
    }
    return mapper;
  }

  @SafeVarargs
  public static <T> List<T> array(T... items) {
    return Arrays.asList(items);
  }

  public static Mapper fromString(String mapperString) {
    return UtilMapper.getDefaultMapperDeserializer().getMapper(mapperString);
  }

  public static Mapper fromReflective(ReflectiveMapBacked mapBacked) {
    Map<String, Object> map = mapBacked.get_reflectiveMapBacked();
    if (map instanceof Mapper) {
      return (Mapper) map;
    } else {
      return new Mapper(map);
    }
  }

  protected ReflectiveKeyParser reflectiveKeyParser = UtilMapper.getDefaultKeyParser();
  protected Map<String, String> reflectiveCustomKeyMap = null;

  public Mapper() {
    super();
  }

  public Mapper(Map<? extends String, ? extends Object> map) {
    super(map);
  }

  public void setKeyParser(ReflectiveKeyParser keyParser) {
    reflectiveKeyParser = keyParser;
  }

  public void setCustomKeyMap(Map<String, String> customKeyMap) {
    reflectiveCustomKeyMap = customKeyMap;
  }

  @Override
  public String toString() {
    return UtilMapper.getDefaultMapperSerializer().getMapperString(this);
  }

  public <T> T toReflective(Class<T> clazz) {
    return Reflective.getReflectiveInstance(
        clazz,
        this,
        reflectiveKeyParser,
        reflectiveCustomKeyMap
    );
  }

  public <T> List<T> getReflectiveList(Class<T> clazz, Object... keys) {
    Object object = getVal(Object.class, keys);
    if (object instanceof List) {
      List<Map<String, Object>> objectList = (List<Map<String, Object>>) object;
      List<T> returnList = new ArrayList<>(objectList.size());
      for (Map<String, Object> iterMap : objectList) {
        returnList.add(
            Reflective.getReflectiveInstance(
                clazz,
                iterMap,
                reflectiveKeyParser,
                reflectiveCustomKeyMap
            )
        );
      }
      return returnList;
    }
    return null;
  }

  public <T> T getReflective(Class<T> clazz, Object... keys) {
    Object object = getVal(Object.class, keys);
    if (object instanceof Map) {
      return Reflective.getReflectiveInstance(
          clazz,
          (Map<String, Object>) object,
          reflectiveKeyParser,
          reflectiveCustomKeyMap
      );
    }
    return null;
  }

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

  public <T> T putValue(T value, boolean safeMode, Class<T> clazz, Object... keys) {
    try {
      Object returnObject = this;
      Object iterKey;
      for (int i = 0; i < keys.length - 1; i++) {
        iterKey = keys[i];
        if (iterKey instanceof String) {
          returnObject = ((Map<String, Object>) returnObject).get(iterKey);
        } else if (iterKey instanceof Integer) {
          if ((Integer) iterKey < 0) {
            returnObject = ((List<Object>) returnObject).get(((List<Object>) returnObject).size() + (Integer) iterKey);
          } else {
            returnObject = ((List<Object>) returnObject).get((Integer) iterKey);
          }
        } else if (!safeMode) {
          throw new IllegalStateException("iterKey is not String or Integer: " + iterKey);
        }
      }
      iterKey = keys[keys.length - 1];
      if (iterKey instanceof String) {
        returnObject = ((Map<String, Object>) returnObject).put((String) iterKey, value);
      } else if (iterKey instanceof Integer) {
        List list = ((List<Object>) returnObject);
        final int putIndex;
        if ((Integer) iterKey < 0) {
          putIndex = list.size() + (Integer) iterKey;
          returnObject = list.get(putIndex);
          list.add(putIndex + 1, value);
        } else {
          putIndex = (Integer) iterKey;
          returnObject = list.get(putIndex);
          list.add(putIndex, value);
        }
      } else if (!safeMode) {
        throw new IllegalStateException("iterKey is not String or Integer: " + iterKey);
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

  public <T> T getValue(boolean safeMode, Class<T> clazz, Object... keys) {
    try {
      Object returnObject = this;
      Object iterKey;
      for (int i = 0; i < keys.length; i++) {
        iterKey = keys[i];
        if (iterKey instanceof String) {
          returnObject = ((Map<String, Object>) returnObject).get(iterKey);
        } else if (iterKey instanceof Integer) {
          if ((Integer) iterKey < 0) {
            returnObject = ((List<Object>) returnObject).get(((List<Object>) returnObject).size() + (Integer) iterKey);
          } else {
            returnObject = ((List<Object>) returnObject).get((Integer) iterKey);
          }
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
