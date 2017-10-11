package com.ztory.lib.reflective.gson.map;

import java.util.ArrayList;
import java.util.List;

/**
 * Can be used to parse JSON-arrays with GSON-lib.
 * Created by jonruna on 2017-10-10.
 */
public class MapperList extends ArrayList<Mapper> {

  public <T> List<T> toReflective(Class<T> clazz) {
    List<T> returnList = new ArrayList<>(this.size());
    for (Mapper iterMapper : this) {
      returnList.add(iterMapper.toReflective(clazz));
    }
    return returnList;
  }

}
