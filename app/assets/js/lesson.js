$(function() {

    function addLessonParagraph(lessonPart){

        var paragraphHTML = "<tr class='lesson-part-row'><td>";

        paragraphHTML = paragraphHTML +  "<table class='table lesson-part'>";

        paragraphHTML = paragraphHTML + "<tr><td>";
        paragraphHTML = paragraphHTML +     "<a href='/admin/lessonpart/" + lessonPart.lesson_part_id + "'><span class='glyphicon glyphicon-edit' aria-hidden='true'></span></a>";
        paragraphHTML = paragraphHTML +     "&nbsp;";
        paragraphHTML = paragraphHTML +     "<a class='delete-lesson-part' data-id='" + lessonPart.lesson_part_id + "' href='#'><span class='glyphicon glyphicon-remove' aria-hidden='true'></span></a>";
        paragraphHTML = paragraphHTML + "</td></tr>";

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

        $(".lesson-part-row").remove();

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


    function deleteLessonPart(lessonPartId){
        $.ajax({
                 type: "DELETE",
                 url: "/admin/api/lessonpart/" + lessonPartId,
                 success: function( data ) {

                    loadLessonParts();

                 }
         });
    }


    $('body').on('click', '.delete-lesson-part', function() {

        var lessonPartId = $(this).data("id");

        bootbox.confirm("Are you sure you want to delete this lesson part?", function(result) {
                 if(result){
                      deleteLessonPart(lessonPartId);
                 }
        });

        return false;

    });


    loadLesson();
    loadLessonParts();

});