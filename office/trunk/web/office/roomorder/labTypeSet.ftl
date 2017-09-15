<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function myOrder(){
	var url="${request.contextPath}/office/roomorder/roomorder.action";
	load("#container", url);
}

function orderApply(){
	var url="${request.contextPath}/office/roomorder/roomorder-orderApply.action";
	load("#container", url);
}

function orderAudit(){
	var url="${request.contextPath}/office/roomorder/roomorder-orderAudit.action";
	load("#container", url);
}

function timeSet(){
	var url="${request.contextPath}/office/roomorder/roomorder-timeSet.action";
	load("#container", url);
}

function orderSet(){
	var url="${request.contextPath}/office/roomorder/roomorder-orderSet.action";
	load("#container", url);
}

function labTypeSet(){
	var url="${request.contextPath}/office/roomorder/roomorder-labTypeSet.action";
	load("#container", url);
}

function searchLabTypeSet(){
	var searchSubject = $("#searchSubject").val();
	load("#labTypeSetListDiv", "${request.contextPath}/office/roomorder/roomorder-labTypeSet-list.action?searchSubject="+searchSubject);
}

function doLabTypeSetAdd(){
	openDiv("#labTypeSetLayer", "#labTypeSetLayer .close,#labTypeSetLayer .submit,#labTypeSetLayer .reset", "${request.contextPath}/office/roomorder/roomorder-labTypeSet-add.action", null, null, "900px");
}

function doLabTypeSetEdit(id){
	openDiv("#labTypeSetLayer", "#labTypeSetLayer .close,#labTypeSetLayer .submit,#labTypeSetLayer .reset", "${request.contextPath}/office/roomorder/roomorder-labTypeSet-edit.action?officeLabSet.id="+id, null, null, "900px");
}

function labApplyCount(){
	var url="${request.contextPath}/office/roomorder/roomorder-labApplyCount.action";
	load("#container", url);
}
      
</script>
<div class="popUp-layer" id="labTypeSetLayer" style="display:none;width:700px;"></div>
<div class="pub-tab">
	<ul class="pub-tab-list">
	<li onclick="myOrder();">我的预约</li>
	<li onclick="orderApply();">预约申请</li>
	<#if auditAdmin>
	<li onclick="orderAudit();">预约审核</li>
	<li onclick="timeSet();">时段设置</li>
	<li onclick="orderSet();">类型信息设置</li>
	<#if !edu>
	<li class="current" onclick="labTypeSet();">实验种类设置</li>
	</#if>
	</#if>
	<#if !edu>
	<li onclick="labApplyCount();">实验申请统计</li>
	</#if>
	</ul>
</div>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
	<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
	<div class="query-part">
		<div class="query-tt ml-10">
			<span class="fn-left">学科：</span>
		</div>
		<div class="select_box fn-left mr-10">
		<@common.select style="width:120px;" valId="searchSubject" valName="searchSubject" myfunchange="searchLabTypeSet">
			<a val=""><span>----全部----</span></a>
        	<#list appsetting.getMcode('DM-SYSLX').getMcodeDetailList() as item>
        		<a val="${item.thisId}" <#if searchSubject?default('') == item.thisId>class="selected"</#if>><span>${item.content}</span></a>
        	</#list>
		</@common.select>
		</div>
		<a href="javascript:void(0);" onclick="doLabTypeSetAdd();" class="abtn-orange-new fn-right mr-10">新增实验种类</a>
		<div class="fn-clear"></div>
	</div>
	</div>
	</div>
</div>
<div id="labTypeSetListDiv"></div>
<script>
$(document).ready(function(){
	searchLabTypeSet();
});
</script>
</@common.moduleDiv>