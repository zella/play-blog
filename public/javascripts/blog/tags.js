$(document).ready(function () {

var tagContainer = $('#tag-container');
var tagsInput = $('#tags-input');


//function findAndRemove(array, value) {
//   $.each(array, function(index, result) {
//      if(result == value) {
//          //Remove from array
//          array.splice(index, 1);
//      }
//   });
//}

function removeFromJsonArray(arr, val) {
    for (var i = 0; i < arr.length; i++) if (arr[i] === val) arr.splice(i, 1);
    return arr;
}

//var removeFromJsonArray = function(jsonArray, item) {
////   for ( var i = 0; i < jsonArray.length; i++) {
////       var val = jsonArray[i];
//////       var val = jsonArray.getJSONObject(i).getString();
////       if (val == value) {
////           jsonArray.remove(i);
////       }
////   }
//
//     var index = jsonArray.indexOf(item);
//     jsonArray.splice(index, 1);
//     return jsonArray;
//};

var addTagClick = function() {

    var tagName = $('#tag-add-input').val();

    var tagHtml = "<div class=\"uk-button-group uk-margin-small-right uk-margin-small-bottom\"><div class=\"uk-button\">"
    + tagName + "</div><a class=\"uk-button tag-delete-btn\" data-tag=\"" + tagName + "\"\">x</a></div>";

    //createElement
    var tagElement = $(tagHtml);

    $(tagHtml).appendTo(tagContainer);

    var tagsInputVal = tagsInput.val();

    var jsonTags;
    if (tagsInputVal){
       jsonTags = JSON.parse(tagsInputVal);
    } else {
       jsonTags = [];
    }

    jsonTags.push(tagName);

    tagsInput.val(JSON.stringify(jsonTags));
    console.log(tagsInput.val());

    //add delete handler to [x] button
    tagContainer.on('click', '*[data-tag=\"'+tagName+ '\"]', function() {

        //TODO make better; worked.
        tagsInput.val(JSON.stringify(removeFromJsonArray(JSON.parse(tagsInput.val()), tagName)));
        console.log(tagsInput.val());
        $(this).parent().remove();
    });

    return false;

};

$('#btn-tag-add').on("click", addTagClick);

});
