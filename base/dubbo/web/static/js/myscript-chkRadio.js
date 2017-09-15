 function vselect(){
	//模拟下拉框
	var $zIndex=999;
	$(".ui-select-box").each(function(index){
		var $sel_w=$(this).width()+2;
		var $txt_w=$sel_w-25;
		$(this).find(".ui-option").width($sel_w);
		$(this).find(".ui-select-txt").width($txt_w);
	});
	$(".ui-select-box-disable").each(function(index){
		$(this).find('.ui-select-txt').attr("readonly","readonly");
	});
	$("body").off('click','.ui-select-box:not(".ui-select-box-disable")').on('click','.ui-select-box:not(".ui-select-box-disable")',function(event){
		event.stopPropagation();
		var $index=$(".ui-select-box").index(this);
		$(this).find(".ui-option").toggle();		
		$(this).find(".ui-select-close").toggleClass("ui-select-open");
		$(".ui-select-box .ui-select-close").not(".ui-select-close:eq("+$index+")").removeClass("ui-select-open");
		$(".ui-select-box .ui-option").not(".ui-option:eq("+$index+")").hide();
		$(".select_box02").each(function(){
			var container = $(this).children(".select_list02_container");
			if(container.is(":visible")  && $(this).children(".select_current02").size() > 0){
				$(this).children(".select_current02").removeClass("select_current02_hover").end().children(".select_list02_container").hide();	
			}
		});
	});
	
	function documentVselectClick(event){
		var eo=$(event.target);
		if($(".ui-select-box").is(":visible") && eo.attr("class")!="ui-option" && !eo.parent(".ui-option").length)
		$('.ui-option').hide();  
		$(".ui-select-close").removeClass("ui-select-open");  
		
		//选择弹出层
		$(".select_box02").each(function(){
			var container = $(this).children(".select_list02_container");
			if(container.is(":visible") && eo.parents('.select_box02').length == 0 && $(this).children(".select_current02").size() > 0){
				$(this).children(".select_current02").removeClass("select_current02_hover").end().children(".select_list02_container").hide();	
			}
		});
	
	};	
	$(document).unbind('click',documentVselectClick).click(documentVselectClick);
	
	$(".ui-option a").mouseover(function(){
		$(this).parent().find("a").addClass("no");
		$(this).addClass("hover").siblings("a").removeClass("hover");
	})
	$(".ui-option a").mouseout(function(){
		$(this).parent().find("a").removeClass("no").removeClass("hover");
	})
	/*默认赋值给文本框*/
	$(".ui-select-box").each(function(index){
		var $len=$(this).children(".ui-option").find("a.selected").length;
		if($len==0){
			var $val_txt=$(this).find(".a-wrap").children("a:first").text();
			var $val_zhi=$(this).find(".a-wrap").children("a:first").attr("val");
			$(this).find(".a-wrap").children("a:first").addClass("selected");
		}else{
			var $val_txt=$(this).find(".a-wrap").find("a.selected").text();
			var $val_zhi=$(this).find(".a-wrap").find("a.selected").attr("val");
		}
		$(this).children(".ui-select-txt").val($val_txt);
		$(this).children(".ui-select-value").val($val_zhi);
	})
	/*点击后赋值给文本框*/
	$("body").off('click','.ui-option .a-wrap a').on('click','.ui-option .a-wrap a',function(event){
		var $val_txt=$(this).text();
		var $val_zhi=$(this).attr("val");
		$(this).parents('.ui-option').siblings(".ui-select-txt").val($val_txt);
		$(this).parents('.ui-option').siblings(".ui-select-value").val($val_zhi);
		$(this).addClass("selected").siblings("a").removeClass("selected");
		
		//自定义属性或事件
		var option = $(this).parent().parent();
		var optionId = option.attr('id');
	    if(typeof(option.attr("myfunchange"))!="undefined"){
			eval(option.attr("myfunchange")+"('"+$val_zhi+"','"+$val_txt+"','"+optionId+"')");
		}		
		option.trigger("myeventchange",[$val_zhi,$val_txt,optionId]);
	});
	//	模拟单选框
	$('.ui-radio:not(".ui-radio-disabled,.ui-radio-disabled-checked")').unbind('click').click(function(){
		var $radioName=$(this).attr('data-name');
		$(document).find('.ui-radio[data-name="'+$radioName+'"]').removeClass('ui-radio-current');
		$(document).find('.ui-radio[data-name="'+$radioName+'"]').find('.radio').removeAttr('checked');
		$(this).addClass('ui-radio-current');
		$(this).children('.radio').attr('checked','checked');
		$(this).children('.radio').trigger("onclick");
	});
	
	//模拟复选框	
	$('.ui-checkbox:not(".ui-checkbox-all,.ui-checkbox-disabled,.ui-checkbox-disabled-checked")').unbind('click').click(function(){
		var chkLen=$(this).parents('form').find('.ui-checkbox').not('.ui-checkbox-all').length;
		if(!$(this).hasClass('ui-checkbox-current')){
			$(this).addClass('ui-checkbox-current').find('.chk').attr('checked','checked');
			var chkedLen=$(this).parents('form').find('.ui-checkbox-current').not('.ui-checkbox-all').length;
			if(chkLen==chkedLen){
				$(this).parents('form').find('.ui-checkbox-all').addClass('ui-checkbox-current').find('.chk').attr('checked','checked');
				$(this).parents('form').find('.ui-checkbox-all').attr('data-all','yes');
			};
		}else{
			$(this).removeClass('ui-checkbox-current').find('.chk').removeAttr('checked');
			$(this).parents('form').find('.ui-checkbox-all').removeClass('ui-checkbox-current').find('.chk').removeAttr('checked');
			$(this).parents('form').find('.ui-checkbox-all').attr('data-all','no');
		};
		if(typeof($(this).attr("myfunclick"))!="undefined"){
			eval($(this).attr("myfunclick")+"()");
		}		
		//$(this).trigger("myeventchange",[$val_zhi,$val_txt,optionId]);
	});
	$('.ui-checkbox-all').unbind('click').click(function(){
		var chkAll=$(this).attr('data-all');
		if(chkAll=="no"){
			$(this).attr('data-all','yes').parents('form').find('.ui-checkbox:not(".ui-checkbox-disabled,.ui-checkbox-disabled-checked")').addClass('ui-checkbox-current').find('.chk').attr('checked','checked');
		}else{
			$(this).attr('data-all','no').parents('form').find('.ui-checkbox:not(".ui-checkbox-disabled,.ui-checkbox-disabled-checked")').removeClass('ui-checkbox-current').find('.chk').removeAttr('checked');
		};
	});
	
	$(".ui-select-box,.select_box,.select_box02").each(function(){
		i=--$zIndex;
		$(this).css("z-index",i);
	})
}
