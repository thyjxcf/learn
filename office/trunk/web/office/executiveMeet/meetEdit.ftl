<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="">
<script>
var flag = false;
function doSave(state){
	if(flag){
		return;
	}
	if(!checkAllValidate("#meetEditDiv")){
		return;
	}
	$("#state").val(state);
	flag = true;
	var options = {
       url:'${request.contextPath}/office/executiveMeet/executiveMeet-meetSave.action', 
       success : showReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post'
    };
    $('#mainform').ajaxSubmit(options);
}

function showReply(data){
	if(!data.operateSuccess){
		flag = false;
		showMsgError(data.errorMessage);
		return;
	}else{
		showMsgSuccess(data.promptMessage, "提示", function(){
			sear();
		}); 
	}
}
</script>
<div id="meetEditDiv">
<form id="mainform" action="" method="post">
<input type="hidden" id="id" name="officeExecutiveMeet.id" value="${officeExecutiveMeet.id!}"/>
<input type="hidden" id="state" name="officeExecutiveMeet.state" value=""/>
	<p class="table-dt">编辑会议</p>
    <table border="0" cellspacing="0" cellpadding="0" class="table-form">
    <tr>
        <th width="30%"><span class="c-orange mr-5">*</span>会议名称：</th>
        <td width="70%">
        	<input type="text" id="name" name="officeExecutiveMeet.name" class="input-txt fn-left" style="width:300px;" notNull="true" msgName="会议名称" maxlength="100" value="${officeExecutiveMeet.name!}">
        </td>
    </tr>
    <tr>
        <th width="30%"><span class="c-orange mr-5">*</span>会议时间：</th>
        <td width="70%">
    		<#if officeExecutiveMeet.state?default(0) == 0>
    			<@htmlmacro.datepicker name="officeExecutiveMeet.meetDate" id="meetDate" class="input-date"  notNull="true" style="width:300px;" msgName="会议时间" maxlength="30" value="${(officeExecutiveMeet.meetDate?string('yyyy-MM-dd HH:mm:00'))?if_exists}" dateFmt='yyyy-MM-dd HH:mm:00'/>
        	<#else>
        		<input name="officeExecutiveMeet.meetDate" id="meetDate" type="hidden" style="width:300px;" value="${(officeExecutiveMeet.meetDate?string('yyyy-MM-dd HH:mm:00'))?if_exists}" />
        		${(officeExecutiveMeet.meetDate?string('yyyy-MM-dd HH:mm:00'))?if_exists}
        	</#if>
        </td>
    </tr>
    <tr>
    	<th width="30%"><span class="c-orange mr-5">*</span>会议地点：</th>
        <td width="70%">
        	<input type="text" id="place" name="officeExecutiveMeet.place" class="input-txt fn-left" style="width:300px;" notNull="true" msgName="会议地点" maxlength="100" value="${officeExecutiveMeet.place!}">
        </td>
	</tr>
	<tr>
    	<th width="30%">固定参会科室：</th>
        <td width="70%">
        	<#if deptNamesStr?exists && deptNamesStr!=''>
        		${deptNamesStr!}
        	<#else>
        		未维护固定参会科室
        	</#if>
        </td>
	</tr>
    </table>
</form>
</div>
<p class="t-center pt-15">
	<#if officeExecutiveMeet.state?default(0) == 0>
		<a href="javascript:doSave('0');" class="abtn-blue-big">保存</a>
	    <a href="javascript:doSave('1');" class="abtn-blue-big">发布</a>
    <#elseif !officeExecutiveMeet.start>
    	<a href="javascript:doSave('1');" class="abtn-blue-big">保存</a>
    </#if>
    <a href="javascript:sear();" class="abtn-blue-big">返回</a>
</p>
</@htmlmacro.moduleDiv>