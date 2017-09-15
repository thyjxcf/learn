<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="按服务授权">
<div class="query-builder">
    	<div class="query-part fn-rel fn-clear">
			<div class="query-tt b"></div>
			<#if EisuSchool>
			<@commonmacro.selectDiv idObjectId="specialtyId" nameObjectId="specialtyId" url="/common/getSpecialtySelectDivData.action" divName='专业' onclick="" referto="classId">
			</@commonmacro.selectDiv >
			<@commonmacro.fuzzySelectDiv  idObjectId="classId" nameObjectId="className" url="/common/getFuzzyClassSelectDivData.action"  divName='班级' onclick="getList" dependson="specialtyId">
			</@commonmacro.fuzzySelectDiv>
			<#else >
			<@commonmacro.selectDiv idObjectId="gradeId" nameObjectId="gradeId" url="/common/getEisGradeSelectDivData.action" divName='年级' onclick="" referto="classId">
			</@commonmacro.selectDiv >
			<@commonmacro.fuzzySelectDiv  idObjectId="classId" nameObjectId="className" url="/common/getFuzzyEisClassSelectDivData.action" otherParam="schoolId=${unitId!}"  divName='班级' onclick="getList" dependson="gradeId">
			</@commonmacro.fuzzySelectDiv>
			</#if>
			
		</div>
</div>
<div id="deptAdminContainer">
</div>
<script>
	function getList(id){
	    load("#deptAdminContainer","${request.contextPath}/basedata/user/userOpenAdmin-listStudent.action?classId="+id);
	}
	$(document).ready(function(){
		var url="${request.contextPath}/common/tipMsg.action?";
		<#if EisuSchool>
			url +="msg=请选择专业&subMsg=班级";
		<#else>
			url += "msg=请选择年级&subMsg=班级";
		</#if>
	   load("#deptAdminContainer",url);
	});
</script>
</@htmlmacro.moduleDiv>