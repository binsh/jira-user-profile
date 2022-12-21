AJS.toInit(function () {
    function updateProfile(source, target, data) {
        AJS.$.ajax({
            url: AJS.contextPath() + source,
            type: "PUT",
            dataType : "json",
            contentType: "application/json",
            data: JSON.stringify(data),
            processData: false,
            success: function (response) {
                if(response.hasOwnProperty('data')){
                    for (var key in response.data) {
                        if ($("#up-profile-" + key).length){ 
                            AJS.$("#up-profile-" + key).html(response.data[key]["content"]);
                        }
                    }
                }
                AJS.dialog2("#profile-user-profile-dialog").hide();
                AJS.$("#profile-action-dialog").show().delay(10000).hide(400);
            },
            error: function (error) {
                if (error.responseText) {
                    AJS.$("#error").text(error.responseText).show();
                }
            }
        });
    }

    function loadProfile(source, target) {
        AJS.$.ajax({
            url: AJS.contextPath() + source,
            type: "GET",
            dataType : "json",
            contentType: "application/json",
            processData: false,
            success: function (response) {
                if(response.hasOwnProperty('data')){
                    for (var key in response.data) {
                        if ($("#up-profile-new-" + key).length){
                            AJS.$("#up-profile-new-" + key).val(response.data[key]["content"]);
                        }
                    }
                }
            },
            error: function (error) {
                if (error.responseText) {
                    AJS.$("#error").text(error.responseText).show();
                }
            }
        });
    }
    
    AJS.$('#update-user-preferences').on("submit", function(a){
        a.preventDefault();
        var target = $(this).attr("aria-controls");
        var data = {};
        var formdata = {};
        $(this).serializeArray().map(function(x){
            formdata[x.name] = {};
            formdata[x.name]['key'] = x.name;
            formdata[x.name]['content'] = x.value;
            formdata[x.name]['userKey'] = $(this).attr("data-id"); // not work
        });
        data['key'] = $(this).attr("data-id");
        data['data'] = formdata;
        console.log(JSON.stringify(data));
        updateProfile("/rest/userprofile/latest/profiles", target, data); //source , target, data
         return false;
    });
    AJS.$("#profile-dialog-close").click(function (e) {
        e.preventDefault();
        AJS.dialog2("#profile-user-profile-dialog").hide();
    });

    AJS.$("#edit-profile-profile-link").click(function(e) {
        e.preventDefault();
        loadProfile("/rest/userprofile/latest/profiles?key=" + $("#update-user-preferences").attr("data-id"), $(this).attr("aria-controls"));
        AJS.$("#error").text("");
        AJS.dialog2("#profile-user-profile-dialog").show();
    });
});
