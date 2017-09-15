function closeFilter(){
	document.getElementById('mask').remove();
	document.getElementById('filter').style.right='-7.6rem';
	document.getElementById('regionLayer').style.right='-7.6rem';
};
function closeFilterFull(){
	document.getElementById('mask').remove();
	document.getElementById('filterFull').removeAttribute('style');
};
function closeShowAll(){
	document.getElementById('showAllLayer').style.display='none';
	document.getElementById('______footerMask').remove();
};
function closeRegion(){
	document.getElementById('regionLayer').style.right='-7.6rem';
	document.getElementById('______regionMask').remove();
};

//取消链接点击外部虚线框
$('a').attr('hideFocus','true');

$(function(){
	//loading
	function winLoad(){
		var pageH=$(window).height();
		$('#page').height(pageH);
	};
	winLoad();
	$(window).resize(function(){
		winLoad();
	});
	$('#page').css('background','none').children('#pageInner').delay(100).removeClass('hidde');
	
	//按钮效果
	$('a,.unit-list li,.submit-outer').on('touchstart', function(){
		$(this).addClass('active');
	});
	$('a,.unit-list li,.submit-outer').on('touchend', function(){
		$(this).removeClass('active');
	});
	
	//footer
	$('footer a').click(function(e){
		e.preventDefault();
		$(this).addClass('active').siblings('a').removeClass('active');
	});
	
	//tab
	$('.tab li').on('touchstart', function(){
		$(this).addClass('current').siblings('li').removeClass('current');
		$('.tab-grid .tab-item:eq('+$(this).index()+')').show().siblings('.tab-item').hide();
	});
	
	//checkbox
	$('.ui-checkbox').on('touchstart', function(){
		if(!$(this).hasClass('ui-radio')){
			if(!$(this).hasClass('ui-checkbox-current')){
				$(this).addClass('ui-checkbox-current');
			}else{
				$(this).removeClass('ui-checkbox-current');
			};
		}else{
			$(this).parents('form').find('.ui-radio').removeClass('ui-checkbox-current');
			$(this).addClass('ui-checkbox-current');
		};
	});
	
	//header more-sel
	$('.more-sel').click(function(){
		$('.more-layer').toggle();
	});
	$('.more-layer p').click(function(e){
		e.preventDefault();
		$(this).addClass('current').siblings('p').removeClass('current');
		$(this).parent('.more-layer').hide().siblings('.more-sel').children('.txt').text($(this).text());
	});
	$(document).click(function(event){
		var eo=$(event.target);
		if($('.more-layer').is(':visible') && !eo.hasClass('more-sel') && !eo.parent('span').hasClass('more-sel'))
		$('.more-layer').hide();  
	});
	
	//header search
	$('header .abtn-search').on('touchstart', function(){
		$('.list-search-wrap').toggle();
	});
	
	//上弹层-设备名称选择
	$('.device-name').click(function(e){
		e.preventDefault();
		$('body').prepend('<div class="ui-layer-mask ui-layer-mask-close" id="mask" onclick="closeFilterFull()"></div>');
		$('.filter-layer').animate({'bottom':'0'},500);
	});
	//筛选
	$('.ui-search .filter').click(function(e){
		e.preventDefault();
		$('body').prepend('<div class="ui-layer-mask ui-layer-mask-close" id="mask" onclick="closeFilter()"></div>');
		$('.filter-layer').animate({'right':'0'},500);
	});
	$('.filter-layer .opt .submit').click(function(e){
		e.preventDefault();
		$('.ui-layer-mask').remove();
		if($('.filter-layer').hasClass('filter-layer-full')){
			$('.filter-layer').animate({'bottom':'-9.5rem'},500);
			var dev_cate=$('.filter-layer li.current').parent('ul').prev('p.tt').text();
			var dev_name=$('.filter-layer li.current').text();
			$('.device-cate input.txt').val(dev_cate);
			$('.device-name input.txt').val(dev_name);
		}else{
			$('.filter-layer').animate({'right':'-7.6rem'},500);
		};
	});
	$('.filter-layer .opt .reset').click(function(e){
		e.preventDefault();
		$('.filter-layer li').removeClass('current');
		$('#selRegion #selRegionTips').show().next('#region').hide().text('所有地区');
	});
	$('.filter-layer li').click(function(){
		if(!$('.filter-layer').hasClass('filter-layer-full')){
			$(this).addClass('current').siblings('li').removeClass('current');
		}else{
			$('.filter-layer li').removeClass('current');
			$(this).addClass('current');
		};
	})
	
	//全部
	$('.show-all').click(function(){
		if(!$('#______footerMask').length){
			$('#______footerMask').remove();
		};
		if(!$('.show-all-layer').is(':visible')){
			var allH=$(window).height();
			var headH=$('header').height();
			var layerT=parseInt(allH-headH);
			$('.show-all-layer').show();
			$('#container').append('<div onClick="closeShowAll()" id="______footerMask" style="position:fixed;bottom:0;left:0;z-index:8;width:100%;height:'+layerT+'px;background:rgba(0,0,0,0.7);"></div>');
		}else{
			$('.show-all-layer').hide();
			$('#______footerMask').remove()
		};
	});
	$('.show-all-layer ul li').click(function(){
		$(this).addClass('current').siblings('li').removeClass('current');
		$('.show-all-layer').hide();
		$('.show-all font').text($(this).text());
		$('#______footerMask').remove();
	});
	//tab切换的时候关闭层
	$('.tab li').click(function(){
		$('.show-all-layer').hide();
		$('#______footerMask').remove()
	});
	
	
	//通讯录
	$('.address-book-sel li').click(function(){
		var this_id=$(this).attr('data-id');
		var this_txt=$(this).children('.name').text();
		if(!$(this).hasClass('current')){
			$(this).addClass('current');
			$('.address-footer .scroll').append('<a href="#" data-id="'+this_id+'">'+this_txt+'</div>');
		}else{
			$(this).removeClass('current');
			$('.address-footer .scroll a[data-id='+this_id+']').remove();
		};
		var this_num=$('.address-footer .scroll a').length;
		$('.address-footer .submit span').text(this_num);
	});
	$('.address-footer .scroll').on('click','a',function(e){
		e.preventDefault();
		var this_id=$(this).attr('data-id');
		var this_txt=$(this).children('.name').text();
		$('.address-book-sel li[data-id='+this_id+']').removeClass('current');
		$(this).remove();
		var this_num=$('.address-footer .scroll a').length;
		$('.address-footer .submit span').text(this_num);
	});

	//ui-form
	$('.ui-form li').each(function(){
        var $tt=$(this).children('.tt');
        var $dd=$(this).children('.dd');
		var li_width=$(this).width();
		var icon_width=$(this).children('.icon-img').width();
		var tt_width=$tt.width();
		var dd_padding=parseInt($dd.css('padding-right'))+parseInt($dd.css('padding-left'));
		var dd_width=li_width-tt_width-icon_width-dd_padding-20;
		$dd.width(dd_width);
    });
	$('.ui-form li:not(.sel,.date)').click(function(){
		$(this).find('input,select').focus();
	});
	
	//公文详情
	$('#container').on('click','.note-des .more',function(){
		if(!$('.note-des').hasClass('note-des-open')){
			$('.note-des').addClass('note-des-open');
		}else{
			$('.note-des').removeClass('note-des-open');
		};
	});

	//S----------20160927省市区联动选择----------
	//地区选择
	$('#selRegion').click(function(){
		$('.region-layer').show().animate({'right':'0'},500);
	});
	$('#showRegion').click(function(){
		$('body').prepend('<div class="ui-layer-mask ui-layer-mask-close" id="______regionMask" onclick="closeRegion()"></div>');
		$('.region-layer').show().animate({'right':'0'},500);
	});
	//省市选择下级
	$('body').on('click','.region-layer ul li',function(){
		$(this).addClass('current').siblings('li').removeClass('current');
	});
	//获取省数据
	$('#selRegion,#showRegion').click(function(){
		$('#regionTxt span').text('');
		$('#city,#district').hide();
		$.getJSON('js/data.json', function(data1) {
	        $('.region-layer ul').html('');//清空省、市、区列表
	        $.each(data1, function(x, item1) {
	            //插入省列表
	            $('#province ul').append(
                    '<li pro-sort="'+ x +'"><span>' + item1.name + '<i class="icon-img icon-current"></i></span></li>'
                );
	        });
        });
	});
	//获取市数据
	$('#province').on('click','li',function(){
		var cityTop=$('#province').height();
		$('#multi-select').animate({scrollTop:cityTop},300);
		var proName=$(this).text();
		$('#regionTxt span').text('');
		$('#regionTxt .province').text(proName);
		var proSort=$(this).attr('pro-sort');
		$.getJSON('js/data.json', function(data2) {
	        $('#city ul,#district ul').html('');//清空市、区列表
	        $.each(data2[proSort].city, function(y, item2) {
	        	//插入市列表
	        	$('#city').show().siblings('#district').hide();
	            $('#city ul').append(
                    '<li pro-sort="'+ proSort +'" city-sort="'+ y +'"><span>' + item2.name + '<i class="icon-img icon-current"></i></span></li>' 
                );
	        });
        });
	});
	//获取区数据
	$('#city').on('click','li',function(){
		var disTop=$('#province').height()+$('#city').height();
		$('#multi-select').animate({scrollTop:disTop},300);
		var cityName=$(this).text();
		$('#regionTxt .district').text('');
		$('#regionTxt .city').text(cityName);
		var proSort=$(this).attr('pro-sort');
		var citySort=$(this).attr('city-sort');
		$.getJSON('js/data.json', function(data3) {
	        $('#district ul').html('');//清空区列表
	        $.each(data3[proSort].city[citySort].area, function(z, item3) {
	        	//插入区列表
	            $('#district').show().children('ul').append(
                    '<li><span>' + item3 + '<i class="icon-img icon-current"></i></span></li>' 
                );
	        });
        });
	});
	//选中区数据
	$('#district').on('click','li',function(){
		var disName=$(this).text();
		$('#regionTxt .district').text(disName);
	});
	//重置
	$('.region-layer .opt .reset').click(function(e){
		e.preventDefault();
		$('.region-layer li').removeClass('current');
	});
	//确认
	$('.region-layer .opt .submit').click(function(e){
		e.preventDefault();
		$('.region-layer').animate({'display':'none','right':'-7.6rem'},500);
		$('#selRegionTips').hide().next('#region').show().text($('#regionTxt').text());
	});
	$('.region-layer-form .opt .submit').click(function(e){
		e.preventDefault();
		$('.ui-layer-mask').remove();
		$('#showRegion .txt').val($('#regionTxt').text());
	});
	//E----------20160927省市区联动选择----------
});