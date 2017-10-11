package com.ztory.lib.reflective.tst;

import com.ztory.lib.reflective.ReflectiveMapBacked;
import com.ztory.lib.reflective.ReflectiveRequired;
import com.ztory.lib.reflective.ReflectiveType;
import java.util.List;

/**
 * Created by jonruna on 2017-10-10.
 */
public interface TstBase extends ReflectiveMapBacked {
  @ReflectiveRequired
  String getTitle();
  @ReflectiveRequired
  Integer getCount();
  @ReflectiveRequired @ReflectiveType(TstNestOne.class)
  List<TstNestOne> getNestOneList();
}
