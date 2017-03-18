package com.ztory.lib.reflective;

/**
 * Created by jonruna on 2017-03-18.
 */
public interface BizTestJSONObject {

    @ReflectiveRequired
    Boolean isBaller();

    @ReflectiveRequired
    String getVeryLongKeyNameForStringValue();

    @ReflectiveRequired
    String getName();

    @ReflectiveRequired
    String getTitle();

    @ReflectiveRequired
    String getDescription();

    @ReflectiveRequired
    Number getPositionX();

    @ReflectiveRequired
    Number getPositionY();

    @ReflectiveRequired
    Number getPositionW();

    @ReflectiveRequired
    Number getPositionH();

    String getOptionalLabelString1();
    String getOptionalLabelString2();
    String getOptionalLabelString3();
    String getOptionalLabelString4();

}
