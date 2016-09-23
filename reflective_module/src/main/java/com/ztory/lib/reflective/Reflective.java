package com.ztory.lib.reflective;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Reflective functionality leverages Java-Reflection functionality to provide easy to use tools
 * for data consumption and manipulation.
 * Created by jonruna on 16/04/16.
 */
public class Reflective {

    private static final List<Class<?>> sWrapperClasses = new ArrayList<>(9);
    private static final List<Class<?>> sPrimitiveClasses = new ArrayList<>(9);
    private static final Map<Class<?>, Object> sDefaultValues = new HashMap<>(8);
    static {
        sWrapperClasses.add(Integer.class);
        sWrapperClasses.add(Boolean.class);
        sWrapperClasses.add(Long.class);
        sWrapperClasses.add(Float.class);
        sWrapperClasses.add(Double.class);
        sWrapperClasses.add(Byte.class);
        sWrapperClasses.add(Character.class);
        sWrapperClasses.add(Short.class);
        sWrapperClasses.add(Void.class);

        sPrimitiveClasses.add(int.class);
        sPrimitiveClasses.add(boolean.class);
        sPrimitiveClasses.add(long.class);
        sPrimitiveClasses.add(float.class);
        sPrimitiveClasses.add(double.class);
        sPrimitiveClasses.add(byte.class);
        sPrimitiveClasses.add(char.class);
        sPrimitiveClasses.add(short.class);
        sPrimitiveClasses.add(void.class);

        sDefaultValues.put(int.class, 0);
        sDefaultValues.put(boolean.class, false);
        sDefaultValues.put(long.class, 0L);
        sDefaultValues.put(float.class, 0.0f);
        sDefaultValues.put(double.class, 0.0d);
        sDefaultValues.put(byte.class, (byte) 0);
        sDefaultValues.put(char.class, '\u0000');
        sDefaultValues.put(short.class, (short) 0);
    }

    public static final int
            INTEGER_INDEX = 0,
            BOOLEAN_INDEX = 1,
            LONG_INDEX = 2,
            FLOAT_INDEX = 3,
            DOUBLE_INDEX = 4,
            BYTE_INDEX = 5,
            CHARACTER_INDEX = 6,
            SHORT_INDEX = 7,
            VOID_INDEX = 8;

    public static final ReflectiveKeyParser LOWERCASE_UNDERSCORE = new ReflectiveKeyParser() {
        @Override
        public String getParsedKey(int startOffset, String key) {
            return Reflective.getKeyLowerCaseWithUnderscore(startOffset, key);
        }
    };

    private static final char UNDERSCORE = '_';

    /**
     * Test if supplied primitiveIndex is of a type that can be represented as a Number.
     * @param primitiveIndex int value was returned from calling getPrimitiveWrapperIndex() or
     *                       getPrimitiveIndex()
     * @return true if the index corresponds to a data type that can be represented as a Number,
     * otherwise returns false.
     */
    public static boolean isPrimitiveIndexNumber(int primitiveIndex) {
        switch (primitiveIndex) {
            case INTEGER_INDEX:
                return true;
            case LONG_INDEX:
                return true;
            case FLOAT_INDEX:
                return true;
            case DOUBLE_INDEX:
                return true;
            case BYTE_INDEX:
                return true;
            case SHORT_INDEX:
                return true;
        }
        return false;
    }

    /**
     * Returns the default value for the supplied primitive Class parameter, will return null for
     * all other Classes.
     * @param clazz Class to get default value for
     * @return default value Class
     */
    public static Object getPrimitiveDefaultValue(final Class<?> clazz) {
        return sDefaultValues.get(clazz);
    }

