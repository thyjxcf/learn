<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function toPersonal(obj){
	$(obj).addClass('current').siblings('li').removeClass('current');
	var url="${request.contextPath}/office/schedule/schedule-toPersonal.action?calDto.creatorType=1";
	load("#scheduleDiv", url);
}

function toAuth(obj){
	var url="${request.contextPath}/office/schedule/schedule-toAuth.action";
	load("#scheduleDiv", url);
}


function toCalendarInfo(){
	var url="${request.contextPath}/office/schedule/schedule-toCalendarInfo.action";
	load("#scheduleDiv", url);
}


function toLeader(obj){
	$(obj).addClass('current').siblings('li').removeClass('current');
	var url="${request.contextPath}/office/schedule/schedule-toPersonal.action?calDto.creatorType=3";
	load("#scheduleDiv", url);
}

function toDeptLog(){
	var url="${request.contextPath}/office/schedule/schedule-toDeptLog.action";
	load("#scheduleDiv", url);
}

function toDeptLeaderSchedule(){
	var url="${request.contextPath}/office/schedule/schedule-deptLeaderSchedule.action";
	load("#scheduleDiv", url);
}

function viewSchedule(id){
	openDiv("#scheduleLayer", "#scheduleLayer .close,#scheduleLayer .submit,#scheduleLayer .reset", "${request.contextPath}/office/schedule/schedule-queryLogDetail.action?cal.id="+id, null, null, "600px");
}


</script>
<div class="popUp-layer schedule-layer-edit" id="scheduleLayer" style="display:none;width:1000px;height:600px;"></div>
<div class="popUp-layer schedule-layer-des" id="scheduleListLayer" style="display:none;width:1000px;height:600px;"></div>
<div class="pub-tab">
	<ul class="pub-tab-list">
	<li class="current" onclick="toPersonal(this);">个人日志</li>
	<#if groupHead>
	<li onclick="toDeptLog();">科室人员日志</li>
	</#if>
	<#if leader>
	<li onclick="toDeptLeaderSchedule();">科室负责人日志</li>
	</#if>
	<li onclick="toDeptWeekWork();">科室周重点工作</li>
	<#if hasLeader>
	<li onclick="toLeader(this);"><#if loginInfo.unitClass==1>局<#else>校</#if>领导日志</li>
	</#if>
	<li onclick="toCalendarInfo();">行事历</li>
	<#if hasAuthority>
	<li onclick="toAuth();">权限设置</li>
	</#if>
	</ul>
</div>
<div id="scheduleDiv"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script>
	$(document).ready(function(){
		toPersonal();
	});
	function toDeptWeekWork(){
		var url="${request.contextPath}/office/schedule/schedule-workOutline.action?isWeek=true&isMonth=false&isDay=false";
		load("#scheduleDiv",url);
	}
</script>
</@common.moduleDiv>