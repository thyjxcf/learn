<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function toAdd(){
    var url = "${request.contextPath}/office/weekwork/weekwork-outlineEdit.action";
    load("#weekworkContainer", url);
}

function toEdit(workOutlineId) {
    var url = "${request.contextPath}/office/weekwork/weekwork-outlineEdit.action?workOutlineId="+workOutlineId;
    load("#weekworkContainer", url);
}

function toView(workOutlineId) {
    var url = "${request.contextPath}/office/weekwork/weekwork-outlineView.action?workOutlineId="+workOutlineId;
    load("#weekworkContainer", url);
}

function toDelete(workOutlineId) {
    var searchYear = $("#searchYear").val();
	if(confirm("你确定要删除吗？")) {
		$.getJSON("${request.contextPath}/office/weekwork/weekwork-outlineDelete.action", {"workOutlineId":workOutlineId}, function(data){
			if(!data.operateSuccess){
			   if(data.errorMessage!=null&&data.errorMessage!=""){
				   showMsgError(data.errorMessage);
				   return;
			   }
			}else{
			    var msg="删除成功!";
				showMsgSuccess(msg,"",function(){
				  load("#container","${request.contextPath}/office/weekwork/weekwork.action?searchYear="+searchYear);
				});
				return;
			}
		});
	}
}

function toSearch(){
	<#if loginInfo.unitClass == 1>
	    var searchYear = $("#searchYear").val();
	    var url = "${request.contextPath}/office/weekwork/weekwork-outlineList.action?searchYear="+searchYear;
    <#else>
	    var acadyear = $("#acadyear").val();
		var semester = $("#semester").val();
		var url = "${request.contextPath}/office/weekwork/weekwork-outlineList.action?acadyear="+acadyear+"&semester="+semester;
    </#if>
    load("#weekworkContainer", url);
}

</script>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
		<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
    		<div class="query-part">
    			<#if loginInfo.unitClass == 1>
	    			<div class="query-tt ml-10">
	    				<span class="fn-left">年份：</span>
	    			</div>
					<@common.select style="width:100px;float:left;" valName="searchYear" valId="searchYear" notNull="true" myfunchange="toSearch">
						<#if yearList?exists && yearList?size gt 0>
	                		<#list yearList as year>
	                			<a val="${year!}" <#if searchYear?default('') == year>class="selected"</#if>>${year!}</a>
	                		</#list>
	                	</#if>
					</@common.select>
				<#else>
					<div class="query-tt ml-10"><span class="fn-left">学年：</span></div>
				    <div class="select_box fn-left">
						<@common.select style="width:100px;" valId="acadyear" valName="acadyear" txtId="searchAcadyearTxt" myfunchange="toSearch" >
							<#list acadyears as item>
								<a val="${item}" <#if item == acadyear?default('')>class="selected"</#if>>${item!}</a>
							</#list>
						</@common.select>
					</div>
					
					<div class="query-tt ml-10"><span class="fn-left">学期：</span></div>
					<div class="select_box fn-left">
						<@common.select style="width:100px;" valName="semester" valId="semester" myfunchange="toSearch">
							<a val="1"  <#if semester?default("1")=="1">class="selected"</#if>><span>第一学期</span></a>
							<a val="2"  <#if semester?default("1")=="2">class="selected"</#if>><span>第二学期</span></a>
						</@common.select>
					</div>
				</#if>
			</div>
			<#if canEdit>
	            <a href="javascript:void(0);" class="abtn-orange-new fn-right" onclick="toAdd()">新建大纲</a>
        	</#if>
        	<div class="fn-clear"></div>
    	</div>
    </div>
</div>
<@common.tableList id="tablelist">
	<tr>
		<th width="33%">工作大纲名称</th>
		<th width="14%">开始日期</th>
		<th width="14%">结束日期</th>
		<th width="29%">工作重点</th>
		<th width="10%">操作</th>
	</tr>
	<#if officeWorkArrangeOutlineList?exists && officeWorkArrangeOutlineList?size gt 0>
	<#list officeWorkArrangeOutlineList as outline>
		<tr>
			<td title="${outline.name!}">
				<@common.cutOff str="${outline.name!}" length=40 />
			</td>
			<td>
				${(outline.startTime?string('yyyy-MM-dd'))?if_exists}
			</td>
			<td>
				${(outline.endTime?string('yyyy-MM-dd'))?if_exists}
			</td>
			<td title="${outline.workContent!}">
				<@common.cutOff str="${outline.workContent!}" length=40 />
			</td>
			<td>
				<#--<#if canEdit && outline.state == '2' && !outline.use>-->
				<#if canEdit && outline.state == '2'>
					<a href="javascript:void(0);" onclick="toEdit('${outline.id!}');"><img src="${request.contextPath}/static/images/icon/edit.png" title="编辑"></a>
					&nbsp;&nbsp;
					<a href="javascript:void(0);" onclick="toDelete('${outline.id!}');"><img src="${request.contextPath}/static/images/icon/del2.png" title="删除"></a>
				<#else>
					<a href="javascript:void(0);" onclick="toView('${outline.id!}');"><img src="${request.contextPath}/static/images/icon/view.png" title="查看"></a>
				</#if>
			</td>
		</tr>
	</#list>
<#else>
	<tr>
        <td colspan="5"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
   	</tr>
</#if>
</@common.tableList>

<@common.Toolbar container="#weekworkContainer"/>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script type="text/javascript">
$(function(){
});
</script>
</@common.moduleDiv>