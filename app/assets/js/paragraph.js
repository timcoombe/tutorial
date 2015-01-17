$(function() {


    function loadParagraph() {

      $(".alert").hide();

      /*  $.ajax({
          url: "/admin/api/lesson/" + lessonId,
          success: function( data ) {

               $('#course_id').val(data.course_id);
               $('#lesson_id').val(data.lesson_id);
               $('#name').val(data.name);
               $('#description').val(data.description);
           }
        });*/

    }


   $('body').on('click', '.update', function() {

      var fd = new FormData();
      fd.append( 'lesson_id', $('#lesson_id').val());
      fd.append( 'part_id', $('#part_id').val());
      fd.append( 'headline', $('#headline').val());
      fd.append( 'text', $('#text').val());

      $.ajax({
        url: '/admin/api/lesson/' + $('#lesson_id').val() + "/paragraph",
        data: fd,
        processData: false,
        contentType: false,
        type: 'POST',
        success: function(data){
          $(".alert").val("Saved successfully");
          $(".alert").show();
        }
      });

      return false;

    });


    loadParagraph();

});