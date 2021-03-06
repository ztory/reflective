package com.ztory.lib.reflective;

import com.google.gson.Gson;
import com.ztory.lib.reflective.gson.map.Mapper;
import com.ztory.lib.reflective.tst.ImplTstBase;
import com.ztory.lib.reflective.tst.TstBase;
import com.ztory.lib.reflective.tst.TstNestOne;
import com.ztory.lib.reflective.tst.TstNestTre;
import com.ztory.lib.reflective.tst.TstNestTwo;
import com.ztory.lib.reflective.tst.faulty.FaultyBase;
import java.util.List;
import java.util.Map;
import junit.framework.TestCase;

/**
 * Created by jonruna on 2017-10-10.
 */
public class ReflectiveTestNestGSON extends TestCase {

  private static final Gson GSON = new Gson();

  private static final String
      JSON_STRING_1 = "{\"title\":\"hej\",\"count\":3,\"nestOneList\":[{\"name\":\"tjo\",\"size\":12,\"nestTwo\":{\"status\":\"fritt\",\"counter\":44,\"nestTreList\":[{\"label\":\"frittZ\",\"attempts\":445,\"roles\":[\"admin\",\"baller\"]},{\"label\":\"frittzo\",\"attempts\":46,\"roles\":[\"admin\",\"baller\"]}]}},{\"name\":\"tjolahöpp\",\"size\":129,\"nestTwo\":{\"status\":\"fritthöpp\",\"counter\":449,\"nestTreList\":[{\"label\":\"frittZhöpp\",\"attempts\":4459,\"roles\":[\"admin\",\"baller\"]},{\"label\":\"frittzop\",\"attempts\":469,\"roles\":[\"admin\",\"baller\"]}]}}]}",
      JSON_STRING_2 = "{\"name\":\"jonny\",\"phoneNumber\":\"+46777112233\",\"id\":123,\"faultyInnerList\":[{\"title\":\"Tjenna!X\",\"message\":\"Vad göres?X\",\"messageCount\":31,\"faultyInnerInner\":{\"label\":\"some_cool_labelX\"}},{\"title\":\"Tjenna!XX\",\"message\":\"Vad göres?XX\",\"messageCount\":32,\"faultyInnerInner\":{\"label\":\"some_cool_labelXX\"}},{\"title\":\"Tjenna!XXX\",\"message\":\"Vad göres?XXX\",\"messageCount\":33,\"faultyInnerInner\":{\"label\":\"some_cool_labelXXX\"}},{\"title\":\"Tjenna!XXXX\",\"message\":\"Vad göres?XXXX\",\"messageCount\":34,\"faultyInnerInner\":{\"label\":\"some_cool_labelXXXX\"}}]}";

  //TODO TEST PERFORMANCE OF NEW REQUIRED FUNCTIOANLITY !!!!

  public void testFaultyBaseInterface() throws Exception {
    FaultyBase faulty = GSON.fromJson(JSON_STRING_2, Mapper.class).toReflective(FaultyBase.class);
    boolean threwException = false;
    try {
      Reflective.checkReflectiveRequired(FaultyBase.class, faulty);
    } catch (ReflectiveRequiredException e) {
      assertEquals("ReflectiveRequired method getMissingRequiredString returned null!", e.getMessage());
      threwException = true;
    }
    assertTrue(threwException);
  }

  public void testNestedRequiredFieldsThousand() throws Exception {
    TstBase tstBase = GSON.fromJson(JSON_STRING_1, Mapper.class).toReflective(TstBase.class);
    for (int i = 0; i < 1000; i++) {
      Reflective.checkReflectiveRequired(TstBase.class, tstBase);
    }
  }

  public void testNestedRequiredFields() throws Exception {
    TstBase tstBase = GSON.fromJson(JSON_STRING_1, Mapper.class).toReflective(TstBase.class);
    validateTstBase(tstBase);
  }

  public void testReflectiveTypedReturn() throws Exception {
    Mapper mapper = GSON.fromJson(JSON_STRING_1, Mapper.class);
    TstBase tstBase = mapper.toReflective(TstBase.class);
    validateTstBase(tstBase);
  }

  public void testReflectiveTypedListReturn() throws Exception {
    TstBase tstBase = new ImplTstBase(GSON.fromJson(JSON_STRING_1, Mapper.class));
    validateTstBase(tstBase);
    Mapper tstNestOneMapper = new Mapper(tstBase.getNestOneList().get(0).get_reflectiveMapBacked());
    assertNotNull(tstNestOneMapper);
    assertEquals(3, tstNestOneMapper.size());

    TstNestOne tstNestOne = tstNestOneMapper.toReflective(TstNestOne.class);
    assertNotNull(tstNestOne);
    assertNotNull(tstNestOne.getNestTwo());
    assertNotNull(tstNestOne.getNestTwo().getNestTreList());
    TstNestTre tstNestTre = tstNestOne.getNestTwo().getNestTreList().get(0);
    assertNotNull(tstNestTre);
    assertEquals("frittZ", tstNestTre.getLabel());
    assertEquals(Integer.valueOf(445), tstNestTre.getAttempts());
  }

  public void testReflectiveTstNestOneTypedReturnMethod() throws Exception {
    TstBase tstBase = new ImplTstBase(GSON.fromJson(JSON_STRING_1, Mapper.class));
    validateTstBase(tstBase);
    Mapper tstNestOneMapper = new Mapper(tstBase.getNestOneList().get(0).get_reflectiveMapBacked());
    assertNotNull(tstNestOneMapper);
    assertEquals(3, tstNestOneMapper.size());

    TstNestOne tstNestOne = tstNestOneMapper.toReflective(TstNestOne.class);
    assertNotNull(tstNestOne);
    assertNotNull(tstNestOne.getNestTwo());
    assertEquals("fritt", tstNestOne.getNestTwo().getStatus());
    assertEquals(Integer.valueOf(44), tstNestOne.getNestTwo().getCounter());
  }

