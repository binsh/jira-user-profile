AJS.toInit(function () {
    AJS.$(document).on("click", ".checkbox", function(){
        $(this).val($(this).is(':checked'));
    });
    AJS.$(document).bind(AJS.RestfulTable.Events.SERVER_ERROR, function (e, response) {
        var myFlag = AJS.flag({
            type: 'error',
            close: 'auto',
            body: AJS.I18n.getText("com.kolhozcustoms.atlas.plugins.user-profile.admin.scheme.error") + '<br>' + response.message,
        });
    });
    var contactsTable = new AJS.RestfulTable({
        autoFocus: false,
        allowReorder: false,
        el: AJS.$("#up-profile-scheme-table"),
        resources: {
            all: AJS.contextPath() + "/rest/userprofile/latest/scheme/all",
            self: AJS.contextPath() + "/rest/userprofile/latest/scheme/self"
        },
        /*deleteConfirmationCallback: function(model) {
            AJS.$("#restful-table-model")[0].innerHTML = "<b>KEY</b> " + model.key + " <br><b>Name:</b> " + model.displayName;
            AJS.dialog2("#delete-confirmation-dialog").show();
            return new Promise(function(resolve, reject) {
                AJS.$("#dialog-submit-button").on('click', function (e) {
                    resolve();
                    e.preventDefault();
                    AJS.dialog2("#delete-confirmation-dialog").hide();
                });
                AJS.$(".aui-dialog2-header-close, #warning-dialog-cancel").on('click', function (e) {
                    reject();
                    e.preventDefault();
                    AJS.dialog2("#delete-confirmation-dialog").hide();
                });
            });
        },*/
        columns: [
            {
                id: "key",
                header: AJS.I18n.getText("com.kolhozcustoms.atlas.plugins.user-profile.admin.scheme.table.key") + ' *',
                allowEdit: true,
                allowCreate: true,
                createView: AJS.RestfulTable.CustomCreateView.extend({
                    render: function (self) {
                        var field = AJS.$('<input class="text" type="text" name="key" value="" data-aui-validation-field required" minlength="2">');
                        return field;
                    }
                }),
                editView: AJS.RestfulTable.CustomEditView.extend({
                    render: function (self) {
                        var field = AJS.$('<input class="text" type="text" name="key" value="false" data-aui-validation-field minlength="2">');
                        field.val(self.value);
                        return field;
                    }
                })
            },
            {
                id: "displayName",
                header: AJS.I18n.getText("com.kolhozcustoms.atlas.plugins.user-profile.admin.scheme.table.display-name") + ' *',
                allowEdit: true,
                allowCreate: true,
                createView: AJS.RestfulTable.CustomCreateView.extend({
                    render: function (self) {
                        var field = AJS.$('<input class="text" type="text" name="displayName" value="" data-aui-validation-field required" minlength="2">');
                        return field;
                    }
                }),
                editView: AJS.RestfulTable.CustomEditView.extend({
                    render: function (self) {
                        var field = AJS.$('<input class="text" type="text" name="displayName" value="false" data-aui-validation-field minlength="2">');
                        field.val(self.value);
                        return field;
                    }
                })
            },
            {
                id: "fieldType",
                header: AJS.I18n.getText("com.kolhozcustoms.atlas.plugins.user-profile.admin.scheme.table.field-type"),
                allowEdit: true,
                allowCreate: true,
                createView: AJS.RestfulTable.CustomCreateView.extend({
                    render: function (self) {
                        var field = AJS.$('<select class="select" name="fieldType">\
                        <option value="String">' + AJS.I18n.getText("com.kolhozcustoms.atlas.plugins.user-profile.admin.scheme.table.field-type.string") + '</option>\
                        <option value="Integer">' + AJS.I18n.getText("com.kolhozcustoms.atlas.plugins.user-profile.admin.scheme.table.field-type.integer") + '</option>\
                        <option value="Text">' + AJS.I18n.getText("com.kolhozcustoms.atlas.plugins.user-profile.admin.scheme.table.field-type.text") + '</option>\
                        <option value="Date">' + AJS.I18n.getText("com.kolhozcustoms.atlas.plugins.user-profile.admin.scheme.table.field-type.date") + '</option>\
                        <option value="DateTime">' + AJS.I18n.getText("com.kolhozcustoms.atlas.plugins.user-profile.admin.scheme.table.field-type.date-time") + '</option>\
                    </select>');
                        return field;
                    }
                }),
                editView: AJS.RestfulTable.CustomEditView.extend({
                    render: function (self) {
                        var field = AJS.$('<select class="select" name="fieldType">\
                        <option value="String">' + AJS.I18n.getText("com.kolhozcustoms.atlas.plugins.user-profile.admin.scheme.table.field-type.string") + '</option>\
                        <option value="Integer">' + AJS.I18n.getText("com.kolhozcustoms.atlas.plugins.user-profile.admin.scheme.table.field-type.integer") + '</option>\
                        <option value="Text">' + AJS.I18n.getText("com.kolhozcustoms.atlas.plugins.user-profile.admin.scheme.table.field-type.text") + '</option>\
                        <option value="Date">' + AJS.I18n.getText("com.kolhozcustoms.atlas.plugins.user-profile.admin.scheme.table.field-type.date") + '</option>\
                        <option value="DateTime">' + AJS.I18n.getText("com.kolhozcustoms.atlas.plugins.user-profile.admin.scheme.table.field-type.date-time") + '</option>\
                    </select>');
                    field.val(self.value).change();
                        return field;
                    }
                }),
                readView: AJS.RestfulTable.CustomReadView.extend({
                    render: function (self) {
                        var field = AJS.$('<select class="select" name="fieldType" disabled>\
                        <option value="String">' + AJS.I18n.getText("com.kolhozcustoms.atlas.plugins.user-profile.admin.scheme.table.field-type.string") + '</option>\
                        <option value="Integer">' + AJS.I18n.getText("com.kolhozcustoms.atlas.plugins.user-profile.admin.scheme.table.field-type.integer") + '</option>\
                        <option value="Text">' + AJS.I18n.getText("com.kolhozcustoms.atlas.plugins.user-profile.admin.scheme.table.field-type.text") + '</option>\
                        <option value="Date">' + AJS.I18n.getText("com.kolhozcustoms.atlas.plugins.user-profile.admin.scheme.table.field-type.date") + '</option>\
                        <option value="DateTime">' + AJS.I18n.getText("com.kolhozcustoms.atlas.plugins.user-profile.admin.scheme.table.field-type.date-time") + '</option>\
                    </select>');
                    field.val(self.value).change();
                        return field;
                    }
                })
            },
            {
                id: "fieldDescription",
                header: AJS.I18n.getText("com.kolhozcustoms.atlas.plugins.user-profile.admin.scheme.table.field-description"),
                allowEdit: true,
                allowCreate: true
            },
            {
                id: "fieldLength",
                header: AJS.I18n.getText("com.kolhozcustoms.atlas.plugins.user-profile.admin.scheme.table.field-length"),
                allowEdit: true,
                allowCreate: true,
                createView: AJS.RestfulTable.CustomCreateView.extend({
                    render: function (self) {
                        var field = AJS.$('<input class="text" type="number" name="fieldLength" value="" style="width: 6em;">');
                        return field;
                    }
                }),
                editView: AJS.RestfulTable.CustomEditView.extend({
                    render: function (self) {
                        var field = AJS.$('<input class="text" type="number" name="fieldLength" style="width: 6em;">');
                        field.val(self.value);
                        return field;
                    }
                })
            },
            {
                id: "hidden",
                header: AJS.I18n.getText("com.kolhozcustoms.atlas.plugins.user-profile.admin.scheme.table.hidden"),
                allowEdit: true,
                allowCreate: true,
                createView: AJS.RestfulTable.CustomCreateView.extend({
                    render: function (self) {
                        var field = AJS.$('<input class="checkbox" type="checkbox" name="hidden" value="false">');
                        return field;
                    }
                }),
                editView: AJS.RestfulTable.CustomEditView.extend({
                    render: function (self) {
                        var field = AJS.$('<input class="checkbox" type="checkbox" name="hidden" >');
                        field.val(self.value);
                        if (self.value == true){ field.prop( "checked", true );}
                        return field;
                    }
                }),
                readView: AJS.RestfulTable.CustomReadView.extend({
                    render: function (self) {
                        var field = AJS.$('<input class="checkbox" type="checkbox" name="hidden" disabled>');
                        field.val(self.value);
                        if (self.value == true){ field.prop( "checked", true );}
                        return field;
                    }
                })
            },
            {
                id: "allowHide",
                header: AJS.I18n.getText("com.kolhozcustoms.atlas.plugins.user-profile.admin.scheme.table.allow-hide"),
                allowEdit: true,
                allowCreate: true,
                createView: AJS.RestfulTable.CustomCreateView.extend({
                    render: function (self) {
                        var field = AJS.$('<input class="checkbox" type="checkbox" name="allowHide" value="false">');
                        return field;
                    }
                }),
                editView: AJS.RestfulTable.CustomEditView.extend({
                    render: function (self) {
                        var field = AJS.$('<input class="checkbox" type="checkbox" name="allowHide" >');
                        field.val(self.value);
                        if (self.value == true){ field.prop( "checked", true );}
                        return field;
                    }
                }),
                readView: AJS.RestfulTable.CustomReadView.extend({
                    render: function (self) {
                        var checked = (self.value == true) ? "checked" : "";
                        var field = AJS.$('<input class="checkbox" type="checkbox" name="allowHide" disabled>');
                        field.val(self.value);
                        if (self.value == true){ field.prop( "checked", true );}
                        return field;
                    }
                })
            },
            {
                id: "fieldOrder",
                header: AJS.I18n.getText("com.kolhozcustoms.atlas.plugins.user-profile.admin.scheme.table.field-order"),
                allowEdit: true,
                allowCreate: true,
                createView: AJS.RestfulTable.CustomCreateView.extend({
                    render: function (self) {
                        var field = AJS.$('<input class="text" type="number" name="fieldOrder" value="" style="width: 5em;">');
                        return field;
                    }
                }),
                editView: AJS.RestfulTable.CustomEditView.extend({
                    render: function (self) {
                        var field = AJS.$('<input class="text" type="number" name="fieldOrder" style="width: 5em;">');
                        field.val(self.value);
                        return field;
                    }
                })
            }
        ]
    });
});
