$(document).ready(function () {
    //exit confirmation
    $("#postForm").submit(function () {
         $(window).off('beforeunload');
    })

    var titleOld = $('#title').serialize();

    var mardownEditor = $('.CodeMirror')[0].CodeMirror;
    var contentOld = mardownEditor.getValue();

    var isPrivateOld = $('#isPrivate').serialize();

    $(window).on('beforeunload', function (e) {

        var title = $('#title').serialize();
        var content = mardownEditor.getValue();
        var isPrivate = $('#isPrivate').serialize();

        if ((title !== titleOld) || (content !== contentOld) || (isPrivate !== isPrivateOld)) {
            return 'All changes will be lost!';
        }
    });

    //auto saving
    var $status = $('#status'),
        $commentBox = $('#commentBox'),
        timeoutId;

    mardownEditor.on('change', function(cm, e) {
         $status.attr('class', 'pending').text('changes pending');
         if (timeoutId)
            clearTimeout(timeoutId);
         timeoutId = setTimeout(function () {
            updatePost($("#postForm").data("post-id"));
         }, 850);
    });

    var onUpdateSuccess = function(data) {
        $status.attr('class', 'saved').text('changes saved');
    }

    var onUpdateError = function(error) {
         $status.attr('class', 'saved').text('changes saved');
    }

    var updatePost = function(postId) {
        var ajaxCallBack = {
            success : onUpdateSuccess,
            error : onUpdateError,
            data: $("#postForm").serialize()
        }
        jsRoutes.controllers.Posts.doEdit(postId).ajax(ajaxCallBack);
    };


});