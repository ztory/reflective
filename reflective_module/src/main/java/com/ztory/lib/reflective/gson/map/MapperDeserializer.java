package com.ztory.lib.reflective.gson.map;

/**
 * Interface for deserializing Mapper instances from String.
 * Created by jonruna on 2018-02-03.
 */
public interface MapperDeserializer {
  Mapper getMapper(String mapperString);
}
