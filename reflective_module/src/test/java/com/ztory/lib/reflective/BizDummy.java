package com.ztory.lib.reflective;

/**
 * Created by jonruna on 26/04/16.
 */
public interface BizDummy {
    String getString();
    void setString(String theString);
    DummyClassBase getDummyClass();
    void setInt(int theInt);
    int getInt();
    long getLong();
    float getFloat();
    double getDouble();
    short getShort();
    byte getByte();
    char getChar();
    boolean getBoolean();
    void getVoid();
    Integer getInteger();
}
