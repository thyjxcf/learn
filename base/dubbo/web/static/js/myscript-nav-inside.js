$(function(){
	//-----=S 主导航 Start-----
	function windowAuto(){
		//初始化框架高度
		var reWindow=$(window).height();
		var navHeight=reWindow-40;
		var navTop=(navHeight-80)/2;
		$('.inside-nav').height(navHeight);
		$('.inside-nav-wrap').height(navHeight-35);
		$('.inside-nav-cut').css('top',navTop);
	};
	windowAuto();
	$(window).resize(function(){windowAuto();});
	$('.nav-tips .close').click(function(){
		$('.nav-tips').remove();
		return false;
	});
	//滚动条
	$('.mCustomScrollbar').mCustomScrollbar();
	$('.mCSB_scrollTools .mCSB_draggerContainer').show();
	//展开收起
	$('.inside-nav-cut').click(function(e){
		e.preventDefault();
		if($(this).hasClass('inside-nav-cut-show')){
			//展开按钮点击效果
			$(this).attr('title','收起');
			$(this).addClass('inside-nav-cut-hide').removeClass('inside-nav-cut-show');
			$('.inside-nav').addClass('inside-nav-show');
			$('.mCSB_scrollTools .mCSB_draggerContainer').show();
		}else{
			//收起按钮点击效果
			$(this).attr('title','展开');
			$(this).addClass('inside-nav-cut-show').removeClass('inside-nav-cut-hide');
			$('.inside-nav').removeClass('inside-nav-show');
			$('.mCSB_scrollTools .mCSB_draggerContainer').hide();
		};
	});
	//-----=E 主导航 End-----

});