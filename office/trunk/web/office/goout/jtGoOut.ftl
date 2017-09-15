<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function doSearch(){
	var states = $("#states").val();
	load("#jtGoOutListDiv", "${request.contextPath}/office/goout/goout-jtGoOutList.action?states="+states);
}

function doJTGoOutAdd(){
	load("#goOutDiv", "${request.contextPath}/office/goout/goout-jtGoOutEdit.action");
}


</script>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
	<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
	<div class="query-part">
		<div class="query-tt ml-10">
			<span class="fn-left">审核状态：</span>
		</div>
		<div class="select_box fn-left mr-10">
		<@common.select style="width:150px;" valId="states" valName="states" myfunchange="doSearch" >
			<a val=""><span>全部</span></a>
    		<a val="1"><span>待提交</span></a>
    		<a val="2"><span>审核中</span></a>
    		<a val="3"><span>审核通过</span></a>
    		<a val="4"><span>审核不通过</span></a>
		</@common.select></div>
		<a href="javascript:void(0);" onclick="doJTGoOutAdd();" class="abtn-orange-new fn-right mr-10">集体外出申请</a>
		<div class="fn-clear"></div>
	</div>
	</div>
	</div>
</div>
<div id="jtGoOutListDiv"></div>
<script>
	$(document).ready(function(){
		doSearch();
	});
</script>
</@common.moduleDiv>