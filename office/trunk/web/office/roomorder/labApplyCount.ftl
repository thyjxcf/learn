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

function labApplyCount(){
	var url="${request.contextPath}/office/roomorder/roomorder-labApplyCount.action";
	load("#container", url);
}

function searchLabApplyCount(){
	var searchLabMode = $("#searchLabMode").val();
	var searchName = encodeURI($("#searchName").val());
	var searchSubject = $("#searchSubject").val();
	var searchGrade = $("#searchGrade").val();
	var searchUserName = encodeURI($("#searchUserName").val());
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	if("" != startTime && ""!=	endTime){
		if(compareDate(startTime, endTime) > 0){
			showMsgWarn("结束时间不能小于开始时间！");
			return;
		}
	}
	load("#labApplyCountListDiv", "${request.contextPath}/office/roomorder/roomorder-labApplyCount-list.action?searchLabMode="+searchLabMode+"&searchName="+searchName+"&searchSubject="+searchSubject+"&searchGrade="+searchGrade+"&searchUserName="+searchUserName+"&startTime="+startTime+"&endTime="+endTime);
}

function doExport(){
	var searchLabMode = $("#searchLabMode").val();
	var searchName = encodeURI($("#searchName").val());
	var searchSubject = $("#searchSubject").val();
	var searchGrade = $("#searchGrade").val();
	var searchUserName = encodeURI($("#searchUserName").val());
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	if("" != startTime && ""!=	endTime){
		if(compareDate(startTime, endTime) > 0){
			showMsgWarn("结束时间不能小于开始时间！");
			return;
		}
	}
	location.href="${request.contextPath}/office/roomorder/roomorder-labApplyCount-export.action?searchLabMode="+searchLabMode+"&searchName="+searchName+"&searchSubject="+searchSubject+"&searchGrade="+searchGrade+"&searchUserName="+searchUserName+"&startTime="+startTime+"&endTime="+endTime;
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
	<li onclick="labTypeSet();">实验种类设置</li>
	</#if>
	<li class="current" onclick="labApplyCount();">实验申请统计</li>
	</ul>
</div>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
	<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
	<div class="query-part">
		<div class="query-tt"><span class="fn-left">实验形式：</span></div>
		<div class="select_box fn-left mr-10">
		<@common.select style="width:100px;" valId="searchLabMode" valName="searchLabMode" myfunchange="searchLabApplyCount">
			<a val=""><span>全部</span></a>
    		<a val="1" <#if searchSubject?default('') == "1">class="selected"</#if>><span>教师演示实验</span></a>
    		<a val="2" <#if searchSubject?default('') == "2">class="selected"</#if>><span>学生操作实验</span></a>
		</@common.select>
		</div>
		
		<div class="query-tt"><span class="fn-left">实验名称：</span></div>
        <input name="searchName" id="searchName" value="${searchName!}" maxLength="100" class="input-txt fn-left" style="width:80px;"/>
		
		<div class="query-tt ml-10"><span class="fn-left">学科：</span></div>
		<div class="select_box fn-left mr-10">
		<@common.select style="width:100px;" valId="searchSubject" valName="searchSubject" myfunchange="searchLabApplyCount">
			<a val=""><span>全部</span></a>
        	<#list appsetting.getMcode('DM-SYSLX').getMcodeDetailList() as item>
        		<a val="${item.thisId}" <#if searchSubject?default('') == item.thisId>class="selected"</#if>><span>${item.content}</span></a>
        	</#list>
		</@common.select>
		</div>
		
		<#if !hasGrade>
		<input type="hidden" id="searchGrade" name="searchGrade" value="" />
		<#else>
		<div class="query-tt"><span class="fn-left">年级：</span></div>
		<div class="select_box fn-left mr-10">
		<@common.select style="width:100px;" valId="searchGrade" valName="searchGrade" myfunchange="searchLabApplyCount">
			<a val=""><span>全部</span></a>
			<#if gradeList?exists && gradeList?size gt 0>
        	<#list gradeList as item>
        		<a val="${item}" <#if searchGrade?default('') == item>class="selected"</#if>><span>${item}</span></a>
        	</#list>
        	</#if>
		</@common.select>
		</div>
		</#if>
		
		<div class="query-tt"><span class="fn-left">申请人：</span></div>
        <input name="searchName" id="searchUserName" value="${searchUserName!}" maxLength="50" class="input-txt fn-left" style="width:80px;"/>
		
		<div class="query-tt ml-10"><span class="fn-left">时间：</span></div>
		<div class="select_box fn-left mr-10">
		<@common.datepicker class="input-txt" style="width:80px;" id="startTime" value="${((startTime)?string('yyyy-MM-dd'))?if_exists}"/>
		&nbsp;-&nbsp;
		<@common.datepicker class="input-txt" style="width:80px;" id="endTime" value="${((endTime)?string('yyyy-MM-dd'))?if_exists}"/>
		</div>
		
		<a href="javascript:void(0)" onclick="searchLabApplyCount();" class="abtn-blue fn-left ml-20">查找</a>
		<a href="javascript:void(0)" onclick="doExport();" class="abtn-blue fn-left ml-20">导出</a>
		<div class="fn-clear"></div>
	</div>
	</div>
	</div>
</div>
<div id="labApplyCountListDiv"></div>
<script>
$(document).ready(function(){
	searchLabApplyCount();
});
</script>
</@common.moduleDiv>