<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="">
<#include "/common/handlefielderror.ftl">
<#import "/common/commonmacro.ftl" as commonmacro>
<#import "../goout/archiveWebuploader.ftl" as archiveWebuploader>
	<br>
    <@htmlmacro.tableDetail divClass="table-form">
		<tr>
	        <th colspan="4" style="text-align:center;">教师外出</th>
	    </tr>
	    <tr>
		       <th style="width:20%"><span class="c-orange mr-5">*</span>开始时间：</th>
		        <td style="width:30%">
		        	<@htmlmacro.datepicker name="officeGoOut.beginTime" id="beginTime"  style="width:39%;" msgName="开始时间" notNull="true" readonly="true" size="20" maxlength="19" dateFmt="yyyy-MM-dd HH:mm:00" value="${((officeGoOut.beginTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}" />
		        </td>
		        <th style="width:20%"><span class="c-orange mr-5">*</span>结束时间：</th>
		        <td style="width:30%">
		        	<@htmlmacro.datepicker name="officeGoOut.endTime" id="endTime"  style="width:39%;" msgName="结束时间" notNull="true" readonly="true" size="20" maxlength="19" dateFmt="yyyy-MM-dd HH:mm:00" value="${((officeGoOut.endTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}" />
		        </td>
		    </tr>
		    <tr>
		      <th style="width:20%"><span class="c-orange mr-5">*</span>外出时间(小时)：</th>
		       <td style="width:30%">
		        	<input name="officeGoOut.hours" id="hours" type="text" readonly="readonly" class="input-txt fn-left" style="width:140px;" maxlength="5"  dataType="float" maxValue="999" minValue="0.1" decimalLength="1" value="${(officeGoOut.hours?string('0.#'))?if_exists}" msgName="外出时间" notNull="true" />
		       </td>
		       <th style="width:20%"><span class="c-orange mr-5">*</span>外出类型：</th>
		       <td style="width:30%">
		        	<@htmlmacro.select style="width:42%;" className="ui-select-box-disable "  valName="officeGoOut.outType" valId="outType"  msgName="外出类型">
					<a val="1" <#if officeGoOut.outType?default('')=='1'>class="selected"</#if> ><span>因公外出</span></a>
					<a val="2" <#if officeGoOut.outType?default('')=='2'>class="selected"</#if> ><span>因私外出</span></a>
					</@htmlmacro.select>
		       </td>
		    </tr>
		    <tr>
		        <th><span class="c-orange mr-5">*</span>外出事由：</th>
		        <td colspan="3">
		        	<textarea name="officeGoOut.tripReason" id="tripReason" cols="70" rows="4"  readonly="true" class="text-area my-5" style="width:80%;padding:5px 1%;height:50px;" msgName="外出事由" notNull="true" maxLength="100">${officeGoOut.tripReason!}</textarea>
		        </td>
		    </tr>
		    <tr>
		        <th>提交时间：</th>
		        <td colspan="3">
		        	${officeGoOut.createTime?string('yyyy-MM-dd')!}
		        </td>
		    </tr>
	     <@archiveWebuploader.archiveWebuploaderEditViewer canEdit=false showAttachmentDivId='showAttDiv' editContentDivId='editContentDiv' isSend=true loadDiv=false /> 
	</@htmlmacro.tableDetail>
    <#if officeGoOut.flowId?default('')!='1'>
    <br>
	<div class="fw-edit mt-10">
    	<p class="tt">流程意见</p>
        <div class="fw-item-wrap">
        	<#if (officeGoOut.hisTaskList?size>0)>
        	<#list officeGoOut.hisTaskList as item>
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
		<#if canBeRetract>
			<a href="javascript:void(0)" class="abtn-blue-big" onclick="retractFlow('${showReBackId!}','${taskKey!}','${reTaskId!}')">撤回</a>
		</#if>
    	<a href="javascript:void(0)" class="abtn-blue-big" onclick="goBack()">返回</a>
	</p>
	<iframe name="downloadFrame" id="downloadFrame" style="display:none;"></iframe>
	<div id="flowShow" class="docReader my-20" style="height:660px;">
	</div>
<script>
$(document).ready(function(){
vselect();
load("#flowShow","${request.contextPath}/jbmp/editor/wfPreview.action?id=${officeGoOut.flowId!}&subsystemId=70&instanceType=instance");
});
function doDownload(url){
	document.getElementById('downloadFrame').src=url;
}
function goBack(){
		load("#goOutDiv", "${request.contextPath}/office/goout/goout-myGoOutList.action");
}
function retractFlow(showReBackId, taskKey, reTaskId){
	$.getJSON("${request.contextPath}/office/goout/goout-retractFlow.action", {showReBackId:showReBackId,taskKey:taskKey,reTaskId:reTaskId}, function(data){
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
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>
