<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#include "/common/handlefielderror.ftl">
<@htmlmacro.moduleDiv titleName="">
<div class="pub-table-wrap">
	<div class="pub-table-inner">
	<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
	<div class="query-part">
	    <div class="fn-right ml-10">
	    	<a href="javascript:doAdd()" class="abtn-orange-new fn-right" >新增考勤时段</a>
	    </div>
		<div class="fn-clear"></div>
	</div>
	</div>
	</div>
</div>
<@htmlmacro.tableList class="public-table table-list table-list-edit">
  	<tr>
    	<th >序号</th>
    	<th >考勤时段</th>
    	<th >上班时间</th>
    	<th >下午上班时间</th>
    	<th >下班时间</th>
    	<th >是否弹性工作制</th>
    	<th >上班弹性范围</th>
    	<th >下班弹性范围</th>
    	<th >上班可提前打卡时间</th>
    	<th class="t-center">操作</th>
    </tr>
    <#if officeAttendanceSetList?exists && officeAttendanceSetList?size gt 0>
    	<#list officeAttendanceSetList as attendanceSet>
    		<tr>
    			<td >${attendanceSet_index+1}</td>
    			<td >${attendanceSet.name!}</td>
    			<td >${attendanceSet.startTime!}</td>
    			<td >${attendanceSet.pmTime!}</td>
    			<td >${attendanceSet.endTime!}</td>
    			<#if attendanceSet.isElastic?c=="true">
	    			<td >是</td>
	    			<td >${attendanceSet.elasticRange!}分钟</td>
	    			<td >${attendanceSet.endElasticRange!}分钟</td>
    			<#else>
	    			<td >否</td>
	    			<td ></td>
	    			<td ></td>
    			</#if>
    			<td >${attendanceSet.startRange!}小时</td>
		    	<td class="t-center">
		    		<a href="javascript:void(0);" onclick="doEdit('${attendanceSet.id!}');">编辑</a>
		    		<a href="javascript:void(0);" onclick="doDelete('${attendanceSet.id!}');">删除</a>
		    	</td>
    		</tr>
    	</#list>
    <#else>
    	<tr>
    		<td colspan='10'><p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td>
	 	</tr>
    </#if>
</@htmlmacro.tableList>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script>
	$(document).ready(function(){
		//load("#showListDiv","${request.contextPath!}/");
	});
	function doAdd(){
		load("#showListDiv","${request.contextPath!}/office/teacherAttendance/teacherAttendance-setEdit.action");
	}
	function doEdit(id){
		load("#showListDiv","${request.contextPath!}/office/teacherAttendance/teacherAttendance-setEdit.action?id="+id);
	}
	function doDelete(id){
		if(showConfirm("确定要删除该考勤时段?")){
			$.getJSON("${request.contextPath}/office/teacherAttendance/teacherAttendance-setDelete.action",{id:id},function(data){
				if(data.operateSuccess){
					showMsgSuccess(data.promptMessage,"",getAttendanceSetTab);
				}else{
					showMsgError(data.errorMessage);
					return;
				}
		   }).error(function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);});
		}
	}
</script>
</@htmlmacro.moduleDiv>