$(function() {

    function loadCourse() {

        $("#lessons tr").remove();

        $.ajax({
          url: "/admin/api/course/" + courseId,
          success: function( data ) {

               $('#course_id').val(data.course_id);
               $('#name').val(data.name);
               $('#description').val(data.description);
               $("#lesson_course_id").val(data.course_id);
          }
        });



      $.ajax({
          url: "/admin/api/course/" + courseId + "/lessons",
          success: function( data ) {
            $.each(data, function(index,item) {
                $("#lessons").append("<tr><td><a href='/admin/lesson/" + item.lesson_id + "'>" + item.name + "</a></td><td><a class='delete_lesson' data-id='" + item.lesson_id + "' href='#'>del</a></td></tr>");
            });          }
        });



    }

   $('body').on('click', '.update', function() {

      var fd = new FormData();
      fd.append( 'course_id', $('#course_id').val());
      fd.append( 'name', $('#name').val());
      fd.append( 'description', $('#description').val());

      $.ajax({
        url: '/admin/api/course/' + $('#course_id').val(),
        data: fd,
        processData: false,
        contentType: false,
        type: 'PUT',
        success: function(data){
          //alert(data);
        }
      });

      return false;

    });


      $('body').on('click', '.delete_lesson', function() {

           $.ajax({
                 type: "DELETE",
                 url: "/admin/api/lesson/" + $(this).data("id"),
                 success: function( data ) {

                    loadCourse();

                 }
               });

        });

    loadCourse();

});