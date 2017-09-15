<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="${webAppTitle}--编辑用户">
<#assign EDU=stack.findValue('@net.zdsoft.eis.system.constant.SystemConstant@TEACHER_IMPORT_EDU')>
<#assign SCH=stack.findValue('@net.zdsoft.eis.system.constant.SystemConstant@TEACHER_IMPORT_SCH')>
<script>
	function add() {
		load("#container","${request.contextPath}/basedata/researchgroup/researchGroupAdmin-add.action");
	}
	
	function edit(data) {
		load("#container","${request.contextPath}/basedata/researchgroup/researchGroupAdmin-edit.action?researchGroupId="+data);
	}
	
	function Delete(){
		if(!check()) return;
		if(!confirm("您确认要删除信息吗？")) return;
		tongYong("${request.contextPath}/basedata/researchgroup/researchGroupAdmin-delete.action");
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
			addActionError("请先选择想要进行操作的教研组！");
		}
		return flag;
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
	   					load("#container","${request.contextPath}/basedata/researchgroup/researchGroupAdmin.action");
	   				});
	   			}else{
	   				showMsgError(data.errorMessage);
	   			}
	   		}
 		});
	}
</script>
<p class="pub-operation">
	<a href="javascript:void(0);" onclick="add();" class="abtn-orange-new">新增</a>
</p>

<form id="ec" action="${action}"  method="post" >
<@common.tableList id="tablelist">
<tr>
	<th width="5%" class="t-center">选择</th>
	<th width="12%">教研组名称</th>
	<th width="15%">科目</th>
	<th width="15%">负责人</th>
	<th width="38%">成员</th>
	<th width="15%" class="t-center">操作</th>
</tr>
<#if researchGroupDtoList?exists && (researchGroupDtoList?size>0)>
	<#list researchGroupDtoList as list>
	<tr>
		<td class="t-center"><p><span class="ui-checkbox"><input type="checkbox" name="arrayIds" class="chk" value="${list.id!}"></span></p></td>
		<td title="${list.teachGroupName!}"><p style="width:100px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">${list.teachGroupName!}</p></td> 
		<td title="${list.subjectIds!}"><p style="width:130px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">${list.subjectIds!}</p></td> 
		<td title="${list.principalTeacherName!}"><p style="width:130px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">${list.principalTeacherName!}</p></td> 
		<td title="${list.memberTeacherName!}"><p style="width:400px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">${list.memberTeacherName!}</p></td>
		<td class="t-center"><a href="javascript:edit('${list.id!}');"><img src="${request.contextPath}/static/images/icon/edit.png" alt="修改"></a></td> 
	</tr>
	</#list>
	<#else>
		<tr>
       		<td colspan=7> <p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td>
    	</tr>
	</#if>
</@common.tableList>
<div class="base-operation">
	<#if researchGroupDtoList?exists && (researchGroupDtoList?size>0)>
	<p class="opt">
    	<span class="ui-checkbox ui-checkbox-all" data-all="no"><input type="checkbox" class="chk">全选</span>
        <a href="javascript:void(0);" onclick="Delete();" class="abtn-blue">删除</a>
    </p>
    </#if>
</div>
</form>
<div  id="classLayer" class="popUp-layer" style="display:none;width:720px"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>




