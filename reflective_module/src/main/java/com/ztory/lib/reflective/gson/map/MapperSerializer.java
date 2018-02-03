package com.ztory.lib.reflective.gson.map;

/**
 * Interface for serializing Mapper instances to String.
 * Created by jonruna on 2018-02-03.
 */
public interface MapperSerializer {
  String getMapperString(Mapper mapper);
}
