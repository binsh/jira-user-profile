package com.kolhozcustoms.atlas.plugins.userprofile.panel;

import java.util.HashMap;
import java.util.Map;

import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.plugin.PluginParseException;
import com.atlassian.plugin.web.ContextProvider;
import com.kolhozcustoms.atlas.plugins.userprofile.manager.UserDataManager;
import com.kolhozcustoms.atlas.plugins.userprofile.manager.FieldSchemeManager;

public class ProfileExtendedPanel implements ContextProvider {
    private final UserDataManager userDataManager;
    private final FieldSchemeManager fieldSchemeManager;

    public ProfileExtendedPanel(FieldSchemeManager fieldSchemeManager, UserDataManager userDataManager) {
        this.userDataManager = userDataManager;
        this.fieldSchemeManager = fieldSchemeManager;
    }

    @Override
    public void init(Map<String, String> params) throws PluginParseException {
    }

    @Override
    public Map<String, Object> getContextMap(Map<String, Object> context) {
        ApplicationUser currentUser = (ApplicationUser) context.get("currentUser");
        ApplicationUser profileUser = (ApplicationUser) context.get("profileUser");
        Map<String, Object> result = new HashMap<>(context);
        result.put("scheme", fieldSchemeManager.getAllFields());
        result.put("data", userDataManager.getByKey(profileUser.getKey()).getData());
        result.put("isProfileOwner", currentUser.equals(profileUser));
        System.out.println(result);
        return result;
    }
}
