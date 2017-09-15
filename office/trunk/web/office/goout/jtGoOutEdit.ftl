<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<script type="text/javascript" src="${request.contextPath}/static/jbmp/editor/js/flow.js"></script>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/boxy/boxy.css"/>
<script type="text/javascript" src="${request.contextPath}/static/boxy/jquery.boxy.js"></script>
<@htmlmacro.moduleDiv titleName="集体外出申请">
	<form name="officeJtgoOutForm" id="officeJtgoOutForm" method="post" enctype="multipart/form-data">
		<input id="id" name="officeJtgoOut.id" value="${officeJtgoOut.id!}" type="hidden" />
		<input id="unitId" name="officeJtgoOut.unitId" value="${officeJtgoOut.unitId!}" type="hidden" />
		<input id="applyUserId" name="officeJtgoOut.applyUserId" value="${officeJtgoOut.applyUserId!}" type="hidden" />
		<input id="createTime" name="officeJtgoOut.createTime" value="${officeJtgoOut.createTime!}" type="hidden" />
		<@htmlmacro.tableDetail divClass="table-form">
			<tr>
		        <th colspan="4" style="text-align:center;">集体外出申请</th>
		    </tr>
		    <tr>
		        <th style="width:20%"><span class="c-orange mr-5">*</span>${appsetting.getString("offce.goout.jtwc")!"外出类型"}：</th>
		        <td style="width:30%">
		        	<div class="select_box">
			    		<@htmlmacro.select style="width:150px;" valName="officeJtgoOut.outType" msgName="外出类型" notNull="true" valId="outType">
							${appsetting.getMcode("DM-JTWC").getHtmlTag(officeJtgoOut.outType?default(''))}
						</@htmlmacro.select>
					</div>
		        </td>
		        <th style="width:20%"><span class="c-orange mr-5">*</span>外出时间：</th>
		        <td style="width:30%">
		        	<input name="officeJtgoOut.days" id="days" type="text" class="input-txt" style="width:135px;" maxlength="20"  value="${officeJtgoOut.days!}" msgName="外出时间" notNull="true" />
		        </td>
		    </tr>
		    <tr>
		       <th style="width:20%"><span class="c-orange mr-5">*</span>外出人员：</th>
		       <td style="width:30%" colspan="3">
		        	<input name="officeJtgoOut.tripPerson" id="tripPerson" type="text" class="input-txt" style="width:81%;" maxlength="255"  value="${officeJtgoOut.tripPerson!}" msgName="外出人员" notNull="true" />
		       </td>
		    </tr>
		    <tr>
		        <th><span class="c-orange mr-5">*</span>外出事由：</th>
		        <td colspan="3">
		        	<textarea name="officeJtgoOut.tripReason" id="tripReason" cols="70" rows="4" class="text-area my-5" style="width:80%;padding:5px 1%;height:50px;" msgName="外出事由" notNull="true" maxLength="255">${officeJtgoOut.tripReason!}</textarea>
		        </td>
		    </tr>
		        <th>附件：</th>
		        <td colspan="3">
		        	<#if officeJtgoOut.attachments?exists && officeJtgoOut.attachments?size gt 0 >
		        		<input id="uploadContentFileInput" name="officeJtgoOut.uploadContentFileInput" type="text" class="input-txt input-readonly" readonly="readonly" style="width:200px;" value="<#if officeJtgoOut.attachments.get(0)?exists>${officeJtgoOut.attachments.get(0).fileName!}</#if>" maxLength="125"/>&nbsp;&nbsp;
		        	<#else>
		        		<input id="uploadContentFileInput" name="" type="text" class="input-txt input-readonly" readonly="readonly" style="width:200px;" value="" maxLength="125"/>&nbsp;&nbsp;
		        	</#if>
		        	<span class="upload-span1 "><a href="javascript:void(0);" class="">上传附件</a></span>       
					<input style="display:none" id="uploadContentFile" name="uploadContentFile" hidefocus type="file" onchange="uploadContent(this);" value="" >
					<div id="cleanFile" <#if officeJtgoOut.attachments?exists && officeJtgoOut.attachments?size gt 0>style="display:display"<#else>style="display:none"</#if>>
	 				<span class="upload-span" style="position:relative;left:325px;top:-20px;"><a href="javascript:deleteFile();" class="">清空</a></span>
	 				</div>
		        </td>
		    </tr>
		    <tr>
	    	   <th colspan="1" >
	    	   <span class="c-orange mr-5">*</span>
	    	   		集体外出流程选择：
	    	   </th>
	    	   <td colspan="3">
				<div class="query-part fn-rel fn-clear promt-div  flowDiv">
	    	   	<@htmlmacro.select style="width:40%;" valName="officeJtgoOut.flowId" valId="flowId" notNull="true" myfunchange="flowChange" msgName="集体外出流程">
					<a val="" ><span>请选择</span></a>
					<#if flowList?exists && flowList?size gt 0>
						<#list flowList as flow>
							<a val="${flow.flowId!}"<#if officeJtgoOut.flowId?exists && officeJtgoOut.flowId==flow.flowId>class="selected"</#if> dataEasyLevel="${flow.easyLevel?default('0')}" flowUnitId="${flow.ownerId!}"  flowOwnerType="${flow.ownerType!}"><span>${flow.flowName!}</span></a>
						</#list>
					</#if>
				</@htmlmacro.select>
				</div>
	    	   </td>
		    </tr>   
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
			
			load("#flowShow","${request.contextPath}/jbmp/editor/wfPreview.action?subsystemId=70&id="+flowId);
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
			jtGoOut();
		}
		var isSubmit =false;
		function doSave(){
			if(isSubmit){
				return;
			}
			if(!checkAllValidate("#officeJtgoOutForm")){
				return;
			}

			var flowId= $("#flowId").val();
			if(!flowId||flowId==""){
				 showMsgError("请选择一个合适的审批流程");
				 return;
			}
			var options = {
		       url:'${request.contextPath}/office/goout/goout-jtGoOutSave.action', 
		       success : showReply,
		       dataType : 'json',
		       clearForm : false,
		       resetForm : false,
		       type : 'post'
		    };
		    isSubmit = true;
		    $('#officeJtgoOutForm').ajaxSubmit(options);
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
			if(!checkAllValidate("#officeJtgoOutForm")){
				return;
			}
			var options = {
		       url:'${request.contextPath}/office/goout/goout-jtGoOutSubmit.action', 
		       success : showReply,
		       dataType : 'json',
		       clearForm : false,
		       resetForm : false,
		       type : 'post'
		    };
		    isSubmit = true;
		    $('#officeJtgoOutForm').ajaxSubmit(options);
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
				if(!checkAllValidate("#officeJtgoOutForm")){
					return;
				}
				
				var flowId= $("#flowId").val();
				if(!flowId||flowId==""){
					 showMsgError("请选择一个合适的审批流程");
					 return;
				}
				var options = {
			       url:'${request.contextPath}/office/goout/goout-jtGoOutSave.action', 
			       success : showReply1,
			       dataType : 'json',
			       clearForm : false,
			       resetForm : false,
			       type : 'post'
			    };
			    isSubmit = true;
			    $('#officeJtgoOutForm').ajaxSubmit(options);
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
			gooutId = gooutId+ "_2";
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
		
	</script>
</@htmlmacro.moduleDiv >