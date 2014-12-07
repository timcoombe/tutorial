$(function() {

    function loadCourse() {


        $.ajax({
          url: "/admin/api/course/" + courseId,
          success: function( data ) {

               $('#course_id').val(data.courseId);
               $('#name').val(data.name);
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