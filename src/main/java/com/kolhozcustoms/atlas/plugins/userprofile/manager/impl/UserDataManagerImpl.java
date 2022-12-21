package com.kolhozcustoms.atlas.plugins.userprofile.manager.impl;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.atlassian.activeobjects.external.ActiveObjects;
import net.java.ao.Query;
import com.kolhozcustoms.atlas.plugins.userprofile.data.ao.UserDataAO;
import com.kolhozcustoms.atlas.plugins.userprofile.data.ao.UserDataBlobAO;
import com.kolhozcustoms.atlas.plugins.userprofile.data.model.UserDataModel;
import com.kolhozcustoms.atlas.plugins.userprofile.data.model.UserDataUnitModel;
import com.kolhozcustoms.atlas.plugins.userprofile.manager.UserDataManager;
import com.kolhozcustoms.atlas.plugins.userprofile.manager.FieldSchemeManager;
import com.kolhozcustoms.atlas.plugins.userprofile.data.model.FieldSchemeModel;

public class UserDataManagerImpl implements UserDataManager {
    private final ActiveObjects ao;
    private final FieldSchemeManager fieldSchemeManager;

    public UserDataManagerImpl(ActiveObjects ao, FieldSchemeManager fieldSchemeManager) {
        this.ao = ao;
        this.fieldSchemeManager = fieldSchemeManager;
    }

    private UserDataAO[] querySelect(String fieldName, String fieldValue){
        return ao.find(UserDataAO.class, Query.select().where("\"" + fieldName + "\" = ?", fieldValue));
    }
    private UserDataBlobAO[] querySelectBlob(String fieldName, String fieldValue){
        return ao.find(UserDataBlobAO.class, Query.select().where("\"" + fieldName + "\" = ?", fieldValue));
    }
    private UserDataUnitModel greateUnitModel(UserDataAO userDataAO){
        if (userDataAO != null){
            return new UserDataUnitModel(userDataAO.getID(), userDataAO.getKey(), userDataAO.getUserKey(), userDataAO.getContent(), userDataAO.getHidden());
        }
        return new UserDataUnitModel();
    }
    private UserDataUnitModel greateUnitBlobModel(UserDataBlobAO userDataAO){
        if (userDataAO != null){
            return new UserDataUnitModel(userDataAO.getID(), userDataAO.getKey(), userDataAO.getUserKey(), userDataAO.getContent(), userDataAO.getHidden());
        }
        return new UserDataUnitModel();
    }
/*
    private UserDataModel greateModel(UserDataAO userDataAO, UserDataBlobAO[] userDataBlobAO){
        userDataModel = new UserDataModel();
        if (userDataAO != null){
           userDataModel.putAll(userDataAO);
        }
        return userDataModel;
    }

    @Override
    public List<UserDataModel> getAllUsersData() {
        UserDataAO[] userDataAO = ao.find(UserDataAO.class, Query.select());
        UserDataBlobAO[] userDataBlobAO = ao.find(UserDataBlobAO.class, Query.select());
        List<ContactModel> contacts = new ArrayList<>();
        return Stream.of(contactAOs)
                .map(contactAO -> greateModel(contactAO))
                .collect(Collectors.toList());
    }

    @Override
    public ContactModel update(ContactModel contact) {
        ContactAO contactAO = ao.get(ContactAO.class, contact.getId());
        if (contactAO != null) {
            if (contact.getKey() != null) {
                contactAO.setKey(contact.getKey());
            }
            if (contact.getUserName() != null) {
                contactAO.setUserName(contact.getUserName());
            }
            if (contact.getPhone() != null) {
                contactAO.setPhone(contact.getPhone());
            }
            contactAO.save();
            contact.setId(contactAO.getID());
            contact.setKey(contactAO.getKey());
            contact.setUserName(contactAO.getUserName());
            contact.setPhone(contactAO.getPhone());
            
        }
        return contact;
    }
*/
    @Override
    public void save(UserDataModel userDataModel) { // TODO refact
        Collection<FieldSchemeModel> fieldSchemeModels = fieldSchemeManager.getAllFields();
        
        for (UserDataUnitModel userDataUnitModel : userDataModel.getData().values()){
            FieldSchemeModel fieldSchemeModel = fieldSchemeModels.stream().filter(scheme -> scheme.getKey().equals(userDataUnitModel.getKey())).findFirst().orElse(null);
            if (fieldSchemeModel.getFieldType().equals("Text")) {
                UserDataBlobAO[] userDataAOs = ao.find(UserDataBlobAO.class, Query.select().where("\"USER_KEY\" = ? AND \"KEY\" = ?", userDataModel.getKey(), userDataUnitModel.getKey()));
                if (userDataAOs.length > 0) {
                    Stream.of(userDataAOs).forEach(userDataAO -> {
                        userDataAO.setKey(userDataUnitModel.getKey());
                        userDataAO.setUserKey(userDataUnitModel.getUserKey());
                        userDataAO.setContent(userDataUnitModel.getContent());
                        userDataAO.setHidden(userDataUnitModel.getHidden());
                        userDataAO.save();
                        userDataUnitModel.setId(userDataAO.getID()); // костыль!!!!!
                    });
                    System.out.println("modify blob object: " + userDataAOs[0].getUserKey());
                } else {
                    UserDataBlobAO userDataAO = ao.create(UserDataBlobAO.class);
                    userDataAO.setKey(userDataUnitModel.getKey());
                    userDataAO.setUserKey(userDataUnitModel.getUserKey());
                    userDataAO.setContent(userDataUnitModel.getContent());
                    userDataAO.setHidden(userDataUnitModel.getHidden());
                    userDataAO.save();
                    userDataUnitModel.setId(userDataAO.getID()); // костыль!!!!!
                    System.out.println("new blob object: " + userDataAO.getUserKey());
                }
            } else {
                UserDataAO[] userDataAOs = ao.find(UserDataAO.class, Query.select().where("\"USER_KEY\" = ? AND \"KEY\" = ?", userDataModel.getKey(), userDataUnitModel.getKey()));
                if (userDataAOs.length > 0) {
                    Stream.of(userDataAOs).forEach(userDataAO -> {
                        userDataAO.setKey(userDataUnitModel.getKey());
                        userDataAO.setUserKey(userDataUnitModel.getUserKey());
                        userDataAO.setContent(userDataUnitModel.getContent());
                        userDataAO.setHidden(userDataUnitModel.getHidden());
                        userDataAO.save();
                        userDataUnitModel.setId(userDataAO.getID()); // костыль!!!!!
                    });
                    System.out.println("modify object: " + userDataAOs[0].getUserKey());
                } else {
                    UserDataAO userDataAO = ao.create(UserDataAO.class);
                    userDataAO.setKey(userDataUnitModel.getKey());
                    userDataAO.setUserKey(userDataUnitModel.getUserKey());
                    userDataAO.setContent(userDataUnitModel.getContent());
                    userDataAO.setHidden(userDataUnitModel.getHidden());
                    userDataAO.save();
                    userDataUnitModel.setId(userDataAO.getID()); // костыль!!!!!
                    System.out.println("new object: " + userDataAO.getUserKey());
                }
            }
        }
    }