    /**
     * Returns -1 if supplied class is not a primitive-wrapper-class.
     * Will return a positive-integer that is equal to one of the following constants:
     * INTEGER_INDEX
     * BOOLEAN_INDEX
     * LONG_INDEX
     * FLOAT_INDEX
     * DOUBLE_INDEX
     * BYTE_INDEX
     * CHARACTER_INDEX
     * SHORT_INDEX
     * VOID_INDEX
     * @param clazz Class to test if it is a primitive-wrapper-class
     * @return -1 for no match, 0 or greater int if there is a match
     */
    public static int getPrimitiveWrapperIndex(final Class<?> clazz) {
        return sWrapperClasses.indexOf(clazz);
    }

    /**
     * Returns -1 if supplied class is not a primitive-class.
     * Will return a positive-integer that is equal to one of the following constants:
     * INTEGER_INDEX
     * BOOLEAN_INDEX
     * LONG_INDEX
     * FLOAT_INDEX
     * DOUBLE_INDEX
     * BYTE_INDEX
     * CHARACTER_INDEX
     * SHORT_INDEX
     * VOID_INDEX
     * @param clazz Class to test if it is a primitive-class
     * @return -1 for no match, 0 or greater int if there is a match
     */
    public static int getPrimitiveIndex(final Class<?> clazz) {
        return sPrimitiveClasses.indexOf(clazz);
    }

    public static <T> void checkReflectiveRequired(
            Class<T> clazz,
            List<T> objects
    ) throws ReflectiveRequiredException {
        for (T iterObject : objects) {
            checkReflectiveRequired(clazz, iterObject);
        }
    }

    /**
     * NOTE THAT ANNOTATIONS ARE NOT INHERITED BY SUBCLASSES!
     * Easy fix is to use base-interface/class for validation and subclass for logic.
     *
     * Validates data in object with the methods from clazz that have the
     * ReflectiveRequired annotation.
     * @param clazz Class containing ReflectiveRequired annotated methods
     * @param object subclass of Class that will have its ReflectiveRequired methods validated
     * @param <T> Type of clazz and object
     * @throws ReflectiveRequiredException if a ReflectiveRequired method returns null or any other
     * exception is thrown when executing method.invoke(object)
     */
    public static <T> void checkReflectiveRequired(
            Class<T> clazz,
            T object
    ) throws ReflectiveRequiredException {
        ReflectiveRequired aRequired;
        for (Method iterMethod : clazz.getMethods()) {
            if ((aRequired = iterMethod.getAnnotation(ReflectiveRequired.class)) != null) {

                Object returnObject = null;

                try {
                    returnObject = iterMethod.invoke(object);

                    /*
                    "Class", clazz,
                    "Method", iterMethod.getName(),
                    "ReflectiveRequired", aRequired,
                    "hasMinimumNumber", aRequired.hasMinimumNumber(),
                    "minimumNumber", aRequired.minimumNumber(),
                    "returnObject", returnObject
                    */

                    if (returnObject == null) {
                        throw new ReflectiveRequiredException(
                                "ReflectiveRequired method " + iterMethod.getName() + " returned null!"
                        );
                    }
                    else if (
                            aRequired.hasMinimumNumber()
                            && returnObject instanceof Number
                            && ((Number) returnObject).doubleValue() < aRequired.minimumNumber()
                            ) {
                        throw new ReflectiveRequiredException(
                                "ReflectiveRequired method " + iterMethod.getName() + " returned "
                                + returnObject + ". Minimum value is: " + aRequired.minimumNumber()
                        );
                    }
                } catch (ReflectiveRequiredException e) {
                    throw e;
                } catch (Exception e) {
                    throw new ReflectiveRequiredException(
                            "ReflectiveRequired method " + iterMethod.getName()
                            + " returned: " + returnObject,
                            e
                    );
                }
            }
        }
    }

