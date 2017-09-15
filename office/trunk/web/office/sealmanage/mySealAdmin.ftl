<#import "/common/htmlcomponent.ftl" as common />
<script>

function doQueryChange(){
	var years=$("#years").val();
	var semesters = $("#semesters").val();
	load("#mySealListDiv", "${request.contextPath}/office/sealmanage/sealmanage-mySealList.action?years="+years+"&semesters="+semesters);
}

function doApply(){
	//openDiv("#sealAddLayer", "#sealAddLayer .close,#sealAddLayer .submit,#sealAddLayer .reset", "${request.contextPath}/office/sealmanage/sealmanage-addSeal.action", null, null, "900px");
	load("#sealDiv","${request.contextPath}/office/sealmanage/sealmanage-addSeal.action");
}
$(function(){
	load("#mySealListDiv", "${request.contextPath}/office/sealmanage/sealmanage-mySealList.action?years="+'${year!}'+"&semesters="+'${semester!}');
});

</script>
<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">
    <div class="query-part">
    	<div class="query-tt b ml-10"><span class="fn-left">学年：</span></div>
			 <div class="select_box fn-left">
				<@common.select style="width:100px;float:left;" valName="years" valId="years" notNull="true" myfunchange="doQueryChange">
						<#if yearList?exists && yearList?size gt 0>
	                		<#list yearList as yearl>
	                			<a val="${yearl!}" <#if year?default('') == yearl>class="selected"</#if>>${yearl!}</a>
	                		</#list>
	                	</#if>
					</@common.select>
			</div>
				
		<div class="query-tt b ml-10"><span class="fn-left">学期：</span></div>
			<div class="select_box fn-left">
				<@common.select style="width:80px;" valName="semesters" valId="semesters" myfunchange="doQueryChange">
					<a val=""><span>--请选择--</span></a>
					<a val="1"  <#if semester?default("1")=="1">class="selected"</#if>><span>第一学期</span></a>
					<a val="2"  <#if semester?default("1")=="2">class="selected"</#if>><span>第二学期</span></a>
				</@common.select>
		</div>
	    <a href="javascript:void(0);" onclick="doQueryChange();" class="abtn-blue fn-left ml-20">查找</a>
	     <a href="javascript:void(0);" onclick="doApply('false');" class="abtn-orange-new fn-right applyForBtn" style="width:52px;">用印申请</a>
        <div class="fn-clear"></div>
    </div>
</div>
<div id="mySealListDiv"></div>
<div class="popUp-layer" id="sealAddLayer" style="display:none;width:1000px;"></div>