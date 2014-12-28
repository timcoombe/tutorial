$(function() {

    function loadCourse() {


        $.ajax({
          url: "/admin/api/course/" + courseId,
          success: function( data ) {

               $('#course_id').val(data.course_id);
               $('#name').val(data.name);
               $('#description').val(data.description);
          }
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

    loadCourse();

});