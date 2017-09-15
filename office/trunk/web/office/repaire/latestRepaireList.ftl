<#import "/common/htmlcomponent.ftl" as common />
<@common.tableList id="listTable" name="listTable" class="public-table table-list table-dragSort" style="table-layout:fixed">
	<tr>
		<#if loginInfo.unitClass == 2 && teachAreaList?size gt 1>
		<th class="t-center" width="8%">校区</th>
		</#if>
		<th class="t-center" width="8%">类别</th>
		<#if loginInfo.unitClass == 2 && "hzzc" != systemDeploySchool>
		<th class="t-center" width="10%">班级信息</th>
		</#if>
    	<th class="t-center" width="8%">报修人</th>
		<th class="t-center" width="8%">联系电话</th>
		<th class="t-center" width="8%">报修时间</th>
		<th class="t-center" width="10%">设备名称</th>
    	<th class="t-center" width="15%">故障设备地点</th>
    	<#if loginInfo.unitClass == 2 && "hzzc" != systemDeploySchool>
		<th class="t-center" width="9%">故障详情</th>
		<#else>
		<th class="t-center" width="19%">故障详情</th>
		</#if>
		<th class="t-center" width="8%">维修状态</th>
		<th class="t-center" width="8%">操作</th>
	</tr>
<#if repaireList?exists &&  (repaireList?size>0)>
	<#list repaireList as x>
	<tr>
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
	  	<td class="t-center" style="word-break:break-all; word-wrap:break-word;">${x.remark?default('')}</td>
	  	<td class="t-center">
	  		<#if x.state == '1'>
	  			<font color="red">未维修</font>
	  		<#elseif x.state == "2">
	  			维修中	
	  		<#else>
	  			已维修
	  		</#if>
	  	</td>
	  	<td class="t-center">
				<a href="javascript:view('${x.id!}');">查看</a>
			</td>
	</tr>
	</#list>
<#else>
	<tr>
		<#assign colspan = 9>
		<#if loginInfo.unitClass == 2 && teachAreaList?size gt 1>
			<#assign colspan = colspan + 1>
		</#if>
		<#if loginInfo.unitClass == 2  && "hzzc" != systemDeploySchool>
		<#assign colspan = colspan + 1>
		</#if>
		<td colspan="${colspan}"> <p class="no-data mt-20">还没有任何记录哦！</p></td>
	</tr>
</#if>
</@common.tableList>
<@common.Toolbar container="#latestRepaireListDiv"/>
<#if needPrint>
<div id="printDiv" style="display:none;">
	<@common.tableList id="listTable1" name="listTable1" class="public-table table-list table-dragSort" style="table-layout:fixed">
		<tr>
		<#if loginInfo.unitClass == 2 && teachAreaList?size gt 1>
			<th class="t-center" width="8%">校区</th>
		</#if>
			<th class="t-center" width="8%">类别</th>
			<#if loginInfo.unitClass == 2 && "hzzc" != systemDeploySchool>
			<th class="t-center" width="8%">班级信息</th>
			</#if>
	    	<th class="t-center" width="8%">报修人</th>
			<th class="t-center" width="10%">联系电话</th>
			<th class="t-center" width="8%">报修时间</th>
			<th class="t-center" width="8%">设备名称</th>
	    	<th class="t-center" width="12%">故障设备地点</th>
			<th class="t-center" width="12%">故障详情</th>
			<th class="t-center" width="10%">维修状态</th>
			<th class="t-center" width="10%">操作</th>
		</tr>
	<#if repaireAllList?exists &&  (repaireAllList?size>0)>
		<#list repaireAllList as x>
		<tr>
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
		  	<td class="t-center" style="word-break:break-all; word-wrap:break-word;">${x.remark?default('')}</td>
		  	<td class="t-center">
		  		<#if x.state == '1'>
		  			<font color="red">未维修</font>
		  		<#elseif x.state == "2">
		  			维修中	
		  		<#else>
		  			已维修
		  		</#if>
		  	</td>
		  	<td class="t-center">
				<a href="javascript:view('${x.id!}');">查看</a>
			</td>
		</tr>
		</#list>
	</#if>
	</@common.tableList>

</div>
</#if>
<p class="t-center pt-30">
	<#if needPrint>
		<a href="javaScript:void(0);" onclick="doPrint();" class="abtn-blue-big">打印</a>
	</#if>
	<a href="javaScript:void(0);" onclick="doExport();" class="abtn-blue-big">导出</a>
</p>
<iframe name="downloadFrame" id="downloadFrame" style="display:none;"></iframe>
<script>
$(document).ready(function(){
	vselect();
});
function doPrint(){
	LODOP=getLodop();
	LODOP.SET_PRINT_PAGESIZE(2, 0, 0,"A4"); //打印页面设置，2表示横打，A4纸
	LODOP.ADD_PRINT_HTM("10mm","5mm","RightMargin:5mm","BottomMargin:10mm",getPrintContent(jQuery('#printDiv')));
  	LODOP.PREVIEW();
}
function doExport(){
	var state = document.getElementById("repaireState").value;
	var areaId= document.getElementById("repaireAreaId").value;
	var type = document.getElementById("repaireType").value;
	var startTime = document.getElementById("startTime").value;
	var endTime = document.getElementById("endTime").value;
	var startTime= document.getElementById("startTime").value;
	var endTime= document.getElementById("endTime").value;
	if(startTime!=''&&endTime!=''){
		var re = compareDate(startTime,endTime);
		if(re==1){
			showMsgWarn("结束时间不能小于开始时间！");
			return;
		}
	}
	location.href="${request.contextPath}/office/repaire/repaire-latestRepaireListExport.action?state="+state+"&areaId="+areaId+"&type="+type+"&startTime="+startTime+"&endTime="+endTime;
	//"${request.contextPath}/office/teacherLeave/teacherLeave-applySummaryExport.action"+str;
}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/printarea.js"></script>