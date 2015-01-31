$(function() {


    function loadParagraph() {

      $(".alert").hide();

        $.ajax({
          url: "/admin/api/lessonpart/" + partId,
          success: function( data ) {

               $('#lesson_id').val(data.lesson_id);
               $('#part_id').val(data.lesson_part_id);
               $('#headline').val(data.headline);
               $('#text').val(data.text);
           }
        });

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