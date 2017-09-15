<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="权限设置">
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script>
function add(userId){
   	var url="${request.contextPath}/office/repaire/repaire-auditAdd.action?userId="+userId;
   	openDiv("#classLayer3", "",url, false,"","300px");
}

function setTypeSMS(){
	var url="${request.contextPath}/office/repaire/repaire-typeSMSEdit.action";
   	openDiv("#classLayer3", "",url, false,"","300px");
}

function deleteRep(){
	var ci = '';
		var tps = document.getElementsByName("userIds");
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
	if(!showConfirm('确认要删除所选信息吗？')){
		return false;
	}
	
	$.getJSON("${request.contextPath}/office/repaire/repaire-auditDel.action", 
	{"userId":ci}, function(data){
		//如果有错误信息（与action中对应），则给出提示
		if(data && data != ""){
			showMsgError(data);
			return;
		}
		else{
			//没有错误，提示成功，关闭提示窗口后，通过调用回调函数，使页面进行刷新
			showMsgSuccess("删除成功！", "提示", function(){
				var url="${request.contextPath}/office/repaire/repaire-audit.action";
				load("#repaireDiv",url);
			});			
			return;
		}
	}).error(function(){
		showMsgError("删除失败！");
	});
}
</script>
<form>
<p class="pub-operation fn-clear">
	<a href="javascript:void(0);" class="abtn-orange-new fn-right ml-15" onclick="add('');">新增权限</a>
	<a href="javascript:void(0);" class="abtn-blue fn-right ml-15" onclick="setTypeSMS();">类别短息</a>
</p>
<@common.tableList id="listTable" name="listTable" class="public-table table-list table-dragSort">
	<tr>
	    <th class="t-center">选择</th>
	    <th class="t-center">姓名</th>
		<th class="t-center">操作</th>
	</tr>
<#if typelist?exists &&  (typelist?size>0)>
	<#list typelist as x>
	<tr>
	    <td class="t-center">
	    	<p>
	    		<span class="ui-checkbox">
					<input type="checkbox" class="chk" name="userIds" value="${x.userId}">
				</span>
			</p>
	    </td>
	  	<td class="t-center">${x.userName!}</td>
		<td class="t-center">
			<a href="javascript:add('${x.userId!}');">查看权限</a>
		</td>
	</tr>
	</#list>
<#else>
	<tr>
		<td colspan="5"> <p class="no-data mt-20">还没有任何记录哦！</p></td>
	</tr>
</#if>

</@common.tableList>
<@common.ToolbarBlank>
<span class="ui-checkbox ui-checkbox-all" data-all="no"><input type="checkbox" class="chk">全选</span>
<a class="abtn-blue" href="javascript:void(0);" onclick="deleteRep();">删除</a>
</@common.ToolbarBlank>
</form>
</@common.moduleDiv>