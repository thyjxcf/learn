<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#include "/common/handlefielderror.ftl">
<@htmlmacro.moduleDiv titleName="学生信息列表">
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>

<div id="container1">    
    <form name="form1" id="form1" action="" method="post">
	<div class="query-builder mt-10" style="height:30px;">
        <div class="query-part">
            <div class="query-tt">姓名：</div>
            <input type="text" name="searchName" id="searchName" value="${searchName?default('')}" class="input-txt fn-left">
            <a href="javascript:void(0);" class="abtn-blue fn-left ml-10" onclick="searchStudent();">查询</a>
            <#if unitDto.unitclass==2>
            <div class="fn-right">
                <a href="javascript:void(0);" class="abtn-blue fn-left ml-10" onclick="editStudent('','${unitId!}');">新增</a>
                <a href="javascript:void(0);" class="abtn-blue fn-left ml-10" onclick="exportStudent();">导出</a>
            </div>
            </#if>
            <div class="fn-clear"></div>
        </div>
    </div>
    
	<input type="hidden" name="unitId" id="unitId" value="${unitId?default('')}"/>    
	<@htmlmacro.tableList id="tablelist" class="public-table table-list mt-15">
	<tr>
	  <#-- 注释掉 学校端 教育局 端 都可以删除 
	    <#if isEdu>
	    -->
		<th width="10%" class="t-center" >选择</th>
		<th width="20%">班级</th>
		<th width="20%">姓名</th>
		<th width="15%">性别</th>
		<th >账号</th>
		<th width="15%" class="t-center">操作</th>
	</tr>
	<#if listOfStudentDto?exists&&(listOfStudentDto?size>0)>
	<#list listOfStudentDto as item>
	<tr>

 		<td class="t-center">
        	<p><span class="ui-checkbox"><input type="checkbox" name="checkids" value="${item.id?default('')}" class="chk"></span></p>
		</td>
		<td >
			${item.className?default("")}
		</td>
		<td >
			${item.stuname?default("")}
		</td>
		<td >
			${appsetting.getMcode("DM-XB").get((item.sex?string)!)}
		</td>
		<td >
			${item.userName?default("")}
		</td>
		<td class="t-center">
		<a href="javascript:void(0);" onclick="editStudent('${item.id!}','${unitId!}');"><img src="${request.contextPath}/static/images/icon/edit.png" alt="编辑"></a></td>
	</tr>
	</#list>	
	<#else>
	  <tr><td colspan="88"> <p class="no-data mt-50 mb-50"><#if unitDto.unitclass==1>请选择一所学校！<#else>还没有任何记录哦！</#if></p></td></tr>
	</#if>
	
	</@htmlmacro.tableList>
	<#if listOfStudentDto?exists && listOfStudentDto?size gt 0>
	<@htmlmacro.Toolbar container="#container1">

		<p class="opt">
			<span class="ui-checkbox ui-checkbox-all" data-all="no"><input type="checkbox" class="chk">全选</span>
	        <a class="abtn-blue" href="javascript:doDelete();">删除</a>
	    </p>
	</@htmlmacro.Toolbar>
	</#if> 
  </form>
</div>
<div class="popUp-layer" id="addDiv" style="display:none;width:400px;"></div>
<script type="text/javascript">
$('#searchName').keydown(function(event){
    if(event.keyCode==13){
    	searchStudent();
    }
});

function editStudent(studentId, unitId){
 	var searchName=$('#searchName').val();
    searchName=encodeURI(encodeURI(searchName));
	openDiv("#addDiv", "#addDiv .close,#addDiv .reset", "${request.contextPath}/basedata/stu/studentadmin-edit.action?studentId="+studentId+"&unitId="+unitId+"&pageIndex=${pageIndex!}"+"&searchName="+searchName, null, null, 400, vselect);
}

function searchStudent(){
	var searchName=$.trim($("#searchName").val());
	if(searchName==''){
		return ;
	}
	if(searchName.indexOf('\'')>-1||searchName.indexOf('%')>-1){
		showMsgError("请确认欲查询的学生姓名不包含单引号、百分号等特殊符号！");
		return ;
	}	
    var str = "searchName="+encodeURI(searchName);
    load("#container1","${request.contextPath}/basedata/stu/studentadmin-list.action?"+str);
}

function doDelete() {;
	var checkids =[];
	var i = 0;
	$("input[name='checkids']:checked").each(function(){
           checkids[i] = $(this).val();
           i++;
	});
	if (checkids.length == 0) {
		showMsgError("没有选要删除的学生，请先选择！");
		return;
	}
	var unitId = $('#unitId').val();
	var message = "删除学生，将同时删除其账号，请确认！";
	if(showConfirm(message)){
		$.ajax({
			type: "POST",
			url: "${request.contextPath}/basedata/stu/studentadmin-studentDelete.action",
			data: $.param({checkids:checkids},true),
			dataType: "json",
			success: function(data){
				if(data.operateSuccess){
					showMsgSuccess(data.promptMessage,"提示",function(){
						load("#container1","${request.contextPath}/basedata/stu/studentadmin-list.action?unitId="+unitId);
						<#if isEdu>
						load("#ztreeDiv","${request.contextPath}/common/xtree/unitztree.action?useCheckbox=false"+"&unitId=${loginInfo.getUnitID()?default('')}");
						</#if>
					});
				}else{
					showMsgError(data.errorMessage);
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
		});
	}
}
function exportStudent(){
    var searchName=$.trim($("#searchName").val());

	if(searchName.indexOf('\'')>-1||searchName.indexOf('%')>-1){
		showMsgError("请确认欲查询的学生姓名不包含单引号、百分号等特殊符号！");
		return ;
	}	
    var str = "searchName="+encodeURI(searchName);
	location.href = "${request.contextPath}/basedata/stu/studentadmin-studentExport.action?" + str;
}
</script>
</@htmlmacro.moduleDiv>