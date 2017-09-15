<script>
	$('#broadcastDiv ul li').hover(
		function(){
			$(this).children('i').show();
		},function(){
			$(this).children('i').hide();
		}
	);
	
	$('#broadcastDiv ul .add').click(function(e){
		e.preventDefault();
		$('#broadcastDiv .form').show();
	});
</script>
<li><a href="javascript:void(0);" class="add"><img src="${request.contextPath}/static/images/app/desk_icon/add.png" alt="添加"><span>添加</span></a></li>
<#list broadcastList as app>
	<li id="${app.id!}" class="externalAppList"><i title="移除" onclick="deleteBroadcast('${app.id!}');return false;"></i><a href="javascript:void(0);"><img src="${request.contextPath}/static/images/app/desk_icon/004.png"><span>${app.appName!}</span></a></li>
</#list>