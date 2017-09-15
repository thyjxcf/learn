<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function addAttendanceGroup(){
	load("#showListDiv","${request.contextPath}/office/teacherAttendance/teacherAttendance-newGroup.action?addAttendance=true");
}
jQuery(document).ready(function(){
	load("#groupListDiv","${request.contextPath}/office/teacherAttendance/teacherAttendance-groupList.action");
});
function editNotAttendanceGroup(){
	openDiv("#addDiv", "#addDiv.close", "${request.contextPath}/office/teacherAttendance/teacherAttendance-notAddStatisticPeopleLink.action",null,null,"500px");
}
</script>
<div class="popUp-layer" id="addDiv" title="">
    
</div>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
	<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
	<div class="query-part">
		
		<a href="javascript:void(0);" class="abtn-orange-new ml-10 fn-right" onclick="editNotAttendanceGroup();">不参加考勤统计人员维护</a>
		<a href="javascript:void(0);" class="abtn-orange-new  fn-right" onclick="addAttendanceGroup();">新增考勤组</a>
		<div class="fn-clear"></div>
	</div>
	</div>
	</div>
</div>
<div id="groupListDiv">
	
</div>

</@common.moduleDiv>