<script>
function saveUserApps(){
	var array_id = new Array(); 
    var a = 0;  
	$('.app-list-yes img').each(function(fn){
		var id=$(this).attr('id');
		array_id[a++] = id;  
	});
	getJSON("${request.contextPath}/system/desktop/app/userApp-remote!userAppSave.action",{"moduleIds":array_id.join(",")},"closeUserApp()");
}

function closeUserApp(){
	closeDiv("#commonApp","load('#userApp','${request.contextPath}/system/desktop/app/userApp.action')");
}

function commonAppScroll(){
	var myHeight=$('#commonApp .app-wrap').height();
		if(myHeight>476){
		$('#commonApp .wrap').css({'height':'476px'});
		$('#commonApp .wrap').jscroll({ W:"5px"//设置滚动条宽度
			,Bar:{  Pos:""//设置滚动条初始化位置在底部
					,Bd:{Out:"#999fa5",Hover:"#5b5c5d"}//设置滚动滚轴边框颜色：鼠标离开(默认)，经过
					,Bg:{Out:"#999fa5",Hover:"#67686a",Focus:"#67686a"}}//设置滚动条滚轴背景：鼠标离开(默认)，经过，点击
			,Btn:{btn:false}//是否显示上下按钮 false为不显示
		});
		$('#commonApp .wrap .jscroll-e').show();
		$('#commonApp .wrap .jscroll-c').css('position','relative');
	}else{
		$('#commonApp .wrap').css({'height':'auto'});
		$('#commonApp .wrap .jscroll-e').hide();
		$('#commonApp .wrap .jscroll-c').css('position','static');
	}
}

function commonCopy(){
	var $newItem=$('.common .app-list')
	var newTxt='<li><p></p></li>';
	var itemLen=$('.app-list-yes li').length;
	$newItem.empty();
	$('#commonApp .app-list-yes').contents().clone().appendTo($newItem);
	if(itemLen<6){
		$(newTxt+newTxt+newTxt+newTxt+newTxt+newTxt).appendTo($newItem);
	}
}

$(document).ready(function(){
	commonAppScroll();
	
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
	
	$('.app-class a').click(function(e){
		e.preventDefault();
		$(this).addClass('current').siblings('a').removeClass('current');
		var appClass=$(this).attr('data-appClass');
		if(appClass==0){
			$('.app-list-no').children('li').show();
		}else{
			$('.app-list-no').children('li').hide();
			$('.app-list-no').children('li[data-appclass='+appClass+']').show();
		}
		commonAppScroll();
	});
});
</script>
<p class="tt"><a href="javascript:void(0);" onclick="closeDiv('#commonApp');return false;" class="close">关闭</a></p>
<div class="wrap">
	<div class="app-wrap">
        <h3 class="pt-15">常用操作<span>（最多6个）</span></h3>
        <ul class="app-list app-list-yes">
            <#list userAppList as app>
            <li><p><a href="javascript:void(0);"><img id="${app.moduleId}" src="<#if app.picture! =="" || app.picture?index_of(".") != -1>${request.contextPath}/static/images/ad/3.png<#else>${request.contextPath}${app.picture}_m.png</#if>">${app.name}</a></p></li>
            </#list>
        </ul>
        <h3>其他操作</h3>
        <p class="app-class">
	        <a data-appClass="0" href="javascript:void(0);" class="current">全部</a>
	        <#list userSystemList! as app>
				<a href="javascript:void(0);" data-appClass="${app.id}">${app.appname!}</a>
			</#list>
        </p>
        <ul class="app-list app-list-no">
           <#list moduleList as module>
            <li data-appClass="${module.subsystem!}"><p><a href="javascript:void(0);"><#if module.picture! =="" || module.picture?index_of(".") != -1><img id="${module.id}" src="${request.contextPath}/static/images/ad/3.png"><#else><img id="${module.id}" src="${request.contextPath}${module.picture}_m.png"></#if>${module.name}</a></p></li>
            </#list>
        </ul>
    </div>
</div>
<p class="dd">
    <a href="javascript:void(0);" onClick="saveUserApps();return false;" class="abtn-blue">确定</a>
    <a href="javascript:void(0);" onclick="closeDiv('#commonApp');return false;" class="abtn-gray reset ml-5">取消</a>
</p>