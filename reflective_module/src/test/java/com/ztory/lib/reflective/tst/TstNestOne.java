package com.ztory.lib.reflective.tst;

import com.ztory.lib.reflective.ReflectiveMapBacked;
import com.ztory.lib.reflective.ReflectiveRequired;

/**
 * Created by jonruna on 2017-10-10.
 */
public interface TstNestOne extends ReflectiveMapBacked {
  @ReflectiveRequired
  String getName();
  @ReflectiveRequired
  Integer getSize();
  @ReflectiveRequired
  TstNestTwo getNestTwo();
}
