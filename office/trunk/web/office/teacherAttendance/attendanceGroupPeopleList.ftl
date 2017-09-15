<#import "/common/htmlcomponent.ftl" as common />
<#import "/common/commonmacro.ftl" as commonmacro>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<@common.tableList id="listTable" name="listTable" class="public-table table-list table-dragSort" style="table-layout:fixed">
	<tr>
		<th class="t-center" width="20%">序号</th>
		<th class="t-center" width="20%">姓名</th>
	    <th class="t-center" width="20%">部门</th>
		<th class="t-center" width="40%">是否参与考勤</th>

	</tr>
<#if groupPeopelDtoList?exists &&  (groupPeopelDtoList?size>0)>
	<#assign index_num = 0 >
	<#list groupPeopelDtoList as group>
		<#assign index_num = index_num + 1>
		
		<tr>
			<td>${index_num!}</td>
			<td>${group.name!}</td>
			<td>${group.departmentName!}</td>
			<td>
			<#if group.addAttendancestatistics >
			<span class="ui-radio ui-radio-current" data-name="a_${group.groupUserId}"><input checked="checked" onclick="changeState('${group.groupUserId}',true)" type="radio" class="radio"  value="true">是</span>
			<span class="ui-radio " data-name="a_${group.groupUserId}"><input  onclick="changeState('${group.groupUserId}',false)" type="radio" class="radio"  value="false">否</span>
			<#else>
			<span class="ui-radio " data-name="a_${group.groupUserId}"><input  onclick="changeState('${group.groupUserId}',true)" type="radio" class="radio"  value="true">是</span>
			<span class="ui-radio ui-radio-current" data-name="a_${group.groupUserId}"><input checked="checked" onclick="changeState('${group.groupUserId}',false)" type="radio" class="radio" value="false">否</span>
			</#if>
			
			
			</td>
		</tr>
	</#list>
<#else>
	<tr>
		<td colspan="5"><p class="no-data mt-20 mb-20">还没有任何记录哦！</p></td>
	</tr>
</#if>
</@common.tableList>
	<p class="t-center pt-15">
    <a href="javascript:;" class="abtn-blue-big" onclick="doBack();" >返回</a>
    </p>
<script>
function doBack(){
		load("#showListDiv","${request.contextPath}/office/teacherAttendance/teacherAttendance-groupMain.action");
	}
	function changeState(id,data){
		$.ajax({
			url:"${request.contextPath}/office/teacherAttendance/teacherAttendance-groupPeopleEdit.action?&groupUserId="+id+"&addAttendance="+data,
			type:"post",
			dataType:"json",
			async:false,
			success:function(data){
				if(data.operateSuccess){
					/*
					showMsgSuccess(data.promptMessage,"",function(){
						load("#showListDiv","${request.contextPath}/office/teacherAttendance/teacherAttendance-groupPeopleList.action?groupId=${groupId!}");
					});
					*/			
				}else{
					showMsgError(data.errorMessage);
				}
			}
		});
	}
</script>