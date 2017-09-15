$(function(){
	//参数说明：都放在.skin-thumb-img img上，$color(data-color)：图片填充颜色，六位颜色值；$img(src)：图片地址
	
	$('#skinLayer').removeClass('fn-hide');
	/*
	$('.skin-thumb-list').masonry({
		itemSelector: '.skin-thumb',
		columnWidth: 215
	});
	*/
	$('#skinLayer').addClass('fn-hide');
	var $wallImg=$('.wallpaper img').attr('src');
	$('.skin-thumb-list li a').click(function(e){
		e.preventDefault();
		
		$(this).parent().addClass('current').siblings('li').removeClass('current');
		
		var $color=$(this).find('img').attr('data-color');
		var $img=$(this).find('img').attr('src');
		var $orgImg=$img.replace('205_115','original')
		var $orgImg=$orgImg.replace('420_240','original')
		var $css=$(this).find('img').attr('data-css');
//		var $bgc='url('+$orgImg+') no-repeat center top #'+$color;
//		$('body,.common-wrap').css({'background':$bgc});
//		$('#skinCSS').attr('href','css/'+$css+'.css');
		
		var $img1=$(this).find('img').attr('data-img');
		$('#backgroundColor').val($color);
		$('#backgroundImg').val($img1);
		$('#skin1').val($css);
	});
	$('.reset-wallpaper').click(function(){
		//$('body,.common-wrap').css('background-image','url('+$wallImg+')');
		//$('body,.common-wrap').removeAttr('style');
		
		$('#layout').val("1");
		$('#backgroundImg').val("");
		$('#backgroundColor').val("");
		$('#skin1').val($('#defaultSkin').val());
		$('#theme').val("2");
		saveSkin();
	});
	$('.skin-theme-list li').click(function(){
		$(this).addClass('current').siblings('li').removeClass('current');
		$dataType = $(this).attr('data-type');
		$('#'+$dataType).val($(this).attr('data-value'));
	});
});