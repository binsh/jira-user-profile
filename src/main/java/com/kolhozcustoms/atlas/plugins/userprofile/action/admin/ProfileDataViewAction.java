package com.kolhozcustoms.atlas.plugins.userprofile.action.admin;

import com.atlassian.jira.web.action.JiraWebActionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Optional.ofNullable;

import static com.atlassian.jira.permission.GlobalPermissionKey.ADMINISTER;

public class ProfileDataViewAction extends JiraWebActionSupport {
    private static final Logger log = LoggerFactory.getLogger(ProfileDataViewAction.class);

    private boolean hasAdminPermission() {
        return ofNullable(getLoggedInUser())
                .map(x -> getGlobalPermissionManager().hasPermission(ADMINISTER, x))
                .orElse(false);
    }
}
