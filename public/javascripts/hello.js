


var func = function () {

    alert('cl');
    jsRoutes.controllers.Application.ajaxCall().ajax({
        success: function (data) {
            alert("ajax!")
        },
        error: function () {
            alert("Error!")
        }
    })
};

window.onload = func

var ajaxCall = function () {

    alert('mama');
    var ajaxCallBack = {
        success: onSuccess,
        error: onError
    }

    jsRoutes.controllers.Application.ajaxCall().ajax(ajaxCallBack);

};

var onSuccess = function (data) {
    alert(data);
}

var onError = function (error) {
    alert(error);
}