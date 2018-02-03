package com.ztory.lib.reflective;

import com.google.gson.Gson;
import com.ztory.lib.reflective.gson.map.Mapper;
import com.ztory.lib.reflective.gson.map.MapperList;
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

    //TODO CREATE TEST CASE THAT CAN BE USED IN README AS EXAMPLE CODE !!!!

    Mapper mapper = Mapper.obj(
        "id", "abc123",
        "email", "jonny@example.com",
        "firstName", "jonny",
        "lastName", "rogers",
        "rating", 87,
        "activityScore", 9.6729,
        "friends", Mapper.array(
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
    mapper.toReflectiveValidated(UserProfile.class);
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
