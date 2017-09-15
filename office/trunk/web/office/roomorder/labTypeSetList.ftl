<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<form name="labTypeSetListForm" id="labTypeSetListForm" action="" method="post">
<@common.tableList id="tablelist">
	<tr>
		<th class="t-center" width="5%"><nobr>选择</nobr></th>
		<th width="15%">实验名称</th>
		<th width="10%">教材页面</th>
		<th width="20%">所需仪器</th>
		<th width="20%">所需药品</th>
		<th width="10%">学科</th>
		<#if hasGrade>
		<th width="10%">年级</th>
		</#if>
		<th class="t-center">操作</th>
	</tr>
	<#if officeLabSetList?exists && officeLabSetList?size gt 0>
		<#list officeLabSetList as item>
			<tr style="word-break:break-all; word-wrap:break-word;">
				<td class="t-center"><p><span class="ui-checkbox">
		    		<input type="checkbox" class="chk" name="checkid" value="${item.id?default('')}">
		    		</span></p>
		    	</td>
				<td>${item.name!}</td>
				<td>${item.courseBook!}</td>
				<td title="${item.apparatus!}"><@common.cutOff str='${item.apparatus!}' length=35/></td>
				<td title="${item.reagent!}"><@common.cutOff str='${item.reagent!}' length=35/></td>
				<td>${appsetting.getMcodeName("DM-SYSLX",item.subject!)?default("")}</td>
				<#if hasGrade><td>${item.grade!}</td></#if>
				<td class="t-center">
					<a href="javascript:doLabTypeSetEdit('${item.id!}');"><img alt="编辑" src="${request.contextPath}/static/images/icon/edit.png"></a>
		     	</td>
			</tr>
		</#list>
	<#else>
		<tr>
	        <td <#if hasGrade>colspan="8"<#else>colspan="7"</#if>><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
	   	</tr>
	</#if>
</@common.tableList>
<#if officeLabSetList?exists && officeLabSetList?size gt 0>
<@common.Toolbar container="#labTypeSetListDiv">
	<span class="ui-checkbox ui-checkbox-all" data-all="no"><input type="checkbox" class="chk">全选</input></span>
	<a href="javascript:void(0);" id="btnDelete" class="abtn-blue"  onclick="doDelete()"> 删除</a>
</@common.Toolbar>
</#if>
</form>
<script>
function doDelete(){
	if(isCheckBoxSelect($("[name='checkid']")) == false){
		showMsgWarn("没有选要删除的实验类型，请先选择!");
		return;
	}
	
	if(!showConfirm('您确认要删除实验类型吗？')){
		return;
	}
	$("#btnDelete").attr("class", "abtn-unable");	
	var options = {
       url:'${request.contextPath}/office/roomorder/roomorder-labTypeSet-delete.action', 
       success : showReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post'
    };
	
    $('#labTypeSetListForm').ajaxSubmit(options);
}
function showReply(data){
	$("#btnDelete").attr("class", "abtn-blue");
	if(data!=null && data != ''){
		showMsgError(data);
	}else{
   		showMsgSuccess("删除成功！","",function(){
   			searchLabTypeSet();
		});
	}
}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>