package com.ztory.lib.reflective;

/**
 * Created by jonruna on 20/04/16.
 */
@ReflectiveNamespace("Profile")
public interface BizTestProfileCustom {

    //REQUIRED
    int getProfileId();
    Boolean isProfileForKids();
    String getProfileName();

    //OPTIONAL
    String getProfileAvatarUrl();
    String getProfileEmail();
    Integer getProfileAge();
    Integer getProfileGender();

}
