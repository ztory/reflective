package com.ztory.lib.reflective;

import android.graphics.Point;

import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Tests related to Reflective functionality.
 * Created by jonruna on 15/04/16.
 */
public class ReflectiveTest extends TestCase {

    private static final int VAL_ID = 999;
    private static final boolean VAL_FOR_KIDS = true;
    private static final String VAL_NAME = "johnny baLLo";

    private static final Map<String, Object>
            sProfileMap = new HashMap<>(5),
            sCamelCaseProfileMap = new HashMap<>(5);
    private static final BizTestProfileCustom sBizProfile;

    static {
        try {

            sProfileMap.put("id", VAL_ID);
            sProfileMap.put("for_kids", VAL_FOR_KIDS);
            sProfileMap.put("name", VAL_NAME);
            sProfileMap.put("avatar_url", "www.test.com/test_image.jpg");
            sProfileMap.put("e-post", "en_svensk_epost@example.com");

            sCamelCaseProfileMap.put("ProfileId", VAL_ID);
            sCamelCaseProfileMap.put("ProfileForKids", VAL_FOR_KIDS);
            sCamelCaseProfileMap.put("ProfileName", VAL_NAME);
            sCamelCaseProfileMap.put("ProfileAvatarUrl", "www.test.com/test_image.jpg");

            sBizProfile = Reflective.getReflectiveInstance(
                    BizTestProfileCustom.class,
                    sProfileMap,
                    Reflective.LOWERCASE_UNDERSCORE,
                    null
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testBizTestJSONObjectCamelCaseMulti() throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("veryLongKeyNameForStringValue", "veryLongValue");
        jsonObject.put("name", "Jonny");
        jsonObject.put("title", "BaLLeR");
        jsonObject.put("description", "Very very kewl!!!!");
        jsonObject.put("baller", true);
        jsonObject.put("positionX", 123.456);
        jsonObject.put("positionY", 123.456);
        jsonObject.put("positionW", 123.456);
        jsonObject.put("positionH", 123.456);

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);
        jsonArray.put(jsonObject);
        jsonArray.put(jsonObject);
        jsonArray.put(jsonObject);
        jsonArray.put(jsonObject);
        jsonArray.put(jsonObject);
        jsonArray.put(jsonObject);
        jsonArray.put(jsonObject);

        List<BizTestJSONObject> bizTestJSONlist = Reflective.getMultiReflectiveInstances(
                BizTestJSONObject.class,
                jsonArray,
                Reflective.CAMELCASE,
                null
        );

        Reflective.checkReflectiveRequired(BizTestJSONObject.class, bizTestJSONlist);

        assertEquals(bizTestJSONlist.get(0).getName(), jsonObject.getString("name"));
        assertEquals(bizTestJSONlist.get(0).getTitle(), jsonObject.getString("title"));
        assertEquals(bizTestJSONlist.get(0).getDescription(), jsonObject.getString("description"));
        assertTrue(bizTestJSONlist.get(0).isBaller());
        assertEquals(
                bizTestJSONlist.get(0).getVeryLongKeyNameForStringValue(),
                jsonObject.getString("veryLongKeyNameForStringValue")
        );
        assertEquals(8, bizTestJSONlist.size());
    }

    public void testBizTestJSONObjectCamelCaseKeys2() throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("veryLongKeyNameForStringValue", "veryLongValue");
        jsonObject.put("name", "Jonny");
        jsonObject.put("title", "BaLLeR");
        jsonObject.put("description", "Very very kewl!!!!");
        jsonObject.put("baller", true);
        jsonObject.put("positionX", 123.456);
        jsonObject.put("positionY", 123.456);
        jsonObject.put("positionW", 123.456);
        jsonObject.put("positionH", 123.456);

        BizTestJSONObject bizTestJSON = Reflective.getReflectiveInstance(
                BizTestJSONObject.class,
                jsonObject,
                Reflective.CAMELCASE,
                null
        );
//        BizTestJSONObject bizTestJSON = Reflective.getReflectiveInstance(
//                BizTestJSONObject.class,
//                new ReflectiveInvocationHandler(
//                        Reflective.getReflectiveNamespaceSafe(BizTestJSONObject.class),
//                        jsonObject,
//                        Reflective.CAMELCASE,
//                        null//theCustomKeyMap
//                )
//        );

        Reflective.checkReflectiveRequired(BizTestJSONObject.class, bizTestJSON);

        assertEquals(bizTestJSON.getName(), jsonObject.getString("name"));
        assertEquals(bizTestJSON.getTitle(), jsonObject.getString("title"));
        assertEquals(bizTestJSON.getDescription(), jsonObject.getString("description"));
        assertTrue(bizTestJSON.isBaller());
        assertEquals(
                bizTestJSON.getVeryLongKeyNameForStringValue(),
                jsonObject.getString("veryLongKeyNameForStringValue")
        );
    }

