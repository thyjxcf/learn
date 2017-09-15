<script>
	$('#addSystem ul li').hover(
		function(){
			$(this).children('i').show();
		},function(){
			$(this).children('i').hide();
		}
	);
	
	$('#addSystem ul .add').click(function(e){
		e.preventDefault();
		$('#addSystem .form').show();
	});
</script>
<li><a href="javascript:void(0);" class="add"><img src="${request.contextPath}/static/images/app/desk_icon/add.png" alt="添加"><span>添加</span></a></li>
<#list externalAppList as app>
	<li id="${app.id!}" class="externalAppList"><i title="移除" onclick="deleteExternalApp('${app.id!}');return false;"></i><a href="javascript:void(0);"><img src="${app.downloadPath!}" alt="${app.appName!}"><span>${app.appName!}</span></a></li>
</#list>