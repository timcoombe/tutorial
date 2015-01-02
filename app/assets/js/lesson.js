$(function() {

    function loadLesson() {


        $.ajax({
          url: "/admin/api/lesson/" + lessonId,
          success: function( data ) {

               $('#course_id').val(data.course_id);
               $('#lesson_id').val(data.lesson_id);
               $('#name').val(data.name);
               $('#description').val(data.description);
           }
        });

    }

   $('body').on('click', '.update', function() {

      var fd = new FormData();
      fd.append( 'course_id', $('#course_id').val());
      fd.append( 'lesson_id', $('#lesson_id').val());
      fd.append( 'name', $('#name').val());
      fd.append( 'description', $('#description').val());

      $.ajax({
        url: '/admin/api/lesson/' + $('#lesson_id').val(),
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


    loadLesson();

});