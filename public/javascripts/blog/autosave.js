$(document).ready(function () {

    var mardownEditor = $('.CodeMirror')[0].CodeMirror;

    //auto saving
    var $status = $('#status'),
        $commentBox = $('#commentBox'),
        timeoutId;

    var tryAutoSave = function () {
        //autosave disabled
        if(!($('#checkbox-autosave-on').prop('checked'))){
           $status.attr('class', 'pending').text('changes pending');
           return;
        }

        $status.attr('class', 'pending').text('changes pending');
        if (timeoutId)
            clearTimeout(timeoutId);
        timeoutId = setTimeout(function () {
            updatePost($("#postForm").data("post-id"));
        }, 850);
    }

    var onUpdateSuccess = function (data) {
        $status.attr('class', 'saved').text('changes saved');
    }

    var onUpdateError = function (xhr, statusCode, errorThrown) {

        var statusCode = xhr.status;

        var errorMessage;

        if (statusCode == 0) {
            errorMessage = "You're offline, not saved!";
        } else
            errorMessage = (xhr.responseText);

        $status.attr('class', 'error').text(errorMessage);
    }

    var updatePost = function (postId) {
        var ajaxCallBack = {
            success: onUpdateSuccess,
            error: onUpdateError,
            data: $("#postForm").serialize()
        }
        jsRoutes.controllers.Posts.doEdit(postId).ajax(ajaxCallBack);
    };

    mardownEditor.on('change', tryAutoSave);

    $('#titleArea').on('input propertychange', tryAutoSave);

    $('#privateCheckBox').on('change',tryAutoSave);


});