<#import "/common/htmlcomponent.ftl" as common />
<#import "/common/commonmacro.ftl" as commonmacro>
<@common.moduleDiv titleName="">
<script>

function toSearch(){
    <#if loginInfo.unitClass == 1>
	    var searchYear = $("#searchYear").val();
	    var url = "${request.contextPath}/office/weekwork/weekwork-arrangeQuery.action?searchYear="+searchYear;
    <#else>
	    var acadyear = $("#acadyear").val();
		var semester = $("#semester").val();
		var url = "${request.contextPath}/office/weekwork/weekwork-arrangeQuery.action?acadyear="+acadyear+"&semester="+semester;
    </#if>
    load("#weekworkContainer", url);
}

function doQuery() {
   var workOutlineId = $('#workOutlineId').val();
   if(workOutlineId!=''){
	   var deptId = $("#deptId").val();
	   var url = "${request.contextPath}/office/weekwork/weekwork-arrangeQueryList.action?deptId="+deptId+"&workOutlineId="+workOutlineId;
	   load("#arrangeQueryListDiv", url);
   }
  
}
</script>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
		<div class="query-builder-nobg mt-15" style="padding:0 0 10px 0;">  
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
				<div class="query-tt ml-10"><span class="fn-left">工作大纲：</span></div>
				<div class="select_box fn-left">
       	            <#if !arrangeQuery?default(false)&&officeWorkArrangeOutlineList?size gt 0>
       	            	<#assign workOutlineId = officeWorkArrangeOutlineList.get(0).id/>
       	            </#if>
       	            <@common.select style="width:325px;" valName="workOutlineId" valId="workOutlineId" myfunchange="doQuery">
						<a val="">请选择工作大纲</a>
						<#list officeWorkArrangeOutlineList as owao>
							<a val="${owao.id!}" <#if owao.id == workOutlineId?default('')>class="selected"</#if>>${owao.name!}</a>
						</#list>
					</@common.select>
			    </div>
			    <div class="query-tt ml-10">
    				<span class="fn-left">部门：</span>
    			</div>
				<@common.select style="width:120px;float:left;" valName="deptId" valId="deptId" myfunchange="doQuery">
					<a val="" >请选择</a>
					<#if deptList?exists && deptList?size gt 0>
                		<#list deptList as dept>
                			<a val="${dept.id!}" <#if deptId?default('') == dept.id>class="selected"</#if>>${dept.deptname!}</a>
                		</#list>
                	</#if>
				</@common.select>
				 <div class="fn-clear"></div>
			</div>
    	</div>
    </div>
</div>
<div id="arrangeQueryListDiv"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	<#if workOutlineId?default('') != ''>
		var workOutlineId = '${workOutlineId!}';
	    var url = "${request.contextPath}/office/weekwork/weekwork-arrangeQueryList.action?workOutlineId="+workOutlineId;
	    load("#arrangeQueryListDiv", url);
	<#else>
    load('#arrangeQueryListDiv','${request.contextPath}/common/tipMsg.action?msg=请选择一个工作大纲！');
    </#if>
});
</script>
</@common.moduleDiv>