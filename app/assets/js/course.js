$(function() {

    function loadCourse() {


        $.ajax({
          url: "/admin/api/course/" + courseId,
          success: function( data ) {

            $.each(data, function(index,item) {
                $("#courses").append("<tr><td><a href='/admin/course/" + item.course_id + "'>" + item.name + "</a></td><td><a class='delete_course' data-id='" + item.course_id + "' href='#'>del</a></td></tr>");
            });

          }
        });

    }

/*
    $('body').on('click', '.delete_course', function() {

       $.ajax({
             type: "DELETE",
             url: "/admin/api/course/" + $(this).data("id"),
             success: function( data ) {

                loadCourses();

             }
           });

    });*/

    loadCourse();

});