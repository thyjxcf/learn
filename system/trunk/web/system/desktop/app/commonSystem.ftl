<script>
function go2commonSystemForNewWindow(url){
	if(url == "")
		return;
	window.open(url);
}
</script>
<div class="dt fn-clear">
	<span class="item-name fn-left">常用操作</span>
</div>
<ul class="app-list app-list-yes">
    <li data-appClass="1"><p><a href="javascript:void(0);" onclick="go2commonSystemForNewWindow('http://zhxy.zczyedu.net/eisu8/system/homepage/index.action?appId=65')"><img src="${request.contextPath}/static/images/eiss/ope_01.png">资产管理</a></p></li>
    <li data-appClass="3"><p><a href="javascript:void(0);" onclick="go2commonSystemForNewWindow('http://zhxy.zczyedu.net/netdisk')"><img src="${request.contextPath}/static/images/eiss/ope_03.png">网络硬盘</a></p></li>
    <li data-appClass="4"><p><a href="javascript:void(0);" onclick="go2commonSystemForNewWindow('http://www.zczyedu.net/user/manageInfos.aspx')"><img src="${request.contextPath}/static/images/eiss/ope_04.png">网站后台</a></p></li>
    <li data-appClass="5"><p><a href="javascript:void(0);" onclick="go2Module('office/repaire/repaire.action','97','71006','97001','报修','desktop'); return false;"><img src="${request.contextPath}/static/images/eiss/ope_05.png">设备报修</a></p></li>
    <li data-appClass="6"><p><a href="javascript:void(0);" onclick="go2commonSystemForNewWindow('http://zhxy.zczyedu.net/res')"><img src="${request.contextPath}/static/images/eiss/ope_06.png">资源管理</a></p></li>
	<li data-appClass="2"><p><a href="javascript:void(0);" onclick="go2commonSystemForNewWindow('http://zhxy.zczyedu.net/netdisk?target=othershare&continue=true')"><img src="${request.contextPath}/static/images/eiss/ope_02.png">软件下载</a></p></li>
</ul>
