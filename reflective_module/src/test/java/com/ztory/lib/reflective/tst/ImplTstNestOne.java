package com.ztory.lib.reflective.tst;

import com.ztory.lib.reflective.gson.map.Mapper;
import java.util.Map;

/**
 * Created by jonruna on 2017-10-11.
 */
public class ImplTstNestOne implements TstNestOne {

  private final Mapper backingMap;

  public ImplTstNestOne(Map<String, Object> theBackingMap) {
    if (theBackingMap instanceof Mapper) {
      backingMap = (Mapper) theBackingMap;
    } else {
      backingMap = new Mapper(theBackingMap);
    }
  }

  @Override
  public Map<String, Object> get_reflectiveMapBacked() {
    return backingMap;
  }

  @Override
  public String getName() {
    return backingMap.optString("name");
  }

  @Override
  public Integer getSize() {
    Number number = backingMap.optNumber("size");
    if (number == null) {
      return null;
    }
    return number.intValue();
  }

  @Override
  public TstNestTwo getNestTwo() {
    Map<String, Object> nestTwoMap = backingMap.getMap(Object.class, "nestTwo");
    if (nestTwoMap == null) {
      return null;
    }
    return new ImplTstNestTwo(nestTwoMap);
  }
}
