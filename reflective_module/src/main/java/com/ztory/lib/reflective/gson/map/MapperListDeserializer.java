package com.ztory.lib.reflective.gson.map;

/**
 * Interface for deserializing MapperList instances from String.
 * Created by jonruna on 2018-02-03.
 */
public interface MapperListDeserializer {
  MapperList getMapperList(String mapperListString);
}
