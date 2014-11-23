$(function() {
    $.ajax({
      url: "/admin/courses",
      success: function( data ) {

        $.each(data, function(index,item) {
            $("#courses").append("<li>" + item.name + "</li>");
        });


      }
    });



});