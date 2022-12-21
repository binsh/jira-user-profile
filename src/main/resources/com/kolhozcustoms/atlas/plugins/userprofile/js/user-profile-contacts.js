AJS.toInit(function () {
    var contactsTable = new AJS.RestfulTable({
        autoFocus: false,
        allowReorder: false,
        el: AJS.$("#up-profile-contacts-table"),
        resources: {
            all: AJS.contextPath() + "/rest/userprofile/latest/profiles/all",
            self: AJS.contextPath() + "/rest/userprofile/latest/profiles/self"
        },
        columns: [
            {
                id: "userName",
                header: AJS.I18n.getText("com.kolhozcustoms.atlas.plugins.user-profile.admin.profile.table.user"),
                allowEdit: true,
                allowCreate: true
            },
            {
                id: "phone",
                header: AJS.I18n.getText("com.kolhozcustoms.atlas.plugins.user-profile.admin.profile.table.phone"),
                allowEdit: true,
                allowCreate: true
            }
        ]
    });
});