    @Override
    public void deleteById(Integer id) { // under construction
        UserDataAO userDataAO = ao.get(UserDataAO.class, id);
        if (userDataAO != null) {
            ao.delete(userDataAO);
        }
    }

    @Override
    public void deleteByKey(String key){
        // under construction
    }

    @Override
    public void clear() { // use carefully
        UserDataAO[] userDataAOs = ao.find(UserDataAO.class, Query.select());
        if (userDataAOs.length > 0) {
            Stream.of(userDataAOs).forEach(userDataAO -> {
                ao.delete(userDataAO);
            });
        }
    }
/*
    @Override
    public ContactModel getById(Integer id) {
        ContactAO contactAO = ao.get(ContactAO.class, id);
        if (contactAO != null) {
            return greateModel(contactAO);
        }
        return null;
    }
*/
    @Override
    public UserDataModel getByKey(String key){
        UserDataAO[] userDataAOs = querySelect("USER_KEY", key);
        UserDataBlobAO[] userDataBlobAOs = querySelectBlob("USER_KEY", key);
        /*List<UserDataInterface> newList = Stream.concat(Stream.of(userDataAOs), Stream.of(userDataBlobAOs))
                             .collect(Collectors.toList());
                             */
        UserDataModel userDataModel = new UserDataModel(key);
        if (userDataAOs.length > 0) {
            for (UserDataAO userDataAO : userDataAOs){
                userDataModel.putData(userDataAO.getKey(), greateUnitModel(userDataAO));
            }
        }
        if (userDataBlobAOs.length > 0) {
            for (UserDataBlobAO userDataAO : userDataBlobAOs){
                userDataModel.putData(userDataAO.getKey(), greateUnitBlobModel(userDataAO));
            }
        }
        return userDataModel;
    }
}
