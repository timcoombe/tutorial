$(function() {

    function loadCourses() {

        $("#courses tr").remove();

        $.ajax({
          url: "/admin/api/courses",
          success: function( data ) {

            $.each(data, function(index,item) {
                $("#courses").append("<tr><td><a href='/admin/course/" + item.course_id + "'>" + item.name + "</a></td><td><a class='delete_course' data-id='" + item.course_id + "' href='#'>del</a></td></tr>");
            });


          }
        });

    }

    function deleteCourse(courseId){

     $.ajax({
         type: "DELETE",
         url: "/admin/api/course/" + courseId,
         success: function( data ) {

            loadCourses();

         }
       });
    }



    $('body').on('click', '.delete_course', function() {

       var courseId = $(this).data("id");

       bootbox.confirm("Are you sure you want to delete this course?", function(result) {
           if(result){
                deleteCourse(courseId);
           }
       });

    });

    loadCourses();

});