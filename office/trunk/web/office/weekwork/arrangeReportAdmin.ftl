<#import "/common/htmlcomponent.ftl" as htmlmacro />
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="">
<script type="text/javascript" src="${request.contextPath}/static/js/chinaexcel.js"></script>
<script>

function toSearch(){
    <#if loginInfo.unitClass == 1>
	    var searchYear = $("#searchYear").val();
	    var url = "${request.contextPath}/office/weekwork/weekwork-arrangeReportAdmin.action?searchYear="+searchYear;
    <#else>
	    var acadyear = $("#acadyear").val();
		var semester = $("#semester").val();
		var url = "${request.contextPath}/office/weekwork/weekwork-arrangeReportAdmin.action?acadyear="+acadyear+"&semester="+semester;
    </#if>
    load("#weekworkContainer", url);
}

function changeOutline(){
    var workOutlineId = $('#workOutlineId').val();
    if(workOutlineId != "") {
		var url = "${request.contextPath}/office/weekwork/weekwork-arrangeReportList.action?workOutlineId="+workOutlineId;
    	load('#msgDiv',url);
    }
}

function doExport(){
	var workOutlineId = $('#workOutlineId').val();
	if(workOutlineId != "") {
	    var url = "${request.contextPath}/office/weekwork/weekwork-arrangeReportExport.action?workOutlineId="+workOutlineId;
	    location.href=url;
	}else{
		showMsgWarn("请选择一个工作大纲！");
		return;
	}
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
					<@htmlmacro.select style="width:100px;float:left;" valName="searchYear" valId="searchYear" notNull="true" myfunchange="toSearch">
						<#if yearList?exists && yearList?size gt 0>
	                		<#list yearList as year>
	                			<a val="${year!}" <#if searchYear?default('') == year>class="selected"</#if>>${year!}</a>
	                		</#list>
	                	</#if>
					</@htmlmacro.select>
				<#else>
					<div class="query-tt ml-10"><span class="fn-left">学年：</span></div>
				    <div class="select_box fn-left">
						<@htmlmacro.select style="width:100px;" valId="acadyear" valName="acadyear" txtId="searchAcadyearTxt" myfunchange="toSearch" >
							<#list acadyears as item>
								<a val="${item}" <#if item == acadyear?default('')>class="selected"</#if>>${item!}</a>
							</#list>
						</@htmlmacro.select>
					</div>
					
					<div class="query-tt ml-10"><span class="fn-left">学期：</span></div>
					<div class="select_box fn-left">
						<@htmlmacro.select style="width:100px;" valName="semester" valId="semester" myfunchange="toSearch">
							<a val="1"  <#if semester?default("")=="1">class="selected"</#if>><span>第一学期</span></a>
							<a val="2"  <#if semester?default("")=="2">class="selected"</#if>><span>第二学期</span></a>
						</@htmlmacro.select>
					</div>
				</#if>
				<div class="query-tt ml-10"><span class="fn-left">工作大纲：</span></div>
				<div class="select_box fn-left">
       	            <#if officeWorkArrangeOutlineList?size gt 0>
       	            	<#assign workOutlineId = officeWorkArrangeOutlineList.get(0).id/>
       	            </#if>
       	            <@htmlmacro.select style="width:325px;" valName="workOutlineId" valId="workOutlineId" myfunchange="changeOutline">
						<a val="">请选择工作大纲</a>
						<#list officeWorkArrangeOutlineList as owao>
							<a val="${owao.id!}" <#if owao.id == workOutlineId?default('')>class="selected"</#if>>${owao.name!}</a>
						</#list>
					</@htmlmacro.select>
			    </div>
			    <a href="javascript:void(0);" class="abtn-blue fn-left ml-15" onclick="doExport()">导出</a>
			    <div class="fn-clear"></div>
			</div>
    	</div>
    </div>
</div>
<div id="msgDiv"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    <#if workOutlineId?default('') != ''>
		var workOutlineId = '${workOutlineId!}';
	    var url = "${request.contextPath}/office/weekwork/weekwork-arrangeReportList.action?workOutlineId="+workOutlineId;
	    load("#msgDiv", url);
	<#else>
		load('#msgDiv','${request.contextPath}/common/tipMsg.action?msg=请选择一个工作大纲！');
	</#if>
});
</script>
</@htmlmacro.moduleDiv>