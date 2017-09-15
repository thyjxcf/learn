<div class="dt fn-clear">
	<span class="item-name fn-left">通知</span>
    <span class="tab">
    	<span class="current" onclick="loadBulletinList(2);">本单位通知</span>
    	<span onclick="loadBulletinList(1);">教育局通知</span>
    </span>
    <span class="fn-right">
    		<a href="javascript:void(0);" id="a_${module.id?substring(0,2)}" onclick="go2Module('${module.url}','${module.subsystem}','${module.id}','${module.parentid}','${module.name}','desktop');return false;">更多</a>
	</span>
</div>
<div id="bulletinList"></div>
<script>
	$('.newest .tab span').click(function(){
		$(this).addClass('current').siblings('span').removeClass('current');
	});
	function loadBulletinList(tabClass){
		load("#bulletinList","${request.contextPath}/office/desktop/app/smgBulletinList.action?bulletinType=${bulletinType!}&tabClass="+tabClass);
	}
	loadBulletinList(2);
</script>