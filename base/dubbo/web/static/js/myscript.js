$(function(){
	//框架结构
	function windowScreen(){
		var winWidth=$(window).width();
		if(winWidth>=1200){
			$('body').not('.login').addClass('widescreen');
		}else{
			$('body').removeClass('widescreen');
		};
	};
	windowScreen();
	$(window).resize(function(){
		windowScreen();
	});
		
	//表格效果
	$('.public-table tr:odd').addClass('odd');
	$('.public-table tr').hover(
		function(){
			$(this).addClass('tr-hover').siblings('tr').removeClass('tr-hover');
		},
		function(){
			$(this).removeClass('tr-hover');
		}
	);
	//表格内部有下级元素操作
	$('.table-hasInner tr').not('.tr-header').click(function(){
		$('.table-hasInner tr').removeClass('tr-wrap');
		$(this).addClass('tr-wrap');
		$('.table-hasInner tr.has-table').hide();
		$(this).next('tr.has-table').show();
	});
	$('.table-list-more .open-detail').click(function(e){
		e.preventDefault();
		$(this).parents('tr').next('tr.more').show();
	});
	
	//表格可编辑
	$('.edit-td,.editSelect-td,.openAddress').hover(
		function(){
			$(this).addClass('can-edit-hover');
		},function(){
			$(this).removeClass('can-edit-hover');
		}
	);
	$('.edit-td .input-txt').click(function(){
		$(this).parents('.table-list-edit').find('.input-txt').removeClass('input-date input-txt-current');
		$(this).parents('.table-list-edit').find('.editSelect-wrap').removeClass('editSelect-wrap-current');
		$(this).parents('.table-list-edit').find('.subSelect-list').hide();
		if($(this).parent('.edit-td').hasClass('edit-td-date')){
			$(this).addClass('input-date input-txt-current');
		}else{
			$(this).addClass('input-txt-current');
		}
	});
	$('.table-list-edit .editSelect-td').click(function(e){
		e.stopPropagation();
		$(this).removeClass('can-edit-hover');
		$(this).parents('.table-list-edit').find('.input-txt').removeClass('input-date input-txt-current');
		$(this).find('.editSelect-wrap').addClass('editSelect-wrap-current');
		$('.subSelect-list').hide();
		$(this).find('.subSelect-list').show(0,function(){
			$('.subSelect-list li').click(function(e){
				e.stopPropagation();
				var txt=$(this).text();
				$(this).parent('.subSelect-list').hide();
				$(this).parent('.subSelect-list').siblings('.input-txt').val(txt);
			});
		});
	});

	//$('.edit-td,.editSelect-td,.openAddress').hover(
//		function(){
//			$(this).addClass('can-edit-hover');
//		},function(){
//			$(this).removeClass('can-edit-hover');
//		}
//	);
//	$('.table-list-edit').on('click','.edit-td',function(){
//		if($(this).children('.origin-txt').is(":visible")){
//			var originTxt=$(this).children('.origin-txt').text();
//			$('.table-list-edit').find('.edit-txt').removeClass('input-txt-onfocus');
//			if($(this).children('.origin-txt').hasClass('origin-date')){
//				$(this).children('.origin-txt').hide().after("<input type='text' class='edit-txt input-txt input-date input-txt-onfocus' value='"+originTxt+"'>");
//			}else{
//				$(this).children('.origin-txt').hide().after("<input type='text' class='edit-txt input-txt input-txt-onfocus' value='"+originTxt+"'>");
//			}
//		}
//	});
//	$('.table-list-edit .editSelect-td').click(function(e){
//		e.stopPropagation();
//		$(this).removeClass('can-edit-hover');
//		$(this).find('.editSelect-wrap').addClass('editSelect-wrap-current');
//		$('.subSelect-list').hide();
//		$(this).find('.subSelect-list').show(0,function(){
//			$('.subSelect-list li').click(function(e){
//				e.stopPropagation();
//				var txt=$(this).text();
//				$(this).parent('.subSelect-list').hide();
//				$(this).parent('.subSelect-list').siblings('.input-txt').val(txt);
//			});
//		});
//	});
	
	//头部消息
	$('.user-info .new-msg,.info-layer').hover(
		function(){
			$('.user-info').addClass('user-info-hover');
			$('.more-list').hide();
		},function(){
			$('.user-info').removeClass('user-info-hover');
		}
	);
	$('.user-info .code').hover(
		function(){
			$('.user-info .code-layer').show();
		},function(){
			$('.user-info .code-layer').hide();
		}
	);
	
	//桌面模块
	$('.desk-item').hover(
		function(){
			$(this).css('z-index',4).children('.desk-item-inner').addClass('desk-item-inner-hover');
		},function(){
			$(this).css('z-index',1).children('.desk-item-inner').removeClass('desk-item-inner-hover');
		}
	);
	
	//最新消息
	$('.newest .news-list li').hover(
		function(){
			$(this).addClass('hover').siblings('li').removeClass('hover');
		},function(){
			$(this).removeClass('hover');
		}
	);
	$('.newest .tab span').click(function(){
		$(this).addClass('current').siblings('span').removeClass('current');
		$('.newest .news-list:eq('+$(this).index()+')').show().siblings('.news-list').hide();
	});
	
	$('.news-list-inside li').click(function(){
		$(this).addClass('current').siblings('li').removeClass('current');
	});
	
	$('.newest-two .news-list').each(function(){
        $(this).children('li').wrapInner('<p class="inner"></p>');
		$(this).children('li:odd').addClass('even');
    });
		
	//添加应用
	function appContentScroll(){
		$('#deskApp .app-content .app-wrap').jscroll({ W:"5px"//设置滚动条宽度
			,Bar:{  Pos:""//设置滚动条初始化位置在底部
					,Bd:{Out:"#999fa5",Hover:"#5b5c5d"}//设置滚动滚轴边框颜色：鼠标离开(默认)，经过
					,Bg:{Out:"#999fa5",Hover:"#67686a",Focus:"#67686a"}}//设置滚动条滚轴背景：鼠标离开(默认)，经过，点击
			,Btn:{btn:false}//是否显示上下按钮 false为不显示
		});
		if($('#deskApp .app-content ul').height()>470){
			$('#deskApp .app-content .jscroll-e').show();
		}
	};
	$('#deskApp .app-slider .app-wrap').on('click','ul li i',function(e){
		$(this).parent('li').appendTo('#deskApp .app-content .app-wrap ul');
		$('#deskApp .app-content .app-wrap ul li img').each(function(fn){
			var url=$(this).attr('src');
			url=url.replace('app/icon','app/desk_icon');
			$(this).attr('src',url);
		});
		appContentScroll();
	});
	$('#deskApp .app-content .app-wrap').on('click','ul li i',function(e){
		$(this).parent('li').appendTo('#deskApp .app-slider .app-wrap ul');
		$('#deskApp .app-slider .app-wrap ul li img').each(function(fn){
			var url=$(this).attr('src');
			url=url.replace('app/desk_icon','app/icon');
			$(this).attr('src',url);
		});
		appContentScroll();
	});
	
	//个人设置
	$('#setLayer .set-tab li').click(function(){
		$(this).addClass('current').siblings().removeClass();
		$('#setLayer .set-wrap:eq('+$(this).index()+')').show().siblings('.set-wrap').hide();
	});
	
	//tab
	$('.pub-tab-list li').click(function(){
		$(this).addClass('current').siblings('li').removeClass('current');
		$('.pub-table-wrap .pub-table-inner:eq('+$(this).index()+')').show().siblings('.pub-table-inner').hide();
	});
	$('.pub-sub-tab li').click(function(){
		$(this).addClass('current').siblings('li').removeClass('current');
		$(this).parents('.pub-table-inner').children('.pub-sub-tab-inner:eq('+$(this).index()+')').show().siblings('.pub-sub-tab-inner').hide();
	});
	
	//20151223 东莞人事增加
	$('.pub-two-tab li').click(function(){
		$(this).addClass('current').siblings('li').removeClass('current');
		$(this).parents('.pub-table-inner').children('.pub-two-tab-inner:eq('+$(this).index()+')').show().siblings('.pub-two-tab-inner').hide();
	});
	$('.pub-tri-tab li').click(function(){
		$(this).addClass('current').siblings('li').removeClass('current');
		$(this).parents('.pub-two-tab-inner').children('.pub-tri-tab-inner:eq('+$(this).index()+')').show().siblings('.pub-tri-tab-inner').hide();
	});
	
	//sub-menu
	$('.small-model').each(function(){
		var modelLen=$(this).find('li').length;
		$(this).width(Math.ceil(modelLen/3)*128);
	});
	
	$('.sub-menu-wrap .sub-menu-grid:first').addClass('sub-menu-grid-first');
	$('.sub-menu-wrap .sub-menu-grid-last').remove();
	$('.menu,.sub-menu-wrap .sub-menu-grid').find('a').attr('hidefocus','true');
	var sWidth=$('.header-inner').width();
	$('.sub-menu-cut .next').click(function(e){
		e.preventDefault();
		if(!$(this).hasClass('last')){
			$('.sub-menu-wrap').animate({'left':'-'+sWidth+'px'},500);
			$(this).addClass('last').siblings('.prev').removeClass('first');
		}
	});
	$('.sub-menu-cut .prev').click(function(e){
		e.preventDefault();
		if(!$(this).hasClass('first')){
			$('.sub-menu-wrap').animate({'left':0},500);
			$(this).addClass('first').siblings('.next').removeClass('last');
		}
	});
	
	//树级菜单1
	$('.tree-menu-select .first-level .user-sList span').click(function(){
		$('.tree-menu-select .second-level').show();
	});
	$('.tree-menu-select .table-bt-gray .submit').click(function(e){
		e.preventDefault();
		$('.tree-menu-select .three-level').show();
	});
	$('.tree-menu-select').on('click','.show-part',function(){
		$(this).removeClass('show-part').addClass('show-all').text('展开');
		$(this).siblings('.level-list').css('height','30px');
	});
	$('.tree-menu-select').on('click','.show-all',function(){
		$(this).removeClass('show-all').addClass('show-part').text('收起');
		$(this).siblings('.level-list').css('height','auto');
	});
	
	$('.address-selected').on('click','.level-box .show-part',function(){
		$(this).removeClass('show-part').addClass('show-all').text('展开');
		$(this).siblings('.level-list').css('height','20px');
	});
	$('.address-selected').on('click','.level-box .show-all',function(){
		$(this).removeClass('show-all').addClass('show-part').text('收起');
		$(this).siblings('.level-list').css('height','auto');
	});
	
	//是否切换效果
	$('.onOff-list span').click(function(){
		$(this).addClass('current').siblings('span').removeClass('current');
		$(this).parent('.onOff-list').addClass('onOff-list-current');
		$(this).siblings('input').val($(this).attr('data-val'));
	});
	$('.onOff-list').hover(
		function(){
			if($(this).find('.current').length>=1){
				$(this).addClass('onOff-list-hover');
			}
		},function(){
			$(this).removeClass('onOff-list-hover');
		}
	);
	
	//通讯录
	$('.user-group .dt-show').click(function(){
		if(!$(this).hasClass('dt-show-all')){
			$(this).addClass('dt-show-all').next('.page-list').hide();
		}else{
			$(this).removeClass('dt-show-all').next('.page-list').show();
		}
//		var myHeight=$('#addressLayer .wrap').height();
//		if(myHeight>476){
//			$('#addressLayer .address-wrap').css({'height':'476px'});
//			$('#addressLayer .address-wrap').jscroll({ W:"5px"//设置滚动条宽度
//				,Bar:{  Pos:""//设置滚动条初始化位置在底部
//						,Bd:{Out:"#999fa5",Hover:"#5b5c5d"}//设置滚动滚轴边框颜色：鼠标离开(默认)，经过
//						,Bg:{Out:"#999fa5",Hover:"#67686a",Focus:"#67686a"}}//设置滚动条滚轴背景：鼠标离开(默认)，经过，点击
//				,Btn:{btn:false}//是否显示上下按钮 false为不显示
//			});			
//		}
	});
	$('.user-group .dt-show .prev,.user-group .dt-show .next').click(function(e){
		e.preventDefault();
		e.stopPropagation();
	});
	$('.page-list-edit').on('click','li .source .del',function(e){//删除
		e.preventDefault();
		$(this).parents('li').remove();
	});
	$('.page-list-edit').on('click','li .source .edit',function(e){//编辑
		e.preventDefault();
		var sourceName=$(this).siblings('.source-name').text();
		$('.page-list-edit li .source').show().siblings('.edit-wrap').hide();
		$(this).parents('.source').hide().siblings('.edit-wrap').show().find('.edit-name').val(sourceName);
	});
	$('.page-list-edit').on('click','li .edit-wrap .reset',function(e){//取消编辑
		e.preventDefault();
		$(this).parents('.edit-wrap').hide().siblings('.source').show();
	});
	$('.page-list-edit').on('click','li .edit-wrap .submit',function(e){//保存编辑
		e.preventDefault();
		var editName=$(this).parents('.edit-wrap').find('.edit-name').val();
		$(this).parents('.edit-wrap').hide().siblings('.source').show().find('.source-name').text(editName);
	});
	$('.page-list-edit').on('click','li .add-new',function(){//新建
		$('.page-list-edit li .source').show().siblings('.edit-wrap').hide();
		$(this).hide().siblings('.edit-wrap').show();
	});
	$('.province-group .page-list li').click(function(){
		$('.province-group .page-list li').removeClass('current');
		$(this).addClass('current')
	});
	
	//实训管理
	$('.audit-now').hover(
		function(){
			$(this).siblings('.audit-progress').fadeIn(300);
		},function(){
			$(this).siblings('.audit-progress').fadeOut(300);
		}
	);
	
	//更改流程步骤
	$(".step-info .tab li").click(function(){
		$(this).addClass("current").siblings("li").removeClass("current");
		$(this).parents(".step-info").children(".box:eq("+$(this).index()+")").show().siblings(".box").hide();
	})
	
	//异动
	$('.transaction-type span').click(function(){
		$(this).parents('.transaction-type-wrap').next('.transaction-type-wrap').show();
	});
	
	/*设置按钮*/
	$('.set-start-orange').click(function(){
		$(this).toggleClass('set-start-orange-current');
	});
	$('.set-start-gray').click(function(){
		$(this).toggleClass('set-start-gray-current');
	});
	//focus blur
	$(".focus_blur,.select_current02").focus(function(){
		var txt_value = $(this).val();
		if(txt_value == this.defaultValue){$(this).val("");}     
	});
	$(".focus_blur,.select_current02").blur(function(){
		var txt_value = $(this).val();
		if(txt_value == ""){$(this).val(this.defaultValue); }  
	});
	
	//删除单项
	$('.del-item').click(function(e){
		e.preventDefault();
		$(this).remove();
	});
	//20150602 东莞学籍管理增加
	$('.explain-wrap .btn').click(function(e){
		e.preventDefault();
		if($(this).hasClass('btn-hide')){
			$('.explain-wrap .des').css({'height':'22px','overflow':'hidden'});
			$(this).removeClass('btn-hide').addClass('btn-show').text('展开详情');
		}else{
			$('.explain-wrap .des').css({'height':'auto','overflow':'hidden'});
			$(this).removeClass('btn-show').addClass('btn-hide').text('收起详情');
		};
	});
	

});