<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function attendance() {
	load('#container','${request.contextPath}/office/attendance/attendance.action');
}

function attendanceRadio() {
	load('#container','${request.contextPath}/office/attendance/attendance-radio.action');
}

function attendanceQuery() {
	load('#container','${request.contextPath}/office/attendance/attendance-query.action');
}

function searchList() {
    var id = $("#id").val();
    var queryBeginDate= $("#queryBeginDate").val();
    var queryEndTime=$("#queryEndTime").val();
    if(compareDate(queryBeginDate, queryEndTime) > 0 ){
		showMsgWarn("开始时间不能大于结束时间，请重新选择！");
		return;
	}
	var url="${request.contextPath}/office/attendance/attendance-queryList.action?id="+id+"&queryBeginDate="+queryBeginDate+"&queryEndTime="+queryEndTime;
	load("#courseAttendanceDiv", url);
}

$(document).ready(function(){
	searchList();
});

</script>

<div class="popUp-layer" id="classLayer3" style="display:none;width:500px;"></div>
<div class="popUp-layer" id="bulletinLayer" style="display:none;top:100px;left:300px;width:700px;height:580px;"></div>
<div class="pub-tab">
	<ul class="pub-tab-list">
	<li onclick="attendance();">到课情况</li>
	<li onclick="attendanceRadio();">到课率</li>
	<li class="current" onclick="attendanceQuery();">门禁查询</li>
	</ul>
</div>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
		<div class="query-builder-nobg mt-15" style="padding:0 0 45px 0;">  
    		<div class="query-part">
    			<div class="query-tt b ml-10"><span class="fn-left">机房：</span></div>
    			<div class="fn-left">
					<@common.select style="width:180px;" valName="id" valId="id" myfunchange="">
						<a val="">请选择</a>
						<#if teachPlaceList?exists && (teachPlaceList.size() > 0)>
							<#list teachPlaceList as teachPlace>
								<a val="${teachPlace.id!}">${teachPlace.placeName!}</a>
							</#list>
						</#if>
					</@common.select>
				</div>
				<div class="query-tt b ml-10"><span class="fn-left">日期：</span></div>
    			<div class="fn-left">
					<@common.datepicker class="input-txt" style="width:100px;" id="queryBeginDate" 
				   	value="${(queryBeginDate?string('yyyy-MM-dd'))?if_exists}"/>
				</div>
				<div class="query-tt ml-10 mt-5"><span class="fn-left">-&nbsp</span></div>
	    		<div class="fn-left">
	            	<@common.datepicker class="input-txt" style="width:100px;" id="queryEndTime" 
	   				value="${(queryEndTime?string('yyyy-MM-dd'))?if_exists}"/>
				</div>
				<a href="javascript:void(0)" onclick="searchList();" class="abtn-blue fn-left ml-20">查找</a>
    		</div>
    	</div>
    </div>
</div>
<div id="courseAttendanceDiv"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>