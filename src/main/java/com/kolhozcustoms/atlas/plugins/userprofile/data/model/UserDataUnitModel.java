package com.kolhozcustoms.atlas.plugins.userprofile.data.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "data")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserDataUnitModel {
    private Integer id;
    private String key;
    private String userKey;
    private String content;
    private boolean hidden;

    public UserDataUnitModel() {} //protected
    public UserDataUnitModel(Integer id, String key, String userKey, String content, boolean hidden) {
        this.id = id;
        this.key = key;
        this.userKey = userKey;
        this.content = content;
        this.hidden = hidden;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserKey() {
        return this.userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
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

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", key='" + getKey() + "'" +
            ", userKey='" + getUserKey() + "'" +
            ", content='" + getContent() + "'" +
            ", hidden='" + isHidden() + "'" +
            "}";
    }
}
