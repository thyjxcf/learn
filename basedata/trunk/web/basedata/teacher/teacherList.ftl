<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="${webAppTitle}--编辑用户">
<#assign EDU=stack.findValue('@net.zdsoft.eis.system.constant.SystemConstant@TEACHER_IMPORT_EDU')>
<#assign SCH=stack.findValue('@net.zdsoft.eis.system.constant.SystemConstant@TEACHER_IMPORT_SCH')>
<script>
var qNameOrId ="&queryTchName=${queryTchName?default('')}&queryTchId=${queryTchId?default('')}";
function colsDisplay(){
	load("#teacherList",
	 "${request.contextPath}/basedata/common/colsdisplay/colsDisplaySet.action?type=teacher&backUrl="+encodeURIComponent("${request.contextPath}/basedata/teacher/teacherAdmin-list.action?modID=${modID?default('')}&deptidnow=${deptidnow?default('')}"));
}
function changeDept(f){
	window.location.href="teacherAdmin-list.action?modID=${modID?default('')}&deptidnow="+f;
}


function teacherOrder(){
	load("#teacherList","${request.contextPath}/basedata/teacher/teacherAdmin-toOrder.action?modID=${modID?default('')}&deptidnow=${deptidnow?default('')}");
}

function dutyAdmin(){
	parent.location.href = "teacherAdmin-dutyList.action";
}

function teacherDelete(){
	if(!check()) return;
	if(!confirm("您确认要删除信息吗？")) return;
	tongYong("${request.contextPath}/basedata/teacher/teacherAdmin-delete.action?modID=${modID?default('')}&deptidnow=${deptidnow?default('')}");
}
function check(){
	var flag=false;
	var f=document.getElementById('ec');	
	var length = f.elements.length;
	for(i=0;i<length;i++){
		if(f.elements[i].name == "arrayIds" && f.elements[i].checked){
			flag = true;
			break;
		}
	}
	if(!flag){
		addActionError("请先选择想要进行操作的职工！");
	}
	return flag;
}
function doEdit(tchid){
	load("#teacherList","${request.contextPath}/basedata/teacher/teacherAdmin-edit.action?modID=${modID?default('')}&id="+tchid+"&deptidnow=${deptidnow?default('')}"+qNameOrId);
}

function tongYong(url){
  		 jQuery.ajax({
	   		url:url,
	   		type:"POST",
	   		data:jQuery('#ec').serialize(),
	   		dataType:"JSON",
	   		async:false,
	   		success:function(data){
	   			if(data.operateSuccess){
	   					showMsgSuccess(data.promptMessage,"",function(){
	   					load("#teacherList","${request.contextPath}/basedata/teacher/teacherAdmin-list.action?modID=${modID?default('')}&deptidnow=${deptidnow?default('')}");
	   				});
	   			}else{
	   				showMsgError(data.errorMessage);
	   			}
	   		}
 		});
}
function insTeacher(){
	load("#teacherList","${request.contextPath}/basedata/teacher/teacherAdmin-new.action?modID=${modID?default('')}&deptidnow=${deptidnow?default('')}"+qNameOrId);
}

function importTeacher(){
	<#if isSXDeploy>
		load("#teacherList","${request.contextPath}/basedata/teacher/teacherAdmin-sxrrt-importMain.action?objectName=<#if unitClass==1>${EDU}<#else>${SCH}</#if>");
	<#else>
		load("#teacherList","${request.contextPath}/basedata/teacher/teacherAdmin-importMain.action?objectName=<#if unitClass==1>${EDU}<#else>${SCH}</#if>");
	</#if>
}
//导出教师信息
function exportTch(){
	location.href="${request.contextPath}/basedata/teacher/teacherAdmin-export.action?deptidnow=${deptidnow?default('')}";
}
</script>
<p class="pub-operation">
	<a href="javascript:void(0);" onclick="insTeacher();" class="abtn-orange-new">新增</a>
    <a href="javascript:void(0);" onclick="colsDisplay();" class="abtn-blue">详情显示设置</a> 
	<a href="javascript:void(0);" onclick="teacherOrder();" class="abtn-blue">调整排序</a> 
	<a href="javascript:void(0);" onclick="exportTch();" class="abtn-blue">导出</a>
	<a href="javascript:void(0);" onclick="importTeacher();" class="abtn-blue">导入</a>
</p>

<form id="ec" action="${action}"  method="post" >
<@common.tableList id="tablelist">
<tr>
	<th width="5%" class="t-center">选择</th>
	<th width="10%">编号</th>
	<th width="15%">姓名</th>
	<th width="7%">性别</th>
	<th width="20%">手机号</th>
	<th width="20%">账号</th>
	<th width="18%">用户状态</th>
	<th width="15%" class="t-center">操作</th>
</tr>
<#if teacherList?exists &&  (teacherList?size>0)>
<#list teacherList as x>
	<tr>	
		<td class="t-center"><p><span class="ui-checkbox<#if (x.id?default('').equals(adminTeacherId?default('')))> ui-checkbox-disabled</#if>">
		<input type="checkbox" class="chk" value="${x.id?default('')}"
		<#if (x.id?default('').equals(adminTeacherId?default('')))>name="notarrayIds" <#else> name="arrayIds"</#if>>
		</span>
		</p> 				         	
		</td>
		<td >
			${x.tchId?default("")}
		</td>
		<td style='word-WRAP: break-word;'>
			${x.name?default("")}
		</td>
		<td >
			${appsetting.getMcode("DM-XB").get(x.sex?default(-1))}
		</td>									
		<td >
			<font <#if x.hidePhone == 1>color="#ADADAD"</#if>>${x.personTel?default("")}</font>
		</td>
		<#-- 
		<td class="tddata" >
			${x.sequence?default("")}
		</td>
		-->
		<td style='word-WRAP: break-word;'>
			${x.loginName?default("")}
		</td>
		<td >
			<#if x.userState?default(-1) != 1><font color='red'><strong></#if>
	  	  		${appsetting.getMcode("DM-YHZT").get(x.userState?default(-1)?string)}
	  	  	<#if x.userState?default(-1) != 1></font></strong></#if>
		</td>
		<td class="t-center"><a href="javascript:doEdit('${x.id!}');"><img src="${request.contextPath}/static/images/icon/edit.png" alt="显示详情"></a></td>
	</tr>
</#list>
<#else>
	<tr>
		<td colspan="8"> <p class="no-data mt-20">还没有任何记录哦！</p></td>
	</tr>
</#if>
</@common.tableList>
<div class="base-operation">
	<#if teacherList?exists &&  (teacherList?size>0)>
	<p class="opt">
    	<span class="ui-checkbox ui-checkbox-all" data-all="no"><input type="checkbox" class="chk">全选</span>
        <a href="javascript:void(0);" onclick="teacherDelete();" class="abtn-blue">删除</a>
		<span>${information?default('')}</span>
    </p>
    </#if>
</div>
</form>
<script>
vselect();
</script>
<div  id="classLayer" class="popUp-layer" style="display:none;width:720px"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>



