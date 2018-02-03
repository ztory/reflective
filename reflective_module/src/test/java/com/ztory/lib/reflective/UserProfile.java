package com.ztory.lib.reflective;

import java.util.List;

/**
 * Created by jonruna on 2018-02-03.
 */
public interface UserProfile extends ReflectiveMapBacked {
  @ReflectiveRequired
  String getId();
  @ReflectiveRequired
  String getEmail();

  String getFirstName();

  String getLastName();

  Integer getRating();

  Double getActivityScore();
  @ReflectiveType(UserProfile.class)
  List<UserProfile> getFriends();
}
