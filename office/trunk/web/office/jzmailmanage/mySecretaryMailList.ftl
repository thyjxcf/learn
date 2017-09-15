<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
function doView(jzmailId){
	load("#mySecretaryListDiv", "${request.contextPath}/office/jzmailmanage/jzmailmanage-myMailView.action?jzmailId="+jzmailId);
}

//删除
function doDelete(jzmailId){
	if(!showConfirm("确认要删除吗?")){
		return;
	}
	$.getJSON("${request.contextPath}/office/jzmailmanage/jzmailmanage-myMailDelete.action", 
		{"jzmailId":jzmailId}, function(data){
		if(!data.operateSuccess){
		   	if(data.errorMessage!=null&&data.errorMessage!=""){
			   	showMsgError(data.errorMessage);
			   	return;
		   	}
		}else{
	   		showMsgSuccess(data.promptMessage,"",function(){
	   			//load("#myWorkReportDiv", "${request.contextPath}/office/workreport/workReport-myWorkReportList.action");
	   			mySearch();
			});
			return;
		}
	}).error(function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);});
}
</script>
<div class="pub-table-inner">

<div class="pub-table-wrap" id="myWorkReportDiv">
<form name="form1" action="" method="post">
	<@htmlmacro.tableList id="tablelist">
	<tr>
		<th width="21%">标题</th>
		<th width="8%">日期</th>
		<th width="63%">内容</th>
		<th class="t-center" width="8%">操作</th>
	</tr>
	<#if officeJzmails?exists && (officeJzmails?size>0)>
		<#list officeJzmails as officeJzmail>
		    <tr>
                <td>${officeJzmail.title!}</td>
                <td>${officeJzmail.createTime?string('yyyy-MM-dd')?if_exists}</td>
				<td><@htmlmacro.cutOff4List str="${officeJzmail.content!}" length=100 /></td>
				<td class="t-center">
					<a href="javascript:doDelete('${officeJzmail.id!}');">删除</a> &nbsp;
					<a href="javascript:doView('${officeJzmail.id!}');">查看</a>
				</td>
			</tr>
		</#list>
	<#else>
	   <tr><td colspan="6"> <p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td></tr>
	</#if>
	</@htmlmacro.tableList>	
	<@htmlmacro.Toolbar container="#mySecretaryListDiv"></@htmlmacro.Toolbar>
</form>
</div>
</div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>