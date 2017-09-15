<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="">
<#include "/common/handlefielderror.ftl">
<#import "/common/commonmacro.ftl" as commonmacro>
	<br>
    <@htmlmacro.tableDetail divClass="table-form">
		<tr>
	        <th colspan="4" style="text-align:center;">教师集体外出</th>
	    </tr>
	    <tr>
		      <th style="width:20%"><span class="c-orange mr-5">*</span>${appsetting.getString("offce.goout.jtwc")!"外出类型"}：</th>
		        <td style="width:30%">
		        	<div class="select_box">
			    		<@htmlmacro.select style="width:150px;" valName="officeJtgoOut.outType" className="ui-select-box-disable " msgName="外出类型" valId="outType">
							${appsetting.getMcode("DM-JTWC").getHtmlTag(officeJtgoOut.outType?default(''))}
						</@htmlmacro.select>
					</div>
		        </td>
		        <th style="width:20%"><span class="c-orange mr-5">*</span>外出时间：</th>
		        <td style="width:30%">
		        	<input name="officeJtgoOut.days" id="days" type="text" class="input-txt" style="width:135px;" maxlength="20" readonly="true" value="${officeJtgoOut.days!}" msgName="外出时间" notNull="true" />
		        </td>
		    </tr>
		    <tr>
		      <th style="width:20%"><span class="c-orange mr-5">*</span>外出人员：</th>
		       <td style="width:30%" colspan="3">
		        	<input name="officeJtgoOut.tripPerson" id="tripPerson" type="text" class="input-txt" readOnly="true" style="width:81%;" maxlength="255"  value="${officeJtgoOut.tripPerson!}" msgName="外出人员" notNull="true" />
		       </td>
		    </tr>
		    <tr>
		        <th><span class="c-orange mr-5">*</span>外出事由：</th>
		        <td colspan="3">
		        	<textarea name="officeJtgoOut.tripReason" id="tripReason" cols="70" rows="4" readOnly="true" class="text-area my-5" style="width:80%;padding:5px 1%;height:50px;" msgName="外出事由" notNull="true" maxLength="255">${officeJtgoOut.tripReason!}</textarea>
		        </td>
		    </tr>
	     <tr>
	        <th>附件：</th>
	        <td colspan="3">
	        <#list officeJtgoOut.attachments as item>
			<a href="javascript:doDownload('${item.downloadPath!}');">${item.fileName!}</a>&nbsp;&nbsp;&nbsp;&nbsp;
        	</#list>
	        </td>
	    </tr>
	</@htmlmacro.tableDetail>
    <#if officeJtgoOut.flowId?default('')!='1'>
    <br>
	<div class="fw-edit mt-10">
    	<p class="tt">流程意见</p>
        <div class="fw-item-wrap">
        	<#if (officeJtgoOut.hisTaskList?size>0)>
        	<#list officeJtgoOut.hisTaskList as item>
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
    	<a href="javascript:void(0)" class="abtn-blue-big" onclick="goBack()">返回</a>
	</p>
	<iframe name="downloadFrame" id="downloadFrame" style="display:none;"></iframe>
	<div id="flowShow" class="docReader my-20" style="height:660px;">
	</div>
<script>
$(document).ready(function(){
vselect();
load("#flowShow","${request.contextPath}/jbmp/editor/wfPreview.action?id=${officeJtgoOut.flowId!}&subsystemId=70&instanceType=instance");
});
function doDownload(url){
	document.getElementById('downloadFrame').src=url;
}
function goBack(){
		doSearch();
}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>
