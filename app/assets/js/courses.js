$(function() {
    $.ajax({
      url: "/admin/api/courses",
      success: function( data ) {

        $.each(data, function(index,item) {
            $("#courses").append("<tr><td>" + item.course_id + "</td><td>" + item.name + "</td></tr>");
        });


      }
    });



});