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
import com.kolhozcustoms.atlas.plugins.userprofile.manager.FieldSchemeManager;
import com.kolhozcustoms.atlas.plugins.userprofile.data.model.FieldSchemeModel;

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

@Path("/scheme")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FieldSchemeResource {
    private static final Logger log = LoggerFactory.getLogger(FieldSchemeResource.class);

    private final JiraAuthenticationContext authenticationContext;
    private final FieldSchemeManager fieldSchemeManager;
    private final UserManager userManager;
    private final GlobalPermissionManager globalPermissionManager;

    public FieldSchemeResource(
            JiraAuthenticationContext authenticationContext,
            FieldSchemeManager fieldSchemeManager,
            UserManager userManager,
            GlobalPermissionManager globalPermissionManager) {
        this.authenticationContext = authenticationContext;
        this.fieldSchemeManager = fieldSchemeManager;
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
    public Response getFieldByKey(@QueryParam("key") String key) {
        Response response = checkPermission(false);
        if (response != null) { return response;}
        if (isBlank(key)) {
            return status(BAD_REQUEST)
                    .entity(authenticationContext.getI18nHelper().getText("com.kolhozcustoms.atlas.plugins.user-profile.rest.nokey"))
                    .build();
        }
        return Response.ok(fieldSchemeManager.getByKey(key)).build();
    }

    @PUT
    public Response storeField(FieldSchemeModel fieldSchemeModel) {
        Response response = checkPermission(true);
        if (response != null) { return response;}
        if (isBlank(fieldSchemeModel.getKey()) || isBlank(fieldSchemeModel.getDisplayName())) {
            return status(BAD_REQUEST)
                    .entity(authenticationContext.getI18nHelper().getText("com.kolhozcustoms.atlas.plugins.user-profile.rest.nokeyorname"))
                    .build();
        }
        fieldSchemeManager.save(fieldSchemeModel);
        System.out.println(fieldSchemeModel.toString()); //
        return Response.ok(fieldSchemeModel).build();
    }

    @GET
    @Path("/all")
    public Response getScheme() {
        Response response = checkPermission(false);
        if (response != null) { return response;}
        return Response.ok(fieldSchemeManager.getAllFields()).build();
    }

    @POST
    @Path("/self")
    public Response addField(FieldSchemeModel fieldSchemeModel) {
        Response response = checkPermission(true);
        if (response != null) { return response;}
        if (isBlank(fieldSchemeModel.getKey()) || isBlank(fieldSchemeModel.getDisplayName())) {
            throw new IllegalArgumentException(authenticationContext.getI18nHelper().getText("com.kolhozcustoms.atlas.plugins.user-profile.rest.nokeyorname"));
        }
        fieldSchemeManager.save(fieldSchemeModel);
        return Response.ok(fieldSchemeModel).build();
    }

    @DELETE
    @Path("/self/{id}")
    public Response delete(@PathParam("id") Integer id) {
        Response response = checkPermission(true);
        if (response != null) { return response;}
        fieldSchemeManager.deleteById(id);
        return Response.ok().build();
    }

    @GET
    @Path("/self/{id}")
    public Response getfieldScheme(@PathParam ("id") Integer id) {
        Response response = checkPermission(true);
        if (response != null) { return response;}
        return Response.ok(fieldSchemeManager.getById(id)).build();
    }

    @PUT
    @Path("/self/{id}")
    public Response updatefieldScheme(@PathParam ("id") Integer id, FieldSchemeModel fieldSchemeModel) {
        Response response = checkPermission(true);
        if (response != null) { return response;}
        if (isBlank(fieldSchemeModel.getKey()) || isBlank(fieldSchemeModel.getDisplayName())) {
            throw new IllegalArgumentException(authenticationContext.getI18nHelper().getText("com.kolhozcustoms.atlas.plugins.user-profile.rest.nokeyorname"));
        }
        fieldSchemeModel.setId(id);
        FieldSchemeModel newfieldSchemeModel = fieldSchemeManager.update(fieldSchemeModel); // TODO refact method
        return Response.ok(newfieldSchemeModel).build();
    }
}
