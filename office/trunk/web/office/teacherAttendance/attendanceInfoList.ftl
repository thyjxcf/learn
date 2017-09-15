<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#include "/common/handlefielderror.ftl">
<@htmlmacro.moduleDiv titleName="">
<form id="queryForm">
<div class="pub-table-wrap">
	<div class="pub-table-inner">
	<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
	<div class="query-part">
		<#if teacherAttenceAdmin>
			<div class="query-tt ml-10">
				<span class="fn-left">部门：</span>
			</div>
			<div class="select_box fn-left mr-10">
			</div>
			<@htmlmacro.select style="width:150px;" valId="deptId" valName="deptId" myfunchange="doSearch" >
				<a val=""><span>全部</span></a>
	        	<#if deptList?exists && deptList?size gt 0>
	            	<#list deptList as dept>
	            		<a val="${dept.id!}" <#if deptId?default('') == dept.id>class="selected"</#if>><span>${dept.deptname!}</span></a>
	            	</#list>
	        	</#if>
			</@htmlmacro.select>
		</#if>
		<div class="query-tt ml-10">
			<span class="fn-left">截止日期：</span>
		</div>
		<div class="fn-left">
		<@htmlmacro.datepicker class="input-txt" style="width:100px;" maxDate="${(.now)?string('yyyy-MM-dd')}" name="startTime" id="startTime" value="${((startTime)?string('yyyy-MM-dd'))?if_exists}"/>
		</div>
		<span class="fn-left">&nbsp;-&nbsp;</span>
		<div class="fn-left">
		<@htmlmacro.datepicker class="input-txt" style="width:100px;" maxDate="${(.now)?string('yyyy-MM-dd')}" name="endTime" id="endTime" value="${((endTime)?string('yyyy-MM-dd'))?if_exists}"/>
		</div>
		<#if teacherAttenceAdmin>
			<div class="query-tt ml-10"><span class="fn-left">姓名：</span></div>
	        	<input name="searchName" id="searchName" value="${searchName!}" maxLength="50" class="input-txt fn-left" style="width:170px;"/>
		</#if>
		<a href="javascript:void(0)" onclick="doSearch();" class="abtn-blue fn-left ml-20">查找</a>
		<a href="javascript:void(0)" onclick="doExport();" class="abtn-blue fn-left ml-20">导出</a>
		<div class="fn-clear"></div>
	</div>
	</div>
	</div>
