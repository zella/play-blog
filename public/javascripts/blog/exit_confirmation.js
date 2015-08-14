 $( document ).ready(function() {

      var titleOld = $('#title').serialize();

      var mardownEditor = $('.CodeMirror')[0].CodeMirror;
      var contentOld = mardownEditor.getValue();

      var isPrivateOld = $('#isPrivate').serialize();

       $(window).bind('beforeunload', function(e){

          var title = $('#title').serialize();
          var content = mardownEditor.getValue();
          var isPrivate = $('#isPrivate').serialize();

          if((title !== titleOld)||(content !== contentOld)||(isPrivate !== isPrivateOld))
          {
             return 'All changes will be lost!';
          }
       });
 });