package com.kolhozcustoms.atlas.plugins.userprofile.manager;

import java.util.List;
import java.util.Map;

import com.atlassian.activeobjects.tx.Transactional;
import com.kolhozcustoms.atlas.plugins.userprofile.data.model.FieldSchemeModel;

@Transactional
public interface FieldSchemeManager {
    List<FieldSchemeModel> getAllFields();
    FieldSchemeModel update(FieldSchemeModel field);
    void save(FieldSchemeModel field);
    FieldSchemeModel getById(Integer id);
    FieldSchemeModel getByKey(String key);
    void deleteById(Integer id);
}