    /**
     * Creates a clazz proxy-instance with a ReflectiveInvocationHandler.
     * @param clazz Class of return data
     * @param invocationHandler InvocationHandler that will handle method invokes.
     * @param <T> Type of returned data
     * @return a <T> proxy-instance with its values backed by a Map instance
     */
    @SuppressWarnings("unchecked")
    public static <T> T getReflectiveInstance(
            Class<T> clazz,
            InvocationHandler invocationHandler
    ) {
        return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class[] { clazz },
                invocationHandler
        );
    }

    /**
     * Creates a clazz proxy-instance with a ReflectiveInvocationHandler.
     * @param clazz Class of return data
     * @param valueMap Map that holds the key/value pairs that the proxy-instance will interact on.
     * @param <T> Type of returned data
     * @return a <T> proxy-instance with its values backed by a Map instance
     */
    @SuppressWarnings("unchecked")
    public static <T> T getReflectiveInstance(
            Class<T> clazz,
            final Map<String, Object> valueMap,
            Map<String, String> theCustomKeyMap
    ) {
        return getReflectiveInstance(
                clazz,
                new ReflectiveInvocationHandler(
                        getReflectiveNamespaceSafe(clazz),
                        valueMap,
                        LOWERCASE_UNDERSCORE,
                        theCustomKeyMap
                )
        );
    }

    /**
     * Creates a clazz proxy-instance with a ReflectiveInvocationHandler.
     * @param clazz Class of return data
     * @param valueJSON JSONObject that holds the key/value pairs that the proxy-instance will interact on.
     * @param <T> Type of returned data
     * @return a <T> proxy-instance with its values backed by a JSONObject instance
     */
    @SuppressWarnings("unchecked")
    public static <T> T getReflectiveInstance(
            Class<T> clazz,
            final JSONObject valueJSON,
            Map<String, String> theCustomKeyMap
    ) {
        return getReflectiveInstance(
                clazz,
                new ReflectiveInvocationHandler(
                        getReflectiveNamespaceSafe(clazz),
                        valueJSON,
                        LOWERCASE_UNDERSCORE,
                        theCustomKeyMap
                )
        );
    }

    public static <T> List<T> getMultiReflectiveInstances(
            Class<T> clazz,
            JSONArray JSONArrayValues,
            Map<String, String> theCustomKeyMap
    ) throws JSONException {
        List<T> returnList = new ArrayList<>(JSONArrayValues.length());
        for (int i = 0; i < JSONArrayValues.length(); i++) {
            returnList.add(
                    getReflectiveInstance(
                            clazz,
                            JSONArrayValues.getJSONObject(i),
                            theCustomKeyMap
                    )
            );
        }
        return returnList;
    }

    private static <T> String getReflectiveNamespaceSafe(Class<T> clazz) {
        ReflectiveNamespace reflectiveNamespace = clazz.getAnnotation(ReflectiveNamespace.class);
        if (reflectiveNamespace != null) {
            return reflectiveNamespace.value();
        }
        else {
            return null;
        }
    }

    /**
     * Will convert a camel-case-key to a lowercase-with-underscore-key.
     * Example: CamelCaseKey -> camel_case_key
     * @param startOffset startOffset to be used on supplied keyName
     * @param keyName key-name in its raw form
     * @return a lowercase-with-underscore String from the keyName using the startOffset as the
     * number of characters to strip from keyName in the returned String.
     */
    public static String getKeyLowerCaseWithUnderscore(int startOffset, String keyName) {

        int splitCount = 0;
        for (int i = 1 + startOffset; i < keyName.length(); i++) {
            if (Character.isUpperCase(keyName.charAt(i))) {
                splitCount++;
            }
        }

        if (splitCount == 0) {
            return keyName.substring(startOffset).toLowerCase(Locale.ENGLISH);
        }

        StringBuilder sb = new StringBuilder(keyName.length() - startOffset + splitCount);
        int lastOffset = startOffset;

        for (int i = 1 + startOffset; i < keyName.length(); i++) {
            if (Character.isUpperCase(keyName.charAt(i))) {
                sb.append(keyName, lastOffset, i);
                sb.append(UNDERSCORE);
                lastOffset = i;
            }
        }

        sb.append(keyName, lastOffset, keyName.length());

        return sb.toString().toLowerCase(Locale.ENGLISH);
    }

}
