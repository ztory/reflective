package com.ztory.lib.reflective.tst;

import com.ztory.lib.reflective.gson.map.Mapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jonruna on 2017-10-11.
 */
public class ImplTstBase implements TstBase {

  private final Mapper backingMap;

  public ImplTstBase(Map<String, Object> theBackingMap) {
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
  public String getTitle() {
    return backingMap.optString("title");
  }

  @Override
  public Integer getCount() {
    Number number = backingMap.optNumber("count");
    if (number == null) {
      return null;
    }
    return number.intValue();
  }

  @Override
  public List<TstNestOne> getNestOneList() {
    List<Map> mapList = backingMap.getList(Map.class, "nestOneList");
    if (mapList == null) {
      return null;
    }
    List<TstNestOne> returnList = new ArrayList<>(mapList.size());
    for (Map iterMap : mapList) {
      try {
        returnList.add(new ImplTstNestOne((Map<String, Object>) iterMap));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return returnList;
  }
}
