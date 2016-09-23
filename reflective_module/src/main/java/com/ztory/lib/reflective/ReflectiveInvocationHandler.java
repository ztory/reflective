package com.ztory.lib.reflective;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Standard InvocationHandler implementation for Reflective functionality.
 * Created by jonruna on 20/04/16.
 */
public class ReflectiveInvocationHandler implements InvocationHandler {

    private static final String GET = "get", IS = "is", SET = "set";

    private final boolean jsonMode;
    private final String methodNamespace;
    private final int methodNamespaceLength;
    private final JSONObject membersJSON;
    private final Map<String, Object> membersMap;
    private final ReflectiveKeyParser keyParser;
    private final boolean hasKeyParser;
    private final Map<String, String> customKeyMap;
    private final boolean hasCustomKeyMap;

    public ReflectiveInvocationHandler(
            String theMethodNamespace,
            JSONObject theMembersJSON,
            ReflectiveKeyParser theKeyParser,
            Map<String, String> theCustomKeyMap
    ) {
        if (theMembersJSON == null) {
            throw new IllegalArgumentException("jsonMode == true && membersJSON == null");
        }
        jsonMode = true;
        methodNamespace = theMethodNamespace;
        methodNamespaceLength = (methodNamespace != null) ? methodNamespace.length() : 0;
        membersJSON = theMembersJSON;
        membersMap = null;
        keyParser = theKeyParser;
        hasKeyParser = keyParser != null;
        customKeyMap = theCustomKeyMap;
        hasCustomKeyMap = customKeyMap != null;
    }

    public ReflectiveInvocationHandler(
            String theMethodNamespace,
            Map<String, Object> theMembersMap,
            ReflectiveKeyParser theKeyParser,
            Map<String, String> theCustomKeyMap
    ) {
        if (theMembersMap == null) {
            throw new IllegalArgumentException("jsonMode == false && membersMap == null");
        }
        jsonMode = false;
        methodNamespace = theMethodNamespace;
        methodNamespaceLength = (methodNamespace != null) ? methodNamespace.length() : 0;
        membersJSON = null;
        membersMap = theMembersMap;
        keyParser = theKeyParser;
        hasKeyParser = keyParser != null;
        customKeyMap = theCustomKeyMap;
        hasCustomKeyMap = customKeyMap != null;
    }

    private Object getValue(Method method, String key) {

        Object returnValue;

        if (jsonMode) {
            returnValue = membersJSON.opt(key);
        }
        else {
            returnValue = membersMap.get(key);
        }

        Class<?> methodReturnType = method.getReturnType();

        if (returnValue == null) {
            return Reflective.getPrimitiveDefaultValue(methodReturnType);
        }

        Class<?> returnValueType = returnValue.getClass();

        if (methodReturnType.isPrimitive()) {

            int returnValueWrapperIndex = Reflective.getPrimitiveWrapperIndex(returnValueType);

            if (returnValueWrapperIndex >= 0) {
                try {
                    int methodReturnPrimitiveIndex = Reflective.getPrimitiveIndex(methodReturnType);
                    if (returnValueWrapperIndex == methodReturnPrimitiveIndex) {
                        return returnValue;//boxed-type mathes raw-primitive-type, safe to return
                    }

                    switch (methodReturnPrimitiveIndex) {
                        case Reflective.INTEGER_INDEX:
                            return ((Number) returnValue).intValue();
                        case Reflective.LONG_INDEX:
                            return ((Number) returnValue).longValue();
                        case Reflective.FLOAT_INDEX:
                            return ((Number) returnValue).floatValue();
                        case Reflective.DOUBLE_INDEX:
                            return ((Number) returnValue).doubleValue();
                        case Reflective.BYTE_INDEX:
                            return ((Number) returnValue).byteValue();
                        case Reflective.SHORT_INDEX:
                            return ((Number) returnValue).shortValue();
                        default://void return will call this as well
                            return Reflective.getPrimitiveDefaultValue(methodReturnType);
                    }
                } catch (Exception e) {
                    return Reflective.getPrimitiveDefaultValue(methodReturnType);
                }
            }
            else {
                return Reflective.getPrimitiveDefaultValue(methodReturnType);
            }
        }
        else if (!methodReturnType.isAssignableFrom(returnValueType)) {
            int returnValueWrapperIndex = Reflective.getPrimitiveWrapperIndex(returnValueType);
            if (Reflective.isPrimitiveIndexNumber(returnValueWrapperIndex)) {
                switch (Reflective.getPrimitiveWrapperIndex(methodReturnType)) {
                    case Reflective.INTEGER_INDEX:
                        return ((Number) returnValue).intValue();
                    case Reflective.LONG_INDEX:
                        return ((Number) returnValue).longValue();
                    case Reflective.FLOAT_INDEX:
                        return ((Number) returnValue).floatValue();
                    case Reflective.DOUBLE_INDEX:
                        return ((Number) returnValue).doubleValue();
                    case Reflective.BYTE_INDEX:
                        return ((Number) returnValue).byteValue();
                    case Reflective.SHORT_INDEX:
                        return ((Number) returnValue).shortValue();
                }
            }
            return null;
        }

        return returnValue;
    }

    private void setValue(String key, Object value) throws JSONException {
        if (jsonMode) {
            membersJSON.put(key, value);
        }
        else {
            membersMap.put(key, value);
        }
    }

    @Override
    public Object invoke(
            Object proxy,
            Method method,
            Object[] args
    ) throws java.lang.Throwable {

        try {

            String methodName = method.getName();

            if (hasCustomKeyMap) {
                String forcedReflectiveKey = customKeyMap.get(methodName);
                if (forcedReflectiveKey != null) {
                    return getValue(method, forcedReflectiveKey);
                }
            }

            if (methodName.startsWith(GET)) {
                if (hasKeyParser) {
                    return getValue(
                            method,
                            keyParser.getParsedKey(
                                    GET.length() + methodNamespaceLength,
                                    methodName
                            )
                    );
                }
                else {
                    return getValue(
                            method,
                            methodName.substring(
                                    GET.length() + methodNamespaceLength
                            )
                    );
                }
            }
            else if (methodName.startsWith(IS)) {
                if (hasKeyParser) {
                    return getValue(
                            method,
                            keyParser.getParsedKey(
                                    IS.length() + methodNamespaceLength,
                                    methodName
                            )
                    );
                }
                else {
                    return getValue(
                            method,
                            methodName.substring(
                                    IS.length() + methodNamespaceLength
                            )
                    );
                }
            }
            else if (methodName.startsWith(SET)) {
                if (hasKeyParser) {
                    setValue(
                            keyParser.getParsedKey(
                                    SET.length() + methodNamespaceLength,
                                    methodName
                            ),
                            args[0]
                    );
                    return null;
                }
                else {
                    setValue(
                            methodName.substring(
                                    SET.length() + methodNamespaceLength
                            ),
                            args[0]
                    );
                    return null;
                }
            }
            else {//if NOT get/is/set prefix return value for methodName-key
                if (hasKeyParser) {
                    return getValue(
                            method,
                            keyParser.getParsedKey(
                                    0,
                                    methodName
                            )
                    );
                }
                else {
                    return getValue(
                            method,
                            methodName
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
