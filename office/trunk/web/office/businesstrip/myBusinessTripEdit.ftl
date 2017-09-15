<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<#import "../businesstrip/archiveWebuploader.ftl" as archiveWebuploader>
<script type="text/javascript" src="${request.contextPath}/static/jbmp/editor/js/flow.js"></script>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/boxy/boxy.css"/>
<script type="text/javascript" src="${request.contextPath}/static/boxy/jquery.boxy.js"></script>
<script type="text/javascript" charset="utf-8" src="${request.contextPath}/static/webuploader/webuploader.js"></script>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/webuploader/webuploader.css"/>
<@htmlmacro.moduleDiv titleName="出差申请">
	<form name="myBusinessTripForm" id="myBusinessTripForm" method="post">
		<input id="id" name="officeBusinessTrip.id" value="${officeBusinessTrip.id!}" type="hidden" />
		<input id="unitId" name="officeBusinessTrip.unitId" value="${officeBusinessTrip.unitId!}" type="hidden" />
		<input id="applyUserId" name="officeBusinessTrip.applyUserId" value="${officeBusinessTrip.applyUserId!}" type="hidden" />
		<input id="createTime" name="officeBusinessTrip.createTime" value="${officeBusinessTrip.createTime!}" type="hidden" />
		<@htmlmacro.tableDetail divClass="table-form">
			<tr>
		        <th colspan="4" style="text-align:center;">出差申请</th>
		    </tr>
		    <tr>
		        <th style="width:20%"><span class="c-orange mr-5">*</span>开始时间：</th>
		        <td style="width:30%">
		        	<@htmlmacro.datepicker name="officeBusinessTrip.beginTime" id="beginTime" class="input-txt" style="width:39%;" msgName="开始时间" notNull="true" onpicked="clickChangeBegin" value="${((officeBusinessTrip.beginTime)?string('yyyy-MM-dd'))?if_exists}" />
		        </td>
		        <th style="width:20%"><span class="c-orange mr-5">*</span>结束时间：</th>
		        <td style="width:30%">
		        	<@htmlmacro.datepicker name="officeBusinessTrip.endTime" id="endTime" class="input-txt" style="width:39%;" msgName="结束时间" notNull="true" onpicked="clickChangeEnd" value="${((officeBusinessTrip.endTime)?string('yyyy-MM-dd'))?if_exists}" />
		        </td>
		    </tr>
		    <tr>
		       <th style="width:20%"><span class="c-orange mr-5">*</span>出差天数：</th>
		       <td style="width:30%">
		        	<input name="officeBusinessTrip.days" id="days" type="text" class="input-txt" style="width:140px;" maxlength="5" dataType="float" maxValue="999" minValue="0.1" decimalLength="1" value="${(officeBusinessTrip.days?string('0.#'))?if_exists}" msgName="出差天数" notNull="true" />
		       </td>
		        <th style="width:20%"><span class="c-orange mr-5">*</span>出差地点：</th>
		        <td style="width:30%">
		        	<input type="text" msgName="出差地点" class="input-txt fn-left" id="place" name="officeBusinessTrip.place" maxlength="50" notNull="true" value="${officeBusinessTrip.place!}" style="width:180px;">
		        </td>
		    </tr>
		    <tr>
		        <th><span class="c-orange mr-5">*</span>出差事由：</th>
		        <td colspan="3">
		        	<textarea name="officeBusinessTrip.tripReason" id="tripReason" cols="70" rows="4" class="text-area my-5" style="width:80%;padding:5px 1%;height:50px;" msgName="出差事由" notNull="true" maxLength="255">${officeBusinessTrip.tripReason!}</textarea>
		        </td>
		    </tr>
		    <!--<tr>
		        <th><span class="c-orange mr-5">*</span>附件：</th>
		        <td colspan="3">
		        	<#if officeBusinessTrip.attachments?exists && officeBusinessTrip.attachments?size gt 0 >
		        		<input id="uploadContentFileInput" name="officeBusinessTrip.uploadContentFileInput" type="text" class="input-txt input-readonly" readonly="readonly" style="width:200px;margin-top:5px;" value="<#if officeBusinessTrip.attachments.get(0)?exists>${officeBusinessTrip.attachments.get(0).fileName!}</#if>" maxLength="125"/>&nbsp;&nbsp;
		        	<#else>
		        		<input id="uploadContentFileInput" name="" type="text" class="input-txt input-readonly" readonly="readonly" style="width:200px;" value="" maxLength="125"/>&nbsp;&nbsp;
		        	</#if>
		        	<span class="upload-span1 "><a href="javascript:void(0);" class="">上传附件</a></span>       
					<input style="display:none" id="uploadContentFile" name="uploadContentFile" hidefocus type="file" onchange="uploadContent(this);" value="" >
	 				<span id="noneFile"  class="field_tip input-txt-warn-tip"></span>
					<div id="cleanFile" <#if officeBusinessTrip.attachments?exists && officeBusinessTrip.attachments?size gt 0>style="display:display"<#else>style="display:none"</#if>>
	 				<span class="upload-span" style="position:relative;left:325px;top:-20px;"><a href="javascript:deleteFile();" class="">清空</a></span>
	 				</div>
		        </td>
		    </tr>-->
		    <tr>
	    	   <th colspan="1" >
	    	   <span class="c-orange mr-5">*</span>
	    	   		出差流程选择：
	    	   </th>
	    	   <td colspan="3">
	    	   <div class="query-part fn-rel fn-clear promt-div  flowDiv">
	    	   	<@htmlmacro.select style="width:260px;" valName="officeBusinessTrip.flowId" valId="flowId" myfunchange="flowChange" msgName="出差流程">
					<a val="" ><span>请选择</span></a>
					<#if flowList?exists && flowList?size gt 0>
						<#list flowList as flow>
							<!--<a val="${flow.flowId!}"<#if officeBusinessTrip.flowId?exists && officeBusinessTrip.flowId==flow.flowId>class="selected"</#if>  ><span>${flow.flowName!}</span></a>-->
							<a val="${flow.flowId!}" <#if officeBusinessTrip.flowId?exists && officeBusinessTrip.flowId==flow.flowId>class="selected"</#if> dataEasyLevel="${flow.easyLevel?default('0')}" flowUnitId="${flow.ownerId!}" flowOwnerType="${flow.ownerType!}"><span>${flow.flowName!}</span></a>
						</#list>
					</#if>
				</@htmlmacro.select>
				</div>
	    	   </td>
		    </tr>
		    </@htmlmacro.tableDetail>
		    <@archiveWebuploader.archiveWebuploaderEditViewer canEdit=true showAttachmentDivId='showAttDiv' editContentDivId='editContentDiv' isSend=true loadDiv=false />     
		    <@htmlmacro.tableDetail>
		    <tr>
		    	<td colspan="4" class="td-opt">
		    		<a id="changeFlowDiv" class="abtn-blue-big" href="javascript:void(0);" onclick="doChangeFlow() ">修改流程</a>
		    	    <a class="abtn-blue-big" href="javascript:void(0);" onclick="doSave() ">保存</a>
		    	    <a class="abtn-blue-big" href="javascript:void(0);" onclick="doSubmit()">提交审核</a>
				    <a class="abtn-blue-big ml-5" href="javascript:void(0);" onclick="back();">返回</a>
		        </td>
		    </tr>
		</@htmlmacro.tableDetail>
	</form>
	<div id="flowShow"  class="docReader my-20" style="height:660px;">
		<p >请选择流程</p>
	</div>
	<input id="wf-actionUrl" value="" type="hidden" />
	<input id="wf-taskHandlerSaveJson" value="" type="hidden" />
	<script>
	
		function doChangeFlow(){
			if(showConfirm("确定保存信息并进入流程修改？")){
				if(isSubmit){
					return;
				}
				if(!checkAllValidate("#myBusinessTripForm")){
					if(!checkNoneFile()){
						return;
					}
					return;
				}
				
				if(!checkNoneFile()){
					return;
				}
				if(!checkAfterDate($("#beginTime").get(0),$("#endTime").get(0))){
					return;
				};
				var flowId= $("#flowId").val();
				if(!flowId||flowId==""){
					 showMsgError("请选择一个合适的审批流程");
					 return;
				}
			var options = {
		       url:'${request.contextPath}/office/businesstrip/businessTrip-myBusinessTripSave.action', 
		       success : showReply1,
		       dataType : 'json',
		       clearForm : false,
		       resetForm : false,
		       type : 'post'
		    };
		    isSubmit = true;
		    $('#myBusinessTripForm').ajaxSubmit(options);
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
					var officeBusinessTripId = data.errorMessage;
					$("#id").val(officeBusinessTripId);
				  	openNew(officeBusinessTripId);
				});
				return;
			}
		}
	
		function openNew(officeBusinessTripId){
			var flowId = $("#flowId").val();
			var easyLevel = $(".flowDiv .selected").attr("dataEasyLevel");
			formUrl = '${request.contextPath}/jbmp/editor/wfEdit.action';
			businessType = '7004';
			operation = 'start';
			instanceType ='model';
			id = flowId;  
			actionUrl ='${request.contextPath}/office/businesstrip/businessTrip-changeFlow.action?businessTripId='+officeBusinessTripId;
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
		
		//重置上传位置的计算
		function resetFilePos(){
			try{
				$("#uploadContentFile").css({"position":"absolute","-moz-opacity":"0","opacity":"0","filter":"alpha(opacity=0)","width":$(".upload-span1 a").width() + 27,"height":$(".upload-span1").height(),"cursor":"pointer"});
				$("#uploadContentFile").offset({"left":$(".upload-span1").offset().left});		
				$("#uploadContentFile").css({"display":""});
				$("#uploadContentFile").offset({"top":$(".upload-span1").offset().top });
			}catch(e){
			}
		}
		//流程选择
		function flowChange(){
			var flowId= $("#flowId").val();
			
			var easyLevel = $(".flowDiv .selected").attr("dataEasyLevel");
			
			var businessUnitId = $("#unitId").val();
			var flowUnitId = $(".flowDiv .selected").attr("flowUnitId");
			var flowOwnerType = $(".flowDiv .selected").attr("flowOwnerType");
			if(flowOwnerType == "0"){
				if(businessUnitId == flowUnitId){
					$("#changeFlowDiv").show();
				}else{
					$("#changeFlowDiv").hide();
				}
			}
			else{
				$("#changeFlowDiv").show();
			}
			
			load("#flowShow","${request.contextPath}/jbmp/editor/wfPreview.action?subsystemId=70&id="+flowId+"&easyLevel="+easyLevel);
			
			//load("#flowShow","${request.contextPath}/jbmp/editor/wfPreview.action?subsystemId=70&id="+flowId);
		}
		//监听窗口 防止窗口变化影响上传位置
		$(window).resize(function() {
			resetFilePos();
		});
		
		function uploadContent(target){
			$("#uploadContentFileInput").val($(target).val());
			$('#cleanFile').attr("style","display:display");
			$('#cleanFile').attr("style","height:0px");
			$("#noneFile").attr("style","display:none");
			$("#noneFile").text("");
		}
		
		$(document).ready(function(){
			vselect();
			resetFilePos();
			var flowId= $("#flowId").val();
			load("#flowShow","${request.contextPath}/jbmp/editor/wfPreview.action?subsystemId=70&id="+flowId);
		});
		
		function back(){
			load("#businessTripeDiv", "${request.contextPath}/office/businesstrip/businessTrip-myBusinessTripList.action");
		}
		var isSubmit =false;
		function doSave(){
			if(isSubmit){
				return;
			}
			if(!checkAllValidate("#myBusinessTripForm")){
				if(!checkNoneFile()){
					return;
				}
				return;
			}
			if(!checkNoneFile()){
				return;
			}
			if(!checkAfterDate($("#beginTime").get(0),$("#endTime").get(0))){
				return;
			};
			var flowId= $("#flowId").val();
			if(!flowId||flowId==""){
				 showMsgError("请选择一个合适的审批流程");
				 return;
			}
			var options = {
		       url:'${request.contextPath}/office/businesstrip/businessTrip-myBusinessTripSave.action', 
		       success : showReply,
		       dataType : 'json',
		       clearForm : false,
		       resetForm : false,
		       type : 'post'
		    };
		    isSubmit = true;
		    $('#myBusinessTripForm').ajaxSubmit(options);
		}
		function deleteFile(){
			$('#uploadContentFileInput').val('');
			var file = $("#uploadContentFile")
			file.after(file.clone().val(""));
			file.remove();
			$('#cleanFile').attr("style","display:none");
			$("#noneFile").attr("style","display:display");
			$("#noneFile").text("");
		}
		
		function checkNoneFile(){
			$("#noneFile").text("");
			if(($("#attachmentP").children("li")).length<=0){
				$("#noneFile").text("附件 不能为空！");
				return false;
			}
			$("#noneFile").text("");
			return true;
		}
		function doSubmit(){
			var flowId= $("#flowId").val();
			var displayFile=$("#cleanFile").attr("style");
			if(!flowId||flowId==""){
				 showMsgError("要提交审核,必须选择一个流程");
				 return;
			}
			if(!checkAfterDate($("#beginTime").get(0),$("#endTime").get(0))){
				return;
			};
			if(isSubmit){
				return;
			}
			if(!checkAllValidate("#myBusinessTripForm")){
				if(!checkNoneFile()){
					return;
				}
				return;
			}
			if(!checkNoneFile()){
				return;
			}
			var options = {
		       url:'${request.contextPath}/office/businesstrip/businessTrip-myBusinessTripSubmit.action', 
		       success : showReply,
		       dataType : 'json',
		       clearForm : false,
		       resetForm : false,
		       type : 'post'
		    };
		    isSubmit = true;
		    $('#myBusinessTripForm').ajaxSubmit(options);
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
		
		function clickChangeBegin() {  
			var startDate = $("#beginTime").val();
			var endDate = $("#endTime").val();
			if(startDate!=null&&startDate!="" && endDate!=null&&endDate!=""){
				if(startDate>endDate){
					showMsgError('开始时间不能大于结束时间');
					return;
				}
				var num = countTimeLength('D',startDate,endDate);
				$("#days").val(parseInt(num)+1);
			}
		}
		function clickChangeEnd() {  
			var startDate = $("#beginTime").val();
			var endDate = $("#endTime").val();
			if(startDate!=null&&startDate!="" && endDate!=null&&endDate!=""){
				if(startDate>endDate){
					showMsgError('开始时间不能大于结束时间');
					return;
				}
				var num = countTimeLength('D',startDate,endDate);
				$("#days").val(parseInt(num)+1);
			}
		}
		
		function countTimeLength(interval, date1, date2) {  
		    var objInterval = {'D' : 1000 * 60 * 60 * 24, 'H' : 1000 * 60 * 60, 'M' : 1000 * 60, 'S' : 1000, 'T' : 1};  
		    interval = interval.toUpperCase();  
		    var dt1 = Date.parse(date1.replace(/-/g, "/"));  
		    var dt2 = Date.parse(date2.replace(/-/g, "/"));  
		    try{  
		        return ((dt2 - dt1) / objInterval[interval]).toFixed(2);//保留两位小数点  
		    }catch (e){  
		        return e.message;  
		    }  
		} 
	</script>
</@htmlmacro.moduleDiv >