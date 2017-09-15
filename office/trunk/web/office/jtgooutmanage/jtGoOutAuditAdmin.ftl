<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
$(function(){
	doSearch();
});

function doSearch(){
	load("#jtGoOutListDiv","${request.contextPath}/office/jtgooutmanage/jtgooutmanage-jtGoOutAuditList.action");
}
</script>
<div class="query-builder-no">
	<div class="query-part fn-rel fn-clear promt-div">
		<span id="resourceofficedocTypeList" class="user-sList user-sList-radio">
	    	<span <#if applyStatus?default('')==''> class="current"</#if> key="">待我审核</span>
	    	<span <#if applyStatus?default('')=='1'> class="current"</#if> key="1">我已审核</span>
	    	<span <#if applyStatus?default('')=='2'> class="current"</#if> key="2">审核结束</span>
	    	<span <#if applyStatus?default('')=='3'> class="current"</#if> key="3">已作废</span>
	    </span>
    </div>
</div>
<div id="jtGoOutListDiv"></div>
<script>
	$(document).ready(function(){
		vselect();
		$('.user-sList-radio span').click(function(){
			$(this).addClass('current').siblings('span').removeClass('current');
			var status=$('#resourceofficedocTypeList.user-sList-radio span.current').attr("key");
			var str = "?applyStatus="+status;
			load("#jtGoOutListDiv","${request.contextPath}/office/jtgooutmanage/jtgooutmanage-jtGoOutAuditList.action"+str);
		});
	});
</script>
</@common.moduleDiv>