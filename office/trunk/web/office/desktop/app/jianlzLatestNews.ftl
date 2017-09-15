<div class="dt fn-clear">
    <span class="tab">
    	<#list modules as module>
	    	<#if module.id == 70502 || module.id == 70002>
    		<span class="current" onclick="loadLatestNew(1);">通知消息<#if bulletinNew5><font></font></#if></span>
    		</#if>
	    	<#if module.id == 70505 || module.id == 70005>
    		<span onclick="loadLatestNew(2);">政策文件<#if bulletinNew4><font></font></#if></span>
    		</#if>
    		<#if module.id==70539 || module.id==70039>
    		<span onclick="loadLatestNew(4);">检查记录<#if bulletinNew6><font></font></#if></span>
    		</#if>
    		<#if module.id==70540 || module.id==70040>
    		<span onclick="loadLatestNew(5);">工作计划<#if bulletinNew7><font></font></#if></span>
    		</#if>
    		<#if module.id==70541 || module.id==70041>
    		<span onclick="loadLatestNew(6);">工作小结<#if bulletinNew8><font></font></#if></span>
    		</#if>
    		<#if module.id==70542 || module.id==70042>
    		<span onclick="loadLatestNew(7);">行政值周<#if bulletinNew9><font></font></#if></span>
    		</#if>
    		<#if module.id==70543 || module.id==70043>
    		<span onclick="loadLatestNew(8);">其他<#if bulletinNew10><font></font></#if></span>
    		</#if>
    	</#list>
    	<span onclick="loadLatestNew(3);">未读消息<#if newsNew><font></font></#if></span>
    </span>
    <span class="fn-right">
    	<#list modules as module>
    		<a href="javascript:void(0);" id="a_${module.id}" onclick="go2Module(<#if module.id==69002||module.id==69052>'${module.url}?desktopIn=1'<#else>'${module.url}'</#if>,'${module.subsystem}','${module.id}','${module.parentid}','${module.name}','desktop');return false;">更多&gt;&gt;</a>
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
		var a69052,a70502,a70505,a70539,a70540,a70541,a70542,a70543;
		<#if edu>
			a69052 = $("#a_69052");
			a70502 = $("#a_70502");
			a70505 = $("#a_70505");
			a70539 = $("#a_70539");
			a70540 = $("#a_70540");
			a70541 = $("#a_70541");
			a70542 = $("#a_70542");
			a70543 = $("#a_70543");
		<#else>
			a69052 = $("#a_69002");
			a70502 = $("#a_70002");
			a70505 = $("#a_70005");
			a70539 = $("#a_70039");
			a70540 = $("#a_70040");
			a70541 = $("#a_70041");
			a70542 = $("#a_70042");
			a70543 = $("#a_70043");
		</#if>
		if(type==1){
			$("#bjNewsList").load("${request.contextPath}/office/desktop/app/newslist.action?bulletinType="+1,function(){
				$("#bjNewsList").children("p").each(function(){
					$(this).remove();
				});
			});			
			
			a70502.attr("style","display:block;");
			if(a70505 != null){
				a70505.attr("style","display:none;");
			}
			if(a70539 != null){
				a70539.attr("style","display:none;");
			}
			if(a70540 != null){
				a70540.attr("style","display:none;");
			}
			if(a70541 != null){
				a70541.attr("style","display:none;");
			}
			if(a70542 != null){
				a70542.attr("style","display:none;");
			}
			if(a70543 != null){
				a70543.attr("style","display:none;");
			}
			if(a69052!=null){
				a69052.attr("style","display:none;");
			}
		}else if(type==2){
			$("#bjNewsList").load("${request.contextPath}/office/desktop/app/newslist.action?bulletinType="+4,function(){
				$("#bjNewsList").children("p").each(function(){
					$(this).remove();
				});
			});			
			
			a70505.attr("style","display:block;");
			if(a70502 != null){
				a70502.attr("style","display:none;");
			}
			if(a70539 != null){
				a70539.attr("style","display:none;");
			}
			if(a70540 != null){
				a70540.attr("style","display:none;");
			}
			if(a70541 != null){
				a70541.attr("style","display:none;");
			}
			if(a70542 != null){
				a70542.attr("style","display:none;");
			}
			if(a70543 != null){
				a70543.attr("style","display:none;");
			}
			if(a69052!=null){
				a69052.attr("style","display:none;");
			}
		}else if(type==3){
			load("#bjNewsList","${request.contextPath}/office/desktop/app/bjMessages.action");
			a69052.attr("style","display:block;");
			if(a70502 != null){
				a70502.attr("style","display:none;");
			}
			if(a70505 != null){
				a70505.attr("style","display:none;");
			}
			if(a70539 != null){
				a70539.attr("style","display:none;");
			}
			if(a70540 != null){
				a70540.attr("style","display:none;");
			}
			if(a70541 != null){
				a70541.attr("style","display:none;");
			}
			if(a70542 != null){
				a70542.attr("style","display:none;");
			}
			if(a70543 != null){
				a70543.attr("style","display:none;");
			}
		}else if(type==4){
			$("#bjNewsList").load("${request.contextPath}/office/desktop/app/newslist.action?bulletinType="+21,function(){
				$("#bjNewsList").children("p").each(function(){
					$(this).remove();
				});
			});			
			
			a70539.attr("style","display:block;");
			if(a70502 != null){
				a70502.attr("style","display:none;");
			}
			if(a70505 != null){
				a70505.attr("style","display:none;");
			}
			if(a70540 != null){
				a70540.attr("style","display:none;");
			}
			if(a70541 != null){
				a70541.attr("style","display:none;");
			}
			if(a70542 != null){
				a70542.attr("style","display:none;");
			}
			if(a70543 != null){
				a70543.attr("style","display:none;");
			}
			if(a69052!=null){
				a69052.attr("style","display:none;");
			}
		}else if(type==5){
			$("#bjNewsList").load("${request.contextPath}/office/desktop/app/newslist.action?bulletinType="+22,function(){
				$("#bjNewsList").children("p").each(function(){
					$(this).remove();
				});
			});			
			
			a70540.attr("style","display:block;");
			if(a70502 != null){
				a70502.attr("style","display:none;");
			}
			if(a70505 != null){
				a70505.attr("style","display:none;");
			}
			if(a70539 != null){
				a70539.attr("style","display:none;");
			}
			if(a70541 != null){
				a70541.attr("style","display:none;");
			}
			if(a70542 != null){
				a70542.attr("style","display:none;");
			}
			if(a70543 != null){
				a70543.attr("style","display:none;");
			}
			if(a69052!=null){
				a69052.attr("style","display:none;");
			}
		}else if(type==6){
			$("#bjNewsList").load("${request.contextPath}/office/desktop/app/newslist.action?bulletinType="+23,function(){
				$("#bjNewsList").children("p").each(function(){
					$(this).remove();
				});
			});			
			
			a70541.attr("style","display:block;");
			if(a70502 != null){
				a70502.attr("style","display:none;");
			}
			if(a70505 != null){
				a70505.attr("style","display:none;");
			}
			if(a70539 != null){
				a70539.attr("style","display:none;");
			}
			if(a70540 != null){
				a70540.attr("style","display:none;");
			}
			if(a70542 != null){
				a70542.attr("style","display:none;");
			}
			if(a70543 != null){
				a70543.attr("style","display:none;");
			}
			if(a69052!=null){
				a69052.attr("style","display:none;");
			}
		}else if(type==7){
			$("#bjNewsList").load("${request.contextPath}/office/desktop/app/newslist.action?bulletinType="+24,function(){
				$("#bjNewsList").children("p").each(function(){
					$(this).remove();
				});
			});			
			
			a70542.attr("style","display:block;");
			if(a70502 != null){
				a70502.attr("style","display:none;");
			}
			if(a70505 != null){
				a70505.attr("style","display:none;");
			}
			if(a70539 != null){
				a70539.attr("style","display:none;");
			}
			if(a70540 != null){
				a70540.attr("style","display:none;");
			}
			if(a70541 != null){
				a70541.attr("style","display:none;");
			}
			if(a70543 != null){
				a70543.attr("style","display:none;");
			}
			if(a69052!=null){
				a69052.attr("style","display:none;");
			}
		}else if(type==8){
			$("#bjNewsList").load("${request.contextPath}/office/desktop/app/newslist.action?bulletinType="+25,function(){
				$("#bjNewsList").children("p").each(function(){
					$(this).remove();
				});
			});			
			
			a70543.attr("style","display:block;");
			if(a70502 != null){
				a70502.attr("style","display:none;");
			}
			if(a70505 != null){
				a70505.attr("style","display:none;");
			}
			if(a70539 != null){
				a70539.attr("style","display:none;");
			}
			if(a70540 != null){
				a70540.attr("style","display:none;");
			}
			if(a70541 != null){
				a70541.attr("style","display:none;");
			}
			if(a70542 != null){
				a70542.attr("style","display:none;");
			}
			if(a69052!=null){
				a69052.attr("style","display:none;");
			}
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