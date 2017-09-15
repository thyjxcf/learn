<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="">
<#include "/common/handlefielderror.ftl">
<#import "/common/commonmacro.ftl" as commonmacro>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
		<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
    		<div class="query-part">
    			<div class="query-tt ml-10">
					<span class="fn-left">开始时间：</span>
				</div>
				<div class="fn-left">
    			<@htmlmacro.datepicker class="input-txt" style="width:100px;" id="startTime" value="${((startTime)?string('yyyy-MM-dd'))?if_exists}"/>
				</div>
    			<div class="query-tt ml-10">
					<span class="fn-left">结束时间：</span>
				</div>
				<div class="fn-left">
    			<@htmlmacro.datepicker class="input-txt" style="width:100px;" id="endTime" value="${((endTime)?string('yyyy-MM-dd'))?if_exists}"/>
				</div>
				<#if unitViewRole>
	    			<div class="query-tt ml-10">
						<span class="fn-left">部门：</span>
					</div>
					<div class="select_box fn-left" objectId="deptId">
					<@htmlmacro.select style="width:100px;" valName="deptId" txtId="deptIdTxt" valId="deptId" myfunchange="doSearch">
						<a val="">--请选择--</a>
						<#if directDepts?exists>
			                	<#list directDepts as pn> 
			                   		<a val="${pn.id}" <#if pn.id==deptId?default('')>class="selected"</#if>><span>${pn.deptname?default("")}</span></a>
				                </#list>
				        </#if>
					</@htmlmacro.select>
					</div>
				<#else>
					<input type="hidden" id="deptId" name="deptId" />
				</#if>
				<#if unitViewRole || groupHead>
	    			<div class="query-tt ml-10">
						<span class="fn-left">姓名：</span>
					</div>
					<div class="fn-left">
						<input type="input" class="input-txt" style="width:100px;" id="userName" value="${userName!}">
					</div>
				<#else>
					<input type="hidden" id="userName" />
				</#if>
				&nbsp;&nbsp;<a href="javascript:void(0);" onclick="doSearch();" class="abtn-blue">查找</a>
				&nbsp;&nbsp;<a href="javascript:void(0);" onclick="doExport();" class="abtn-blue">导出</a>
			</div>
    	</div>
    </div>
</div>
<table border="0" cellspacing="0" cellpadding="0" class="public-table table-list">
    <tr>
    	<th width="10%">序号</th>
    	<th width="15%">申请人</th>
    	<th width="15%">请假开始时间</th>
    	<th width="15%">请假结束时间</th>
    	<th width="15%">部门</th>
    	<th width="10%">请假类型</th>
    	<th width="10%">请假天数</th>
    	<th width="15%" class="t-center">查看</th>
    </tr>
    <#if officeTeacherLeaveList?exists&&(officeTeacherLeaveList?size>0)>
     <#list officeTeacherLeaveList as item>
     <tr>
     	<td>
     		${item_index+1}
     	</td>
     	<td>
     		${item.applyUserName!}
     	</td>
     	<td>
     		${((item.leaveBeignTime)?string('yyyy-MM-dd'))?if_exists}
     	</td>
     	<td>
     		${((item.leaveEndTime)?string('yyyy-MM-dd'))?if_exists}
     	</td>
     	<td>
     		${item.deptName!}
     	</td>
     	<td>
     	${appsetting.getMcode("DM-QJLX").get(item.leaveType?default(''))}
     	</td>
     	<td >${item.days?string('0.#')!}天</td>
     	<td class="t-center">
			<a href="javascript:void(0);" onclick="seeDetail('${item.id!}');">查看</a>
     	</td>
     </tr>
     </#list>
     <#else>
     <tr>
   		<td colspan="55" > <p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
	 </tr>
     </#if>
</table>
<#if officeTeacherLeaveList?exists&&(officeTeacherLeaveList?size>0)>
<@htmlmacro.Toolbar container="#showListDiv">
</@htmlmacro.Toolbar>
</#if>
<script>
$(document).ready(function(){
	vselect();
});
function doSearch(){
	var startTime=$("#startTime").val();
	var endTime=$("#endTime").val();
	if(startTime!=''&&endTime!=''){
		var re = compareDate(startTime,endTime);
		if(re==1){
			showMsgError("结束时间不能早于开始时间，请重新选择！");
			return;
		}
	}
	var deptId=$("#deptId").val();
	var userName=$("#userName").val();
	var str="?startTime="+startTime+"&endTime="+endTime+"&deptId="+deptId+"&userName="+encodeURIComponent(userName);
	load("#showListDiv","${request.contextPath}/office/teacherLeave/teacherLeave-applyQueryList.action"+str);
}
function seeDetail(id){
	var startTime=$("#startTime").val();
	var endTime=$("#endTime").val();
	var deptId=$("#deptId").val();
	var userName=$("#userName").val();
	var str="?startTime="+startTime+"&endTime="+endTime+"&deptId="+deptId+"&userName="+encodeURIComponent(userName)+"&fromTab=3&id="+id;
	load("#showListDiv","${request.contextPath}/office/teacherLeave/teacherLeave-applyDetail.action"+str);
}
function doExport(){
	var startTime=$("#startTime").val();
	var endTime=$("#endTime").val();
	if(startTime!=''&&endTime!=''){
		var re = compareDate(startTime,endTime);
		if(re==1){
			showMsgError("结束时间不能早于开始时间，请重新选择！");
			return;
		}
	}
	var deptId=$("#deptId").val();
	var userName=$("#userName").val();
	var str="?startTime="+startTime+"&endTime="+endTime+"&deptId="+deptId+"&userName="+encodeURIComponent(userName);
	location.href="${request.contextPath}/office/teacherLeave/teacherLeave-teacherLeaveExport.action"+str;
}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>
