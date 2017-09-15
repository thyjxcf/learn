<#import "/common/htmlcomponent.ftl" as common>
<#import "/common/commonmacro.ftl" as commonmacro>
<@common.moduleDiv titleName="${webAppTitle}--编辑用户">
<#assign EDU=stack.findValue('@net.zdsoft.eis.system.constant.SystemConstant@TEACHER_IMPORT_EDU')>
<#assign SCH=stack.findValue('@net.zdsoft.eis.system.constant.SystemConstant@TEACHER_IMPORT_SCH')>
<script>
function onchangefun(obj, str){
	var value = obj.value.trim();
	if (value == ""){
		obj.value = str;
	}
}

function inputFocus(obj, str){
	if (obj.value == str){
		obj.value = "";		
	}
}

function inputBlur(obj, str){
	if (obj.value == ""){
		obj.value = str;
	}
}

//根据编号、姓名或点到卡号去查询教职工
function queryTeacher(deptidnow){
	if(deptidnow==''){
		deptidnow = $("#deptidnow").val();
	}
   	var queryTchName =$("#queryTchName").val().trim();
	var queryTchId = $("#queryTchId").val().trim();
	var queryTchUserName = $("#queryTchUserName").val().trim();
	
	
	if (queryTchId == "请输入编号"){
		queryTchId = "";
	}
	if (queryTchName == "请输入姓名"){
		queryTchName = "";
	}
	if (queryTchUserName == "请输入账号"){
		queryTchUserName = "";
	}
	load("#teacherList","${request.contextPath}/basedata/teacher/teacherAdmin-list.action?modID=${modID?default('')}&deptidnow="+deptidnow+"&queryTchName="+queryTchName+"&queryTchId="+queryTchId+"&queryTchUserName="+queryTchUserName);
}
</script>
	<div class="tree-menu-search">
    	<@commonmacro.couplingSelect idObjectId="deptidnow" nameObjectId="deptId" url="/common/getDeptSelectDataByParentId.action" divName='请选择部门' onclick="queryTeacher">
		<div class="pub-search fn-left ml-30" style="width:380px";>
		    <input type="text" class="input-txt" id="queryTchId" name="queryTchId" onkeydown="if(event.keyCode==13)queryTeacher('');" size="12" maxlength="12" onchange="onchangefun(this, '请输入编号');" onfocus="inputFocus(this, '请输入编号');" onblur="inputBlur(this,'请输入编号'); " value="${queryTchId?default("请输入编号")}">
			<input type="text" class="input-txt" id="queryTchName" name="queryTchName" onkeydown="if(event.keyCode==13)queryTeacher('');"  size="12" maxlength="12" onchange="onchangefun(this, '请输入姓名');" onfocus="inputFocus(this, '请输入姓名');" onblur="inputBlur(this,'请输入姓名'); " value="${queryTchName?default("请输入姓名")}">
			<input type="text" class="input-txt" id="queryTchUserName" name="queryTchUserName" onkeydown="if(event.keyCode==13)queryTeacher('');"  size="12" maxlength="12" onchange="onchangefun(this, '请输入账号');" onfocus="inputFocus(this, '请输入账号');" onblur="inputBlur(this,'请输入账号'); " value="${queryTchName?default("请输入账号")}">
		    <a class="btn" href="javascript:queryTeacher('');">查找</a>
		</div>
		</@commonmacro.couplingSelect>
    </div>
 <div name="teacherList" id="teacherList"></div>
<script>
	$(document).ready(function(){
		load("#teacherList","${request.contextPath}/basedata/teacher/teacherAdmin-list.action?modID=${modID?default('')}&deptidnow=00000000000000000000000000000000");
	});
</script>
</@common.moduleDiv>