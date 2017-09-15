<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function sear(){
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
	var url="${request.contextPath}/office/repaire/repaire-latestRepaireList.action?state="+state+"&areaId="+areaId+"&type="+type+"&startTime="+startTime+"&endTime="+endTime;
	load("#latestRepaireListDiv", url);
}
</script>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
	<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
	<div class="query-part fn-rel fn-clear">
		<#if loginInfo.unitClass == 2  && teachAreaList?size gt 1>
		<div class="query-tt ml-10">校区：</div>
	    <div class="fn-left">
    		<@common.select style="width:100px;" valName="areaName" valId="repaireAreaId" myfunchange="sear">
				<a val="">全部</a>
				<#list teachAreaList as area>
					<a val="${area.id}">${area.areaName!}</a>
				</#list>
			</@common.select>
		</div>
		<#else>
		<input type="hidden" name="repaireAreaId" id="repaireAreaId" value="00000000000000000000000000000000">
		</#if>
		<div class="query-tt ml-10">类别：</div>
	    <div class="fn-left">
			<@common.select style="width:100px;" valName="type" valId="repaireType" myfunchange="sear">
				<a val="">全部</a>
				<#list mcodelist as m>
					<a val="${m.thisId}">${m.content!}</a>
				</#list>
			</@common.select>
		</div>
		<div class="query-tt ml-10">维修状态：</div>
	    <div class="fn-left">
    		<@common.select style="width:100px;" valName="state" valId="repaireState" myfunchange="sear">
				<a val="">全部</a>
				<a val="1">未维修</a>
				<a val="2">维修中</a>
				<a val="3">已维修</a>
			</@common.select>
		</div>
		<div class="query-tt ml-10">报修开始日期：</div>
		<div class="fn-left">
			<@common.datepicker class="input-txt" style="width:100px;" id="startTime" 
		   	value="${(startTime?string('yyyy-MM-dd'))?if_exists}"/>
	    </div>
	    <div class="query-tt ml-10">报修结束日期：</div>
	    <div class="fn-left">
			<@common.datepicker class="input-txt" style="width:100px;" id="endTime" 
	   		value="${(endTime?string('yyyy-MM-dd'))?if_exists}"/>
		</div>
		<a href="javascript:void(0)" onclick="sear();" class="abtn-blue fn-left ml-20">查找</a>
		<div class="fn-clear"></div>
	</div>
	</div>
	</div>
</div>
<div id="latestRepaireListDiv"></div>
<script>
	$(document).ready(function(){
		sear();
	});
</script>
</@common.moduleDiv>