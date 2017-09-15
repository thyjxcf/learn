<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<#import "../expense/archiveWebuploader.ftl" as archiveWebuploader>
<script type="text/javascript" src="${request.contextPath}/static/jbmp/editor/js/flow.js"></script>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/boxy/boxy.css"/>
<script type="text/javascript" src="${request.contextPath}/static/boxy/jquery.boxy.js"></script>
<script type="text/javascript" charset="utf-8" src="${request.contextPath}/static/webuploader/webuploader.js"></script>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/webuploader/webuploader.css"/>
<@htmlmacro.moduleDiv titleName="报销申请">
<form name="expenseForm" id="expenseForm" method="post">
	<input id="id" name="officeExpense.id" value="${officeExpense.id!}" type="hidden" />
	<input id="unitId" name="officeExpense.unitId" value="${officeExpense.unitId!}" type="hidden" />
	<input id="applyUserId" name="officeExpense.applyUserId" value="${officeExpense.applyUserId!}" type="hidden" />
	<@htmlmacro.tableDetail divClass="table-form">
		<tr>
	        <th colspan="4" style="text-align:center;" class="p1 pt-10">报销申请</th>
	    </tr>
	    <tr>
	       <th style="width:20%"><span class="c-orange mr-5">*</span>报销金额（元）：</th>
	       <td style="width:30%">
	        	<input name="officeExpense.expenseMoney" id="expenseMoney" type="text" class="input-txt <#if viewOnly>input-readonly</#if>" <#if viewOnly>readonly="true"</#if> style="width:140px;" maxlength="10" dataType="float" minValue="0.01" decimalLength="2" value="${(officeExpense.expenseMoney?string('0.00'))?if_exists}" msgName="报销金额" notNull="true" />
	       </td>
	        <th style="width:20%"><span class="c-orange mr-5">*</span>报销类别：</th>
	        <td style="width:30%">
				<input type="text" id="expenseType" name="officeExpense.expenseType" notNull="true" msgName="报销类别" value="${officeExpense.expenseType!}" class="input-txt fn-left <#if viewOnly>input-readonly</#if>" <#if viewOnly>readonly="true"</#if> style="width:200px;" maxlength="30"/>
	        </td>
	    </tr>
	    <tr>
	        <th><span class="c-orange mr-5">*</span>费用明细：</th>
	        <td colspan="3">
	        	<textarea name="officeExpense.detail" id="detail" cols="70" rows="4" class="text-area my-5 <#if viewOnly>input-readonly</#if>" <#if viewOnly>readonly="true"</#if> style="width:80%;padding:5px 1%;height:50px;" msgName="费用明细" notNull="true" maxLength="250">${officeExpense.detail!}</textarea>
	        </td>
	    </tr>
	    <#if viewOnly>
	    <tr>
	        <th>提交时间：</th>
	        <td colspan="3">
	        	${(officeExpense.createTime?string('yyyy-MM-dd'))?if_exists}
	        </td>
	    </tr>
	    </#if>
			<#if viewOnly>
	    		<@archiveWebuploader.archiveWebuploaderEditViewer canEdit=false showAttachmentDivId='showAttDiv' editContentDivId='editContentDiv' isSend=true loadDiv=false />
	 		</#if>
	    <#if !viewOnly>
	    <tr>
    	   <th colspan="1" >
    	   <span class="c-orange mr-5">*</span>
    	   		审核流程：
    	   </th>
    	   <td colspan="3">
    	   <div class="query-part fn-rel fn-clear promt-div  flowDiv">
    	   	<@htmlmacro.select style="width:260px;" valName="officeExpense.flowId" valId="flowId" myfunchange="flowChange" msgName="报销流程">
				<a val="" ><span>请选择</span></a>
				<#if flowList?exists && flowList?size gt 0>
					<#list flowList as flow>
						<a val="${flow.flowId!}" <#if officeExpense.flowId?exists && officeExpense.flowId==flow.flowId>class="selected"</#if> dataEasyLevel="${flow.easyLevel?default('0')}" flowUnitId="${flow.ownerId!}" flowOwnerType="${flow.ownerType!}"><span>${flow.flowName!}</span></a>
					</#list>
				</#if>
			</@htmlmacro.select>
			</div>
    	   </td>
	    </tr>
	    </#if>   
	</@htmlmacro.tableDetail>
	<#if viewOnly>
    <br>
	<div class="fw-edit mt-10">
    	<p class="tt">审核意见</p>
        <div class="fw-item-wrap">
        	<#if (officeExpense.hisTaskList?size>0)>
        	<#list officeExpense.hisTaskList as item>
        		<div class="fw-item fn-clear">
                    <p class="tit fn-clear">
                        <span class="num">${item_index+1}</span>
                        <span class="pl-5">${item.taskName!}</span>
                    </p>
                    <p class="name">负责人：${item.assigneeName!}</p>
                    <div class="fn-clear"></div>
                    <div class="des" >
						<#if item.comment.commentType==1>
						${item.comment.textComment!}
		                <#else>
		                <img name='imgPic' class="my-image-class" border='0' align='absmiddle'  onmouseover="style.cursor='hand'"
							src="<#if item.comment.downloadPath?default("") != "">${item.comment.downloadPath?default("")}<#else></#if>" >
		                </#if>
		                </div>
		                <p class="date">${((item.comment.operateTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}</p>
                    </div>
        	</#list>
        	<#else>
        	<p class="no-data mt-50 mb-50">还没有数据哦！</p>
        	</#if>
        </div>
    </div>
    </#if>
    <@htmlmacro.tableDetail>
    <#if !viewOnly>
    	<@archiveWebuploader.archiveWebuploaderEditViewer canEdit=true showAttachmentDivId='showAttDiv' editContentDivId='editContentDiv' isSend=true loadDiv=false /> 
    </#if>
    
	<p class="pt-20 t-center">
	<#if !viewOnly>
		<a id="changeFlowDiv" class="abtn-blue-big" href="javascript:void(0);" onclick="doChangeFlow() ">修改流程</a>
	    <a class="abtn-blue-big" href="javascript:void(0);" onclick="doSave() ">保存</a>
	    <a class="abtn-blue-big" href="javascript:void(0);" onclick="doSubmit()">提交审核</a>
	</#if>
	<#if canBeRetract>
		<a href="javascript:void(0)" class="abtn-blue-big" onclick="retractFlow('${showReBackId!}','${taskKey!}','${reTaskId!}')">撤回</a>
	</#if>
	    <a class="abtn-blue-big ml-5" href="javascript:void(0);" onclick="back();">返回</a>
	<#if viewOnly>
		<a class="abtn-blue-big ml-5" href="javascript:void(0);" onclick="toDown('${officeExpense.id!}');">导出用款申请书</a>
		<a class="abtn-blue-big ml-5" href="javascript:void(0);" onclick="toPrint();">打印申请书</a>
	</#if>
    </p>
    </@htmlmacro.tableDetail>
</form>
<iframe name="downloadFrame" id="downloadFrame" style="display:none;"></iframe>
<div id="flowShow"  class="docReader my-20" style="height:660px;">
	<p >请选择流程</p>
</div>
<input id="wf-actionUrl" value="" type="hidden" />
<input id="wf-taskHandlerSaveJson" value="" type="hidden" />

<#if viewOnly>
<div class="table-print-warp" style="display:none;" id="printDiv">
    <div class="table-print-warp-sp">
    	<p class="tt-b">用款申请表</p>
        <table class="table-print-sp table-print-sp">
        	<tr>
        		<td width="88" height="39">
                    申请人
                </td>
                <td width="148">
                	${officeExpense.applyUserName!}
                </td>
                <td width="88">
                    部门（科室）
                </td>
                <td width="148">
                	${officeExpense.deptName!}
                </td>
        	</tr>
            <tr>
                <td height="39">
                	 费用明细   
                </td>
                <td colspan="3">
                	${officeExpense.detail!}
                </td>
            </tr>
            <tr>
                <td height="39">
                    结算方式
                </td>
                <td width="148">
                	转账•电汇•现金
                </td>
                <td width="88">
                    金额
                </td>
                <td width="148">
                	${officeExpense.expenseMoney!}
                </td>
            </tr>
            <tr>
                <td rowspan="2" height="79">
                    对方单位<br>名称
                </td>
                <td rowspan="2">
                    
                </td>
                <td height="39">
                   开户银行 
                </td>
                <td>
                   
                </td>
            </tr>
            <tr>
                <td height="39">
                  账号 
                </td>
                <td>
                   
                </td>
            </tr>
            <#if officeExpense.hisTaskList?exists&&officeExpense.hisTaskList?size gt 0>
            <#list officeExpense.hisTaskList as hisTask>
            <tr>
                <td height="124">
                    ${hisTask.taskName!}
                </td>
                <td colspan="3" class="table-print-vehicle fw-normal t-left"> 
                    <p class="m-left">审核意见:${hisTask.comment.textComment!}</p>
                    <br><br><br>
                    <p style="float:right;"><span class="td-spac0">审核人:${hisTask.assigneeName!}</span><span>审核时间:${((hisTask.comment.operateTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}</span></p>
                </td>
            </tr>
            </#list>
            </#if>
    </div>
</div>
</#if>
<script>
//流程选择
function flowChange(){
	var flowId= $("#flowId").val();
	var easyLevel = $(".flowDiv .selected").attr("dataEasyLevel");
	
	var expenseUnitId = $("#unitId").val();
	var flowUnitId = $(".flowDiv .selected").attr("flowUnitId");
	var flowOwnerType = $(".flowDiv .selected").attr("flowOwnerType");
	if(flowOwnerType == "0"){
		if(expenseUnitId == flowUnitId){
			$("#changeFlowDiv").show();
		}else{
			$("#changeFlowDiv").hide();
		}
	}
	else{
		$("#changeFlowDiv").show();
	}
	load("#flowShow","${request.contextPath}/jbmp/editor/wfPreview.action?subsystemId=70&id="+flowId+"&easyLevel="+easyLevel);
}
function toDown(id){
	location.href="${request.contextPath}/office/expense/expense-expenseDown.action?officeExpense.id="+id;
}
function toPrint(){
	$("#printDiv").css("display","");
	LODOP=getLodop();
	LODOP.ADD_PRINT_HTM("10mm","5mm","RightMargin:5mm","BottomMargin:15mm",getPrintContent(jQuery('#printDiv')));
  	LODOP.PREVIEW();
  	$("#printDiv").css("display","none");
}
function doChangeFlow(){
	if(showConfirm("确定保存信息并进入流程修改？")){
		if(isSubmit){
			return;
		}
		if(!checkAllValidate("#expenseForm")){
			if(!checkNoneFile()){
				return;
			}
			return;
		}
		if(!checkNoneFile()){
			return;
		}
		var expenseMoney = $("#expenseMoney").val();
		if(parseFloat(expenseMoney)==0){
			showMsgError("报销金额不得为0");
			return;
		}
		var flowId= $("#flowId").val();
		if(!flowId||flowId==""){
			 showMsgError("请选择一个合适的审批流程");
			 return;
		}
		var options = {
	       url:'${request.contextPath}/office/expense/expense-myExpense-save.action?officeExpense.state=1', 
	       success : showReply1,
	       dataType : 'json',
	       clearForm : false,
	       resetForm : false,
	       type : 'post'
	    };
	    isSubmit = true;
	    $('#expenseForm').ajaxSubmit(options);
	}
}
	
	function showReply1(data){
		if(!data.operateSuccess){
		   if(data.errorMessage!=null&&data.errorMessage!=""){
			   showMsgError(data.errorMessage);
			   isSubmit = false;
			   return;
		   }
		}else{
			showMsgSuccess(data.promptMessage,"",function(){
				var expenseId = data.errorMessage;
				$("#id").val(expenseId);
			  	openNew(expenseId);
			});
			return;
		}
	}

	function openNew(expenseId){
		var flowId = $("#flowId").val();
		var easyLevel = $(".flowDiv .selected").attr("dataEasyLevel");
		formUrl = '${request.contextPath}/jbmp/editor/wfEdit.action';
		businessType = '7005';
		operation = 'start';
		instanceType ='model';
		id = flowId;  
		actionUrl ='${request.contextPath}/office/expense/expense-changeFlow.action?expenseId='+expenseId;
		callBackJs = 'flowSuccess'; 
		taskHandlerSaveJson = '';
		currentStepId='';
		develop="false";
		subsystemId = 70;
		openOfficeWin(formUrl,businessType,operation,instanceType,id,actionUrl,callBackJs,taskHandlerSaveJson,currentStepId,develop,subsystemId,easyLevel);
	}
	
	function flowSuccess(){
		closeTip();
		showMsgSuccess("设计完成","",back);
	}

$(document).ready(function(){
	vselect();
	<#if viewOnly>
	load("#flowShow","${request.contextPath}/jbmp/editor/wfPreview.action?subsystemId=70&id=${officeExpense.flowId!}&instanceType=instance");
	<#else>
	var flowId= $("#flowId").val();
	load("#flowShow","${request.contextPath}/jbmp/editor/wfPreview.action?subsystemId=70&id="+flowId);
	</#if>
});

function back(){
	<#if fromTab="1">
		load("#adminDiv","${request.contextPath}/office/expense/expense-myExpense.action");
	<#elseif fromTab="2">
		load("#adminDiv","${request.contextPath}/office/expense/expense-expenseAudit.action");
	<#else>
		load("#adminDiv","${request.contextPath}/office/expense/expense-expenseQueryAdmin.action");
	</#if>
}
var isSubmit =false;

function checkNoneFile(){
	$("#noneFile").text("");
	if(($("#attachmentP").children("li")).length<=0){
		$("#noneFile").text("附件 不能为空！");
		return false;
	}
	$("#noneFile").text("");
	return true;
}
function doSave(){
	if(isSubmit){
		return;
	}
	if(!checkAllValidate("#expenseForm")){
		if(!checkNoneFile()){
			return;
		}
		return;
	}
	if(!checkNoneFile()){
		return;
	}
	var expenseMoney = $("#expenseMoney").val();
	if(parseFloat(expenseMoney)==0){
		showMsgError("报销金额不得为0");
		return;
	}
	var flowId= $("#flowId").val();
	if(!flowId||flowId==""){
		 showMsgError("请选择一个合适的审批流程");
		 return;
	}
	var options = {
       url:'${request.contextPath}/office/expense/expense-myExpense-save.action?officeExpense.state=1', 
       success : showReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post'
    };
    isSubmit = true;
    $('#expenseForm').ajaxSubmit(options);
}

function doSubmit(){
	var flowId= $("#flowId").val();
	if(!flowId||flowId==""){
		 showMsgError("要提交审核,必须选择一个流程");
		 return;
	}
	if(isSubmit){
		return;
	}
	if(!checkAllValidate("#expenseForm")){
		if(!checkNoneFile()){
			return;
		}
		return;
	}
	if(!checkNoneFile()){
		return;
	}
	
	var expenseMoney = $("#expenseMoney").val();
	if(parseFloat(expenseMoney)==0){
		showMsgError("报销金额不得为0");
		return;
	}
	
	var options = {
       url:'${request.contextPath}/office/expense/expense-myExpense-save.action?officeExpense.state=2', 
       success : showReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post'
    };
    isSubmit = true;
    $('#expenseForm').ajaxSubmit(options);
}

function showReply(data){
	if(!data.operateSuccess){
	   if(data.errorMessage!=null&&data.errorMessage!=""){
		   showMsgError(data.errorMessage);
		   isSubmit = false;
		   return;
	   }
	}else{
		showMsgSuccess(data.promptMessage,"",function(){
		  	back();
		});
		return;
	}
}

function doDownload(url){
	document.getElementById("downloadFrame").src = url;
}

function retractFlow(showReBackId, taskKey, reTaskId){
	$.getJSON("${request.contextPath}/office/expense/expense-retractFlow.action", {showReBackId:showReBackId,taskKey:taskKey,reTaskId:reTaskId}, function(data){
		if (!data.operateSuccess) {
	        if (data.errorMessage != null && data.errorMessage != "") {
	            showMsgError(data.errorMessage);
	            return;
	        }
	    } else {
	    	showMsgSuccess("撤回成功", "", back);
	        return;
	    }
	}).error(function(XMLHttpRequest, textStatus, errorThrown){alert(errorThrown);});
}
</script>
</@htmlmacro.moduleDiv >