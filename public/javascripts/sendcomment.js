/**
 * Send comment as ajax action
 *
 * Created by dru on 13.01.2015.
 */
$("#commentForm").submit(function () {
        jsRoutes.controllers.Posts.addComment($("#blogpostContainer").data("post-id")).ajax({
            success: function (data) {
                $("#commentsContainer").append("<p>" + data.body + "</p><small>By a " + data.name + "</small><hr/>");
                $('#commentForm').each(function () {
                    this.reset();
                });
            },
            error: function () {
                alert("Error!")
            },
            data: $("#commentForm").serialize()
        })
        return false;
    }
)