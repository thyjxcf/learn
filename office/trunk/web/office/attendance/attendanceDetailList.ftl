<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<@htmlmacro.moduleDiv titleName="">
	 <form name="listForm" id="listForm">
	     <input type="hidden" id="phaseId" name="phaseId" value="${phaseId!}" />
		 <table class="public-table table-list table-list-edit mt-0">
	        <tr>
	            <th class="t-center" width="20%">学号</th>
				<th class="t-center" width="20%">姓名</th>
				<th class="t-center" width="15%">性别</th>
				<th class="t-center" width="20%">班级</th>
				<th class="t-center" width="25%">是否刷卡</th>
	        </tr>
	        <#if attendanceRadioDtoList?exists &&(attendanceRadioDtoList?size>0)>
	        <#list attendanceRadioDtoList as dto>
	        <tr>
	            <td class="t-center">${dto.stuCode!}</td>
	            <td class="t-center">${dto.stuName!}</td>
	            <td class="t-center">${dto.sexStr!}</td>
	        	<td class="t-center">${dto.className!}</td>
	        	<td class="t-center">${dto.radioInfo!}</td>
	        </tr>
	        </#list>
	    <#else>
	        <tr><td colspan="9"> <p class="no-data mt-50 mb-50">还没有记录哦！</p></td></tr>
	    </#if>
	    </table> 
		<p class="t-center pt-30">
		  <a href="javaScript:goBack();" class="abtn-blue reset ml-5">返回</a>
		</p>
	<div class="popUp-layer" id="batchSetDiv" style="display:none;width:430px;"></div>	
	</form>
	<script type="text/javascript">
	vselect();
	function goBack() {
	   load("#courseAttendanceDiv","${request.contextPath}/office/attendance/attendance-list.action?courseId=${courseId!}");
	}
	</script>
	<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>
