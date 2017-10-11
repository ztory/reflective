package com.ztory.lib.reflective.tst.faulty;

import com.ztory.lib.reflective.ReflectiveRequired;

/**
 * Created by jonruna on 2017-10-11.
 */
public interface FaultyInnerInner {
  @ReflectiveRequired
  String getLabel();
  @ReflectiveRequired
  String getMissingRequiredString();
}
