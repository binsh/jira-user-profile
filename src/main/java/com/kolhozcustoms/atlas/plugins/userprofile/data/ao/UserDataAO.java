package com.kolhozcustoms.atlas.plugins.userprofile.data.ao;

import net.java.ao.Entity;
import net.java.ao.Preload;
import net.java.ao.schema.*;

@Preload
//@Table(uniqueConstraints={ @UniqueConstraint(columnNames = {"key", "userKey"})})
public interface UserDataAO extends Entity{
    @Indexed
    String getKey();
    void setKey(String key);
    
    @Indexed
    String getUserKey();
    void setUserKey(String userKey);

    @StringLength(255)
    String getContent();
    void setContent(String content);

    boolean getHidden();
    void setHidden(boolean hidden);
}
