$(function(){
/*--------------------------- Listener for data-reveal-class attributes ----------------------------*/
$("[data-reveal-class]").click(function(e){
	e.preventDefault();
	var modalLocation = $(this).attr("data-reveal-class");
	$("." + modalLocation).reveal();
});
/*--------------------------- Extend and Execute ----------------------------*/
$.fn.reveal = function(){
	/*--------------------------- Global Variables ----------------------------*/
	var modal = $(this),
		topMeasure =$(document).scrollTop() + $(window).height()/2-$(modal).height()/2,
		modalBG = $(".reveal-modal-bg");
	/*--------------------------- Create Modal BG ----------------------------*/
	if(modalBG.length == 0){
		modalBG = $("<div class='reveal-modal-bg' />").insertAfter(modal);
	}
	/*--------------------------- Open & Close Animations ----------------------------*/
	//Entrance Animations
	modal.bind("reveal:open",function(){
	    modal.css({"top":topMeasure,"display":"block"});
		modalBG.css({"display":"block"});
	});
	//Closing Animations
	modal.bind("reveal:close",function(){
		modal.css({"top":topMeasure,"display":"none"});
		modalBG.css({"display":"none"});	
	});
	/*--------------------------- add Open and Closing Listeners ----------------------------*/
	//Open Modal Immediately
	modal.trigger("reveal:open")
	//Close Modal Listeners
	$(".close-reveal-modal").click(function (){
		modal.trigger("reveal:close")
	});
}
});