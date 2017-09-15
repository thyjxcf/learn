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
	$('.info-tab span').click(function(){
		$(this).addClass('current').siblings('span').removeClass();
		$(this).parents('.info-layer').children('ul:eq('+$(this).index()+')').show().siblings('ul').hide();
	});
	
	//导航
	$('.menu .show-more').click(function(){
		$('.more-list').show();
		$('.more-list').click(function(){
			$(this).hide();
		});
		$(document).click(function(event){
			var eo=$(event.target);
			if($(".more-list").is(":visible") && !eo.hasClass('show-more') && !eo.parent(".more-list").length)
			$('.more-list').hide();  
		});
	});
	//宁波导航
	$('.NB-menu .list li.has').hover(
		function(){
			$(this).children('.NB-sub-menu').show();
		},function(){
			$(this).children('.NB-sub-menu').hide();
		}
	);
	$('.NB-sub-menu').hover(
		function(){
			$(this).parents('li').addClass('hover');
		},function(){
			$(this).parents('li').removeClass('hover');
		}
	);
	
	//备忘录
	var myWidth=$(window).width();
	//if(myWidth>=1200){myCount=4;}else{myCount=3;};
	var myCount=3;
	var memoLiW=$('.memo-list ul li').width()+52;
	var memoLen=$('.memo-list ul li').length;
	var memoGroup=Math.ceil(memoLen/myCount);
	$('.memo-list ul').css('left',0).attr('data-store','0');
	$('.memo-list .prev').hide();
	$('.memo-list .next').show();
	if(memoLen<=myCount){
		$('.memo-list .next').hide();
	}
	$('.memo-list .next').click(function(e){
		e.preventDefault();
		var dataStore=parseInt($('.memo-list ul').attr('data-store'));
		dataStore=dataStore+1;
		scWidth=dataStore*myCount*memoLiW;
		if(dataStore<memoGroup){
			$('.memo-list ul').attr('data-store',dataStore);
			$('.memo-list ul').animate({'left':'-'+scWidth+'px'},500);
			$('.memo-list .prev').show();
		}
		if(dataStore==memoGroup-1){
			$('.memo-list .next').hide();
		}
	});
	$('.memo-list .prev').click(function(e){
		e.preventDefault();
		var dataStore=parseInt($('.memo-list ul').attr('data-store'));
		dataStore=dataStore-1;
		scWidth=dataStore*myCount*memoLiW;
		if(dataStore>=0){
			$('.memo-list ul').attr('data-store',dataStore);
			$('.memo-list ul').animate({'left':'-'+scWidth+'px'},500);
			$('.memo-list .next').show();
		}
		if(dataStore==0){
			$('.memo-list .prev').hide();
		}
	});
	$('.memo-list ul li').not('.add-new,.more').hover(
		function(){
			$(this).addClass('hover');
			var liIndex=$(this).index();
			if(liIndex==1 || liIndex==4 || liIndex==7){
				if(myWidth>=1200){
					$(this).find('.memo-wrap').css({'left':'-130px'});
				}else{
					$(this).find('.memo-wrap').css({'left':'-105px'});
				}
			}else if(liIndex==2 || liIndex==5 || liIndex==8){
				$(this).find('.memo-wrap').css({'left':'auto','right':'-1px'});
			}
		},function(){
			$(this).removeClass('hover');
		}
	);
	//备忘录弹出层
	$('.memorandum ul.dt li').click(function(){
		$(this).addClass('current').siblings('li').removeClass('current');
		$('.memorandum .grid:eq('+$(this).index()+')').show().siblings('.grid').hide();
	})
	$('.re-memorandum-tab li input:radio').click(function(){
		var $index=$(this).attr('index');
		$index=parseInt($index);
		$('.re-memorandum-grid:eq('+$index+')').show().siblings('.re-memorandum-grid').hide();
	});
	$('.table-kb-wek td,.table-kb-mon td').mouseover(function(){
		$('.table-kb td').removeClass('hover cur')
		$(this).addClass('hover');
	})
	$('.table-kb-wek td').click(function(){
		$('.table-kb-wek td').removeClass('hover cur')
		$(this).addClass('hover cur');
		$('#addWek,#modelLayer').show();
	})
	$('.table-kb-mon td').click(function(){
		$('.table-kb-mon td').removeClass('hover cur')
		$(this).addClass('hover cur');
		$('#addMon,#modelLayer').show();
	})
	$('.table-kb-wek td .more').click(function(e){
		e.stopPropagation();
		$('.table-kb-wek td').removeClass('hover cur')
		$(this).parents('td').addClass('hover cur');
		$('#listWek,#modelLayer').show();
	})
	$('.table-kb-mon td .have').click(function(e){
		e.stopPropagation();
		$('.table-kb-mon td').removeClass('hover cur')
		$(this).parents('td').addClass('hover cur');
		$('#listMon,#modelLayer').show();
	})
	$('#closeAddMon,#closeListMon,#closeAddWek,#closeListWek').click(function(){
		$('#modelLayer').hide();
	})
	$('#listWekTable tr,#listMonTable tr').mouseover(function(){
		$(this).addClass('current').siblings('tr').removeClass('current');
	})
	$('.add-img').click(function(){
		$('#addMon,#modelLayer').show();
		$('#listMon').hide();
	})
	$('.close-top').click(function(){
		$(this).parents('.top-layer').hide();
		$('.table-kb td').removeClass('current')
	})
	$('.schedule-slider li').click(function(){
		$(this).addClass('current').siblings('li').removeClass('current');
		$('.grid .schedule-content:eq('+$(this).index()+')').show().siblings('.schedule-content').hide();
		$('.re-memorandum-grid .schedule-content:eq('+$(this).index()+')').show().siblings('.schedule-content').hide();
	})
	$('.schedule-content ul li').hover(function(){
		$(this).addClass('current').siblings('li').removeClass('current');
	})
	$('.add-bw').click(function(){
		$('#memorandum').hide();
		$('#memoLayer').show().addClass('new-memoLayer');
	})
	$('body').on('click','.new-memoLayer .close,.new-memoLayer .submit,.new-memoLayer .reset',function(){
		$('#memoLayer').removeClass('new-memoLayer');
		$('#memorandum').show();
		$('#memoLayer').hide();
	})
	
	//桌面模块
	$('.desk-item').hover(
		function(){
			$(this).css('z-index',4).children('.desk-item-inner').addClass('desk-item-inner-hover');
		},function(){
			$(this).css('z-index',1).children('.desk-item-inner').removeClass('desk-item-inner-hover');
		}
	);
	
	//最新消息
	$('.school-notice .news-list li,.newest .news-list li').hover(
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
	
	//常用操作
	function commonCopy(){
		var $newItem=$('.common .app-list')
		var newTxt='<li><p></p></li>';
		var itemLen=$('.app-list-yes li').length;
		$newItem.empty();
		$('#commonApp .app-list-yes').contents().clone().appendTo($newItem);
		if(itemLen<6){
			$(newTxt+newTxt+newTxt+newTxt+newTxt+newTxt).appendTo($newItem)
		}
	}
	commonCopy();
	$('#commonApp .app-list li').click(function(e){
		e.preventDefault();
		var hasClass=$(this).parent('.app-list').hasClass('app-list-yes');
		var itemLen=$('.app-list-yes li').length;
		if(!hasClass){
			if(itemLen<6){
				$(this).appendTo('#commonApp .app-list-yes');
			}else{
				alert('最多只能添加6个常用操作！');
			}
		}else{
			$(this).appendTo('#commonApp .app-list-no');;
		}
		$('#commonApp .wrap').jscroll({ W:"5px"//设置滚动条宽度
			,Bar:{  Pos:""//设置滚动条初始化位置在底部
					,Bd:{Out:"#999fa5",Hover:"#5b5c5d"}//设置滚动滚轴边框颜色：鼠标离开(默认)，经过
					,Bg:{Out:"#999fa5",Hover:"#67686a",Focus:"#67686a"}}//设置滚动条滚轴背景：鼠标离开(默认)，经过，点击
			,Btn:{btn:false}//是否显示上下按钮 false为不显示
		});	
	});
	$('#commonApp .close,#commonApp .submit,#commonApp .reset').click(function(e){
		e.preventDefault();
		commonCopy();
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
	
	//流程图
	$('.flow-wrap .flowchart-dt').click(function(){
		$(this).toggleClass('flowchart-dt-open');
		$(this).siblings('.flowchart').toggle()
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
	
	//树级菜单2
	$('.tree-menu-search .tree-menu-box').on('click','.level-list li',function(){
		var $text=$(this).text();
		var $order=$('.tree-menu-selected .level-selected').length+1;
		$('.tree-menu-search b').text('您已选择：');
		$('.tree-menu-selected .level-selected').removeClass('level-current');
		$("<span class='level-selected level-current' data-order='"+$order+"'><span>"+$text+"<\/span> > <\/span>").appendTo('.tree-menu-selected');
	});
	$('.tree-menu-search').on('click','.level-current',function(){
		$(this).prev('.level-selected').addClass('level-current');
		$(this).remove();
	});
	$('.tree-menu-search').on('click','.show-part',function(){
		$(this).removeClass('show-part').addClass('show-all').text('展开');
		$(this).parents('.tree-menu-search').find('.tree-menu-box').hide();
	});
	$('.tree-menu-search').on('click','.show-all',function(){
		$(this).removeClass('show-all').addClass('show-part').text('收起');
		$(this).parents('.tree-menu-search').find('.tree-menu-box').show();
	});
	
	//树级菜单3	
	//div模拟select下拉框select_box
	//mouseover/mouseout展开或关闭下拉菜单
	$(".select_box").each(function(){
        var myW=$(this).find('.select_list').width()+5;
        var myH=$(this).find('.select_list').height()+5;
		$(this).find('#ovLayer').css({'width':myW,'height':myH});
    });
	$(".select_box").hover(function(){
	    $(this).children(".select_current").addClass("select_current_hover").end().find(".select_list,#ovLayer").show();	
	},function(){
	    $(this).children(".select_current").removeClass("select_current_hover").end().find(".select_list,#ovLayer").hide();	
	});
	//获取选中的值
	$(".select_list li").click(function(){
		//$(this).parents(".select_list").hide().prev().attr("value",$(this).text());
		$(this).parents(".select_box").find('.select_list,#ovLayer').hide().end().find('.select_current').attr("value",$(this).text());
	});
	//下拉列表hover
	$(".select_list li").mouseenter(function(){
	    $(this).addClass("select_hover");
	})
	$(".select_list li").mouseleave(function(){
	    $(this).removeClass("select_hover");
	})
	//focus blur
	$(".select_current").focus(function(){
		var txt_value = $(this).val();
		if(txt_value == this.defaultValue){$(this).val("");}     
	});
	$(".select_current").blur(function(){
		var txt_value = $(this).val();
		if(txt_value == ""){$(this).val(this.defaultValue);}  
	});
	$('.search-box').click(function(e){
		$(this).find('.search-list-wrap').show();
	});
	$('.search-list-wrap a').click(function(e){
		e.stopPropagation();
		e.preventDefault();
		$(this).parent('.search-list-wrap').hide().siblings('.txt').val($(this).text());
	});
	$('.search-box').mouseleave(function(){
		$(this).find('.search-list-wrap').hide();
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
	
	//用户选择
	$('.user-sList-checkbox span').click(function(){
		$(this).addClass('current');
	});
	$('.user-sList-radio span').click(function(){
		$(this).addClass('current').siblings('span').removeClass('current');
	});
	
	//异动
	$('.transaction-type span').click(function(){
		$(this).parents('.transaction-type-wrap').next('.transaction-type-wrap').show();
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
	
	//浮动选择tab切换
	var $div_li1=$(".tab_menu > ul > li");
	$div_li1.click(function(){
		$(this).addClass("active").siblings().removeClass("active");
		var index=$div_li1.index(this);
		$(".tab_box > div").eq(index).show().siblings().hide();
	});
	
	//删除单项
	$('.del-item').click(function(e){
		e.preventDefault();
		$(this).remove();
	});

	//div模拟select下拉框select_box02
	//mouseover/mouseout展开或关闭下拉菜单
	$(".select_box02").hover(function(){
		$(this).children(".select_current02").addClass("select_current02_hover").end().children(".select_list02_container").show();	
	},function(){
		$(this).children(".select_current02").removeClass("select_current02_hover").end().children(".select_list02_container").hide();	
	});
	$('.select_list02_container .close').click(function(e){
		$(this).parent('.select_list02_container').hide();
	});
	
});