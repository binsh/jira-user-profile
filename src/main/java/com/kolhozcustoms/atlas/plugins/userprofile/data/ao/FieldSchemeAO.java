package com.kolhozcustoms.atlas.plugins.userprofile.data.ao;

import net.java.ao.Entity;
import net.java.ao.Preload;
import net.java.ao.schema.*;


@Preload
public interface FieldSchemeAO extends Entity {
    @Indexed
    @StringLength(60)
    String getKey();
    void setKey(String key);

    @StringLength(100)
    String getDisplayName();
    void setDisplayName(String displayName);
    
    @StringLength(24)
    @NotNull
    @Default("String")
    String getFieldType();
    void setFieldType(String fieldType);

    @StringLength(255)
    String getFieldDescription();
    void setFieldDescription(String fieldDescription);

    Integer getFieldLength();
    void setFieldLength(Integer fieldLength);

    boolean getHidden();
    void setHidden(boolean hidden);

    boolean getAllowHide();
    void setAllowHide(boolean allowHide);

    Integer getFieldOrder();
    void setFieldOrder(Integer fieldOrder);

}