</div>
</form>
<@htmlmacro.tableList class="public-table table-list table-list-edit">
  	<tr>
    	<th width="15%">考勤日期</th>
    	<th width="10%">姓名</th>
    	<th width="15%">所在部门</th>
    	<th width="15%">上班打卡时间</th>
    	<th width="15%">下班打卡时间</th>
    	<th width="7%">状态</th>
    	<th width="7%">时长</th>
    	<th >考勤说明</th>
    </tr>
    <#if officeAttendanceInfoList?exists && officeAttendanceInfoList?size gt 0>
    	<#list officeAttendanceInfoList as attendanceInfo>
    		<tr>
    			<td >${(attendanceInfo.attenceDate?string('yyyy-MM-dd'))?if_exists}</td>
    			<td >${attendanceInfo.userName!}</td>
    			<td >${attendanceInfo.deptName!}</td>
    			<td >
    			<#if attendanceInfo.haveColckApplyAm>补卡申请 待审核
    			<#else>
    				<#if attendanceInfo.clockTimeAm?exists>
    					${(attendanceInfo.clockTimeAm?string('yyyy-MM-dd HH:mm:ss'))?if_exists}
    					<#if teacherAttenceAdmin>
    					<a href="javascript:void(0);" onclick="deleteInfo('${attendanceInfo.infoIdAm!}');">删除
    					</#if>
    				<#else>
						<#if attendanceInfo.mySelf>
							<#if !attendanceInfo.canNoApplyAm>
								<a href="javascript:void(0);" onclick="cardApply('1','${(attendanceInfo.attenceDate?string('yyyy-MM-dd'))?if_exists}','${attendanceInfo.userId!}');">
    							<#if attendanceInfo.auditNoPassAm?default(false)>未通过-继续申请<#elseif attendanceInfo.isHoliday?default(false)>补卡申请<#else>缺卡-补卡申请</#if>
							</#if>
						<#else>
							<#if !attendanceInfo.canNoApplyAm && !attendanceInfo.isHoliday?default(false)>
								缺卡
							</#if>
						</#if>
    				</#if>
    			</#if>
    			</td>
    			<td >
    			<#if attendanceInfo.haveColckApplyPm>补卡申请 待审核
    			<#else>
    				<#if attendanceInfo.clockTimePm?exists>
    					${(attendanceInfo.clockTimePm?string('yyyy-MM-dd HH:mm:ss'))?if_exists}
    					<#if teacherAttenceAdmin>
    					<a href="javascript:void(0);" onclick="deleteInfo('${attendanceInfo.infoIdPm!}');">删除
    					</#if>
    				<#else>
						<#if attendanceInfo.mySelf>
							<#if !attendanceInfo.canNoApplyPm>
								<a href="javascript:void(0);" onclick="cardApply('2','${(attendanceInfo.attenceDate?string('yyyy-MM-dd'))?if_exists}','${attendanceInfo.userId!}');">
    							<#if attendanceInfo.auditNoPassPm?default(false)>未通过-继续申请<#elseif attendanceInfo.isHoliday?default(false)>补卡申请<#else>缺卡-补卡申请</#if>
							</#if>
						<#else>
							<#if !attendanceInfo.canNoApplyPm && !attendanceInfo.isHoliday?default(false)>
								缺卡
							</#if>
						</#if>
    				</#if>
    			</#if>
    			</td>
    			<td ><#if !attendanceInfo.isHoliday?default(false)>${attendanceInfo.clockStateTotal!}<#else>正常</#if></td>
    			<td >${attendanceInfo.timeLength!}</td>
    			<td >
		             <@htmlmacro.cutOff4List str="${attendanceInfo.remark?default('')}" length=13/>
    			</td>
    			
    		</tr>
    	</#list>
    <#else>
    	<tr>
    		<td colspan='10'><p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td>
	 	</tr>
    </#if>
</@htmlmacro.tableList>
<#if officeAttendanceInfoList?exists && officeAttendanceInfoList?size gt 0>
	<@htmlmacro.Toolbar container="#contentDiv">
	</@htmlmacro.Toolbar>
</#if> 
<div class="popUp-layer" id="classLayer3" style="display:none;width:500px;"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script>
	$(document).ready(function(){
		
	});
	function cardApply(type,attenceDate,userId){
	   	var url="${request.contextPath}/office/teacherAttendance/teacherAttendance-clockApply.action?type="+type+"&attenceDate="+attenceDate+"&userId="+userId;
	   	openDiv("#classLayer3", "",url, false,"","300px");
	}
	function deleteInfo(id){
		if(showConfirm("确定要删除该考勤信息?")){
			$.getJSON("${request.contextPath}/office/teacherAttendance/teacherAttendance-deleteInfo.action",{id:id},function(data){
				if(data.operateSuccess){
					showMsgSuccess(data.promptMessage,"",doSearch);
				}else{
					showMsgError(data.errorMessage);
					return;
				}
		   }).error(function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);});
		}
	}
	function doSearch(){
		var startTime=$("#startTime").val();
		var endTime=$("#endTime").val();
		if(startTime!=''&&endTime!=''){
			var re = compareDate(startTime,endTime);
			if(re==1){
				showMsgError("截止日期开始时间不能大于结束时间，请修改！");
				return;
			}
		}
		load("#contentDiv","${request.contextPath}/office/teacherAttendance/teacherAttendance-infoList.action?"+jQuery("#queryForm").serialize());
		//load("#contentDiv","${request.contextPath}/office/teacherAttendance/teacherAttendance-infoList.action?startTime="+startTime+"&endTime="+endTime+"&searchName="+searchName+"&deptId="+deptId);
	}
	function doExport(){
		var startTime=$("#startTime").val();
		var endTime=$("#endTime").val();
		if(startTime!=''&&endTime!=''){
			var re = compareDate(startTime,endTime);
			if(re==1){
				showMsgError("截止日期开始时间不能大于结束时间，请修改！");
				return;
			}
		}
		location.href = "${request.contextPath}/office/teacherAttendance/teacherAttendance-export.action?"+jQuery("#queryForm").serialize();
	}
</script>
</@htmlmacro.moduleDiv>