<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<@htmlmacro.moduleDiv titleName="">
		<table border="0" cellspacing="0" cellpadding="0" class="public-table table-list" id="listTable">
			<tr>
				<th width="15%">日期</th>
				<th width="15%">节次</th>
				<th width="15%">机房</th>
				<th width="15%">任课老师</th>
				<th width="20%">班级</th>
				<th width="10%">已到人数/总人数</th>
				<th width="10%">详情</th>
			</tr>
			<#if attendanceDtoList?exists &&(attendanceDtoList?size>0)>
	   			<#list attendanceDtoList as dto>
					<tr>
						<td>${dto.dateStr!}</td>
						<td>${dto.periodStr!}</td>
						<td>${dto.roomName!}</td>
						<td>${dto.teacherName!}</td>
						<td>${dto.className!}</td>
						<td>${dto.attendanceInfo!}</td>
						<td><a href="javascript:void(0);" onclick="doDetail('${dto.courseId!}','${dto.dateStr!}','${dto.startPeriod!}','${dto.endPeriod!}');" class="mr-10">
						 	<img src="${request.contextPath}/static/images/icon/detail.png" alt="详情" title="详情"></a>
						</td>
					</tr>
				</#list>
			<#else>
				<tr><td colspan=9><p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td></tr>		
			</#if>
		</table>
	<script type="text/javascript">
	    function doDetail(courseId, dateStr, startPeriod, endPeriod) {
	         var url="${request.contextPath}/office/attendance/attendance-detail.action?courseId="+courseId+"&dateStr="+dateStr+"&startPeriod="+startPeriod+"&endPeriod="+endPeriod;
	         load("#courseAttendanceDiv", url);
	    }
	</script>    
</@htmlmacro.moduleDiv>