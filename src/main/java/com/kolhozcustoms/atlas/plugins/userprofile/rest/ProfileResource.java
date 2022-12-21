package com.kolhozcustoms.atlas.plugins.userprofile.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import com.atlassian.jira.security.GlobalPermissionManager;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.user.util.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kolhozcustoms.atlas.plugins.userprofile.manager.UserDataManager;
import com.kolhozcustoms.atlas.plugins.userprofile.data.model.UserDataModel;

//import static java.util.Comparator.comparing;
//import static java.util.stream.Collectors.toList;

import static com.atlassian.jira.permission.GlobalPermissionKey.ADMINISTER;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.FORBIDDEN;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.status;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.math.NumberUtils.isDigits;

@Path("/profiles")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProfileResource {
    private static final Logger log = LoggerFactory.getLogger(ProfileResource.class);

    private final JiraAuthenticationContext authenticationContext;
    private final UserDataManager userDataManager;
    private final UserManager userManager;
    private final GlobalPermissionManager globalPermissionManager;

    public ProfileResource(
            JiraAuthenticationContext authenticationContext,
            UserDataManager userDataManager,
            UserManager userManager,
            GlobalPermissionManager globalPermissionManager) {
        this.authenticationContext = authenticationContext;
        this.userDataManager = userDataManager;
        this.userManager = userManager;
        this.globalPermissionManager = globalPermissionManager;
    }

    private Response checkPermission(boolean isAdmin){
        ApplicationUser currentUser = authenticationContext.getLoggedInUser();
        if (currentUser == null) {
            return status(UNAUTHORIZED)
                    .entity(authenticationContext.getI18nHelper().getText("com.kolhozcustoms.atlas.plugins.user-profile.rest.unauthorized"))
                    .build();
        }
        if (isAdmin && !globalPermissionManager.hasPermission(ADMINISTER, currentUser)) {
            log.warn("Invalid user:{} tries to access profile", currentUser.getName());
            return status(FORBIDDEN)
                    .entity(authenticationContext.getI18nHelper().getText("com.kolhozcustoms.atlas.plugins.user-profile.rest.notgranted"))
                    .build();
        }
        return null;
    }

    @GET
    public Response getUserByKey(@QueryParam("key") String key) {
        Response response = checkPermission(false);
        if (response != null) { return response;}
        ApplicationUser currentUser = authenticationContext.getLoggedInUser();
        ApplicationUser user = null;
        if (isNotBlank(key)) {
            user = userManager.getUserByKey(key);
        } else {
            user = currentUser;
        }
        if (user == null) {
            return status(NOT_FOUND)
                    .entity(authenticationContext.getI18nHelper().getText("com.kolhozcustoms.atlas.plugins.user-profile.rest.nouser"))
                    .build();
        }
        /*
        if (!globalPermissionManager.hasPermission(ADMINISTER, currentUser) && !currentUser.equals(user)) {
            log.warn("Invalid user:{} tries to access phone number", currentUser.getName());
            return status(FORBIDDEN)
                    .entity(authenticationContext.getI18nHelper().getText("com.kolhozcustoms.atlas.plugins.user-profile.rest.notgranted"))
                    .build();
        }
        */
        return Response.ok(userDataManager.getByKey(user.getKey())).build();
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response saveUserProfile(UserDataModel model) {
        Response response = checkPermission(false);
        if (response != null) { return response;}
        ApplicationUser currentUser = authenticationContext.getLoggedInUser();
        ApplicationUser user = null;
        if (isNotBlank(model.getKey())) {  
            user = userManager.getUserByKey(model.getKey());
        } else {
            return status(BAD_REQUEST)
                    .entity(authenticationContext.getI18nHelper().getText("com.kolhozcustoms.atlas.plugins.user-profile.rest.nouserkeyorname"))
                    .build();
        }

        if (user == null) {
            return status(NOT_FOUND)
                    .entity(authenticationContext.getI18nHelper().getText("com.kolhozcustoms.atlas.plugins.user-profile.rest.nouser"))
                    .build();
        }

        if (!globalPermissionManager.hasPermission(ADMINISTER, currentUser) && !currentUser.equals(user)) {
            log.warn("Invalid user:{} tries to access profile", currentUser.getName());
            return status(FORBIDDEN)
                    .entity(authenticationContext.getI18nHelper().getText("com.kolhozcustoms.atlas.plugins.user-profile.rest.notgranted"))
                    .build();
        }

        model.applyKey();
        userDataManager.save(model);
        System.out.println(model.toString()); //
        return Response.ok(model).build();
    }

    @GET
    @Path("/clear")
    public Response deleteAll() { //use carefully!!!
        Response response = checkPermission(true);
        if (response != null) { return response;}
        userDataManager.clear();
        return Response.ok().build();
    }
/*
    @GET
    @Path("/all")
    public Response getUserData() {
        Response response = checkPermission(true);
        if (response != null) { return response;}
        return Response.ok(userDataManager.getAllUserData()).build();
    }
*/

    @POST
    @Path("/self")
    public Response addContact(UserDataModel userDataModel) {
        Response response = checkPermission(true);
        if (response != null) { return response;}
        ApplicationUser applicationUser = userManager.getUserByKey(userDataModel.getKey());

        if (applicationUser == null) {
            throw new IllegalArgumentException(authenticationContext.getI18nHelper().getText("com.kolhozcustoms.atlas.plugins.user-profile.rest.nouser")); // why not response????
        }

        userDataModel.setKey(applicationUser.getKey());
        userDataModel.applyKey();
        userDataManager.save(userDataModel);
        return Response.ok(userDataModel).build();
    }

    @DELETE
    @Path("/self/{key}") // userkey, not ID
    public Response delete(@PathParam("key") String key) {
        Response response = checkPermission(true);
        if (response != null) { return response;}
        userDataManager.deleteByKey(key);
        return Response.ok().build();
    }

    @GET
    @Path("/self/{key}") // userkey, not ID
    public Response getProfile(@PathParam ("key") String key) {
        Response response = checkPermission(true);
        if (response != null) { return response;}
        UserDataModel userDataModel = userDataManager.getByKey(key);
        return Response.ok(userDataModel).build();
    }

    @PUT
    @Path("/self/{key}")
    public Response updateProfile(@PathParam ("key") String key, UserDataModel userDataModel) {
        Response response = checkPermission(true);
        if (response != null) { return response;}
        if (isBlank(key)) {  
            throw new IllegalArgumentException(authenticationContext.getI18nHelper().getText("com.kolhozcustoms.atlas.plugins.user-profile.rest.nokey"));
        }
        ApplicationUser applicationUser = userManager.getUserByKey(key);
        if (applicationUser == null) {
            throw new IllegalArgumentException(authenticationContext.getI18nHelper().getText("com.kolhozcustoms.atlas.plugins.user-profile.rest.nouser"));
        }
        userDataModel.applyKey();
        userDataManager.save(userDataModel); // TODO refact method, why not "save"? because need check NOT NULL value!
        return Response.ok(userDataModel).build();
    }
}
