$(function(){
	//-----=S 主导航 Start-----
	function windowAuto(){
		//初始化框架高度
		var reWindow=$(window).height();
		var navHeight=reWindow-40;
		var navTop=(navHeight-80)/2;
		$('.main-nav,.nav-wrap').height(navHeight);
		$('.nav-cut').css('top',navTop);
		$('.nav-tips').css({'top':navTop-55,'right':'-180px'});
		
		//提示
		var t1_1,t1_2
		function clearTipsAuto(){
			clearTimeout(t1_1);
			clearTimeout(t1_2);
		};
		function tipsAuto(){
			clearTipsAuto();
			var navTop=parseInt($('.nav-cut').css('top'));
			t1_1=$('.nav-tips').animate({'top':navTop-75},300).animate({'top':navTop-55},300);
			t1_2=setTimeout(function(){tipsAuto()},3600);
		};
		tipsAuto();
	};
	windowAuto();
	$(window).resize(function(){windowAuto();});
	$('.nav-tips .close').click(function(){
		$('.nav-tips').remove();
		return false;
	});
	//滚动条
	$('.mCustomScrollbar').mCustomScrollbar();
	//展开收起
	$('.nav-cut').click(function(e){
		e.preventDefault();
		if($(this).hasClass('nav-show')){
			//展开按钮点击效果
			$(this).attr('title','收起');
			$(this).addClass('nav-hide').removeClass('nav-show');
			$('.main-nav').addClass('main-nav-show').animate({'left':0},200);
			$('.mCSB_scrollTools .mCSB_draggerContainer').show();
		}else{
			//收起按钮点击效果
			$(this).attr('title','展开');
			$(this).addClass('nav-show').removeClass('nav-hide');
			$('.main-nav').removeClass('main-nav-show').animate({'left':'-145px'},200);
			$('.mCSB_scrollTools .mCSB_draggerContainer').hide();
		};
	});
	//鼠标进入展开二级菜单
	$('body').on('mouseover','.main-nav-show .item',function(e){
		
		e.stopPropagation();
		e.stopImmediatePropagation();
		$('.item-tt a').stop();
		$('.item-wrap').hide().stop();
		
		if($(this).hasClass('item2')){
			$(this).children('.item-tt').addClass('item-tt-hover').children('a').stop(true,true).animate({'left':'33px'},500);
		}else{
			$(this).children('.item-tt').addClass('item-tt-hover').children('a').stop(true,true).animate({'left':'20px'},500);
		};
		if($(this).children('.item-wrap').attr('source') !="1")
			return;
		
		if($(this).hasClass('item2')){
			$(this).children('.item-wrap').show().parents('.nav-inner2').prev('.item-wrap').hide();
		}else{
			$(this).children('.item-wrap').show();
		};
		//二级菜单定位
		var $subWrap=$(this).children('.item-wrap');		//二级菜单
		var navHeight=$('.nav-wrap').height();			//内部窗口总高度
		var inScrollTop=parseInt($('#mCSB_1_dragger_vertical').css('top'));		//获取内部滚动条位置
		var subHeight=10;	//当前一级菜单距离浏览器顶部高度
		if($(this).hasClass('item2')){
			innerIndex=$(this).index();
			outIndex=$(this).parents('.item1').index();
			for(var i=0; i< outIndex; i++){
				subHeight=subHeight+$('.item1:eq('+i+')').height();
			};
			subHeight=subHeight+(innerIndex+1)*35
		}else{
			outIndex=$(this).index();
			for(var i=0; i< outIndex; i++){
				subHeight=subHeight+$('.item1:eq('+i+')').height();
			};
		};
		var surHeight=subHeight-inScrollTop;	//内部滚动条滚动后，一级菜单距离浏览器顶部还剩余的高度
		var surBottom=navHeight-surHeight;		//内部滚动条滚动后，一级菜单距离浏览器底部还剩余的高度
		var subWrapHeight=$subWrap.height()+20;		//二级菜单高度
		var modHeight=surBottom-subWrapHeight;	//二级菜单距底部的高度，如果值为负数，说明底部放不下，需要上移；
		//alert(inScrollTop);
		if(modHeight<0){
			$subWrap.css({'top':'auto','bottom':0});
		}else{
			$subWrap.css({'top':0,'bottom':'auto'});
		};
		
	});
	//鼠标离开收起二级菜单
	$('body').on('mouseleave','.main-nav-show .item',function(){
		$('.item-tt a').stop();
		//$(this).children('.item-wrap').hide();
		if($(this).hasClass('item2')){
			$(this).children('.item-tt').removeClass('item-tt-hover').children('a').stop(true,true).animate({'left':'28px'},500);
		}else{
			$(this).children('.item-tt').removeClass('item-tt-hover').children('a').stop(true,true).animate({'left':'15px'},500);
		};
	});
	$(document).mouseover(function(event){
		var eo=$(event.target);
		if(!eo.parents('.item-wrap').length && !eo.parents('.nav-wrap').length)
		$('.item-wrap').hide();  
	});
	//-----=E 主导航 End-----

});