$(function(){
	//属性（参数）说明：应用ID请保证唯一性data-id=1；预添加data-load=yes/no；是否第三方应用data-out=yes/no；应用是否已添加data-add=yes/no
	
	//鼠标滑过效果
	$('.app-grid').on('mousemove','ul li',function(){
		$(this).addClass('hover');
	});
	$('.app-grid').on('mouseout','ul li',function(){
		$(this).removeClass('hover');
	});
	
	//添加-预添加
	$('#deskAppListLayer .app-grid li:not(.add) a').unbind('click').click(function(e){
		e.preventDefault();
		var $li=$(this).parent('li');
		var dataId=$li.attr('data-id');
		var dataLoad=$li.attr('data-load');
		var dataOut=$li.attr('data-out');
		if(dataLoad=='no'){
			$li.attr('data-load','yes').find('i').css('display','block');
//			var $con=$li.html();
//			$con='<li data-id="'+dataId+'" data-out="'+dataOut+'" data-add="no" data-load="yes" style="display:none;">'+$con+'</li>';
//			$($con).appendTo('#deskAppList ul');
		}else{
			$li.attr('data-load','no').find('i').css('display','none');
//			$('#deskAppList li[data-id='+dataId+']').remove();
		};
	});
	
	//添加-保存
	$('#saveAppList').click(function(e){
		e.preventDefault();
//		$('#_______overlayer').remove();
//		$('#deskAppListLayer').hide().find('.app-grid li[data-load=yes]').remove();
//		$('#deskAppList li[data-load=yes]').attr('data-add','yes').css('display','block').removeAttr('data-load').find('i').removeAttr('style');
	});
	
	//添加-取消
	$('#cancelAppList,#deskAppListLayer .close').click(function(e){
		e.preventDefault();
//		$('#_______overlayer').remove();
//		$('#deskAppListLayer').hide().find('.app-grid li[data-load=yes]').attr('data-load','no').find('i').css('display','none');
//		$('#deskAppList li[data-load=yes]').remove();
	});
	
	//删除
	$('#deskAppList').on('click','ul li i',function(){
//		var $li=$(this).parents('li');
//		$li.attr('data-load','no').attr('data-add','no');
//		var dataOut=$li.attr('data-out');
//		if(dataOut=='no'){
//			$li.appendTo('#appListNo ul');
//		}else{
//			$('#addAppOut').before($li );
//		};
	});
	
	//上传图片
	$('#addAppOut a').click(function(e){
		e.preventDefault();
		$('.add-out-layer').show();
	});
	$('.add-out-layer .submit,.add-out-layer .cancel').click(function(e){
		e.preventDefault();
		$('.add-out-layer').hide();
	});
	$('.add-out-layer .submit').click(function(){
//		var appName=$('#appName').val();
//		var appLink=$('#appUrl').val();
//		var appImg=$('#appPic').val();
//		if(!appName){alert('请输入应用名称！');};
//		if(!appLink){alert('请输入应用地址！');};
//		if(!appImg){alert('请输入应用图标！');};
//		var maxId = 0;
//		//获取唯一性ID最大值
//		$('.app-grid li').each(function(){
//			var dataId=parseInt($(this).attr('data-id'));
//				if (dataId>maxId) {
//				maxId=dataId;
//			};
//		});
//		var myOutId=maxId+1;
//		if(appName && appLink && appImg){
//			var $myLi='<li data-id="'+myOutId+'" data-add="no" data-out="yes" data-load="no"><a href="'+appLink+'"><span class="app-img"><img src="'+appImg+'"></span><span class="app-name">'+appName+'</span><i></i></a></li>';
//			$('#addAppOut').before($myLi);
//		};
	});
	
	//拖动排序
	$("#deskAppList ul" ).sortable();
});