<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="会议室管理">
<p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span><#if officeBoardroomXj?exists&&officeBoardroomXj.id?exists>修改会议室<#else>新增会议室</#if></span></p>
	<div class="wrap pa-10">
	<form name="myBusinessTripForm" id="myBusinessTripForm" method="post">
	<table border="0" cellspacing="0" cellpadding="0" class="table-form">
		<input id="id" name="officeBoardroomXj.id" value="${officeBoardroomXj.id!}" type="hidden" />
		<input id="unitId" name="officeBoardroomXj.unitId" value="${officeBoardroomXj.unitId!}" type="hidden" />
		<input id="createTime" name="officeBoardroomXj.createTime" value="${officeBoardroomXj.createTime!}" type="hidden" />
		<#if officeBulletinSet?exists&&officeBulletinSet.needAudit=='0'>
		<input id="needAudit" name="officeBoardroomXj.needAudit" value="${officeBoardroomXj.needAudit!}" type="hidden" />
		</#if>
		<@htmlmacro.tableDetail divClass="table-form">
		    <tr>
		        <th style="width:10%"><span class="c-orange mr-5">*</span>会议室名称：</th>
		        <td style="width:40%">
		        	<input type="text" msgName="会议室名称" class="input-txt fn-left" id="name" name="officeBoardroomXj.name" maxlength="30" notNull="true" value="${officeBoardroomXj.name!}" style="width:180px;">
		        </td>
		        <#if officeBulletinSet?exists&&officeBulletinSet.needAudit=='1'>
		        <th style="width:10%"><span class="c-orange mr-5">*</span>预约是否审核：</th>
		        <td style="width:40%">
                <span class="ui-radio ui-radio-noTxt <#if officeBoardroomXj.needAudit?default('')=='0'>ui-radio-current</#if>" data-name="a">
                <input type="radio" class="radio" <#if officeBoardroomXj.needAudit?default('')=='0'>checked="checked"</#if> name="officeBoardroomXj.needAudit" value="0">&nbsp不开启</span>&nbsp;&nbsp;
                <span class="ui-radio ui-radio-noTxt <#if officeBoardroomXj.needAudit?default('')=='1'>ui-radio-current</#if>" data-name="a">
                <input type="radio" class="radio" <#if officeBoardroomXj.needAudit?default('')=='1'>checked="checked"</#if> name="officeBoardroomXj.needAudit" value="1">&nbsp开启</span></td>
                </#if>
		    </tr>
		    <tr>
		       <th style="width:20%" rowspan="2"><span class="c-orange mr-5">*</span>可用时段：</th>
		       <td style="width:20%">
		       	&nbsp;开始：
  	  			<@htmlmacro.datepicker class="input-txt" style="width:100px;" id="startTime" name="officeBoardroomXj.startTime" value="${officeBoardroomXj.startTime!}" dateFmt="HH:mm" notNull="true"/>
  	  			&nbsp;结束：
  	  			<@htmlmacro.datepicker class="input-txt" style="width:100px;" id="endTime" name="officeBoardroomXj.endTime" value="${officeBoardroomXj.endTime!}" dateFmt="HH:mm" notNull="true"/>
  	  			</td>
		       <th style="width:20%"><span class="c-orange mr-5">*</span>间隔：</th>
		       <td>
		  	  	<@htmlmacro.select style="width:110px;" valName="officeBoardroomXj.timeInterval" valId="timeInterval" notNull="true">
					<a val="">请选择</a>
					<a <#if officeBoardroomXj.timeInterval?default('0') == '30'>class="selected"</#if> val="30">30分钟</a>
					<a <#if officeBoardroomXj.timeInterval?default('0') == '60'>class="selected"</#if> val="60">60分钟</a>
				</@htmlmacro.select>
		  	  </td>
		        
		    </tr>
		    <tr>
		        <td colspan="4">
		        	&nbsp;&nbsp;午休：
		  	  	<@htmlmacro.datepicker class="input-txt" style="width:100px;" id="noonStartTime" name="officeBoardroomXj.noonStartTime" value="${officeBoardroomXj.noonStartTime!}" dateFmt="HH:mm" notNull="true"/>
		  	  	至&nbsp;
		  	  	<@htmlmacro.datepicker class="input-txt" style="width:100px;" id="noonEndTime" name="officeBoardroomXj.noonEndTime" value="${officeBoardroomXj.noonEndTime!}" dateFmt="HH:mm" notNull="true"/>
		  	  </td>
		    </tr>
		    <tr>
		        <th style="width:20%"><span class="c-orange mr-5">*</span>容纳人数：</th>
		        <td style="width:30%">
		        	<input type="text" msgName="容纳人数" class="input-txt fn-left" id="maxNumber" name="officeBoardroomXj.maxNumber" maxlength="5" dataType="integer" maxValue="9999" minValue="0" notNull="true" value="${officeBoardroomXj.maxNumber!}" style="width:180px;">
		        </td>
		        <th style="width:20%">位置：</th>
		        <td style="width:30%">
                <input type="text" msgName="位置" class="input-txt fn-left" id="address" name="officeBoardroomXj.address" maxlength="50"  value="${officeBoardroomXj.address!}" style="width:180px;">
		    </tr>
		    <tr>
		    	<th style="width:20%" rowspan="3"><span class="c-orange mr-5">*</span>配置说明：</th>
		    	<td style="width:30%">
		    	<#if officeBoardroomXj.rostrum?default(0)!=0><span class="ui-checkbox fn-left mt-5 ml-10 ui-checkbox-current" myfunclick="clickSms">
		    	<input type="checkbox" id="rostrums" name="rostrums" value="true" class="chk" checked="checked"/>
		    	<#else>
		       	<span class="ui-checkbox fn-left mt-5 ml-10" style="width:37px" myfunclick="clickSms">
        			<input type="checkbox" id="rostrums" name="rostrums" value="true" class="chk"/>
		       	</#if>
		       	主席台
        		</span>
        			<input type="text" msgName="主席台" class="input-txt" style="margin-top:-2px;margin-left:6px;" id="rostrum" name="officeBoardroomXj.rostrum" maxlength="5" dataType="integer" maxValue="999" minValue="0"   value="${officeBoardroomXj.rostrum!}" style="width:50px;">人
  	  			</td>
  	  			<td style="width:30%" colspan="2">
  	  				<#if officeBoardroomXj.conferenceSeats?default(0)!=0><span class="ui-checkbox fn-left mt-5 ml-10 ui-checkbox-current" myfunclick="clickSms">
  	  				<input type="checkbox" id="conferenceSeatss" name="conferenceSeatss" value="true" class="chk" checked="checked"/>
  	  				<#else>
		        	<span class="ui-checkbox fn-left mt-5 ml-10" myfunclick="clickSms">
        			<input type="checkbox" id="conferenceSeatss" name="conferenceSeatss" value="true" class="chk"/>
		        	</#if>
		        	会议席
        		</span>
        			<input type="text" msgName="会议席" class="input-txt" style="margin-top:-2px;margin-left:6px;" id="conferenceSeats" name="officeBoardroomXj.conferenceSeats" maxlength="5" dataType="integer" maxValue="999" minValue="0"  value="${officeBoardroomXj.conferenceSeats!}" style="width:50px;">人
		        </td>
		    </tr>   
		    <tr>
		    	<td style="width:30%">
		    	<#if officeBoardroomXj.tableType?default(0)!=0><span class="ui-checkbox fn-left mt-5 ml-10 ui-checkbox-current" myfunclick="clickSms">
		    	<input type="checkbox" id="tableTypes" name="tableTypes" value="true" class="chk" checked="checked"/>
		    	<#else>
		       	<span class="ui-checkbox fn-left mt-5 ml-10" myfunclick="clickSms">
        			<input type="checkbox" id="tableTypes" name="tableTypes" value="true" class="chk"/>
		       	</#if>
		       		围桌式
        		</span>
        			<input type="text" msgName="围桌式" class="input-txt" style="margin-top:-2px;margin-left:6px;" id="tableType" name="officeBoardroomXj.tableType" maxlength="5" maxlength="5" dataType="integer" maxValue="999" minValue="0" value="${officeBoardroomXj.tableType!}" style="width:50px;">人
  	  			</td>
  	  			<td style="width:30%" colspan="2">
  	  				<#if officeBoardroomXj.attendNumber?default(0)!=0><span class="ui-checkbox fn-left mt-5 ml-10 ui-checkbox-current" myfunclick="clickSms">
  	  				<input type="checkbox" id="attendNumbers" name="attendNumbers" value="true" class="chk" checked="checked"/>
		        	<#else>
		        	<span class="ui-checkbox fn-left mt-5 ml-10" myfunclick="clickSms">
        			<input type="checkbox" id="attendNumbers" name="attendNumbers" value="true" class="chk"/>
		        	</#if>
		        	列席
        		</span>
        			<input type="text" msgName="列席" class="input-txt" style="margin-top:-2px;margin-left:6px;" id="attendNumber" name="officeBoardroomXj.attendNumber" maxlength="5" maxlength="5" dataType="integer" maxValue="999" minValue="0" value="${officeBoardroomXj.attendNumber!}" style="width:50px;">人
		        </td>
		    </tr>   
		    <tr>
		    	<td style="width:30%">
		    	<#if officeBoardroomXj.isProjector?default(false)!=false><span class="ui-checkbox fn-left mt-5 ml-10 ui-checkbox-current" myfunclick="clickSms">
		    	<input type="checkbox" id="isProjector" name="officeBoardroomXj.isProjector" value="true" class="chk" checked="checked"/>
		    	<#else>
		       	<span class="ui-checkbox fn-left mt-5 ml-10" myfunclick="clickSms">
        			<input type="checkbox" id="isProjector" name="officeBoardroomXj.isProjector" value="true" class="chk"/>
		       	</#if>
        			带投影
        		</span>
  	  			</td>
  	  			<td style="width:30%" colspan="2">
		        	其他：
                	<input type="text" msgName="其他" class="input-txt" id="remark" name="officeBoardroomXj.remark" maxlength="50" value="${officeBoardroomXj.remark!}" style="width:180px;">
        		</span>
		        </td>
		    </tr>   
		    <tr>
		    	<td colspan="4" class="td-opt">
		    	    <a class="abtn-blue" href="javascript:void(0);" onclick="doSave() ">保存</a>
		    	    <a class="abtn-blue reset ml-5" href="javascript:void(0);">取消</a>
		        </td>
		    </tr>
		</@htmlmacro.tableDetail>
		</table>
	</form>
	</div>
