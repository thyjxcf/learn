<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<script type="text/javascript" src="${request.contextPath}/static/jbmp/editor/js/flow.js"></script>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/boxy/boxy.css"/>
<script type="text/javascript" src="${request.contextPath}/static/boxy/jquery.boxy.js"></script>
<@htmlmacro.moduleDiv titleName="报销申请">
<form name="editForm" id="editForm" method="post" enctype="multipart/form-data">
	<input id="id" name="officeAttendLecture.id" value="${officeAttendLecture.id!}" type="hidden" />
	<input id="unitId" name="officeAttendLecture.unitId" value="${officeAttendLecture.unitId!}" type="hidden" />
	<input id="applyUserId" name="officeAttendLecture.applyUserId" value="${officeAttendLecture.applyUserId!}" type="hidden" />
	<@htmlmacro.tableDetail divClass="table-form">
		<tr>
	        <th colspan="4" style="text-align:center;" class="p1 pt-10">听课登记</th>
	    </tr>
	    <tr>
	       <th style="width:20%"><span class="c-orange mr-5">*</span>听课时间：</th>
	       <td style="width:30%">
	      		<#if viewOnly>
	      		<@htmlmacro.datepicker readonly="readonly" style="width:80%;" id="attendDate" name="officeAttendLecture.attendDate" notNull="true" value="${((officeAttendLecture.attendDate)?string('yyyy-MM-dd'))?if_exists}"/>
	      		<#else>
	        	<@htmlmacro.datepicker style="width:80%;" msgName="听课时间" id="attendDate" name="officeAttendLecture.attendDate" notNull="true" minDate="${minDate?string('yyyy-MM-dd')}" maxDate="${.now}" value="${((officeAttendLecture.attendDate)?string('yyyy-MM-dd'))?if_exists}"/>
	      		</#if>
	       </td>
	        <th style="width:20%"><span class="c-orange mr-5">*</span>课次：</th>
	        <td style="width:30%">
	        	<#if viewOnly>
	        	<@htmlmacro.select className="ui-select-box-disable" style="width:40%;" valName="officeAttendLecture.attendPeriod" valId="attendPeriod" notNull="true">
					${appsetting.getMcode("DM-TKSD").getHtmlTag(officeAttendLecture.attendPeriod?default('1'),false)}
				</@htmlmacro.select>
				<@htmlmacro.select className="ui-select-box-disable" style="width:43%;" valName="officeAttendLecture.attendPeriodNum" valId="attendPeriodNum" notNull="true">
					${appsetting.getMcode("DM-TKJC").getHtmlTag(officeAttendLecture.attendPeriodNum?default('1'),false)}
				</@htmlmacro.select>
				<#else>
	        	<@htmlmacro.select style="width:40%;" valName="officeAttendLecture.attendPeriod" valId="attendPeriod" notNull="true">
					${appsetting.getMcode("DM-TKSD").getHtmlTag(officeAttendLecture.attendPeriod?default('1'),false)}
				</@htmlmacro.select>
				<@htmlmacro.select style="width:43%;" valName="officeAttendLecture.attendPeriodNum" valId="attendPeriodNum" notNull="true">
					${appsetting.getMcode("DM-TKJC").getHtmlTag(officeAttendLecture.attendPeriodNum?default('1'),false)}
				</@htmlmacro.select>
				</#if>
	        </td>
	    </tr>
	    <tr>
	    	<th><span class="c-orange mr-5"></span>类型：</th>
	        <td colspan="3">
	        	<#if viewOnly>
	        		<@htmlmacro.select className="ui-select-box-disable" style="width:20%;" valName="officeAttendLecture.type" valId="type" notNull="true" myfunchange="changeType">
						<a val="0" <#if officeAttendLecture.type?default(0) == 0>class="selected"</#if>><span>校内</span></a>
						<a val="1" <#if officeAttendLecture.type?default(0) == 1>class="selected"</#if>><span>校外</span></a>
					</@htmlmacro.select>
				<#else>
					<@htmlmacro.select style="width:20%;" valName="officeAttendLecture.type" valId="type" notNull="true" myfunchange="changeType">
						<a val="0" <#if officeAttendLecture.type?default(0) == 0>class="selected"</#if>><span>校内</span></a>
						<a val="1" <#if officeAttendLecture.type?default(0) == 1>class="selected"</#if>><span>校外</span></a>
					</@htmlmacro.select>
				</#if>
	        </td>
	    </tr>
	    <tr id="doChange">
			<th style="width:20%"><span class="c-orange mr-5"><#if officeAttendLecture.type?default(0) == 1><#else>*</#if></span>年级：</th>
			<td style="width:30%">
				<#if viewOnly>
					<#if officeAttendLecture.type?default(0) == 0>
					<@htmlmacro.select className="ui-select-box-disable" style="width:83%;" valName="officeAttendLecture.gradeId" valId="gradeId" notNull="true" myfunchange="changeGrade" msgName="年级">
					<a val="" ><span>请选择</span></a>
						<#if gradesList?exists && gradesList?size gt 0>
							<#list gradesList as item>
								<a val="${item.id!}"<#if officeAttendLecture.gradeId?default('') == item.id>class="selected"</#if>  ><span>${item.gradename!}</span></a>
							</#list>
						</#if>
					</@htmlmacro.select>
					<#else>
						<input type="text" id="gradeId" readonly="true" name="officeAttendLecture.gradeId" value="${officeAttendLecture.gradeId!}" class="input-txt fn-left" style="width:80%;" maxlength="30"/>
					</#if>
				<#else>
				<#if officeAttendLecture.type?default(0) == 0>
					<@htmlmacro.select style="width:83%;" valName="officeAttendLecture.gradeId" valId="gradeId" notNull="true" myfunchange="changeGrade" msgName="年级">
					<a val="" ><span>请选择</span></a>
						<#if gradesList?exists && gradesList?size gt 0>
							<#list gradesList as item>
								<a val="${item.id!}"<#if officeAttendLecture.gradeId?default('') == item.id>class="selected"</#if>  ><span>${item.gradename!}</span></a>
							</#list>
						</#if>
					</@htmlmacro.select>
				<#else>
					<input type="text" id="gradeId" name="officeAttendLecture.gradeId" value="${officeAttendLecture.gradeId!}" class="input-txt fn-left" style="width:80%;" maxlength="30"/>
				</#if>
				</#if>
			</td>
	        <th style="width:20%"><span class="c-orange mr-5"><#if officeAttendLecture.type?default(0) == 1><#else>*</#if></span>班级：</th>
	        <td style="width:30%">
	        	<#if viewOnly>
	        		<#if officeAttendLecture.type?default(0) == 0>
	        		<@htmlmacro.select className="ui-select-box-disable" style="width:83%;" valName="officeAttendLecture.classId" valId="classId" txtId="classTxt" notNull="true" optionDivName="itemClassIdOptionDiv" msgName="班级">
						<a val="" ><span>请选择</span></a>
					</@htmlmacro.select>
					<#else>
						<input type="text" readonly="true" id="classId" name="officeAttendLecture.classId" value="${officeAttendLecture.classId!}" class="input-txt fn-left" style="width:80%;" maxlength="30"/>
					</#if>
	        	<#else>
	        	<#if officeAttendLecture.type?default(0) == 0>
	        		<@htmlmacro.select style="width:83%;" valName="officeAttendLecture.classId" valId="classId" txtId="classTxt" notNull="true" optionDivName="itemClassIdOptionDiv" msgName="班级">
						<a val="" ><span>请选择</span></a>
					</@htmlmacro.select>
	        	<#else>
	        		<input type="text" id="classId" name="officeAttendLecture.classId" value="${officeAttendLecture.classId!}" class="input-txt fn-left" style="width:80%;" maxlength="30"/>
				</#if>
	        	</#if>
	        </td>
	    </tr>
	    <tr>
			<th style="width:20%"><span class="c-orange mr-5">*</span>学科：</th>
			<td style="width:30%">
				<input type="text" <#if viewOnly>readonly="readonly"</#if> id="subjectName" name="officeAttendLecture.subjectName" notNull="true" value="${officeAttendLecture.subjectName!}" class="input-txt fn-left" style="width:80%;" msgName="学科" maxlength="100"/>
			</td>
	        <th style="width:20%"><span class="c-orange mr-5">*</span>授课教师：</th>
	        <td style="width:30%">
	        	<input type="text" <#if viewOnly>readonly="readonly"</#if> id="teacherName" name="officeAttendLecture.teacherName" notNull="true" value="${officeAttendLecture.teacherName!}" class="input-txt fn-left" style="width:80%;" msgName="授课教师" maxlength="50"/>
	        </td>
	    </tr>
	    <tr>
	        <th><span class="c-orange mr-5">*</span>课程名称：</th>
	        <td colspan="3">
	        	<input type="text" <#if viewOnly>readonly="readonly"</#if> id="projectName" name="officeAttendLecture.projectName" notNull="true" value="${officeAttendLecture.projectName!}" class="input-txt fn-left" style="width:81%;" msgName="课题名称" maxlength="100"/>
	        </td>
	    </tr>
	    <tr>
	        <th><span class="c-orange mr-5">*</span>听课内容：</th>
	        <td colspan="3">
	        	<textarea <#if viewOnly>readonly="readonly"</#if> name="officeAttendLecture.projectContent" id="projectContent" cols="70" rows="4" class="text-area my-5" style="width:80%;padding:5px 1%;height:50px;" msgName="听课内容" notNull="true" maxLength="1000">${officeAttendLecture.projectContent!}</textarea>
	        </td>
	    </tr>
	    <tr>
	        <th>建议：</th>
	        <td colspan="3">
	        	<textarea <#if viewOnly>readonly="readonly"</#if> name="officeAttendLecture.projectOpinion" id="projectOpinion" cols="70" rows="4" class="text-area my-5" style="width:80%;padding:5px 1%;height:50px;" msgName="建议" maxLength="1000">${officeAttendLecture.projectOpinion!}</textarea>
	        </td>
	    </tr>
	    <#if !viewOnly>
	    <tr>
    	   <th colspan="1" >
    	   <span class="c-orange mr-5">*</span>
    	   		审核流程：
    	   </th>
    	   <td colspan="3">
    	   	<div class="query-part fn-rel fn-clear promt-div  flowDiv">
    	   	<@htmlmacro.select style="width:260px;" valName="officeAttendLecture.flowId" notNull="true" valId="flowId" myfunchange="flowChange" msgName="听课流程">
				<a val="" ><span>请选择</span></a>
				<#if flowList?exists && flowList?size gt 0>
					<#list flowList as flow>
						<a val="${flow.flowId!}" <#if officeAttendLecture.flowId?exists && officeAttendLecture.flowId==flow.flowId>class="selected"</#if> dataEasyLevel="${flow.easyLevel?default('0')}" flowUnitId="${flow.ownerId!}" flowOwnerType="${flow.ownerType!}"><span>${flow.flowName!}</span></a>
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
        	<#if (officeAttendLecture.hisTaskList?size>0)>
        	<#list officeAttendLecture.hisTaskList as item>
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
    </p>
