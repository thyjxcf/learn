<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="">
<#include "/common/handlefielderror.ftl">
<#import "/common/commonmacro.ftl" as commonmacro>
<#import "../teacherLeave/archiveWebuploader.ftl" as archiveWebuploader>
	<br>
    <@htmlmacro.tableDetail divClass="table-form">
		<tr>
	        <th colspan="4" style="text-align:center;">教师请假</th>
	    </tr>
	    <tr id="teat">
	        <th><span class="c-orange mr-5">*</span>请假时间：</th>
		        <td colspan="3" >
		        	<@htmlmacro.datepicker name="officeTeacherLeave.leaveBeignTime" id="leaveBeignTime" class="input-txt" style="width:39%;" readonly="true" msgName="开始时间" notNull="true" onpicked="clickChangeBegin" value="${((officeTeacherLeave.leaveBeignTime)?string('yyyy-MM-dd'))?if_exists}" />
		        	至
		        	<@htmlmacro.datepicker name="officeTeacherLeave.leaveEndTime" id="leaveEndTime" class="input-txt" style="width:39%;" readonly="true" msgName="结束时间" notNull="true" onpicked="clickChangeEnd" value="${((officeTeacherLeave.leaveEndTime)?string('yyyy-MM-dd'))?if_exists}" />
		        </td>
	    </tr>
	    <tr>
	       <th style="width:20%"><span class="c-orange mr-5">*</span>共计天数：</th>
	       <td style="width:30%">
	        	${officeTeacherLeave.days?string('0.#')}天
	       </td>
	        <th style="width:20%">申请人：</th>
	        <td style="width:30%">
	        	${officeTeacherLeave.applyUserName!}
	        </td>
	    </tr>
	     <tr>
	        <th style="width:20%">请假类型：</th>
	        <td colspan="3" style="width:80%">
	        ${appsetting.getMcode("DM-QJLX").get(officeTeacherLeave.leaveType?default(''))}
	        </td>
	    </tr>
	     <tr>
	        <th>请假原因：</th>
	        <td colspan="3">
	        ${officeTeacherLeave.leaveReason!}
	        </td>
	    </tr>
	    <tr>
	        <th>通知人员：</th>
	        <td colspan="3">
	        ${officeTeacherLeave.noticePersonNames!}
	        </td>
	    </tr>
	     <tr>
	        <th style="width:20%">提交时间：</th>
	        <td style="width:30%">
	        ${(officeTeacherLeave.createTime?string('yyyy-MM-dd'))?if_exists}
	        </td>
	        <th style="width:20%">提交人：</th>
	        <td style="width:30%">
	        ${officeTeacherLeave.createUserName!}
	        </td>
	    </tr>
	   <@archiveWebuploader.archiveWebuploaderEditViewer canEdit=false showAttachmentDivId='showAttDiv' editContentDivId='editContentDiv' isSend=true loadDiv=false /> 
	</@htmlmacro.tableDetail>
    <#if officeTeacherLeave.flowId?default('')!='1'>
    <br>
	<div class="fw-edit mt-10">
    	<p class="tt">审核意见</p>
        <div class="fw-item-wrap">
        	<#if (officeTeacherLeave.hisTaskList?size>0)>
        	<#list officeTeacherLeave.hisTaskList as item>
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
		                </#if>
	                </div>
	                <p class="date">${((item.comment.operateTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}</p>
                </div>
        	</#list>
        	</#if>
        	<div class="fw-item fn-clear">
                <p class="tit fn-clear">
                	<span class="num">${officeTeacherLeave.hisTaskList?size+1}</span>
                	<span class="pl-5">${officeTeacherLeave.taskName!}</span>
            	</p>
                <p class="name">负责人：${userName!}</p>
                <div class="fn-clear"></div>
                <p><textarea class="txt" name="textComment" id="textComment"  maxLength="200">${textComment!}</textarea></p>
            </div>
        </div>
    </div>
    </#if>
	<p class="pt-20 t-center">
		<a href="javascript:void(0)" class="abtn-blue" onclick="changeFlow()">修改当前流程</a>
		<#if canChangeNextTask>
			<a href="javascript:void(0)" class="abtn-blue" onclick="changeNextStepUserDiv()">修改下一步负责人</a>
		</#if>
		<a href="javascript:void(0)" class="abtn-blue" onclick="passFlow()">审核通过</a>
		<a href="javascript:void(0)" class="abtn-blue" onclick="unpassFlow()">审核不通过</a>
		<#if canBeRetract>
			<a href="javascript:void(0)" class="abtn-blue" onclick="retractFlow('${showReBackId!}','${taskKey!}','${reTaskId!}')">撤回</a>
		</#if>
    	<a href="javascript:void(0)" class="abtn-blue" onclick="goBack()">返回</a>
	</p>
	<iframe name="downloadFrame" id="downloadFrame" style="display:none;"></iframe>
	<form name="teacherLeaveForm" id="teacherLeaveForm" method="post">
		<input type="hidden" name="taskHandlerSaveJson" id="taskHandlerSaveJson" value=""/>
		<input type="hidden" name="pass" id="pass" value=""/>
	</form>
	<div id="flowShow" class="docReader my-20" style="height:660px;">
	</div>
	<div  id="classLayer" class="popUp-layer showSgParam" style="display:none;width:60%"></div>
	<div id="nextStepLayer" style="display:none;width:980px;z-index:9997" class="popUp-layer "></div>
<script>
$(document).ready(function(){
	vselect();
    load("#flowShow","${request.contextPath}/jbmp/editor/wfPreview.action?id=${flowId!}&subsystemId=70&instanceType=instance&currentStepId=${currentStepId!}");
    clickChangeBegin();
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
function doDownload(url){
	document.getElementById('downloadFrame').src=url;
}
function goBack(){
	load("#showListDiv","${request.contextPath}/office/teacherLeave/teacherLeave-auditList.action");
}

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
						chr=chr+"<input name='' id='detailTime"+trIndex+"' class='input-txt' style='width:40px;' value='' msgName='时间' maxlength='4' readonly='true' dataType='float' maxValue='31' minValue='0.1' decimalLength='1' regexMsg='请输入正确的天数,并且不能超过31天'/>";
						
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


var taskHandlerSave = ${taskHandlerSaveJson!};
isSubmit = false;
function passFlow() {
 	if (isSubmit) {
        return;
    }
 	if(!checkAllValidate("#teacherLeaveForm")){
		return;
	}
	var textComment=$("#textComment").val().trim();
	if(textComment==null||textComment==''){
		$("#textComment").val("同意");
	}
	
	$("#pass").val('true');
	submitForm();
}
function unpassFlow(){
	if (isSubmit) {
        return;
    }
    if(!checkAllValidate("#teacherLeaveForm")){
		return;
	}
	var textComment=$("#textComment").val();
	if(textComment==null||textComment==''){
		showMsgWarn("请填写审核不通过的原因！");
	  		return;
	}
	$("#pass").val('false');
	submitForm();
}
function submitForm() {
    taskHandlerSave.comment.textComment = $("#textComment").val();
    taskHandlerSave.comment.commentType = 1;
    $("#taskHandlerSaveJson").val(JSON.stringify(taskHandlerSave));
    var options = {
        url: '${request.contextPath}/office/teacherLeave/teacherLeave-auditPassLeave.action?id=${officeTeacherLeave.id!}&currentStepId=${currentStepId!}',
        success: showReply,
        dataType: 'json',
        clearForm: false,
        resetForm: false,
        type: 'post'
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
		  	goBack();
		});
		return;
	}
}

function changeFlow(){
	openDiv("#classLayer","#classLayer .close,#classLayer .reset",
 	"${request.contextPath}/office/teacherLeave/teacherLeave-findCurrentstep.action?leaveId=${officeTeacherLeave.id!}&taskId=${taskId!}", null, null ,null,function(){closeChange()},null);
}

function reload(id, taskId){
	load("#showListDiv","${request.contextPath}/office/teacherLeave/teacherLeave-auditTeacherLeave.action?id="+id+"&taskId="+taskId);
}

function retractFlow(showReBackId, taskKey, reTaskId){
	$.getJSON("${request.contextPath}/office/teacherLeave/teacherLeave-retractFlow.action", {showReBackId:showReBackId,taskKey:taskKey,reTaskId:reTaskId}, function(data){
		if (!data.operateSuccess) {
	        if (data.errorMessage != null && data.errorMessage != "") {
	            showMsgError(data.errorMessage);
	            return;
	        }
	    } else {
	    	showMsgSuccess("撤回成功", "", goBack);
	        return;
	    }
	}).error(function(XMLHttpRequest, textStatus, errorThrown){alert(errorThrown);});
}

function changeNextStepUserDiv(){
	openDiv("#nextStepLayer","#nextStepLayer .close,#nextStepLayer .reset",
 		"${request.contextPath}/office/common/loadNextStepTaskLayer.action?taskId=${taskId!}&flowId=${officeTeacherLeave.flowId!}", null, null ,null,function(){vselect();},null);
}

</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>
