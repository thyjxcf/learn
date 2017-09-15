<#import "/common/htmlcomponent.ftl" as common />
<#import "/common/commonmacro.ftl" as commonmacro>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<@common.tableList id="listTable" name="listTable" class="public-table table-list table-dragSort" style="table-layout:fixed">
	<tr>
		<th class="t-center" width="20%">考勤组名</th>
		<th class="t-center" width="15%">考勤时段</th>
	    <th class="t-center" width="15%">办公地点</th>
		<th class="t-center" width="30%">考勤组人员</th>
		<th class="t-center" width="20%">操作</th>

	</tr>
<#if groupList?exists &&  (groupList?size>0)>
	<#list groupList as group>
		<tr>
			<td>${group.name!}</td>
			<td>${group.attSetName!}</td>
			<td>
			<#if group.placeName?length gt 10>  
             <@common.cutOff4List str="${group.placeName?default('')}" length=30/>
             <#else>${group.placeName!}   
             </#if>
			</td>
			<td>
			<#if group.userNames?length gt 25>  
             <@common.cutOff4List str="${group.userNames?default('')}" length=30/>
             <#else>${group.userNames!}   
             </#if>
			</td>
			<td class="t-center" ><a href="javascript:void(0)" onclick="doEdit('${group.id!}')"  class="ml-5"  >修改</a>
			<a href="javascript:void(0)" onclick="doDelete('${group.id!}');"  class="ml-5"  >删除</a>
			<a href="javascript:void(0)" onclick="groupPeopleList('${group.id!}')"  class="ml-5"  >查看组成员</a>
			</td>
		</tr>
	</#list>
<#else>
	<tr>
		<td colspan="5"><p class="no-data mt-20 mb-20">还没有任何记录哦！</p></td>
	</tr>
</#if>
</@common.tableList>
<script>
		function doDelete(id){
			if(!confirm("该操作不可撤销,确定删除?")){
			return;
		    }
			jQuery.ajax({
				url:"${request.contextPath}/office/teacherAttendance/teacherAttendance-deleteGroup.action?groupId="+id,
				dataType:"json",
				async:false,
				type:"post",
				success:function(data){
					if(data.operateSuccess){
						showMsgSuccess(data.promptMessage,"",function(){
							load("#groupListDiv","${request.contextPath}/office/teacherAttendance/teacherAttendance-groupList.action");
						})
						
					}else{
						showMsgError(data.errorMessage);
					}
				}
			});
		}
		function doEdit(id){
			load("#showListDiv","${request.contextPath}/office/teacherAttendance/teacherAttendance-newGroup.action?groupId="+id+"&addAttendance="+true);
		}
		function groupPeopleList(id){
			load("#showListDiv","${request.contextPath}/office/teacherAttendance/teacherAttendance-groupPeopleList.action?groupId="+id);
		}
</script>
