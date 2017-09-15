<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<#import "../teacherLeave/archiveWebuploader.ftl" as archiveWebuploader>
<script type="text/javascript" src="${request.contextPath}/static/jbmp/editor/js/flow.js"></script>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/boxy/boxy.css"/>
<script type="text/javascript" src="${request.contextPath}/static/boxy/jquery.boxy.js"></script>
<@htmlmacro.moduleDiv titleName="教师请假申请">
	<form name="teacherLeaveForm" id="teacherLeaveForm" method="post" enctype="multipart/form-data">
		<input id="id" name="officeTeacherLeave.id" value="${officeTeacherLeave.id!}" type="hidden" />
		<input id="unitId" name="officeTeacherLeave.unitId" value="${officeTeacherLeave.unitId!}" type="hidden" />
		<input id="deptId" name="officeTeacherLeave.deptId" value="${officeTeacherLeave.deptId!}" type="hidden" />
		<@htmlmacro.tableDetail divClass="table-form">
			<tr>
		        <th colspan="4" style="text-align:center;">教师请假申请</th>
		    </tr>
		    <tr id="teat">
		        <th  ><span class="c-orange mr-5">*</span>请假时间：</th>
		        <td colspan="3" >
		        	<@htmlmacro.datepicker name="officeTeacherLeave.leaveBeignTime" id="leaveBeignTime" class="input-txt" style="width:39%;" msgName="开始时间" notNull="true" onpicked="clickChangeBegin" value="${((officeTeacherLeave.leaveBeignTime)?string('yyyy-MM-dd'))?if_exists}" />
		        	至
		        	<@htmlmacro.datepicker name="officeTeacherLeave.leaveEndTime" id="leaveEndTime" class="input-txt" style="width:39%;" msgName="结束时间" notNull="true" onpicked="clickChangeEnd" value="${((officeTeacherLeave.leaveEndTime)?string('yyyy-MM-dd'))?if_exists}" />
		        </td>
		    </tr>
		    
		    <tr>
		       <th style="width:20%"><span class="c-orange mr-5">*</span>共计天数：</th>
		       <td style="width:30%">
		        	<input name="officeTeacherLeave.days" id="days" type="text" class="input-txt" style="width:140px;" maxlength="5" dataType="float" maxValue="999" minValue="0.1" decimalLength="1" value="${officeTeacherLeave.days?string('0.#')}" msgName="共计天数" notNull="true" />
		       </td>
		        <th style="width:20%"><span class="c-orange mr-5">*</span>申请人：</th>
		        <td style="width:30%">
		        	<@commonmacro.selectOneUser idObjectId="applyUserId" nameObjectId="userName" width=400 height=300>
						<input type="hidden" id="applyUserId" name="officeTeacherLeave.applyUserId" value="${officeTeacherLeave.applyUserId!}"/> 
						<input type="text" id="userName" name="officeTeacherLeave.applyUserName" notNull="true" msgName="申请人" value="${officeTeacherLeave.applyUserName!}" class="input-txt fn-left" style="width:200px;" readonly="readonly"/>
			  		</@commonmacro.selectOneUser>
		        </td>
		    </tr>
		     <tr>
		        <th style="width:20%"><span class="c-orange mr-5">*</span>请假类型：</th>
		        <td colspan="3" style="width:80%">
		        	<#if mcodedetails?exists &&mcodedetails?size gt 0>
		        	<#list mcodedetails as mcode>
		       		 <span class="ui-radio <#if mcode.thisId?default('')==officeTeacherLeave.leaveType?default('')>ui-radio-current</#if>"  data-name="a"><input <#if mcode.thisId?default('')==officeTeacherLeave.leaveType?default('')>checked="checked"</#if> type="radio"  class="radio" name="officeTeacherLeave.leaveType" value="${mcode.thisId!}">${mcode.content!}</span>
	        	 	</#list>
	        	 	</#if>
	        	 	<span id="errorType" class="field_tip input-txt-warn-tip"></span>
	        	 	<#-- <@htmlmacro.select style="width:40%;" valName="officeTeacherLeave.leaveType" valId="leaveType" msgName="请假类型" myfunchange="">
						${appsetting.getMcode("DM-QJLX").getHtmlTag(officeTeacherLeave.leaveType?default(1)?string,false)}
					</@htmlmacro.select>-->
		        </td>
		    </tr>
		    <tr>
		        <th><span class="c-orange mr-5">*</span>请假原因：</th>
		        <td colspan="3">
		        	<textarea name="officeTeacherLeave.leaveReason" id="leaveReason" cols="70" rows="4" class="text-area my-5" style="width:80%;padding:5px 1%;height:50px;" msgName="请假原因" notNull="true" maxLength="100">${officeTeacherLeave.leaveReason!}</textarea>
		        </td>
		    </tr>
		    <tr>
		        <th>通知人员：</th>
		        <td colspan="3">
		        	<@commonmacro.selectMoreUser idObjectId="noticePersonIds" nameObjectId="noticePersonNames" width=400 height=300>
			        	<input type="hidden" id="noticePersonIds" name="officeTeacherLeave.noticePersonIds" value="${officeTeacherLeave.noticePersonIds!}"/>
			        	<textarea id="noticePersonNames" name="officeTeacherLeave.noticePersonNames" cols="70" rows="4" class="text-area my-5" style="width:80%;padding:5px 1%;height:50px;" msgName="通知人员" maxLength="100">${officeTeacherLeave.noticePersonNames!}</textarea>
		        	</@commonmacro.selectMoreUser>
		        </td>
		    </tr>
		    <tr>
	    	   <th colspan="1" >
	    	   		<span class="c-orange mr-5">*</span>
	    	   		请假流程选择：
	    	   </th>
	    	   <td colspan="3">
		    	   <div class="query-part fn-rel fn-clear promt-div  flowDiv">
					<#if flowList?exists && flowList?size gt 0>
			        	<@htmlmacro.select style="width:260px;" valName="officeTeacherLeave.flowId" valId="flowId" myfunchange="flowChange" msgName="流程选择"notNull='true'>
							<a val="" ><span>--请选择--</span></a>
								<#list flowList as flow>
									<a val="${flow.flowId!}" <#if officeTeacherLeave.flowId?exists && officeTeacherLeave.flowId==flow.flowId>class="selected"</#if> dataEasyLevel="${flow.easyLevel?default('0')}" flowUnitId="${flow.ownerId!}" flowOwnerType="${flow.ownerType!}"><span>${flow.flowName!}</span></a>
								</#list>
						</@htmlmacro.select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<#else>&nbsp;请管理员添加流程!
					</#if>
			        <#-- <a class="abtn-blue" href="javascript:void(0);" onclick="goModuleFlow('${module.url!}','${module.subsystem}','${module.id}','${module.parentid}','${module.name}','subsystem','0',true); return false;">办公流程跳转</a>-->
			        </div>
	    	   </td>
		    </tr>
		        <#-- <tr><th>附件：</th>
		        <td colspan="3">
		        	<#if officeTeacherLeave.attachments?exists && officeTeacherLeave.attachments?size gt 0 >
		        		<input id="uploadContentFileInput" name="" type="text" class="input-txt input-readonly" readonly="readonly" style="width:200px;" value="<#if officeTeacherLeave.attachments.get(0)?exists>${officeTeacherLeave.attachments.get(0).fileName!}</#if>" maxLength="125"/>&nbsp;&nbsp;
		        	<#else>
		        		<input id="uploadContentFileInput" name="" type="text" class="input-txt input-readonly" readonly="readonly" style="width:200px;" value="" maxLength="125"/>&nbsp;&nbsp;
		        	</#if>
		        	<span class="upload-span1 "><a href="javascript:void(0);" class="">上传附件</a></span>       
					<input style="display:none" id="uploadContentFile" name="uploadContentFile" hidefocus type="file" onchange="uploadContent(this);" value="" >
		        </td> </tr>-->
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
			
			var leaveUnitId = $("#unitId").val();
			var flowUnitId = $(".flowDiv .selected").attr("flowUnitId");
			var flowOwnerType = $(".flowDiv .selected").attr("flowOwnerType");
			if(flowOwnerType == "0"){
				if(leaveUnitId == flowUnitId){
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
		}
		
		$(document).ready(function(){
			vselect();
			resetFilePos();
			var flowId= $("#flowId").val();
			
			var leaveUnitId = $("#unitId").val();
			var flowUnitId = $(".flowDiv .selected").attr("flowUnitId");
			var flowOwnerType = $(".flowDiv .selected").attr("flowOwnerType");
			if(flowOwnerType == "0"){
				if(leaveUnitId == flowUnitId){
					$("#changeFlowDiv").show();
				}else{
					$("#changeFlowDiv").hide();
				}
			}
			else{
				$("#changeFlowDiv").show();
			}
			
			load("#flowShow","${request.contextPath}/jbmp/editor/wfPreview.action?subsystemId=70&id="+flowId);
			clickChangeBegins();
			<#assign times=officeTeacherLeave.times>
				var i=0;
			<#if times?exists&&times?size gt 0>
				<#list times as time>
					$("#detailTime"+i).val(${time!});
					var des=$("#idd"+i).val();
					var dess=des+"_"+${time!};
					$("#id"+i).val(dess);
					i++;
				</#list>
			</#if>
		});
		function clickChangeBegin() {  
			var startDate = $("#leaveBeignTime").val();
			var endDate = $("#leaveEndTime").val();
			if(startDate!=null&&startDate!="" && endDate!=null&&endDate!=""){
				if(startDate>endDate){
					showMsgError('开始时间不能大于结束时间');
					return;
				}
				
				var num = countTimeLength('D',startDate,endDate);
				$("#days").val(parseInt(num)+1);
				
				var dt1 = new Date(startDate.replace(/-/g, "/"));  
		    	var dt2 = new Date(endDate.replace(/-/g, "/"));
				if(dt1.getMonth() != dt2.getMonth()){
					var trIndex=0;
					var c =getD(startDate,endDate);
					var chr="<tr id='moreD'>";
					chr=chr+"<th style='width:20%'><span class='c-orange mr-5'>*</span>请假时间明细：</th>";
					chr=chr+"<td colspan='3'>";
					for(i=0;i<c.length;i++){
						var desTime=c[i];
						var dy=desTime.substr(0,4);
						var dm=+desTime.substr(4,2);
						chr=chr+"&nbsp;<span style='margin-left:5px;'>"+dy+"年"+dm+"月"+"</span>";
						chr=chr+"<input type='hidden' id='idd"+trIndex+"' value='"+desTime+"'/>";
						chr=chr+"<input type='hidden' id='id"+trIndex+"' name='detailTime' value='"+desTime+"'/>";
						chr=chr+"<input name='' id='detailTime"+trIndex+"' class='input-txt' style='width:40px;' value='' notNull='true' msgName='时间' maxlength='4' dataType='float' maxValue='31' minValue='0.1' decimalLength='1' regexMsg='请输入正确的天数,并且不能超过31天' onblur='test("+trIndex+")'/>天";
						
						trIndex++;
					}
						chr=chr+"</td>";
						chr=chr+"</tr>";
						$("#oneD").remove();
						$("#moreD").remove();
						$("#teat").after(chr);
				}else{
					var trIndex=0;
					var c =getD(startDate,endDate);
					var chr="<tr style='display:none;' id='oneD'>";
					chr=chr+"<td>";
					for(i=0;i<c.length;i++){
						var desTime=c[i];
						chr=chr+"<input type='hidden' id='idd"+trIndex+"' value='"+desTime+"'/>";
						chr=chr+"<input type='hidden' id='id"+trIndex+"' name='detailTime' value='"+desTime+"_"+(parseInt(num)+1)+"'/>";
						trIndex++;
					}
						chr=chr+"</td>";
						chr=chr+"</tr>";
						$("#oneD").remove();
						$("#moreD").remove();
						$("#teat").after(chr);
				}
			}
		}
		function clickChangeBegins() {  
			var startDate = $("#leaveBeignTime").val();
			var endDate = $("#leaveEndTime").val();
			if(startDate!=null&&startDate!="" && endDate!=null&&endDate!=""){
				if(startDate>endDate){
					showMsgError('开始时间不能大于结束时间');
					return;
				}
				
				var num = countTimeLength('D',startDate,endDate);
				
				var dt1 = new Date(startDate.replace(/-/g, "/"));  
		    	var dt2 = new Date(endDate.replace(/-/g, "/"));
				if(dt1.getMonth() != dt2.getMonth()){
					var trIndex=0;
					var c =getD(startDate,endDate);
					var chr="<tr id='moreD'>";
					chr=chr+"<th style='width:20%'><span class='c-orange mr-5'>*</span>请假时间明细：</th>";
					chr=chr+"<td colspan='3'>";
					for(i=0;i<c.length;i++){
						var desTime=c[i];
						var dy=desTime.substr(0,4);
						var dm=+desTime.substr(4,2);
						chr=chr+"&nbsp;<span style='margin-left:5px;'>"+dy+"年"+dm+"月"+"</span>";
						chr=chr+"<input type='hidden' id='idd"+trIndex+"' value='"+desTime+"'/>";
						chr=chr+"<input type='hidden' id='id"+trIndex+"' name='detailTime' value='"+desTime+"'/>";
						chr=chr+"<input name='' id='detailTime"+trIndex+"' class='input-txt' style='width:40px;' value='' notNull='true' msgName='时间' maxlength='4' dataType='float' maxValue='31' minValue='0.1' decimalLength='1' regexMsg='请输入正确的天数,并且不能超过31天' onblur='test("+trIndex+")'/>天";
						
						trIndex++;
					}
						chr=chr+"</td>";
						chr=chr+"</tr>";
						$("#oneD").remove();
						$("#moreD").remove();
						$("#teat").after(chr);
				}else{
					var trIndex=0;
					var c =getD(startDate,endDate);
					var chr="<tr style='display:none;' id='oneD'>";
					chr=chr+"<td>";
					for(i=0;i<c.length;i++){
						var desTime=c[i];
						chr=chr+"<input type='hidden' id='idd"+trIndex+"' value='"+desTime+"'/>";
						chr=chr+"<input type='hidden' id='id"+trIndex+"' name='detailTime' value='"+desTime+"_"+(parseInt(num)+1)+"'/>";
						trIndex++;
					}
						chr=chr+"</td>";
						chr=chr+"</tr>";
						$("#oneD").remove();
						$("#moreD").remove();
						$("#teat").after(chr);
				}
			}
		}
		function clickChangeEnd() {  
			var startDate = $("#leaveBeignTime").val();
			var endDate = $("#leaveEndTime").val();
			if(startDate!=null&&startDate!="" && endDate!=null&&endDate!=""){
				if(startDate>endDate){
					showMsgError('开始时间不能大于结束时间');
					return;
				}
				
				var num = countTimeLength('D',startDate,endDate);
				$("#days").val(parseInt(num)+1);
				
				var dt1 = new Date(startDate.replace(/-/g, "/"));  
		    	var dt2 = new Date(endDate.replace(/-/g, "/"));
				if(dt1.getMonth() != dt2.getMonth()){
					var trIndex=0;
					var c =getD(startDate,endDate);
					var chr="<tr id='moreD'>";
					chr=chr+"<th style='width:20%'><span class='c-orange mr-5'>*</span>请假时间明细：</th>";
					chr=chr+"<td colspan='3'>";
					for(i=0;i<c.length;i++){
						var desTime=c[i];
						var dy=desTime.substr(0,4);
						var dm=+desTime.substr(4,2);
						chr=chr+"&nbsp;<span style='margin-left:5px;'>"+dy+"年"+dm+"月"+"</span>";
						chr=chr+"<input type='hidden' id='idd"+trIndex+"' value='"+desTime+"'/>";
						chr=chr+"<input type='hidden' id='id"+trIndex+"' name='detailTime' value='"+desTime+"'/>";
						chr=chr+"<input name='' id='detailTime"+trIndex+"' class='input-txt' style='width:40px;' value='' notNull='true' msgName='时间' maxlength='4' dataType='float' maxValue='31' minValue='0.1' decimalLength='1' regexMsg='请输入正确的天数,并且不能超过31天' onblur='test("+trIndex+")'/>天";
						
						trIndex++;
					}
						chr=chr+"</td>";
						chr=chr+"</tr>";
						$("#oneD").remove();
						$("#moreD").remove();
						$("#teat").after(chr);
				}else{
					var trIndex=0;
					var c =getD(startDate,endDate);
					var chr="<tr style='display:none;' id='oneD'>";
					chr=chr+"<td>";
					for(i=0;i<c.length;i++){
						var desTime=c[i];
						chr=chr+"<input type='hidden' id='idd"+trIndex+"' value='"+desTime+"'/>";
						chr=chr+"<input type='hidden' id='id"+trIndex+"' name='detailTime' value='"+desTime+"_"+(parseInt(num)+1)+"'/>";
						trIndex++;
					}
						chr=chr+"</td>";
						chr=chr+"</tr>";
						$("#oneD").remove();
						$("#moreD").remove();
						$("#teat").after(chr);
				}
			}
		}
		
		function test(obj){
			var  time=$("#idd"+obj).val();
			var lala=$("#detailTime"+obj).val();
			var desTime=time+"_"+lala
			$("#id"+obj).val(desTime);
		}
		
		function getD(a,b){
			var arrA = a.split("-"),
			arrB = b.split("-"),
			yearA = arrA[0],
			yearB = arrB[0],
			monthA = +arrA[1],
			monthB = (yearB-(+yearA))*12+parseInt(arrB[1]),
			rA = [],
			rB = [];
			do{
				do{
						rA.push(yearA+""+(+monthA > 9 ? monthA : "0"+monthA));
						//rB.push(yearA+"年"+monthA+"月");
						if(monthA == 12){
						monthA=1;
						monthB -= 12;
						break;
				   }
				}while(monthB > monthA++)
			}while(yearB > yearA++)
			return rA;
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
		function checkType(){
			$("#errorType").text("");
			if($(".ui-radio.ui-radio-current").attr("data-name")==undefined){
				$("#errorType").text("请假类型 不能为空！");
				return false;
			}
			$("#errorType").text("");
			return true;
		}
		function back(){
			load("#showListDiv","${request.contextPath}/office/teacherLeave/teacherLeave-applyList.action");
		}
		var isSubmit =false;
		function doSave(){
			if(isSubmit){
				return;
			}
			if(!checkAllValidate("#teacherLeaveForm")){
				if(!checkType()){
					return;
				}
				return;
			}
			if(!checkType()){
					return;
				}
			if(!checkAfterDate($("#leaveBeignTime").get(0),$("#leaveEndTime").get(0))){
				return;
			}
			
			var startDate = $("#leaveBeignTime").val();
			var endDate = $("#leaveEndTime").val();
			var c =getD(startDate,endDate);
			var days=$("#days").val();
			if(startDate!=null&&startDate!="" && endDate!=null&&endDate!=""){
				if(startDate>endDate){
					showMsgError('开始时间不能大于结束时间');
					return;
				}
				
				var dt1 = new Date(startDate.replace(/-/g, "/"));  
		    	var dt2 = new Date(endDate.replace(/-/g, "/"));
				if(dt1.getMonth()==dt2.getMonth()){
					var trIndex=0;
					for(k=0;k<c.length;k++){
						var desTime=c[k];
						$("#id"+trIndex).val(desTime+"_"+days);
						trIndex++;
					}
				}
			}
			
			
			//var startDate = $("#leaveBeignTime").val();
			//var endDate = $("#leaveEndTime").val();
			//var c =getD(startDate,endDate);
			var mytime=new Array();
			for(i=0;i<c.length;i++){
				var desTime=$("#detailTime"+i).val();
				mytime[i]=desTime;
			}
			var titalTime=0;
			for(i=0;i<mytime.length;i++){
				titalTime+=parseInt(mytime[i]);
			}
			//var days=$("#days").val();
			if(titalTime>days){
				showMsgError("请假详细时间不能大于总时间");
				return;
			}
			
			var flowId= $("#flowId").val();
			if(!flowId||flowId==""){
				 showMsgError("请选择一个合适的审批流程");
				 return;
			}
			var options = {
		       url:'${request.contextPath}/office/teacherLeave/teacherLeave-saveTeacherLeave.action', 
		       success : showReply,
		       dataType : 'json',
		       clearForm : false,
		       resetForm : false,
		       type : 'post'
		    };
		    isSubmit = true;
		    $('#teacherLeaveForm').ajaxSubmit(options);
		}
		
		function doSubmit(){
			var flowId= $("#flowId").val();
			if(!flowId||flowId==""){
				 showMsgError("要提交审核,必须选择一个流程");
				 return;
			}
			if(!checkAfterDate($("#leaveBeignTime").get(0),$("#leaveEndTime").get(0))){
				return;
			};
			
			var startDate = $("#leaveBeignTime").val();
			var endDate = $("#leaveEndTime").val();
			var c =getD(startDate,endDate);
			var days=$("#days").val();
			if(startDate!=null&&startDate!="" && endDate!=null&&endDate!=""){
				if(startDate>endDate){
					showMsgError('开始时间不能大于结束时间');
					return;
				}
				
				var dt1 = new Date(startDate.replace(/-/g, "/"));  
		    	var dt2 = new Date(endDate.replace(/-/g, "/"));
				if(dt1.getMonth()==dt2.getMonth()){
					var trIndex=0;
					for(k=0;k<c.length;k++){
						var desTime=c[k];
						$("#id"+trIndex).val(desTime+"_"+days);
						trIndex++;
					}
				}
			}
			
			
			//var startDate = $("#leaveBeignTime").val();
			//var endDate = $("#leaveEndTime").val();
			//var c =getD(startDate,endDate);
			var mytime=new Array();
			for(i=0;i<c.length;i++){
				var desTime=$("#detailTime"+i).val();
				mytime[i]=desTime;
			}
			var titalTime=0;
			for(i=0;i<mytime.length;i++){
				titalTime+=parseInt(mytime[i]);
			}
			//var days=$("#days").val();
			if(titalTime>days){
				showMsgError("请假详细时间不能大于总时间");
				return;
			}
			
			if(isSubmit){
				return;
			}
			if(!checkAllValidate("#teacherLeaveForm")){
				if(!checkType()){
					return;
				}
				return;
			}
			if(!checkType()){
					return;
			}
			var options = {
		       url:'${request.contextPath}/office/teacherLeave/teacherLeave-submitTeacherLeave.action', 
		       success : showReply,
		       dataType : 'json',
		       clearForm : false,
		       resetForm : false,
		       type : 'post'
		    };
		    isSubmit = true;
		    $('#teacherLeaveForm').ajaxSubmit(options);
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
	function goModuleFlow(url,subsystem,moduleId,parentId,moduleName,source,listType,isGoFlow){
		if(isGoFlow){
			if(showConfirm("是否保存请假申请信息？")){
				if(isSubmit){
					return;
				}
				if(!checkAllValidate("#teacherLeaveForm")){
					return;
				}
				if(!checkAfterDate($("#leaveBeignTime").get(0),$("#leaveEndTime").get(0))){
					return;
				}
				var flowId= $("#flowId").val();
				if(!flowId||flowId==""){
					 showMsgError("请选择一个合适的审批流程");
					 return;
				}
				var options = {
			       url:'${request.contextPath}/office/teacherLeave/teacherLeave-saveTeacherLeave.action', 
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
			    $('#teacherLeaveForm').ajaxSubmit(options);
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
		function doChangeFlow(){
			if(showConfirm("确定保存请假信息并进入流程修改？")){
				if(isSubmit){
					return;
				}
				if(!checkAllValidate("#teacherLeaveForm")){
					return;
				}
				
				var startDate = $("#leaveBeignTime").val();
				var endDate = $("#leaveEndTime").val();
				var c =getD(startDate,endDate);
				var days=$("#days").val();
				if(startDate!=null&&startDate!="" && endDate!=null&&endDate!=""){
					if(startDate>endDate){
					showMsgError('开始时间不能大于结束时间');
					return;
				}
				
				var dt1 = new Date(startDate.replace(/-/g, "/"));  
		    	var dt2 = new Date(endDate.replace(/-/g, "/"));
				if(dt1.getMonth()==dt2.getMonth()){
					var trIndex=0;
					for(k=0;k<c.length;k++){
						var desTime=c[k];
						$("#id"+trIndex).val(desTime+"_"+days);
						trIndex++;
					}
				}
			}
				
				
				
				if(!checkAfterDate($("#leaveBeignTime").get(0),$("#leaveEndTime").get(0))){
					return;
				}
				var flowId= $("#flowId").val();
				if(!flowId||flowId==""){
					 showMsgError("请选择一个合适的审批流程");
					 return;
				}
				var options = {
			       url:'${request.contextPath}/office/teacherLeave/teacherLeave-saveTeacherLeave.action', 
			       success : showReply1,
			       dataType : 'json',
			       clearForm : false,
			       resetForm : false,
			       type : 'post'
			    };
			    isSubmit = true;
			    $('#teacherLeaveForm').ajaxSubmit(options);
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
					var leaveId = data.errorMessage;
					$("#id").val(leaveId);
				  	openNew(leaveId);
				});
				return;
			}
		}
		
		function openNew(leaveId){
			var flowId = $("#flowId").val();
			var easyLevel = $(".flowDiv .selected").attr("dataEasyLevel");
			formUrl = '${request.contextPath}/jbmp/editor/wfEdit.action';
			businessType = '7001';
			operation = 'start';
			instanceType ='model';
			id = flowId;  
			actionUrl ='${request.contextPath}/office/teacherLeave/teacherLeave-changeFlow.action?leaveId='+leaveId;
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