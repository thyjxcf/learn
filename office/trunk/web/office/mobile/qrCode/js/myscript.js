$(function(){
	var windowH=$(window).height();
	var windowW=$(window).width();		//屏幕宽度
	var screenW=screen.width;		//屏幕宽度
	var visualW=window.innerWidth;	//visual viewport
	//var layoutW=document.documentElement.clientWidth; 	//layout viewport
	var layoutW=720;
	var scale=screenW/layoutW;
	//(windowW>screenW) ? fullW=windowW : fullW=screenW;
	//$('header,footer,#footer,.user-sel-wrap').width(fullW);
	//页面缩放比例
	$('meta[name="viewport"]').attr('content','width=720, target-densitydpi=device-dpi, initial-scale='+scale+', maximum-scale='+scale+', minimum-scale='+scale+', user-scalable=no');
	//窗口框架
	var headerH=$('header').height()+1;
	var footerH=$('footer').height()+1;
	var tabH=$('.re-tab-wrap').outerHeight();
	var marT=headerH+tabH;
	var containerH=windowH-headerH-footerH-tabH;
	$('#container').css('margin-top',marT);
	$('#container,.note-wrap').css('height',containerH);
	$('.user-address #container').css('height',containerH-$('.user-sel-wrap').height());
	//头部按钮
	$('header .search').click(function(){
		$(this).toggleClass('search-current');
		$('.note-list-search').toggle();
	});
	$('header .edit').click(function(){
		$(this).addClass('edit-current');
	});
	$('.show-layer').click(function(){
		$('.layer').show();
	});
	$('.layer p').click(function(){
		$(this).addClass('current').siblings('p').removeClass('current');
		$('.layer').hide().siblings('.show-layer').children('.txt').text($(this).text());
		if($(this).attr("id")=="sign"){
			//收文签收
			$("#handleNo").text("待签收");
			$("#handleYes").text("已签收");
		}else{
			$("#handleNo").text("待处理");
			$("#handleYes").text("已处理");
		}
	});
	
	$(document).click(function(event){
		var eo=$(event.target);
		if($('.layer').is(':visible') && !eo.hasClass('show-layer') && !eo.parent('span').hasClass('show-layer'))
		$('.layer').hide();  
	});
	
	//底部按钮
	$('footer p,#footer p').click(function(){
		$(this).addClass('current').siblings('p').removeClass('current');
	});
	//公文分类：88-搜索框高度；10-分类外边框高度;
	var noteLiLength=$('.note-class li').length;
	var noteLiHeight=(containerH-88)/noteLiLength-10;
	noteLiHeight=noteLiHeight+'px';
	$('.note-class li').height(noteLiHeight);
	$('.note-class li p').css({'height':noteLiHeight,'line-height':noteLiHeight});
	$('.note-search label').click(function(){
		$(this).hide();
	});
	$('.note-search .txt').blur(function(){
		if($(this).val()==""){
			$('.note-search label').show();
		}
	});
	//公文列表
	$('.note-tab li,.note-list li,.note-class li,.unit-list li,.re-tab-wrap ul li').click(function(){
		$(this).addClass('current').siblings('li').removeClass('current');
	});
	//公文详情
	$('#container').on('click','.note-des .more',function(){
		if(!$('.note-des').hasClass('note-des-open')){
			$('.note-des').addClass('note-des-open');
		}else{
			$('.note-des').removeClass('note-des-open');
		};
	});
	
	//工作汇报
	$('.add-user').click(function(){
		$(this).addClass('add-user-current');
	});
	$('.user-sel span').click(function(){
		$(this).addClass('del').delay(100).fadeOut(10,function(){
			$(this).remove();
		});
	});
	
	//----------=S 模拟单选框 Start ----------//
	//将默认单选框控件转换成模拟单选框
	$('input:radio').each(function(){
        var rName=$(this).attr('name');
        var rVal=$(this).attr('value');
		var rDis=$(this).attr('disabled');
		var rChk=$(this).attr('checked');
		var rClass=$(this).attr('class');
		var rStyle=$(this).attr('style');
		if(rClass==undefined){rClass='';};
		if(rStyle==undefined){rStyle='';};
		if(rDis==undefined && rChk==undefined){
			rClass='ui-radio '+rClass;
		}else if(rDis=='disabled' && rChk==undefined){
			rClass='ui-radio ui-radio-disabled '+rClass;
		}else if(rDis==undefined && rChk=='checked'){
			rClass='ui-radio ui-radio-current '+rClass;
		}else if(rDis=='disabled' && rChk=='checked'){
			rClass='ui-radio ui-radio-disabled-checked '+rClass;
		};
		$(this).addClass('radio').wrap('<span class="'+rClass+'" style="'+rStyle+'" val="'+rVal+'" data-name="'+rName+'"></span>');
    });
	//	模拟单选框
	$('.ui-radio:not(".ui-radio-disabled,.ui-radio-disabled-checked")').click(function(){
		var $radioName=$(this).attr('data-name');
		$(document).find('.ui-radio[data-name='+$radioName+']').removeClass('ui-radio-current');
		$(this).addClass('ui-radio-current');
		$(this).children('.radio').attr('checked','checked');
	});
	//----------=E 模拟单选框 End ----------//

})