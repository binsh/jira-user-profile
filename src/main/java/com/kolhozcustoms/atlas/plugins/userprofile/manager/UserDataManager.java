package com.kolhozcustoms.atlas.plugins.userprofile.manager;

import java.util.List;
import java.util.Map;

import com.atlassian.activeobjects.tx.Transactional;
import com.kolhozcustoms.atlas.plugins.userprofile.data.model.UserDataModel;

@Transactional
public interface UserDataManager {
    //List<UserDataModel> getAllUsersData();
    //UserDataModel update(UserDataModel userData);
    void save(UserDataModel userData);
    //UserDataModel getById(Integer id);
    UserDataModel getByKey(String key);
    void deleteById(Integer id);
    void deleteByKey(String key);
    void clear();
}
