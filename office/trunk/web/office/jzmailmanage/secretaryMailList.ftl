<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
function doView(jzmailId){
	load("#secretaryListDiv", "${request.contextPath}/office/jzmailmanage/jzmailmanage-mailView.action?jzmailId="+jzmailId);
}

//删除
function doSave(jzmailId){
	$.getJSON("${request.contextPath}/office/jzmailmanage/jzmailmanage-mailSave.action", 
		{"jzmailId":jzmailId}, function(data){
		if(!data.operateSuccess){
		   	if(data.errorMessage!=null&&data.errorMessage!=""){
			   	showMsgError(data.errorMessage);
			   	return;
		   	}
		}else{
	   		showMsgSuccess(data.promptMessage,"",function(){
	   			msgManage();
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
		<th width="23%">标题</th>
		<th width="8%">日期</th>
		<th width="43%">内容</th>
		<th width="10%">举报人</th>
		<th width="5%">电话</th>
		<th class="t-center" width="11%">操作</th>
	</tr>
	<#if officeJzmails?exists && (officeJzmails?size>0)>
		<#list officeJzmails as officeJzmail>
		    <tr>
                <td>${officeJzmail.title!}</td>
                <td>${officeJzmail.createTime?string('yyyy-MM-dd')?if_exists}</td>
				<td><@htmlmacro.cutOff4List str="${officeJzmail.content!}" length=100 /></td>
				<td><#if officeJzmail.anonymous?default(false)==true>匿名<#else>${officeJzmail.createUserName?default("")}</#if></td>
				<td>${officeJzmail.phone?default("")}</td>
				<td class="t-center">
					<#if officeJzmail.state?exists&&officeJzmail.state==1>
					<a href="javascript:doSave('${officeJzmail.id!}');">已回复</a> &nbsp;
					</#if>
					<a href="javascript:doView('${officeJzmail.id!}');">查看</a> &nbsp;
				</td>
			</tr>
		</#list>
	<#else>
	   <tr><td colspan="6"> <p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td></tr>
	</#if>
	</@htmlmacro.tableList>	
	<@htmlmacro.Toolbar container="#secretaryListDiv"></@htmlmacro.Toolbar>
</form>
</div>
</div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>