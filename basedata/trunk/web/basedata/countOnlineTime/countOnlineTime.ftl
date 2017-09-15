<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="">
<script>
function queryTime(){
   var startTime = $('#startTime').val();
   var endTime = $('#endTime').val();
   var shiId = $('#shiId').val();
   var quxianId = $('#quxianId').val();
   var unitName = $('#unitId').val();
   var queryType = "1";
   if(unitName==''){
   if(shiId==''){
    	showMsgWarn("请选择市！");
		return;
    }
    if(quxianId==''){
    	showMsgWarn("请选择区县！");
		return;
    }
    }
    if(startTime==''){
    	showMsgWarn("请选择开始时间！");
		return;
    }
    if(endTime==''){
    	showMsgWarn("请选择结束时间！");
		return;
    }
    if(endTime<startTime){
    	showMsgWarn("开始时间不能大于结束时间！");
		return;
    }
   var url = encodeURI("${request.contextPath}/basedata/countOnlineTime/countOnlineTimeAdmin.action?beginTime="+startTime+"&endTime="+endTime+"&quxianId="+quxianId+"&unitName="+unitName+"&shiId="+shiId+"&queryType="+queryType);
   load("#container",url);
}

function getQuxian(){
var shiId=$('#shiId').val();
$.getJSON("${request.contextPath}/basedata/countOnlineTime/countOnlineTimeAdmin-getQuxian.action", 
							{"shiId":shiId}, function(data){
							$("#quxianId").parent("div").find(".a-wrap").html("");
							$("#quxianName").val("--请选择区县--");						
							$.each(data,function(key){
									$("#quxianId").parent("div").find(".a-wrap").append("<a val='"+key+"'>"+data[key]+"</a>");
							});
	 }).error(function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);});
}

function doExport(){
var startTime = $('#startTime').val();
var endTime = $('#endTime').val();
var shiId = $('#shiId').val();
var quxianId = $('#quxianId').val();
var unitName = $('#unitId').val();
var queryType = "2";
var url = "${request.contextPath}/basedata/countOnlineTime/countOnlineTimeAdmin-doExport.action?beginTime="+startTime+"&endTime="+endTime+"&quxianId="+quxianId+"&unitName="+unitName+"&shiId="+shiId+"&queryType="+queryType;
window.location=url;

}
</script>
<div id="countTimeContainer">
<form name="searchForm" method="post">
	<div class="query-builder">
    	<div class="query-part">
    	<#if (regionLevel==3 || regionLevel==2) && (unitType==1 || unitType==2)>
        	<div class="query-tt ml-10">
					<span class="fn-left">地区：</span>
			</div>
		</#if>
		    <div class="fn-left">
		    <#if regionLevel==2>
		  	<@common.select style="width:130px;" valId="shiId" valName="shiId" txtId="shiName" myfunchange="getQuxian" >
		    <a val="">--请选择市--</a>
		    <#list shiRegionMap?keys as key>
			<a val="${key!}" <#if key=='${shiId!}'>class="selected"</#if>>${shiRegionMap.get(key)!}</a>
		    </#list>
	        </@common.select>
	        </#if> 
  	  		</div>    
  	  	       
		    <div class="fn-left ml-10">
		    <#if (regionLevel==3 || regionLevel==2) && (unitType==1 || unitType==2)>
		  	<@common.select  valId="quxianId" valName="quxianId" txtId="quxianName" style="width:180px">
		    <a val="">--请选择区县--</a>
		    <#list quxianMap?keys as key>
			<a val="${key!}" <#if key=='${quxianId!}'>class="selected"</#if>>${quxianMap.get(key)!}</a>
		    </#list>
	        </@common.select>
	        </#if> 
  	  		</div>	
  	  		<#if unitType==1 || unitType==2>
  	  		<div class="query-tt ml-10">
					<span class="fn-left">单位名称：</span>
			</div>
		    <div class="fn-left">
		  	 <input name="unitName" id="unitId" value="${unitName!}"  type="text" class="input-txt" style="width:150px;" maxlength="100">
  	  		</div>
		     </#if>
		  <br><br>
                <div class="query-tt ml-10">
					<span class="fn-left">开始时间：</span>
				</div>
				<div class="fn-left">
    			<@common.datepicker class="input-txt" style="width:120px;" id="startTime" dateFmt="yyyy-MM-dd HH:mm:ss" value="${beginTime!}"/>
				</div>
    			<div class="query-tt ml-10">
					<span class="fn-left">结束时间：</span>
				</div>
				<div class="fn-left">
    			<@common.datepicker class="input-txt" style="width:120px;" id="endTime" dateFmt="yyyy-MM-dd HH:mm:ss" value="${endTime!}"/>
				</div>
            <a href="javascript:void(0);" class="abtn-blue ml-50" onclick="queryTime();">统计</a>
        </div>
    </div>
	<p class="pub-operation">
    	<a href="javascript:void(0);" onclick="doExport();" class="abtn-blue">导出</a>
    </p>
</form>

<form name="ec" id="ec" action="" method="post"> 
<@common.tableList id="tablelist">
<tr>
	<th width="10%" class="t-center">姓名</th>
	<th width="10%" class="t-center">性别</th>
	<th width="10%" class="t-center">手机号</th>
	<th width="15%" class="t-center">用户名</th>
	<th width="15%" class="t-center">单位</th>
	<th width="15%" class="t-center">部门</th>
	<th width="10%" class="t-center">在线状态</th>
	<th width="10%" class="t-center">在线时长（小时）</th>
</tr>
<#if countOnlineTimeList?exists &&  (countOnlineTimeList?size>0)>
<#list countOnlineTimeList as x>
	<tr>			
		<td class="t-center">
			${x.name!}
		</td>
		<td class="t-center">
			${x.sex!}
		</td>
		<td class="t-center">
			${x.phone!}
		</td>
		<td class="t-center">
			${x.userName!}
		</td>
		<td class="t-center">
			${x.unitName!}
		</td>
		<td class="t-center">
			${x.department!}
		</td>
		<td class="t-center">
			${x.onlineStaus!}
		</td>
		<td class="t-center">
			${x.onlineHourTime!}
		</td>
		</tr>
</#list>
<#else>
	<tr>
		<td colspan="9"> <p class="no-data mt-20">还没有任何记录哦！</p></td>
	</tr>

</#if>		
</@common.tableList>
<@common.Toolbar container="#countTimeContainer">

</@common.Toolbar>	
</form>
</div>
</@common.moduleDiv>