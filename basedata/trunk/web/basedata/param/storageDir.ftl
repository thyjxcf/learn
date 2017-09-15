<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="存储路径设置">
<div class="popUp-layer" id="classLayer" style="display:none;width:400px;">
</div>
<p class="pub-operation">
	<a href="javascript:void(0);" onclick="add();" class="abtn-orange-new">新增</a>
</p>
  <@htmlmacro.tableList id="tablelist">
	<tr>
		<th>类型</th>
		<th>路径</th>
		<th>是否激活</th>
		<th>是否系统内置</th>
    	<th class="t-center">操作</th>
    </tr>
    <#list storageDirs as x>
    <tr id="tr_${x.id}">
    	<td>${x.dirType.description}</td>
    	<td>${x.dir?default("")}</td>
    	<td><#if x.active>是<#else>否</#if></td>
    	<td><#if x.preset>是<#else>否</#if></td>
    	<td class="t-center">
    	<a href="javascript:void(0);" onclick="doEdit('${x.id}');"><img title="修改" src="${request.contextPath}/static/images/icon/edit.png" alt="修改"></a>
    	<#if "${x.id}" != "00000000000000000000000000000000">
    	<a href="javascript:void(0);" onclick="doDeleteDir('${x.id}');" class="ml-15"><img title="删除" src="${request.contextPath}/static/images/icon/del2.png" alt="删除"></a>
    	<#else>
    	<a class="ml-15">&nbsp;&nbsp;</a>
    	</#if>
    	</td>
    </tr>
	</#list>
  </@htmlmacro.tableList>
  
  <script>
function doEdit(id){
	openDiv("#classLayer", "#classLayer .close,#classLayer .submit,#classLayer .reset", "${request.contextPath}/system/admin/platformInfoAdmin-StorageDirDetail.action?storageDir.id="+id, null, null, null, vselect);
}

function doDeleteDir(id){
if(showConfirm('您确认要删除信息吗？')){
	$.getJSON("${request.contextPath}/system/admin/platformInfoAdmin-delteStorageDir.action", {
		"jsonString":id
		}, function(data){
			if(data.jsonError != null && data.jsonError != ""){
				showMsgError(data.jsonError);
			}
			else{
				showMsgSuccess("删除成功！", "提示", 
				function(){
					$("#tr_" + id).remove();
				});
			}
			//成功后，变回样式
			$("#btnSaveAll").attr("class", "abtn-blue-big");
	}).error(function(XMLHttpRequest, textStatus, errorThrown){
    });
    }else{
			return;
		}
}

function add() {
	openDiv("#classLayer", "#classLayer .close,#classLayer .submit,#classLayer .reset", '${request.contextPath}/system/admin/platformInfoAdmin-StorageDirDetail.action', null, null, null, vselect);
}

function doDelete(){
	var args=new Array();
	var j=0;
	var ids = document.getElementsByName("ids");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			args[j]=ids[i].value;
			j=j+1;
			}
	}
	
	if(args.length<1){
		showMsgWarn("请选择您要删除的记录！");
		return;	
	}
		
	if(window.confirm("刪除目录有可能引起找不到对应的文件、附件路径等错误，请慎重。您确认要删除此记录吗？")){
		listForm.action="platformInfoAdmin-delteStorageDir.action";
		listForm.submit();	
	} else {
		return;	
		
	}
}
</script>
</@htmlmacro.moduleDiv>