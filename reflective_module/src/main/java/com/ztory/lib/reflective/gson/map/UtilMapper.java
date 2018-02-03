package com.ztory.lib.reflective.gson.map;

import com.ztory.lib.reflective.ReflectiveKeyParser;

/**
 * Created by jonruna on 2018-02-03.
 */
public class UtilMapper {

  //TODO CREATE MapperListSerializer AND MapperListDeserializer INSTANCES AS WELL?

  private static MapperSerializer sDefaultMapperSerializer = null;
  private static MapperDeserializer sDefaultMapperDeserializer = null;
  private static MapperListSerializer sDefaultMapperListSerializer = null;
  private static MapperListDeserializer sDefaultMapperListDeserializer = null;

  private static ReflectiveKeyParser sDefaultKeyParser = null;

  public static void initDefaultKeyParser(ReflectiveKeyParser keyParser) {
    if (sDefaultKeyParser != null) {
      throw new IllegalStateException("sDefaultKeyParser != null");
    } else {
      sDefaultKeyParser = keyParser;
    }
  }

  public static ReflectiveKeyParser getDefaultKeyParser() {
    return sDefaultKeyParser;
  }

  public static void initDefaultMapperSerializer(MapperSerializer mapperSerializer) {
    if (sDefaultMapperSerializer != null) {
      throw new IllegalStateException("sDefaultMapperSerializer != null");
    } else {
      sDefaultMapperSerializer = mapperSerializer;
    }
  }

  public static void initDefaultMapperDeserializer(MapperDeserializer mapperDeserializer) {
    if (sDefaultMapperDeserializer != null) {
      throw new IllegalStateException("sDefaultMapperDeserializer != null");
    } else {
      sDefaultMapperDeserializer = mapperDeserializer;
    }
  }

  public static void initDefaultMapperListSerializer(MapperListSerializer mapperListSerializer) {
    if (sDefaultMapperListSerializer != null) {
      throw new IllegalStateException("sDefaultMapperListSerializer != null");
    } else {
      sDefaultMapperListSerializer = mapperListSerializer;
    }
  }

  public static void initDefaultMapperListDeserializer(MapperListDeserializer mapperListDeserializer) {
    if (sDefaultMapperListDeserializer != null) {
      throw new IllegalStateException("sDefaultMapperListDeserializer != null");
    } else {
      sDefaultMapperListDeserializer = mapperListDeserializer;
    }
  }

  public static MapperSerializer getDefaultMapperSerializer() {
    return sDefaultMapperSerializer;
  }

  public static MapperDeserializer getDefaultMapperDeserializer() {
    return sDefaultMapperDeserializer;
  }

  public static MapperListSerializer getDefaultMapperListSerializer() {
    return sDefaultMapperListSerializer;
  }

  public static MapperListDeserializer getDefaultMapperListDeserializer() {
    return sDefaultMapperListDeserializer;
  }

}
