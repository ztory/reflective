package com.ztory.lib.reflective.tst;

import com.ztory.lib.reflective.ReflectiveMapBacked;
import com.ztory.lib.reflective.ReflectiveRequired;
import java.util.List;

/**
 * Created by jonruna on 2017-10-10.
 */
public interface TstNestTre extends ReflectiveMapBacked {
  @ReflectiveRequired
  String getLabel();
  @ReflectiveRequired
  Integer getAttempts();
  @ReflectiveRequired
  List<String> getRoles();
}
