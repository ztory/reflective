package com.ztory.lib.reflective.tst.faulty;

import com.ztory.lib.reflective.ReflectiveRequired;
import com.ztory.lib.reflective.ReflectiveType;

/**
 * Created by jonruna on 2017-10-11.
 */
public interface FaultyInner {
  @ReflectiveRequired
  String getTitle();
  @ReflectiveRequired
  String getMessage();
  @ReflectiveRequired
  Integer getMessageCount();
  @ReflectiveRequired @ReflectiveType(FaultyInnerInner.class)
  FaultyInnerInner getFaultyInnerInner();
}
