<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="学生家庭信息">
<script>
vselect();

function add() {
<#if systemDeploySchVersion?exists && systemDeploySchVersion == 'eis'>
	var url='${request.contextPath}/stusys/sch/student/studentadmin-familyAdmin-add.action?studentId=${family.studentId}';
<#else>
   	var url='${request.contextPath}/basedata/stu/familyAdmin-add.action?studentId=${family.studentId}';
 </#if>
   	load("#famDiv",url);
}

function doEdit(familyid){
<#if systemDeploySchVersion?exists && systemDeploySchVersion == 'eis'>
	var url='${request.contextPath}/stusys/sch/student/studentadmin-familyAdmin-edit.action?id='+familyid;
<#else>
	var url='${request.contextPath}/basedata/stu/familyAdmin-edit.action?id='+familyid;
</#if>
   	load("#famDiv",url);
}
<#if systemDeploySchVersion?exists && systemDeploySchVersion == 'eis'>
function blackStuAdmin(){
	$('.query-builder').show();
	var queryStudentCode = encodeURIComponent(trim(document.getElementById("queryStudentCode").value).replace(/\s+/g, "%").replace(/　+/g, "%"));
	var queryStudentName = encodeURIComponent(trim(document.getElementById("queryStudentName").value).replace(/\s+/g, "%").replace(/　+/g, "%"));
	var clsId = trim(document.getElementById("classId").value);
	var url = "${request.contextPath}/stusys/sch/student/studentadmin-list.action?classid="+clsId+"&queryStudentCode="+queryStudentCode+"&queryStudentName="+queryStudentName;
	load('#stuDiv',url);
}
function familyImport(){
	var url = "${request.contextPath}/stusys/sch/student/studentadmin-familyAdmin-importMain.action?studentId=${family.studentId!}";
	load('#famDiv',url);
}
</#if>

function queryFam(){
<#if systemDeploySchVersion?exists && systemDeploySchVersion == 'eis'>
	load("#famDiv","${request.contextPath}/stusys/sch/student/studentadmin-familyAdmin-list.action?studentId=${family.studentId}");
<#else>
	load("#famDiv","${request.contextPath}/basedata/stu/familyAdmin-list.action?studentId=${family.studentId}");
</#if>
}

function removeFam(id){
		var ci = '';
	if(id && id != ''){
		ci = id;
	} else {
		var tps = document.getElementsByName("checkid");
		for(var k=0;k<tps.length;k++){
			if(tps[k].checked==true){
				if(ci != ''){
					ci+=',';
				}
				ci+= tps[k].value;
			}
		}
		if(ci == ''){
			showMsgWarn("没有选要删除的信息，请先选择！");
			return false;
		}
	}
	if(!showConfirm('确认要删除信息吗？')){
		return;
	}
<#if systemDeploySchVersion?exists && systemDeploySchVersion == 'eis'>
	var url = "${request.contextPath}/stusys/sch/student/studentadmin-familyAdmin-Remove.action";
<#else>
	var url = "${request.contextPath}/basedata/stu/familyAdmin-Remove.action";
</#if>
	$.getJSON(url, 
	{"famId":ci}, function(data){
		var suc = data.operateSuccess;
		//如果有错误信息（与action中对应），则给出提示
		if(!suc){
			showMsgError(data.errorMessage);
			return;
		}else{
			//没有错误，提示成功，关闭提示窗口后，通过调用回调函数，使页面进行刷新
			showMsgSuccess(data.promptMessage, "提示", queryFam);			
			return;
		}
	}).error(function(){
		showMsgError("删除失败！");
	});
}
</script>
<p class="pub-operation">
	<a href="javascript:void(0);" class="abtn-orange-new" onclick="add();">新增</a>
	<#if systemDeploySchVersion?exists && systemDeploySchVersion == 'eis'>
	<a href="javascript:void(0);" class="abtn-blue ml-15" onclick="familyImport();">导入</a>
	<a href="javascript:void(0);" class="abtn-blue ml-15" onclick="blackStuAdmin();">返回</a>
	</#if>
</p>
<form name="form1"  action="" method="post">
<input type="hidden" name="studentId" id="studentId" value='${family.studentId}' >
<@common.tableList id="tablelist">
	<tr>
	<th class="t-center">选择</th>
	<th>关系</th>
	<th>姓名</th>
	<th>工作或学习单位</th>
	<th>家庭电话</th>
	<th>监护人</th>
	<th class="t-center">操作</th>
	</tr>
	<#if !familyMemberList?exists || familyMemberList?size == 0>
	<tr>
        <td colspan=7><p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td>
   	</tr>
	<#else>
	<#list familyMemberList as familyMember>
	<tr style="cursor:pointer;">
	<td class="t-center"><p><span class="ui-checkbox"><input type="checkbox" class="chk" name="checkid" value="${familyMember.id}"></span></p></td>
	<td>
	<#if systemDeploySchVersion?exists && systemDeploySchVersion == 'eis'>
		${appsetting.getMcode("DM-GX").get(familyMember.relation?default(''))}
	<#else>
		${appsetting.getMcode("DM-CGX").get(familyMember.relation?default(''))}
	</#if>
	</td>
	<td>${familyMember.name!}</td>
	<td>${familyMember.company!}</td>
	<td>${familyMember.linkPhone!}</td>
	<td>
		<#if familyMember.guardian>
	  		是
	  	<#else>
	  		否
		</#if>
	</td>
	<td class="t-center"><a href="javascript:void(0);" onclick="doEdit('${familyMember.id!}')"><img src="${request.contextPath}/static/images/icon/edit.png" alt="编辑"></a>
	<a href="javascript:void(0);" onclick="removeFam('${familyMember.id!}')" class="ml-15"><img src="${request.contextPath}/static/images/icon/del2.png" alt="删除"></a></td>
	</tr>
	</#list>
	</#if>
</@common.tableList>
<@common.ToolbarBlank>
	<span class="ui-checkbox ui-checkbox-all" data-all="no"><input type="checkbox" class="chk">全选</span>
    <a class="abtn-blue" href="javascript:void(0);" onclick="removeFam();">删除</a>
</@common.ToolbarBlank>
</form>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>