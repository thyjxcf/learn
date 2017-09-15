var t = n = 0, count;
function showAuto(){
	n = n >= (count-1) ? 0 : ++n;
	$('.cut-banner li').eq(n).trigger('click');
}

$(function(){
	//首页图片轮播
	if (jQuery.browser.msie) {
		var browserVer=parseInt(jQuery.browser.version);
	}
	count=$('.banner-list a').length;
	$('.banner-list a:not(:first-child)').hide();
	$('.cut-banner li').click(function(){
		var i=$(this).text()-1;
		n=i;                                       
		if(i>=count) return;
		if(browserVer==6 || browserVer==7){
			$('.banner-list a').filter(':visible').hide().parent().children().eq(i).show();
		}else{
			$('.banner-list a').filter(':visible').fadeOut(500).parent().children().eq(i).fadeIn(1000);
		}
		$(this).addClass('on').siblings('li').removeClass('on');
	})
	t=setInterval('showAuto()',5000);
	$('.banner').hover(function(){
		clearInterval(t);						   
	},function(){
		t=setInterval('showAuto()',5000);
	});
});