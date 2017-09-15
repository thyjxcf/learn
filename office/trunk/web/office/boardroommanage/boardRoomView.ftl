<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="会议室管理">
<p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span>会议室查看</span></p>
	<div class="wrap pa-10">
	<form name="myBusinessTripForm" id="myBusinessTripForm" method="post" enctype="multipart/form-data">
	<table border="0" cellspacing="0" cellpadding="0" class="table-form">
		<input id="id" name="officeBoardroomXj.id" value="${officeBoardroomXj.id!}" type="hidden" />
		<input id="unitId" name="officeBoardroomXj.unitId" value="${officeBoardroomXj.unitId!}" type="hidden" />
		<input id="createTime" name="officeBoardroomXj.createTime" value="${officeBoardroomXj.createTime!}" type="hidden" />
		<@htmlmacro.tableDetail divClass="table-form">
		    <tr>
		        <th style="width:10%"><span class="c-orange mr-5">*</span>会议室名称：</th>
		        <td style="width:40%">
		        	${officeBoardroomXj.name!}
		        </td>
		        <th style="width:10%"><span class="c-orange mr-5">*</span>预约是否审核：</th>
		        <td style="width:40%">
                <#if officeBoardroomXj.needAudit?default('')=='0'>不审核</#if>
                <#if officeBoardroomXj.needAudit?default('')=='1'>审核</#if>
		    </tr>
		    <tr>
		       <th style="width:20%" rowspan="2"><span class="c-orange mr-5">*</span>可用时段：</th>
		       <td style="width:20%">
		       	&nbsp;开始：
  	  			<@htmlmacro.datepicker class="input-txt" style="width:100px;" id="startTime" name="officeBoardroomXj.startTime" readonly="true" value="${officeBoardroomXj.startTime!}" dateFmt="HH:mm" notNull="true"/>
  	  			&nbsp;结束：
  	  			<@htmlmacro.datepicker class="input-txt" style="width:100px;" id="endTime" name="officeBoardroomXj.endTime" readonly="true" value="${officeBoardroomXj.endTime!}" dateFmt="HH:mm" notNull="true"/>
  	  			</td>
		       <th style="width:20%"><span class="c-orange mr-5">*</span>间隔：</th>
		       <td>
				<#if officeBoardroomXj.timeInterval?default('0') == '30'>30分钟</#if>
				<#if officeBoardroomXj.timeInterval?default('0') == '60'>60分钟</#if>
		  	  </td>
		        
		    </tr>
		    <tr>
		        <td colspan="4">
		        	&nbsp;&nbsp;午休：
		  	  	<@htmlmacro.datepicker class="input-txt" style="width:100px;" id="noonStartTime" name="officeBoardroomXj.noonStartTime" readonly="true" value="${officeBoardroomXj.noonStartTime!}" dateFmt="HH:mm" notNull="true"/>
		  	  	至&nbsp;
		  	  	<@htmlmacro.datepicker class="input-txt" style="width:100px;" id="noonEndTime" name="officeBoardroomXj.noonEndTime" readonly="true" value="${officeBoardroomXj.noonEndTime!}" dateFmt="HH:mm" notNull="true"/>
		  	  </td>
		    </tr>
		    <tr>
		        <th style="width:20%"><span class="c-orange mr-5">*</span>容纳人数：</th>
		        <td style="width:30%">
		        	<input type="text" msgName="容纳人数" class="input-txt  input-readonly" id="maxNumber" name="officeBoardroomXj.maxNumber" readonly="true" maxlength="50" notNull="true" value="${officeBoardroomXj.maxNumber!}" style="width:180px;">
		        </td>
		        <th style="width:20%">位置：</th>
		        <td style="width:30%">
                <input type="text" msgName="位置" class="input-txt  input-readonly" id="address" name="officeBoardroomXj.address" maxlength="50" readonly="true"  value="${officeBoardroomXj.address!}" style="width:180px;">
		    </tr>
		    <tr>
		    	<th style="width:20%" rowspan="3"><span class="c-orange mr-5">*</span>配置说明：</th>
		    	<td style="width:30%">
		    	<#if officeBoardroomXj.rostrum?default(0)!=0><span class="ui-checkbox ui-checkbox-disabled fn-left mt-5 ml-10 disabled="true" ui-checkbox-current" myfunclick="clickSms">
		    	<input type="checkbox" id="rostrums" name="rostrums" disabled="true" value="true" class="chk" checked="checked"/>
		    	<#else>
		       	<span class="ui-checkbox ui-checkbox-disabled fn-left mt-5 ml-10" disabled="true" myfunclick="clickSms">
        			<input type="checkbox" id="rostrums" disabled="true" name="rostrums" value="true" class="chk"/>
		       	</#if>
        			主席台&nbsp;<input type="text" msgName="主席台" class="input-txt  input-readonly" style="margin-top:-5px;" id="rostrum" name="officeBoardroomXj.rostrum" maxlength="50" readonly="true"  value="${officeBoardroomXj.rostrum!}" style="width:50px;">人
        		</span>
  	  			</td>
  	  			<td style="width:30%" colspan="2">
  	  				<#if officeBoardroomXj.conferenceSeats?default(0)!=0><span class="ui-checkbox ui-checkbox-disabled fn-left mt-5 ml-10 ui-checkbox-current" myfunclick="clickSms">
  	  				<input type="checkbox" id="conferenceSeatss" name="conferenceSeatss" value="true" class="chk" checked="checked"/>
  	  				<#else>
		        	<span class="ui-checkbox ui-checkbox-disabled fn-left mt-5 ml-10" myfunclick="clickSms">
        			<input type="checkbox" id="conferenceSeatss" name="conferenceSeatss" value="true" class="chk"/>
		        	</#if>
        			会议席&nbsp;<input type="text" msgName="会议席" class="input-txt  input-readonly" style="margin-top:-5px;" id="conferenceSeats" name="officeBoardroomXj.conferenceSeats" maxlength="50" readonly="true" value="${officeBoardroomXj.conferenceSeats!}" style="width:50px;">人
        		</span>
		        </td>
		    </tr>   
		    <tr>
		    	<td style="width:30%">
		    	<#if officeBoardroomXj.tableType?default(0)!=0><span class="ui-checkbox ui-checkbox-disabled fn-left mt-5 ml-10 ui-checkbox-current" myfunclick="clickSms">
		    	<input type="checkbox" id="tableTypes" name="tableTypes" value="true" class="chk" checked="checked"/>
		    	<#else>
		       	<span class="ui-checkbox ui-checkbox-disabled fn-left mt-5 ml-10" myfunclick="clickSms">
        			<input type="checkbox" id="tableTypes" name="tableTypes" value="true" class="chk"/>
		       	</#if>
        			围桌式&nbsp;<input type="text" msgName="围桌式" class="input-txt  input-readonly" style="margin-top:-5px;" id="tableType" name="officeBoardroomXj.tableType" maxlength="50" readonly="true" value="${officeBoardroomXj.tableType!}" style="width:50px;">人
        		</span>
  	  			</td>
  	  			<td style="width:30%" colspan="2">
  	  				<#if officeBoardroomXj.attendNumber?default(0)!=0><span class="ui-checkbox ui-checkbox-disabled fn-left mt-5 ml-10 ui-checkbox-current" myfunclick="clickSms">
  	  				<input type="checkbox" id="attendNumbers" name="attendNumbers" value="true" class="chk" checked="checked"/>
		        	<#else>
		        	<span class="ui-checkbox ui-checkbox-disabled fn-left mt-5 ml-10" myfunclick="clickSms">
        			<input type="checkbox" id="attendNumbers" name="attendNumbers" value="true" class="chk"/>
		        	</#if>
        			列席&nbsp;<input type="text" msgName="列席" class="input-txt  input-readonly" style="margin-top:-5px;" id="attendNumber" name="officeBoardroomXj.attendNumber" maxlength="50" readonly="true" value="${officeBoardroomXj.attendNumber!}" style="width:50px;">人
        		</span>
		        </td>
		    </tr>   
		    <tr>
		    	<td style="width:30%">
		    	<#if officeBoardroomXj.isProjector?default(false)!=false>
		    	带投影
		    	<#else>
		       	无投影
		       	</#if>
  	  			</td>
  	  			<td style="width:30%" colspan="2">
		        	其他：
                	<input type="text" msgName="其他" class="input-txt  input-readonly" id="remark" name="officeBoardroomXj.remark" maxlength="50" readonly="true" value="${officeBoardroomXj.remark!}" style="width:180px;">
        		</span>
		        </td>
		    </tr>   
		    <tr>
		    	<td colspan="4" class="td-opt">
		    	    <a class="abtn-blue reset ml-5" href="javascript:void(0);">取消</a>
		        </td>
		    </tr>
		</@htmlmacro.tableDetail>
		</table>
	</form>
	</div>
</@htmlmacro.moduleDiv >