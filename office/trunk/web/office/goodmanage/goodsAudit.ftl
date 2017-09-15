<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function doSearch(){
	var applyType = $("#applyType").val();
	var searchType = $("#searchType").val();
	var searchName = $("#searchName").val();
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	if("" != startTime && ""!=	endTime){
		if(compareDate(startTime, endTime) > 0){
			showMsgWarn("结束时间不能小于开始时间！");
			return;
		}
	}
	load("#goodsAuditListDiv", "${request.contextPath}/office/goodmanage/goodmanage-goodsAudit-list.action?applyType="+applyType+"&searchType="+searchType+"&searchName="+searchName+"&startTime="+startTime+"&endTime="+endTime);
}

function doNotPass(goodsReqId){
	openDiv("#goodsAuditLayer", "#goodsAuditLayer .close,#goodsAuditLayer .submit,#goodsAuditLayer .reset", "${request.contextPath}/office/goodmanage/goodmanage-goodsAudit-edit.action?officeGoodsReq.id="+goodsReqId, null, null, "900px");
}

var isSubmit=false;
function doPass(goodsReqId){
	if(isSubmit){
		return;
	}
	if(!confirm("您确定要通过该申请？")){
		return;
	}
	isSubmit=true;
	var options = {
       url:'${request.contextPath}/office/goodmanage/goodmanage-goodsAudit-save.action?officeGoodsReq.id='+goodsReqId+'&officeGoodsReq.state=1', 
       success : showAuditReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post'
    };
	
    $('#auditform').ajaxSubmit(options);
}

function showAuditReply(data){
	isSubmit=false;
	if(!data.operateSuccess){
			   if(data.errorMessage!=null&&data.errorMessage!=""){
				   showMsgError(data.errorMessage);
				   return;
			   }else{
			   	   showMsgError(data.promptMessage);
				   return;
			   }
	}else{
		   		showMsgSuccess("审核成功！","",function(){
		   			doSearch();
				});
	}
}

function doExport(){
	var searchType = $("#searchType").val();
	var searchName = $("#searchName").val();
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	if("" != startTime && ""!=	endTime){
		if(compareDate(startTime, endTime) > 0){
			showMsgWarn("结束时间不能小于开始时间！");
			return;
		}
	}
	location.href="${request.contextPath}/office/goodmanage/goodmanage-goodsAudit-export.action?searchType="+searchType+"&searchName="+searchName+"&startTime="+startTime+"&endTime="+endTime;
}
</script>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
	<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
	<div class="query-part">
		<div class="query-tt ml-10">
			<span class="fn-left">审核状态：</span>
		</div>
		<div class="select_box fn-left">
		<@common.select style="width:150px;" valId="applyType" valName="applyType" myfunchange="doSearch" >
			<a val=""><span>全部</span></a>
			<a val="0" <#if applyType?default('')=="0">class="selected"</#if>><span>待审核</span></a>
			<a val="1" <#if applyType?default('')=="1">class="selected"</#if>><span>已通过</span></a>
			<a val="2" <#if applyType?default('')=="2">class="selected"</#if>><span>未通过</span></a>
		</@common.select></div>
		<div class="query-tt ml-15">
			<span class="fn-left">物品类别：</span>
		</div>
		<div class="select_box fn-left">
		<@common.select style="width:150px;" valId="searchType" valName="searchType" myfunchange="doSearch" >
			<a val=""><span>全部</span></a>
        	<#if goodsTypeList?exists && (goodsTypeList?size>0)>
            	<#list goodsTypeList as type>
            		<a val="${type.typeId}" <#if searchType?default('') == type.typeId>class="selected"</#if>><span>${type.typeName}</span></a>
            	</#list>
        	</#if>
		</@common.select></div>
    	<div class="query-tt ml-15"><span class="fn-left">物品名称：</span></div>
        <input name="searchName" id="searchName" value="${searchName!}" maxLength="50" class="input-txt fn-left" style="width:170px;"/>
		<div class="query-tt ml-15">
			<span class="fn-left">申请日期：</span>
		</div>
		<div class="fn-left">
		<@common.datepicker class="input-txt" style="width:100px;" id="startTime" value="${((startTime)?string('yyyy-MM-dd'))?if_exists}"/>
		&nbsp;-&nbsp;
		<@common.datepicker class="input-txt" style="width:100px;" id="endTime" value="${((endTime)?string('yyyy-MM-dd'))?if_exists}"/>
		</div>
		<a href="javascript:void(0)" onclick="doSearch();" class="abtn-blue fn-left ml-20">查找</a>
		<a href="javascript:void(0)" onclick="doExport();" class="abtn-blue fn-left ml-20">导出</a>
		<div class="fn-clear"></div>
	</div>
	</div>
	</div>
</div>
<div id="goodsAuditListDiv"></div>
<div class="popUp-layer" id="goodsAuditLayer" style="display:none;width:500px;"></div>
<script>
	$(document).ready(function(){
		doSearch();
	});
</script>
</@common.moduleDiv>