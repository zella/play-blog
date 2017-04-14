$(document).ready(function () {
   // exit confirmation
    $("#postForm").submit(function () {
        $(window).off('beforeunload');
    })

    var mardownEditor = $('.CodeMirror')[0].CodeMirror;
    var previewEditor = $('.CodeMirror')[1].CodeMirror;

    var titleOld = $('#titleArea').val();
    var contentOld = mardownEditor.getValue();
    var previewOld = previewEditor.getValue();
    var isPrivateOld = $('#privateCheckBox').is(":checked");

    $(window).on('beforeunload', function (e) {

        var title = $('#titleArea').val();
        var content = mardownEditor.getValue();
        var preview = previewEditor.getValue();
        var isPrivate = $('#privateCheckBox').is(":checked");

        if ((title !== titleOld) || (content !== contentOld) || (isPrivate !== isPrivateOld) || (preview !== previewOld)) {

            return 'All changes will be lost!';
        }
    });

    previewEditor.setSize("100%",250);
});