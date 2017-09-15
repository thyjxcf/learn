<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<@htmlmacro.moduleDiv titleName="">
		<table border="0" cellspacing="0" cellpadding="0" class="public-table table-list" id="listTable">
			<tr>
				<th width="15%">学号</th>
				<th width="15%">姓名</th>
				<th width="15%">性别</th>
				<th width="15%">班级</th>
				<th width="15%">课程</th>
				<th width="10%">已到次数/总次数</th>
			</tr>
			<#if attendanceRadioDtoList?exists &&(attendanceRadioDtoList?size>0)>
	   			<#list attendanceRadioDtoList as dto>
					<tr>
						<td>${dto.stuCode!}</td>
						<td>${dto.stuName!}</td>
						<td>${dto.sexStr!}</td>
						<td>${dto.className!}</td>
						<td>${dto.courseName!}</td>
						<td>${dto.radioInfo!}</td>
					</tr>
				</#list>
			<#else>
				<tr><td colspan=9><p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td></tr>		
			</#if>
		</table>
	<script type="text/javascript">
	    
	</script>    
</@htmlmacro.moduleDiv>