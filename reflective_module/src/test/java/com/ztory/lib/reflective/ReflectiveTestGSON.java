package com.ztory.lib.reflective;

import static org.junit.Assert.assertNotEquals;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ztory.lib.reflective.gson.map.Mapper;

import com.ztory.lib.reflective.gson.map.MapperList;
import java.util.HashMap;
import junit.framework.TestCase;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by jonruna on 26/09/16.
 */
public class ReflectiveTestGSON extends TestCase {

    private static final Gson GSON = new Gson();

    private static final String JSON_STRING_1 = "{\"key0\": true,\"key1\": \"jonny\",\"key2\": \"anny\",\"obj1\": {\"obj1_one\": {\"mega_nest_1\": {\"mega_map\": {\"one\": 1, \"two\": 2}, \"mega_array\": [4, 8, 12], \"mega_key1\": \"balleriffico\"}, \"nesto1\": \"tjolahopp\"}, \"obj_key1\": \"ork\",\"obj_key2\": 48, \"obj_array\": [\"one\", 2, 3], \"obj_array_two\": [4, 5, 6]}}";
    private static final String JSON_STRING_ARRAY = "[{\"_id\":\"dsahudasi4234444\",\"title\":\"hej1\",\"description\":\"1tjenare där!\",\"nest_list\":[{\"nest_string\":\"tjo\",\"nest_number\":1244.3432,\"nestX\":{\"nestX_list\":[{\"deepz\":\"is it not?!\"},{\"deepz2\":\"is it not mate?!\"}]}}]},{\"_id\":\"dsahudasi4235555\",\"title\":\"hej2\",\"description\":\"2tjenare där!\"},{\"_id\":\"dsahudasi42323299\",\"title\":\"hej3\",\"description\":\"3tjenare där!\"},{\"_id\":\"dsahudasi423666\",\"title\":\"hej4\",\"description\":\"4tjenare där!\"}]";

    private static final MapperList sMapperList = GSON.fromJson(JSON_STRING_ARRAY, MapperList.class);

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

//    public void testMapperListNested1000000() throws Exception {
//        for (int i = 0; i < 1000000; i++) { testMapperListNested(); }
//    }

    public void testMapperNestedPut() throws Exception {
        MapperList mapperList = GSON.fromJson(JSON_STRING_ARRAY, MapperList.class);
        assertEquals(4, mapperList.size());
        Mapper mapper = mapperList.get(0);
        assertEquals("tjo", mapper.getVal(String.class, "nest_list", 0, "nest_string"));
        String jsonString = GSON.toJson(mapperList, MapperList.class);
        assertEquals(JSON_STRING_ARRAY, jsonString);

        mapper.putValue("putValz2000", false, String.class, "nest_list", -1, "nest_string_new");
        assertEquals("tjo", mapper.getVal(String.class, "nest_list", 0, "nest_string"));
        assertEquals("putValz2000", mapper.getVal(String.class, "nest_list", -1, "nest_string_new"));

        assertEquals(1, mapper.getList(Object.class, "nest_list").size());
        mapper.putValue("putListItemEnd", false, String.class, "nest_list", -1);
        assertEquals("putListItemEnd", mapper.getVal(String.class, "nest_list", -1));
        assertEquals(2, mapper.getList(Object.class, "nest_list").size());
        assertEquals("putValz2000", mapper.getVal(String.class, "nest_list", -2, "nest_string_new"));
        mapper.putValue("putListItemEnd-1", false, String.class, "nest_list", -2);
        assertEquals("putListItemEnd-1", mapper.getVal(String.class, "nest_list", -2));
        assertEquals(3, mapper.getList(Object.class, "nest_list").size());
        assertEquals(mapper.getVal(String.class, "nest_list", 1), mapper.getVal(String.class, "nest_list", -2));
        mapper.putValue("putListItemStart", false, String.class, "nest_list", 0);
        assertEquals("putListItemStart", mapper.getVal(String.class, "nest_list", 0));
        assertEquals(4, mapper.getList(Object.class, "nest_list").size());
        assertEquals(mapper.getVal(String.class, "nest_list", 2), mapper.getVal(String.class, "nest_list", -2));
        Object valIndex1 = mapper.getVal(String.class, "nest_list", 1);
        assertEquals(valIndex1, mapper.putValue("newIndex1Value", false, String.class, "nest_list", 1));
        assertEquals("newIndex1Value", mapper.getVal(String.class, "nest_list", 1));
        assertNotEquals(valIndex1, mapper.getVal(String.class, "nest_list", 1));
        assertEquals(
            mapper.getList(Object.class, "nest_list").get(0),
            mapper.getVal(String.class, "nest_list", 0)
        );
        assertEquals(
            mapper.getList(Object.class, "nest_list").get(2),
            mapper.getVal(String.class, "nest_list", 2)
        );
        assertEquals(
            mapper.getList(Object.class, "nest_list").get(mapper.getList(Object.class, "nest_list").size() - 2),
            mapper.getVal(String.class, "nest_list", -2)
        );
        assertEquals(
            mapper.getList(Object.class, "nest_list").get(mapper.getList(Object.class, "nest_list").size() - 1),
            mapper.getVal(String.class, "nest_list", -1)
        );
        assertNull(mapper.optVal(String.class, "nest_list", -100));
        assertNull(mapper.optString("nest_list", -100));
        assertNull(mapper.optVal(String.class, "nest_list", 100));
        assertNull(mapper.optString("nest_list", 100));

        assertNotNull(mapper.getVal(String.class, "nest_list", 1));
        assertNotNull(mapper.getString("nest_list", 1));
        assertNotNull(mapper.optString("nest_list", 1));
        assertEquals(mapper.getVal(String.class, "nest_list", 1), mapper.getString("nest_list", 1));
        assertEquals(mapper.getString("nest_list", 1), mapper.optString("nest_list", 1));

        assertNotNull(mapper.optString("nest_list", 0));
        assertNotNull(mapper.optString("nest_list", -1));

        String jsonStringModified = GSON.toJson(mapperList, MapperList.class);
        assertNotEquals(JSON_STRING_ARRAY, jsonStringModified);
    }

