<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
$(function(){
	doSearch();
});

function doSearch(){
	load("#jtGoOutListDiv","${request.contextPath}/office/jtgooutmanage/jtgooutmanage-jtGoOutList.action");
}
function doJTGoOutAdd(){
	load("#jtGoOutListDiv", "${request.contextPath}/office/jtgooutmanage/jtgooutmanage-jtGoOutEdit.action");
}

</script>
<div class="query-builder-no">
	<div class="query-part fn-rel fn-clear promt-div">
		<span id="resourceofficedocTypeList" class="user-sList user-sList-radio">
	    	<span <#if applyStatus?default('')==''> class="current"</#if> key="">全部</span>
	    	<span <#if applyStatus?default('')=='1'> class="current"</#if> key="1">待提交</span>
	    	<span <#if applyStatus?default('')=='2'> class="current"</#if> key="2">审核中</span>
	    	<span <#if applyStatus?default('')=='3'> class="current"</#if> key="3">审核结束-通过</span>
	    	<span <#if applyStatus?default('')=='4'> class="current"</#if> key="4">审核结束-不通过</span>
	    </span>
	    <div class="fn-right ml-10">
	    	<a href="javascript:doJTGoOutAdd()" class="abtn-orange-new fn-right" id="addNew">集体外出申请</a>
	    </div>
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
			load("#jtGoOutListDiv","${request.contextPath}/office/jtgooutmanage/jtgooutmanage-jtGoOutList.action"+str);
		});
	});
</script>
</@common.moduleDiv>