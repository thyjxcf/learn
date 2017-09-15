<#import "/common/commonmacro.ftl" as htmlmacro>
<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="${webAppTitle}--全局学年学期设置">
<script language="javascript">
function doAdd(){
	load("#container","${request.contextPath}/basedata/edu/semesterAdmin-add.action");
}

function doEdit(semesterid){
 	load("#container","${request.contextPath}/basedata/edu/semesterAdmin-edit.action?id=" + semesterid);
}

function doDelete(){
	if(!checkSelectCheckbox(mainform,'checkid')) return;
	var checkid =[];
	var i = 0;
	$("input[name='checkid']:checked").each(function(){
       checkid[i] = $(this).val();
       i++;
	});
	if(!confirm("确定要删除选中的全局学年学期？")) return;
	$.ajax({
		type: "POST",
		url: "${request.contextPath}/basedata/edu/semesterAdmin-delete.action",
		data: $.param({checkid:checkid},true),
		dataType: "json",
		success: function(data){
			if(data.operateSuccess){
				showMsgSuccess(data.promptMessage,"提示",function(){
					load("#container","${request.contextPath}/basedata/edu/semesterAdmin.action");
				});
			}else{
				showMsgError(data.errorMessage);
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
   });
}

</script>
<div id="container1">   
<form action="" method="post" id="mainform" name="mainform">
<p class="pub-operation">
    <div class="query-tt fn-left">当前学期：<#if currentadadyear?exists>${currentadadyear}<#else><font color="#FF0000">未维护当前学年学期</font></#if></div>
    <div class="query-tt fn-left">
    	<#if gradeUpgrade>
		&nbsp;&nbsp;&nbsp;注：
		<#if currentadadyear?exists>
		系统自动年级升级时间为每年${gradeUpgradeDate?default('')}&nbsp;23:50
		<#else>
		当前年级升级方式为自动，请维护好新学年学期时间，系统会自动升级
		</#if>
		</#if>
	</div>
	<#if unitEduTop>
	<div class="fn-right">
	<a href="javascript:doAdd();" class="abtn-orange-new">新增</a>
	</div>
	</#if>
	<div class="fn-clear"></div>
</p>
<@common.tableList id="tablelist" class="public-table table-list mt-15">
	<tr>
		<#if unitEduTop><!--<th width="5%">选择</th>--></#if>
		<th>学年名称</th>
		<th>学期名称</th>
		<th>工作开始日期</th>
		<th>学期开始日期</th>
		<th>学期结束日期</th>
		<th>工作结束日期</th>
		<th class="t-center">操作</th>
	</tr>
	<#if semesterList?exists && (semesterList?size>0)>
	<#list semesterList as item>
	<tr>
 		<#if unitEduTop><!--
 		<td>
 			<p><span class="ui-checkbox"><input type="checkbox" name="checkid" value="${item.id?default("")}" class="chk"></span></p>
		</td>-->
		</#if>
		<td>
			<#if cntid?exists && item.id == cntid><font color="#FF0000"></#if>${item.acadyear?default("")}
		 	<#if cntid?exists && item.id == cntid></font></#if>
		</td>
		<td>
			 <#if cntid?exists && item.id == cntid><font color="#FF0000"></#if><@htmlmacro.getValue list=semesterMcodeList?if_exists key=item.semester?default("")/>
			 <#if cntid?exists && item.id == cntid></font></#if>
		</td>
		<td>
			<#if cntid?exists && item.id == cntid><font color="#FF0000"></#if><#if item.workBegin?exists>${item.workBegin?string("yyyy-MM-dd")}</#if>
		 	<#if cntid?exists && item.id == cntid></font></#if>
		</td>
		<td>
			<#if cntid?exists && item.id == cntid><font color="#FF0000"></#if><#if item.workBegin?exists>${item.semesterBegin?string("yyyy-MM-dd")}</#if>
		 	<#if cntid?exists && item.id == cntid></font></#if>
		</td>
		<td>
			<#if cntid?exists && item.id == cntid><font color="#FF0000"></#if><#if item.workEnd?exists>${item.semesterEnd?string("yyyy-MM-dd")}</#if>
		 	<#if cntid?exists && item.id == cntid></font></#if>
		</td>
		<td>
			<#if cntid?exists && item.id == cntid><font color="#FF0000"></#if><#if item.workEnd?exists>${item.workEnd?string("yyyy-MM-dd")}</#if>
		 	<#if cntid?exists && item.id == cntid></font></#if>
		</td>
		<td class="t-center"><a href="javascript:doEdit('${item.id!}');"><img src="${request.contextPath}/static/images/icon/edit.png" alt="编辑"></a></td>
	</tr>
	</#list>	
	<#else>
	  	<tr>
           <td colspan=8> <p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td>
    	</tr>
   </#if>			
</@common.tableList>
<#if semesterList?exists && (semesterList?size>0)>		
	<#if unitEduTop>
	<!--
	<@common.ToolbarBlank>
		<span class="ui-checkbox ui-checkbox-all" data-all="no"><input type="checkbox" class="chk">全选</span>
	    <a class="abtn-blue" href="javascript:doDelete();">删除</a>
	</@common.ToolbarBlank>
	-->
	</#if>
</#if>
</form>
</div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>