  public void testImplTstBase() throws Exception {
    TstBase tstBase = new ImplTstBase(GSON.fromJson(JSON_STRING_1, Mapper.class));
    validateTstBase(tstBase);
  }

  private void validateTstBase(TstBase tstBase) throws Exception {
    assertNotNull(tstBase);
    assertEquals("hej", tstBase.getTitle());
    assertEquals(Integer.valueOf(3), tstBase.getCount());
    assertNotNull(tstBase.getNestOneList());
    assertEquals(2, tstBase.getNestOneList().size());
    for (int i = 0; i < tstBase.getNestOneList().size(); i++) {
      TstNestOne tstNestOne = tstBase.getNestOneList().get(i);
      assertNotNull(tstNestOne);
      if (i == 0) {
        assertEquals("tjo", tstNestOne.getName());
        assertEquals(Integer.valueOf(12), tstNestOne.getSize());

        TstNestTwo tstNestTwo = tstNestOne.getNestTwo();
        assertNotNull(tstNestTwo);
        assertEquals("fritt", tstNestTwo.getStatus());
        assertEquals(Integer.valueOf(44), tstNestTwo.getCounter());

        assertNotNull(tstNestTwo.getNestTreList());
        assertEquals(2, tstNestTwo.getNestTreList().size());
        for (int i2 = 0; i2 < tstNestTwo.getNestTreList().size(); i2++) {
          TstNestTre tstNestTre = tstNestTwo.getNestTreList().get(i2);
          assertNotNull(tstNestTre);
          if (i2 == 0) {
            assertEquals("frittZ", tstNestTre.getLabel());
            assertEquals(Integer.valueOf(445), tstNestTre.getAttempts());
            assertEquals("admin", tstNestTre.getRoles().get(0));
            assertEquals("baller", tstNestTre.getRoles().get(1));
          } else if (i2 == 1) {
            assertEquals("frittzo", tstNestTre.getLabel());
            assertEquals(Integer.valueOf(46), tstNestTre.getAttempts());
            assertEquals("admin", tstNestTre.getRoles().get(0));
            assertEquals("baller", tstNestTre.getRoles().get(1));
          }
        }
      } else if (i == 1) {
        assertEquals("tjolahöpp", tstNestOne.getName());
        assertEquals(Integer.valueOf(129), tstNestOne.getSize());

        TstNestTwo tstNestTwo = tstNestOne.getNestTwo();
        assertNotNull(tstNestTwo);
        assertEquals("fritthöpp", tstNestTwo.getStatus());
        assertEquals(Integer.valueOf(449), tstNestTwo.getCounter());

        assertNotNull(tstNestTwo.getNestTreList());
        assertEquals(2, tstNestTwo.getNestTreList().size());
        for (int i2 = 0; i2 < tstNestTwo.getNestTreList().size(); i2++) {
          TstNestTre tstNestTre = tstNestTwo.getNestTreList().get(i2);
          assertNotNull(tstNestTre);
          if (i2 == 0) {
            assertEquals("frittZhöpp", tstNestTre.getLabel());
            assertEquals(Integer.valueOf(4459), tstNestTre.getAttempts());
            assertEquals("admin", tstNestTre.getRoles().get(0));
            assertEquals("baller", tstNestTre.getRoles().get(1));
          } else if (i2 == 1) {
            assertEquals("frittzop", tstNestTre.getLabel());
            assertEquals(Integer.valueOf(469), tstNestTre.getAttempts());
            assertEquals("admin", tstNestTre.getRoles().get(0));
            assertEquals("baller", tstNestTre.getRoles().get(1));
          }
        }
      }
    }
    Reflective.checkReflectiveRequired(TstBase.class, tstBase);
  }

  public void testMapperReflectiveGet() throws Exception {
    final Mapper mapper = GSON.fromJson(JSON_STRING_1, Mapper.class);
    TstNestOne tstNestOne = mapper.getReflective(TstNestOne.class, "nestOneList", 0);
    assertNotNull(tstNestOne);
    assertEquals("tjo", tstNestOne.getName());
    assertEquals(mapper.getVal(Object.class, "nestOneList", 0), tstNestOne.get_reflectiveMapBacked());

    List<TstNestOne> tstNestOneList = mapper.getReflectiveList(TstNestOne.class, "nestOneList");
    assertNotNull(tstNestOneList);
    assertEquals("tjo", tstNestOneList.get(0).getName());
    assertEquals(tstNestOne.getName(), tstNestOneList.get(0).getName());
  }

  public void testGetReflectiveMapBacked() throws Exception {
    Mapper mapper = GSON.fromJson(JSON_STRING_1, Mapper.class);
    assertEquals("hej", mapper.getString("title"));
    TstBase tstBase = Reflective.getReflectiveInstance(
        TstBase.class,
        mapper,
        Reflective.CAMELCASE,
        null
    );
    assertEquals(mapper.getString("title"), tstBase.getTitle());
    assertEquals(mapper, tstBase.get_reflectiveMapBacked());

    assertTrue(List.class.isAssignableFrom(mapper.get("nestOneList").getClass()));
    assertFalse(mapper.get("nestOneList").getClass().isAssignableFrom(List.class));

    TstNestOne tstNestOne = Reflective.getReflectiveInstance(
        TstNestOne.class,
        mapper.getVal(Map.class, "nestOneList", 0),
        Reflective.CAMELCASE,
        null
    );
    assertNotNull(tstNestOne);
    assertEquals(mapper.getString("nestOneList", 0, "name"), tstNestOne.getName());
  }

}
