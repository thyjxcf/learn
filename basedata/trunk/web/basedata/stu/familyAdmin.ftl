<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showMsg />
<@htmlmacro.moduleDiv titleName="学生家庭信息">
<#import "/common/commonmacro.ftl" as commonmacro>
<script>
function stuIdList(id){
	load("#famDiv","${request.contextPath}/basedata/stu/familyAdmin-list.action?studentId="+id);
}

function stuIdList2(){
	var queryStudentCode = encodeURIComponent(trim(document.getElementById("queryStudentCode").value).replace(/\s+/g, "%").replace(/　+/g, "%"));
	var queryStudentName = encodeURIComponent(trim(document.getElementById("queryStudentName").value).replace(/\s+/g, "%").replace(/　+/g, "%"));
    
    if(queryStudentCode=='' && queryStudentName == ''){
		showMsgWarn('请输入学号或姓名查询条件');
		return;
	}
	if(queryStudentCode.indexOf('\'')!=-1||queryStudentName.indexOf('\'')!=-1) {
	    showMsgWarn("请勿输入非安全字符");
	    return;
	}
	load("#famDiv","${request.contextPath}/basedata/stu/familyAdmin-list2.action?queryStudentCode="+queryStudentCode+"&queryStudentName="+queryStudentName);
}
</script>
<div class="query-builder">
	<div class="query-part fn-rel fn-clear">
		<div class="query-tt b">请选择：</div>
		<@commonmacro.selectDiv idObjectId="specialtyId" nameObjectId="specialtyId" url="/common/getSpecialtySelectDivData.action" divName='专业' otherParam="stusysShowPopedom=true" onclick="" referto="classId,studentId">
		</@commonmacro.selectDiv>
		<@commonmacro.fuzzySelectDiv idObjectId="classId" nameObjectId="className" url="/common/getFuzzyClassSelectDivData.action" divName='班级' otherParam="stusysShowPopedom=true" dependson="specialtyId" referto="studentId">
		</@commonmacro.fuzzySelectDiv>
		<@commonmacro.fuzzySelectDiv idObjectId="studentId" nameObjectId="studentName" url="/common/getFuzzyStudentSelectDivData.action"  dependson="classId"
			divName='学生' onclick="stuIdList" defaultItem="N" tipMsg="该班级下没有学生(如果没有选择班级，请先选择班级)">
		</@commonmacro.fuzzySelectDiv>
		<div class="query-tt ml-10">
		学号：<input type="text" class="input-txt" name="queryStudentCode" id="queryStudentCode">
		学生姓名：<input type="text" class="input-txt" name="queryStudentName" id="queryStudentName">
		</div>
		<a href="javascript:stuIdList2();" class="abtn-blue ml-30 fn-left">查找</a>
	    <div class="fn-clear"></div>
	</div>
</div>
<div id="famDiv"></div>
<script>
$(document).ready(function(){
	load('#famDiv','${request.contextPath}/common/tipMsg.action?msg=请选择一个学生！');
})
</script>
</@htmlmacro.moduleDiv>