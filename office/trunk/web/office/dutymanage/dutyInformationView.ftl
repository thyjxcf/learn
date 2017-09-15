<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<#import "../jtgooutmanage/archiveWebuploader.ftl" as archiveWebuploader>
<@htmlmacro.moduleDiv titleName="出差申请">
	<p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span style="text-align:center;">值班信息设置查看</span></p>
	<div class="wrap pa-10">
	<form name="officeDutyInformationSetForm" id="officeDutyInformationSetForm" method="post">
		<input id="id" name="officeDutyInformationSet.id" value="${officeDutyInformationSet.id!}" type="hidden" />
		<input id="unitId" name="officeDutyInformationSet.unitId" value="${officeDutyInformationSet.unitId!}" type="hidden" />
		<input id="createUserId" name="officeDutyInformationSet.createUserId" value="${officeDutyInformationSet.createUserId!}" type="hidden" />
		<input id="createTime" name="officeDutyInformationSet.createTime" value="${officeDutyInformationSet.createTime!}" type="hidden" />
		<input id="year" name="officeDutyInformationSet.year" value="${officeDutyInformationSet.year!}" type="hidden" />
		<@htmlmacro.tableDetail divClass="table-form">
		    <tr>
		       <th style="width:10%"><span class="c-orange mr-5">*</span>值班名称：</th>
		       <td style="width:40%"  colspan="3">
		        	<input name="officeDutyInformationSet.dutyName" id="dutyName" type="text" class="input-txt" style="width:35%;" maxlength="50"  value="${officeDutyInformationSet.dutyName!}" readonly="true" msgName="值班名称 " notNull="true" />
		       </td>
		    </tr>
		    <tr>
		        <th><span class="c-orange mr-5">*</span>值班日期：</th>
		        <td colspan="3" >
		        	<@htmlmacro.datepicker name="officeDutyInformationSet.dutyStartTime" id="dutyStartTime" class="input-txt" style="width:25%;" msgName="值班开始日期" notNull="true" size="20" maxlength="19" readonly="true" dateFmt="yyyy-MM-dd HH:mm:00" value="${((officeDutyInformationSet.dutyStartTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}" />
		        	至
		        	<@htmlmacro.datepicker name="officeDutyInformationSet.dutyEndTime" id="dutyEndTime" class="input-txt" style="width:25%;" msgName="值班结束日期" notNull="true" size="20" maxlength="19" readonly="true" dateFmt="yyyy-MM-dd HH:mm:00" value="${((officeDutyInformationSet.dutyEndTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}" />
		        </td>
		    </tr>
		    <tr>
		        <th  ><span class="c-orange mr-5">*</span>报名日期：</th>
		        <td colspan="3" >
		        	<@htmlmacro.datepicker name="officeDutyInformationSet.registrationStartTime" id="registrationStartTime" class="input-txt" style="width:25%;" msgName="报名开始日期" notNull="true" readonly="true" size="20" maxlength="19" dateFmt="yyyy-MM-dd HH:mm:00" value="${((officeDutyInformationSet.registrationStartTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}" />
		        	至
		        	<@htmlmacro.datepicker name="officeDutyInformationSet.registrationEndTime" id="registrationEndTime" class="input-txt" style="width:25%;" msgName="报名结束日期" notNull="true" readonly="true" size="20" maxlength="19" dateFmt="yyyy-MM-dd HH:mm:00" value="${((officeDutyInformationSet.registrationEndTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}" />
		        </td>
		    </tr>
		    
		    <tr>
		       <th style="width:10%"><span class="c-orange mr-5">*</span>类型：</th>
		        <td style="width:40%" colspan="3">
                <#if officeDutyInformationSet.type?default('0')=='0'>上下午
                <#else>
                	一天
                </#if>
		    </tr>
		    <tr>
		        <th>说明：</th>
		        <td colspan="3">
		        	<textarea name="officeDutyInformationSet.instruction" id="instruction" cols="70" rows="4" class="text-area my-5" readonly="true" style="width:97%;padding:5px 1%;height:75px;" maxLength="500">${officeDutyInformationSet.instruction!}</textarea>
		        </td>
		    </tr>
		    <tr>
		       <th><span class="c-orange mr-5">*</span>人员设置：</th>
		        <td colspan="3">
		        	${officeDutyInformationSet.userNames!}
		        </td>
		        <span id="noneUser" class="field_tip input-txt-warn-tip"></span>
		    </tr>
		    <tr>
		    	<td colspan="4" class="td-opt">
		    	    <a class="abtn-blue reset ml-5" href="javascript:void(0);">取消</a>
		        </td>
		    </tr>
		</@htmlmacro.tableDetail>
	</form>
	</div>
	<script>
		
	</script>
</@htmlmacro.moduleDiv >