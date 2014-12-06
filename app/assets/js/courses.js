$(function() {
    $.ajax({
      url: "/admin/courses",
      success: function( data ) {

        $.each(data, function(index,item) {
            $("#courses").append("<tr><td>" + item.name + "</td></tr>");
        });


      }
    });



});