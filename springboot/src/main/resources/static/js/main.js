// config the draggable (the header name)
$(".emp").draggable({
  revert: "invalid",
  containment: "document",
  cursor: "move",
  start: function (event, ui) {
    if ($(this).hasClass("dropped")){
      $(this).removeClass("dropped");
    }
    $(this).addClass("dragged");
  },
  stop: function (event, ui) {
    $(this).removeClass("dragged");
  }
});

// config the droppable (the SSOT)
$(".job-emp").droppable({
  accept: ".emp",
  activeClass: "active",
  drop: function (event, ui) {
    // Check if the SSOT already has the field name to it, if yes, cannot drag a field name to it
    if ($(this).find(".dropped").length > 0){
      ui.draggable.draggable( "option", "revert", true );
    
    }else{
      ui.draggable.draggable( "option", "revert", "invalid" );
      ui.draggable.addClass("dropped");
      var fieldName = ui.draggable.text(); // retrieve what user dropped 
      ui.draggable.detach().appendTo($(this));
      $(this).parent().find("input").val(fieldName);    // assign to hidden value
    }
  }
});

// config the droppable (empty area to drop when need to change the mapping)
$(".content.headers").droppable({
  accept: ".emp",
  activeClass: "active",
  drop: function (event, ui) { 
    // drop the field name back to white area and unassign the value in hidden input
    ui.draggable.parent().parent().find("input").val(null);
    ui.draggable.detach().appendTo($(this));

    ui.draggable.addClass("dropped");
    ui.draggable.removeClass("dropped");

    droppableOffset = $(this).offset();
    var relX = event.pageX - $(this).parent().offset().left + 10;
    var relY = event.pageY - $(this).parent().offset().top; 

    ui.draggable.css({top: relY, left: relX, position:'absolute'});

  }
});

