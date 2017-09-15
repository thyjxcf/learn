<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="">
<#include "/common/handlefielderror.ftl">
<#import "/common/commonmacro.ftl" as commonmacro>
<#import "../businesstrip/archiveWebuploader.ftl" as archiveWebuploader>
	<br>
    <@htmlmacro.tableDetail divClass="table-form">
		<tr>
	        <th colspan="4" style="text-align:center;">教师出差</th>
	    </tr>
	    <tr>
		        <th style="width:20%"><span class="c-orange mr-5">*</span>开始时间：</th>
		        <td style="width:30%">
		        	<@htmlmacro.datepicker name="officeBusinessTrip.beginTime" id="beginTime" class="input-txt" style="width:39%;" msgName="开始时间" readonly="true" notNull="true" value="${((officeBusinessTrip.beginTime)?string('yyyy-MM-dd'))?if_exists}" />
		        </td>
		        <th style="width:20%"><span class="c-orange mr-5">*</span>结束时间：</th>
		        <td style="width:30%">
		        	<@htmlmacro.datepicker name="officeBusinessTrip.endTime" id="endTime" class="input-txt" style="width:39%;" msgName="结束时间" readonly="true" notNull="true" value="${((officeBusinessTrip.endTime)?string('yyyy-MM-dd'))?if_exists}" />
		        </td>
		    </tr>
		    <tr>
		       <th style="width:20%"><span class="c-orange mr-5">*</span>出差天数：</th>
		       <td style="width:30%">
		        	<input name="officeBusinessTrip.days" id="days" type="text" class="input-txt fn-left input-readonly" readonly="true" style="width:136px;" maxlength="5" dataType="float" maxValue="999" minValue="0.1" decimalLength="1" value="${(officeBusinessTrip.days?string('0.#'))?if_exists}" msgName="出差天数" notNull="true" />
		       </td>
		        <th style="width:20%"><span class="c-orange mr-5">*</span>出差地点：</th>
		        <td style="width:30%">
		        	<input type="text" msgName="出差地点" class="input-txt fn-left input-readonly" id="place" readonly="true" name="officeBusinessTrip.place" maxlength="50" notNull="true" value="${officeBusinessTrip.place!}" style="width:136px;">
		        </td>
		    </tr>
		    <tr>
		        <th><span class="c-orange mr-5">*</span>出差事由：</th>
		        <td colspan="3">
		        	<textarea name="officeBusinessTrip.tripReason" id="tripReason" cols="70" rows="4"  readonly="true" class="text-area my-5" style="width:80%;padding:5px 1%;height:50px;" msgName="出差事由" notNull="true" maxLength="100">${officeBusinessTrip.tripReason!}</textarea>
		        </td>
		    </tr>
		    <tr>
		        <th>提交时间：</th>
		        <td colspan="3">
		        	${(officeBusinessTrip.createTime?string('yyyy-MM-dd'))?if_exists}
		        </td>
		    </tr>
	     <@archiveWebuploader.archiveWebuploaderEditViewer canEdit=false showAttachmentDivId='showAttDiv' editContentDivId='editContentDiv' isSend=true loadDiv=false />
	</@htmlmacro.tableDetail>
    <#if officeBusinessTrip.flowId?default('')!='1'>
    <br>
	<div class="fw-edit mt-10">
    	<p class="tt">流程意见</p>
        <div class="fw-item-wrap">
        	<#if (officeBusinessTrip.hisTaskList?size>0)>
        	<#list officeBusinessTrip.hisTaskList as item>
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
    	<a href="javascript:void(0)" class="abtn-blue-big" onclick="toDown('${officeBusinessTrip.id!}')">导出出差审批表</a>
    	<a href="javascript:void(0)" class="abtn-blue-big" onclick="toPrint()">打印审批表</a>
	</p>
	<iframe name="downloadFrame" id="downloadFrame" style="display:none;"></iframe>
	<div id="flowShow" class="docReader my-20" style="height:660px;">
	</div>
	
