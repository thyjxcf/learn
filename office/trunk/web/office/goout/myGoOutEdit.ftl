<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<#import "../goout/archiveWebuploader.ftl" as archiveWebuploader>
<script type="text/javascript" src="${request.contextPath}/static/jbmp/editor/js/flow.js"></script>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/boxy/boxy.css"/>
<script type="text/javascript" src="${request.contextPath}/static/boxy/jquery.boxy.js"></script>
<script type="text/javascript" charset="utf-8" src="${request.contextPath}/static/webuploader/webuploader.js"></script>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/webuploader/webuploader.css"/>
<@htmlmacro.moduleDiv titleName="出差申请">
	<form name="myGoOutForm" id="myGoOutForm" method="post" enctype="multipart/form-data">
		<input id="id" name="officeGoOut.id" value="${officeGoOut.id!}" type="hidden" />
		<input id="unitId" name="officeGoOut.unitId" value="${officeGoOut.unitId!}" type="hidden" />
		<input id="applyUserId" name="officeGoOut.applyUserId" value="${officeGoOut.applyUserId!}" type="hidden" />
		<input id="createTime" name="officeGoOut.createTime" value="${officeGoOut.createTime!}" type="hidden" />
		<@htmlmacro.tableDetail divClass="table-form">
			<tr>
		        <th colspan="4" style="text-align:center;">外出申请</th>
		    </tr>
		    <tr>
		        <th style="width:20%"><span class="c-orange mr-5">*</span>开始时间：</th>
		        <td style="width:30%">
		        	<@htmlmacro.datepicker name="officeGoOut.beginTime" id="beginTime"  style="width:39%;" msgName="开始时间" notNull="true" size="20" maxlength="19" dateFmt="yyyy-MM-dd HH:mm:00" onpicked="clickChangeBegin" value="${((officeGoOut.beginTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}" />
		        </td>
		        <th style="width:20%"><span class="c-orange mr-5">*</span>结束时间：</th>
		        <td style="width:30%">
		        	<@htmlmacro.datepicker name="officeGoOut.endTime" id="endTime" style="width:39%;" msgName="结束时间" notNull="true" size="20" maxlength="19" dateFmt="yyyy-MM-dd HH:mm:00" onpicked="clickChangeEnd" value="${((officeGoOut.endTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}" />
		        </td>
		    </tr>
		    <tr>
		       <th style="width:20%"><span class="c-orange mr-5">*</span>外出时间(小时)：</th>
		       <td style="width:30%">
		        	<input name="officeGoOut.hours" id="hours" onChange="checkHours()" type="text" class="input-txt" style="width:135px;" maxlength="5"  maxValue="999" minValue="1"  value="${((officeGoOut.hours)?string('0.0'))?if_exists}" msgName="外出时间" notNull="true" />
		       		<span id="errorHours" class="field_tip input-txt-warn-tip"></span>
		       </td>
		       <th style="width:20%"><span class="c-orange mr-5">*</span>外出类型：</th>
		       <td style="width:30%">
		        	<#-- <@htmlmacro.select style="width:42%;" valName="officeGoOut.outType" valId="outType"  msgName="外出类型">
					<a val="1" <#if officeGoOut.outType?default('')=='1'>class="selected"</#if> ><span>因公外出</span></a>
					<a val="2" <#if officeGoOut.outType?default('')=='2'>class="selected"</#if> ><span>因私外出</span></a>
				</@htmlmacro.select>-->
				 <span class="ui-radio <#if officeGoOut.outType?default('')=='1'>ui-radio-current</#if>"  data-name="a"><input <#if officeGoOut.outType?default('')=='1'>checked="checked"</#if> type="radio" class="radio" name="officeGoOut.outType" value="1">因公外出</span>
				 <span class="ui-radio <#if officeGoOut.outType?default('')=='2'>ui-radio-current</#if>"  data-name="a"><input <#if officeGoOut.outType?default('')=='2'>checked="checked"</#if> type="radio" class="radio" name="officeGoOut.outType" value="2">因私外出</span>
		       	 <span id="errorType" class="field_tip input-txt-warn-tip"></span>
		       </td>
		    </tr>
		    <tr>
		        <th><span class="c-orange mr-5">*</span>外出事由：</th>
		        <td colspan="3">
		        	<textarea name="officeGoOut.tripReason" id="tripReason" cols="70" rows="4" class="text-area my-5" style="width:80%;padding:5px 1%;height:50px;" msgName="外出事由" notNull="true" maxLength="255">${officeGoOut.tripReason!}</textarea>
		        </td>
		    </tr>
		    <!--<tr>
		        <th>附件：</th>
		        <td colspan="3">
		        	<#if officeGoOut.attachments?exists && officeGoOut.attachments?size gt 0 >
		        		<input id="uploadContentFileInput" name="officeGoOut.uploadContentFileInput" type="text" class="input-txt input-readonly" readonly="readonly" style="width:200px;" value="<#if officeGoOut.attachments.get(0)?exists>${officeGoOut.attachments.get(0).fileName!}</#if>" maxLength="125"/>&nbsp;&nbsp;
		        	<#else>
		        		<input id="uploadContentFileInput" name="" type="text" class="input-txt input-readonly" readonly="readonly" style="width:200px;" value="" maxLength="125"/>&nbsp;&nbsp;
		        	</#if>
		        	<span class="upload-span1 "><a href="javascript:void(0);" class="">上传附件</a></span>       
					<input style="display:none" id="uploadContentFile" name="uploadContentFile" hidefocus type="file" onchange="uploadContent(this);" value="" >
					<div id="cleanFile" <#if officeGoOut.attachments?exists && officeGoOut.attachments?size gt 0>style="display:display;height:0px;"<#else>style="display:none"</#if>>
	 				<span class="upload-span" style="position:relative;left:325px;top:-20px;"><a href="javascript:deleteFile();" class="">清空</a></span>
	 				</div>
		        </td>
		    </tr>-->
		    <tr>
	    	   <th colspan="1" >
	    	   <span class="c-orange mr-5">*</span>
	    	   		外出流程选择：
	    	   </th>
	    	   <td colspan="3">
				<div class="query-part fn-rel fn-clear promt-div  flowDiv">
					<#if flowList?exists && flowList?size gt 0>
			        	<@htmlmacro.select style="width:260px;" valName="officeGoOut.flowId" valId="flowId" myfunchange="flowChange" msgName="流程选择"notNull='true'>
							<a val="" ><span>--请选择--</span></a>
								<#list flowList as flow>
									<a val="${flow.flowId!}" <#if officeGoOut.flowId?exists && officeGoOut.flowId==flow.flowId>class="selected"</#if> dataEasyLevel="${flow.easyLevel?default('0')}" flowUnitId="${flow.ownerId!}" flowOwnerType="${flow.ownerType!}"><span>${flow.flowName!}</span></a>
								</#list>
						</@htmlmacro.select>
					<#else>
						&nbsp;请管理员添加流程!
					</#if>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			        <#-- <a class="abtn-blue" href="javascript:void(0);" onclick="goModuleFlow('${module.url!}','${module.subsystem}','${module.id}','${module.parentid}','${module.name}','subsystem','0',true); return false;">办公流程跳转</a>-->
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
			
			var goooutUnitId = $("#unitId").val();
			var flowUnitId = $(".flowDiv .selected").attr("flowUnitId");
			var flowOwnerType = $(".flowDiv .selected").attr("flowOwnerType");
			if(flowOwnerType == "0"){
				if(goooutUnitId == flowUnitId){
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
		//监听窗口 防止窗口变化影响上传位置
		$(window).resize(function() {
			resetFilePos();
		});
		
		function uploadContent(target){
			$("#uploadContentFileInput").val($(target).val());
			$('#cleanFile').attr("style","display:display");
			$('#cleanFile').attr("style","height:0px");
		}
		
		function deleteFile(){
			$('#uploadContentFileInput').val('');
			var file = $("#uploadContentFile")
			file.after(file.clone().val(""));
			file.remove();
			$('#cleanFile').attr("style","display:none");
		}
		
		$(document).ready(function(){
			vselect();
			resetFilePos();
			var flowId= $("#flowId").val();
			
			var goooutUnitId = $("#unitId").val();
			var flowUnitId = $(".flowDiv .selected").attr("flowUnitId");
			var flowOwnerType = $(".flowDiv .selected").attr("flowOwnerType");
			if(flowOwnerType == "0"){
				if(goooutUnitId == flowUnitId){
					$("#changeFlowDiv").show();
				}else{
					$("#changeFlowDiv").hide();
				}
			}
			else{
				$("#changeFlowDiv").show();
			}
			
			load("#flowShow","${request.contextPath}/jbmp/editor/wfPreview.action?subsystemId=70&id="+flowId);
		});
		
		function back(){
			load("#goOutDiv", "${request.contextPath}/office/goout/goout-myGoOutList.action?");
		}
		function checkType(){
			$("#errorType").text("");
			if($(".ui-radio.ui-radio-current").attr("data-name")==undefined){
				$("#errorType").text("外出类型 不能为空！");
				return false;
			}
			$("#errorType").text("");
			return true;
		}
		
		var isSubmit =false;
		function doSave(){
			if(isSubmit){
				return;
			}
			if(!checkAllValidate("#myGoOutForm")){
				if(!checkType()){
					return;
				}
				return;
			}
			if(!checkType()){
				return;
			}
			if(!checkHours()){
				return;
			}
			if(!checkAfterDate($("#beginTime").get(0),$("#endTime").get(0))){
				return;
			}
			var flowId= $("#flowId").val();
			if(!flowId||flowId==""){
				 showMsgError("请选择一个合适的审批流程");
				 return;
			}
			var options = {
		       url:'${request.contextPath}/office/goout/goout-myGoOutSave.action', 
		       success : showReply,
		       dataType : 'json',
		       clearForm : false,
		       resetForm : false,
		       type : 'post'
		    };
		    isSubmit = true;
		    $('#myGoOutForm').ajaxSubmit(options);
		}
		
		function doSubmit(){
			var flowId= $("#flowId").val();
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
			if(!checkAllValidate("#myGoOutForm")){
				if(!checkType()){
					return;
				}
				return;
			}
			if(!checkType()){
				return;
			}
			if(!checkHours()){
				return;
			}
			var options = {
		       url:'${request.contextPath}/office/goout/goout-myGoOutSubmit.action', 
		       success : showReply,
		       dataType : 'json',
		       clearForm : false,
		       resetForm : false,
		       type : 'post'
		    };
		    isSubmit = true;
		    $('#myGoOutForm').ajaxSubmit(options);
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
		
		function doChangeFlow(){
			if(showConfirm("确定保存信息并进入流程修改？")){
				if(isSubmit){
					return;
				}
				if(!checkAllValidate("#myGoOutForm")){
					if(!checkType()){
						return;
					}
					return;
				}
				
				if(!checkType()){
					return;
				}
				
				if(!checkHours()){
					return;
				}
				if(!checkAfterDate($("#beginTime").get(0),$("#endTime").get(0))){
					return;
				}
				var flowId= $("#flowId").val();
				if(!flowId||flowId==""){
					 showMsgError("请选择一个合适的审批流程");
					 return;
				}
				var options = {
			       url:'${request.contextPath}/office/goout/goout-myGoOutSave.action', 
			       success : showReply1,
			       dataType : 'json',
			       clearForm : false,
			       resetForm : false,
			       type : 'post'
			    };
			    isSubmit = true;
			    $('#myGoOutForm').ajaxSubmit(options);
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
					var gooutId = data.errorMessage;
					$("#id").val(gooutId);
				  	openNew(gooutId);
				});
				return;
			}
		}
		
		function openNew(gooutId){
			var flowId = $("#flowId").val();
			var easyLevel = $(".flowDiv .selected").attr("dataEasyLevel");
			gooutId = gooutId+ "_1";
			formUrl = '${request.contextPath}/jbmp/editor/wfEdit.action';
			businessType = '7006';
			operation = 'start';
			instanceType ='model';
			id = flowId;  
			actionUrl ='${request.contextPath}/office/goout/goout-changeFlow.action?gooutId='+gooutId;
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
		
		function checkHours(){
			var hours=$("#hours").val();
			$("#hours").css("border-color","#a0b2be");
			$("#errorHours").text("");
			if(hours==undefined || isNaN(hours) ||hours==0){
				$("#errorHours").text("请填写正确的数字!");
				return false;
			}
			var hoursTwo=hours*2
			if(hoursTwo-parseInt(hoursTwo)!=0){
				$("#errorHours").text("请填写正确的数字!");
				return false;
			}
			$("#errorHours").text("");
			return true;
		}
		function clickChangeBegin() {  
			var startDate = $("#beginTime").val();
			var endDate = $("#endTime").val();
			if(startDate!=null&&startDate!="" && endDate!=null&&endDate!=""){
				if(startDate>endDate){
					showMsgError('开始时间不能大于结束时间');
					return;
				}
				var num = countTimeLength('H',startDate,endDate);
				if(num-parseInt(num)>0.5){
					$("#hours").val((parseInt(num)+1)+".0");
				}else if(num-parseInt(num)>0){
					$("#hours").val(parseInt(num)+0.5);
				}else{
					$("#hours").val(num);
				}
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
				var num = countTimeLength('H',startDate,endDate);
				if(num-parseInt(num)>0.5){
					$("#hours").val((parseInt(num)+1)+".0");
				}else if(num-parseInt(num)>0){
					$("#hours").val(parseInt(num)+0.5);
				}else{
					$("#hours").val(num);
				}
			}
		}
		
		function countTimeLength(interval, date1, date2) {  
		    var objInterval = {'D' : 1000 * 60 * 60 * 24, 'H' : 1000 * 60 * 60, 'M' : 1000 * 60, 'S' : 1000, 'T' : 1};  
		    interval = interval.toUpperCase();  
		    var dt1 = Date.parse(date1.replace(/-/g, "/"));  
		    var dt2 = Date.parse(date2.replace(/-/g, "/"));  
		    try{  
		        return ((dt2 - dt1) / objInterval[interval]).toFixed(1);//保留两位小数点  
		    }catch (e){  
		        return e.message;  
		    }  
		}
		function goModuleFlow(url,subsystem,moduleId,parentId,moduleName,source,listType,isGoFlow){
		if(isGoFlow){
			if(showConfirm("是否保存外出申请信息？")){
				if(isSubmit){
					return;
				}
				if(!checkAllValidate("#myGoOutForm")){
					return;
				}
				if(!checkHours()){
					return;
				}
				if(!checkAfterDate($("#beginTime").get(0),$("#endTime").get(0))){
					return;
				}
				var flowId= $("#flowId").val();
				if(!flowId||flowId==""){
					 showMsgError("请选择一个合适的审批流程");
					 return;
				}
				var options = {
			       url:'${request.contextPath}/office/goout/goout-myGoOutSave.action', 
			       success : function(data){
						if(!data.operateSuccess){
						   if(data.errorMessage!=null&&data.errorMessage!=""){
							   showMsgError(data.errorMessage);
							   isSubmit = false;
							   return;
						   }
						}else{
							showMsgSuccess(data.promptMessage,"",function(){
								isGoFlow=false;
								goModuleFlow(url,subsystem,moduleId,parentId,moduleName,source,listType,isGoFlow);
							});
						}			       
			       },
			       dataType : 'json',
			       clearForm : false,
			       resetForm : false,
			       type : 'post'
			    };
			    isSubmit = true;
			    $('#myGoOutForm').ajaxSubmit(options);
			}else{
				isGoFlow=false;
				goModuleFlow(url,subsystem,moduleId,parentId,moduleName,source,listType,isGoFlow);
			}
		}else{
			if(url.indexOf("http://")==0){
			    window.open(url);
			}else{
				load("#container","${request.contextPath}/"+url);
				$('body,.common-wrap').removeAttr('style');
				if(source =="desktop"){
					$("#modelList").show();
					$('.current').removeClass('current');
					//如果是在更多里面 需要reload subsytem
					if($('#subsystem'+subsystem).attr("name") =="more"){
						load("#subSystemList","${request.contextPath}/system/homepage/subsystem.action?appId="+subsystem+"&moduleID="+moduleId);
					}else{
						$('#subsystem'+subsystem).addClass('current');
					}
					load("#modelList","${request.contextPath}/system/homepage/model.action?appId="+subsystem+"&moduleID="+moduleId,"assembleCrumbs('"+subsystem+"','"+parentId+"','"+moduleName+"')");
				}else{
					$('.module').each(function(fn){
						$(this).removeClass('current');
					});
					$('#module'+moduleId).addClass('current');
					assembleCrumbs(subsystem,parentId,moduleName);
				}
				
				if(listType && listType=='1'){
					hiddenModel();
				}else{
					showModel();
				};
			}
		}
	
	} 
	</script>
</@htmlmacro.moduleDiv >