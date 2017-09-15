<#import "/common/htmlcomponent.ftl" as common />

<@common.moduleDiv titleName="">
<script>
function deleteRe(){
	var ci = '';
		var tps = document.getElementsByName("reId");
		for(var k=0;k<tps.length;k++){
			if(tps[k].checked==true){
				if(ci != ''){
					ci+=',';
				}
				ci+= tps[k].value;
			}
		}
		if(ci == ''){
			showMsgWarn("没有选要删除的信息，请先选择！");
			return false;
		}
	if(!showConfirm('确认要删除所选信息吗？')){
		return false;
	}
	
	$.getJSON("${request.contextPath}/office/repaire/repaire-myDel.action", 
	{"id":ci}, function(data){
		//如果有错误信息（与action中对应），则给出提示
		if(data && data != ""){
			showMsgError(data);
			return;
		}
		else{
			//没有错误，提示成功，关闭提示窗口后，通过调用回调函数，使页面进行刷新
			showMsgSuccess("删除成功！", "提示", toMyRepaire);			
			return;
		}
	}).error(function(){
		showMsgError("删除失败！");
	});
}
</script>
<form>
<@common.tableList id="listTable" name="listTable" class="public-table table-list table-dragSort" style="table-layout:fixed">
	<tr>
		<th class="t-center" width="5%">选择</th>
		<#if loginInfo.unitClass == 2 && teachAreaList?size gt 1>
		<th class="t-center" width="7%">校区</th>
		</#if>
		<th class="t-center" width="7%">类别</th>
		<#if loginInfo.unitClass == 2 && "hzzc" != systemDeploySchool>
		<th class="t-center" width="10%">班级信息</th>
		</#if>
	    <th class="t-center" width="7%">报修人</th>
		<th class="t-center" width="8%">联系电话</th>
		<th class="t-center" width="8%">报修时间</th>
		<#if loginInfo.unitClass == 2 && "hzzc" != systemDeploySchool>
		<th class="t-center" width="8%">设备名称</th>
	    <th class="t-center" width="10%">故障设备地点</th>
		<th class="t-center" width="8%">故障详情</th>
		<#else>
		<th class="t-center" width="10%">设备名称</th>
		<th class="t-center" width="12%">故障设备地点</th>
		<th class="t-center" width="14%">故障详情</th>
		</#if>
		<th class="t-center" width="8%">维修状态</th>
		<th class="t-center" width="8%">维修备注</th>
		<th class="t-center" width="6%">操作</th>
	</tr>
<#if repaireList?exists &&  (repaireList?size>0)>
	<#list repaireList as x>
	<tr>
		<td class="t-center">
			<p>
	    		<span class="ui-checkbox">
					<input type="checkbox" class="chk" name="reId" value="${x.id!}">
				</span>
			</p>
		</td>
		<#if loginInfo.unitClass == 2 && teachAreaList?size gt 1>
	    <td class="t-center" style="word-break:break-all; word-wrap:break-word;">${x.teachAreaName!}</td>
	    </#if>
		<td class="t-center" style="word-break:break-all; word-wrap:break-word;">${appsetting.getMcode("DM-BXLB").get(x.type!)}<#if x.repaireTypeName?exists && x.repaireTypeName != ''>(${x.repaireTypeName!})</#if></td>
		<#if loginInfo.unitClass == 2 && "hzzc" != systemDeploySchool>
	  	<td class="t-center" style="word-break:break-all; word-wrap:break-word;">${x.className!}</td>
		</#if>
	  	<td class="t-center" style="word-break:break-all; word-wrap:break-word;">${x.userName!}</td>
		<td class="t-center" style="word-break:break-all; word-wrap:break-word;">${x.phone!}</td>
	  	<td class="t-center">${(x.detailTime?string('MM-dd HH:mm'))?if_exists}</td>
	  	<td class="t-center" style="word-break:break-all; word-wrap:break-word;">${x.goodsName!}</td>
	    <td class="t-center" style="word-break:break-all; word-wrap:break-word;">${x.goodsPlace!}<#if x.equipmentType?exists && x.equipmentType != ''>(${appsetting.getMcode("DM-SBLX").get(x.equipmentType!)})</#if></td>
	  	<td class="t-center" style="word-break:break-all; word-wrap:break-word;">${x.remark?default('')} </td>
	  	<td class="t-center">
	  		<#if x.state == "1">
	  			<font color="red">未维修</font>
	  		<#elseif x.state == "2">
	  			维修中
	  		<#elseif x.state == "3">
	  			已维修
	  		</#if>
	  	</td>
	  	<td class="t-center" style="word-break:break-all; word-wrap:break-word;">${x.repaireRemark!}</td>
		<td class="t-center">
	  		<#if x.state =='1'>
				<a href="javascript:edit('${x.id!}');">编辑</a>
			<#elseif x.state='2'>
				<a href="javascript:view('${x.id!}');">查看</a>
			<#elseif x.state='3'>
				<#if x.isFeedback>
					<a href="javascript:view('${x.id!}');">已反馈</a>
				<#else>
					<a href="javascript:feedback('${x.id!}');">未反馈</a>
				</#if>
			</#if>
		</td>
	</tr>
	</#list>
<#else>
	<tr>
		<#assign colspan = 11>
		<#if loginInfo.unitClass == 2 && teachAreaList?size gt 1>
			<#assign colspan = colspan + 1>
		</#if>
		<#if loginInfo.unitClass == 2 && "hzzc" != systemDeploySchool>
		<#assign colspan = colspan + 1>
		</#if>
		<td colspan="${colspan}"> <p class="no-data mt-20">还没有任何记录哦！</p></td>
	</tr>
</#if>

</@common.tableList>			
<@common.Toolbar container="#myRepaireListDiv">
<span class="ui-checkbox ui-checkbox-all" data-all="no"><input type="checkbox" class="chk">全选</span>
<a class="abtn-blue" href="javascript:void(0);" onclick="deleteRe();">删除</a>
</@common.Toolbar>
<iframe name="downloadFrame" id="downloadFrame" style="display:none;"></iframe>
</form>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>