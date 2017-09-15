<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>

function toEdit(workOutlineId) {
    var searchYear = $("#searchYear").val();
    var url = "${request.contextPath}/office/weekwork/weekwork-arrangeEdit.action?workOutlineId="+workOutlineId+"&searchYear="+searchYear;
    load("#weekworkContainer", url);
}

function toView(workOutlineId) {
    var searchYear = $("#searchYear").val();
    var url = "${request.contextPath}/office/weekwork/weekwork-arrangeView.action?workOutlineId="+workOutlineId+"&searchYear="+searchYear;
    load("#weekworkContainer", url);
}

function toSearch(){
	<#if loginInfo.unitClass == 1>
	    var searchYear = $("#searchYear").val();
	    var url = "${request.contextPath}/office/weekwork/weekwork-arrangeList.action?searchYear="+searchYear;
    <#else>
	    var acadyear = $("#acadyear").val();
		var semester = $("#semester").val();
		var url = "${request.contextPath}/office/weekwork/weekwork-arrangeList.action?acadyear="+acadyear+"&semester="+semester;
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
							<a val="1"  <#if semester?default("")=="1">class="selected"</#if>><span>第一学期</span></a>
							<a val="2"  <#if semester?default("")=="2">class="selected"</#if>><span>第二学期</span></a>
						</@common.select>
					</div>
				</#if>
			</div>
			<div class="fn-clear"></div>
    	</div>
    </div>
</div>
<@common.tableList id="tablelist">
	<tr>
		<th width="33%">工作大纲名称</th>
		<th width="9%">开始日期</th>
		<th width="9%">结束日期</th>
		<th width="30%">工作重点</th>
		<th width="9%">状态</th>
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
				<#if outline.detailState?default('1') == '1'>
					未提交
				<#elseif outline.detailState?default('1') == '2'>
					已提交
				<#elseif outline.detailState?default('1') == '3'>
					已发布
				</#if>
			</td>
			<td>
				<#if outline.detailState?default('1') == '1' || outline.detailState?default('1') == '2'>
				    <a href="javascript:void(0);" onclick="toEdit('${outline.id!}');"><img src="${request.contextPath}/static/images/icon/edit.png" alt="编辑"></a>
			    <#else>
				    <a href="javascript:void(0);" onclick="toView('${outline.id!}');"><img src="${request.contextPath}/static/images/icon/view.png" alt="查看"></a>
			    </#if>
			</td>
		</tr>
	</#list>
<#else>
	<tr>
        <td colspan="6"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
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