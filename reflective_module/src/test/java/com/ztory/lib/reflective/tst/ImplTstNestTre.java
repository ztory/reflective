package com.ztory.lib.reflective.tst;

import com.ztory.lib.reflective.gson.map.Mapper;
import java.util.List;
import java.util.Map;

/**
 * Created by jonruna on 2017-10-11.
 */
public class ImplTstNestTre implements TstNestTre {

  private final Mapper backingMap;

  public ImplTstNestTre(Map<String, Object> theBackingMap) {
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
  public String getLabel() {
    return backingMap.optString("label");
  }

  @Override
  public Integer getAttempts() {
    Number number = backingMap.optNumber("attempts");
    if (number == null) {
      return null;
    }
    return number.intValue();
  }

  @Override
  public List<String> getRoles() {
    return backingMap.getList(String.class, "roles");
  }
}