<div class="table-print-warp" id="printDiv" style="display:none;">
    <div class="table-print-warp-sp">
    	<p class="tt-b">出差审批表</p>
        <table class="table-print-sp table-print-sp">
        	<tr>
        		<td width="88" height="39">
                    申请人
                </td>
                <td width="148">
                	${officeBusinessTrip.applyUserName!}
                </td>
                <td width="88">
                    部门（科室）
                </td>
                <td width="148">
                	${officeBusinessTrip.deptName!}
                </td>
        	</tr>
            <tr>
                <td height="39">
                    出差天数
                </td>
                <td>
                	${(officeBusinessTrip.days?string('0.#'))?if_exists}
                </td>
                <td>
                    出差地点
                </td>
                <td>
                	${officeBusinessTrip.place!}
                </td>
            </tr>
            <tr>
                <td height="39">
                    出差时间
                </td>
                <td colspan="3">
                    ${((officeBusinessTrip.beginTime)?string('yyyy-MM-dd'))?if_exists}至
                    ${((officeBusinessTrip.endTime)?string('yyyy-MM-dd'))?if_exists}
                </td>
            </tr>
            <tr>
                <td height="39">
                    出差事由
                </td>
                <td colspan="3">
                	${officeBusinessTrip.tripReason!}
                </td>
            </tr>
            <tr>
                <td height="139">
                    乘坐交<br>通工具
                </td>
                <td colspan="3" class="table-print-vehicle fw-normal t-left"> 
                    <p class="m-left">1.乘坐营运交通工具</p>
                    <p class="m-left"> 
                       <span><em class="square-b">&nbsp;</em>飞机</span> 
                       <span><em class="square-b">&nbsp;</em>火车</span> 
                       <span><em class="square-b">&nbsp;</em>汽车</span> 
                       <span><em class="square-b">&nbsp;</em>轮船</span> 
                       <span><em class="square-b">&nbsp;</em>其他_______</span> 
                    </p>
                    <p class="m-left">2.单位派车</p>
                    <p class="m-left"><span class="td-spac0">车号:</span><span class="td-spac0">司机:</span></p>
                    <p class="m-left">3.其他（须注明）________________________________________</p>
                </td>
            </tr>
            <tr>
                <td height="79">
                    住宿标准
                </td>
                <td colspan="3" class="fw-normal t-left"> 
                    <p class="m-left">标准间：<span class="td-spac1"></span>元/间，共<span class="td-spac2"></span>间</p>
                    <p class="m-left">单<span class="td-spac3"></span>间：<span class="td-spac1"></span>元/间，共<span class="td-spac2"></span>间</p>
                </td>
            </tr>
            <#if officeBusinessTrip.hisTaskList?exists&&officeBusinessTrip.hisTaskList?size gt 0>
            <#list officeBusinessTrip.hisTaskList as hisTask>
            <tr>
                <td height="79">
                    ${hisTask.taskName!}
                </td>
                <td colspan="3" class="table-print-vehicle fw-normal t-left"> 
                    <p class="m-left">审核意见:${hisTask.comment.textComment!}</p>
                    <br>
                    <p class="f-r"><span class="td-spac0">审核人:${hisTask.assigneeName!}</span><span>审核时间:${((hisTask.comment.operateTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}</span></p>
                </td>
            </tr>
            </#list>
            </#if>
    </div>
</div>
	
<script>
$(document).ready(function(){
vselect();
load("#flowShow","${request.contextPath}/jbmp/editor/wfPreview.action?id=${officeBusinessTrip.flowId!}&subsystemId=70&instanceType=instance");
});
function doDownload(url){
	document.getElementById('downloadFrame').src=url;
}
function goBack(){
		load("#businessTripeDiv", "${request.contextPath}/office/businesstrip/businessTrip-myBusinessTripList.action");
}

function toDown(id){
	location.href="${request.contextPath}/office/businesstrip/businessTrip-businessTripDown.action?officeBusinessTrip.id="+id;
}

function toPrint(){
	$("#printDiv").css("display","");
	LODOP=getLodop();
	LODOP.ADD_PRINT_HTM("10mm","5mm","RightMargin:5mm","BottomMargin:15mm",getPrintContent(jQuery('#printDiv')));
  	LODOP.PREVIEW();
  	$("#printDiv").css("display","none");
}

function retractFlow(showReBackId, taskKey, reTaskId){
	$.getJSON("${request.contextPath}/office/businesstrip/businessTrip-retractFlow.action", {showReBackId:showReBackId,taskKey:taskKey,reTaskId:reTaskId}, function(data){
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
