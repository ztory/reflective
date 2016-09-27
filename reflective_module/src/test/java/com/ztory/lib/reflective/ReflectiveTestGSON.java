package com.ztory.lib.reflective;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ztory.lib.reflective.gson.map.Mapper;

import junit.framework.TestCase;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by jonruna on 26/09/16.
 */
public class ReflectiveTestGSON extends TestCase {

    private static final String JSON_STRING_1 = "{\"key1\": \"jonny\",\"key2\": \"anny\",\"obj1\": {\"obj1_one\": {\"mega_nest_1\": {\"mega_map\": {\"one\": 1, \"two\": 2}, \"mega_array\": [4, 8, 12], \"mega_key1\": \"balleriffico\"}, \"nesto1\": \"tjolahopp\"}, \"obj_key1\": \"ork\",\"obj_key2\": 48, \"obj_array\": [\"one\", 2, 3], \"obj_array_two\": [4, 5, 6]}}";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testMapNumberValueOptStringGSON() throws Exception {
        Mapper root = Mapper.json(JSON_STRING_1);
        String oneValue = root.optString("obj1", "obj_key2");
        assertNull(oneValue);
    }

    public void testMapStringValueOptNumberGSON() throws Exception {
        Mapper root = Mapper.json(JSON_STRING_1);
        Number oneValue = root.optNumber("obj1", "obj1_one", "mega_nest_1", "mega_key1");
        assertNull(oneValue);
    }

    public void testMapNumberValueGSON() throws Exception {
        Mapper root = Mapper.json(JSON_STRING_1);
        Number oneValue = root.getNumber("obj1", "obj_key2");
        assertEquals(48.0, oneValue);
    }

    public void testMapStringValueGSON() throws Exception {
        Mapper root = Mapper.json(JSON_STRING_1);
        String oneValue = root.getString("obj1", "obj1_one", "mega_nest_1", "mega_key1");
        assertEquals("balleriffico", oneValue);
    }

    public void testMapValueGSON() throws Exception {
        Mapper root = Mapper.json(JSON_STRING_1);
        Number oneValue = root.getValue("obj1", "obj1_one", "mega_nest_1", "mega_map", "one");
        assertEquals(1.0, oneValue);
    }

    public void testMapNumberMapGSON() throws Exception {
        Gson gson = new Gson();
        Mapper root = gson.fromJson(JSON_STRING_1, Mapper.class);
        Map<String, Number> numberMap = root.getMap("obj1", "obj1_one", "mega_nest_1", "mega_map");
        assertEquals(1.0, numberMap.get("one"));
        assertEquals(2.0, numberMap.get("two"));
    }

    public void testMapNestedListGSON() throws Exception {
        Mapper root = Mapper.json(new Gson(), JSON_STRING_1);
        assertEquals(4.0, root.getList("obj1", "obj1_one", "mega_nest_1", "mega_array").get(0));
        assertEquals(8.0, root.getList("obj1", "obj1_one", "mega_nest_1", "mega_array").get(1));
        assertEquals(12.0, root.getList("obj1", "obj1_one", "mega_nest_1", "mega_array").get(2));

        List<Number> numberList = root.getList("obj1", "obj1_one", "mega_nest_1", "mega_array");
        assertEquals(4.0, numberList.get(0));
        assertEquals(8.0, numberList.get(1));
        assertEquals(12.0, numberList.get(2));
    }

    public void testMapGSON() throws Exception {
        Gson gson = new Gson();
        Mapper root = gson.fromJson(JSON_STRING_1, Mapper.class);
        assertEquals("jonny", root.get("key1"));
        assertEquals("ork", root.getMap("obj1").get("obj_key1"));
        assertEquals("tjolahopp", root.getMap("obj1", "obj1_one").get("nesto1"));
        assertEquals("balleriffico", root.getMap("obj1", "obj1_one", "mega_nest_1").get("mega_key1"));

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

}
