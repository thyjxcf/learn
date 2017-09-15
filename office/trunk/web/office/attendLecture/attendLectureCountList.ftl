<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="">
<form id="countId">
<div class="pub-table-wrap">
	<div class="pub-table-inner">
		<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
    		<div class="query-part">
    			<div class="query-tt ml-10">
					<span class="fn-left">听课时间：</span>
				</div>
				<div class="fn-left">
    			<@htmlmacro.datepicker class="input-txt" style="width:100px;" id="startTime" value="${((startTime)?string('yyyy-MM-dd'))?if_exists}"/>
				</div>
				<span class="fn-left">&nbsp;&nbsp;至&nbsp;&nbsp;</span>
				<div class="fn-left">
    			<@htmlmacro.datepicker class="input-txt" style="width:100px;" id="endTime" value="${((endTime)?string('yyyy-MM-dd'))?if_exists}"/>
				</div>
				<#if unitViewRole>
	    			<div class="query-tt ml-10">
						<span class="fn-left">部门：</span>
					</div>
					<div class="select_box fn-left" objectId="deptId">
					<@htmlmacro.select style="width:150px;" valName="deptId" txtId="deptIdTxt" valId="deptId" myfunchange="doSearch">
						<a val="">--请选择--</a>
						<#if deptList?exists>
			                	<#list deptList as dept> 
			                   		<a val="${dept.id}" <#if dept.id==deptId?default('')>class="selected"</#if>><span>${dept.deptname!}</span></a>
				                </#list>
				        </#if>
					</@htmlmacro.select>
					</div>
				<#else>
					<input type="hidden" id="deptId" name="deptId" />
				</#if>
				<#if unitViewRole || groupHead>
					<div class="query-tt ml-10">
						<span class="fn-left">提交人：</span>
					</div>
					<div class="fn-left">
					<input style="width:100px;" class="input-txt"  type="text" id="applyUserName" name="applyUserName" value="${applyUserName!}">
					</div>
				<#else>
					<input type="hidden" id="applyUserName" name="applyUserName" />
				</#if>
				&nbsp;&nbsp;<a href="javascript:void(0);" onclick="doSearch();" class="abtn-blue">查找</a>
				&nbsp;&nbsp;<a href="javascript:void(0);" onclick="doExport(true);" class="abtn-blue">导出</a>
			</div>
    	</div>
    </div>
</div>
<input type="hidden" name="noDefault" value="noDefault">
</form>
<table border="0" cellspacing="0" cellpadding="0" class="public-table table-list">
    <tr>
    	<th width="20%" style="text-align:center;">部门名</th>
    	<th width="20%" style="text-align:center;">教师</th>
    	<th width="15%" style="text-align:center;">校内听课数(数量)</th>
    	<th width="15%" style="text-align:center;">校外听课数(数量)</th>
    	<th width="10%" style="text-align:center;">合计</th>
    	<th width="20%" style="text-align:center;">说明</th>
    </tr>
    <#if officeAttendLectureList?exists && officeAttendLectureList?size gt 0>
    <#list officeAttendLectureList as office>
    	<tr>
    		<td style="text-align:center;">${office.deptName!}</td>
    		<td style="text-align:center;">${office.applyUserName!}</td>
    		<td style="text-align:center;">${office.schoolInNum?default(0)}</td>
    		<td style="text-align:center;">${office.schoolOutNum?default(0)}</td>
    		<td style="text-align:center;">${office.lectureNum?default(0)}</td>
    		<td style="text-align:center;">
	    		<a href="javascript:void(0);" onclick="doCheck('${office.applyUserId!}',false);"><img src="${request.contextPath}/static/images/icon/view.png" alt="查看"></a>
    		</td>
    	</tr>
	</#list>
		<tr>
			<td style="text-align:center;">合计</td>
    		<td style="text-align:center;">${officeAttendLectureList?size?default(0)}</td>
    		<td style="text-align:center;">${totalInNum?default(0)}</td>
    		<td style="text-align:center;">${totalOutNum?default(0)}</td>
    		<td style="text-align:center;">${totalNum?default(0)}</td>
    		<td style="text-align:center;">
	    		<a href="javascript:void(0);" onclick="doCheck('${deptId!}',true);"><img src="${request.contextPath}/static/images/icon/view.png" alt="查看"></a>
    		</td>
		</tr>
	<#else>
     <tr>
   		<td colspan="6" > <p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
	 </tr>
     </#if>
</table>

<script>
function doSearch(){
	var startTime=$("#startTime").val();
	var endTime=$("#endTime").val();
	if(startTime!=''&&endTime!=''){
		var re = compareDate(startTime,endTime);
		if(re==1){
			showMsgError("听课时间有误，请修改");
			return;
		}
	}
	load("#showListDiv","${request.contextPath}/office/attendLecture/attendLecture-countList.action?"+jQuery("#countId").serialize());
}

function doExport(obj){
	location.href="${request.contextPath}/office/attendLecture/attendLecture-doExport.action?"+jQuery("#countId").serialize()+"&total="+obj;
}
function doCheck(deptId,obj){
	load("#showListDiv","${request.contextPath}/office/attendLecture/attendLecture-countInfo.action?deptName="+deptId+"&total="+obj+"&"+jQuery("#countId").serialize());
}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>