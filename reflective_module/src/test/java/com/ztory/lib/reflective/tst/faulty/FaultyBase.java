package com.ztory.lib.reflective.tst.faulty;

import com.ztory.lib.reflective.ReflectiveRequired;
import com.ztory.lib.reflective.ReflectiveType;
import java.util.List;

/**
 * Created by jonruna on 2017-10-11.
 */
public interface FaultyBase {
  @ReflectiveRequired
  String getName();
  @ReflectiveRequired
  String getPhoneNumber();
  @ReflectiveRequired
  Integer getId();
  @ReflectiveRequired @ReflectiveType(FaultyInner.class)
  List<FaultyInner> getFaultyInnerList();
}
