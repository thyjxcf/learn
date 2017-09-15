<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script type="text/javascript" src="${request.contextPath}/static/js/attachmentUpload.js"></script>
<script>
$(function(){
	mySearch();
});

function mySearch(){
	load("#jzMailDiv", "${request.contextPath}/office/jzmailmanage/jzmailmanage-myMailAdmin.action");
}

function msgManage(){
	load("#jzMailDiv", "${request.contextPath}/office/jzmailmanage/jzmailmanage-mailAdmin.action");
}

function msgEdit(){
	load("#jzMailDiv", "${request.contextPath}/office/jzmailmanage/jzmailmanage-mailEdit.action");
}

function doDisplay(){
	if($("#tit").css("display")=="block"){
		$("#tit").hide();
		$("#desId").hide();
		$("#dis").text("打开说明");
	}else{
		$("#tit").show();
		$("#desId").show();
		$("#dis").text("收起说明");
	}
}
</script>
<div class="pub-table-inner" id="teacherSalaryAdminDiv">
<div class="explain-wrap">
	<p class="pt-15 t-center" style="font-size:xx-large" id="tit">${topUnitName}信箱使用说明：</p>
	<div class="des" id="desId">
    	${officeJzmailInfo.content!}
    </div>
	<a href="javascript:void(0);"style="position:absolute;top:9px;right:20px;display:inline-block;padding-right:12px;" onclick="doDisplay();" id="dis">收起说明</a>
</div>
<div class="pub-tab mb-15">
	<ul class="pub-tab-list">
		<li class="current" onclick="mySearch();">我的留言</li>
		<#if mailManager&&topEdu>
		<li onclick="msgManage();">信箱查询</li>
		<li onclick="msgEdit();">${topUnitName}信箱使用编辑</li>
		</#if>
	</ul>
</div>
<div id="jzMailDiv"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>