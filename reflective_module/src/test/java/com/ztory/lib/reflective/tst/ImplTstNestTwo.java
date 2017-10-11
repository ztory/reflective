package com.ztory.lib.reflective.tst;

import com.ztory.lib.reflective.gson.map.Mapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jonruna on 2017-10-11.
 */
public class ImplTstNestTwo implements TstNestTwo {

  private final Mapper backingMap;

  public ImplTstNestTwo(Map<String, Object> theBackingMap) {
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
  public String getStatus() {
    return backingMap.optString("status");
  }

  @Override
  public Integer getCounter() {
    Number number = backingMap.optNumber("counter");
    if (number == null) {
      return null;
    }
    return number.intValue();
  }

  @Override
  public List<TstNestTre> getNestTreList() {
    List<Map> mapList = backingMap.getList(Map.class, "nestTreList");
    if (mapList == null) {
      return null;
    }
    List<TstNestTre> returnList = new ArrayList<>(mapList.size());
    for (Map iterMap : mapList) {
      try {
        returnList.add(new ImplTstNestTre((Map<String, Object>) iterMap));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return returnList;
  }
}
