<#import "/common/htmlcomponent.ftl" as common />
<script>
function doApply(readonlyStyle){
	openDiv("#classLayer", "#classLayer .close,#classLayer .submit,#classLayer .reset", "${request.contextPath}/office/dutymanage/dutymanage-dutyInformationEdit.action", null, null, "500px");
}

function doQueryChange(){
	var years =$("#years").val();
	var dutyName=$("#dutyName").val();
	
	load("#wirkReportDiv", "${request.contextPath}/office/dutymanage/dutymanage-dutyInformationList.action?dutyName="+encodeURIComponent(dutyName)+"&years="+years);
}

$(function(){
	load("#wirkReportDiv", "${request.contextPath}/office/dutymanage/dutymanage-dutyInformationList.action?years="+${year!});
});

</script>
<div class="popUp-layer" id="classLayer3" style="display:none;width:460px;"></div>
<div class="popUp-layer" id="classLayer" style="display:none;top:200px;left:300px;width:700px;height:482px;"></div>
<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">
    <div class="query-part">
    	<div class="query-tt b ml-10"><span class="fn-left">年度：</span></div>
			 <div class="select_box fn-left">
				<@common.select style="width:100px;float:left;" valName="years" valId="years" notNull="true" myfunchange="doQueryChange">
						<#if yearList?exists && yearList?size gt 0>
	                		<#list yearList as yearl>
	                			<a val="${yearl!}" <#if year?default('') == yearl>class="selected"</#if>>${yearl!}</a>
	                		</#list>
	                	</#if>
					</@common.select>
			</div>
		
    	<div class="query-tt ml-10"><span class="fn-left">名称：</span></div>
        <input name="dutyName" id="dutyName" value="${dutyName!}" maxLength="30" class="input-txt fn-left" style="width:120px;"/>
	    <a href="javascript:void(0);" onclick="doQueryChange();" class="abtn-blue fn-left ml-20">查询</a>
        <a href="javascript:void(0);" onclick="doApply('false');" class="abtn-orange-new fn-right applyForBtn" style="width:75px;">新增值班计划</a>
        <div class="fn-clear"></div>
    </div>
</div>
<div id="wirkReportDiv"></div>