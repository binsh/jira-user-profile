package com.kolhozcustoms.atlas.plugins.userprofile.manager.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.atlassian.activeobjects.external.ActiveObjects;
import net.java.ao.Query;
import com.kolhozcustoms.atlas.plugins.userprofile.data.ao.FieldSchemeAO;
import com.kolhozcustoms.atlas.plugins.userprofile.data.model.FieldSchemeModel;
import com.kolhozcustoms.atlas.plugins.userprofile.manager.FieldSchemeManager;

public class FieldSchemeManagerImpl implements FieldSchemeManager {
    private final ActiveObjects ao;

    public FieldSchemeManagerImpl(ActiveObjects ao) {
        this.ao = ao;
    }

    private FieldSchemeAO[] querySelect(String fieldName, String fieldValue){
        return ao.find(FieldSchemeAO.class, Query.select().where("\"" + fieldName + "\" = ?", fieldValue).order("FIELD_ORDER"));
    }

    private FieldSchemeModel greateModel(FieldSchemeAO fieldSchemeAO){
        if (fieldSchemeAO != null){
            return new FieldSchemeModel(fieldSchemeAO.getID(), fieldSchemeAO.getKey(), fieldSchemeAO.getDisplayName(), 
            fieldSchemeAO.getFieldType(), fieldSchemeAO.getFieldDescription(), fieldSchemeAO.getFieldLength(),
            fieldSchemeAO.getHidden(), fieldSchemeAO.getAllowHide(), fieldSchemeAO.getFieldOrder());
        }
        return new FieldSchemeModel();
    }

    @Override
    public List<FieldSchemeModel> getAllFields() {
        FieldSchemeAO[] fieldSchemeAOs = ao.find(FieldSchemeAO.class, Query.select().order("FIELD_ORDER"));
        List<FieldSchemeModel> fieldSchemeModels = new ArrayList<>();
        return Stream.of(fieldSchemeAOs)
                .map(fieldSchemeAO -> greateModel(fieldSchemeAO))
                .collect(Collectors.toList());
    }

    @Override
    public FieldSchemeModel update(FieldSchemeModel fieldSchemeModel) { // for RESTfull table
        FieldSchemeAO fieldSchemeAO = ao.get(FieldSchemeAO.class, fieldSchemeModel.getId());
        if (fieldSchemeAO != null) {
            if (fieldSchemeModel.getKey() != null) {
                fieldSchemeAO.setKey(fieldSchemeModel.getKey());
            }
            if (fieldSchemeModel.getDisplayName() != null) {
                fieldSchemeAO.setDisplayName(fieldSchemeModel.getDisplayName());
            }
            if (fieldSchemeModel.getFieldType() != null) {
                fieldSchemeAO.setFieldType(fieldSchemeModel.getFieldType());
            }
            if (fieldSchemeModel.getFieldDescription() != null) {
                fieldSchemeAO.setFieldDescription(fieldSchemeModel.getFieldDescription());
            }
            if (fieldSchemeModel.getFieldLength() != null) {
                fieldSchemeAO.setFieldLength(fieldSchemeModel.getFieldLength());
            }
            //if (fieldSchemeModel.getHidden() != null) {
                fieldSchemeAO.setHidden(fieldSchemeModel.getHidden());
            //}
            //if (fieldSchemeModel.getAllowHide() != null) {
                fieldSchemeAO.setAllowHide(fieldSchemeModel.getAllowHide());
            //}
            if (fieldSchemeModel.getFieldOrder() != null) {
                fieldSchemeAO.setFieldOrder(fieldSchemeModel.getFieldOrder());
            }
            fieldSchemeAO.save();
            fieldSchemeModel.setId(fieldSchemeAO.getID());
            fieldSchemeModel.setKey(fieldSchemeAO.getKey());
            fieldSchemeModel.setDisplayName(fieldSchemeAO.getDisplayName());
            fieldSchemeModel.setFieldType(fieldSchemeAO.getFieldType());
            fieldSchemeModel.setFieldDescription(fieldSchemeAO.getFieldDescription());
            fieldSchemeModel.setFieldLength(fieldSchemeAO.getFieldLength());
            fieldSchemeModel.setHidden(fieldSchemeAO.getHidden());
            fieldSchemeModel.setAllowHide(fieldSchemeAO.getAllowHide());
            fieldSchemeModel.setFieldOrder(fieldSchemeAO.getFieldOrder());
        }
        return fieldSchemeModel;
    }

    @Override
    public void save(FieldSchemeModel fieldSchemeModel) {
        System.out.println(fieldSchemeModel.toString());
        FieldSchemeAO[] fieldSchemeAOs = querySelect("KEY", fieldSchemeModel.getKey());
        if (fieldSchemeAOs.length > 0) {
            Stream.of(fieldSchemeAOs).forEach(fieldSchemeAO -> {
                fieldSchemeAO.setKey(fieldSchemeModel.getKey());
                fieldSchemeAO.setDisplayName(fieldSchemeModel.getDisplayName());
                fieldSchemeAO.setFieldType(fieldSchemeModel.getFieldType());
                fieldSchemeAO.setFieldDescription(fieldSchemeModel.getFieldDescription());
                fieldSchemeAO.setFieldLength(fieldSchemeModel.getFieldLength());
                fieldSchemeAO.setHidden(fieldSchemeModel.getHidden());
                fieldSchemeAO.setAllowHide(fieldSchemeModel.getAllowHide());
                fieldSchemeAO.setFieldOrder(fieldSchemeModel.getFieldOrder());
                fieldSchemeAO.save();
                fieldSchemeModel.setId(fieldSchemeAO.getID());
            });
        } else {
            FieldSchemeAO fieldSchemeAO = ao.create(FieldSchemeAO.class);
            fieldSchemeAO.setKey(fieldSchemeModel.getKey());
            fieldSchemeAO.setDisplayName(fieldSchemeModel.getDisplayName());
            fieldSchemeAO.setFieldType(fieldSchemeModel.getFieldType());
            fieldSchemeAO.setFieldDescription(fieldSchemeModel.getFieldDescription());
            fieldSchemeAO.setFieldLength(fieldSchemeModel.getFieldLength());
            fieldSchemeAO.setHidden(fieldSchemeModel.getHidden());
            fieldSchemeAO.setAllowHide(fieldSchemeModel.getAllowHide());
            fieldSchemeAO.setFieldOrder(fieldSchemeModel.getFieldOrder());
            fieldSchemeAO.save();
            fieldSchemeModel.setId(fieldSchemeAO.getID());
        }
    }

    @Override
    public void deleteById(Integer id) {
        FieldSchemeAO fieldSchemeAO = ao.get(FieldSchemeAO.class, id);
        if (fieldSchemeAO != null) {
            ao.delete(fieldSchemeAO);
        }
    }

    @Override
    public FieldSchemeModel getById(Integer id) {
        FieldSchemeAO fieldSchemeAO = ao.get(FieldSchemeAO.class, id);
        if (fieldSchemeAO != null) {
            return greateModel(fieldSchemeAO);
        }
        return null;
    }

    @Override
    public FieldSchemeModel getByKey(String key){
        FieldSchemeAO[] fieldSchemeAOs = querySelect("KEY", key);
        if (fieldSchemeAOs.length > 0) {
            return greateModel(fieldSchemeAOs[0]);
        }
        return greateModel(null);
    }
}
