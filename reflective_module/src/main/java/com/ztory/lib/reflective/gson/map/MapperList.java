package com.ztory.lib.reflective.gson.map;

import com.ztory.lib.reflective.ReflectiveMapBacked;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Can be used to parse JSON-arrays with GSON-lib.
 * Created by jonruna on 2017-10-10.
 */
public class MapperList extends ArrayList<Mapper> {

  public static MapperList fromString(String mapperListString) {
    return UtilMapper.getDefaultMapperListDeserializer().getMapperList(mapperListString);
  }

  public static MapperList fromReflective(List<? extends ReflectiveMapBacked> mapBackedList) {
    MapperList mapperList = new MapperList();
    for (ReflectiveMapBacked iterMapBacked : mapBackedList) {
      Map<String, Object> map = iterMapBacked.get_reflectiveMapBacked();
      if (map instanceof Mapper) {
        mapperList.add((Mapper) map);
      } else {
        mapperList.add(new Mapper(map));
      }
    }
    return mapperList;
  }

  @Override
  public String toString() {
    return UtilMapper.getDefaultMapperListSerializer().getMapperListString(this);
  }

  public <T> List<T> toReflective(Class<T> clazz) {
    List<T> returnList = new ArrayList<>(this.size());
    for (Mapper iterMapper : this) {
      returnList.add(iterMapper.toReflective(clazz));
    }
    return returnList;
  }

}
