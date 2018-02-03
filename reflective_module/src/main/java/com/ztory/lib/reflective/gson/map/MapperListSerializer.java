package com.ztory.lib.reflective.gson.map;

/**
 * Interface for serializing MapperList instances to String.
 * Created by jonruna on 2018-02-03.
 */
public interface MapperListSerializer {
  String getMapperListString(MapperList mapperList);
}
