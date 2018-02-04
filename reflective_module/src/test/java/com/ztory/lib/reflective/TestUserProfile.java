package com.ztory.lib.reflective;

import com.google.gson.Gson;
import com.ztory.lib.reflective.gson.map.Mapper;
import com.ztory.lib.reflective.gson.map.MapperList;
import java.util.ArrayList;
import java.util.Map.Entry;
import junit.framework.TestCase;

/**
 * Created by jonruna on 2018-02-03.
 */
public class TestUserProfile extends TestCase {

  private static final Gson GSON = TestGsonAndAppInitializer.init();

  @Override
  protected void setUp() throws Exception {
    super.setUp();
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testUserProfileGeneral() throws Exception {

    Mapper mapper = Mapper.obj(
        "id", "abc123",
        "email", "jonny@example.com",
        "firstName", "jonny",
        "lastName", "rogers",
        "rating", 87.0,// input must be double since we will compare to GSON-serialized data below
        "activityScore", 9.6729,
        "friends", Mapper.array(
            Mapper.obj(
                "id", "abc1234",
                "email", "jonny2@example.com",
                "firstName", "jonny2",
                "lastName", "rogers3",
                "rating", 88.0,// input must be double since we will compare to GSON-serialized data below
                "activityScore", 8.6729
            ),
            Mapper.obj(
                "id", "abc12345",
                "email", "jonny3@example.com",
                "firstName", "jonny3",
                "lastName", "rogers3",
                "rating", 89.0,// input must be double since we will compare to GSON-serialized data below
                "activityScore", 7.6729
            )
        )
    );
    UserProfile userProfile = mapper.toReflectiveValidated(UserProfile.class);
    assertEquals("abc123", userProfile.getId());
    assertEquals("jonny", userProfile.getFirstName());

    assertEquals("abc1234", userProfile.getFriends().get(0).getId());
    assertEquals("jonny2", userProfile.getFriends().get(0).getFirstName());

    assertEquals("abc12345", userProfile.getFriends().get(1).getId());
    assertEquals("jonny3", userProfile.getFriends().get(1).getFirstName());

    Mapper sameMapperInstance = Mapper.fromReflective(userProfile);
    assertTrue(mapper == sameMapperInstance);
    assertTrue(mapper.equals(sameMapperInstance));

    String jsonString = mapper.toString();
    Mapper mapperFromJsonString = Mapper.fromString(jsonString);
    assertEquals(mapper.entrySet().size(), mapperFromJsonString.entrySet().size());
    assertEquals(mapper.toString(), mapperFromJsonString.toString());
    assertEquals(mapper.getClass(), mapperFromJsonString.getClass());

    ArrayList<Entry<String, Object>> mapperEntrySet = new ArrayList<>(mapper.entrySet());
    ArrayList<Entry<String, Object>> mapperJsonStringEntrySet = new ArrayList<>(mapperFromJsonString.entrySet());
    assertEquals(mapperEntrySet.get(0), mapperJsonStringEntrySet.get(0));
    assertEquals(mapperEntrySet.get(1), mapperJsonStringEntrySet.get(1));
    assertEquals(mapperEntrySet.get(2), mapperJsonStringEntrySet.get(2));
    assertEquals(mapperEntrySet.get(3), mapperJsonStringEntrySet.get(3));
    assertEquals(mapperEntrySet.get(4), mapperJsonStringEntrySet.get(4));
    assertEquals(mapperEntrySet.get(5), mapperJsonStringEntrySet.get(5));
    assertEquals(mapperEntrySet.get(6), mapperJsonStringEntrySet.get(6));

    assertEquals(mapperEntrySet, mapperJsonStringEntrySet);
    assertEquals(mapper, mapperFromJsonString);
    assertTrue(mapper.equals(mapperFromJsonString));// equals == true
    assertTrue(mapper != mapperFromJsonString);// different instances
  }

  public void testUserProfileList() throws Exception {
    MapperList mapperList = new MapperList();
    mapperList.addAll(
        Mapper.array(
            Mapper.obj(
                "id", "abc1234",
                "email", "jonny2@example.com",
                "firstName", "jonny2",
                "lastName", "rogers3",
                "rating", 88,
                "activityScore", 8.6729
            ),
            Mapper.obj(
                "id", "abc12345",
                "email", "jonny3@example.com",
                "firstName", "jonny3",
                "lastName", "rogers3",
                "rating", 89,
                "activityScore", 7.6729
            )
        )
    );
    mapperList.toReflectiveValidated(UserProfile.class);
  }

}
