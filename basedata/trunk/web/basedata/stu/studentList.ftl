<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="在校生维护">
<script>
function edit(id,name){
    $("#queryStudentName").val(name);
	load("#famDiv","${request.contextPath}/basedata/stu/familyAdmin-list.action?studentId="+id);
}
</script>

<p class="pt-10 c-orange">

<#if !students?exists || students?size == 0>
（注:查找学生结果为空，请确认！）
<#else>
（注:当前筛选学生过多，请选择某个学生进行操作）
</#if>
</p>


<form name="form1" id="form1" action="" method="post">
<#if students?exists && (students?size>13)>
<div style="width:1220px; height:500px; overflow:scroll;overflow-x: hidden">
</#if>
<@common.tableList id="tablelist">
	<tr>
	<th>学号</th>
	<th>班级</th>
	<th>姓名</th>
	<th>性别</th>
	<th>出生日期</th>
	<th class="t-center">操作</th>
	</tr>
	<#if !students?exists || students?size == 0>
	<tr>
        <td colspan=6><p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td>
   	</tr>
	<#else>
	<#list students as student>
	<tr>
	<td>${student.stucode!}</td>
	<td>${student.className!}</td>
	<td>${student.stuname!}</td>
	<td>${appsetting.getMcode("DM-XB").get((student.sex?string)!)}</td>
	<td>${(student.birthday?string("yyyy-MM-dd"))?if_exists}</td>
	<td class="t-center"><a href="javascript:void(0);" onclick="edit('${student.id}','${student.stuname!}');"><img src="${request.contextPath}/static/images/icon/edit.png" alt="编辑"></a>
	</tr>
	</#list>
	</#if>
</@common.tableList>
<#if students?exists && (students?size>13)>
</div>
</#if>
</form>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>