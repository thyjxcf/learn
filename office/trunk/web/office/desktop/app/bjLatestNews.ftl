<div class="dt fn-clear">
    <span class="tab">
    	<#if switchName>
    	<span class="current" onclick="loadLatestNew(1);">内部邮件<#if newsNew><font></font></#if></span>
    	<#else>
    	<span class="current" onclick="loadLatestNew(1);">办公消息<#if newsNew><font></font></#if></span>
    	</#if>
    	<#list modules as module>
	    	<#if module.id == 70504 || module.id == 70004>
    		<span onclick="loadLatestNew(2);">办公公告<#if bulletinNew3><font></font></#if></span>
	    	</#if>
	    	<#if module.id == 70502 || module.id == 70002>
    		<span onclick="loadLatestNew(5);">通知消息<#if bulletinNew5><font></font></#if></span>
    		</#if>
	    	<#if module.id == 70503 || module.id == 70003>
    		<span onclick="loadLatestNew(4);">行事历<#if bulletinNew2><font></font></#if></span>
    		</#if>
    	</#list>
    	<span onclick="loadLatestNew(3);">待办事项<#if docNew><font></font></#if></span>
    </span>
    <span class="fn-right">
    	<#list modules as module>
    	<#if module.id!=70504&&module.id!=70004>
    		<a href="javascript:void(0);" id="a_${module.id}" onclick="go2Module(<#if module.id==69002||module.id==69052>'${module.url}?desktopIn=1'<#else>'${module.url}'</#if>,'${module.subsystem}','${module.id}','${module.parentid}','${module.name}','desktop');return false;">更多&gt;&gt;</a>
    		<#if module.id==70502>
		    	<a href="javascript:void(0);" id="a_70504" onclick="go2Module('${module.url}','${module.subsystem}','${module.id}','${module.parentid}','${module.name}','desktop');return false;">更多&gt;&gt;</a>
    		</#if>
    		<#if module.id==70002>
		    	<a href="javascript:void(0);" id="a_70004" onclick="go2Module('${module.url}','${module.subsystem}','${module.id}','${module.parentid}','${module.name}','desktop');return false;">更多&gt;&gt;</a>
    		</#if>
    	</#if>
    	</#list>
	</span>
</div>
<div id="bjNewsList"></div>
<script>
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
	function loadLatestNew(type){
		var a69052,a70504,a70503,a70502;
		<#if edu>
			a69052 = $("#a_69052");
			a70504 = $("#a_70504");
			a70503 = $("#a_70503");
			a70502 = $("#a_70502");
		<#else>
			a69052 = $("#a_69002");
			a70504 = $("#a_70004");
			a70503 = $("#a_70003");
			a70502 = $("#a_70002");
		</#if>
		if(type==1){
			load("#bjNewsList","${request.contextPath}/office/desktop/app/bjMessages.action");
			a69052.attr("style","display:block;");
			if(a70504 != null){
				a70504.attr("style","display:none;");
			}
			if(a70503 != null){
				a70503.attr("style","display:none;");
			}
			if(a70502 != null){
				a70502.attr("style","display:none;");
			}
		}else if(type==2){
			$("#bjNewsList").load("${request.contextPath}/office/desktop/app/newslist.action?bulletinType="+3,function(){
				$("#bjNewsList").children("p").each(function(){
					$(this).remove();
				});
			});			
			
			a70504.attr("style","display:block;");
			if(a70503 != null){
				a70503.attr("style","display:none;");
			}
			if(a70502 != null){
				a70502.attr("style","display:none;");
			}
			a69052.attr("style","display:none;");
		}else if(type==3){
			load("#bjNewsList","${request.contextPath}/office/desktop/app/bjToDoWork.action");
			if(a70504 != null){
				a70504.attr("style","display:none;");
			}
			if(a70503 != null){
				a70503.attr("style","display:none;");
			}
			if(a70502 != null){
				a70502.attr("style","display:none;");
			}
			a69052.attr("style","display:none;");
		}else if(type==4){
			$("#bjNewsList").load("${request.contextPath}/office/desktop/app/newslist.action?bulletinType="+2,function(){
				$("#bjNewsList").children("p").each(function(){
					$(this).remove();
				});
			});			
			
			a70503.attr("style","display:block;");
			if(a70504 != null){
				a70504.attr("style","display:none;");
			}
			if(a70502 != null){
				a70502.attr("style","display:none;");
			}
			a69052.attr("style","display:none;");
		}else if(type==5){
			$("#bjNewsList").load("${request.contextPath}/office/desktop/app/newslist.action?bulletinType="+1,function(){
				$("#bjNewsList").children("p").each(function(){
					$(this).remove();
				});
			});			
			
			a70502.attr("style","display:block;");
			if(a70504 != null){
				a70504.attr("style","display:none;");
			}
			if(a70503 != null){
				a70503.attr("style","display:none;");
			}
			a69052.attr("style","display:none;");
		}
	}
	loadLatestNew(1);
	
	function viewContent(id){
		$("#"+id+"_span").css("font-weight","normal");
		window.open("${request.contextPath}/office/desktop/app/info-viewDetail.action?bulletinId="+id,'','fullscreen,scrollbars,resizable=yes,toolbar=no');
		var i=0;
		$("#bjNewsList").find("span").each(function(){
			if(jQuery(this).css("font-weight")==700){
				i++;
			}
		});
		if(i==0){
			$("#news").find("span.current").children("font").remove();
		}
	}
</script>