<script>
	function back(){
		doSearch();
	}
	var isSubmit =false;
	function doSave(){
		if(isSubmit){
			return;
		}
		if(!checkAllValidate("#myBusinessTripForm")){
			return;
		}
		var startTime = document.getElementById("startTime").value;
		var endTime = document.getElementById("endTime").value;
		var timeInterval=document.getElementById("timeInterval").value
		var startTimes=startTime.split(':');  
		var endTimes=endTime.split(':');
		var startStr = "1"+startTimes[0]+startTimes[1];
		var endStr = "1"+endTimes[0]+endTimes[1];
		var start = parseInt(startStr);
		var end = parseInt(endStr);
		var intervals=parseInt(timeInterval);
		var xx=end-start;
		if(end<=start){
			showMsgWarn("开始时间必须小于结束时间！");
			return;
		}
		var noonStartTime = document.getElementById("noonStartTime").value;
		var noonEndTime = document.getElementById("noonEndTime").value;
		var noonStartTimes=noonStartTime.split(':');  
		var noonEndTimes=noonEndTime.split(':');
		var noonStartStr = "1"+noonStartTimes[0]+noonStartTimes[1];
		var noonEndStr = "1"+noonEndTimes[0]+noonEndTimes[1];
		var noonStart = parseInt(noonStartStr);
		var noonEnd = parseInt(noonEndStr);
		if(noonEnd<=noonStart){
			showMsgWarn("午休时间开始时间必须小于结束时间！");
			return;
		}
		if(start>noonStart||end<noonEnd){
			showMsgWarn("午休时间不在申请时间范围内！");
			return;
		}
		if(xx<intervals){
			showMsgWarn("可用时段的开始结束时间差不能小于时段时间间隔！");
			return;
		}
		var rostrums = $("#rostrums").attr('checked');
			if(!rostrums==true){
				$("#rostrum").val("");
			}
		var conferenceSeatss = $("#conferenceSeatss").attr('checked');
			if(!conferenceSeatss==true){
				$("#conferenceSeats").val("");
			}
		var tableTypes = $("#tableTypes").attr('checked');
			if(!tableTypes==true){
				$("#tableType").val("");
			}
		var attendNumbers = $("#attendNumbers").attr('checked');
			if(!attendNumbers==true){
				$("#attendNumber").val("");
			}
		isSubmit = true;
		var options = {
	       url:'${request.contextPath}/office/boardroommanage/boardroommanage-saveBoardRoom.action', 
	       success : showReply,
	       dataType : 'json',
	       clearForm : false,
	       resetForm : false,
	       type : 'post'
	    };
	    isSubmit = true;
	    $('#myBusinessTripForm').ajaxSubmit(options);
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

</script>
</@htmlmacro.moduleDiv >