</form>
<div id="flowShow"  class="docReader my-20" style="height:660px;">
	<p >请选择流程</p>
</div>
<input id="wf-actionUrl" value="" type="hidden" />
<input id="wf-taskHandlerSaveJson" value="" type="hidden" />
<script>
var selectedValue="";
$(document).ready(function(){
	vselect();
	<#if viewOnly>
	load("#flowShow","${request.contextPath}/jbmp/editor/wfPreview.action?subsystemId=70&id=${officeAttendLecture.flowId!}&instanceType=instance");
	<#else>
	var flowId= $("#flowId").val();
	load("#flowShow","${request.contextPath}/jbmp/editor/wfPreview.action?subsystemId=70&id="+flowId);
	</#if>
	selectedValue = '${officeAttendLecture.classId!}';
	<#if officeAttendLecture.type?default(0)==0>
 	changeGrade();
	</#if>
});
//流程选择
function flowChange(){
	var flowId= $("#flowId").val();
	var easyLevel = $(".flowDiv .selected").attr("dataEasyLevel");
	
	var attendLectureUnitId = $("#unitId").val();
	var flowUnitId = $(".flowDiv .selected").attr("flowUnitId");
	var flowOwnerType = $(".flowDiv .selected").attr("flowOwnerType");
	if(flowOwnerType == "0"){
		if(attendLectureUnitId == flowUnitId){
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

function doChangeFlow(){
	if(showConfirm("确定保存信息并进入流程修改？")){
		if(isSubmit){
			return;
		}
		if(!checkAllValidate("#editForm")){
			return;
		}
		
		var options = {
	       url:'${request.contextPath}/office/attendLecture/attendLecture-save.action?officeAttendLecture.state=1', 
	       success : showReply1,
	       dataType : 'json',
	       clearForm : false,
	       resetForm : false,
	       type : 'post'
	    };
	    isSubmit = true;
	    $('#editForm').ajaxSubmit(options);
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
			var attendLectureId = data.errorMessage;
			$("#id").val(attendLectureId);
		  	openNew(attendLectureId);
		});
		return;
	}
}

function openNew(attendLectureId){
	var flowId = $("#flowId").val();
	var easyLevel = $(".flowDiv .selected").attr("dataEasyLevel");
	formUrl = '${request.contextPath}/jbmp/editor/wfEdit.action';
	businessType = '7007';
	operation = 'start';
	instanceType ='model';
	id = flowId;  
	actionUrl ='${request.contextPath}/office/attendLecture/attendLecture-changeFlow.action?attendLectureId='+attendLectureId;
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

function changeType(){
	var lala=$("#type").val();
	if(lala==0){
		load("#doChange","${request.contextPath}/office/attendLecture/attendLecture-changeScoolIn.action");
	}else{
		load("#doChange","${request.contextPath}/office/attendLecture/attendLecture-changeSchoolOut.action");
	}
	//var url="${request.contextPath}/office/salarymanage/salarymanage-salaryManageList.action";
	//url += "?importId="+importId;
	//load("#doChange", url);
}

function changeGrade(){
	var gradeId = $("#gradeId").val();
	getClassItems(gradeId);
}
function getClassItems(gradeId,curVal){
	if(curVal){
		selectedValue=curVal;
	}
	$.ajax({
		type: "POST",
		url: "${request.contextPath}/office/attendLecture/attendLecture-findClassList.action",
		async:false,
		dataType:"json",
		data: $.param({"id":gradeId},true),
		success: assembelClassItemList,
		error: function(XMLHttpRequest, textStatus, errorThrown){alert(errorThrown);}
	});
}	
function assembelClassItemList(data){
	if (data.length>0) {
		var optionHtml="<div style='position:absolute;z-index:-1;width:100%;height:102%;'><iframe style='width:100%;height:100%;border:0;filter:alpha(opacity=0);-moz-opacity:0'></iframe></div>"
		optionHtml+="<div class='a-wrap'>";
		optionHtml+="<a val=''><span>请选择</span></a>";
		$.each(data,function(j,item){
			if(item.id == selectedValue){
				optionHtml+="<a val='"+item.id+"' class='selected'><span>"+item.classnamedynamic+"</span></a>";
			}else{
				optionHtml+="<a val='"+item.id+"'><span>"+item.classnamedynamic+"</span></a>";
			}
		});	
		optionHtml+="</div>";
		$("#classTxt").val('请选择');
		$("#classId").val('');
		$("#itemClassIdOptionDiv").html(optionHtml);

	} else {
		var optionHtml="<div style='position:absolute;z-index:-1;width:100%;height:102%;'><iframe style='width:100%;height:100%;border:0;filter:alpha(opacity=0);-moz-opacity:0'></iframe></div>"
		optionHtml+="<div class='a-wrap'>";
		optionHtml+="<a val=''><span>请选择</span></a>";
		optionHtml+="</div>";

		$("#itemClassIdOptionDiv").html(optionHtml);
		$("#classTxt").val('请选择');
		$("#classId").val('');
	}
	vselect();
	selectedValue="";
}
	
function back(){
	load("#showListDiv","${request.contextPath}/office/attendLecture/attendLecture-list.action?searchType=${searchType!}&startTime=${((startTime)?string('yyyy-MM-dd'))?if_exists}&endTime=${((endTime)?string('yyyy-MM-dd'))?if_exists}");
}
var isSubmit = false;
function doSave(){
	if(isSubmit){
		return;
	}
	isSubmit = true;
	if(!checkAllValidate("#editForm")){
		isSubmit = false;
		return;
	}
	
	var options = {
       url:'${request.contextPath}/office/attendLecture/attendLecture-save.action?officeAttendLecture.state=1', 
       success : showReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post'
    };
    isSubmit = true;
    $('#editForm').ajaxSubmit(options);
}

function doSubmit(){
	if(isSubmit){
		return;
	}
	isSubmit = true;
	if(!checkAllValidate("#editForm")){
		isSubmit = false;
		return;
	}
	
	var options = {
       url:'${request.contextPath}/office/attendLecture/attendLecture-save.action?officeAttendLecture.state=2', 
       success : showReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post'
    };
    $('#editForm').ajaxSubmit(options);
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

function retractFlow(showReBackId, taskKey, reTaskId){
	$.getJSON("${request.contextPath}/office/attendLecture/attendLecture-retractFlow.action", {showReBackId:showReBackId,taskKey:taskKey,reTaskId:reTaskId}, function(data){
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