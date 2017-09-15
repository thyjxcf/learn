<#import "/common/htmlcomponent.ftl" as common />
<#import "/common/commonmacro.ftl" as commonmacro>
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

function toSearch() {
	var acadyear = $("#acadyear").val();
	var semester = $("#semester").val();
	load('#container','${request.contextPath}/office/attendance/attendance-radio.action?acadyear='+acadyear+'&semester='+semester);
}

function searchTeachClass() {
    $("#classIdSelectedName").val('请选择行政班');
    var courseId = $("#courseId").val();
    if(courseId !='') {
		var url="${request.contextPath}/office/attendance/attendance-radioList.action?courseId="+courseId;
		load("#courseAttendanceDiv", url);
    }else {
    	load('#courseAttendanceDiv','${request.contextPath}/common/tipMsg.action?msg=请选择班级！');
    }
}

function searchClass() {
    $("#cc a").removeAttr("class");
    var acadyear = $("#acadyear").val();
	var semester = $("#semester").val();
    var classId = $("#classId").val();
	var url="${request.contextPath}/office/attendance/attendance-radioList.action?classId="+classId+"&acadyear="+acadyear+"&semester="+semester;
	load("#courseAttendanceDiv", url);
}

$(document).ready(function(){
	load('#courseAttendanceDiv','${request.contextPath}/common/tipMsg.action?msg=请选择班级！');
});

</script>

<div class="popUp-layer" id="classLayer3" style="display:none;width:500px;"></div>
<div class="popUp-layer" id="bulletinLayer" style="display:none;top:100px;left:300px;width:700px;height:580px;"></div>
<div class="pub-tab">
	<ul class="pub-tab-list">
	<li onclick="attendance();">到课情况</li>
	<li class="current" onclick="attendanceRadio();">到课率</li>
	<li onclick="attendanceQuery();">门禁查询</li>
	</ul>
</div>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
		<div class="query-builder-nobg mt-15" style="padding:0 0 45px 0;">  
    		<div class="query-part">
    			<div class="query-tt b ml-10"><span class="fn-left">学年：</span></div>
			    <div class="select_box fn-left">
					<@common.select style="width:100px;" valId="acadyear" valName="acadyear" txtId="searchAcadyearTxt" myfunchange="toSearch" >
						<#list acadyearList as item>
							<a val="${item}" <#if item == acadyear?default('')>class="selected"</#if>>${item!}</a>
						</#list>
					</@common.select>
				</div>
				
				<div class="query-tt b ml-10"><span class="fn-left">学期：</span></div>
				<div class="select_box fn-left">
					<@common.select style="width:100px;" valName="semester" valId="semester" myfunchange="toSearch">
						<a val="1"  <#if semester?default("1")=="1">class="selected"</#if>><span>第一学期</span></a>
						<a val="2"  <#if semester?default("1")=="2">class="selected"</#if>><span>第二学期</span></a>
					</@common.select>
				</div>
				<div class="query-tt b ml-10">
					<span class="fn-left">选修班：</span>
				</div>
				<div class="fn-left" id="cc">
					<@common.select style="width:180px;" valName="courseId" valId="courseId" txtId="courseTxtId" myfunchange="searchTeachClass">
						<a val="">请选择选修班</a>
						<#if eduadmCourseDtoList?exists && (eduadmCourseDtoList.size() > 0)>
							<#list eduadmCourseDtoList as course>
								<a val="${course.id!}" name="c">${course.subjectName!}</a>
							</#list>
						</#if>
					</@common.select>
				</div>
				<#if admin?default(false)>
					<@commonmacro.fuzzySelectDiv idObjectId="classId" nameObjectId="className" url="/common/getFuzzyClassSelectDivData.action" divName='行政班' onclick="searchClass">
		       	 	</@commonmacro.fuzzySelectDiv>
		        <#else>
		        	<@commonmacro.fuzzySelectDiv idObjectId="classId" nameObjectId="className" url="/common/getFuzzyClassSelectDivData.action" divName='行政班' otherParam="teacherId=${teacherId!}" onclick="searchClass">
		            </@commonmacro.fuzzySelectDiv>
		        </#if>
			</div>
    	</div>
    </div>
</div>
<div id="courseAttendanceDiv"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>