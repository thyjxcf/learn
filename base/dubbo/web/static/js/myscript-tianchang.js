//取消链接点击外部虚线框
$('a').attr('hideFocus','true');

//表格奇偶行背景色分隔、鼠标滑过变色效果
function interval(obj){
	$(obj).each(function(){
        var mod=$(this).index()%2;
		if(mod==1){$(this).addClass('even')};
    });
	$(obj).hover(function(){
		$(this).addClass('hover').siblings('tr').removeClass('hover');
	},function(){
		$(this).removeClass('hover');
	});
};
interval('.ui-table-list tbody tr');

//tab选项卡
function tabcut(tab,model,even){
	if(even=='hover'){//鼠标滑过切换，其他全部为点击
		$(tab).hover(function(){
			$(this).addClass('current').siblings().removeClass('current');
			$(model).eq($(this).index()).show().siblings().hide();
		});
	}else{
		$(tab).click(function(e){
			e.preventDefault();
			$(this).addClass('current').siblings().removeClass('current');
			$(model).eq($(this).index()).show().siblings(model).hide();
		});
	};
};
$(function(){
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
});