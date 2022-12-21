package com.kolhozcustoms.atlas.plugins.userprofile.data.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

@XmlRootElement(name = "usersprofile")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserDataModel {

    private String key;
    private Map<String, UserDataUnitModel> data = new TreeMap<>();

    public UserDataModel() {} //protected
    public UserDataModel(String key) {
        this.key = key;
    }
    public UserDataModel(String key, Map<String, UserDataUnitModel> data) {
        this.key = key;
        this.data = new TreeMap<>(data);
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Map<String, UserDataUnitModel> getData() {
        return this.data;
    }
    
    public void setData(Map<String, UserDataUnitModel> data) {
        this.data = new TreeMap<>(data);
    }

    public void putData(String key, UserDataUnitModel value) {
        this.data.put(key, value);
    }

    public void putAllData(Map<String, UserDataUnitModel> data) {
        this.data.putAll(data);
    }

    public void putDataFromList(List<UserDataUnitModel> data) {
        for (UserDataUnitModel userData: data){
            this.data.put(userData.getKey(), userData);
        }
    }

    public void applyKey(){
        this.data.values().forEach(data -> data.setUserKey(this.key));
    }

    @Override
    public String toString() {
        return "{" +
            " key='" + getKey() + "'" +
            ", data=[" + getData().toString() + "]" +
            "}";
    }

}