    public void testMapperListNested() throws Exception {

        assertEquals(4, sMapperList.size());
        Mapper mapper = sMapperList.get(0);
        assertEquals("tjo", mapper.getVal(String.class, "nest_list", 0, "nest_string"));
        assertEquals(1244.3432, mapper.getVal(String.class, "nest_list", 0, "nest_number"));

        assertEquals(
            "is it not?!",
            mapper.getVal(String.class, "nest_list", 0, "nestX", "nestX_list", 0, "deepz")
        );

        assertEquals(
            "is it not mate?!",
            mapper.optString("nest_list", 0, "nestX", "nestX_list", 1, "deepz2")
        );
    }

    public void testMapperList() throws Exception {
        MapperList mapperList = new Gson().fromJson(JSON_STRING_ARRAY, MapperList.class);
        assertEquals(4, mapperList.size());
        Mapper mapper = mapperList.get(0);
        assertEquals("tjo", mapper.getList(Map.class, "nest_list").get(0).get("nest_string"));
        assertEquals(1244.3432, mapper.getList(Map.class, "nest_list").get(0).get("nest_number"));
    }

    public void testMapNumberValueOptStringGSON() throws Exception {
        Mapper root = new Gson().fromJson(JSON_STRING_1, Mapper.class);
        String oneValue = root.optString("obj1", "obj_key2");
        assertNull(oneValue);
    }

    public void testMapStringValueOptNumberGSON() throws Exception {
        Mapper root = new Gson().fromJson(JSON_STRING_1, Mapper.class);
        Number oneValue = root.optNumber("obj1", "obj1_one", "mega_nest_1", "mega_key1");
        assertNull(oneValue);
    }

    public void testMapNumberValueGSON() throws Exception {
        Mapper root = new Gson().fromJson(JSON_STRING_1, Mapper.class);
        Number oneValue = root.getNumber("obj1", "obj_key2");
        assertEquals(48.0, oneValue);
        Double dblValue = root.optVal(Double.class, "obj1", "obj_key2");
        assertNotNull(dblValue);
    }

    public void testMapStringValueGSON() throws Exception {
        Mapper root = new Gson().fromJson(JSON_STRING_1, Mapper.class);
        String oneValue = root.getString("obj1", "obj1_one", "mega_nest_1", "mega_key1");
        assertEquals("balleriffico", oneValue);
    }

    public void testSubclassValueGSON() throws Exception {
        DummyClassSubclass theSubclass = new DummyClassSubclass();
        theSubclass.dummyString = "Hello!";
        theSubclass.dummyInteger = 99;
        Mapper theMapper = new Mapper();
        theMapper.put("dummy", theSubclass);
        DummyClassBase theBaseClass = theMapper.optVal(DummyClassBase.class, "dummy");
        assertNotNull(theBaseClass);
        assertEquals(theBaseClass.dummyString, theSubclass.dummyString);
    }

