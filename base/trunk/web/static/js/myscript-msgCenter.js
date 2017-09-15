$(function(){
	//----------自定义侧边栏 end----------
	
	//高级查找
	$('.abtn-advSearch').click(function(e){
		e.preventDefault();
		$('.msg-advSearch').show();
	});
	//邮件列表
	$('.mailing-list tr:not(".thead,.tfoot")').hover(function(){
		$(this).addClass('hover').siblings('tr').removeClass('hover');
	},function(){
		$(this).removeClass('hover');
	});
	$('.mailing-list tr .ui-checkbox').click(function(){
		if(!$(this).hasClass('ui-checkbox-current')){
			$(this).parents('tr').removeClass('current');
		}else{
			$(this).parents('tr').addClass('current');
		}
	});
	$('td,.msg-info-tt').on('click','.i-normal',function(e){
		e.preventDefault();
		e.stopPropagation();
		$(this).addClass('i-import').removeClass('i-normal').attr('title','取消星标邮件');
	});
	$('td,.msg-info-tt').on('click','.i-import',function(e){
		e.preventDefault();
		e.stopPropagation();
		$(this).addClass('i-normal').removeClass('i-import').attr('title','设为星标邮件');
	});
	//移动到
	$('.move-wrap .move-btn').click(function(e){
		e.preventDefault();
		$('.move-wrap .move-inner').hide();
		$(this).siblings('.move-inner').show();
	});
	$('.move-wrap .move-inner a').click(function(e){
		e.preventDefault();
		$(this).parent('.move-inner').hide();
	});
	$(document).click(function(event){
		var eo=$(event.target);
		if(eo.attr("class")!="move-wrap" && !eo.parent(".move-wrap").length)
		$('.move-wrap .move-inner').hide();  
	});
	//邮件详情
	$('.more-userList .more').click(function(e){
		e.preventDefault();
		if(!$(this).hasClass('more-all')){
			$(this).addClass('more-all').text('收起');
			$(this).siblings('p').css('height','auto');
		}else{
			$(this).removeClass('more-all').text('更多');
			$(this).siblings('p').css('height','24px');
		};
	});
	$('.msg-item-des').click(function(){
		$(this).hide().siblings('.msg-item-con').show();
		$(this).parent('.msg-item').siblings('.msg-item').children('.msg-item-con').hide().siblings('.msg-item-des').show();
	});
	$('.msg-hide-con').click(function(e){
		e.preventDefault();
		$(this).parents('.msg-item-con').hide().siblings('.msg-item-des').show();
	});
})