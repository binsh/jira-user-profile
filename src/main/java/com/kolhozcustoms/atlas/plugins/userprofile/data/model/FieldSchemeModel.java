package com.kolhozcustoms.atlas.plugins.userprofile.data.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "fieldsheme")
@XmlAccessorType(XmlAccessType.FIELD)
public class FieldSchemeModel {
    //@XmlElement(name = "id")
    private Integer id;
    private String key;
    private String displayName;
    private String fieldType;
    private String fieldDescription;
    private Integer fieldLength;
    private boolean hidden;
    private boolean allowHide;
    private Integer fieldOrder;

    public FieldSchemeModel() {} //protected
    public FieldSchemeModel(Integer id, String key, String displayName, String fieldType, String fieldDescription,
    Integer fieldLength, boolean hidden, boolean allowHide, Integer fieldOrder) {
        this.id = id;
        this.key = key;
        this.displayName = displayName;
        this.fieldType = fieldType;
        this.fieldDescription = fieldDescription;
        this.fieldLength = fieldLength;
        this.hidden = hidden;
        this.allowHide = allowHide;
        this.fieldOrder = fieldOrder;

    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        if (id != null){
            this.id = id;
        }
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
            this.key = key;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
            this.displayName = displayName;
    }

    public String getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(String fieldType) {
            this.fieldType = fieldType;
    }

    public String getFieldDescription() {
        return this.fieldDescription;
    }

    public void setFieldDescription(String fieldDescription) {
        this.fieldDescription = fieldDescription;
    }

    public Integer getFieldLength() {
        return this.fieldLength;
    }

    public void setFieldLength(Integer fieldLength) {
            this.fieldLength = fieldLength;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public boolean getHidden() {
        return this.hidden;
    }

    public void setHidden(boolean hidden) {
            this.hidden = hidden;
    }

    public boolean isAllowHide() {
        return this.allowHide;
    }

    public boolean getAllowHide() {
        return this.allowHide;
    }

    public void setAllowHide(boolean allowHide) {
            this.allowHide = allowHide;
    }

    public Integer getFieldOrder() {
        return this.fieldOrder;
    }

    public void setFieldOrder(Integer fieldOrder) {
            this.fieldOrder = fieldOrder;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", key='" + getKey() + "'" +
            ", displayName='" + getDisplayName() + "'" +
            ", fieldType='" + getFieldType() + "'" +
            ", fieldDescription='" + getFieldDescription() + "'" +
            ", fieldLength='" + getFieldLength() + "'" +
            ", hidden='" + isHidden() + "'" +
            ", allowHide='" + isAllowHide() + "'" +
            ", fieldOrder='" + getFieldOrder() + "'" +
            "}";
    }

}
