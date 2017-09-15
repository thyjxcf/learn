<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="报销申请">
<form name="editForm" id="editForm" method="post" enctype="multipart/form-data">
	<input id="id" name="officeAttendLecture.id" value="${officeAttendLecture.id!}" type="hidden" />
	<input id="unitId" name="officeAttendLecture.unitId" value="${officeAttendLecture.unitId!}" type="hidden" />
	<input id="applyUserId" name="officeAttendLecture.applyUserId" value="${officeAttendLecture.applyUserId!}" type="hidden" />
	<input type="hidden" name="taskHandlerSaveJson" id="taskHandlerSaveJson" value=""/>
	<@htmlmacro.tableDetail divClass="table-form">
		<tr>
	        <th colspan="4" style="text-align:center;" class="p1 pt-10">听课登记</th>
	    </tr>
	    <tr>
	       <th style="width:20%"><span class="c-orange mr-5">*</span>听课时间：</th>
	       <td style="width:30%">
	        	<@htmlmacro.datepicker readonly="readonly" style="width:80%;" id="attendDate" name="officeAttendLecture.attendDate" notNull="true" value="${((officeAttendLecture.attendDate)?string('yyyy-MM-dd'))?if_exists}"/>
	       </td>
	        <th style="width:20%"><span class="c-orange mr-5">*</span>课次：</th>
	        <td style="width:30%">
	        	<@htmlmacro.select className="ui-select-box-disable" style="width:40%;" valName="officeAttendLecture.attendPeriod" valId="attendPeriod" notNull="true">
					${appsetting.getMcode("DM-TKSD").getHtmlTag(officeAttendLecture.attendPeriod?default('1'),false)}
				</@htmlmacro.select>
				<@htmlmacro.select className="ui-select-box-disable" style="width:43%;" valName="officeAttendLecture.attendPeriodNum" valId="attendPeriodNum" notNull="true">
					${appsetting.getMcode("DM-TKJC").getHtmlTag(officeAttendLecture.attendPeriodNum?default('1'),false)}
				</@htmlmacro.select>
	        </td>
	    </tr>
	    <tr>
	    	<th><span class="c-orange mr-5"></span>类型：</th>
	        <td colspan="3">
	        	<@htmlmacro.select className="ui-select-box-disable" style="width:20%;" valName="officeAttendLecture.type" valId="type" notNull="true" myfunchange="changeType">
					<a val="0" <#if officeAttendLecture.type?default(0) ==0>class="selected"</#if>><span>校内</span></a>
					<a val="1" <#if officeAttendLecture.type?default(1) ==1>class="selected"</#if>><span>校外</span></a>
				</@htmlmacro.select>
	        </td>
	    </tr>
	    <#if officeAttendLecture.type?default(0) ==0>
	    <tr>
			<th style="width:20%"><span class="c-orange mr-5">*</span>年级：</th>
			<td style="width:30%">
				<@htmlmacro.select className="ui-select-box-disable" style="width:83%;" valName="officeAttendLecture.gradeId" valId="gradeId" notNull="true" myfunchange="changeGrade" msgName="年级">
				<a val="" ><span>请选择</span></a>
					<#if gradesList?exists && gradesList?size gt 0>
						<#list gradesList as item>
							<a val="${item.id!}"<#if officeAttendLecture.gradeId?default('') == item.id>class="selected"</#if>  ><span>${item.gradename!}</span></a>
						</#list>
					</#if>
				</@htmlmacro.select>
			</td>
	        <th style="width:20%"><span class="c-orange mr-5">*</span>班级：</th>
	        <td style="width:30%">
	        	<@htmlmacro.select className="ui-select-box-disable" style="width:83%;" valName="officeAttendLecture.classId" valId="classId" txtId="classTxt" notNull="true" optionDivName="itemClassIdOptionDiv" msgName="班级">
					<a val="" ><span>请选择</span></a>
				</@htmlmacro.select>
	        </td>
	    </tr>
	    <#else>
	    <th style="width:20%">年级：</th>
		<td style="width:30%">
			<input type="text" id="gradeId" name="officeAttendLecture.gradeId" readonly="readonly" value="${officeAttendLecture.gradeId!}" class="input-txt fn-left" style="width:80%;" maxlength="100"/>
		</td>
	    <th style="width:20%">班级：</th>
	    <td style="width:30%">
	    	<input type="text" id="classId" name="officeAttendLecture.classId" readonly="readonly" value="${officeAttendLecture.classId!}" class="input-txt fn-left" style="width:80%;" maxlength="100"/>
	    </td>
	    </#if>
	    <tr>
			<th style="width:20%"><span class="c-orange mr-5">*</span>学科：</th>
			<td style="width:30%">
				<input type="text" readonly="readonly" id="subjectName" name="officeAttendLecture.subjectName" notNull="true" value="${officeAttendLecture.subjectName!}" class="input-txt fn-left" style="width:80%;" msgName="学科" maxlength="100"/>
			</td>
	        <th style="width:20%"><span class="c-orange mr-5">*</span>授课教师：</th>
	        <td style="width:30%">
	        	<input type="text" readonly="readonly" id="teacherName" name="officeAttendLecture.teacherName" notNull="true" value="${officeAttendLecture.teacherName!}" class="input-txt fn-left" style="width:80%;" msgName="授课教师" maxlength="50"/>
	        </td>
	    </tr>
	    <tr>
	        <th><span class="c-orange mr-5">*</span>课程名称：</th>
	        <td colspan="3">
	        	<input type="text" readonly="readonly" id="projectName" name="officeAttendLecture.projectName" notNull="true" value="${officeAttendLecture.projectName!}" class="input-txt fn-left" style="width:81%;" msgName="课题名称" maxlength="100"/>
	        </td>
	    </tr>
	    <tr>
	        <th><span class="c-orange mr-5">*</span>听课内容：</th>
	        <td colspan="3">
	        	<textarea readonly="readonly" name="officeAttendLecture.projectContent" id="projectContent" cols="70" rows="4" class="text-area my-5" style="width:80%;padding:5px 1%;height:50px;" msgName="听课内容" notNull="true" maxLength="1000">${officeAttendLecture.projectContent!}</textarea>
	        </td>
	    </tr>
	    <tr>
	        <th>建议：</th>
	        <td colspan="3">
	        	<textarea readonly="readonly" name="officeAttendLecture.projectOpinion" id="projectOpinion" cols="70" rows="4" class="text-area my-5" style="width:80%;padding:5px 1%;height:50px;" msgName="建议" maxLength="1000">${officeAttendLecture.projectOpinion!}</textarea>
	        </td>
	    </tr>
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
    <#else>
    <#if officeAttendLecture.flowId?default('')!='1'>
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
		                </#if>
	                </div>
	                <p class="date">${((item.comment.operateTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}</p>
                </div>
        	</#list>
        	</#if>
        	<div class="fw-item fn-clear">
                <p class="tit fn-clear">
                	<span class="num">${officeAttendLecture.hisTaskList?size+1}</span>
                	<span class="pl-5">${officeAttendLecture.taskName!}</span>
            	</p>
                <p class="name">负责人：${userName!}</p>
                <div class="fn-clear"></div>
                <p><textarea class="text-area my-5" style="width:80%;padding:5px 1%;height:50px;" cols="70" rows="4" name="textComment" id="textComment" maxLength="200"></textarea></p>
            </div>
        </div>
    </div>
    </#if>
    </#if>
	<p class="pt-20 t-center">
	<#if !viewOnly>
		<a class="abtn-blue-big" href="javascript:void(0)"  onclick="changeFlow()">修改当前流程</a>
		<#if canChangeNextTask>
			<a href="javascript:void(0)" class="abtn-blue-big" onclick="changeNextStepUserDiv()">修改下一步负责人</a>
		</#if>
	    <a class="abtn-blue-big" href="javascript:void(0);" onclick="doAudit('true');">通过</a>
	    <a class="abtn-blue-big" href="javascript:void(0);" onclick="doAudit('false');">不通过</a>
	</#if>
	<#if canBeRetract>
		<a href="javascript:void(0)" class="abtn-blue-big" onclick="retractFlow('${showReBackId!}','${taskKey!}','${reTaskId!}')">撤回</a>
	</#if>
	    <a class="abtn-blue-big ml-5" href="javascript:void(0);" onclick="back();">返回</a>
    </p>
</form>
<div id="flowShow"  class="docReader my-20" style="height:660px;">
</div>
<div  id="classLayer" class="popUp-layer showSgParam" style="display:none;width:60%"></div>
<div id="nextStepLayer" style="display:none;width:980px;z-index:9997" class="popUp-layer "></div>
<script>
var selectedValue="";
$(document).ready(function(){
	vselect();
	load("#flowShow","${request.contextPath}/jbmp/editor/wfPreview.action?subsystemId=70&id=${officeAttendLecture.flowId!}&instanceType=instance&currentStepId=${currentStepId!}");
	selectedValue = '${officeAttendLecture.classId!}';
	<#if officeAttendLecture.type?default(0)==0>
	changeGrade();
	</#if>
});
//流程选择
function flowChange(){
	var flowId= $("#flowId").val();
	load("#flowShow","${request.contextPath}/jbmp/editor/wfPreview.action?subsystemId=70&id="+flowId);
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
	load("#showListDiv","${request.contextPath}/office/attendLecture/attendLecture-auditList.action?searchType=${searchType!}&startTime=${((startTime)?string('yyyy-MM-dd'))?if_exists}&endTime=${((endTime)?string('yyyy-MM-dd'))?if_exists}&applyUserName=${applyUserName!}");
}
<#if taskHandlerSaveJson?exists>
var taskHandlerSave = ${taskHandlerSaveJson!};
<#else>
var taskHandlerSave = '';
</#if>
var isSubmit = false;
function doAudit(state) {
 	if (isSubmit) {
        return;
    }
    isSubmit=true;
 	if(!checkAllValidate("#editForm")){
 		isSubmit = false;
		return;
	}
	var textComment=$("#textComment").val();
	if(state=='false' && (textComment==null||textComment=='')){
		isSubmit = false;
		showMsgWarn("请填写审核不通过的原因！");
  		return;
	}

    taskHandlerSave.comment.textComment = $("#textComment").val();
    taskHandlerSave.comment.commentType = 1;
    $("#taskHandlerSaveJson").val(JSON.stringify(taskHandlerSave));
    var options = {
        url: '${request.contextPath}/office/attendLecture/attendLecture-auditSave.action?pass='+state+'&currentStepId=${currentStepId!}',
        success: showReply,
        dataType: 'json',
        clearForm: false,
        resetForm: false,
        type: 'post'
    };
    isSubmit = true;
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

function closeChange(){
	closeDiv("#classLayer");
}

function changeFlow(){
	var attendLectureId = "${officeAttendLecture.id!}";
	openDiv("#classLayer","#classLayer .close,#classLayer .reset",
 	"${request.contextPath}/office/attendLecture/attendLecture-findCurrentstep.action?attendLectureId="+attendLectureId+"&taskId=${taskId!}", null, null ,null,function(){closeChange()},null);
}

function reload(id, taskId){
	load("#showListDiv","${request.contextPath}/office/attendLecture/attendLecture-auditEdit.action?officeAttendLecture.id="+id+"&taskId="+taskId);
}

function changeNextStepUserDiv(){
	openDiv("#nextStepLayer","#nextStepLayer .close,#nextStepLayer .reset",
 		"${request.contextPath}/office/common/loadNextStepTaskLayer.action?taskId=${taskId!}&flowId=${officeAttendLecture.flowId!}", null, null ,null,function(){vselect();},null);
}
</script>
</@htmlmacro.moduleDiv >