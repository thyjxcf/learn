<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<@htmlmacro.moduleDiv titleName="">
		<table border="0" cellspacing="0" cellpadding="0" class="public-table table-list" id="listTable">
			<tr>
				<th width="40%">机房</th>
				<th width="30%">开门人员</th>
				<th width="30%">开门时间</th>
			</tr>
			<#if officeAttendanceDoorRecordDtoList?exists && (officeAttendanceDoorRecordDtoList?size>0)>
				<#list officeAttendanceDoorRecordDtoList as dto>
					<tr>
						<td>${dto.placeName!}</td>
						<td>${dto.teacherName!}</td>
						<td>${dto.openTime!}</td>
					</tr>
				</#list>
			<#else>
				<tr><td colspan=9><p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td></tr>		
			</#if>
		</table>
		<@htmlmacro.Toolbar container="#courseContainer"></@htmlmacro.Toolbar>
	<script type="text/javascript">
	    function doDetail(courseId, dateStr, startPeriod, endPeriod) {
	    	
	    }
	</script>    
</@htmlmacro.moduleDiv>