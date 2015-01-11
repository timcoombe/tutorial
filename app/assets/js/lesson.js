$(function() {

    function addLessonParagraph(lessonPart){

        var paragraphHTML = "<tr><td>";

        paragraphHTML = paragraphHTML +  "<table class='table lesson-part'>";

        paragraphHTML = paragraphHTML + "<tr><td><a href='/admin/lessonpart/" + lessonPart.lesson_part_id + "'>edit</a></td></tr>";
        paragraphHTML = paragraphHTML + "<tr><td>" + lessonPart.headline + "</td></tr>";
        paragraphHTML = paragraphHTML +  "<tr><td>" + lessonPart.text + "</td></tr>";

        paragraphHTML = paragraphHTML +  "</table>";
        paragraphHTML = paragraphHTML +  "</td></tr>";


        $("#lessons-parts").append(paragraphHTML);

      }

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

   function loadLessonParts() {
        $.ajax({
           url: "/admin/api/lesson/" + lessonId + "/parts",
           success: function( data ) {

             $.each(data, function(index,item) {

                switch(item.jsonClass){
                    case "LessonPartParagraph":
                        addLessonParagraph(item);
                        break;
                    default:
                        alert("Type not recognised");
                }



              //  $("#lesson-p").append("<tr><td><a href='/admin/course/" + item.course_id + "'>" + item.name + "</a></td><td><a class='delete_course' data-id='" + item.course_id + "' href='#'>del</a></td></tr>");
             });

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
    loadLessonParts();

});