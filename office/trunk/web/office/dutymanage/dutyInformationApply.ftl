<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>

function searchOrder(){
	var dutyId=$("#dutyId").val();
	if(dutyId!=''){
		var url="${request.contextPath}/office/dutymanage/dutymanage-dutyInformationApplyList.action";
		url += "?dutyId="+dutyId;
		load("#orderApplyListDiv", url);
	}else{
		load('#orderApplyListDiv','${request.contextPath}/common/tipMsg.action?msg=请选择名称！');
	}
}

</script>
<div class="popUp-layer" id="classLayer3" style="display:none;width:500px;"></div>
<div class="popUp-layer" id="bulletinLayer" style="display:none;top:100px;left:300px;width:700px;height:580px;"></div>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
		<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
    		<div class="query-part">
				<div class="query-tt ml-10">
					<span class="fn-left">名称：</span>
				</div>
				<div class="fn-left">
					<@common.select style="width:170px;" valName="dutyId" valId="dutyId" myfunchange="searchOrder">
						<a val="">请选择</a>
						<#list officeDutyInformationSets as item>
						<a val="${item.id}" <#if dutyId?default('')==item.id?default('')> class="selected"</#if>>${item.dutyName!}</a>
						</#list>
					</@common.select>
				</div>
			</div>
    	</div>
    </div>
</div>
<div id="container">
	<div class="fn-clear">
		
        
    </div>
	<div id="orderApplyListDiv"></div>
</div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script>

$(document).ready(function(){
<#if dutyId?exists>
	$("#dutyId").val('${dutyId!}');
	searchOrder();
<#else>
	load('#orderApplyListDiv','${request.contextPath}/common/tipMsg.action?msg=请选择名称！');
</#if>
});
</script>
</@common.moduleDiv>