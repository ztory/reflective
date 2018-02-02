package com.ztory.lib.reflective;

/**
 * Created by jonruna on 2018-02-02.
 */
public interface BizJob extends ReflectiveMapBacked {

  @ReflectiveRequired
  String getId();

  String getTitle();

  String getDescription();

  Integer getCount();

}
