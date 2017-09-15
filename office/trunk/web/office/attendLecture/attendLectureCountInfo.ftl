<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="">
<form id="formId">
<div class="pub-table-wrap">
	<div class="pub-table-inner">
		<div class="query-builder-nobg mt-15" style="padding:0 0 20px 0;">  
    		<div class="query-part">
				&nbsp;&nbsp;<a href="javascript:void(0);" onclick="doExport();" class="abtn-blue fn-right">导出</a>
			</div>
    	</div>
    </div>
</div>
	<input type="hidden" value="${deptId!}" name="deptId">
	<input type="hidden" value="${((endTime)?string('yyyy-MM-dd'))?if_exists}" name="endTime">
	<input type="hidden" value="${((startTime)?string('yyyy-MM-dd'))?if_exists}" name="startTime">
	<input type="hidden" value="${applyUserName!}" name="applyUserName">
	<input type="hidden" name="noDefault" value="noDefault">
	<input type="hidden" name="tableName" value="${tableName!}">
	<input type="hidden" name="deptName" value="${deptName!}">
</form>
<table border="0" cellspacing="0" cellpadding="0" class="public-table table-list">
	<tr>
        <th colspan="8" style="text-align:center;" class="p1 pt-10">${tableName!}</th>
    </tr>
    <tr>
    	<th width="12%" style="text-align:center;">听课教师</th>
    	<th width="12%" style="text-align:center;">听课时间</th>
    	<th width="12%" style="text-align:center;">课次</th>
    	<th width="12%" style="text-align:center;">班级</th>
    	<th width="12%" style="text-align:center;">学科</th>
    	<th width="12%" style="text-align:center;">课题</th>
    	<th width="12%" style="text-align:center;">授课教师</th>
    	<th style="text-align:center;">授课内容</th>
    </tr>
    <#if officeAttendLectureList?exists && officeAttendLectureList?size gt 0>
    <#list officeAttendLectureList as office>
    	<tr>
    		<td style="text-align:center;">${office.applyUserName!}</td>
    		<td style="text-align:center;">${((office.attendDate)?string('yyyy-MM-dd'))?if_exists}</td>
    		<td style="text-align:center;">${appsetting.getMcodeName("DM-TKSD",office.attendPeriod?default(''))}${appsetting.getMcodeName("DM-TKJC",office.attendPeriodNum?default(''))}</td>
    		<td style="text-align:center;">${office.className!}</td>
    		<td style="text-align:center;">${office.subjectName!}</td>
    		<td style="text-align:center;">${office.projectName!}</td>
    		<td style="text-align:center;">${office.teacherName!}</td>
    		<td style="text-align:center;">${office.projectContent!}</td>
    	</tr>
	</#list>
	<#else>
     <tr>
   		<td colspan="8" > <p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
	 </tr>
     </#if>
</table>
<#if officeAttendLectureList?exists && officeAttendLectureList?size gt 0>
<@htmlmacro.Toolbar container="#showListDiv">
</@htmlmacro.Toolbar>
</#if>
<p class="pt-20 t-center">
    <a class="abtn-blue-big ml-5" href="javascript:void(0);" onclick="goBack();">返回</a>
</p>
<script>
	function goBack(){
		load("#showListDiv","${request.contextPath}/office/attendLecture/attendLecture-countList.action?"+jQuery("#formId").serialize());
	}
	function doExport(){
		location.href="${request.contextPath}/office/attendLecture/attendLecture-doInfoExport.action?"+jQuery("#formId").serialize()+"&total="+${total?string!};
	}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>