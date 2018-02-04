package com.ztory.lib.reflective;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.ztory.lib.reflective.gson.map.Mapper;
import com.ztory.lib.reflective.gson.map.MapperDeserializer;
import com.ztory.lib.reflective.gson.map.MapperList;
import com.ztory.lib.reflective.gson.map.MapperListDeserializer;
import com.ztory.lib.reflective.gson.map.MapperListSerializer;
import com.ztory.lib.reflective.gson.map.MapperSerializer;
import com.ztory.lib.reflective.gson.map.UtilMapper;
import java.lang.reflect.Type;

/**
 * Created by jonruna on 2018-02-03.
 */
public class TestGsonAndAppInitializer {

  private static Gson GSON;

  public static Gson init() {
    if (GSON == null) {
      GsonBuilder gsonBuilder = new GsonBuilder();
      gsonBuilder.registerTypeAdapter(Double.class,  new JsonSerializer<Double>() {
        @Override
        public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
          if (src == src.longValue()) {
            return new JsonPrimitive(src.longValue());
          }
          return new JsonPrimitive(src);
        }
      });
      GSON = gsonBuilder.create();
    }

    if (UtilMapper.getDefaultKeyParser() == null) {
      UtilMapper.initDefaultKeyParser(Reflective.CAMELCASE);
    }

    if (UtilMapper.getDefaultMapperSerializer() == null) {
      UtilMapper.initDefaultMapperSerializer(
          new MapperSerializer() {
            @Override
            public String getMapperString(Mapper mapper) {
              return GSON.toJson(mapper, Mapper.class);
            }
          }
      );
    }

    if (UtilMapper.getDefaultMapperDeserializer() == null) {
      UtilMapper.initDefaultMapperDeserializer(
          new MapperDeserializer() {
            @Override
            public Mapper getMapper(String mapperString) {
              return GSON.fromJson(mapperString, Mapper.class);
            }
          }
      );
    }

    if (UtilMapper.getDefaultMapperListSerializer() == null) {
      UtilMapper.initDefaultMapperListSerializer(
          new MapperListSerializer() {
            @Override
            public String getMapperListString(MapperList mapper) {
              return GSON.toJson(mapper, MapperList.class);
            }
          }
      );
    }

    if (UtilMapper.getDefaultMapperListDeserializer() == null) {
      UtilMapper.initDefaultMapperListDeserializer(
          new MapperListDeserializer() {
            @Override
            public MapperList getMapperList(String mapperString) {
              return GSON.fromJson(mapperString, MapperList.class);
            }
          }
      );
    }

    return GSON;
  }

}