    public void testBooleanValueGSON() throws Exception {
        Mapper root = new Gson().fromJson(JSON_STRING_1, Mapper.class);
        assertNotNull(root.getBoolean("key0"));
        assertNull(root.optBoolean("key1"));
    }

    public void testMapValueGSON() throws Exception {
        Mapper root = new Gson().fromJson(JSON_STRING_1, Mapper.class);
        String oneStringValue = root.optVal(String.class, "obj1", "obj1_one", "mega_nest_1", "mega_map", "one");
        assertNull(oneStringValue);
        Number oneValue = root.getVal(Number.class, "obj1", "obj1_one", "mega_nest_1", "mega_map", "one");
        assertEquals(1.0, oneValue);
    }

    public void testMapNumberMapGSON() throws Exception {
        Gson gson = new Gson();
        Mapper root = gson.fromJson(JSON_STRING_1, Mapper.class);
        Map<String, Number> numberMap = root.getMap(Number.class, "obj1", "obj1_one", "mega_nest_1", "mega_map");
        assertEquals(1.0, numberMap.get("one"));
        assertEquals(2.0, numberMap.get("two"));
    }

    public void testMapNestedListGSON() throws Exception {
        Mapper root = new Gson().fromJson(JSON_STRING_1, Mapper.class);
        assertEquals(4.0, root.getList(Object.class, "obj1", "obj1_one", "mega_nest_1", "mega_array").get(0));
        assertEquals(8.0, root.getList(Number.class, "obj1", "obj1_one", "mega_nest_1", "mega_array").get(1));
        assertEquals(12.0, root.getList(Number.class, "obj1", "obj1_one", "mega_nest_1", "mega_array").get(2));

        List<Number> numberList = root.getList(Number.class, "obj1", "obj1_one", "mega_nest_1", "mega_array");
        assertEquals(4.0, numberList.get(0));
        assertEquals(8.0, numberList.get(1));
        assertEquals(12.0, numberList.get(2));

        List<String> stringList = root.getList(String.class, "obj1", "obj1_one", "mega_nest_1", "mega_array");
        assertNull(stringList);
    }

    public void testMapGSON() throws Exception {
        Gson gson = new Gson();
        Mapper root = gson.fromJson(JSON_STRING_1, Mapper.class);
        assertEquals("jonny", root.get("key1"));
        assertEquals("ork", root.getMap(Object.class, "obj1").get("obj_key1"));
        assertEquals("tjolahopp", root.getMap(Object.class, "obj1", "obj1_one").get("nesto1"));
        assertEquals("balleriffico", root.getMap(Object.class, "obj1", "obj1_one", "mega_nest_1").get("mega_key1"));

    }

    public void testMapGSONtwo() throws Exception {
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY);
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        //Map<String, Object> myMap = gson.fromJson("{'k1':'apple','k2':'orange'}", type);
        Map<String, Object> myMap = gson.fromJson(JSON_STRING_1, type);
        assertEquals("jonny", myMap.get("key1"));
        assertEquals("anny", myMap.get("key2"));
        assertTrue(myMap.get("obj1") instanceof Map);
        Map<String, Object> nestedMap = (Map<String, Object>) myMap.get("obj1");
        assertEquals("ork", nestedMap.get("obj_key1"));
        assertTrue(nestedMap.get("obj_array") instanceof List);
        List<Object> objArray = (List<Object>) nestedMap.get("obj_array");
        assertTrue(objArray.size() == 3);
        assertEquals("one", objArray.get(0));
        assertEquals(2.0, objArray.get(1));
        assertEquals(3.0, objArray.get(2));

        assertTrue(nestedMap.get("obj_array_two") instanceof List);
        List<Number> objArrayTwo = (List<Number>) nestedMap.get("obj_array_two");
        assertEquals(4.0, objArrayTwo.get(0));
        assertEquals(5.0, objArrayTwo.get(1));
        assertEquals(6.0, objArrayTwo.get(2));
    }

    public void testMapTypedGSON() throws Exception {
        Mapper mapper = new Mapper();
        Map<String, Boolean> stringBoolMap = new HashMap<>();
        stringBoolMap.put("1", true);
        stringBoolMap.put("2", false);
        mapper.put("stringBoolMap", stringBoolMap);
        Map<String, Number> stringNumberMap = mapper.getMap(Number.class, "stringBoolMap");
        assertNull(stringNumberMap);
        Map<String, Boolean> GETstringBoolMap = mapper.getMap(Boolean.class, "stringBoolMap");
        assertTrue(GETstringBoolMap.get("1"));
        assertFalse(GETstringBoolMap.get("2"));
    }

}