    public void testBizTestJSONObjectCamelCaseKeys() throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("veryLongKeyNameForStringValue", "veryLongValue");
        jsonObject.put("name", "Jonny");
        jsonObject.put("title", "BaLLeR");
        jsonObject.put("description", "Very very kewl!!!!");
        jsonObject.put("baller", true);
        jsonObject.put("positionX", 123.456);
        jsonObject.put("positionY", 123.456);
        jsonObject.put("positionW", 123.456);
        jsonObject.put("positionH", 123.456);

        BizTestJSONObject bizTestJSON = Reflective.getReflectiveInstance(
                BizTestJSONObject.class,
                new ReflectiveInvocationHandler(
                        Reflective.getReflectiveNamespaceSafe(BizTestJSONObject.class),
                        jsonObject,
                        Reflective.CAMELCASE,
                        null//theCustomKeyMap
                )
        );

        Reflective.checkReflectiveRequired(BizTestJSONObject.class, bizTestJSON);

        assertEquals(bizTestJSON.getName(), jsonObject.getString("name"));
        assertEquals(bizTestJSON.getTitle(), jsonObject.getString("title"));
        assertEquals(bizTestJSON.getDescription(), jsonObject.getString("description"));
        assertTrue(bizTestJSON.isBaller());
        assertEquals(
                bizTestJSON.getVeryLongKeyNameForStringValue(),
                jsonObject.getString("veryLongKeyNameForStringValue")
        );
    }

    public void testBizTestJSONObject() throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("very_long_key_name_for_string_value", "veryLongValue");
        jsonObject.put("name", "Jonny");
        jsonObject.put("title", "BaLLeR");
        jsonObject.put("description", "Very very kewl!!!!");
        jsonObject.put("baller", true);
        jsonObject.put("position_x", 123.456);
        jsonObject.put("position_y", 123.456);
        jsonObject.put("position_w", 123.456);
        jsonObject.put("position_h", 123.456);

        BizTestJSONObject bizTestJSON = Reflective.getReflectiveInstance(
                BizTestJSONObject.class,
                new ReflectiveInvocationHandler(
                        Reflective.getReflectiveNamespaceSafe(BizTestJSONObject.class),
                        jsonObject,
                        Reflective.LOWERCASE_UNDERSCORE,
                        null//theCustomKeyMap
                )
        );

        Reflective.checkReflectiveRequired(BizTestJSONObject.class, bizTestJSON);

        assertEquals(bizTestJSON.getName(), jsonObject.getString("name"));
        assertEquals(bizTestJSON.getTitle(), jsonObject.getString("title"));
        assertEquals(bizTestJSON.getDescription(), jsonObject.getString("description"));
        assertTrue(bizTestJSON.isBaller());
        assertEquals(
                bizTestJSON.getVeryLongKeyNameForStringValue(),
                jsonObject.getString("very_long_key_name_for_string_value")
        );
    }

    public void testReflectiveFetchClassMethodNamespace() throws Exception {

        ReflectiveNamespace reflectiveNamespace;
        reflectiveNamespace = BizTestProfileCustom.class.getAnnotation(ReflectiveNamespace.class);
        assertNotNull(reflectiveNamespace);

        assertEquals("Profile", reflectiveNamespace.value());
    }

    public void testReflectiveIntegerValue() throws Exception {

        Map<String, Object> theDummyMap = new HashMap<>(1);

        BizDummy bizDummy = Reflective.getReflectiveInstance(
                BizDummy.class,
                theDummyMap,
                Reflective.LOWERCASE_UNDERSCORE,
                null
        );

        assertEquals(null, bizDummy.getInteger());

        Integer dummyInteger = 123;
        theDummyMap.put("integer", dummyInteger);
        assertEquals(dummyInteger, bizDummy.getInteger());

        theDummyMap.put("integer", 1.123d);
        assertEquals(Integer.valueOf(1), bizDummy.getInteger());
    }

    public void testReflectiveSetter() throws Exception {

        Map<String, Object> theDummyMap = new HashMap<>(2);

        BizDummy bizDummy = Reflective.getReflectiveInstance(
                BizDummy.class,
                theDummyMap,
                Reflective.LOWERCASE_UNDERSCORE,
                null
        );

        assertEquals(0, bizDummy.getInt());
        assertEquals(null, bizDummy.getString());

        String testString = "woot this is a test?!";
        bizDummy.setString(testString);
        assertEquals(testString, bizDummy.getString());

        int testInt = 123;
        bizDummy.setInt(123);
        assertEquals(testInt, bizDummy.getInt());
    }

    public void testReflectiveAllLegalReturnTypes() throws Exception {

        Map<String, Object> theDummyMap = new HashMap<>(9);
        theDummyMap.put("int", 4);
        theDummyMap.put("short", (short) 8);
        theDummyMap.put("long", 12);
        theDummyMap.put("float", 16.0f);
        theDummyMap.put("double", 20.0d);
        theDummyMap.put("byte", (byte) 1);
        theDummyMap.put("boolean", true);
        theDummyMap.put("char", 'J');
        theDummyMap.put("void", null);

        BizDummy bizDummy = Reflective.getReflectiveInstance(
                BizDummy.class,
                theDummyMap,
                Reflective.LOWERCASE_UNDERSCORE,
                null
        );

        assertEquals(4, bizDummy.getInt());
        assertEquals((short) 8, bizDummy.getShort());
        assertEquals(12L, bizDummy.getLong());
        assertEquals(16.0f, bizDummy.getFloat());
        assertEquals(20.0d, bizDummy.getDouble());
        assertEquals((byte) 1, bizDummy.getByte());
        assertEquals(true, bizDummy.getBoolean());
        assertEquals('J', bizDummy.getChar());

        bizDummy.getVoid();
    }

    public void testReflectiveAllIllegalReturnTypes() throws Exception {

        Point illegalPointData = new Point(123, 456);

        Map<String, Object> theDummyMap = new HashMap<>(9);
        theDummyMap.put("int", illegalPointData);
        theDummyMap.put("short", illegalPointData);
        theDummyMap.put("long", illegalPointData);
        theDummyMap.put("float", illegalPointData);
        theDummyMap.put("double", illegalPointData);
        theDummyMap.put("byte", illegalPointData);
        theDummyMap.put("boolean", illegalPointData);
        theDummyMap.put("char", illegalPointData);
        theDummyMap.put("void", illegalPointData);

        BizDummy bizDummy = Reflective.getReflectiveInstance(
                BizDummy.class,
                theDummyMap,
                Reflective.LOWERCASE_UNDERSCORE,
                null
        );

        assertEquals(0, bizDummy.getInt());
        assertEquals((short) 0, bizDummy.getShort());
        assertEquals(0L, bizDummy.getLong());
        assertEquals(0.0f, bizDummy.getFloat());
        assertEquals(0.0d, bizDummy.getDouble());
        assertEquals((byte) 0, bizDummy.getByte());
        assertEquals(false, bizDummy.getBoolean());
        assertEquals('\u0000', bizDummy.getChar());

        bizDummy.getVoid();
    }

    public void testReflectiveIllegalReturnTypeDummyClass() throws Exception {

        DummyClassSubclass dummyClassSubclass = new DummyClassSubclass();

        Map<String, Object> theDummyMap = new HashMap<>(1);
        theDummyMap.put("dummy_class", dummyClassSubclass);//subclass of DummyClassBase

        BizDummy bizDummy = Reflective.getReflectiveInstance(
                BizDummy.class,
                theDummyMap,
                Reflective.LOWERCASE_UNDERSCORE,
                null
        );

        assertEquals(dummyClassSubclass, bizDummy.getDummyClass());
    }

    public void testReflectiveIllegalReturnTypeString() throws Exception {

        Map<String, Object> theDummyMap = new HashMap<>(1);
        theDummyMap.put("string", new Point(100, 200));//string-key now has Point-value

        BizDummy bizDummy = Reflective.getReflectiveInstance(
                BizDummy.class,
                theDummyMap,
                Reflective.LOWERCASE_UNDERSCORE,
                null
        );

        assertEquals(null, bizDummy.getString());
    }

    public void testReflectiveIllegalReturnTypeIntFromBoolean() throws Exception {

        Map<String, Object> theDummyMap = new HashMap<>(1);
        theDummyMap.put("int", true);

        BizDummy bizDummy = Reflective.getReflectiveInstance(
                BizDummy.class,
                theDummyMap,
                Reflective.LOWERCASE_UNDERSCORE,
                null
        );

        assertEquals(0, bizDummy.getInt());
    }

    public void testReflectiveIllegalReturnTypeBooleanFromInt() throws Exception {

        Map<String, Object> theDummyMap = new HashMap<>(1);
        theDummyMap.put("boolean", 1);

        BizDummy bizDummy = Reflective.getReflectiveInstance(
                BizDummy.class,
                theDummyMap,
                Reflective.LOWERCASE_UNDERSCORE,
                null
        );

        assertEquals(false, bizDummy.getBoolean());
    }

    public void testReflectiveIllegalReturnTypeInt() throws Exception {

        String testString = "woot this is a test?!";

        Map<String, Object> theDummyMap = new HashMap<>(1);
        theDummyMap.put("int", testString);//setting string-val to int-key

        BizDummy bizDummy = Reflective.getReflectiveInstance(
                BizDummy.class,
                theDummyMap,
                Reflective.LOWERCASE_UNDERSCORE,
                null
        );

        assertEquals(0, bizDummy.getInt());
    }

    public void testReflectiveIllegalReturnTypeIntFromDouble() throws Exception {

        Map<String, Object> theDummyMap = new HashMap<>(1);
        theDummyMap.put("int", 12.3d);

        BizDummy bizDummy = Reflective.getReflectiveInstance(
                BizDummy.class,
                theDummyMap,
                Reflective.LOWERCASE_UNDERSCORE,
                null
        );

        assertEquals(12, bizDummy.getInt());
    }

    public void testReflectiveIllegalReturnTypeChar() throws Exception {

        String testString = "woot this is a test?!";

        Map<String, Object> theDummyMap = new HashMap<>(2);
        theDummyMap.put("char", testString);//setting string-val to char-key

        BizDummy bizDummy = Reflective.getReflectiveInstance(
                BizDummy.class,
                theDummyMap,
                Reflective.LOWERCASE_UNDERSCORE,
                null
        );

        assertEquals(0, bizDummy.getChar());
    }

    public void testStaticBizProfile() throws Exception {
        assertEquals(VAL_ID, sBizProfile.getProfileId());
        assertEquals(VAL_FOR_KIDS, (boolean) sBizProfile.isProfileForKids());
        assertEquals(VAL_NAME, sBizProfile.getProfileName());
        assertEquals("www.test.com/test_image.jpg", sBizProfile.getProfileAvatarUrl());

        // Gender not set, so should return default value
        assertNull(sBizProfile.getProfileGender());

        // Check empty field
        assertEquals(null, sBizProfile.getProfileEmail());
    }

    public void testBizProfileCustomKeyMap() throws Exception {

        Map<String, String> customKeys = new HashMap<>(1);
        customKeys.put("getProfileEmail", "e-post");

        BizTestProfileCustom bizProfile = Reflective.getReflectiveInstance(
                BizTestProfileCustom.class,
                sProfileMap,
                Reflective.LOWERCASE_UNDERSCORE,
                customKeys
        );

        assertEquals(VAL_ID, bizProfile.getProfileId());
        assertEquals(VAL_FOR_KIDS, (boolean) sBizProfile.isProfileForKids());
        assertEquals(VAL_NAME, bizProfile.getProfileName());
        assertEquals("www.test.com/test_image.jpg", bizProfile.getProfileAvatarUrl());

        // Test so that custom ReflectiveKey is working
        assertEquals("en_svensk_epost@example.com", bizProfile.getProfileEmail());
    }

    public void testBizProfileCustomInvocationHandler() throws Exception {

        BizTestProfileCustom bizProfile = Reflective.getReflectiveInstance(
                BizTestProfileCustom.class,
                new InvocationHandler() {
                    @Override
                    public Object invoke(
                            Object proxy,
                            Method method,
                            Object[] args
                    ) throws Throwable {
                        return sCamelCaseProfileMap.get(method.getName().substring(3));
                    }
                }
        );

        assertEquals(VAL_ID, bizProfile.getProfileId());
        assertEquals(VAL_FOR_KIDS, (boolean) sBizProfile.isProfileForKids());
        assertEquals(VAL_NAME, bizProfile.getProfileName());
        assertEquals("www.test.com/test_image.jpg", bizProfile.getProfileAvatarUrl());
    }

    public void testBizProfileAnonInterface() throws Exception {

        BizTestProfileCustom bizProfile = new BizTestProfileCustom() {
            @Override
            public int getProfileId() {
                return (int) sProfileMap.get("id");
            }

            @Override
            public Boolean isProfileForKids() {
                return (boolean) sProfileMap.get("for_kids");
            }

            @Override
            public String getProfileName() {
                return (String) sProfileMap.get("name");
            }

            @Override
            public String getProfileAvatarUrl() {
                return (String) sProfileMap.get("avatar_url");
            }

            @Override
            public String getProfileEmail() {
                return null;
            }

            @Override
            public Integer getProfileAge() {
                return null;
            }

            @Override
            public Integer getProfileGender() {
                return 0;
            }
        };

        assertEquals(VAL_ID, bizProfile.getProfileId());
        assertEquals(VAL_FOR_KIDS, (boolean) sBizProfile.isProfileForKids());
        assertEquals(VAL_NAME, bizProfile.getProfileName());
        assertEquals("www.test.com/test_image.jpg", bizProfile.getProfileAvatarUrl());

        assertEquals(0, (int) bizProfile.getProfileGender());
    }

    public void testBizProfileRequiredAnonInterface() throws Exception {
        Reflective.checkReflectiveRequired(
                BizTestProfileCustom.class,// Using BASECLASS BizTestProfileCustom for validation
                new BizTestProfileCustom() {
                    @Override
                    public int getProfileId() {
                        return 123;
                    }

                    @Override
                    public Boolean isProfileForKids() {
                        return false;
                    }

                    @Override
                    public String getProfileName() {
                        return "This is my name";
                    }

                    @Override
                    public String getProfileAvatarUrl() {
                        return null;
                    }

                    @Override
                    public String getProfileEmail() {
                        return null;
                    }

                    @Override
                    public Integer getProfileAge() {
                        return null;
                    }

                    @Override
                    public Integer getProfileGender() {
                        return 0;
                    }
                }
        );
    }

    public void testCheckReflectiveRequiredLight() throws Exception {
        BizTestProfileCustom bizProfile = Reflective.getReflectiveInstance(
                BizTestProfileCustom.class,// Using subclass of BizTestProfileCustom for logic
                sProfileMap,
                Reflective.LOWERCASE_UNDERSCORE,
                null
        );
        Reflective.checkReflectiveRequired(
                BizTestProfileCustom.class,// Using BASECLASS BizTestProfileCustom for validation
                bizProfile
        );
    }

    public void testGetKeyLowerCaseWithUnderscoreStartOffset() throws Exception {
        assertEquals(
                "avatar_url",
                Reflective.getKeyLowerCaseWithUnderscore(
                        "getProfile".length(),
                        "getProfileAvatarUrl"
                )
        );
    }

    public void testGetKeyLowerCaseWithUnderscore() throws Exception {
        assertEquals(
                "get_profile_avatar_url",
                Reflective.getKeyLowerCaseWithUnderscore(0, "getProfileAvatarUrl")
        );
    }

    public void testBizProfileCustomInvocationHandlerMulti() throws Exception {
        for (int i = 0; i < 1000; i++) {
            testBizProfileCustomInvocationHandler();
        }
    }
    public void testBizProfileCustomInvocationHandlerMultiLarge() throws Exception {
        for (int i = 0; i < 10000; i++) {
            testBizProfileCustomInvocationHandler();
        }
    }

    public void testStaticBizProfileMulti() throws Exception {
        for (int i = 0; i < 1000; i++) {
            testStaticBizProfile();
        }
    }
    public void testStaticBizProfileMultiLarge() throws Exception {
        for (int i = 0; i < 10000; i++) {
            testStaticBizProfile();
        }
    }

    public void testBizProfileAnonInterfaceMulti() throws Exception {
        for (int i = 0; i < 1000; i++) {
            testBizProfileAnonInterface();
        }
    }
    public void testBizProfileAnonInterfaceMultiLarge() throws Exception {
        for (int i = 0; i < 10000; i++) {
            testBizProfileAnonInterface();
        }
    }

    public void testBizProfileCustomKeyMapMulti() throws Exception {
        for (int i = 0; i < 1000; i++) {
            testBizProfileCustomKeyMap();
        }
    }
    public void testBizProfileCustomKeyMapMultiLarge() throws Exception {
        for (int i = 0; i < 10000; i++) {
            testBizProfileCustomKeyMap();
        }
    }

    public void testCheckReflectiveRequiredLightMulti() throws Exception {
        for (int i = 0; i < 1000; i++) {
            testCheckReflectiveRequiredLight();
        }
    }

}
